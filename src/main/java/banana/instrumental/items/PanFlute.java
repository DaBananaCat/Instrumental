package banana.instrumental.items;

import banana.instrumental.Instrumental;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.Identifier;

public class PanFlute extends InstrumentTemplate {

    public PanFlute() {
        super(SoundEvents.NOTE_BLOCK_FLUTE,"horn", Identifier.fromNamespaceAndPath(Instrumental.MOD_ID, "pan_flute"));
    }
}
