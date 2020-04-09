 package StarBreakerMod.relics;
 import StarBreakerMod.powers.BloodScalesPower;
 import StarBreakerMod.powers.DeepDarkFantasyPower;
 import basemod.abstracts.CustomRelic;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class DarkVanArmor extends CustomRelic {
     public static final String ID = "StarBreaker:DarkVanArmor";
     public static final int BLOCK = 1;
     public static final int BLOCK_PLUS = 1;
     // public static final String IMG_PATH = "StarBreakerImages/scrapOoze.png";

     public DarkVanArmor() {
         super("StarBreaker:DarkVanArmor", "vajra.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0] + this.BLOCK + this.DESCRIPTIONS[1] + this.BLOCK_PLUS + this.DESCRIPTIONS[2];
     }

     public void atBattleStart() {
         flash();
         AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ApplyPowerAction(
                 (AbstractCreature) AbstractDungeon.player, (AbstractCreature) AbstractDungeon.player,
                 (AbstractPower) new DeepDarkFantasyPower((AbstractCreature) AbstractDungeon.player, this.BLOCK, this.BLOCK_PLUS), 1));
     }

     public AbstractRelic makeCopy() {
         return new DarkVanArmor();
     }
 }


