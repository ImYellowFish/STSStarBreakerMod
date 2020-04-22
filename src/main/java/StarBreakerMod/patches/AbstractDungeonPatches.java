package StarBreakerMod.patches;


import StarBreakerMod.minions.system.KakaMinionManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

public class AbstractDungeonPatches {
    @SpireEnum
    public static AbstractDungeon.CurrentScreen SBM_KakaStat;
    @SpireEnum
    public static AbstractDungeon.CurrentScreen SBM_KakaDebug;

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method = "update"
    )
    public static class UpdatePatch{

        public static void Postfix(AbstractDungeon _instance) {
            if(_instance.screen == SBM_KakaStat){
                KakaMinionManager.getKakaStatScreen().update();
            }
            else if(_instance.screen == SBM_KakaDebug){
                KakaMinionManager.getKakaDebugScreen().update();
            }
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method = "render",
            paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
    )
    public static class RenderPatch{

        public static void Postfix(AbstractDungeon _instance, SpriteBatch sb) {
            if(_instance.screen == SBM_KakaStat){
                KakaMinionManager.getKakaStatScreen().render(sb);
            }
            else if(_instance.screen == SBM_KakaDebug){
                KakaMinionManager.getKakaDebugScreen().render(sb);
            }
        }
    }
//
//    @SpirePatch(
//            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
//            method = "openPreviousScreen",
//            paramtypes = {"com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen"}
//    )
//    public static class OpenPreviousScreenPatch{
//
//        public static void Postfix(AbstractDungeon _instance, AbstractDungeon.CurrentScreen s) {
//            if(s == SBM_KakaStat){
//                KakaMinionManager.getKakaStatScreen().reopen();
//            }
//        }
//    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
            method = "nextRoomTransition",
            paramtypes = {"com.megacrit.cardcrawl.saveAndContinue.SaveFile"}
    )
    public static class NextRoomTransitionPatch{
        public static void Postfix(AbstractDungeon _instance, SaveFile saveFile) {
            KakaMinionManager.getKakaStatScreen().upgradePreviewCard = null;
        }
    }
}