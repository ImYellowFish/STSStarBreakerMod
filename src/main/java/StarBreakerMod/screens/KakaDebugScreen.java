package StarBreakerMod.screens;

import StarBreakerMod.cards.kakaCards.*;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.patches.AbstractDungeonPatches;
import StarBreakerMod.relics.KakaDogTag;
import StarBreakerMod.ui.SBMGeneralButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    this.addButton("ClearKakaDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaDeck.clear();
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaStatEnergyCard(0, 2));
        dogTag.kakaDeck.addToBottom(new KakaStatDrawCard(0, 2));
      }
    });

    this.addButton("UpgradeKakaStat", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        for(AbstractCard c : dogTag.kakaDeck.group){
          KakaPlayableCard card = (KakaPlayableCard)c;
          if(card.kakaCardType == KakaPlayableCard.KakaCardType.BaseStat_Draw ||
            card.kakaCardType == KakaPlayableCard.KakaCardType.BaseStat_Energy){
            card.upgrade();
          }
        }
      }
    });

    this.addButton("HealKaka", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaData.currentHealth = dogTag.kakaData.maxHealth;
        if(dogTag.kaka != null){
          dogTag.kaka.heal(100);
        }
      }
    });

    this.addButton("AddStrDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaRitualFormCard());
        dogTag.kakaDeck.addToBottom(new KakaInFlameCard());
        dogTag.kakaDeck.addToBottom(new KakaLimitBreakCard());
        dogTag.kakaDeck.addToBottom(new KakaDoubleTapCard());
        dogTag.kakaDeck.addToBottom(new KakaSwordBoomerangCard());
        dogTag.kakaDeck.addToBottom(new KakaRiddleWithHolesCard());
        dogTag.kakaDeck.addToBottom(new KakaRagnarokCard());
      }
    });

    this.addButton("AddEchoForm", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaEchoFormCard());
      }
    });

    this.addButton("AddClashDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaClashCard());
        dogTag.kakaDeck.addToBottom(new KakaClashCard());
        dogTag.kakaDeck.addToBottom(new KakaRageCard());
        dogTag.kakaDeck.addToBottom(new KakaDoubleTapCard());
      }
    });

    this.addButton("AddWhirlWindDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaWhirlwindCard());
        dogTag.kakaDeck.addToBottom(new KakaAdrenalineCard());
        dogTag.kakaDeck.addToBottom(new KakaBloodLettingCard());
        dogTag.kakaDeck.addToBottom(new KakaOfferingCard());
        dogTag.kakaDeck.addToBottom(new KakaDeusExMachinaCard());
        dogTag.kakaDeck.addToBottom(new KakaTurboCard());
      }
    });

    this.addButton("AddEtherealDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaCarnageCard());
        dogTag.kakaDeck.addToBottom(new KakaRampageCard());
        dogTag.kakaDeck.addToBottom(new KakaCarnageCard());
        dogTag.kakaDeck.addToBottom(new KakaCarnageCard());
      }
    });

    this.addButton("AddZeroEnDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaImmolateCard());
        dogTag.kakaDeck.addToBottom(new KakaBackstabCard());
        dogTag.kakaDeck.addToBottom(new KakaBackstabCard());
        dogTag.kakaDeck.addToBottom(new KakaConsecrateCard());
        dogTag.kakaDeck.addToBottom(new KakaClawCard());
        dogTag.kakaDeck.addToBottom(new KakaClawCard());
        dogTag.kakaDeck.addToBottom(new KakaNeutralizeCard());
      }
    });

    this.addButton("AddPoisonDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaNoxiousFumesCard());
        dogTag.kakaDeck.addToBottom(new KakaCorpseExplosionCard());
        dogTag.kakaDeck.addToBottom(new KakaCripplingPoisonCard());
      }
    });

    this.addButton("AddPoisonDeck2", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaEnvenomCard());
        dogTag.kakaDeck.addToBottom(new KakaRiddleWithHolesCard());
        dogTag.kakaDeck.addToBottom(new KakaSwordBoomerangCard());
        dogTag.kakaDeck.addToBottom(new KakaPoisonedStabCard());
      }
    });

    this.addButton("AddDefDeck", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaFlameBarrierCard());
        dogTag.kakaDeck.addToBottom(new KakaBootSequenceCard());
        dogTag.kakaDeck.addToBottom(new KakaBodySlamCard());
        dogTag.kakaDeck.addToBottom(new KakaLegSweepCard());
        dogTag.kakaDeck.addToBottom(new KakaImperviousCard());
      }
    });

    this.addButton("AddDefDeck2", (btn) -> {
      if(KakaMinionManager.getInstance().dogTags.size() > 0){
        KakaDogTag dogTag = KakaMinionManager.getInstance().dogTags.get(0);
        dogTag.kakaData.alive = true;
        dogTag.usedUp = false;
        dogTag.kakaDeck.addToBottom(new KakaBarricadeCard());
        dogTag.kakaDeck.addToBottom(new KakaEntrenchCard());
        dogTag.kakaDeck.addToBottom(new KakaBootSequenceCard());
        dogTag.kakaDeck.addToBottom(new KakaBodySlamCard());
        dogTag.kakaDeck.addToBottom(new KakaImperviousCard());
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
