package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.List;

public class EnchantmentPredicateParser {

    public static Text parseEnchantmentPredicates(List<EnchantmentPredicate> list) {
        List<MutableText> list2 = new LinkedList<>();
        for (EnchantmentPredicate predicate : list) {
            predicate.enchantment().ifPresent(enchant -> list2.add((MutableText) enchant.value().getName(1)));
        }
        if (!list2.isEmpty()) {
            return LText.translatable("emi_loot.item_predicate.enchant", ListProcessors.buildAndList(list2));
        }
        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable enchantment predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }
}
