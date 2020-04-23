 package StarBreakerMod.powers.kaka;
 
 import StarBreakerMod.actions.KakaPlayCardAction;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;

 public class KakaRagePower extends AbstractKakaMinionPower {
     public static final String POWER_ID = "KakaMinionRagePower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rage");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaRagePower(AbstractCreature owner, int amount) {
         this.name = NAME;
         this.ID = "KakaMinionRagePower";
         this.owner = owner;
         this.amount = amount;
         updateDescription();
         loadRegion("anger");
     }

     public void atStartOfTurnPostDraw() {
         this.updateDescription();
     }

     public void updateDescription() {
         this.description = DESCRIPTIONS[0];
     }

     public void onKakaUseCard(AbstractCard card, KakaPlayCardAction action) {
         if (card.type == AbstractCard.CardType.ATTACK) {
             addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, this.amount));
             flash();
         }
     }

     public void atEndOfTurn(boolean isPlayer) {
         addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "KakaMinionRagePower"));
     }
 }
