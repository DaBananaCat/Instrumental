package banana.instrumental.block;

import banana.instrumental.sound.InstrumentalSounds;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class Drum extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3,0,3,13,13,13);

    public Drum() {
        super(AbstractBlock.Settings.create().nonOpaque().burnable().sounds(BlockSoundGroup.WOOD));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            if (player.isSneaking() || world.getBlockState(new BlockPos(pos.getX(),pos.getY()-1,pos.getZ())).getPistonBehavior() == PistonBehavior.PUSH_ONLY) {
                world.playSound(null, pos, InstrumentalSounds.DRUM, SoundCategory.BLOCKS, 1.0f, 2.0f);
            }
            else {
                world.playSound(null, pos, InstrumentalSounds.DRUM, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
        return ActionResult.SUCCESS;
    }
}
