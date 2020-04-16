 package StarBreakerMod.actions;
 
 import StarBreakerMod.monsters.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.GameActionManager;
 import com.megacrit.cardcrawl.actions.common.DrawCardAction;
 import com.megacrit.cardcrawl.actions.utility.WaitAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.UIStrings;
 import com.megacrit.cardcrawl.vfx.GainPennyEffect;
 import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

 public class KakaShowCardAction
   extends AbstractGameAction {
     private AbstractPlayer p;
     private AbstractCard theCard;

     public KakaShowCardAction(BaseFriendlyKaka kaka, AbstractCard card) {
         setValues((AbstractCreature) AbstractDungeon.player, source, -1);
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
