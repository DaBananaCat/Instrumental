package banana.instrumental;

import banana.instrumental.block.InstrumentalBlocks;
import banana.instrumental.items.Guitar;
import banana.instrumental.items.Harp;
import banana.instrumental.items.PanFlute;
import banana.instrumental.sound.InstrumentalSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Instrumental implements ModInitializer {
	public static final String MOD_ID = "instrumental";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item PAN_FLUTE = new PanFlute();
	public static final Item GUITAR = new Guitar();
	public static final Item HARP = new Harp();

	public static final Identifier DRUM = Identifier.of("drum");
	public static SoundEvent DRUM_SOUND_EVENT = SoundEvent.of(DRUM);


	@Override
	public void onInitialize() {

		InstrumentalSounds.registerSounds();

		InstrumentalBlocks.registerModBlocks();

		Registry.register(Registries.ITEM, Identifier.of(Instrumental.MOD_ID, "pan_flute"), PAN_FLUTE);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.addAfter(Items.GOAT_HORN,PAN_FLUTE);
		});

		Registry.register(Registries.ITEM, Identifier.of(Instrumental.MOD_ID, "harp"), HARP);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.addAfter(PAN_FLUTE,HARP);
		});

		Registry.register(Registries.ITEM, Identifier.of(Instrumental.MOD_ID, "guitar"), GUITAR);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.addAfter(HARP,GUITAR);
		});

	}
}