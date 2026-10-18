package com.matvey.portalcraft.portal;

import com.matvey.portalcraft.block.PortalBlock;
import com.matvey.portalcraft.registry.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PortalManager {
    private static final Map<UUID, PortalPair> PORTALS = new HashMap<>();
    private static final String LAST_TP = "portalcraft_last_tp";
    public static void placePortal(ServerLevel level, Player player, BlockHitResult hit, boolean blue) {
        Direction outward = hit.getDirection();
        BlockPos target = hit.getBlockPos().relative(outward);
        BlockState targetState = level.getBlockState(target);
        if (!targetState.canBeReplaced() && !isPortal(targetState.getBlock())) {
            player.displayClientMessage(Component.translatable("message.portalcraft.no_space").withStyle(ChatFormatting.RED), true);
            return;
        }
        PortalPair oldPair = PORTALS.getOrDefault(player.getUUID(), new PortalPair(null, null));
        PortalEndpoint old = blue ? oldPair.blue() : oldPair.orange();
        if (old != null && level.isLoaded(old.pos())) {
            BlockState oldState = level.getBlockState(old.pos());
            if ((blue && oldState.is(ModBlocks.BLUE_PORTAL.get())) || (!blue && oldState.is(ModBlocks.ORANGE_PORTAL.get()))) level.removeBlock(old.pos(), false);
        }
        BlockState portalState = (blue ? ModBlocks.BLUE_PORTAL.get() : ModBlocks.ORANGE_PORTAL.get()).defaultBlockState().setValue(PortalBlock.FACING, outward);
        level.setBlock(target, portalState, 3);
        PortalEndpoint endpoint = new PortalEndpoint(target.immutable(), outward);
        PortalPair updated = blue ? new PortalPair(endpoint, oldPair.orange()) : new PortalPair(oldPair.blue(), endpoint);
        PORTALS.put(player.getUUID(), updated);
        player.displayClientMessage(Component.translatable(blue ? "message.portalcraft.blue_portal" : "message.portalcraft.orange_portal").withStyle(blue ? ChatFormatting.AQUA : ChatFormatting.GOLD), true);
    }
    public static void tryTeleport(ServerPlayer player, BlockPos enteredPos, boolean enteredBlue) {
        ServerLevel level = player.serverLevel();
        long now = level.getGameTime();
        long last = player.getPersistentData().getLong(LAST_TP);
        if (now - last < 12) return;
        PortalPair pair = PORTALS.get(player.getUUID());
        if (pair == null) return;
        PortalEndpoint entered = enteredBlue ? pair.blue() : pair.orange();
        PortalEndpoint exit = enteredBlue ? pair.orange() : pair.blue();
        if (entered == null || exit == null || !entered.pos().equals(enteredPos)) return;
        if (!level.isLoaded(exit.pos())) return;
        BlockState exitState = level.getBlockState(exit.pos());
        if (enteredBlue && !exitState.is(ModBlocks.ORANGE_PORTAL.get())) return;
        if (!enteredBlue && !exitState.is(ModBlocks.BLUE_PORTAL.get())) return;
        player.getPersistentData().putLong(LAST_TP, now);
        Vec3 velocity = player.getDeltaMovement();
        Direction d = exit.outward();
        double x = exit.pos().getX() + 0.5D + d.getStepX() * 1.15D;
        double z = exit.pos().getZ() + 0.5D + d.getStepZ() * 1.15D;
        double y = exit.pos().getY() + 0.05D;
        if (d == Direction.UP) y = exit.pos().getY() + 1.05D;
        if (d == Direction.DOWN) y = exit.pos().getY() - 1.05D;
        player.teleportTo(x, y, z);
        player.setDeltaMovement(velocity);
        player.hurtMarked = true;
        level.playSound(null, exit.pos(), net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT, net.minecraft.sounds.SoundSource.PLAYERS, 0.5F, enteredBlue ? 1.4F : 0.9F);
    }
    private static boolean isPortal(Block block) { return block == ModBlocks.BLUE_PORTAL.get() || block == ModBlocks.ORANGE_PORTAL.get(); }
    public record PortalEndpoint(BlockPos pos, Direction outward) {}
    public record PortalPair(PortalEndpoint blue, PortalEndpoint orange) {}
    private PortalManager() {}
}
