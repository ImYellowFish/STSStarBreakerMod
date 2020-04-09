 package StarBreakerMod.relics;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.GainGoldAction;
 import com.megacrit.cardcrawl.actions.common.HealAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.PowerTip;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class HadesTrialTroveMark extends AbstractRelic {
     public static final String ID = "StarBreaker:HadesTrialTroveMark";

     public HadesTrialTroveMark() {
         super("StarBreaker:HadesTrialTroveMark", "tinyChest.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
         this.counter = 0;
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     public void onVictory() {
         if(this.counter > 0) {
             AbstractDungeon.getCurrRoom().addGoldToRewards(this.counter);
             setCounter(-2);
             this.description = this.DESCRIPTIONS[1];
             this.tips.clear();
             this.tips.add(new PowerTip(this.name, this.description));
             initializeTips();
         }
     }

     public AbstractRelic makeCopy() {
         return new HadesTrialTroveMark();
     }

     public void setCounter(int setCounter) {
         this.counter = setCounter;
         if (setCounter <= 0) {
             usedUp();
         }
     }
 }