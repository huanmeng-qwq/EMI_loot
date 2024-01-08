package fzzyhmstrs.emi_loot.server.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class BlownUpByCreeperLootCondition implements LootCondition {
    @Override
    public LootConditionType getType() {
        return EMILoot.CREEPER;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return false;
    }

    public static final Codec<BlownUpByCreeperLootCondition> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codecs.createStrictOptionalFieldCodec(Codec.STRING, "type")
                .forGetter(e -> Optional.of("lootify:creeper"))).apply(instance, s -> new BlownUpByCreeperLootCondition());
    });

}
