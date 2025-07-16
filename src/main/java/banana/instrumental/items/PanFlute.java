package banana.instrumental.items;

import banana.instrumental.Instrumental;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class PanFlute extends InstrumentTemplate {

    public PanFlute() {
        super(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE,"horn", Identifier.of(Instrumental.MOD_ID, "pan_flute"));
    }
}
