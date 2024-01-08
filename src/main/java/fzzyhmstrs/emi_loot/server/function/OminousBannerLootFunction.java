package fzzyhmstrs.emi_loot.server.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.TimeCheckLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class OminousBannerLootFunction implements LootFunction {

    @Override
    public LootFunctionType getType() {
        return EMILoot.OMINOUS_BANNER;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext lootContext) {
        return ItemStack.EMPTY;
    }

    public static final Codec<OminousBannerLootFunction> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codecs.createStrictOptionalFieldCodec(Codec.STRING, "type")
                .forGetter(e -> Optional.of("lootify:ominous_banner"))).apply(instance, s -> new OminousBannerLootFunction());
    });
}
