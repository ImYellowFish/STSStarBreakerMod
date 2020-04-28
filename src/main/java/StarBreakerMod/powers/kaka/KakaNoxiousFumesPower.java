 package StarBreakerMod.powers.kaka;
 
 import StarBreakerMod.actions.KakaPlayCardAction;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.powers.PoisonPower;
 import com.megacrit.cardcrawl.powers.StrengthPower;

 public class KakaNoxiousFumesPower extends AbstractKakaMinionPower {
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Noxious Fumes");
     public static final String POWER_ID = "KakaNoxiousFumesPower";
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaNoxiousFumesPower(AbstractCreature owner, int newAmount) {
         this.name = NAME;
         this.ID = "KakaNoxiousFumesPower";
         this.owner = owner;
         this.amount = newAmount;
         this.type = AbstractPower.PowerType.BUFF;
         updateDescription();
         loadRegion("fumes");
     }


     public void updateDescription() {
         this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
     }


     public void onKakaStartTurnPostDraw() {
         if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
             flash();
             for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                 if (!m.isDead && !m.isDying) {
                     addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) m, this.owner, new PoisonPower((AbstractCreature) m, this.owner, this.amount), this.amount));
                 }
             }
         }
     }
 }
