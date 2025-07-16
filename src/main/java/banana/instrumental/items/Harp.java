package banana.instrumental.items;

import banana.instrumental.Instrumental;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class Harp extends InstrumentTemplate{
    public Harp() {
        super(SoundEvents.BLOCK_NOTE_BLOCK_HARP,"bow", Identifier.of(Instrumental.MOD_ID, "harp"));
    }
}
