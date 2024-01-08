package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;

public class EntityTypePredicateParser {

    public static Text parseEntityTypePredicate(EntityTypePredicate predicate) {
        RegistryEntryList<EntityType<?>> types = predicate.types();
        if (types != null) {
            for (RegistryEntry<EntityType<?>> type : types) {
                EntityType<?> value = type.value();
                return LText.translatable("emi_loot.entity_predicate.type_single", value.getName().getString());
            }
        }
        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable entity type predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }
}
