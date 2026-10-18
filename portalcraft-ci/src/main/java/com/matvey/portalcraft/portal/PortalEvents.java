package com.matvey.portalcraft.portal;

import com.matvey.portalcraft.registry.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class PortalEvents {
    @SubscribeEvent public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (!player.getMainHandItem().is(ModItems.PORTAL_GUN.get())) return;
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        Direction face = event.getFace();
        if (face == null) return;
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(event.getPos()), face, event.getPos(), false);
        PortalManager.placePortal(serverLevel, player, hit, true);
        player.getCooldowns().addCooldown(ModItems.PORTAL_GUN.get(), 4);
    }
    private PortalEvents() {}
}
