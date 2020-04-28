 package StarBreakerMod.minions.powers;
 
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class KakaDelayVoidPower extends AbstractKakaMinionPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:KakaDelayVoidPower");
     public static final String POWER_ID = "KakaDelayVoidPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
     private int amountInHand = 0;

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
         amountInHand = KakaMinionManager.getInstance().cardRandomRng.random(this.amount);
         kaka.energy -= amountInHand;
         kaka.cardsInHand -= amountInHand;
         if(amountInHand > 0) {
             addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "KakaDelayVoidPower"));
         }
     }
 }
