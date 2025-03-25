package banana.instrumental.sound;

import banana.instrumental.Instrumental;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class InstrumentalSounds {
    public static final SoundEvent DRUM = registerSoundEvent("drum");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(Instrumental.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Instrumental.LOGGER.info("Registering Sounds");
    }
}
