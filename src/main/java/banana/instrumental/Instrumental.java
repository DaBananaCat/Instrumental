package banana.instrumental;

import banana.instrumental.block.Drum;
import banana.instrumental.block.InstrumentalBlocks;
import banana.instrumental.items.Guitar;
import banana.instrumental.items.Harp;
import banana.instrumental.items.InstrumentTemplate;
import banana.instrumental.items.PanFlute;
import banana.instrumental.sound.InstrumentalSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.WebSocket;

public class Instrumental implements ModInitializer {
	public static final String MOD_ID = "instrumental";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item PAN_FLUTE = new PanFlute();
	public static final Item GUITAR = new Guitar();
	public static final Item HARP = new Harp();

	public static final Identifier DRUM = Identifier.of("drum");
	public static SoundEvent DRUM_SOUND_EVENT = SoundEvent.of(DRUM);


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");


		InstrumentalSounds.registerSounds();

		InstrumentalBlocks.registerModBlocks();

		Registry.register(Registries.ITEM, Identifier.of(Instrumental.MOD_ID, "pan_flute"), PAN_FLUTE);
		Registry.register(Registries.ITEM, Identifier.of(Instrumental.MOD_ID, "guitar"), GUITAR);
		Registry.register(Registries.ITEM, Identifier.of(Instrumental.MOD_ID, "harp"), HARP);
	}
}