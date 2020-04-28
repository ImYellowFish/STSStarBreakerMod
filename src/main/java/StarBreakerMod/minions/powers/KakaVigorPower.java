 package StarBreakerMod.minions.powers;
 
 import StarBreakerMod.actions.KakaPlayCardAction;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class KakaVigorPower extends AbstractKakaMinionPower {
     public static final String POWER_ID = "KakaVigorPower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vigor");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaVigorPower(AbstractCreature owner, int amount) {
         this.name = NAME;
         this.ID = "KakaVigorPower";
         this.owner = owner;
         this.amount = amount;
         updateDescription();
         loadRegion("vigor");
         this.type = AbstractPower.PowerType.BUFF;
         this.isTurnBased = false;
     }

     public void updateDescription() {
         this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
     }


     public float atDamageGive(float damage, DamageInfo.DamageType type) {
         if (type == DamageInfo.DamageType.NORMAL) {
             return damage += this.amount;
         }
         return damage;
     }


     public void onKakaUseCard(AbstractCard card, KakaPlayCardAction action) {
         if (card.type == AbstractCard.CardType.ATTACK) {
             flash();
             addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "KakaVigorPower"));
         }
     }
 }
