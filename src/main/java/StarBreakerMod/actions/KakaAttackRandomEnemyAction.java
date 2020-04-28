 package StarBreakerMod.actions;
 
 import StarBreakerMod.minions.cards.KakaPlayableCard;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
 
 public class KakaAttackRandomEnemyAction extends AbstractGameAction {
     private AbstractCard card;

     public KakaAttackRandomEnemyAction(AbstractCreature source, AbstractCard card, AbstractGameAction.AttackEffect effect) {
         this.card = card;
         this.effect = effect;
         this.source = source;
     }

     private AbstractGameAction.AttackEffect effect;

     public void update() {
         this.target = (AbstractCreature) AbstractDungeon.getMonsters().getRandomMonster(null, true, KakaMinionManager.getInstance().cardRandomRng);
         if (this.target != null) {
             ((KakaPlayableCard)(this.card)).calculateKakaCardDamage((BaseFriendlyKaka) this.source, (AbstractMonster) this.target);
             if (AbstractGameAction.AttackEffect.LIGHTNING == this.effect) {
                 addToTop(new DamageAction(this.target, new DamageInfo(this.source, this.card.damage, this.card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));


                 addToTop((AbstractGameAction) new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                 addToTop((AbstractGameAction) new VFXAction((AbstractGameEffect) new LightningEffect(this.target.hb.cX, this.target.hb.cY)));
             } else {
                 addToTop(new DamageAction(this.target, new DamageInfo(this.source, this.card.damage, this.card.damageTypeForTurn), this.effect));
             }
         }


         this.isDone = true;
     }
 }

