 package StarBreakerMod.minions.powers;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;

 public class KakaFlameBarrierPower extends AbstractKakaMinionPower {
     public static final String POWER_ID = "KakaFlameBarrierPower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flame Barrier");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaFlameBarrierPower(AbstractCreature owner, int amount) {
         this.name = NAME;
         this.ID = "KakaFlameBarrierPower";
         this.owner = owner;
         this.amount = amount;
         updateDescription();
         loadRegion("flameBarrier");
     }

     public void stackPower(int stackAmount) {
         if (this.amount == -1) {
             return;
         }
         this.fontScale = 8.0F;
         this.amount += stackAmount;
         updateDescription();
     }

     public void updateDescription() {
         this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
     }

     public int onAttacked(DamageInfo info, int damageAmount) {
         if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {

             flash();
             addToTop((AbstractGameAction)new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
         }
         return damageAmount;
     }

     public void atStartOfTurnPostDraw() {
         addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "KakaFlameBarrierPower"));
     }
 }
