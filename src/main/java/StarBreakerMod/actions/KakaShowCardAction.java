 package StarBreakerMod.actions;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

 public class KakaShowCardAction
   extends AbstractGameAction {
     private AbstractCard theCard;

     public KakaShowCardAction(AbstractCreature kaka, AbstractCard card) {
         setValues((AbstractCreature) kaka, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.theCard = card;
         this.duration = Settings.ACTION_DUR_MED;
     }

     public void update() {
         if (this.target != null && this.duration == Settings.ACTION_DUR_MED) {
             AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(this.theCard.makeStatEquivalentCopy()));
         }
         tickDuration();
     }
 }
