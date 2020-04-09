 package StarBreakerMod.relics;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.HealAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class BloodBoot extends AbstractRelic {
     public static final String ID = "StarBreaker:BloodBoot";
     private static final int THRESHOLD = 5;

     public BloodBoot() {
         super("StarBreaker:BloodBoot", "boot.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
     }


     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0] + '\005' + this.DESCRIPTIONS[1];
     }

     public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
         AbstractPlayer p = AbstractDungeon.player;
         if (p == info.owner && damageAmount <= this.THRESHOLD && info.type == DamageInfo.DamageType.NORMAL) {
             AbstractDungeon.actionManager.addToTop((AbstractGameAction) new HealAction(p, p, damageAmount));
         }
     }

     public AbstractRelic makeCopy() {
         return new BloodBoot();
     }
 }