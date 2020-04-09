 package StarBreakerMod.powers;
 
 import StarBreakerMod.StarBreakerMod;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class LoseCunningPower extends AbstractPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:LoseCunningPower");
     public static final String POWER_ID = "LoseCunningPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public LoseCunningPower(AbstractCreature owner, int newAmount) {
         this.name = NAME;
         this.ID = "LoseCunningPower";
         this.owner = owner;
         this.amount = newAmount;
         this.type = AbstractPower.PowerType.DEBUFF;
         updateDescription();
         loadRegion("flex");
     }


     public void updateDescription() {
         this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
     }


     public void atStartOfTurnPostDraw() {
         flash();
         StarBreakerMod.logger.info("LoseCunningPower trigger");
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction(this.owner, this.owner, new CunningPower(this.owner, -this.amount), -this.amount));
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
     }
 }
