package com.matvey.portalcraft.registry;

import com.matvey.portalcraft.PortalCraftMod;
import com.matvey.portalcraft.item.PortalGunItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PortalCraftMod.MOD_ID);
    public static final RegistryObject<Item> PORTAL_GUN = ITEMS.register("portal_gun", () -> new PortalGunItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COMPANION_CUBE = ITEMS.register("companion_cube", () -> new BlockItem(ModBlocks.COMPANION_CUBE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_PANEL = ITEMS.register("white_panel", () -> new BlockItem(ModBlocks.WHITE_PANEL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_PANEL = ITEMS.register("black_panel", () -> new BlockItem(ModBlocks.BLACK_PANEL.get(), new Item.Properties()));
    private ModItems() {}
}
