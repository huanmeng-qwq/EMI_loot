package fzzyhmstrs.emi_loot.mixins;

import fzzyhmstrs.emi_loot.imixin.IItemPredicate;
import net.minecraft.item.Item;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 2024/1/3<br>
 * EMI_loot<br>
 *
 * @author huanmeng_qwq
 */
@Mixin(ItemPredicate.class)
public class ItemPredicateAccessor implements IItemPredicate {
    @Shadow @Final private Optional<TagKey<Item>> tag;

    @Shadow @Final private Optional<RegistryEntryList<Item>> items;

    @Shadow @Final private NumberRange.IntRange count;

    @Shadow @Final private NumberRange.IntRange durability;

    @Shadow @Final private List<EnchantmentPredicate> enchantments;

    @Shadow @Final private List<EnchantmentPredicate> storedEnchantments;

    @Override
    public TagKey<Item> eMI_loot$getTag() {
        return tag.orElse(null);
    }

    @Override
    public Set<Item> eMI_loot$getItems() {
        if (items.orElse(null) != null) {
            return items.orElse(null).stream().map(RegistryEntry::value).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public NumberRange.IntRange eMI_loot$getCount() {
        return count;
    }

    @Override
    public NumberRange.IntRange eMI_loot$getDurability() {
        return durability;
    }

    @Override
    public List<EnchantmentPredicate> eMI_loot$getEnchantments() {
        return enchantments;
    }

    @Override
    public List<EnchantmentPredicate> eMI_loot$getStoredEnchantments() {
        return storedEnchantments;
    }
}
