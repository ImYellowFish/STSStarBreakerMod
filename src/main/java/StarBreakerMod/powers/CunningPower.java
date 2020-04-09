 package StarBreakerMod.powers;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.unlock.UnlockTracker;
 
 public class CunningPower extends AbstractPower {
   public static final String POWER_ID = "CunningPower";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:CunningPower");
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

   public CunningPower(AbstractCreature owner, int amount) {
     this.name = NAME;
     this.ID = "CunningPower";
     this.owner = owner;
     this.amount = amount;
     updateDescription();
     loadRegion("nightmare");
     this.canGoNegative = true;
   }


   public void playApplyPowerSfx() {
     CardCrawlGame.sound.play("POWER_FOCUS", 0.05F);
   }


   public void stackPower(int stackAmount) {
     this.fontScale = 8.0F;
     this.amount += stackAmount;

     if (this.amount == 0) {
       AbstractDungeon.actionManager.addToTop((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "CunningPower"));
     }

     if (this.amount >= 999) {
       this.amount = 999;
     }

     if (this.amount <= -999) {
       this.amount = -999;
     }
   }


   public void reducePower(int reduceAmount) {
     this.fontScale = 8.0F;
     this.amount -= reduceAmount;

     if (this.amount == 0) {
       AbstractDungeon.actionManager.addToTop((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, NAME));
     }

     if (this.amount >= 999) {
       this.amount = 999;
     }

     if (this.amount <= -999) {
       this.amount = -999;
     }
   }


   public void updateDescription() {
     if (this.amount > 0) {
       this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
       this.type = AbstractPower.PowerType.BUFF;
     } else {
       this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
       this.type = AbstractPower.PowerType.DEBUFF;
     }
   }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
      if (info.type == DamageInfo.DamageType.THORNS && info.owner == this.owner) {
        return damageAmount + this.amount;
      }
      return damageAmount;
    }
 }
