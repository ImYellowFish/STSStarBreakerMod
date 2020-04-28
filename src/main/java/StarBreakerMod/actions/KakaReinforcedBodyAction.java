 package StarBreakerMod.actions;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;

 public class KakaReinforcedBodyAction extends AbstractGameAction {
     private boolean freeToPlayOnce = false;
     private int energyOnUse = -1;

     private AbstractCreature p;

     public KakaReinforcedBodyAction(AbstractCreature p, int amount, int energyOnUse) {
         this.p = p;
         this.duration = Settings.ACTION_DUR_XFAST;
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.energyOnUse = energyOnUse;
     }

     public void update() {
         int effect = this.energyOnUse;

         if (effect > 0) {
             for (int i = 0; i < effect; i++) {
                 addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)this.p, (AbstractCreature)this.p, this.amount));
             }
         }
         this.isDone = true;
     }
 }


