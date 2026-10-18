package com.matvey.portalcraft.block;

import com.matvey.portalcraft.portal.PortalManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PortalBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private final boolean blue;
    private static final VoxelShape NORTH = box(0, 0, 14, 16, 16, 16);
    private static final VoxelShape SOUTH = box(0, 0, 0, 16, 16, 2);
    private static final VoxelShape EAST  = box(0, 0, 0, 2, 16, 16);
    private static final VoxelShape WEST  = box(14, 0, 0, 16, 16, 16);
    private static final VoxelShape UP    = box(0, 0, 0, 16, 2, 16);
    private static final VoxelShape DOWN  = box(0, 14, 0, 16, 16, 16);

    public PortalBlock(boolean blue, Properties properties) {
        super(properties);
        this.blue = blue;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) { builder.add(FACING); }
    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH; case SOUTH -> SOUTH; case EAST -> EAST; case WEST -> WEST; case UP -> UP; case DOWN -> DOWN;
        };
    }
    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }
    @Override public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) PortalManager.tryTeleport(player, pos, blue);
        super.entityInside(state, level, pos, entity);
    }
}
