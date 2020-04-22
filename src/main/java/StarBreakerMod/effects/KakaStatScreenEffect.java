 package StarBreakerMod.effects;
 
 import StarBreakerMod.minions.system.KakaMinionManager;
 import StarBreakerMod.relics.KakaDogTag;
 import StarBreakerMod.screens.KakaStatScreen;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.math.Interpolation;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.rooms.RestRoom;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
 import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
 
 
 
 
 public class KakaStatScreenEffect
   extends AbstractGameEffect {
//     private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("StarBreaker:KakaStatScreenEffect");
//     public static final String[] TEXT = uiStrings.TEXT;

     private static final float DUR = 1.1F;
     private boolean openedScreen = false;


     public KakaDogTag kakaDogTag;


     public KakaStatScreenEffect(KakaDogTag kakaDogTag) {
         this.kakaDogTag = kakaDogTag;
         this.duration = DUR;
//     AbstractDungeon.overlayMenu.proceedButton.hide();
     }


     public void update() {
         KakaStatScreen screen = KakaMinionManager.getKakaStatScreen();

         if (!AbstractDungeon.isScreenUp) {
             this.duration -= Gdx.graphics.getDeltaTime();
//             updateBlackScreenColor();
         }


         if (!AbstractDungeon.isScreenUp && !screen.selectedCards.isEmpty() && screen.forUpgrade) {

             for (AbstractCard c : screen.selectedCards) {
                 boolean result = KakaMinionManager.getInstance().onTrySmithKakaCard(this.kakaDogTag, c);
                 if(result) {
                     AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                     CardCrawlGame.metricData.addCampfireChoiceData("KAKA_SMITH", c.getMetricID());
                     c.upgrade();
                     AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                 }
             }
             screen.selectedCards.clear();
         }


         if (this.duration < 1.0F && !this.openedScreen) {
             this.openedScreen = true;
             KakaMinionManager.getKakaStatScreen().openForKaka(this.kakaDogTag);
         }


         if (this.duration < 0.0F) {
             if(this.kakaDogTag.kakaData.upgradePoint > 0 && (screen.forUpgrade || screen.forPurge)){
                 // If there are still cards to upgrade, keep open
                 this.duration = DUR;
                 this.openedScreen = true;
                 KakaMinionManager.getKakaStatScreen().openForKaka(this.kakaDogTag, screen.forUpgrade, screen.forPurge);
             }else {
                 this.isDone = true;
             }
         }
     }


     public void render(SpriteBatch sb) {
//         KakaStatScreen screen = KakaMinionManager.getKakaStatScreen();

//         sb.setColor(this.screenColor);
//         sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
//
//         if (AbstractDungeon.screen == AbstractDungeonPatches.SBM_KakaStat)
//             screen.render(sb);
     }

     public void dispose() {
     }
 }
