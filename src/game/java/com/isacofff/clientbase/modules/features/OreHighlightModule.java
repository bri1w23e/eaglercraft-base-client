public class OreHighlightModule extends Module {

    public OreHighlightModule() {
        super("OreHighlight", Category.RENDER);
    }

    @Override
    protected void onEnable() {
        System.out.println("Ore highlighting enabled");
    }

    @Override
    protected void onDisable() {
        System.out.println("Ore highlighting disabled");
    }

    @Override
    public void onUpdate() {
        // Nothing here — rendering happens in renderWorld()
    }

    public void renderWorld(World world) {
        if (!isEnabled()) return;

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Block block = world.getBlock(x, y);

                if (isOre(block)) {
                    System.out.println("Highlighting ORE at (" + x + ", " + y + "): " + block.getType());
                }
            }
        }
    }

    private boolean isOre(Block block) {
        return switch (block.getType()) {
            case COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE, EMERALD_ORE -> true;
            default -> false;
        };
    }
}
