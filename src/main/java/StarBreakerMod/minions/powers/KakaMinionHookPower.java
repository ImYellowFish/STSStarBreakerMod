 package StarBreakerMod.minions.powers;
 
 import StarBreakerMod.minions.system.KakaMinionManager;
 import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
 import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 // Not visible, used to provide some hooks to the KakaMinionManager
 public class KakaMinionHookPower extends AbstractPower implements InvisiblePower, OnPlayerDeathPower {
     public static final String POWER_ID = "KakaMinionHookPower";

     public KakaMinionHookPower(AbstractCreature owner) {
         this.name = "KakaMinionHookPower";
         this.ID = "KakaMinionHookPower";
         this.owner = owner;
         updateDescription();
         loadRegion("ritual");
     }

     public void updateDescription() {
         this.description = "KakaMinionHookPower";
     }

     public boolean onPlayerDeath(AbstractPlayer player, DamageInfo damageInfo){
         if(isOwnerPlayer()) {
             return KakaMinionManager.getInstance(player).onPlayerDeath(damageInfo);
         }

         // todo block damage for player
         return false;
     }


     public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
         KakaMinionManager.getInstance(AbstractDungeon.player).addAggro(
                 this.owner, damageAmount * KakaMinionManager.AGGRO_PER_DAMAGE);
     }

     private boolean isOwnerPlayer(){
         return AbstractDungeon.player == this.owner;
     }
 }
