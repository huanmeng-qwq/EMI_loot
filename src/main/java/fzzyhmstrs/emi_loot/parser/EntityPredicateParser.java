package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.entity.*;
import net.minecraft.text.Text;

public class EntityPredicateParser {

    public static Text parseEntityPredicate(EntityPredicate predicate) {
        return LText.translatable("emi_loot.entity_predicate.base", parseEntityPredicateInternal(predicate).getString());
    }

    private static Text parseEntityPredicateInternal(EntityPredicate predicate) {
        if (predicate == null) {
            if (EMILoot.DEBUG) EMILoot.LOGGER.warn("Entity predicate empty in table: " + LootTableParser.currentTable);
            return LText.empty();
        }

        //entity type check
        EntityTypePredicate typePredicate = predicate.type().orElse(null);
        if (typePredicate != null) {
            return EntityTypePredicateParser.parseEntityTypePredicate(typePredicate);
        }

        //distance check
        DistancePredicate distancePredicate = predicate.distance().orElse(null);
        if (distancePredicate != null) {
            return DistancePredicateParser.parseDistancePredicate(distancePredicate);
        }

        //location check
        LocationPredicate locationPredicate = predicate.location().orElse(null);
        if (locationPredicate != null) {
            return LocationPredicateParser.parseLocationPredicate(locationPredicate);
        }

        //stepping on check
        LocationPredicate steppingOnPredicate = predicate.steppingOn().orElse(null);
        if (steppingOnPredicate != null) {
            return LocationPredicateParser.parseLocationPredicate(locationPredicate);
        }

        //effects check
        EntityEffectPredicate entityEffectPredicate = predicate.effects().orElse(null);
        if (entityEffectPredicate != null) {
            return EntityEffectPredicateParser.parseEntityEffectPredicate(entityEffectPredicate);
        }

        //nbt check
        NbtPredicate nbt = predicate.nbt().orElse(null);
        if (nbt != null) {
            return NbtPredicateParser.parseNbtPredicate(nbt);
        }

        //flags check
        EntityFlagsPredicate entityFlagsPredicate = predicate.flags().orElse(null);
        if (entityFlagsPredicate != null) {
            return EntityFlagsPredicateParser.parseEntityFlagsPredicate(entityFlagsPredicate);
        }

        //equipment check
        EntityEquipmentPredicate entityEquipmentPredicate = predicate.equipment().orElse(null);
        if (entityEquipmentPredicate != null) {
            return EntityEquipmentPredicateParser.parseEntityEquipmentPredicate(entityEquipmentPredicate);
        }

        //Type Specific checks
        TypeSpecificPredicate typeSpecificPredicate = predicate.typeSpecific().orElse(null);
        if (typeSpecificPredicate != null) {
            return TypeSpecificPredicateParser.parseTypeSpecificPredicate(typeSpecificPredicate);
        }

        //vehicle checks
        EntityPredicate vehicle = predicate.vehicle().orElse(null);
        if (vehicle != null) {
            return EntityPredicateParser.parseEntityPredicate(vehicle);
        }

        //passenger checks
        EntityPredicate passenger = predicate.passenger().orElse(null);
        if (passenger != null) {
            return EntityPredicateParser.parseEntityPredicate(passenger);
        }

        //targeted entity checks
        EntityPredicate targetedEntity = predicate.targetedEntity().orElse(null);
        if (targetedEntity != null) {
            return EntityPredicateParser.parseEntityPredicate(targetedEntity);
        }

        if (EMILoot.DEBUG) EMILoot.LOGGER.warn("Entity predicate undefined in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");

    }

}
