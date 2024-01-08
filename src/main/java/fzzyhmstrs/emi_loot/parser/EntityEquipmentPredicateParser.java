package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;

public class EntityEquipmentPredicateParser {

    public static Text parseEntityEquipmentPredicate(EntityEquipmentPredicate predicate) {
        ItemPredicate head = predicate.head().orElse(null);
        if (head != null) {
            return ItemPredicateParser.parseItemPredicate(head);
        }

        ItemPredicate chest = predicate.chest().orElse(null);
        if (chest != null) {
            return ItemPredicateParser.parseItemPredicate(chest);
        }

        ItemPredicate legs = predicate.legs().orElse(null);
        if (legs != null) {
            return ItemPredicateParser.parseItemPredicate(legs);
        }

        ItemPredicate feet = predicate.feet().orElse(null);
        if (feet != null) {
            return ItemPredicateParser.parseItemPredicate(feet);
        }

        ItemPredicate mainhand = predicate.mainhand().orElse(null);
        if (mainhand != null) {
            return ItemPredicateParser.parseItemPredicate(mainhand);
        }

        ItemPredicate offhand = predicate.offhand().orElse(null);
        if (offhand != null) {
            return ItemPredicateParser.parseItemPredicate(offhand);
        }
        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable equipment predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }
}
