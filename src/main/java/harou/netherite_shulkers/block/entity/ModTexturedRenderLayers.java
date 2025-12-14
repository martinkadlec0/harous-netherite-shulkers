package harou.netherite_shulkers.block.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers {
    public static Material NETHERITE_SHULKER_TEXTURE_ID = null;
    public static Map<DyeColor, Material> COLORED_NETHERITE_SHULKER_BOXES_TEXTURES = new LinkedHashMap<>();
    public static MaterialMapper NETHERITE_SHULKER_SPRITE_MAPPER = null;

    public static MaterialMapper registerNetheriteShulkerSpriteMapper(String namespace) {
        MaterialMapper mapper = NETHERITE_SHULKER_SPRITE_MAPPER = new MaterialMapper(
            Sheets.SHULKER_SHEET,
            "entity/shulker/netherite"
        );
        return NETHERITE_SHULKER_SPRITE_MAPPER = mapper;
    }

    public static Material registerMaterialDefaultSprite(String namespace) {
        Material identifier = new Material(
            Sheets.SHULKER_SHEET,
            Identifier.fromNamespaceAndPath(namespace, "entity/shulker/netherite/shulker")
        );
        return NETHERITE_SHULKER_TEXTURE_ID = identifier;
    }

    public static Map<DyeColor, Material> registerMaterialColoringSprites(String namespace) {
        Stream.of(DyeColor.values()).forEach((color) -> {
            COLORED_NETHERITE_SHULKER_BOXES_TEXTURES.put(color, new Material(
                Sheets.SHULKER_SHEET,
                Identifier.fromNamespaceAndPath(namespace, String.format("entity/shulker/netherite/shulker_%s", color.getName()))
            )); 
        });
        return COLORED_NETHERITE_SHULKER_BOXES_TEXTURES;    
    }
}
