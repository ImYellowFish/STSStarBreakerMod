 package StarBreakerMod.powers;
 
 import StarBreakerMod.minions.KakaMinionManager;
 import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
 import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.AbstractCreature;
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
        return KakaMinionManager.getInstance(player).onPlayerDeath(damageInfo);
     }
 }
