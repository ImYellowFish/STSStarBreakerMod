 package StarBreakerMod.powers.kaka;
 
 import StarBreakerMod.actions.KakaPlayCardAction;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.powers.StrengthPower;

 public class KakaDevaFormPower extends AbstractKakaMinionPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("DevaForm");
     public static final String POWER_ID = "KakaDevaFormPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
     private int energyGainAmount = 1;

     public KakaDevaFormPower(AbstractCreature owner, int newAmount) {
         this.name = NAME;
         this.ID = "KakaDevaFormPower";
         this.owner = owner;
         this.amount = 1;
         this.energyGainAmount = 1;
         updateDescription();
         loadRegion("deva2");
     }


     public void updateDescription() {
         if (this.energyGainAmount == 1) {
             this.description = DESCRIPTIONS[0] + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
         } else {
             this.description = DESCRIPTIONS[1] + this.energyGainAmount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
         }
     }

     public void stackPower(int stackAmount) {
         super.stackPower(stackAmount);
         this.energyGainAmount++;
     }

     public void onKakaStartTurnPostDraw() {
         flash();
         BaseFriendlyKaka kaka = (BaseFriendlyKaka)this.owner;
         kaka.energy += this.amount;
         this.energyGainAmount += this.amount;
         updateDescription();
     }
 }
