package StarBreakerMod.ui;

import StarBreakerMod.StarBreakerMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public class SBMGeneralButton {
    public interface ButtonCallback{
        public void click(SBMGeneralButton btn);
    }

    private static final int W = 256;
    private static final int H = 256;
    private static final float HITBOX_W = 150.0F * Settings.scale;
    private static final float HITBOX_H = 100.0F * Settings.scale;
    private static final float TEXT_OFFSET_X = 0;
    private static final float TEXT_OFFSET_Y = 0;

    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.3F);
    private static final Color TEXT_DISABLED_COLOR = new Color(0.6F, 0.6F, 0.6F, 1.0F);
    private Color glowColor = Color.WHITE.cpy();
    private float glowAlpha = 0.0F;
    private float controller_offset_x = 0.0F;

    private boolean isHidden = false;
    public boolean isDisabled = false;
    public boolean isHovered = false;

    public Hitbox hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);

    public String buttonText = "NOT_SET";
    public BitmapFont buttonFont = FontHelper.tipBodyFont;

    private float current_x;
    private float init_x, init_y;
    private float hide_x = -100.0f;

    public ButtonCallback onClick;

    public SBMGeneralButton(float x, float y, String label, ButtonCallback onClick) {
        updateText(label);
        this.hb.move(x, y);
        current_x = x;
        init_x = x;
        init_y = y;
        this.onClick = onClick;
    }

    public void updateText(String label) {
        this.buttonText = label;
        this.controller_offset_x = FontHelper.getSmartWidth(this.buttonFont, label, 99999.0F, 0.0F) / 2.0F;
    }

    public void updateText(String label, BitmapFont font) {
        this.buttonText = label;
        this.buttonFont = font;
        this.controller_offset_x = FontHelper.getSmartWidth(this.buttonFont, label, 99999.0F, 0.0F) / 2.0F;
    }

    public boolean getIsHidden(){
        return isHidden;
    }

    public void update() {
        if (!this.isHidden) {
            updateGlow();
            this.hb.update();

            if (InputHelper.justClickedLeft && this.hb.hovered && !this.isDisabled) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if (this.hb.justHovered && !this.isDisabled) {
                CardCrawlGame.sound.play("UI_HOVER");
            }
            this.isHovered = this.hb.hovered;

            if (CInputActionSet.proceed.isJustPressed()) {
                CInputActionSet.proceed.unpress();
                this.hb.clicked = true;
            }

            if(this.hb.clicked){
                StarBreakerMod.logger.info("on click general button");
                this.onClick.click(this);
                this.hb.clicked = false;
            }
        }
    }

    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }
        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F + 0.3F;
        } else {
            this.glowColor.a = tmp / 2.0F + 0.3F;
        }
    }

    public void hideInstantly() {
        this.current_x = hide_x;
        this.isHidden = true;
    }

    public void hide() {
        if (!this.isHidden) {
            this.current_x = hide_x;
            this.isHidden = true;
        }
    }

    public void show() {
        if (this.isHidden) {
            this.glowAlpha = 0.0F;
            this.current_x = init_x;
            this.isHidden = false;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        renderButton(sb);

        if (this.hb.hovered && !this.isDisabled && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }


        if (this.isDisabled) {
            FontHelper.renderFontCentered(sb, this.buttonFont, this.buttonText, this.current_x + TEXT_OFFSET_X, init_y + TEXT_OFFSET_Y, TEXT_DISABLED_COLOR);


        } else if (this.hb.clickStarted) {
            FontHelper.renderFontCentered(sb, this.buttonFont, this.buttonText, this.current_x + TEXT_OFFSET_X, init_y + TEXT_OFFSET_Y, Color.LIGHT_GRAY);


        } else if (this.hb.hovered) {
            FontHelper.renderFontCentered(sb, this.buttonFont, this.buttonText, this.current_x + TEXT_OFFSET_X, init_y + TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);


        } else {


            FontHelper.renderFontCentered(sb, this.buttonFont, this.buttonText, this.current_x + TEXT_OFFSET_X, init_y + TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        }


        if (!this.isHidden) {
            this.hb.render(sb);
        }
    }

    private void renderButton(SpriteBatch sb) {
        sb.draw(ImageMaster.END_TURN_BUTTON, this.current_x - 128.0F, this.init_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
    }

}
