package StarBreakerMod.patches;

import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
import StarBreakerMod.minions.system.KakaMinionManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SoulGroupPatches{
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.cards.Soul",
            method = "discard",
            paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard", "boolean"}
    )
    public static class SoulDiscardPatch{

        public static SpireReturn Postfix(Soul _instance, AbstractCard c, boolean visualOnly) {
            if(c instanceof KakaPlayableCard){
                // make this not null, so it will be updated
                _instance.group = AbstractDungeon.player.masterDeck;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}