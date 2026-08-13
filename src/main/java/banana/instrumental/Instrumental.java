package banana.instrumental;

import banana.instrumental.block.InstrumentalBlocks;
import banana.instrumental.items.Guitar;
import banana.instrumental.items.Harp;
import banana.instrumental.items.PanFlute;
import banana.instrumental.sound.InstrumentalSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Instrumental implements ModInitializer {
	
	public static final String MOD_ID = "instrumental";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	public static Item PAN_FLUTE;
	public static Item GUITAR;
	public static Item HARP;
	
	public static final Identifier DRUM = Identifier.fromNamespaceAndPath(MOD_ID, "drum");
	public static SoundEvent DRUM_SOUND_EVENT = SoundEvent.createVariableRangeEvent(DRUM);
	
	@Override
	public void onInitialize() {
		
		InstrumentalSounds.registerSounds();
		InstrumentalBlocks.registerModBlocks();
		
		PAN_FLUTE = register("pan_flute", new PanFlute());
		HARP = register("harp", new Harp());
		GUITAR = register("guitar", new Guitar());
		
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
			content.insertAfter(Items.GOAT_HORN, PAN_FLUTE);
			content.insertAfter(PAN_FLUTE, HARP);
			content.insertAfter(HARP, GUITAR);
		});
		
	}
	
	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(Instrumental.MOD_ID, name), item);
	}
}
