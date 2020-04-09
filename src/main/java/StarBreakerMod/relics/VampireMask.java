 package StarBreakerMod.relics;
 
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.HealAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.FocusPower;
 import com.megacrit.cardcrawl.powers.StrengthPower;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.rooms.AbstractRoom;

 public class VampireMask extends AbstractRelic {
     public static final String ID = "StarBreaker:VampireMask";

     public VampireMask() {
         super("StarBreaker:VampireMask", "nloth.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
         this.counter = 0;
     }

     public String getUpdatedDescription() {
         return this.DESCRIPTIONS[0];
     }

     public void atBattleStart() {
         if (this.counter > 0)
             AddPower(this.counter);
     }

     public void onVictory() {
         this.counter = 0;
     }

     public int onPlayerHeal(int healAmount) {
         AbstractPlayer p = AbstractDungeon.player;
         int abundantHeal = healAmount + p.currentHealth - p.maxHealth;
         if (abundantHeal > 0) {
             this.counter += abundantHeal;
             AddPower(abundantHeal);
         }
         return healAmount;
     }

     public void AddPower(int amount) {
         if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
             AbstractPlayer p = AbstractDungeon.player;
             AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ApplyPowerAction(p, p, (new StrengthPower(p, amount)), amount));
             if (p.masterMaxOrbs > 0) {
                 AbstractDungeon.actionManager.addToTop((AbstractGameAction) new ApplyPowerAction(p, p, (new FocusPower(p, amount)), amount));
             }
         }
     }

     public AbstractRelic makeCopy() {
         return new VampireMask();
     }
 }