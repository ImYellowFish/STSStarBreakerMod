 package StarBreakerMod.actions;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.GameActionManager;
 import com.megacrit.cardcrawl.actions.common.DrawCardAction;
 import com.megacrit.cardcrawl.actions.utility.WaitAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.UIStrings;
 
 public class SpireTheoryGamblingChipAction
   extends AbstractGameAction {
     private AbstractPlayer p;
     private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("StarBreaker:SpireTheoryGamblingChipAction");
     public static final String[] TEXT = uiStrings.TEXT;

     public SpireTheoryGamblingChipAction(AbstractCreature source) {
         setValues((AbstractCreature) AbstractDungeon.player, source, -1);
         this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
     }

     public void update() {
         if (this.duration == 0.5F) {
             AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
             AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new WaitAction(0.25F));
             tickDuration();
             return;
         }

         if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
             if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                 for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                     AbstractDungeon.player.hand.addToHand(c);
                 }
             }
             AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
         }

         tickDuration();
     }
 }
