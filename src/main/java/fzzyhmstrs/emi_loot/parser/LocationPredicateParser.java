package fzzyhmstrs.emi_loot.parser;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.LText;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.FluidPredicate;
import net.minecraft.predicate.LightPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public class LocationPredicateParser {

    public static Text parseLocationPredicate(LocationPredicate predicate) {
        Optional<LocationPredicate.PositionRange> position = predicate.position();
        if (position.isPresent()) {
            NumberRange.DoubleRange x = position.get().x();
            NumberRange.DoubleRange y = position.get().y();
            NumberRange.DoubleRange z = position.get().z();
            return LText.translatable("emi_loot.location_predicate.x", x.min().orElse(0d), x.max().orElse(null))
                    .append(
                            LText.translatable("emi_loot.location_predicate.y", y.min().orElse(0d), y.max().orElse(null))
                    )
                    .append(
                            LText.translatable("emi_loot.location_predicate.z", z.min().orElse(0d), z.max().orElse(null))
                    )
                    ;
        }

        RegistryKey<World> dim = predicate.dimension().orElse(null);
        if (dim != null) {
            return LText.translatable("emi_loot.location_predicate.dim", dim.getValue().toString());
        }

        RegistryKey<Biome> biome = predicate.biome().orElse(null);
        if (biome != null) {
            return LText.translatable("emi_loot.location_predicate.biome", biome.getValue().toString());
        }

        RegistryKey<Structure> feature = predicate.structure().orElse(null);
        if (feature != null) {
            return LText.translatable("emi_loot.location_predicate.structure", feature.getValue().toString());
        }

        Boolean smokey = predicate.smokey().orElse(null);
        if (smokey != null) {
            if (smokey) {
                return LText.translatable("emi_loot.location_predicate.smoke_true");
            } else {
                return LText.translatable("emi_loot.location_predicate.smoke_false");
            }
        }

        LightPredicate light = predicate.light().orElse(null);
        if (light != null) {
            return LightPredicateParser.parseLightPredicate(light);
        }

        BlockPredicate block = predicate.block().orElse(null);
        if (block != null) {
            return BlockPredicateParser.parseBlockPredicate(block);
        }

        FluidPredicate fluid = predicate.fluid().orElse(null);
        if (fluid != null) {
            return FluidPredicateParser.parseFluidPredicate(fluid);
        }
        if (EMILoot.DEBUG)
            EMILoot.LOGGER.warn("Empty or unparsable location predicate in table: " + LootTableParser.currentTable);
        return LText.translatable("emi_loot.predicate.invalid");
    }

}
