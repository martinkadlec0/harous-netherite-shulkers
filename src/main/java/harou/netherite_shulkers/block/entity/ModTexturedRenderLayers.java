package harou.netherite_shulkers.block.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.SpriteMapper;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers {
    public static SpriteIdentifier NETHERITE_SHULKER_TEXTURE_ID = null;
    public static Map<DyeColor, SpriteIdentifier> COLORED_NETHERITE_SHULKER_BOXES_TEXTURES = new LinkedHashMap<>();
    public static SpriteMapper NETHERITE_SHULKER_SPRITE_MAPPER = null;

    public static SpriteMapper registerNetheriteShulkerSpriteMapper(String namespace) {
        SpriteMapper mapper = NETHERITE_SHULKER_SPRITE_MAPPER = new SpriteMapper(
            TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE,
            "entity/shulker/netherite"
        );
        return NETHERITE_SHULKER_SPRITE_MAPPER = mapper;
    }

    public static SpriteIdentifier registerMaterialDefaultSprite(String namespace) {
        SpriteIdentifier identifier = new SpriteIdentifier(
            TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE,
            Identifier.of(namespace, "entity/shulker/netherite/shulker")
        );
        return NETHERITE_SHULKER_TEXTURE_ID = identifier;
    }

    public static Map<DyeColor, SpriteIdentifier> registerMaterialColoringSprites(String namespace) {
        Stream.of(DyeColor.values()).forEach((color) -> {
            COLORED_NETHERITE_SHULKER_BOXES_TEXTURES.put(color, new SpriteIdentifier(
                TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE,
                Identifier.of(namespace, String.format("entity/shulker/netherite/shulker_%s", color.getId()))
            )); 
        });
        return COLORED_NETHERITE_SHULKER_BOXES_TEXTURES;    
    }
}
