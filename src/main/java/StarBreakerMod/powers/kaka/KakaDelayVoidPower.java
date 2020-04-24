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

 public class KakaDelayVoidPower extends AbstractKakaMinionPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:KakaDelayVoidPower");
     public static final String POWER_ID = "KakaDelayVoidPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaDelayVoidPower(AbstractCreature owner, int newAmount) {
         this.name = NAME;
         this.ID = "KakaDelayVoidPower";
         this.owner = owner;
         this.amount = newAmount;
         this.type = AbstractPower.PowerType.DEBUFF;
         updateDescription();
         loadRegion("fasting");
     }


     public void updateDescription() {
         this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
     }


     public void onKakaStartTurnPostDraw() {
         flash();
         BaseFriendlyKaka kaka = (BaseFriendlyKaka)this.owner;
         kaka.energy -= this.amount;
         kaka.cardsInHand -= this.amount;
         addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "KakaDelayVoidPower"));
     }
 }
