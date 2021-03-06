package endergeticexpansion.common.entities.booflo.ai;

import java.util.EnumSet;

import endergeticexpansion.common.entities.booflo.EntityBooflo;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class BoofloSinkGoal extends Goal {
	private EntityBooflo booflo;
	
	public BoofloSinkGoal(EntityBooflo booflo) {
		this.booflo = booflo;
		this.setMutexFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean shouldExecute() {
		return (this.booflo.hasCaughtFruit() || this.booflo.hasCaughtPuffBug()) && this.booflo.isBoofed() && !this.booflo.onGround && this.booflo.getRNG().nextInt(70) == 0 && this.isSafePos();
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(!this.isSafePos()) {
			return false;
		}
		return !this.booflo.onGround && this.booflo.isBoofed() && (this.booflo.hasCaughtFruit() || this.booflo.hasCaughtPuffBug());
	}
	
	@Override
	public void tick() {
		this.booflo.getNavigator().clearPath();
	}
	
	private boolean isSafePos() {
		BlockPos pos = this.booflo.getPosition();
		for(int i = 0; i < 10; i++) {
			BlockPos newPos = pos.down(i);
			if(Block.hasSolidSide(this.booflo.world.getBlockState(newPos), this.booflo.world, newPos, Direction.UP)) {
				if(this.booflo.world.getBlockState(newPos).getFluidState().isEmpty() && !this.booflo.world.getBlockState(newPos).isBurning(this.booflo.world, newPos)) {
					return true;
				}
			}
		}
		return false;
	}
}