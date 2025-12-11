package engine.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;



public final class SoundManager {
    private static final Map<String, SoundData> soundDataCache = new ConcurrentHashMap<>();
    private static final Map<String, ClipPool> pools = new ConcurrentHashMap<>();

    private static final ExecutorService executor = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "SoundWorker-") ;
        t.setDaemon(true);
        return t;
    });

    private static volatile float globalVolume = 0.5f;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private SoundManager() { }

    public static void init() {
        if (initialized.compareAndSet(false, true)) {

        }
    }


    public static boolean preload(String resourcePath, int poolSize) {
        Objects.requireNonNull(resourcePath);
        init();
        try {
            SoundData sd = soundDataCache.computeIfAbsent(resourcePath, SoundManager::loadSoundDataFromResource);
            if (sd == null) return false;

            if (poolSize > 0) {
                ClipPool p = new ClipPool(resourcePath, sd, poolSize);
                pools.put(resourcePath, p);
            }
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }


    public static CompletableFuture<Clip> play(String resourcePath) {
        return play(resourcePath, false);
    }

    public static CompletableFuture<Clip> playLoop(String resourcePath) {
        return play(resourcePath, true);
    }

    private static CompletableFuture<Clip> play(String resourcePath, boolean loop) {
        Objects.requireNonNull(resourcePath);
        init();
        CompletableFuture<Clip> future = new CompletableFuture<>();

        executor.submit(() -> {
            try {
                SoundData sd = soundDataCache.computeIfAbsent(resourcePath, SoundManager::loadSoundDataFromResource);
                if (sd == null) { future.completeExceptionally(new IllegalStateException("Sound not found: " + resourcePath)); return; }

                ClipPool pool = pools.get(resourcePath);
                Clip clip = (pool != null) ? pool.acquire() : createClipFromSoundData(sd);
                if (clip == null) { future.completeExceptionally(new IllegalStateException("Failed to create clip: " + resourcePath)); return; }

                setClipVolumeSafe(clip, globalVolume);

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        try {
                            clip.stop();
                        } catch (IllegalStateException ignored) {}

                        ClipPool p = pools.get(resourcePath);
                        if (p != null) {
                            clip.setFramePosition(0);
                            p.release(clip);
                        } else {
                            clip.close();
                        }
                    }
                });

                if (loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.setFramePosition(0);
                    clip.start();
                }

                future.complete(clip);
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        });

        return future;
    }


    public static void setGlobalVolume(float volume) {
        float v = Math.max(0.0f, Math.min(1.0f, volume));
        globalVolume = v;

        for (ClipPool p : pools.values()) {
            p.applyVolumeToIdleClips(v);
        }
    }

    public static float getGlobalVolume() { return globalVolume; }


    public static void cleanup() {
        for (ClipPool p : pools.values()) {
            p.destroy();
        }
        pools.clear();

        soundDataCache.clear();

        executor.shutdownNow();
        initialized.set(false);
    }


    private static SoundData loadSoundDataFromResource(String resourcePath) {
        try (InputStream is = getResourceAsStream(resourcePath)) {
            if (is == null) return null;
            try (AudioInputStream ais = AudioSystem.getAudioInputStream(is)) {
                AudioFormat fmt = ais.getFormat();
                byte[] bytes = ais.readAllBytes();
                return new SoundData(fmt, bytes);
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static InputStream getResourceAsStream(String resourcePath) {
        String path = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
        return SoundManager.class.getResourceAsStream(path);
    }

    private static Clip createClipFromSoundData(SoundData sd) {
        try {
            DataLine.Info info = new DataLine.Info(Clip.class, sd.format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(sd.format, sd.bytes, 0, sd.bytes.length);
            return clip;
        } catch (LineUnavailableException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setClipVolumeSafe(Clip clip, float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float gain = min + (max - min) * volume;
            gainControl.setValue(gain);
        } catch (IllegalArgumentException ignored) {
        }
    }


    private static final class SoundData {
        final AudioFormat format;
        final byte[] bytes;

        SoundData(AudioFormat format, byte[] bytes) {
            this.format = format;
            this.bytes = bytes;
        }
    }


    private static final class ClipPool {
        private final String key;
        private final SoundData data;
        private final LinkedBlockingQueue<Clip> idleClips = new LinkedBlockingQueue<>();
        private final int size;

        ClipPool(String key, SoundData data, int size) {
            this.key = key;
            this.data = data;
            this.size = Math.max(1, size);

            for (int i = 0; i < this.size; ++i) {
                Clip c = createClipFromSoundData(data);
                if (c != null) idleClips.offer(c);
            }
        }

        Clip acquire() {
            Clip c = idleClips.poll();
            if (c != null) return c;
            return createClipFromSoundData(data);
        }

        void release(Clip clip) {
            if (clip == null) return;

            try {
                clip.setFramePosition(0);
                if (!idleClips.offer(clip)) {
                    clip.close();
                }
            } catch (IllegalStateException e) {
                try { clip.close(); } catch (Throwable ignored) {}
            }
        }

        void applyVolumeToIdleClips(float v) {
            for (Clip c : idleClips) {
                setClipVolumeSafe(c, v);
            }
        }

        void destroy() {
            for (Clip c : idleClips) {
                try { c.stop(); } catch (Throwable ignored) {}
                try { c.close(); } catch (Throwable ignored) {}
            }
            idleClips.clear();
        }
    }
}
