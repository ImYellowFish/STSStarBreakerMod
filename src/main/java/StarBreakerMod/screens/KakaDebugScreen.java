package StarBreakerMod.screens;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.patches.AbstractDungeonPatches;
import StarBreakerMod.relics.KakaDogTag;
import StarBreakerMod.ui.SBMGeneralButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;





public class KakaDebugScreen {
  public ArrayList<SBMGeneralButton> buttons = new ArrayList<>();

  private static final float BUTTON_START_X = Settings.WIDTH * 0.2F;
  private static final float BUTTON_START_Y = Settings.HEIGHT * 0.8F;
  private static final float BUTTON_OFFSET_X = Settings.WIDTH * 0.1F;
  private static final float BUTTON_OFFSET_Y = -Settings.HEIGHT * 0.1f;
  private static final int BUTTONS_PER_ROW = 6;

  // ----------------------------------------
  // Interface: Open
  // ----------------------------------------
  public void open() {
    AbstractDungeon.screen = AbstractDungeonPatches.SBM_KakaDebug;
    AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
  }

  public KakaDebugScreen() {
    this.addButton("AddKakaDrop", (btn) -> {
      KakaMinionManager.getInstance().AddRecruitableKakaReward();
    });

    this.addButton("AddLvPt", (btn) -> {
      for(KakaDogTag kakaDogTag : KakaMinionManager.getInstance().dogTags){
        kakaDogTag.kakaData.upgradePoint++;
      }
    });

    this.addButton("DecLvPt", (btn) -> {
      for(KakaDogTag kakaDogTag : KakaMinionManager.getInstance().dogTags){
        kakaDogTag.kakaData.upgradePoint = Math.max(0, kakaDogTag.kakaData.upgradePoint - 1);
      }
    });


    this.addButton(36, "Close", (btn) -> {
      closeSelf();
    });
  }

  public void closeSelf() {
    AbstractDungeon.overlayMenu.hideBlackScreen();
    AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
  }

  // ----------------------------------------
  // Interface: update & render
  // ----------------------------------------
  public void addButton(String text, SBMGeneralButton.ButtonCallback onClick){
    int currentIndex = this.buttons.size();
    int currentYi = currentIndex / BUTTONS_PER_ROW;
    int currentXi = currentIndex - currentYi * BUTTONS_PER_ROW;
    float currentX = BUTTON_START_X + currentXi * BUTTON_OFFSET_X;
    float currentY = BUTTON_START_Y + currentYi * BUTTON_OFFSET_Y;

    this.buttons.add(new SBMGeneralButton(currentX, currentY, text, onClick));
  }

  public void addButton(int index, String text, SBMGeneralButton.ButtonCallback onClick){
    int currentIndex = index;
    int currentYi = currentIndex / BUTTONS_PER_ROW;
    int currentXi = currentIndex - currentYi * BUTTONS_PER_ROW;
    float currentX = BUTTON_START_X + currentXi * BUTTON_OFFSET_X;
    float currentY = BUTTON_START_Y + currentYi * BUTTON_OFFSET_Y;

    this.buttons.add(new SBMGeneralButton(currentX, currentY, text, onClick));
  }

  public void update() {
    for (SBMGeneralButton btn : this.buttons) {
      btn.update();
    }
  }

  public void render(SpriteBatch sb) {
    for (SBMGeneralButton btn : this.buttons) {
      btn.render(sb);
    }
  }
}
