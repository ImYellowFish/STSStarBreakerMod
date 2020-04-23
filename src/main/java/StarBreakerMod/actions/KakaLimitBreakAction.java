 package StarBreakerMod.actions;

 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.powers.StrengthPower;

 public class KakaLimitBreakAction
         extends AbstractGameAction
 {
     public KakaLimitBreakAction(AbstractCreature p){
        this.target = p;
        this.duration = Settings.ACTION_DUR_XFAST;
     }

     public void update() {
         if (this.duration == Settings.ACTION_DUR_XFAST &&
                 this.target.hasPower("Strength")) {
             int strAmt = (this.target.getPower("Strength")).amount;
             addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)this.target, (AbstractCreature)this.target, (AbstractPower)new StrengthPower((AbstractCreature)this.target, strAmt), strAmt));
         }

         tickDuration();
     }
 }

