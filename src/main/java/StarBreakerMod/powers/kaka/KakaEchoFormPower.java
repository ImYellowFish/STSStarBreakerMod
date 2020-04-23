 package StarBreakerMod.powers.kaka;
 
 import StarBreakerMod.actions.KakaPlayCardAction;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.CardQueueItem;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;

 public class KakaEchoFormPower extends AbstractKakaMinionPower {
     public static final String POWER_ID = "KakaEchoFormPower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Echo Form");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
     private int cardsDoubledThisTurn = 0;

     public KakaEchoFormPower(AbstractCreature owner, int amount) {
         this.name = NAME;
         this.ID = "KakaEchoFormPower";
         this.owner = owner;
         this.amount = amount;
         updateDescription();
         loadRegion("echo");
     }

     public void updateDescription() {
         if (this.amount == 1) {
             this.description = DESCRIPTIONS[0];
         } else {
             this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
         }
     }

     public void atStartOfTurn() {
         this.cardsDoubledThisTurn = 0;
     }

     public void onKakaUseCard(AbstractCard card, KakaPlayCardAction action) {
         if (!card.purgeOnUse && this.amount > 0 && AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - this.cardsDoubledThisTurn <= this.amount) {

             this.cardsDoubledThisTurn++;
             flash();
             AbstractMonster m = null;

             if (action.target != null) {
                 m = (AbstractMonster)action.target;
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

             tmp.purgeOnUse = true;
             tmp.energyOnUse = card.energyOnUse;
             AbstractDungeon.actionManager.addToTop(new KakaPlayCardAction((BaseFriendlyKaka) this.owner, m, tmp, false));
         }
     }
 }
