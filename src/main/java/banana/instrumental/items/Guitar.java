package banana.instrumental.items;

import banana.instrumental.Instrumental;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.Identifier;
public class Guitar extends InstrumentTemplate{
    public Guitar() {
        super(SoundEvents.NOTE_BLOCK_GUITAR,"bow", Identifier.fromNamespaceAndPath(Instrumental.MOD_ID, "guitar"));
    }
}
