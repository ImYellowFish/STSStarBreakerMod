 package StarBreakerMod.powers.kaka;
 
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class KakaMinionAggroPower extends AbstractPower {
     public static final String POWER_ID = "KakaMinionAggroPower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:KakaMinionAggroPower");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaMinionAggroPower(AbstractCreature owner) {
         this.name = NAME;
         this.ID = "KakaMinionAggroPower";
         this.owner = owner;
         updateDescription();
         loadRegion("lockon");
     }

     public void atStartOfTurnPostDraw() {
         this.updateDescription();
     }

     public void updateDescription() {
         this.description = DESCRIPTIONS[0];
     }
 }
