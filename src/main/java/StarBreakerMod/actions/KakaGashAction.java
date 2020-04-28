 package StarBreakerMod.actions;
 
 import StarBreakerMod.minions.cards.KakaClawCard;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.ai.DefaultKakaAI;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;

 public class KakaGashAction extends AbstractGameAction {
     private AbstractCard card;

     public KakaGashAction(AbstractCreature source, AbstractCard card, int amount) {
         this.source = source;
         this.card = card;
         this.amount = amount;
     }

     public void update() {
         BaseFriendlyKaka kaka = (BaseFriendlyKaka)this.source;
         this.card.baseDamage += this.amount;

         for(AbstractCard c : ((DefaultKakaAI)kaka.AI).keyOffensiveCardPile.group){
             if(c instanceof KakaClawCard){
                 c.baseDamage += this.amount;
             }
         }

         for(AbstractCard c : ((DefaultKakaAI)kaka.AI).optionalOffensiveCardPile.group){
             if(c instanceof KakaClawCard){
                 c.baseDamage += this.amount;
             }
         }

         this.isDone = true;
     }
 }

