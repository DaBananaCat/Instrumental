package banana.instrumental.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;


import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class InstrumentTemplate extends Item {
	
	float[] keys = new float[25];
	String[] values = {
			"F♯", "G", "A♭", "A", "B♭", "B",
			"C", "D♭", "D", "E♭", "E", "F",
			"F♯", "G", "A♭", "A", "B♭", "B",
			"C", "D♭", "D", "E♭", "E", "F", "F♯"};
	
	String[] hex = {
			"#77D700", "#95C000", "#B2A500", "#CC8600",
			"#E26500", "#F34100", "#FC1E00", "#FE000F",
			"#F70033", "#E8005A", "#CF0083", "#AE00A9",
			"#8600CC", "#8600CC", "#5B00E7", "#2D00F9",
			"#020AFE", "#0037F6", "#0068E0", "#009ABC",
			"#00C68D", "#00E958", "#00FC21", "#1FFC00",
			"#59E800", "#94C100"};
	public final Holder.Reference<SoundEvent> instrument;

	// Add the item key as a parm to the constructor
	// On all your classes that extend this, pass in the itemKey to the constructor
	public InstrumentTemplate(Holder.Reference<SoundEvent> instrument, Identifier itemKey) {
		super(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, itemKey)));
		this.instrument = instrument;
		for (int i = -12; i <= 12; i++) {
			keys[i + 12] = (float) Math.pow(2, (double) (i) / 12);
		}
	}
	
	
	public ItemStack getDefaultStack() {
		return new ItemStack(this);
	}


	@Override
	public InteractionResult use(Level world, Player user, InteractionHand hand) {
		user.swing(hand, true);
		ItemStack itemStack = user.getItemInHand(hand);
		if (!world.isClientSide()) {
			float pitch = user.getXRot();
			float pitch_mod = (float) Math.pow(2, (double) (Math.round(12 * (-pitch + 90) / 90) - 12) / 12);
			
			world.playSound(null, user.getX(), user.getY(), user.getZ(), this.instrument, SoundSource.PLAYERS, 1.0F, pitch_mod);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.FAIL;
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
	public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
		if (!(entity instanceof Player player)) return;
		if (stack == entity.getWeaponItem()) {
			float sound = (float) Math.pow(2, (double) (Math.round(12 * (-entity.getXRot() + 90) / 90) - 12) / 12);
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
				Component part2 = Component.literal(" | ").setStyle(Style.EMPTY.withColor(16777215));
				Component part4 = Component.literal(" | ").setStyle(Style.EMPTY.withColor(16777215));

				Component part1 = Component.literal("").setStyle(Style.EMPTY.withColor(16777215));
				Component part3 = Component.literal("").setStyle(Style.EMPTY.withColor(16777215));
				Component part5 = Component.literal("").setStyle(Style.EMPTY.withColor(16777215));

				if (Math.abs(keys[i] - sound) < tolerance) {
					if (i == 24) {

						int colorIntPr = darkenColor(Integer.parseInt(hex[i - 1].substring(1), 16), 0.5f);
						part1 = Component.literal(values[i - 1]).setStyle(Style.EMPTY.withColor(colorIntPr));
						part3 = Component.literal(values[i]).setStyle(Style.EMPTY.withColor(colorInt));


					} else if (i == 0) {
						int colorIntNe = darkenColor(Integer.parseInt(hex[i + 1].substring(1), 16), 0.5f);
						part3 = Component.literal(values[i]).setStyle(Style.EMPTY.withColor(colorInt));
						part5 = Component.literal(values[i + 1]).setStyle(Style.EMPTY.withColor(colorIntNe));

					} else {
						int colorIntPr = darkenColor(Integer.parseInt(hex[i - 1].substring(1), 16), 0.5f);
						int colorIntNe = darkenColor(Integer.parseInt(hex[i + 1].substring(1), 16), 0.5f);
						part1 = Component.literal(values[i - 1]).setStyle(Style.EMPTY.withColor(colorIntPr));
						part3 = Component.literal(values[i]).setStyle(Style.EMPTY.withColor(colorInt));
						part5 = Component.literal(values[i + 1]).setStyle(Style.EMPTY.withColor(colorIntNe));
					}

					Component noteMessage = part1.copy().append(part2).append(part3).append(part4).append(part5);
					player.sendOverlayMessage(noteMessage);

					break;
				}
			}
		}
	}

}