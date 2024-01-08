package fzzyhmstrs.emi_loot.imixin;

import net.minecraft.item.Item;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.Set;

public interface IItemPredicate {

    TagKey<Item> eMI_loot$getTag();

    Set<Item> eMI_loot$getItems();

    NumberRange.IntRange eMI_loot$getCount();

    NumberRange.IntRange eMI_loot$getDurability();

    List<EnchantmentPredicate> eMI_loot$getEnchantments();

    List<EnchantmentPredicate> eMI_loot$getStoredEnchantments();

}
