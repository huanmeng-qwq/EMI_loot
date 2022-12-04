package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.mixins.EntityPredicateAccessor;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.predicate.entity.*;
import net.minecraft.text.Text;

public class EntityPredicateParser {

    public static Text parseEntityPredicate(EntityPredicate predicate){
        return LText.translatable("emi_loot.entity_predicate.base",parseEntityPredicateInternal(predicate).getString());
    }

    private static Text parseEntityPredicateInternal(EntityPredicate predicate){
        if (predicate.equals(EntityPredicate.ANY)) return LText.translatable("emi_loot.entity_predicate.any");

        //entity type check
        EntityTypePredicate typePredicate = ((EntityPredicateAccessor)predicate).getType();
        if (!typePredicate.equals(EntityTypePredicate.ANY)) {
           return EntityTypePredicateParser.parseEntityTypePredicate(typePredicate);
        }

        //distance check
        DistancePredicate distancePredicate = ((EntityPredicateAccessor)predicate).getDistance();
        if (!distancePredicate.equals(DistancePredicate.ANY)){
            return DistancePredicateParser.parseDistancePredicate(distancePredicate);
        }

        //location check
        LocationPredicate locationPredicate = ((EntityPredicateAccessor)predicate).getLocation();
        if (!locationPredicate.equals(LocationPredicate.ANY)){
            return LocationPredicateParser.parseLocationPredicate(locationPredicate);
        }

        //stepping on check
        LocationPredicate steppingOnPredicate = ((EntityPredicateAccessor)predicate).getSteppingOn();
        if (!steppingOnPredicate.equals(LocationPredicate.ANY)){
            return LocationPredicateParser.parseLocationPredicate(locationPredicate);
        }

        //effects check
        EntityEffectPredicate entityEffectPredicate = ((EntityPredicateAccessor)predicate).getEffects();
        if (!entityEffectPredicate.equals(EntityEffectPredicate.EMPTY)){
            return EntityEffectPredicateParser.parseEntityEffectPredicate(entityEffectPredicate);
        }

        //nbt check
        NbtPredicate nbt = ((EntityPredicateAccessor)predicate).getNbt();
        if (!nbt.equals(NbtPredicate.ANY)){
            return NbtPredicateParser.parseNbtPredicate(nbt);
        }

        //flags check
        EntityFlagsPredicate entityFlagsPredicate = ((EntityPredicateAccessor)predicate).getFlags();
        if (!entityFlagsPredicate.equals(EntityFlagsPredicate.ANY)){
            return EntityFlagsPredicateParser.parseEntityFlagsPredicate(entityFlagsPredicate);
        }

        //equipment check
        EntityEquipmentPredicate entityEquipmentPredicate = ((EntityPredicateAccessor)predicate).getEquipment();
        if (!entityEquipmentPredicate.equals(EntityEquipmentPredicate.ANY)){
            return EntityEquipmentPredicateParser.parseEntityEquipmentPredicate(entityEquipmentPredicate);
        }

        //Type Specific checks
        /*FishingHookPredicate typeSpecificPredicate = ((EntityPredicateAccessor)predicate).getTypeSpecific();
        if (!typeSpecificPredicate.equals(TypeSpecificPredicate.ANY)){
            return TypeSpecificPredicateParser.parseTypeSpecificPredicate(typeSpecificPredicate);
        }*/

        PlayerPredicate playerPredicate = ((EntityPredicateAccessor)predicate).getPlayer();
        if (!playerPredicate.equals(PlayerPredicate.ANY)){
            return TypeSpecificPredicateParser.parsePlayerPredicate(playerPredicate);
        }

        FishingHookPredicate fishingHookPredicate = ((EntityPredicateAccessor)predicate).getFishingHook();
        if (!fishingHookPredicate.equals(FishingHookPredicate.ANY)){
            return TypeSpecificPredicateParser.parseFishingHookPredicate(fishingHookPredicate);
        }

        LightningBoltPredicate lightningBoltPredicate = ((EntityPredicateAccessor)predicate).getLightningBolt();
        if (!lightningBoltPredicate.equals(LightningBoltPredicate.ANY)){
            return TypeSpecificPredicateParser.parseLightningBoltPredicate(lightningBoltPredicate);
        }

        //vehicle checks
        EntityPredicate vehicle = ((EntityPredicateAccessor)predicate).getVehicle();
        if (!vehicle.equals(EntityPredicate.ANY)){
            return EntityPredicateParser.parseEntityPredicate(vehicle);
        }

        //passenger checks
        EntityPredicate passenger = ((EntityPredicateAccessor)predicate).getPassenger();
        if (!passenger.equals(EntityPredicate.ANY)){
            return EntityPredicateParser.parseEntityPredicate(passenger);
        }

        //targeted entity checks
        EntityPredicate targetedEntity = ((EntityPredicateAccessor)predicate).getTargetedEntity();
        if (!targetedEntity.equals(EntityPredicate.ANY)){
            return EntityPredicateParser.parseEntityPredicate(targetedEntity);
        }

        if (EMILoot.DEBUG) EMILoot.LOGGER.warning("Entity predicate undefined in table: "  + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");

    }

}
