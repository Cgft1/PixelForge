package com.matvey.portalcraft.item;

import com.matvey.portalcraft.portal.PortalManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PortalGunItem extends Item {
    public PortalGunItem(Properties properties) { super(properties); }
    @Override public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            Vec3 eye = player.getEyePosition();
            Vec3 end = eye.add(player.getViewVector(1.0F).scale(64.0D));
            BlockHitResult hit = level.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if (hit.getType() == HitResult.Type.BLOCK) PortalManager.placePortal(serverLevel, player, hit, false);
        }
        player.getCooldowns().addCooldown(this, 4);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
