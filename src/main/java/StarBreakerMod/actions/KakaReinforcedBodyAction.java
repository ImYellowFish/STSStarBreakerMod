 package StarBreakerMod.actions;
 
 import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
 import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
 import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

 public class KakaReinforcedBodyAction extends AbstractGameAction {
     private boolean freeToPlayOnce = false;
     private int energyOnUse = -1;

     private AbstractCreature p;

     public KakaReinforcedBodyAction(AbstractCreature p, int amount, int energyOnUse) {
         this.p = p;
         this.duration = Settings.ACTION_DUR_XFAST;
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.energyOnUse = energyOnUse;
     }

     public void update() {
         int effect = this.energyOnUse;

         if (effect > 0) {
             for (int i = 0; i < effect; i++) {
                 addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)this.p, (AbstractCreature)this.p, this.amount));
             }
         }
         this.isDone = true;
     }
 }


