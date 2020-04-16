 package StarBreakerMod.relics;
 import basemod.abstracts.CustomRelic;
 import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class TestSummonRelic extends CustomRelic implements ClickableRelic{
     public static final String ID = "StarBreaker:TestSummonRelic";
     // public static final String IMG_PATH = "StarBreakerImages/scrapOoze.png";
    public static final int USE_PER_BATTLE = 5;

     public TestSummonRelic() {
         super("StarBreaker:TestSummonRelic", "abacus.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
         this.counter = 0;
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     @Override
     public void atBattleStartPreDraw() {
         this.counter = USE_PER_BATTLE;
     }

     public void onRightClick() {
         flash();
     }

     public AbstractRelic makeCopy() {
         return new TestSummonRelic();
     }
 }


