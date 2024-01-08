package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.DistancePredicate;
import net.minecraft.text.Text;

public class DistancePredicateParser {

    public static Text parseDistancePredicate(DistancePredicate predicate) {
        NumberRange.DoubleRange abs = predicate.absolute();
        if (!abs.equals(NumberRange.DoubleRange.ANY)) {
            return LText.translatable("emi_loot.entity_predicate.distance_abs", abs.min().orElse(-1d), abs.max().orElse(-1d));
        }
        NumberRange.DoubleRange hor = predicate.horizontal();
        if (!hor.equals(NumberRange.DoubleRange.ANY)) {
            return LText.translatable("emi_loot.entity_predicate.distance_hor", hor.min().orElse(-1d), hor.max().orElse(-1d));
        }
        NumberRange.DoubleRange x = predicate.x();
        if (!x.equals(NumberRange.DoubleRange.ANY)) {
            return LText.translatable("emi_loot.entity_predicate.distance_x", x.min().orElse(-1d), x.max().orElse(-1d));
        }
        NumberRange.DoubleRange y = predicate.y();
        if (!y.equals(NumberRange.DoubleRange.ANY)) {
            return LText.translatable("emi_loot.entity_predicate.distance_y", y.min().orElse(-1d), y.max().orElse(-1d));
        }
        NumberRange.DoubleRange z = predicate.z();
        if (!z.equals(NumberRange.DoubleRange.ANY)) {
            return LText.translatable("emi_loot.entity_predicate.distance_z", z.min().orElse(-1d), z.max().orElse(-1d));
        }

        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Unparsable distance predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }

}
