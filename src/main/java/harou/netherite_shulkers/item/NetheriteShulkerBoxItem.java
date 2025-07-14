package harou.netherite_shulkers.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NetheriteShulkerBoxItem extends BlockItem {
    public NetheriteShulkerBoxItem(Block block) {
        super(block, new Item.Settings().fireproof());
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true; // Netherite items have enchantment glint
    }
} 