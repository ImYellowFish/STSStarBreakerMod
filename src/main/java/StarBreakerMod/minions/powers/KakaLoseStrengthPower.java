 package StarBreakerMod.minions.powers;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.powers.StrengthPower;

 public class KakaLoseStrengthPower extends AbstractKakaMinionPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flex");
     public static final String POWER_ID = "KakaLoseStrengthPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaLoseStrengthPower(AbstractCreature owner, int newAmount) {
         this.name = NAME;
         this.ID = "KakaLoseStrengthPower";
         this.owner = owner;
         this.amount = newAmount;
         this.type = AbstractPower.PowerType.DEBUFF;
         updateDescription();
         loadRegion("flex");
     }


     public void updateDescription() {
         this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
     }


     public void onKakaEndTurn() {
         flash();
         addToBot((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount), -this.amount));
         addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "KakaLoseStrengthPower"));
     }
 }
