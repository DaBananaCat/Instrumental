package banana.instrumental.block;

import banana.instrumental.Instrumental;
import banana.instrumental.sound.InstrumentalSounds;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Drum extends TransparentBlock {
	private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 13, 13);

	protected VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	
	protected VoxelShape getCameraCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return this.getOutlineShape(state, world, pos, context);
	}

	protected InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (!world.isClientSide()) {
			if (player.isCrouching() || world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getPistonPushReaction() == PushReaction.PUSH_ONLY) {
				world.playSound(null, pos, InstrumentalSounds.DRUM, SoundSource.BLOCKS, 1.0f, 2.0f);
			} else {
				world.playSound(null, pos, InstrumentalSounds.DRUM, SoundSource.BLOCKS, 1.0f, 1.0f);
			}
		}
		return InteractionResult.SUCCESS;
	}

	public Drum(BlockBehaviour.Properties properties) {
		super(properties.noOcclusion().ignitedByLava().sound(SoundType.WOOD));
	}

}
