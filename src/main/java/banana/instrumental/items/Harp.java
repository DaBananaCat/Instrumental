package banana.instrumental.items;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class Harp extends InstrumentTemplate{
    public Harp() {
        super(SoundEvents.BLOCK_NOTE_BLOCK_HARP,"bow");
    }
}
