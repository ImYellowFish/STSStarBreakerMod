package StarBreakerMod.patches;

import StarBreakerMod.helpers.KakaMinionManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/*
 * All of these are checking against AbstractPlayerWithMinions to avoid double calling of those if
 * someone is using AbstractPlayerWithMinions as their base start of a character.
 */
public class PlayerMethodPatches {


    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "initializeClass"
    )
    public static class InitializePatch{

        public static void Prefix(AbstractPlayer _instance) {
            KakaMinionManager.getInstance(_instance).resetKakaData();
        }

    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "damage",
            paramtypes = {"com.megacrit.cardcrawl.cards.DamageInfo"}
    )
    public static class DamagePatch{

        public static SpireReturn Prefix(AbstractPlayer _instance, DamageInfo info) {
            boolean shouldContinue =  KakaMinionManager.getInstance(_instance).onPlayerDamage(info);
            if(shouldContinue){
                return SpireReturn.Continue();
            }
            else{
                return SpireReturn.Return(null);
            }
        }

        public static SpireReturn Postfix(AbstractPlayer _instance, DamageInfo info) {
            if(_instance.isDead){
                KakaMinionManager.getInstance(_instance).onPlayerDead();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "render",
            paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
    )
    public static class RenderPatch{

        public static void Prefix(AbstractPlayer _instance, SpriteBatch sb) {
            KakaMinionManager.getInstance((AbstractPlayer)_instance).onPlayerRender(sb);
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "update"
    )
    public static class UpdatePatch {

        public static void Postfix(AbstractPlayer _instance) {
            KakaMinionManager.getInstance((AbstractPlayer) _instance).onPlayerUpdate();
        }
    }


    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "onVictory"
    )
    public static class OnVictoryPatch{
        public static void Postfix(AbstractPlayer _instance) {
            KakaMinionManager.getInstance(_instance).onPlayerVictory();
        }
    }


    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "preBattlePrep"
    )
    public static class PreBattlePatch{

        public static void Postfix(AbstractPlayer _instance) {
           KakaMinionManager.getInstance(_instance).onPlayerPreBattlePrep();
        }

    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "applyStartOfCombatPreDrawLogic"
    )
    public static class ApplyStartOfCombatPreDrawLogicPatch{

        public static void Postfix(AbstractPlayer _instance) {
            KakaMinionManager.getInstance(_instance).onPlayerApplyStartOfCombatPreDrawLogic();
        }

    }

   @SpirePatch(
           cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
           method = "applyStartOfTurnPostDrawRelics"
    )
    public static class applyStartOfTurnPostDrawRelicsPatch {
       public static void Postfix(AbstractPlayer _instance) {
           KakaMinionManager.getInstance(_instance).onPlayerApplyStartOfTurnPostDraw();
       }
   }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "applyEndOfTurnTriggers"
    )
    public static class EndOfTurnPatch{

        public static void Postfix(AbstractCreature _instance) {
            if(_instance instanceof AbstractPlayer) {
                KakaMinionManager.getInstance((AbstractPlayer) _instance).onPlayerApplyEndOfTurnTriggers();
            }
        }
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "addBlock",
            paramtypez = {int.class}
    )
    public static class AddBlockPatch{
        public static void Postfix(AbstractCreature _instance, int blockAmount) {
            KakaMinionManager.getInstance(AbstractDungeon.player).onCreatureAddBlock(_instance, blockAmount);
        }
    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "applyTurnPowers"
    )
    public static class ApplyTurnPowersPatch{

        public static void Postfix(AbstractCreature _instance) {
            if(_instance instanceof AbstractPlayer) {
                KakaMinionManager.getInstance((AbstractPlayer)_instance).onPlayerApplyTurnPowers();
            }
        }

    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "applyStartOfTurnPostDrawPowers"
    )
    public static class ApplyStartOfTurnPostDrawPowersPatch{

        public static void Postfix(AbstractCreature _instance) {
            if(_instance instanceof AbstractPlayer) {
                KakaMinionManager.getInstance((AbstractPlayer)_instance).onPlayerApplyStartOfTurnPostDrawPowers();
            }
        }

    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "applyStartOfTurnPowers"
    )
    public static class ApplyStartOfTurnPowersPatch{

        public static void Postfix(AbstractCreature _instance) {
            if(_instance instanceof AbstractPlayer) {
                KakaMinionManager.getInstance((AbstractPlayer)_instance).onPlayerApplyStartOfTurnPowers();
            }
        }

    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.core.AbstractCreature",
            method = "updatePowers"
    )
    public static class UpdatePowersPatch{

        public static void Postfix(AbstractCreature _instance) {
            if(_instance instanceof AbstractPlayer) {
                KakaMinionManager.getInstance((AbstractPlayer)_instance).onPlayerUpdatePowers();
            }
        }

    }

}
