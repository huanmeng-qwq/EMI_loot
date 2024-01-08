package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.parser.processor.ListProcessors;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.block.Block;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockPredicateParser {

    public static Text parseBlockPredicate(BlockPredicate predicate) {
        return LText.translatable("emi_loot.block_predicate.base", parseBlockPredicateInternal(predicate).getString());
    }

    private static Text parseBlockPredicateInternal(BlockPredicate predicate) {
        TagKey<Block> tag = predicate.tag().orElse(null);
        if (tag != null) {
            return LText.translatable("emi_loot.block_predicate.tag", tag.id().toString());
        }

        Optional<RegistryEntryList<Block>> blocksOpt = predicate.blocks();
        Set<Block> blocks = null;
        if (blocksOpt.isPresent()) {
            blocks = blocksOpt.get().stream().map(RegistryEntry::value).collect(Collectors.toSet());
        }
        if (blocks != null && !blocks.isEmpty()) {
            List<MutableText> list = blocks.stream().map((Block::getName)).toList();
            return LText.translatable("emi_loot.block_predicate.list_1", ListProcessors.buildOrList(list));

        }

        Optional<StatePredicate> statePredicate = predicate.state();
        if (statePredicate.isPresent()) {
            return StatePredicateParser.parseStatePredicate(statePredicate.get());
        }

        NbtPredicate nbt = predicate.nbt().orElse(null);
        if (nbt != null) {
            return NbtPredicateParser.parseNbtPredicate(nbt);
        }

        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable block predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }
}
