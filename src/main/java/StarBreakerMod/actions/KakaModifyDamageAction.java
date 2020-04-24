 package StarBreakerMod.actions;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
 import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

 public class KakaModifyDamageAction
   extends AbstractGameAction {
     private AbstractCard theCard;

     public KakaModifyDamageAction(AbstractCreature kaka, AbstractCard card, int newAmount) {
         setValues((AbstractCreature) kaka, kaka, -1);
         this.actionType = ActionType.CARD_MANIPULATION;
         this.theCard = card;
         this.amount = newAmount;
     }


     public void update() {
         AbstractCard c = this.theCard;
         c.baseDamage += this.amount;
         if(c.baseDamage < 0)
             c.baseDamage = 0;
         this.isDone = true;
     }
 }
