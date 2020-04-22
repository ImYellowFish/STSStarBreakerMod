 package StarBreakerMod.ui;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.FontHelper;
 import com.megacrit.cardcrawl.helpers.Hitbox;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
 import com.megacrit.cardcrawl.helpers.input.InputHelper;

 public class SBMGeneralToggleButton {
   public interface ToggleButtonCallback {
     public void toggle(SBMGeneralToggleButton btn, boolean enabled);
   }

   private static final int W = 32;
   private float x;
   private float y;
   private ToggleButtonCallback onToggle;

   public String text;
   public Hitbox hb;
   public boolean enabled = true;

   public SBMGeneralToggleButton(float x, float y, float middleY, String text, ToggleButtonCallback onToggle) {
     this.x = x;
     this.y = middleY + y * Settings.scale;
     this.hb = new Hitbox(200.0F * Settings.scale, 32.0F * Settings.scale);
     this.hb.move(x, this.y);
     this.enabled = false;
     this.onToggle = onToggle;
     this.text = text;
   }

   public SBMGeneralToggleButton(float x, float y, float middleY, boolean isLarge, String text, ToggleButtonCallback onToggle) {
     this.x = x;
     this.y = middleY + y * Settings.scale;
     if (isLarge) {
       this.hb = new Hitbox(480.0F * Settings.scale, 32.0F * Settings.scale);
       this.hb.move(x, this.y);
     } else {
       this.hb = new Hitbox(240.0F * Settings.scale, 32.0F * Settings.scale);
       this.hb.move(x, this.y);
     }
     this.enabled = false;
     this.onToggle = onToggle;
     this.text = text;
   }

   public void update() {
     this.hb.update();
     if (this.hb.hovered && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())) {
       InputHelper.justClickedLeft = false;
       this.enabled = !this.enabled;
       this.onToggle.toggle(this, this.enabled);
     }
   }


   public void render(SpriteBatch sb) {
     if (this.enabled) {
       sb.setColor(Color.LIGHT_GRAY);
     } else {
       sb.setColor(Color.WHITE);
     }

     float scale = Settings.scale;
     if (this.hb.hovered) {
       sb.setColor(Color.SKY);
       scale = Settings.scale * 1.25F;
     }

     sb.draw(ImageMaster.OPTION_TOGGLE, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, scale, scale, 0.0F, 0, 0, 32, 32, false, false);


     if (this.enabled) {
       sb.setColor(Color.WHITE);
       sb.draw(ImageMaster.OPTION_TOGGLE_ON, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, scale, scale, 0.0F, 0, 0, 32, 32, false, false);
     }


     this.hb.render(sb);

     // render text
     FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, this.text, this.x + 20.0f, this.y, 10000.0F, 34.8F * Settings.scale, Settings.CREAM_COLOR);
   }
 }