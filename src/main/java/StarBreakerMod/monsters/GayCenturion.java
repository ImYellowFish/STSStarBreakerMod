 package StarBreakerMod.monsters;
 import StarBreakerMod.StarBreakerMod;
 import com.badlogic.gdx.math.MathUtils;
 import com.esotericsoftware.spine.AnimationState;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.ShoutAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.common.RollMoveAction;
 import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.actions.utility.WaitAction;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.CardLibrary;
 import com.megacrit.cardcrawl.localization.MonsterStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.monsters.city.Centurion;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import StarBreakerMod.powers.KindnessStabsPower;

 public class GayCenturion extends Centurion {
     public static final String ID = "GayCenturion";

     private int turnsTaken = 0;
     private static int damageToHealer = 25;

     public GayCenturion(float x, float y) {
         super(x, y);
         this.damage.add(new DamageInfo((AbstractCreature)this, this.damageToHealer));
     }

     @Override
     public void takeTurn(){
         this.turnsTaken++;
         // Kill healer on the first turn
         if(this.nextMove == 4) {
             AbstractCreature healer = findHealer();
             if(healer != null) {
                 StarBreakerMod.logger.info("hit healer.");
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new SFXAction("VO_TANK_1A"));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ChangeStateAction(this, "MACE_HIT"));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new WaitAction(0.3F));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new DamageAction((AbstractCreature) healer, this.damage
                         .get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ShoutAction((AbstractCreature)this, "WHO NEEDS GIRLFRIEND???", 2.0F, 3.0F));
             }
         }
         super.takeTurn();
     }

     @Override
     protected void getMove(int num) {
         // Attack the healer on the first turn
        if(this.turnsTaken == 0){
            setMove((byte)4, AbstractMonster.Intent.UNKNOWN);
            return;
        }

        if(num < 25 && !lastTwoMoves((byte)1)){
            setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
         }
        else if(num < 50 && !lastTwoMoves((byte)2)){
            setMove((byte)2, AbstractMonster.Intent.DEFEND);
        }
        else{
            setMove((byte)3, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, 3, true);
        }
     }

     private AbstractCreature findHealer() {
         for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
             if (!m.isDying && !m.isEscaping && m.id == "Healer") {
                 return m;
             }
         }
         return null;
     }
 }
