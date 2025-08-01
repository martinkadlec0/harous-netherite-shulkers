package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.DyeColor;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected net.minecraft.data.recipe.RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new net.minecraft.data.recipe.RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                HarousNetheriteShulkers.LOGGER.info("Generating Netherite Shulker Box recipes...");
                
                // Generate base netherite shulker box recipe (from regular shulker box)
                offerNetheriteUpgradeRecipe(Items.SHULKER_BOX, RecipeCategory.MISC, ModItems.NETHERITE_SHULKER_BOX);

                // Generate colored netherite shulker box recipes
                for (DyeColor color : DyeColor.values()) {
                    offerNetheriteUpgradeRecipe(getVanillaShulkerBox(color), RecipeCategory.MISC, NetheriteShulkerBoxItem.get(color));
                }
                
                // Generate color conversion recipes for netherite shulker boxes
                // One recipe per color that accepts any netherite shulker box + dye
                for (DyeColor targetColor : DyeColor.values()) {
                    Item targetShulker = NetheriteShulkerBoxItem.get(targetColor);
                    DyeItem dye = DyeItem.byColor(targetColor);
                    
                    createShapeless(RecipeCategory.MISC, targetShulker)
                        .input(ingredientFromTag(ItemTagGenerator.NETHERITE_SHULKER_BOXES)) // Any netherite shulker box
                        .input(dye)
                        .criterion("has_netherite_shulker_box", conditionsFromItem(ModItems.NETHERITE_SHULKER_BOX))
                        .offerTo(exporter);
                }
                
                HarousNetheriteShulkers.LOGGER.info("Netherite Shulker Box recipes generated successfully!");
            }
        };
    }

    private static Item getVanillaShulkerBox(DyeColor color) {
        return switch (color) {
            case WHITE -> Items.WHITE_SHULKER_BOX;
            case ORANGE -> Items.ORANGE_SHULKER_BOX;
            case MAGENTA -> Items.MAGENTA_SHULKER_BOX;
            case LIGHT_BLUE -> Items.LIGHT_BLUE_SHULKER_BOX;
            case YELLOW -> Items.YELLOW_SHULKER_BOX;
            case LIME -> Items.LIME_SHULKER_BOX;
            case PINK -> Items.PINK_SHULKER_BOX;
            case GRAY -> Items.GRAY_SHULKER_BOX;
            case LIGHT_GRAY -> Items.LIGHT_GRAY_SHULKER_BOX;
            case CYAN -> Items.CYAN_SHULKER_BOX;
            case PURPLE -> Items.PURPLE_SHULKER_BOX;
            case BLUE -> Items.BLUE_SHULKER_BOX;
            case BROWN -> Items.BROWN_SHULKER_BOX;
            case GREEN -> Items.GREEN_SHULKER_BOX;
            case RED -> Items.RED_SHULKER_BOX;
            case BLACK -> Items.BLACK_SHULKER_BOX;
        };
    }
    
    @Override
    public String getName() {
        return "Netherite Shulker Box Recipes";
    }
} 
