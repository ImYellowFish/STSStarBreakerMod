 package StarBreakerMod.relics;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import basemod.abstracts.CustomRelic;
 import basemod.abstracts.CustomSavable;
 import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class KakaCommanderBadge extends CustomRelic implements ClickableRelic {
     public static final String ID = "StarBreaker:KakaCommanderBadge";

//     public KakaTeamData teamData;

     public KakaCommanderBadge() {
         super("StarBreaker:KakaCommanderBadge", "clericFace.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.SOLID);
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     // Battle
     @Override
     public void atBattleStartPreDraw() {
        // Do something here
        // TODO: spawn kaka hostage
     }

     public void onRightClick() {
         // Test: spawn a kaka dogTag rewardItem
         flash();
         KakaMinionManager.getInstance(AbstractDungeon.player).AddRecruitableKakaReward();
     }

    // Team management


     // Helpers
     public AbstractRelic makeCopy() {
         return new KakaCommanderBadge();
     }
 }


