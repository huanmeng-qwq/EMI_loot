package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.entity.passive.CatVariant;
import net.minecraft.entity.passive.FrogVariant;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.*;
import net.minecraft.stat.Stat;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TypeSpecificPredicateParser {

    public static Text parseTypeSpecificPredicate(TypeSpecificPredicate predicate) {
        if (predicate instanceof LightningBoltPredicate) {
            return parseLightningBoltPredicate((LightningBoltPredicate) predicate);
        }

        if (predicate instanceof FishingHookPredicate) {
            return parseFishingHookPredicate((FishingHookPredicate) predicate);
        }

        if (predicate instanceof PlayerPredicate) {
            return parsePlayerPredicate((PlayerPredicate) predicate);
        }

        if (predicate instanceof SlimePredicate) {
            return parseSlimePredicate((SlimePredicate) predicate);
        }

        if (predicate instanceof VariantPredicates.Predicate<?> e) {
            Object variant = e.variant();
            if (variant instanceof CatVariant a) {
                MutableText catVar = LText.translatable("emi_loot.entity_predicate.type_specific.cat." + a.texture());
                return LText.translatable("emi_loot.entity_predicate.type_specific.cat", catVar.getString());

            } else if (variant instanceof FrogVariant a) {
                return LText.translatable("emi_loot.entity_predicate.type_specific.frog", a.texture());
            }
        }

        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Type specific predicate undefined or unparsable. Affects table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }


    public static Text parseLightningBoltPredicate(LightningBoltPredicate predicate) {
        NumberRange.IntRange blocksSetOnFire = predicate.blocksSetOnFire();
        if (!blocksSetOnFire.equals(NumberRange.IntRange.ANY)) {
            return LText.translatable(
                    "emi_loot.entity_predicate.type_specific.lightning",
                    NumberProcessors.processNumberRange(
                            blocksSetOnFire,
                            "emi_loot.entity_predicate.type_specific.lightning.blocks",
                            "emi_loot.entity_predicate.type_specific.lightning.blocks_2",
                            "emi_loot.entity_predicate.type_specific.lightning.blocks_3",
                            "emi_loot.entity_predicate.type_specific.lightning.blocks_4",
                            ""
                    )
            );
        }

        EntityPredicate entityStruck = predicate.entityStruck().orElse(null);
        if (entityStruck != null) {
            return LText.translatable(
                    "emi_loot.entity_predicate.type_specific.lightning",
                    LText.translatable(
                            "emi_loot.entity_predicate.type_specific.lightning.struck",
                            EntityPredicateParser.parseEntityPredicate(entityStruck).getString()
                    )
            );
        }
        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Lightning bolt predicate empty or unparsable. Affects table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }


    public static Text parseFishingHookPredicate(FishingHookPredicate predicate) {
        boolean inOpenWater = predicate.inOpenWater().orElse(false);
        return inOpenWater ? LText.translatable("emi_loot.entity_predicate.type_specific.fishing_hook_true") : LText.translatable("emi_loot.entity_predicate.type_specific.fishing_hook_false");
    }

    public static Text parsePlayerPredicate(PlayerPredicate predicate) {
        NumberRange.IntRange experienceLevel = predicate.experienceLevel();
        if (!experienceLevel.equals(NumberRange.IntRange.ANY)) {
            return LText.translatable(
                    "emi_loot.entity_predicate.type_specific.player",
                    NumberProcessors.processNumberRange(
                            experienceLevel,
                            "emi_loot.entity_predicate.type_specific.player.level",
                            "emi_loot.entity_predicate.type_specific.player.level_2",
                            "emi_loot.entity_predicate.type_specific.player.level_3",
                            "emi_loot.entity_predicate.type_specific.player.level_4",
                            ""
                    )
            );
        }

        GameMode gameMode = predicate.gameMode().orElse(null);
        if (gameMode != null) {
            return LText.translatable(
                    "emi_loot.entity_predicate.type_specific.player",
                    LText.translatable("emi_loot.entity_predicate.type_specific.player.gamemode", gameMode.getName()));
        }

        List<PlayerPredicate.StatMatcher<?>> stats = predicate.stats();
        if (!stats.isEmpty()) {
            List<MutableText> list = new LinkedList<>();
            for (PlayerPredicate.StatMatcher<?> statMatcher : stats) {
                Stat<?> stat = statMatcher.stat().get();
                String name = stat.getName();
                String[] namePieces = name.split(":");
                if (namePieces.length == 2) {
                    String typeId = namePieces[0].replace('.', ':');
                    String valueId = namePieces[1].replace('.', ':');
                    MutableText num = NumberProcessors.processNumberRange(
                            statMatcher.range(),
                            "emi_loot.entity_predicate.type_specific.player.stats.exact",
                            "emi_loot.entity_predicate.type_specific.player.stats.between",
                            "emi_loot.entity_predicate.type_specific.player.stats.at_least",
                            "emi_loot.entity_predicate.type_specific.player.stats.at_most",
                            "emi_loot.entity_predicate.type_specific.player.stats.fallback"
                    );
                    list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.stats.type." + typeId, valueId, num));
                }
            }
            if (!list.isEmpty()) {
                return LText.translatable(
                        "emi_loot.entity_predicate.type_specific.player",
                        ListProcessors.buildAndList(list)
                );
            }
        }

        Object2BooleanMap<Identifier> recipes = predicate.recipes();
        if (!recipes.isEmpty()) {
            List<MutableText> list = new LinkedList<>();
            for (Object2BooleanMap.Entry<Identifier> entry : recipes.object2BooleanEntrySet()) {
                list.add(
                        entry.getBooleanValue()
                                ?
                                LText.translatable("emi_loot.entity_predicate.type_specific.player.recipe_true", entry.getKey())
                                :
                                LText.translatable("emi_loot.entity_predicate.type_specific.player.recipe_false", entry.getKey())
                );
            }
            if (!list.isEmpty()) {
                return LText.translatable(
                        "emi_loot.entity_predicate.type_specific.player",
                        ListProcessors.buildAndList(list)
                );
            }
        }

        Map<Identifier, PlayerPredicate.AdvancementPredicate> advancements = predicate.advancements();
        if (!advancements.isEmpty()) {
            List<MutableText> list = new LinkedList<>();
            for (Map.Entry<Identifier, PlayerPredicate.AdvancementPredicate> entry : advancements.entrySet()) {
                String idString = entry.getKey().toString();
                PlayerPredicate.AdvancementPredicate advancementPredicate = entry.getValue();
                if (advancementPredicate instanceof PlayerPredicate.CompletedAdvancementPredicate) {
                    boolean done = ((PlayerPredicate.CompletedAdvancementPredicate) advancementPredicate).done;
                    if (done) {
                        list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.id_true", idString));
                    } else {
                        list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.id_false", idString));
                    }
                } else if (advancementPredicate instanceof PlayerPredicate.AdvancementCriteriaPredicate) {
                    Object2BooleanMap<String> criteria = ((PlayerPredicate.AdvancementCriteriaPredicate) advancementPredicate).criteria;
                    if (!criteria.isEmpty()) {
                        List<MutableText> list2 = new LinkedList<>();
                        for (Object2BooleanMap.Entry<String> criteriaEntry : criteria.object2BooleanEntrySet()) {
                            if (criteriaEntry.getBooleanValue()) {
                                list2.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.crit_true", criteriaEntry.getKey()));
                            } else {
                                list2.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.crit_false", criteriaEntry.getKey()));
                            }
                        }
                        list.add(LText.translatable("emi_loot.entity_predicate.type_specific.player.adv.crit_base", idString, ListProcessors.buildAndList(list2)));
                    }
                }
            }
            return LText.translatable(
                    "emi_loot.entity_predicate.type_specific.player", ListProcessors.buildAndList(list));
        }

        EntityPredicate entityPredicate = predicate.lookingAt().orElse(null);
        if (entityPredicate != null) {
            return LText.translatable(
                    "emi_loot.entity_predicate.type_specific.player",
                    LText.translatable("emi_loot.entity_predicate.type_specific.player.looking", EntityPredicateParser.parseEntityPredicate(entityPredicate)));
        }
        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Lightning bolt predicate empty or unparsable. Affects table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }

    public static Text parseSlimePredicate(SlimePredicate predicate) {
        NumberRange.IntRange size = predicate.size();
        if (size.equals(NumberRange.IntRange.ANY)) {
            if (EMILoot.DEBUG)
                EMILoot.LOGGER.warn("Undefined slime size predicate in table: " + LootTableParser.currentTable);
            return LText.translatable("emi_loot.predicate.invalid");
        }
        return NumberProcessors.processNumberRange(
                size,
                "emi_loot.entity_predicate.type_specific.slime",
                "emi_loot.entity_predicate.type_specific.slime_2",
                "emi_loot.entity_predicate.type_specific.slime_3",
                "emi_loot.entity_predicate.type_specific.slime_4",
                ""
        );
    }
}
