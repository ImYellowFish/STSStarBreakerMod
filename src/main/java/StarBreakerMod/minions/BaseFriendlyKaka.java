package StarBreakerMod.minions;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.actions.KakaShowCardAction;
import StarBreakerMod.minions.cards.KakaPlayableCard;
import StarBreakerMod.minions.ai.AbstractKakaAI;
import StarBreakerMod.minions.system.KakaMinionData;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.minions.powers.AbstractKakaMinionPower;
import StarBreakerMod.relics.KakaDogTag;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BaseFriendlyKaka extends AbstractFriendlyMonster {
    public static final String NAME = "BaseFriendlyKaka";
    public static final String ID = "BaseFriendlyKaka";

    // Constants
    public static final int KAKA_POS_INTERVAL = -100;
    public static final float ANIMATION_SCALE = 1.5f;

    // Variables
    public KakaMinionData kakaData;
    public KakaDogTag dogTag;
    public CardGroup masterDeck;
    public int energy = 0;
    public int cardsInHand = 0;
    public int aggro = 0;

    public AbstractKakaAI AI;

    // ----------------------------------------
    // Initialization
    // ----------------------------------------
    public BaseFriendlyKaka(int index, KakaMinionData kakaData, CardGroup kakaDeck, AbstractKakaAI kakaAI) {
        super(NAME, ID, kakaData.maxHealth, -8.0F, 10.0F, 80.0F, 240.0F, null, 0, 0);
        this.kakaData = kakaData;
        this.masterDeck = kakaDeck;
        this.currentHealth = kakaData.currentHealth;
        this.AI = kakaAI;
        initializeKaka(index);
    }

    public void SetDogTagRelic(KakaDogTag dogTag){
        this.dogTag = dogTag;
        dogTag.kaka = this;
    }

    public void SetDeck(CardGroup deck){
        this.masterDeck = deck;
    }

    protected void initializeKaka(int index){
        SetKakaPosition(index);
        SetKakaAnimation();
    }


    protected void SetKakaAnimation(){
        // Set kaka animation
        loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", ANIMATION_SCALE);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());

    }

    protected void SetKakaPosition(int index){
        StarBreakerMod.logger.info("set kaka pos:" + index);
        // Set kaka position
        this.drawX = Settings.WIDTH * 0.25F + (index + 1) * KAKA_POS_INTERVAL * Settings.scale;
        this.drawY = AbstractDungeon.floorY;
        this.flipHorizontal = true;

    }


    // ----------------------------------------
    // Inherited functions
    // ----------------------------------------
    // Executed at the end of playerTurn.
    public void takeTurn() {
        if (!this.isDead)
            this.AI.onKakaTakeTurn();
        else
            KakaMinionManager.getInstance().onKakaFinishedPlayingCards(this);
    }

    public void die(){
        super.die();
        KakaMinionManager.getInstance(AbstractDungeon.player).playInstantKakaDeathSfx();
        if(this.dogTag != null){
            this.dogTag.usedUp();
        }
        this.kakaData.currentHealth = 0;
        this.kakaData.alive = false;
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        RecordHealth();
    }

    public void heal(int healAmount) {
        super.heal(healAmount);
        RecordHealth();
    }

    public void atTurnStartPostDraw(){
        // TODO: refresh intent, strategy and description

        for(AbstractCard c : this.masterDeck.group){
            c.resetAttributes();
        }
        this.AI.updateEnergyAndDrawOnTurnStart();

        // Apply kaka powers on turn start
        for(AbstractPower p : this.powers){
            if(p instanceof AbstractKakaMinionPower){
                ((AbstractKakaMinionPower) p).onKakaStartTurnPostDraw();
            }
        }

        this.AI.createIntent();

    }

    // ----------------------------------------
    // Utility
    // ----------------------------------------
    public void PlayCard(AbstractCard card, AbstractMonster target){
        card.calculateCardDamage(target);
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction((AbstractCreature)this));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new KakaShowCardAction(this, card));
        ((KakaPlayableCard)card).OnKakaUseCard(this, target);
    }

    public void RecordHealth(){
        this.kakaData.currentHealth = this.currentHealth;
        this.kakaData.maxHealth = this.maxHealth;
    }

    public void ClearAggro(){
        this.aggro = 0;
    }

    public void Test(){
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_CULTIST_1A"));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, "Kaka!", 1.0F, 2.0F));

        AbstractMonster target = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(
                (AbstractGameAction)new DamageAction(
                        target,
                        new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(
                (AbstractGameAction)new DamageAction(
                        this,
                        new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}