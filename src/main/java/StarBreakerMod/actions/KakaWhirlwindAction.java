 package StarBreakerMod.actions;
 
 import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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

 public class KakaWhirlwindAction extends AbstractGameAction {
     public int[] multiDamage;
     private boolean freeToPlayOnce = false;
     private int energyOnUse = -1;
     private DamageInfo.DamageType damageType;

     private AbstractCreature p;

     public KakaWhirlwindAction(AbstractCreature p, int[] multiDamage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse) {
         this.multiDamage = multiDamage;
         this.damageType = damageType;
         this.p = p;
         this.freeToPlayOnce = freeToPlayOnce;
         this.duration = Settings.ACTION_DUR_XFAST;
         this.actionType = AbstractGameAction.ActionType.SPECIAL;
         this.energyOnUse = energyOnUse;
     }

     public void update() {
         int effect = this.energyOnUse;

         if (effect > 0) {
             for (int i = 0; i < effect; i++) {
                 if (i == 0) {
                     addToBot((AbstractGameAction) new SFXAction("ATTACK_WHIRLWIND"));
                     addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new WhirlwindEffect(), 0.0F));
                 }

                 addToBot((AbstractGameAction) new SFXAction("ATTACK_HEAVY"));
                 addToBot((AbstractGameAction) new VFXAction((AbstractCreature) this.p, (AbstractGameEffect) new CleaveEffect(), 0.0F));
                 addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) this.p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));
             }
         }
         this.isDone = true;
     }
 }


