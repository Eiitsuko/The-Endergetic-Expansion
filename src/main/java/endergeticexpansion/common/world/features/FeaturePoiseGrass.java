package endergeticexpansion.common.world.features;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.Dynamic;

import endergeticexpansion.core.registry.EEBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

/**
 * @author - SmellyModder(Luke Tonon)
 */
@SuppressWarnings("deprecation")
public class FeaturePoiseGrass extends Feature<NoFeatureConfig> {
	private static final Supplier<BlockState> POISE_BUSH = () -> EEBlocks.POISE_GRASS.get().getDefaultState();
	
	public FeaturePoiseGrass(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
		super(config);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		for(BlockState blockstate = worldIn.getBlockState(pos); (blockstate.isAir() || blockstate.isIn(BlockTags.LEAVES)) && pos.getY() > 0; blockstate = worldIn.getBlockState(pos)) {
			pos = pos.down();
		}
		
		int i = 0;
		for(int j = 0; j < 128; ++j) {
			BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
			if (!this.isNearBolloomBud(worldIn, blockpos) && worldIn.isAirBlock(blockpos) && POISE_BUSH.get().isValidPosition(worldIn, blockpos)) {
				worldIn.setBlockState(blockpos, POISE_BUSH.get(), 2);
				++i;
			}
		}

		return i > 0;
	}
	
	protected boolean isNearBolloomBud(IWorld world, BlockPos pos) {
		if(world.getBlockState(pos.north()).getBlock() == EEBlocks.BOLLOOM_BUD.get() || world.getBlockState(pos.east()).getBlock() == EEBlocks.BOLLOOM_BUD.get() || world.getBlockState(pos.south()).getBlock() == EEBlocks.BOLLOOM_BUD.get() || world.getBlockState(pos.west()).getBlock() == EEBlocks.BOLLOOM_BUD.get()) {
			return true;
		}
		return false;
	}

}
