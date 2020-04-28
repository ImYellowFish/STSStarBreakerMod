 package StarBreakerMod.actions;
 
 import StarBreakerMod.StarBreakerMod;
 import StarBreakerMod.minions.cards.KakaPlayableCard;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.powers.AbstractKakaMinionPower;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class KakaPlayCardAction
   extends AbstractGameAction {
     public AbstractPlayer p;
     public AbstractCard card;
     private boolean useCopy;

     public KakaPlayCardAction(BaseFriendlyKaka kaka, AbstractCreature target, AbstractCard card) {
         setValues(target, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.card = card;
         this.duration = Settings.ACTION_DUR_MED;
         this.useCopy = true;
     }

     public KakaPlayCardAction(BaseFriendlyKaka kaka, AbstractCreature target, AbstractCard card, boolean useCopy) {
         setValues(target, kaka, -1);
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.card = card;
         this.duration = Settings.ACTION_DUR_MED;
         this.useCopy = useCopy;
     }

     public void update() {
         if (this.duration == Settings.ACTION_DUR_MED) {
             BaseFriendlyKaka kaka = (BaseFriendlyKaka) this.source;
             ((KakaPlayableCard) card).calculateKakaCardDamage(kaka, (AbstractMonster) target);

             // add play card animation
             addToBot((AbstractGameAction) new AnimateSlowAttackAction((AbstractCreature) kaka));
             addToBot((AbstractGameAction) new KakaShowCardAction(kaka, this.card, this.useCopy, false));


             // play the card
             logCard();
             ((KakaPlayableCard) this.card).OnKakaUseCard(kaka, target);

             // If required. exhaust
             if(this.card.exhaust || this.card.type == AbstractCard.CardType.POWER){
                 kaka.AI.onCardRemovedFromBattle(this.card);
             }

             // call on play card power
             for(AbstractPower power : kaka.powers) {
                 StarBreakerMod.logger.info("power onKakaUseCard:" + card + "," + power + "," + power.ID);
                 if (power instanceof AbstractKakaMinionPower) {
                     ((AbstractKakaMinionPower) power).onKakaUseCard(this.card, this);
                 }
             }

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
