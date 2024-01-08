package fzzyhmstrs.emi_loot.server.function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public class SetAnyDamageLootFunction implements LootFunction {

    @Override
    public LootFunctionType getType() {
        return EMILoot.SET_ANY_DAMAGE;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext lootContext) {
        return ItemStack.EMPTY;
    }

    public static final Codec<SetAnyDamageLootFunction> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codecs.createStrictOptionalFieldCodec(Codec.STRING, "type")
                .forGetter(e -> Optional.of("lootify:set_any_damage"))).apply(instance, s -> new SetAnyDamageLootFunction());
    });

}
