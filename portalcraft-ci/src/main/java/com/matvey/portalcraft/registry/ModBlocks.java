package com.matvey.portalcraft.registry;

import com.matvey.portalcraft.PortalCraftMod;
import com.matvey.portalcraft.block.PortalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PortalCraftMod.MOD_ID);
    public static final RegistryObject<Block> BLUE_PORTAL = BLOCKS.register("blue_portal", () -> new PortalBlock(true, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(-1.0F, 3600000.0F).noCollission().noOcclusion().lightLevel(state -> 12)));
    public static final RegistryObject<Block> ORANGE_PORTAL = BLOCKS.register("orange_portal", () -> new PortalBlock(false, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(-1.0F, 3600000.0F).noCollission().noOcclusion().lightLevel(state -> 12)));
    public static final RegistryObject<Block> COMPANION_CUBE = BLOCKS.register("companion_cube", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> WHITE_PANEL = BLOCKS.register("white_panel", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(2.5F, 8.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACK_PANEL = BLOCKS.register("black_panel", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).strength(3.0F, 10.0F).sound(SoundType.STONE)));
    private ModBlocks() {}
}
