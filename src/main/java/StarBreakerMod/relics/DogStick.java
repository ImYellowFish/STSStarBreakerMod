 package StarBreakerMod.relics;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.actions.common.HealAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.FocusPower;
 import com.megacrit.cardcrawl.powers.StrengthPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;

 public class DogStick extends AbstractRelic {
     public static final String ID = "StarBreaker:DogStick";
     public static final float BLOCK_MULTIPLIER = 0.5F;

     public DogStick() {
         super("StarBreaker:DogStick", "nunchaku.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
         AbstractPlayer p = AbstractDungeon.player;
         if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL && target != p && canTriggerNow()){
             AbstractDungeon.actionManager.addToTop((AbstractGameAction)new GainBlockAction(p, p, (int)(damageAmount * BLOCK_MULTIPLIER)));
         }
     }

     public boolean canTriggerNow() {
         AbstractPlayer p = AbstractDungeon.player;
         for (AbstractCard c : p.hand.group) {
             if (c.type == AbstractCard.CardType.POWER || c.type == AbstractCard.CardType.SKILL) {
                 return false;
             }
         }
         return true;
     }

     public AbstractRelic makeCopy() {
         return new DogStick();
     }
 }