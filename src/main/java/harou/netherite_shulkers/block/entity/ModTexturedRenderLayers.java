package harou.netherite_shulkers.block.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SpriteMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers {
  public static SpriteId NETHERITE_SHULKER_TEXTURE_ID = null;
  public static Map<DyeColor, SpriteId> COLORED_NETHERITE_SHULKER_BOXES_TEXTURES = new LinkedHashMap<>();
  public static SpriteMapper NETHERITE_SHULKER_SPRITE_MAPPER = null;

  public static SpriteMapper registerNetheriteShulkerSpriteMapper(String namespace) {
    SpriteMapper mapper = NETHERITE_SHULKER_SPRITE_MAPPER = new SpriteMapper(
        Sheets.SHULKER_SHEET,
        "entity/shulker/netherite"
    );
    return NETHERITE_SHULKER_SPRITE_MAPPER = mapper;
  }

  public static SpriteId registerMaterialDefaultSprite(String namespace) {
    var spriteId = NETHERITE_SHULKER_SPRITE_MAPPER.apply(
      Identifier.fromNamespaceAndPath(namespace, "shulker")
    );
    return NETHERITE_SHULKER_TEXTURE_ID = spriteId;
  }

  public static Map<DyeColor, SpriteId> registerMaterialColoringSprites(String namespace) {
    Stream.of(DyeColor.values()).forEach((color) -> {
      COLORED_NETHERITE_SHULKER_BOXES_TEXTURES.put(color, NETHERITE_SHULKER_SPRITE_MAPPER.apply(
        Identifier.fromNamespaceAndPath(
          namespace,
          String.format("shulker_%s", color.getName())
        )
      )); 
    });
    return COLORED_NETHERITE_SHULKER_BOXES_TEXTURES;    
  }
}
