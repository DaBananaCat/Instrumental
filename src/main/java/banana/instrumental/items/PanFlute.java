package banana.instrumental.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class PanFlute extends Item {

    float[] keys = new float[25];
    String[] values = {"F♯","G","A♭","A","B♭","B","C","D♭","D","E♭","E","F","F♯","G","A♭","A","B♭","B","C","D♭","D","E♭","E","F","F♯"};
    String[] hex = {"#77D700","#95C000","#B2A500","#CC8600","#E26500","#F34100","#FC1E00","#FE000F","#F70033","#E8005A","#CF0083","#AE00A9",
            "#8600CC","#8600CC","#5B00E7","#2D00F9","#020AFE","#0037F6","#0068E0","#009ABC","#00C68D","#00E958","#00FC21","#1FFC00","#59E800","#94C100"};

    public PanFlute() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));

        for (int i = -12; i <= 12; i++) {
            keys[i + 12] = (float) Math.pow(2, (double) (i) / 12);
        }
    }

    @Override
    public ItemStack getDefaultStack() {
        return new ItemStack(this);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            user.setCurrentHand(hand);
            float pitch = user.getPitch();
            float pitch_mod = (float) Math.pow(2, (double) (Math.round(12 * (-pitch + 90) / 90) - 12) /12);

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, SoundCategory.PLAYERS, 1.0F, pitch_mod);
            world.addParticle(ParticleTypes.NOTE, user.getX(), user.getY() + 1.0, user.getZ(), pitch_mod, 0.0, 0.0);
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    public static int darkenColor(int color, float factor) {
        if (factor <= 0 || factor > 1) {
            return color;
        }

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        red = (int) (red * factor);
        green = (int) (green * factor);
        blue = (int) (blue * factor);

        return (red << 16) | (green << 8) | blue;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && selected && entity instanceof PlayerEntity player) {
            float sound = (float) Math.pow(2, (double) (Math.round(12 * (-entity.getPitch() + 90) / 90) - 12) /12);
            if (sound < 0) {
                sound = -sound;
            }
            float tolerance = 0.001f; // Adjust this value as needed
            for (int i = 0; i < keys.length; i++) {
                int colorInt = Integer.parseInt(hex[i].substring(1), 16);

                // part 1: Prev note
                // part 2: |
                // part 3: Current note
                // part 4: |
                // part 5: Next Note
                Text part2 = Text.literal(" | ").setStyle(Style.EMPTY.withColor(16777215));
                Text part4 = Text.literal(" | ").setStyle(Style.EMPTY.withColor(16777215));

                Text part1 = Text.literal("").setStyle(Style.EMPTY.withColor(16777215));
                Text part3 = Text.literal("").setStyle(Style.EMPTY.withColor(16777215));
                Text part5 = Text.literal("").setStyle(Style.EMPTY.withColor(16777215));

                if (Math.abs(keys[i] - sound) < tolerance) {
                    if (i == 24) {

                        int colorIntPr = darkenColor(Integer.parseInt(hex[i-1].substring(1), 16),0.5f);
                        part1 = Text.literal(values[i-1]).setStyle(Style.EMPTY.withColor(colorIntPr));
                        part3 = Text.literal(values[i]).setStyle(Style.EMPTY.withColor(colorInt));


                    } else if (i == 0) {
                        int colorIntNe = darkenColor(Integer.parseInt(hex[i+1].substring(1), 16),0.5f);
                        part3 = Text.literal(values[i]).setStyle(Style.EMPTY.withColor(colorInt));
                        part5 = Text.literal(values[i+1]).setStyle(Style.EMPTY.withColor(colorIntNe));

                    }
                    else {
                        int colorIntPr = darkenColor(Integer.parseInt(hex[i-1].substring(1), 16),0.5f);
                        int colorIntNe = darkenColor(Integer.parseInt(hex[i+1].substring(1), 16),0.5f);
                        part1 = Text.literal(values[i-1]).setStyle(Style.EMPTY.withColor(colorIntPr));
                        part3 = Text.literal(values[i]).setStyle(Style.EMPTY.withColor(colorInt));
                        part5 = Text.literal(values[i+1]).setStyle(Style.EMPTY.withColor(colorIntNe));
                    }

                    Text noteMessage = part1.copy().append(part2).append(part3).append(part4).append(part5);
                    player.sendMessage(noteMessage,true);

                    break;
                }
            }
        }
    }
}