 package StarBreakerMod.actions;
 
 import StarBreakerMod.cards.kakaCards.KakaClawCard;
 import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
 import StarBreakerMod.minions.AbstractFriendlyMonster;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.ai.DefaultKakaAI;
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
 
 public class KakaGashAction extends AbstractGameAction {
     private AbstractCard card;

     public KakaGashAction(AbstractCreature source, AbstractCard card, int amount) {
         this.source = source;
         this.card = card;
         this.amount = amount;
     }

     public void update() {
         BaseFriendlyKaka kaka = (BaseFriendlyKaka)this.source;
         this.card.baseDamage += this.amount;

         for(AbstractCard c : ((DefaultKakaAI)kaka.AI).keyOffensiveCardPile.group){
             if(c instanceof KakaClawCard){
                 c.baseDamage += this.amount;
             }
         }

         for(AbstractCard c : ((DefaultKakaAI)kaka.AI).optionalOffensiveCardPile.group){
             if(c instanceof KakaClawCard){
                 c.baseDamage += this.amount;
             }
         }

         this.isDone = true;
     }
 }

