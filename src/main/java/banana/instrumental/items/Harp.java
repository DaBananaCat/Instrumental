package banana.instrumental.items;

import banana.instrumental.Instrumental;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.Identifier;


public class Harp extends InstrumentTemplate{
    public Harp() {
        super(SoundEvents.NOTE_BLOCK_HARP, Identifier.fromNamespaceAndPath(Instrumental.MOD_ID, "harp"));
    }
}
