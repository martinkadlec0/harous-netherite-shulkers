package harou.netherite_shulkers.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.ColorCollection;
import harou.netherite_shulkers.HarousNetheriteShulkers;

public class ModBlockItemIds {
	public static final BlockItemId NETHERITE_SHULKER_BOX = create("netherite_shulker_box");
	
	public static final ColorCollection<BlockItemId> DYED_NETHERITE_SHULKER_BOX = createSimpleColored("netherite_shulker_box");

	private static BlockItemId create(String name) {
		Identifier identifier = Identifier.fromNamespaceAndPath(HarousNetheriteShulkers.MOD_ID, name);
		return BlockItemId.create(identifier, identifier);
	}

	private static ColorCollection<BlockItemId> createSimpleColored(String name) {
		return ColorCollection.prefixWithColor(ColorCollection.create(name)).map(ModBlockItemIds::create);
	}
}
