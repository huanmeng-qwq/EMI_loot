package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.imixin.IItemPredicate;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.item.Item;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ItemPredicateParser {

    public static Text parseItemPredicate(ItemPredicate predicate) {
        TagKey<Item> tag = ((IItemPredicate) (Object) predicate).eMI_loot$getTag();
        if (tag != null) {
            return LText.translatable("emi_loot.item_predicate.tag", tag.id());
        }

        Set<Item> items = ((IItemPredicate) (Object) predicate).eMI_loot$getItems();
        if (items != null && !items.isEmpty()) {
            List<MutableText> list = items.stream().map((item) -> (MutableText) item.getName()).toList();
            return LText.translatable("emi_loot.item_predicate.items", ListProcessors.buildOrList(list));
        }

        NumberRange.IntRange count = ((IItemPredicate) (Object) predicate).eMI_loot$getCount();
        if (count != NumberRange.IntRange.ANY) {
            Integer max = count.max().orElse(null);
            Integer min = count.min().orElse(null);
            int finalMax = max != null ? max : 0;
            int finalMin = min != null ? min : 0;
            return LText.translatable("emi_loot.item_predicate.count", Integer.toString(finalMin), Integer.toString(finalMax));
        }

        NumberRange.IntRange durability = ((IItemPredicate) (Object) predicate).eMI_loot$getDurability();
        if (durability != NumberRange.IntRange.ANY) {
            Integer max = durability.max().orElse(null);
            Integer min = durability.min().orElse(null);
            int finalMax = max != null ? max : 0;
            int finalMin = min != null ? min : 0;
            return LText.translatable("emi_loot.item_predicate.durability", Integer.toString(finalMin), Integer.toString(finalMax));
        }

        List<EnchantmentPredicate> enchants = ((IItemPredicate) (Object) predicate).eMI_loot$getEnchantments();
        List<EnchantmentPredicate> storedEnchants = ((IItemPredicate) (Object) predicate).eMI_loot$getStoredEnchantments();
        if (enchants.size() + storedEnchants.size() > 0) {
            List<EnchantmentPredicate> list = new LinkedList<>();
            list.addAll(enchants);
            list.addAll(storedEnchants);
            return EnchantmentPredicateParser.parseEnchantmentPredicates(list);
        }
        if (EMILoot.DEBUG) EMILoot.LOGGER.warn("Empty item predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }

}
