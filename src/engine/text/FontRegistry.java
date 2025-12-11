package engine.text;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FontKey {
    String name;
    int size;

    public FontKey(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontKey fontKey = (FontKey) o;
        return size == fontKey.size && Objects.equals(name, fontKey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name , size);
    }
}

public class FontRegistry {
    private static FontRegistry instance;
    private final Map<FontKey, TextRenderer> rendererCache;

    private FontRegistry() {
        rendererCache = new HashMap<>();
    }

    public static FontRegistry getInstance() {
        if (instance == null) {
            instance = new FontRegistry();
        }
        return instance;
    }

    public TextRenderer getRenderer(String name, int size) {
        FontKey key = new FontKey(name, size);

        if (rendererCache.containsKey(key)) {
            return rendererCache.get(key);
        }

        TextRenderer newRenderer = new TextRenderer(new Font(name, Font.BOLD, size), true, false);
        rendererCache.put(key, newRenderer);
        return newRenderer;
    }

    public void dispose() {
        for (TextRenderer tr : rendererCache.values()) {
            tr.dispose();
        }
        rendererCache.clear();
    }
}