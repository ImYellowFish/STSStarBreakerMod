 package StarBreakerMod.minions.powers;
 
 import StarBreakerMod.minions.system.KakaMinionManager;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 // Shows some information about kaka
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
         this.updateDescription();
     }

     public void updateDescription() {
         KakaMinionManager mgr = KakaMinionManager.getInstance(AbstractDungeon.player);
         if(this.owner instanceof AbstractPlayer) {
             this.description = "Total aggro:" + mgr.getAggro(this.owner);
         }
         else if(this.owner instanceof  BaseFriendlyKaka){
            this.description = ((BaseFriendlyKaka)this.owner).kakaData.toString() + " NL Total aggro:" + mgr.getAggro(this.owner);
         }
     }
 }
