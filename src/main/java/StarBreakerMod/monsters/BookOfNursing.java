 package StarBreakerMod.monsters;
 import StarBreakerMod.StarBreakerMod;
 import com.badlogic.gdx.math.MathUtils;
 import com.esotericsoftware.spine.AnimationState;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import StarBreakerMod.powers.KindnessStabsPower;

 public class BookOfNursing extends AbstractMonster {
     public static final String ID = "BookOfNursing";
     private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("StarBreaker:BookOfNursing");
     public static final String NAME = monsterStrings.NAME;
     public static final String[] MOVES = monsterStrings.MOVES;
     public static final String[] DIALOG = monsterStrings.DIALOG;

     private static final int HP_MIN = 50;

     private static final int HP_MAX = 55;

     private static final int A_2_HP_MIN = 55;
     private static final int A_2_HP_MAX = 60;
     private static final int STAB_DAMAGE = 2;
     private static final int BIG_STAB_DAMAGE = 10;
     private static final int A_2_STAB_DAMAGE = 3;
     private static final int A_2_BIG_STAB_DAMAGE = 12;
     private static final byte STAB = 1;
     private static final byte SHUFFLE = 2;

     private int stabCount = 1;
     private int maxStabCount = 6;
     private int turnsTaken = 0;
     private int stabDmg;
     private int bigStabDmg;

     public BookOfNursing() {
         super(NAME, ID, HP_MAX, 0.0F, -10.0F, 320.0F, 420.0F, null, 0.0F, 5.0F);
         loadAnimation("images/monsters/theCity/stabBook/skeleton.atlas", "images/monsters/theCity/stabBook/skeleton.json", 2.0F);


         AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
         e.setTime(e.getEndTime() * MathUtils.random());
         this.stateData.setMix("Hit", "Idle", 0.2F);
         e.setTimeScale(0.8F);

         this.type = AbstractMonster.EnemyType.ELITE;
         this.dialogX = -70.0F * Settings.scale;
         this.dialogY = 50.0F * Settings.scale;

         if (AbstractDungeon.ascensionLevel >= 8) {
             setHp(HP_MIN, HP_MAX);
         } else {
             setHp(A_2_HP_MIN, A_2_HP_MAX);
         }

         if (AbstractDungeon.ascensionLevel >= 3) {
             this.stabDmg = STAB_DAMAGE;
             this.bigStabDmg = BIG_STAB_DAMAGE;
         } else {
             this.stabDmg = A_2_STAB_DAMAGE;
             this.bigStabDmg = A_2_BIG_STAB_DAMAGE;
         }

         this.damage.add(new DamageInfo((AbstractCreature) this, this.stabDmg));
         this.damage.add(new DamageInfo((AbstractCreature) this, this.bigStabDmg));
     }


     public void usePreBattleAction() {
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction((AbstractCreature) this, (AbstractCreature) this, (AbstractPower) new KindnessStabsPower((AbstractCreature) this)));
     }

     public void takeTurn() {
         int i;
         switch (this.nextMove) {
             case STAB:
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ChangeStateAction(this, "ATTACK"));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new WaitAction(0.5F));

                 for (i = 0; i < this.stabCount; i++) {
                     AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new SFXAction("MONSTER_BOOK_STAB_" +
                             MathUtils.random(0, 3)));
                     AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new DamageAction((AbstractCreature) AbstractDungeon.player, this.damage


                             .get(0), AbstractGameAction.AttackEffect.SLASH_VERTICAL, false, true));
                 }
                 break;


             case SHUFFLE:
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ChangeStateAction(this, "ATTACK_2"));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new WaitAction(0.5F));
                 AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AddCardToDeckAction(CardLibrary.getCard("Bandage Up").makeCopy()));
                 if (AbstractDungeon.ascensionLevel >= 16) {
                     AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new AddCardToDeckAction(CardLibrary.getCard("Bandage Up").makeCopy()));
                 }
                 break;
         }

         this.turnsTaken++;
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new RollMoveAction(this));
     }


     public void changeState(String stateName) {
         switch (stateName) {
             case "ATTACK":
                 this.state.setAnimation(0, "Attack", false);
                 this.state.addAnimation(0, "Idle", true, 0.0F);
                 break;
             case "ATTACK_2":
                 this.state.setAnimation(0, "Attack_2", false);
                 this.state.addAnimation(0, "Idle", true, 0.0F);
                 break;
         }
     }


     public void damage(DamageInfo info) {
         super.damage(info);
         if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
             this.state.setAnimation(0, "Hit", false);
             this.state.addAnimation(0, "Idle", true, 0.0F);
         }
     }


     protected void getMove(int num) {
         if ((this.turnsTaken + 1) % 3 > 0) {
             this.stabCount = Math.min(this.stabCount + 1, this.maxStabCount);
             setMove(this.STAB, AbstractMonster.Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base, this.stabCount, true);
         } else {
             setMove(this.SHUFFLE, AbstractMonster.Intent.STRONG_DEBUFF);
         }
     }


     public void die() {
         super.die();
         CardCrawlGame.sound.play("STAB_BOOK_DEATH");
     }
 }
