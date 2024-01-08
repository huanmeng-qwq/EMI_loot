package fzzyhmstrs.emi_loot.mixins;

import fzzyhmstrs.emi_loot.imixin.ISetNameLootFunction;
import net.minecraft.loot.function.SetNameLootFunction;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(SetNameLootFunction.class)
public class SetNameLootFunctionAccessor implements ISetNameLootFunction {

    @Shadow
    @Final
    private Optional<Text> name;

    public Optional<Text> eMI_loot$name() {
        return name;
    }
}
