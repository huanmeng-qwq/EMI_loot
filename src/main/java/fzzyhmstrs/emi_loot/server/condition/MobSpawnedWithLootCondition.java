package fzzyhmstrs.emi_loot.server.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class MobSpawnedWithLootCondition implements LootCondition {
    @Override
    public LootConditionType getType() {
        return EMILoot.SPAWNS_WITH;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return false;
    }

    public static final Codec<MobSpawnedWithLootCondition> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codecs.createStrictOptionalFieldCodec(Codec.STRING, "type")
                .forGetter(e -> Optional.of("lootify:spawns_with"))).apply(instance, s -> new MobSpawnedWithLootCondition());
    });


}
