package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.text.Text;

public class DamageSourcePredicateParser {

    public static Text parseDamageSourcePredicate(DamageSourcePredicate predicate) {
        EntityPredicate directPredicate = predicate.directEntity().orElse(null);
        if (directPredicate != null) {
            return EntityPredicateParser.parseEntityPredicate(directPredicate);
        }

        EntityPredicate sourcePredicate = predicate.sourceEntity().orElse(null);
        if (sourcePredicate != null) {
            return EntityPredicateParser.parseEntityPredicate(sourcePredicate);
        }

        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable damage source predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }

}
