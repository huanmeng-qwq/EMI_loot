package fzzyhmstrs.emi_loot.parser.function;

import fzzyhmstrs.emi_loot.imixin.ISetNameLootFunction;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.util.TextKey;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.text.Text;

import java.util.List;

public class SetNameFunctionParser implements FunctionParser {
    
    @Override
    public LootTableParser.LootFunctionResult parseFunction(LootFunction function,ItemStack stack,boolean parentIsAlternative, List<TextKey> conditionTexts){
        ISetNameLootFunction f = (ISetNameLootFunction) function;
        Text text = f.eMI_loot$name().orElse(null);
        stack.setCustomName(text);
        return new LootTableParser.LootFunctionResult(TextKey.empty(), stack, conditionTexts);
    }
}
