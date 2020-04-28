 package StarBreakerMod.minions.powers;
 
 import StarBreakerMod.StarBreakerMod;
 import StarBreakerMod.actions.KakaPlayCardAction;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;

 public class KakaDoubleTapPower extends AbstractKakaMinionPower {
     public static final String POWER_ID = "KakaDoubleTapPower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rage");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaDoubleTapPower(AbstractCreature owner, int amount) {
         this.name = NAME;
         this.ID = "KakaDoubleTapPower";
         this.owner = owner;
         this.amount = amount;
         updateDescription();
         loadRegion("doubleTap");
     }

     public void updateDescription() {
         if (this.amount == 1) {
             this.description = DESCRIPTIONS[0];
         } else {
             this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
         }
     }

     @Override
     public void onKakaUseCard(AbstractCard card, KakaPlayCardAction action) {
         StarBreakerMod.logger.info("On kaka doubleTap:" + card + ", " + card.type);
         if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && this.amount > 0) {
             flash();
             AbstractMonster m = null;

             if (action.target != null) {
                 m = (AbstractMonster) action.target;
             }

             AbstractCard tmp = card.makeSameInstanceOf();
             AbstractDungeon.player.limbo.addToBottom(tmp);
             tmp.current_x = card.current_x;
             tmp.current_y = card.current_y;
             tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
             tmp.target_y = Settings.HEIGHT / 2.0F;

             if (m != null) {
                 tmp.calculateCardDamage(m);
             }

             tmp.energyOnUse = card.energyOnUse;
             tmp.purgeOnUse = true;

             AbstractDungeon.actionManager.addToTop(new KakaPlayCardAction((BaseFriendlyKaka) this.owner, m, tmp, false));
//             AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);

             this.amount--;
             if (this.amount == 0) {
                 addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "KakaDoubleTapPower"));
             }
         }
     }

     public void onKakaEndTurn() {
         addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "KakaDoubleTapPower"));
     }
 }
