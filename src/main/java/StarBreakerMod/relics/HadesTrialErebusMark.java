 package StarBreakerMod.relics;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.HealAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.PowerTip;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class HadesTrialErebusMark extends AbstractRelic {
     public static final String ID = "StarBreaker:HadesTrialErebusMark";

     public HadesTrialErebusMark() {
         super("StarBreaker:HadesTrialErebusMark", "virus.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
         this.counter = 1;
     }


     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     // Remove all rewards when damaged by enemy
     public int onAttacked(DamageInfo info, int damageAmount) {
         if (this.counter > 0) {
             if (info.owner != null && info.type != DamageInfo.DamageType.THORNS &&
                     info.type != DamageInfo.DamageType.HP_LOSS &&
                     info.owner != AbstractDungeon.player &&
                     damageAmount > 0) {
                 flash();

                 // trigger
                 this.counter--;
                 (AbstractDungeon.getCurrRoom()).rewards.clear();
                 AbstractDungeon.getCurrRoom().addGoldToRewards(1);
                 AbstractDungeon.getCurrRoom().mugged = true;

                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new SFXAction("VO_CHAMP_2A"));
                 CheckErebusExpired();
             }
         }
         return damageAmount;
     }

     public void onVictory() {
         this.counter--;
         CheckErebusExpired();
     }

     public void CheckErebusExpired() {
         if (this.counter <= 0) {
             setCounter(-2);
             this.description = this.DESCRIPTIONS[1];
             this.tips.clear();
             this.tips.add(new PowerTip(this.name, this.description));
             initializeTips();
         }
     }

     public AbstractRelic makeCopy() {
         return new HadesTrialErebusMark();
     }

     public void setCounter(int setCounter) {
         this.counter = setCounter;
         if (setCounter <= 0) {
             usedUp();
         }
     }
 }