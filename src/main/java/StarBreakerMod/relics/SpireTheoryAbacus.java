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

 public class SpireTheoryAbacus extends CustomRelic implements ClickableRelic{
     public static final String ID = "StarBreaker:SpireTheoryAbacus";
     // public static final String IMG_PATH = "StarBreakerImages/scrapOoze.png";
    public static final int USE_PER_BATTLE = 5;

     public SpireTheoryAbacus() {
         super("StarBreaker:SpireTheoryAbacus", "abacus.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
         this.counter = 0;
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     @Override
     public void atBattleStartPreDraw() {
         this.counter = USE_PER_BATTLE;
     }

     public void onRightClick() {
         flash();
         if(this.counter > 0) {
             this.counter--;
             AbstractDungeon.cardRng.random();
             AbstractDungeon.potionRng.random();
         }
     }

     public AbstractRelic makeCopy() {
         return new SpireTheoryAbacus();
     }
 }


