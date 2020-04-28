 package StarBreakerMod.minions.powers;
 
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class KakaBurnPower extends AbstractKakaMinionPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:KakaBurnPower");
     public static final String POWER_ID = "KakaBurnPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
     private int amountInHand = 0;

     public KakaBurnPower(AbstractCreature owner, int newAmount) {
         this.name = NAME;
         this.ID = "KakaBurnPower";
         this.owner = owner;
         this.amount = newAmount;
         this.type = AbstractPower.PowerType.DEBUFF;
         updateDescription();
         loadRegion("flameBarrier");
     }


     public void updateDescription() {
         this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
     }

     public void onKakaStartTurnPostDraw() {
         flash();
         BaseFriendlyKaka kaka = (BaseFriendlyKaka)this.owner;
         this.amountInHand = KakaMinionManager.getInstance().cardRandomRng.random(this.amount);
         kaka.cardsInHand = Math.max(0, kaka.cardsInHand - this.amountInHand);
     }

     public void onKakaEndTurn() {
         flash();
         addToBot((AbstractGameAction)new DamageAction(this.owner, new DamageInfo(this.owner, this.amountInHand * 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
     }
 }
