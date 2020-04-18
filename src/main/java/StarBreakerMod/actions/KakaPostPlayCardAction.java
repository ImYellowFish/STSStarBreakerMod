 package StarBreakerMod.actions;
 
 import StarBreakerMod.StarBreakerMod;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;

 public class KakaPostPlayCardAction
   extends AbstractGameAction {
     private AbstractCard card;

     public KakaPostPlayCardAction(AbstractCreature kaka, AbstractCreature target, AbstractCard card) {
         setValues(target, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.card = card;
         this.duration = Settings.ACTION_DUR_MED;
     }

     public void update() {
         StarBreakerMod.logger.info("KakaPostPlayCardAction");
         ((BaseFriendlyKaka)this.source).AI.postKakaPlayCard(this.target, this.card);
         this.isDone = true;
     }
 }
