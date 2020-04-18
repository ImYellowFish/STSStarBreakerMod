 package StarBreakerMod.actions;
 
 import StarBreakerMod.StarBreakerMod;
 import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;

 public class KakaPlayCardAction
   extends AbstractGameAction {
     private AbstractPlayer p;
     private AbstractCard card;

     public KakaPlayCardAction(BaseFriendlyKaka kaka, AbstractCreature target, AbstractCard card) {
         setValues(target, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.card = card;
         this.duration = Settings.ACTION_DUR_MED;
     }

     public void update() {
         if (this.duration == Settings.ACTION_DUR_MED) {
             BaseFriendlyKaka kaka = (BaseFriendlyKaka) this.source;
             if (this.target != null && this.target instanceof AbstractMonster) {
                 ((KakaPlayableCard) card).calculateKakaCardDamage(kaka, (AbstractMonster) target);
             }

             // add play card animation
             addToBot((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) kaka));
             addToBot((AbstractGameAction) new KakaShowCardAction(kaka, this.card));


             // play the card
             logCard();
             ((KakaPlayableCard) this.card).OnKakaUseCard(kaka, target);

             // call afterPlayCard callback
             addToBot((AbstractGameAction) new KakaPostPlayCardAction(kaka, this.target, this.card));

         }
         this.isDone = true;
     }

     private void logCard() {
         if (this.target != null) {
             StarBreakerMod.logger.info("Kaka use card: " + this.card.name +
                     ",from " + this.source.name + ", to" + this.target.name);
         } else {
             StarBreakerMod.logger.info("Kaka use card: " + this.card.name +
                     ",from " + this.source.name + ", to null");
         }

     }
 }
