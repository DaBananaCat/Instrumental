package banana.instrumental.items;

import banana.instrumental.Instrumental;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class Guitar extends InstrumentTemplate{
    public Guitar() {
        super(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR,"bow", Identifier.of(Instrumental.MOD_ID, "guitar"));
    }
}
