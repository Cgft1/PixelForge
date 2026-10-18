package com.matvey.portalcraft;

import com.matvey.portalcraft.portal.PortalEvents;
import com.matvey.portalcraft.registry.ModBlocks;
import com.matvey.portalcraft.registry.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PortalCraftMod.MOD_ID)
public class PortalCraftMod {
    public static final String MOD_ID = "portalcraft";

    public PortalCraftMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        modBus.addListener(this::addCreativeTabs);
        MinecraftForge.EVENT_BUS.register(PortalEvents.class);
    }

    private void addCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.PORTAL_GUN);
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModItems.COMPANION_CUBE);
            event.accept(ModItems.WHITE_PANEL);
            event.accept(ModItems.BLACK_PANEL);
        }
    }
}
