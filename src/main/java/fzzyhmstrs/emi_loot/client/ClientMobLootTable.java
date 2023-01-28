package fzzyhmstrs.emi_loot.client;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ClientMobLootTable extends AbstractTextKeyParsingClientLootTable<ClientMobLootTable> {

    public static ClientMobLootTable INSTANCE = new ClientMobLootTable();
    private static final Identifier EMPTY = new Identifier("entity/empty");
    public final Identifier id;
    public final Identifier mobId;
    public String color = "";

    public ClientMobLootTable(){
        super();
        this.id = EMPTY;
        this.mobId = new Identifier("empty");
    }

    public ClientMobLootTable(Identifier id,Identifier mobId, Map<List<TextKey>, ClientRawPool> map){
        super(map);
        this.id = id;
        String ns = id.getNamespace();
        String pth = id.getPath();
        if (Objects.equals(mobId, new Identifier("empty"))) {
            int lastSlashIndex = pth.lastIndexOf('/');
            if (lastSlashIndex == -1) {
                this.mobId = new Identifier(ns, pth);
            } else {
                String subString = pth.substring(Math.min(lastSlashIndex + 1, pth.length()));
                Identifier tempMobId = new Identifier(ns, subString);
                if (!Registry.ENTITY_TYPE.containsId(tempMobId)) {
                    String choppedString = pth.substring(0, lastSlashIndex);
                    int nextSlashIndex = choppedString.lastIndexOf('/');
                    if (nextSlashIndex != -1) {
                        String sheepString = choppedString.substring(Math.min(nextSlashIndex + 1, pth.length()));
                        tempMobId = new Identifier(ns, sheepString);
                        this.mobId = tempMobId;
                        if (Registry.ENTITY_TYPE.containsId(tempMobId)) {
                            this.color = subString;
                        }
                    } else {
                        this.mobId = tempMobId;
                    }
                } else {
                    this.mobId = tempMobId;
                }
            }
        } else {
            this.mobId = mobId;
        }
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public boolean isEmpty(){
        return Objects.equals(id, EMPTY);
    }

    @Override
    List<Pair<Integer, Text>> getSpecialTextKeyList(World world, Block block) {
        return List.of();
    }

    @Override
    Pair<Identifier,Identifier> getBufId(PacketByteBuf buf) {
        Identifier id = getIdFromBuf(buf);
        Identifier mobId = getIdFromBuf(buf);
        return new Pair<>(id,mobId);
    }

    @Override
    ClientMobLootTable simpleTableToReturn(Pair<Identifier,Identifier> ids,PacketByteBuf buf) {
        ClientRawPool simplePool = new ClientRawPool(new HashMap<>());
        Object2FloatMap<ItemStack> simpleMap = new Object2FloatOpenHashMap<>();
        ItemStack simpleStack = new ItemStack(buf.readRegistryValue(Registries.ITEM));
        simpleMap.put(simpleStack,100F);
        simplePool.map().put(new ArrayList<>(),simpleMap);
        Map<List<TextKey>, ClientRawPool> itemMap = new HashMap<>();
        itemMap.put(new ArrayList<>(),simplePool);
        return new ClientMobLootTable(ids.getLeft(),ids.getRight(),itemMap);
    }

    @Override
    ClientMobLootTable emptyTableToReturn() {
        return new ClientMobLootTable();
    }

    @Override
    ClientMobLootTable filledTableToReturn(Pair<Identifier,Identifier> ids, Map<List<TextKey>, ClientRawPool> itemMap) {
        return new ClientMobLootTable(ids.getLeft(),ids.getRight(),itemMap);
    }
}
