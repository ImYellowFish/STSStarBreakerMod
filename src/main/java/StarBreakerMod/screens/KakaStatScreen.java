package StarBreakerMod.screens;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.patches.AbstractDungeonPatches;
import StarBreakerMod.relics.KakaDogTag;
import StarBreakerMod.ui.KakaStatScreenCancelButton;
import StarBreakerMod.ui.SBMGeneralToggleButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import java.util.ArrayList;





public class KakaStatScreen
        implements ScrollBarListener {

  // ----------------------------------------
  // Constants
  // ----------------------------------------
  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("StarBreaker:KakaStatScreen");
  public static final String[] TEXT = uiStrings.TEXT;

  // ----------------------------------------
  // Draw variables
  // ----------------------------------------
  private static float drawStartX;
  private static float drawStartY;
  private static float padX;
  private static float padY;
  private static final int CARDS_PER_LINE = 5;
  private static final float SCROLL_BAR_THRESHOLD = 500.0F * Settings.scale;
  private float grabStartY = 0.0F;
  private float currentDiffY = 0.0F;
  private float scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
  private float scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
  private static final int ARROW_W = 64;
  private float arrowScale1 = 1.0F, arrowScale2 = 1.0F, arrowScale3 = 1.0F, arrowTimer = 0.0F;

  private float ritualAnimTimer = 0.0F;
  private static final float RITUAL_ANIM_INTERVAL = 0.1F;

  private static final float KAKA_STAT_START_X = Settings.WIDTH * 0.15F;
  private static final float KAKA_STAT_START_Y = Settings.HEIGHT * 0.75F;

  private static final float TOGGLE_DX = 150.0f;
  private static final float TOGGLE_DY = 500.0f;
  private static final float SCREEN_CENTER_Y = Settings.HEIGHT / 2.0F - 64.0F * Settings.scale;


  public GridSelectConfirmButton confirmButton = new GridSelectConfirmButton(TEXT[0]);
  public KakaStatScreenCancelButton cancelButton = new KakaStatScreenCancelButton(TEXT[1]);
  private ScrollBar scrollBar;

  // ----------------------------------------
  // UI States
  // ----------------------------------------
  // Mode
  private boolean canCancel = true;
  public boolean forUpgrade = false;
  public boolean forTransform = false;
  public boolean forPurge = false;
  public boolean forPreview = false;

  // Toggles
  // preview, upgrade, remove
  public SBMGeneralToggleButton[] toggles;

  // Misc
  public boolean isJustForConfirming = false;
  public boolean anyNumber = false;
  public boolean forClarity = false;
  private String tipMsg = "";

  // Card selection
  private int numCards = 0;
  private int cardSelectAmount = 0;
  public ArrayList<AbstractCard> selectedCards = new ArrayList<>();
  private AbstractCard hoveredCard = null;
  public AbstractCard upgradePreviewCard = null;
  private AbstractCard controllerCard = null;

  // Screen stats
  private boolean grabbedScreen = false;
  public boolean confirmScreenUp = false;

  // Reopen
  public boolean cancelWasOn = false;
  public String cancelText;
  private String lastTip = "";
  private int prevDeckSize = 0;


  // ----------------------------------------
  // Data
  // ----------------------------------------
  public KakaDogTag kakaDogTag;
  public CardGroup targetGroup;


  // ----------------------------------------
  // Interface: Open
  // ----------------------------------------
  public void openForKaka(KakaDogTag kakaDogTag) {
    StarBreakerMod.logger.info("Open Kaka Stat:" + kakaDogTag.name + ", " + kakaDogTag.kakaData.upgradePoint);
    this.kakaDogTag = kakaDogTag;
    open(kakaDogTag.kakaDeck, 1, TEXT[6], false, false, true, false);
    this.forPreview = true;
    this.cancelButton.isDisabled = false;
    this.cancelButton.show();
    this.createToggles();
  }

  public void openForKaka(KakaDogTag kakaDogTag, boolean forUpgrade, boolean forPurge) {
    StarBreakerMod.logger.info("Open Kaka Stat:" + kakaDogTag.name + ", " + kakaDogTag.kakaData.upgradePoint);
    openForKaka(kakaDogTag);
    this.forPreview = !(forPurge || forUpgrade);
    this.forPurge = forPurge;
    this.forUpgrade = forUpgrade;
  }

  public void open(CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge) {
    this.targetGroup = group;
    callOnOpen();


    this.forUpgrade = forUpgrade;
    this.forTransform = forTransform;
    this.canCancel = canCancel;
    this.forPurge = forPurge;
    this.tipMsg = tipMsg;
    this.numCards = numCards;

    if ((forUpgrade || forTransform || forPurge || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) && canCancel) {
      this.cancelButton.show();
    }

    if (!canCancel) {
      this.cancelButton.hide();
    }

    final float SHOW_X = 256.0F * Settings.scale;
    final float DRAW_Y = 128.0F * Settings.scale;
    this.cancelButton.updateText(TEXT[1]);

    calculateScrollBounds();
  }


  // ----------------------------------------
  // Interface: update & render
  // ----------------------------------------
  public KakaStatScreen() {
    drawStartX = Settings.WIDTH;
    drawStartX -= 5.0F * AbstractCard.IMG_WIDTH * 0.75F;
    drawStartX -= 4.0F * Settings.CARD_VIEW_PAD_X;
    drawStartX /= 2.0F;
    drawStartX += AbstractCard.IMG_WIDTH * 0.75F / 2.0F;

    padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
    padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
    this.scrollBar = new ScrollBar(this);
    this.scrollBar.move(0.0F, -30.0F * Settings.scale);
  }

  public void update() {
    updateControllerInput();
    for(SBMGeneralToggleButton t : this.toggles){
      t.update();
    }

    if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && this.upgradePreviewCard == null) {
      if (Gdx.input.getY() > Settings.HEIGHT * 0.75F) {
        this.currentDiffY += Settings.SCROLL_SPEED;
      } else if (Gdx.input.getY() < Settings.HEIGHT * 0.25F) {
        this.currentDiffY -= Settings.SCROLL_SPEED;
      }
    }

    boolean isDraggingScrollBar = false;
    if (shouldShowScrollBar()) {
      isDraggingScrollBar = this.scrollBar.update();
    }
    if (!isDraggingScrollBar) {
      updateScrolling();
    }

    if (this.forClarity) {
      if (this.selectedCards.size() > 0) {
        this.confirmButton.isDisabled = false;
      } else {
        this.confirmButton.isDisabled = true;
      }
    }

    this.confirmButton.update();
    this.cancelButton.update();
//    StarBreakerMod.logger.info("cancel button update:" + this.cancelButton.hb.clicked);


    if (this.cancelButton.hb.clicked) {
      this.cancelButton.hb.clicked = false;
      if (this.confirmScreenUp) {
        this.confirmScreenUp = false;
        this.cancelUpgrade();
      }
      else {
        resetOnToggle();
        closeSelf();
        return;
      }
    }

    if (this.isJustForConfirming) {
      updateCardPositionsAndHoverLogic();
      if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
        CInputActionSet.select.unpress();
        this.confirmButton.hb.clicked = false;
        AbstractDungeon.dynamicBanner.hide();
        this.confirmScreenUp = false;
        for (AbstractCard c : this.targetGroup.group) {
          AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, c.current_x, c.current_y));
        }
        closeSelf();
      }
      return;
    }
    if ((this.anyNumber || this.forClarity) &&
            this.confirmButton.hb.clicked) {
      this.confirmButton.hb.clicked = false;
      closeSelf();

      return;
    }

    if (!this.confirmScreenUp) {
      updateCardPositionsAndHoverLogic();

      if (this.hoveredCard != null && InputHelper.justClickedLeft) {
        this.hoveredCard.hb.clickStarted = true;
      }

      if (this.hoveredCard != null && (this.hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed())) {
        this.hoveredCard.hb.clicked = false;
        if (!this.selectedCards.contains(this.hoveredCard)) {
          if (this.forClarity && this.selectedCards.size() > 0) {
            ((AbstractCard) this.selectedCards.get(0)).stopGlowing();
            this.selectedCards.clear();
            this.cardSelectAmount--;
          }
          this.selectedCards.add(this.hoveredCard);
          this.hoveredCard.beginGlowing();
          this.hoveredCard.targetDrawScale = 0.75F;
          this.hoveredCard.drawScale = 0.875F;

          this.cardSelectAmount++;
          CardCrawlGame.sound.play("CARD_SELECT");

          if (this.numCards == this.cardSelectAmount) {
            if (this.forUpgrade) {

              this.hoveredCard.untip();
              this.confirmScreenUp = true;
              this.upgradePreviewCard = this.hoveredCard.makeStatEquivalentCopy();
              this.upgradePreviewCard.upgrade();
              this.upgradePreviewCard.displayUpgrades();
              this.upgradePreviewCard.drawScale = 0.875F;
              this.hoveredCard.stopGlowing();
              this.selectedCards.clear();
              this.cancelButton.show();
              this.confirmButton.show();
              this.confirmButton.isDisabled = !canKakaSmithCard(this.hoveredCard);
              this.lastTip = this.tipMsg;
              this.tipMsg = TEXT[2];
              return;
            }
            if (this.forTransform) {

              this.hoveredCard.untip();
              this.confirmScreenUp = true;
              this.upgradePreviewCard = this.hoveredCard.makeStatEquivalentCopy();
              this.upgradePreviewCard.drawScale = 0.875F;
              this.hoveredCard.stopGlowing();
              this.selectedCards.clear();
              this.cancelButton.show();
              this.confirmButton.show();
              this.confirmButton.isDisabled = !canKakaSmithCard(this.hoveredCard);
              this.lastTip = this.tipMsg;
              this.tipMsg = TEXT[2];
              return;
            }
            if (this.forPurge) {
              if (this.numCards == 1) {
                this.hoveredCard.untip();
                this.hoveredCard.stopGlowing();
                this.confirmScreenUp = true;
                this.hoveredCard.current_x = Settings.WIDTH / 2.0F;
                this.hoveredCard.target_x = Settings.WIDTH / 2.0F;
                this.hoveredCard.current_y = Settings.HEIGHT / 2.0F;
                this.hoveredCard.target_y = Settings.HEIGHT / 2.0F;
                this.hoveredCard.update();
                this.hoveredCard.targetDrawScale = 1.0F;
                this.hoveredCard.drawScale = 1.0F;
                this.selectedCards.clear();
                this.confirmButton.show();
                this.confirmButton.isDisabled = !canKakaSmithCard(this.hoveredCard);
                this.lastTip = this.tipMsg;
                this.tipMsg = TEXT[2];
                this.cancelButton.show();
              } else {
                closeSelf();
              }
              for (AbstractCard c : this.selectedCards)
                c.stopGlowing();
              return;
            }
            // For kaka
            // If kaka has no available upgrade points
            // Just support preview
            if (this.forPreview) {
              this.hoveredCard.untip();
              this.hoveredCard.stopGlowing();
              this.cancelButton.show();
              for (AbstractCard c : this.selectedCards)
                c.stopGlowing();
              CardCrawlGame.cardPopup.open(this.hoveredCard, this.kakaDogTag.kakaDeck);
              this.hoveredCard = null;
              this.cardSelectAmount = 0;
              this.selectedCards.clear();
              return;
            }

            if (!this.anyNumber) {

              closeSelf();
              if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.SHOP) {
                this.cancelButton.hide();
              } else {
                this.cancelButton.updateText(TEXT[3]);
                this.cancelButton.show();
              }

              for (AbstractCard c : this.selectedCards) {
                c.stopGlowing();
              }

              if (this.targetGroup.type == CardGroup.CardGroupType.DISCARD_PILE)
                for (AbstractCard c : this.targetGroup.group) {
                  c.drawScale = 0.12F;
                  c.targetDrawScale = 0.12F;
                  c.teleportToDiscardPile();
                  c.lighten(true);
                }
              return;
            }
            if (this.cardSelectAmount < this.targetGroup.size() && this.anyNumber) {
              closeSelf();
              if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.SHOP) {
                this.cancelButton.hide();
              } else {
                this.cancelButton.updateText(TEXT[3]);
                this.cancelButton.show();
              }

              for (AbstractCard c : this.selectedCards) {
                c.stopGlowing();
              }

              if (this.targetGroup.type == CardGroup.CardGroupType.DISCARD_PILE) {
                for (AbstractCard c : this.targetGroup.group) {
                  c.drawScale = 0.12F;
                  c.targetDrawScale = 0.12F;
                  c.teleportToDiscardPile();
                  c.lighten(true);
                }
              }
            }
          }
        } else if (this.selectedCards.contains(this.hoveredCard)) {
          this.hoveredCard.stopGlowing();
          this.selectedCards.remove(this.hoveredCard);
          this.cardSelectAmount--;
        }

        return;
      }
    } else {
      if (this.forTransform) {
        this.ritualAnimTimer -= Gdx.graphics.getDeltaTime();
        if (this.ritualAnimTimer < 0.0F) {
          this
                  .upgradePreviewCard = AbstractDungeon.returnTrulyRandomCardFromAvailable(this.upgradePreviewCard).makeCopy();
          this.ritualAnimTimer = 0.1F;
        }
      }

      if (this.forUpgrade) {
        this.upgradePreviewCard.update();
      }
      if (!this.forPurge) {
        if (this.upgradePreviewCard != null)
          this.upgradePreviewCard.drawScale = 1.0F;
        this.hoveredCard.update();
        this.hoveredCard.drawScale = 1.0F;
      }

      if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
        CInputActionSet.select.unpress();
        this.confirmButton.hb.clicked = false;
        this.cancelButton.hide();
        this.confirmScreenUp = false;
        this.selectedCards.add(this.hoveredCard);

        closeSelf();
      }
    }

    if (Settings.isControllerMode) {
      if (this.upgradePreviewCard != null) {
        CInputHelper.setCursor(this.upgradePreviewCard.hb);
      } else if (this.controllerCard != null) {
        CInputHelper.setCursor(this.controllerCard.hb);
      }
    }

  }


  public void render(SpriteBatch sb) {
    if (shouldShowScrollBar()) {
      this.scrollBar.render(sb);
    }

    if (!PeekButton.isPeeking) {
      if (this.hoveredCard != null) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
          this.targetGroup.renderExceptOneCard(sb, this.hoveredCard);
        } else {
          this.targetGroup.renderExceptOneCardShowBottled(sb, this.hoveredCard);
        }

        this.hoveredCard.renderHoverShadow(sb);
        this.hoveredCard.render(sb);

        if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
          // handle bottles
        }
        this.hoveredCard.renderCardTip(sb);
      } else if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
        this.targetGroup.render(sb);
      } else {
        this.targetGroup.renderShowBottled(sb);
      }
    }

    renderKakaStat(sb);

    if (this.confirmScreenUp) {
      sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
      sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT - 64.0F * Settings.scale);

      if (this.forTransform || this.forUpgrade) {
        renderArrows(sb);

        this.hoveredCard.current_x = Settings.WIDTH * 0.36F;
        this.hoveredCard.current_y = Settings.HEIGHT / 2.0F;
        this.hoveredCard.target_x = Settings.WIDTH * 0.36F;
        this.hoveredCard.target_y = Settings.HEIGHT / 2.0F;
        this.hoveredCard.render(sb);
        this.hoveredCard.updateHoverLogic();
        this.hoveredCard.renderCardTip(sb);


        this.upgradePreviewCard.current_x = Settings.WIDTH * 0.63F;
        this.upgradePreviewCard.current_y = Settings.HEIGHT / 2.0F;
        this.upgradePreviewCard.target_x = Settings.WIDTH * 0.63F;
        this.upgradePreviewCard.target_y = Settings.HEIGHT / 2.0F;
        this.upgradePreviewCard.render(sb);
        this.upgradePreviewCard.updateHoverLogic();
        this.upgradePreviewCard.renderCardTip(sb);
      } else {

        this.hoveredCard.current_x = Settings.WIDTH / 2.0F;
        this.hoveredCard.current_y = Settings.HEIGHT / 2.0F;
        this.hoveredCard.render(sb);
        this.hoveredCard.updateHoverLogic();
      }

      if(this.forPurge || this.forUpgrade){
        String msg = TEXT[14] + KakaMinionManager.getInstance().getKakaSmithCardCost(this.kakaDogTag, this.hoveredCard);
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, msg, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.3F,
                10000.0F, 34.8F * Settings.scale, Settings.CREAM_COLOR);
      }
    }

    if (!PeekButton.isPeeking && (
            this.forUpgrade || this.forTransform || this.forPurge || this.forPreview || this.isJustForConfirming || this.anyNumber || this.forClarity || this.forPreview)) {
      this.confirmButton.render(sb);
    }
    this.cancelButton.render(sb);

    if ((!this.isJustForConfirming || this.targetGroup.size() > 5) && !PeekButton.isPeeking) {
      FontHelper.renderDeckViewTip(sb, this.tipMsg, 96.0F * Settings.scale, Settings.CREAM_COLOR);
    }

    for(SBMGeneralToggleButton t : this.toggles){
      t.render(sb);
    }
  }

  // ----------------------------------------
  // Toggle utility
  // ----------------------------------------
  private void createToggles() {
    this.toggles = new SBMGeneralToggleButton[2];
    // preview
    this.toggles[0] = new SBMGeneralToggleButton(
            KAKA_STAT_START_X + TOGGLE_DX * 0,
            KAKA_STAT_START_Y + TOGGLE_DY,
            0,
            TEXT[4],
            (btn, enabled) -> {
              if (!this.confirmScreenUp && btn.enabled) {
                resetOnToggle();
                this.forPreview = true;
                btn.enabled = true;
                resetTargetGroup();
                return;
              }
              // cancel toggle op
              btn.enabled = !btn.enabled;
            }
    );
    this.toggles[0].enabled = this.forPreview;

    // upgrade
    this.toggles[1] = new SBMGeneralToggleButton(
            KAKA_STAT_START_X + TOGGLE_DX * 1,
            KAKA_STAT_START_Y + TOGGLE_DY,
            0,
            TEXT[7],
            (btn, enabled) -> {
              if (!this.confirmScreenUp && btn.enabled) {
                resetOnToggle();
                this.forUpgrade = true;
                btn.enabled = true;
                resetTargetGroup();
                return;
              }
              // cancel toggle op
              btn.enabled = !btn.enabled;
            }
    );
    this.toggles[1].enabled = this.forUpgrade;
  }

  private void resetOnToggle(){
    if(!this.confirmScreenUp) {
      this.forPreview = false;
      this.forUpgrade = false;
      this.forPurge = false;

      for (AbstractCard c : this.selectedCards)
        c.stopGlowing();
      if(this.hoveredCard != null)
        this.hoveredCard.stopGlowing();

      for (SBMGeneralToggleButton t : this.toggles)
        t.enabled = false;

      this.selectedCards.clear();
      this.cardSelectAmount = 0;
      this.hoveredCard = null;
    }
  }

  private void resetTargetGroup(){
    if(this.forUpgrade){
      this.targetGroup = this.kakaDogTag.kakaDeck.getUpgradableCards();
    }
    else if(this.forPreview){
      this.targetGroup = this.kakaDogTag.kakaDeck;
    }
    else if(this.forPurge){
      this.targetGroup = this.kakaDogTag.kakaDeck;
    }
  }

  // ----------------------------------------
  // Helpers
  // ----------------------------------------
  private boolean canKakaSmithCard(AbstractCard card){
    return KakaMinionManager.getInstance().canKakaSmithCard(kakaDogTag, card);
  }

  private void renderKakaStat(SpriteBatch sb){
    String msg = TEXT[9] + kakaDogTag.kakaData.name;
    msg += TEXT[10] + kakaDogTag.kakaData.level;
    msg += TEXT[11] + kakaDogTag.kakaData.currentHealth + TEXT[12] + kakaDogTag.kakaData.maxHealth;
    msg += TEXT[13] + kakaDogTag.kakaData.upgradePoint;
    FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, msg, KAKA_STAT_START_X, KAKA_STAT_START_Y,
            10000.0F, 34.8F * Settings.scale, Settings.CREAM_COLOR);
  }

  private void closeSelf(){
    // Hack, so the black screen can be cleaned up in AbstractDungeon
    AbstractDungeon.screen = AbstractDungeon.CurrentScreen.GRID;
    AbstractDungeon.closeCurrentScreen();
  }

  private void updateControllerInput() {
    if (!Settings.isControllerMode || this.upgradePreviewCard != null) {
      return;
    }

    boolean anyHovered = false;
    int index = 0;

    for (AbstractCard c : this.targetGroup.group) {
      if (c.hb.hovered) {
        anyHovered = true;
        break;
      }
      index++;
    }

    if (!anyHovered && this.controllerCard == null) {
      CInputHelper.setCursor(((AbstractCard) this.targetGroup.group.get(0)).hb);
      this.controllerCard = this.targetGroup.group.get(0);
    } else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && this.targetGroup
            .size() > 5) {

      if (index < 5) {
        index = this.targetGroup.size() + 2 - 4 - index;
        if (index > this.targetGroup.size() - 1) {
          index -= 5;
        }
        if (index > this.targetGroup.size() - 1 || index < 0) {
          index = 0;
        }
      } else {

        index -= 5;
      }
      CInputHelper.setCursor(((AbstractCard) this.targetGroup.group.get(index)).hb);
      this.controllerCard = this.targetGroup.group.get(index);
    } else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && this.targetGroup
            .size() > 5) {
      if (index < this.targetGroup.size() - 5) {
        index += 5;
      } else {
        index %= 5;
      }
      CInputHelper.setCursor(((AbstractCard) this.targetGroup.group.get(index)).hb);
      this.controllerCard = this.targetGroup.group.get(index);
    } else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
      if (index % 5 > 0) {
        index--;
      } else {
        index += 4;
        if (index > this.targetGroup.size() - 1) {
          index = this.targetGroup.size() - 1;
        }
      }
      CInputHelper.setCursor(((AbstractCard) this.targetGroup.group.get(index)).hb);
      this.controllerCard = this.targetGroup.group.get(index);
    } else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
      if (index % 5 < 4) {
        index++;
        if (index > this.targetGroup.size() - 1) {
          index -= this.targetGroup.size() % 5;
        }
      } else {
        index -= 4;
        if (index < 0) {
          index = 0;
        }
      }

      if (index > this.targetGroup.group.size() - 1) {
        index = 0;
      }
      CInputHelper.setCursor(((AbstractCard) this.targetGroup.group.get(index)).hb);
      this.controllerCard = this.targetGroup.group.get(index);
    }
  }


  private void updateCardPositionsAndHoverLogic() {
    if (this.isJustForConfirming && this.targetGroup.size() <= 4) {
      switch (this.targetGroup.size()) {
        case 1:
          (this.targetGroup.getBottomCard()).current_x = Settings.WIDTH / 2.0F;
          (this.targetGroup.getBottomCard()).target_x = Settings.WIDTH / 2.0F;
          break;
        case 2:
          ((AbstractCard) this.targetGroup.group.get(0)).current_x = Settings.WIDTH / 2.0F - padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(0)).target_x = Settings.WIDTH / 2.0F - padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(1)).current_x = Settings.WIDTH / 2.0F + padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(1)).target_x = Settings.WIDTH / 2.0F + padX / 2.0F;
          break;
        case 3:
          ((AbstractCard) this.targetGroup.group.get(0)).current_x = drawStartX + padX;
          ((AbstractCard) this.targetGroup.group.get(1)).current_x = drawStartX + padX * 2.0F;
          ((AbstractCard) this.targetGroup.group.get(2)).current_x = drawStartX + padX * 3.0F;
          ((AbstractCard) this.targetGroup.group.get(0)).target_x = drawStartX + padX;
          ((AbstractCard) this.targetGroup.group.get(1)).target_x = drawStartX + padX * 2.0F;
          ((AbstractCard) this.targetGroup.group.get(2)).target_x = drawStartX + padX * 3.0F;
          break;
        case 4:
          ((AbstractCard) this.targetGroup.group.get(0)).current_x = Settings.WIDTH / 2.0F - padX / 2.0F - padX;
          ((AbstractCard) this.targetGroup.group.get(0)).target_x = Settings.WIDTH / 2.0F - padX / 2.0F - padX;
          ((AbstractCard) this.targetGroup.group.get(1)).current_x = Settings.WIDTH / 2.0F - padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(1)).target_x = Settings.WIDTH / 2.0F - padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(2)).current_x = Settings.WIDTH / 2.0F + padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(2)).target_x = Settings.WIDTH / 2.0F + padX / 2.0F;
          ((AbstractCard) this.targetGroup.group.get(3)).current_x = Settings.WIDTH / 2.0F + padX / 2.0F + padX;
          ((AbstractCard) this.targetGroup.group.get(3)).target_x = Settings.WIDTH / 2.0F + padX / 2.0F + padX;
          break;
      }

      ArrayList<AbstractCard> c2 = this.targetGroup.group;

      for (int j = 0; j < c2.size(); j++) {
        ((AbstractCard) c2.get(j)).target_y = drawStartY + this.currentDiffY;
        ((AbstractCard) c2.get(j)).fadingOut = false;
        ((AbstractCard) c2.get(j)).update();
        ((AbstractCard) c2.get(j)).updateHoverLogic();

        this.hoveredCard = null;
        for (AbstractCard c : c2) {
          if (c.hb.hovered) {
            this.hoveredCard = c;
          }
        }
      }


      return;
    }

    int lineNum = 0;
    ArrayList<AbstractCard> cards = this.targetGroup.group;
    for (int i = 0; i < cards.size(); i++) {
      int mod = i % 5;
      if (mod == 0 && i != 0) {
        lineNum++;
      }
      ((AbstractCard) cards.get(i)).target_x = drawStartX + mod * padX;
      ((AbstractCard) cards.get(i)).target_y = drawStartY + this.currentDiffY - lineNum * padY;
      ((AbstractCard) cards.get(i)).fadingOut = false;
      ((AbstractCard) cards.get(i)).update();
      ((AbstractCard) cards.get(i)).updateHoverLogic();

      this.hoveredCard = null;
      for (AbstractCard c : cards) {
        if (c.hb.hovered) {
          this.hoveredCard = c;
        }
      }
    }
  }

  public void openConfirmationGrid(CardGroup group, String tipMsg) {
    this.targetGroup = group;
    callOnOpen();


    this.isJustForConfirming = true;
    this.tipMsg = tipMsg;


    this.cancelButton.hideInstantly();


    this.confirmButton.show();
    this.confirmButton.updateText(TEXT[0]);
    this.confirmButton.isDisabled = !canKakaSmithCard(this.hoveredCard);

    this.canCancel = false;

    if (group.size() <= 5) {
      AbstractDungeon.dynamicBanner.appear(tipMsg);
    }
  }

  private void callOnOpen() {
    if (Settings.isControllerMode) {
      Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
      this.controllerCard = null;
    }

    this.anyNumber = false;
    this.forClarity = false;
    this.canCancel = false;
    this.forUpgrade = false;
    this.forTransform = false;
    this.forPurge = false;
    this.confirmScreenUp = false;
    this.isJustForConfirming = false;
    AbstractDungeon.overlayMenu.proceedButton.hide();
    this.controllerCard = null;
    this.hoveredCard = null;
    this.selectedCards.clear();
    AbstractDungeon.topPanel.unhoverHitboxes();
    this.cardSelectAmount = 0;
    this.currentDiffY = 0.0F;
    this.grabStartY = 0.0F;
    this.grabbedScreen = false;
    hideCards();
    AbstractDungeon.isScreenUp = true;
    AbstractDungeon.screen = AbstractDungeonPatches.SBM_KakaStat;
    AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
    this.confirmButton.hideInstantly();
    if (this.targetGroup.group.size() <= 5) {
      drawStartY = Settings.HEIGHT * 0.5F;
    } else {
      drawStartY = Settings.HEIGHT * 0.66F;
    }

  }

  public void reopen() {
    AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
    AbstractDungeon.isScreenUp = true;
    AbstractDungeon.screen = AbstractDungeonPatches.SBM_KakaStat;
    AbstractDungeon.topPanel.unhoverHitboxes();
    this.cancelButton.show();
    for (AbstractCard c : this.targetGroup.group) {
      c.targetDrawScale = 0.75F;
      c.drawScale = 0.75F;
      c.lighten(false);
    }
    this.cardSelectAmount = 0;
    this.numCards = 1;
    this.scrollBar.reset();
  }

  public void hide() {
//    if (!this.cancelButton.isHidden) {
//      this.cancelWasOn = true;
//      this.cancelText = AbstractDungeon.overlayMenu.cancelButton.buttonText;
//    }
  }

  private void updateScrolling() {
    if (PeekButton.isPeeking) {
      return;
    }


    if (this.isJustForConfirming && this.targetGroup.size() <= 5) {
      this.currentDiffY = -64.0F * Settings.scale;

      return;
    }
    int y = InputHelper.mY;
    boolean isDraggingScrollBar = this.scrollBar.update();

    if (!isDraggingScrollBar) {
      if (!this.grabbedScreen) {
        if (InputHelper.scrolledDown) {
          this.currentDiffY += Settings.SCROLL_SPEED;
        } else if (InputHelper.scrolledUp) {
          this.currentDiffY -= Settings.SCROLL_SPEED;
        }

        if (InputHelper.justClickedLeft) {
          this.grabbedScreen = true;
          this.grabStartY = y - this.currentDiffY;
        }

      } else if (InputHelper.isMouseDown) {
        this.currentDiffY = y - this.grabStartY;
      } else {
        this.grabbedScreen = false;
      }
    }


    if (this.prevDeckSize != this.targetGroup.size()) {
      calculateScrollBounds();
    }
    resetScrolling();
    updateBarPosition();
  }


  private void calculateScrollBounds() {
    int scrollTmp = 0;
    if (this.targetGroup.size() > 10) {
      scrollTmp = this.targetGroup.size() / 5 - 2;
      if (this.targetGroup.size() % 5 != 0) {
        scrollTmp++;
      }
      this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * padY;
    } else {
      this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
    }

    this.prevDeckSize = this.targetGroup.size();
  }


  private void resetScrolling() {
    if (this.currentDiffY < this.scrollLowerBound) {
      this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
    } else if (this.currentDiffY > this.scrollUpperBound) {
      this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
    }
  }

  private void hideCards() {
    int lineNum = 0;
    ArrayList<AbstractCard> cards = this.targetGroup.group;
    for (int i = 0; i < cards.size(); i++) {
      ((AbstractCard) cards.get(i)).setAngle(0.0F, true);
      int mod = i % 5;
      if (mod == 0 && i != 0) {
        lineNum++;
      }

      ((AbstractCard) cards.get(i)).lighten(true);
      ((AbstractCard) cards.get(i)).current_x = drawStartX + mod * padX;
      ((AbstractCard) cards.get(i)).current_y = drawStartY + this.currentDiffY - lineNum * padY - MathUtils.random(100.0F * Settings.scale, 200.0F * Settings.scale);


      ((AbstractCard) cards.get(i)).targetDrawScale = 0.75F;
      ((AbstractCard) cards.get(i)).drawScale = 0.75F;
    }
  }

  public void cancelUpgrade() {
    this.cardSelectAmount = 0;
    this.confirmScreenUp = false;
    this.confirmButton.hide();
    this.confirmButton.isDisabled = true;
    this.hoveredCard = null;
    this.upgradePreviewCard = null;

    if (Settings.isControllerMode && this.controllerCard != null) {
      this.hoveredCard = this.controllerCard;
      CInputHelper.setCursor(this.hoveredCard.hb);
    }

    if ((this.forUpgrade || this.forTransform || this.forPurge || this.forPreview || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) && this.canCancel) {
      this.cancelButton.show();
    }


    int lineNum = 0;
    ArrayList<AbstractCard> cards = this.targetGroup.group;
    for (int i = 0; i < cards.size(); i++) {
      int mod = i % 5;
      if (mod == 0 && i != 0) {
        lineNum++;
      }
      ((AbstractCard) cards.get(i)).current_x = drawStartX + mod * padX;
      ((AbstractCard) cards.get(i)).current_y = drawStartY + this.currentDiffY - lineNum * padY;
    }

    this.tipMsg = this.lastTip;
  }

  private void renderArrows(SpriteBatch sb) {
    float x = Settings.WIDTH / 2.0F - 73.0F * Settings.scale - 32.0F;
    sb.setColor(Color.WHITE);
    sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale1 * Settings.scale, this.arrowScale1 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);


    x += 64.0F * Settings.scale;
    sb.setColor(Color.WHITE);
    sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale2 * Settings.scale, this.arrowScale2 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);


    x += 64.0F * Settings.scale;

    sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.arrowScale3 * Settings.scale, this.arrowScale3 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);


    this.arrowTimer += Gdx.graphics.getDeltaTime() * 2.0F;
    this.arrowScale1 = 0.8F + (MathUtils.cos(this.arrowTimer) + 1.0F) / 8.0F;
    this.arrowScale2 = 0.8F + (MathUtils.cos(this.arrowTimer - 0.8F) + 1.0F) / 8.0F;
    this.arrowScale3 = 0.8F + (MathUtils.cos(this.arrowTimer - 1.6F) + 1.0F) / 8.0F;
  }


  public void scrolledUsingBar(float newPercent) {
    this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
    updateBarPosition();
  }

  private void updateBarPosition() {
    float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
    this.scrollBar.parentScrolledToPercent(percent);
  }

  private boolean shouldShowScrollBar() {
    return (!this.confirmScreenUp && this.scrollUpperBound > SCROLL_BAR_THRESHOLD && !PeekButton.isPeeking);
  }
}
