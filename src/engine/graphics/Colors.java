package engine.graphics;

import java.util.Random;

public final class Colors {
    private static final Random COLOR_RANDOM = new Random();

    private Colors(){}
    public static final float[] WHITE = {1.0f, 1.0f, 1.0f};
    public static final float[] BLACK = {0,0,0};
    public static final float[] YELLOW = {1.0f, 204f / 255f, 0};
    public static final float[] BLUE = {51f / 255, 51f / 255, 153f / 255};
    public static final float[] RED = {204f / 255, 51f / 255, 51f / 255};
    public static final float[] LIGHT_BLUE = {128f / 255f, 204f / 255f, 1.0f};
    public static final float[] ORANGE_BROWN = {153f / 255f, 76f / 255f, 51f / 255f};
    public static final float[] LIGHT_TAN = {204f / 255, 153f / 255, 102 / 255f};
    public static final float[] DARK_BLUE_GREY = {77f / 255, 102f / 255, 128f / 255};
    public static final float[] DARK_BROWN = {102f / 255, 51f / 255, 0f / 255};
    public static final float[] LIGHT_GREEN = {153f / 255, 204f / 255, 102f / 255};
    public static final float[] DARK_GREEN = {102f / 255, 153f / 255, 51f / 255};
    public static final float[] TAN_GREY = {179f / 255, 179f / 255, 153f / 255};
    public static final float[] MEDIUM_GREEN = {102f / 255, 179f / 255, 102f / 255};
    public static final float[] BRIGHT_GREEN = {51f / 255, 153f / 255, 51f / 255};
    public static final float[] PINE_GREEN = {0f, 102f / 255, 51f / 255};
    public static final float[] TREE_BROWN = {102f / 255, 51f / 255, 0f};
    public static final float[] FENCE_BROWN = {153f / 255, 102f / 255, 51f / 255};
    public static final float[] SKIN_TONE = {255f / 255, 204f / 255, 153f / 255};
    public static final float[] SKY_BLUE = {135f / 255f, 206f / 255f, 235f / 255f};
    public static final float[] DEEP_BLUE = {0f, 0f, 128f / 255f};
    public static final float[] CLOUD_GREY = {220f / 255f, 220f / 255f, 220f / 255f};
    public static final float[] MOUNTAIN_GREY = {96f / 255f, 96f / 255f, 96f / 255f};
    public static final float[] MOUNTAIN_DARK_GREY = {64f / 255f, 64f / 255f, 64f / 255f};
    public static final float[] MOUNTAIN_MEDIUM_GREY = {128f / 255f, 128f / 255f, 128f / 255f};
    public static final float[] MOUNTAIN_LIGHT_GREY = {192f / 255f, 192f / 255f, 192f / 255f};
    public static final float[] GRAY = {128f / 255f, 128f / 255f, 128f / 255f};
    public static final float[] LIGHT_GRAY = {192f / 255f, 192f / 255f, 192f / 255f};
    public static final float[] DARK_GRAY = {64f / 255f, 64f / 255f, 64f / 255f};
    public static final float[] SKIN_TONE_LIGHT = {255f / 255f, 229f / 255f, 204f / 255f};
    public static final float[] SKIN_TONE_MEDIUM = {210f / 255f, 161f / 255f, 119f / 255f};
    public static final float[] SKIN_TONE_DARK = {140f / 255f, 98f / 255f, 57f / 255f};
    public static final float[] SKIN_TONE_DARKER = {100f / 255f, 70f / 255f, 40f / 255f};
    public static final float[] BROWN = {139f / 255f, 69f / 255f, 19f / 255f};
    public static final float[] LIGHT_BROWN = {181f / 255f, 101f / 255f, 29f / 255f};
    public static final float[] CHOCOLATE_BROWN = {210f / 255f, 105f / 255f, 30f / 255f};
    public static final float[] SAND = {194f / 255f, 178f / 255f, 128f / 255f};
    public static final float[] FOREST_GREEN = {34f / 255f, 139f / 255f, 34f / 255f};
    public static final float[] OLIVE_GREEN = {107f / 255f, 142f / 255f, 35f / 255f};
    public static final float[] GRASS_GREEN = {124f / 255f, 252f / 255f, 0f};
    public static final float[] SPRING_GREEN = {0f, 255f / 255f, 127f / 255f};
    public static final float[] SEA_GREEN = {46f / 255f, 139f / 255f, 87f / 255f};
    public static final float[] ROYAL_BLUE = {65f / 255f, 105f / 255f, 225f / 255f};
    public static final float[] STEEL_BLUE = {70f / 255f, 130f / 255f, 180f / 255f};
    public static final float[] CORNFLOWER_BLUE = {100f / 255f, 149f / 255f, 237f / 255f};
    public static final float[] MIDNIGHT_BLUE = {25f / 255f, 25f / 255f, 112f / 255f};
    public static final float[] NAVY_BLUE = {0f, 0f, 128f / 255f};
    public static final float[] DARK_RED = {139f / 255f, 0f, 0f};
    public static final float[] CRIMSON = {220f / 255f, 20f / 255f, 60f / 255f};
    public static final float[] PINK = {255f / 255f, 192f / 255f, 203f / 255f};
    public static final float[] HOT_PINK = {255f / 255f, 105f / 255f, 180f / 255f};
    public static final float[] PURPLE = {128f / 255f, 0f, 128f / 255f};
    public static final float[] INDIGO = {75f / 255f, 0f, 130f / 255f};
    public static final float[] VIOLET = {238f / 255f, 130f / 255f, 238f / 255f};
    public static final float[] LAVENDER = {230f / 255f, 230f / 255f, 250f / 255f};
    public static final float[] ORANGE = {255f / 255f, 165f / 255f, 0f};
    public static final float[] DARK_ORANGE = {255f / 255f, 140f / 255f, 0f};
    public static final float[] CORAL = {1.0f, 127f / 255f, 80f / 255f};
    public static final float[] GOLD = {1.0f, 215f / 255f, 0f};
    public static final float[] LIGHT_YELLOW = {1.0f, 1.0f, 224f / 255f};
    public static final float[] KHAKI = {240f / 255f, 230f / 255f, 140f / 255f};
    public static final float[] TERRA_COTTA = {226f / 255f, 114f / 255f, 91f / 255f};
    public static final float[] CLAY = {210f / 255f, 180f / 255f, 140f / 255f};
    public static final float[] STONE = {160f / 255f, 160f / 255f, 160f / 255f};
    public static final float[] OCEAN_BLUE = {0f, 105f / 255f, 148f / 255f};
    public static final float[] RIVER_BLUE = {65f / 255f, 105f / 255f, 225f / 255f};
    public static final float[] TURQUOISE = {64f / 255f, 224f / 255f, 208f / 255f};
    public static final float[] TEAL = {0f, 128f / 255f, 128f / 255f};
    public static final float[] BRICK_RED = {178f / 255f, 34f / 255f, 34f / 255f};
    public static final float[] ROOF_RED = {164f / 255f, 42f / 255f, 42f / 255f};
    public static final float[] WHEAT = {245f / 255f, 222f / 255f, 179f / 255f};
    public static final float[] STRAW = {228f / 255f, 217f / 255f, 111f / 255f};
    public static final float[] GLASS = {200f / 255f, 200f / 255f, 1.0f, 0.5f};
    public static final float[] WATER = {64f / 255f, 164f / 255f, 223f / 255f, 0.7f};

    public static float[] randomColor() {

        return new float[]{
                COLOR_RANDOM.nextFloat(),
                COLOR_RANDOM.nextFloat(),
                COLOR_RANDOM.nextFloat()
        };
    }

    public static float[] lightenColor(float[] color ) {
        return new float[]{
                Math.min(color[0] + 0.2f, 1.0f),
                Math.min(color[1] + 0.2f, 1.0f),
                Math.min(color[2] + 0.2f, 1.0f)
        };
    }

}