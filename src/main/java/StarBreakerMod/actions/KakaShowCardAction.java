 package StarBreakerMod.actions;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
 import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

 public class KakaShowCardAction
   extends AbstractGameAction {
     private AbstractCard theCard;
     private boolean useCopy;

     public KakaShowCardAction(AbstractCreature kaka, AbstractCard card) {
         setValues((AbstractCreature) kaka, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.theCard = card;
         this.duration = Settings.ACTION_DUR_MED;
         this.useCopy = true;
     }

     public KakaShowCardAction(AbstractCreature kaka, AbstractCard card, boolean useCopy) {
         setValues((AbstractCreature) kaka, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.theCard = card;
         this.duration = Settings.ACTION_DUR_MED;
         this.useCopy = useCopy;
     }

     public void update() {
         if (this.target != null && this.duration == Settings.ACTION_DUR_MED) {
             AbstractCard c;
             if(this.useCopy)
                c = this.theCard.makeStatEquivalentCopy();
             else{
                 c = this.theCard;
             }
             AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c));
             if(this.theCard.exhaust){
                 AbstractDungeon.topLevelEffectsQueue.add(new ExhaustCardEffect(c));
             }
         }
         tickDuration();
     }
 }
