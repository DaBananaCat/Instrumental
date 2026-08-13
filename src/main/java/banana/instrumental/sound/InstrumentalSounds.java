package banana.instrumental.sound;

import banana.instrumental.Instrumental;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;

public class InstrumentalSounds {
    public static final SoundEvent DRUM = registerSoundEvent("drum");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(Instrumental.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerSounds() {
        Instrumental.LOGGER.info("Registering Sounds");
    }
}
