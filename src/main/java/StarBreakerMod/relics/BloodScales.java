 package StarBreakerMod.relics;
 import StarBreakerMod.powers.BloodScalesPower;
 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class BloodScales extends CustomRelic {
     public static final String ID = "StarBreaker:BloodScales";
     // public static final String IMG_PATH = "StarBreakerImages/scrapOoze.png";

     public BloodScales() {
         super("StarBreaker:BloodScales", "bronzeScales.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     public void atBattleStart() {
         flash();
         AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ApplyPowerAction((AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player, (AbstractPower) new BloodScalesPower((AbstractCreature) AbstractDungeon.player), 1));
     }

     public AbstractRelic makeCopy() {
         return new BloodScales();
     }
 }


