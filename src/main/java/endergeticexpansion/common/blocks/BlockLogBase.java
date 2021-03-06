package endergeticexpansion.common.blocks;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockLogBase extends RotatedPillarBlock {
	@Nullable
	private Supplier<Block> strippedState;
	
	public BlockLogBase(Properties properties, @Nullable Supplier<Block> strippedState) {
		super(properties);
		this.strippedState = strippedState;
	}
	
	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return 60;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
		if(state.getBlock() == this && player.getHeldItemMainhand().getItem() instanceof AxeItem && this.strippedState != null) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 2.0F, 1.0F);
			world.setBlockState(pos, this.strippedState.get().getDefaultState().with(AXIS, state.get(AXIS)));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}