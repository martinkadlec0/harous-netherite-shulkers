package harou.netherite_shulkers.data;

import harou.netherite_shulkers.HarousNetheriteShulkers;
import harou.netherite_shulkers.item.ModItems;
import harou.netherite_shulkers.item.NetheriteShulkerBoxItem;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.TransmuteRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
	public RecipeGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
		return new net.minecraft.data.recipes.RecipeProvider(registryLookup, exporter) {
			@Override
			public void buildRecipes() {
				HarousNetheriteShulkers.LOGGER.info("Generating Netherite Shulker Box recipes...");
				
				// Generate base netherite shulker box recipe (from regular shulker box)
				netheriteSmithing(Items.SHULKER_BOX, RecipeCategory.MISC, ModItems.NETHERITE_SHULKER_BOX);

				// Generate colored netherite shulker box recipes
				for (DyeColor color : DyeColor.values()) {
					netheriteSmithing(Items.DYED_SHULKER_BOX.pick(color), RecipeCategory.MISC, NetheriteShulkerBoxItem.get(color));
				}
				
				// Generate color conversion recipes for netherite shulker boxes using crafting_transmute
				Ingredient ingredient = tag(ItemTagGenerator.NETHERITE_SHULKER_BOXES);

				for (DyeColor dyeColor : DyeColor.values()) {
					Item targetShulker = NetheriteShulkerBoxItem.get(dyeColor);
					TransmuteRecipeBuilder.transmute(
						RecipeCategory.DECORATIONS,
						ingredient,
						Ingredient.of(Items.DYE.pick(dyeColor)),
						targetShulker.asItem()
					)
					.group("netherite_shulker_box_dye")
					.unlockedBy("has_netherite_shulker_box", has(ItemTagGenerator.NETHERITE_SHULKER_BOXES))
					.save(output);
				}
				
				HarousNetheriteShulkers.LOGGER.info("Netherite Shulker Box recipes generated successfully!");
			}
		};
	}
	
	@Override
	public String getName() {
		return "Netherite Shulker Box Recipes";
	}
} 
