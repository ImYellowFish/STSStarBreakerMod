package StarBreakerMod.actions;
 
 import StarBreakerMod.StarBreakerMod;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.UIStrings;
 import com.megacrit.cardcrawl.core.Settings;

 public class PutOnDeckBottomAction extends AbstractGameAction {
     private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");
     public static final String[] TEXT = uiStrings.TEXT;

     private AbstractPlayer p;
     private AbstractCard c;

     public PutOnDeckBottomAction(AbstractCreature target, AbstractCard c) {
         this.p = (AbstractPlayer) target;
         this.c = c;
         this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
         this.duration = Settings.ACTION_DUR_FAST;
     }


     public void update() {
         if (this.duration == Settings.ACTION_DUR_FAST) {
             StarBreakerMod.logger.info("Action:PutOnDeckBottom");
             this.p.hand.moveToBottomOfDeck(c);
             this.p.hand.refreshHandLayout();
             this.isDone = true;
         }
         // tickDuration();
     }
 }
