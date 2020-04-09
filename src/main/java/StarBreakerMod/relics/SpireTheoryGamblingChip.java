 package StarBreakerMod.relics;
 import StarBreakerMod.actions.SpireTheoryGamblingChipAction;
 import StarBreakerMod.powers.BloodScalesPower;
 import basemod.abstracts.CustomRelic;
 import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class SpireTheoryGamblingChip extends CustomRelic implements ClickableRelic {
     public static final String ID = "StarBreaker:SpireTheoryGamblingChip";
     // public static final String IMG_PATH = "StarBreakerImages/scrapOoze.png";

     public SpireTheoryGamblingChip() {
         super("StarBreaker:SpireTheoryGamblingChip", "gamblingChip.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     public void onRightClick() {
         if (!AbstractDungeon.player.hand.isEmpty()) {
             flash();
             AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new SpireTheoryGamblingChipAction((AbstractCreature) AbstractDungeon.player));
         }
     }

     public AbstractRelic makeCopy() {
         return new SpireTheoryGamblingChip();
     }
 }


