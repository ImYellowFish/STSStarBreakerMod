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
 import com.megacrit.cardcrawl.vfx.GainPennyEffect;

 public class BlackMailAction
   extends AbstractGameAction {
     private AbstractPlayer p;
     private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("StarBreaker:BlackMailAction");
     public static final String[] TEXT = uiStrings.TEXT;
     private static final float DURATION = 0.1F;

     public BlackMailAction(AbstractCreature target, AbstractCreature source, int goldAmount) {
         setValues((AbstractCreature) AbstractDungeon.player, source, -1);
         this.actionType = AbstractGameAction.ActionType.DEBUFF;
         setValues(target, source, goldAmount);
         this.duration = DURATION;
     }

     public void update() {
         if (this.target != null && this.duration == DURATION) {
             AbstractDungeon.player.gainGold(this.amount);
             for (int i = 0; i < this.amount; i++) {
                 AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
             }
         }
         tickDuration();
     }
 }
