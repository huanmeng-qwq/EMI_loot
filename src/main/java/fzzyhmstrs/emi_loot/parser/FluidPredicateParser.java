package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.fluid.Fluid;
import net.minecraft.predicate.FluidPredicate;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;

public class FluidPredicateParser {

    public static Text parseFluidPredicate(FluidPredicate predicate) {
        return LText.translatable("emi_loot.fluid_predicate.base", parseFluidPredicateInternal(predicate).getString());
    }

    private static Text parseFluidPredicateInternal(FluidPredicate predicate) {

        TagKey<Fluid> tag = predicate.tag().orElse(null);
        if (tag != null) {
            return LText.translatable("emi_loot.fluid_predicate.tag", tag.id().toString());
        }

        Fluid fluid = predicate.fluid().orElse(null) != null ? predicate.fluid().orElse(null).value() : null;
        if (fluid != null) {
            return LText.translatable("emi_loot.fluid_predicate.fluid", Registries.FLUID.getId(fluid).toString());
        }

        StatePredicate statePredicate = predicate.state().orElse(null);
        if (statePredicate != null) {
            return StatePredicateParser.parseStatePredicate(statePredicate);
        }

        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable fluid predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }

}
