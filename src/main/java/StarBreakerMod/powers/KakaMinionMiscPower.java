 package StarBreakerMod.powers;
 
 import StarBreakerMod.helpers.KakaMinionManager;
 import com.badlogic.gdx.math.MathUtils;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class KakaMinionMiscPower extends AbstractPower {
     public static final String POWER_ID = "KakaMinionMiscPower";
     private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:KakaMinionMiscPower");
     public static final String NAME = powerStrings.NAME;
     public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

     public KakaMinionMiscPower(AbstractCreature owner) {
         this.name = NAME;
         this.ID = "KakaMinionMiscPower";
         this.owner = owner;
         this.amount = 0;
         updateDescription();
         loadRegion("ritual");
     }

     public void atStartOfTurnPostDraw() {
         this.updateDescription();
     }

     public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
         KakaMinionManager.getInstance(AbstractDungeon.player).addAggro(
                 this.owner, damageAmount * KakaMinionManager.AGGRO_PER_DAMAGE);
         this.updateDescription();
     }

     public void updateDescription() {
         KakaMinionManager mgr = KakaMinionManager.getInstance(AbstractDungeon.player);
         this.description = "Total aggro:" + mgr.getAggro(this.owner) +
                 ". NL IsTarget" + (mgr.aggroTarget == this.owner);

     }
 }
