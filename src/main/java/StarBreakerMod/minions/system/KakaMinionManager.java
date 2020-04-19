package StarBreakerMod.minions.system;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.actions.KakaPlayCardAction;
import StarBreakerMod.cards.kakaCards.KakaDefendCard;
import StarBreakerMod.cards.kakaCards.KakaStatDrawCard;
import StarBreakerMod.cards.kakaCards.KakaStatEnergyCard;
import StarBreakerMod.cards.kakaCards.KakaStrikeCard;
import StarBreakerMod.effects.TopLevelSpeechBubble;
import StarBreakerMod.minions.AbstractFriendlyMonster;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.minions.ai.AbstractKakaAI;
import StarBreakerMod.minions.ai.KakaAIFactory;
import StarBreakerMod.powers.KakaMinionAggroPower;
import StarBreakerMod.powers.KakaMinionHookPower;
import StarBreakerMod.powers.KakaMinionMiscPower;
import StarBreakerMod.relics.KakaDogTag;
import StarBreakerMod.rewards.RecruitableKakaReward;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KakaMinionManager{
    // ----------------------------------------
    // System values
    // ----------------------------------------
    protected static KakaMinionManager _instance;
    public AbstractPlayer player;

    // Related relic instances
    public ArrayList<KakaDogTag> dogTags = new ArrayList<KakaDogTag>();
    // Related kaka data, save/load is done by relics
    public ArrayList<KakaMinionData> kakaDatas = new ArrayList<KakaMinionData>();
    // Persistent Kaka State, dogTagID to data
    public Map<Integer, CardGroup> kakaDecks = new HashMap<>();
    // Kaka instance in battle
    public MonsterGroup battleKakaGroup = new MonsterGroup(new AbstractMonster[]{});

    // Helpers
    public Random cardRandomRng;
    public KakaAIFactory kakaAIFactory = new KakaAIFactory();


    // ----------------------------------------
    // Constant values
    // ----------------------------------------
    public static final int INIT_ENERGY_PER_TURN = 2;
    public static final int INIT_DRAW_PER_TURN = 2;
    public static final int INIT_MAX_HEALTH = 20;

    public static final int AGGRO_PER_DAMAGE = 1;
    public static final int AGGRO_PER_BLOCK = 1;

    public static final int LEVEL_POINT_PER_LEVEL_UP = 1;
    public static final int EXP_TO_UPGRADE = 3;
    public static final int EXP_PER_BATTLE = 1;
    public static final int EXP_PER_KILL = 1;
    public static final int MAX_HP_PER_LEVEL_UP = 8;

    public static UIStrings TIP_STRINGS;

    // ----------------------------------------
    // Battle values
    // ----------------------------------------
    // Aggro;
    public AbstractCreature aggroTarget;
    public int maxAggro;
    public AbstractCreature maxAggroTarget;
    public int playerAggro;


    // ----------------------------------------
    // Basic interface
    // ----------------------------------------
    // Get static instance
    public static KakaMinionManager getInstance(AbstractPlayer player){
        // Initialize here, as player is assigned the first time
        if(_instance.player == null) {
            _instance.TIP_STRINGS = CardCrawlGame.languagePack.getUIString("StarBreaker:KakaBattleTips");
        }

        _instance.player = player;
        return _instance;
    }

    public static KakaMinionManager getInstance(){
        return _instance;
    }

    public static void InitializeInstance(){
        _instance = new KakaMinionManager();
    }


    // Generate new kaka ID
    public int getNewKakaID(){
        return this.dogTags.size();
    }

    // Registration
    // Add kakaDogTag and related data to the global bookkeeper
    // Called by KakaDogTag (when load or obtain)
    public void registerKaka(KakaDogTag dogTag, KakaMinionData kakaData, CardGroup kakaDeck){
        int id = dogTag.dogTagID;
        this.dogTags.add(dogTag);
        this.kakaDatas.add(kakaData);
        this.kakaDecks.put(id, kakaDeck);
    }

    // System data management
    public void resetKakaData() {
        dogTags = new ArrayList<KakaDogTag>();
        kakaDatas = new ArrayList<KakaMinionData>();
        battleKakaGroup = new MonsterGroup(new AbstractMonster[]{});
        kakaDecks = new HashMap<>();
        clearAggro();
    }


    // ----------------------------------------
    // Inherited player system subscriber
    // ----------------------------------------
    public void onPlayerRender(SpriteBatch sb){
        MonsterGroup minions = this.battleKakaGroup;
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            if (AbstractDungeon.getCurrRoom() != null) {
                switch (AbstractDungeon.getCurrRoom().phase) {
                    case COMBAT:
                        minions.render(sb);
                        break;
                }
            }
        }
    }

    public void onPlayerUpdate(){
        MonsterGroup minions = this.battleKakaGroup;
        if (AbstractDungeon.getCurrRoom() != null) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                switch (AbstractDungeon.getCurrRoom().phase) {
                    case COMBAT:
                        minions.update();
                        break;
                }
            }
        }
    }

    // ----------------------------------------
    // Inherited player battle subscriber
    // ----------------------------------------
    public void onPlayerVictory(){
        // TODO add extra exp for kaka with certain traits
        for(KakaDogTag dogTag : this.dogTags){
            giveKakaExp(dogTag, this.EXP_PER_BATTLE);

            // try to drop some traits or cards
            dogTag.kakaAI.onVictory();
        }
    }

    public boolean onPlayerDeath(DamageInfo damageInfo){
        // TODO: add flee animation
        // TODO: add kaka sacrifice when player almost die
        StarBreakerMod.logger.info("OnPlayerDead kaka count:" + this.battleKakaGroup.monsters.size());
        if(this.battleKakaGroup.monsters.isEmpty()){
            return true;
        }

        // TODO: random talk
        AbstractCreature kaka = this.battleKakaGroup.getRandomMonster();
//        AbstractDungeon.actionManager.addToTop(new TalkAction(kaka, "~Nooooooo,~ ~IronClad!~ !", 1.0F, 2.0F));
        KakaInstantTalk(kaka, "~Nooooooo,~ ~IronClad!~ !", 2.0F, true);
        playInstantKakaDeathSfx();

        return true;
    }

    public boolean onPlayerDamage(DamageInfo info){
        // TODO: Let monster take damage here
        // Check aggro
        StarBreakerMod.logger.info("onPlayerDamage: " + this.aggroTarget);
        StarBreakerMod.logger.info(AbstractDungeon.player);
        StarBreakerMod.logger.info(this.aggroTarget == (AbstractCreature) AbstractDungeon.player);
        StarBreakerMod.logger.info(info.base);
        if((this.aggroTarget == null) || (this.aggroTarget instanceof AbstractPlayer))
            return true;
        if(info.type != DamageInfo.DamageType.NORMAL)
            return true;

        // Let the aggro target take the damage
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.aggroTarget, info, AbstractGameAction.AttackEffect.NONE));
        return false;
    }

    public void onPlayerPreBattlePrep(){
        clearBattleKaka();
        clearAggro();
    }

    public void onPlayerApplyStartOfTurnPostDraw(){
        StarBreakerMod.logger.info("----------- Minion After Turn Start Post Draw --------------");
        this.battleKakaGroup.monsters.forEach(monster -> {
            ((AbstractFriendlyMonster)monster).atTurnStartPostDraw();
        });
    }

    public void onPlayerApplyEndOfTurnTriggers(){
        StarBreakerMod.logger.info("----------- Minion Before Attacking --------------");
        ArrayList<AbstractMonster> kakaList = this.battleKakaGroup.monsters;
        // TODO: use action for play card, fix orders
        kakaList.forEach(AbstractMonster::takeTurn);
        kakaList.forEach(AbstractCreature::applyEndOfTurnTriggers);
        kakaList.forEach(monster -> {
            monster.powers.forEach(AbstractPower::atEndOfRound);
        });
    }

    public void onPlayerApplyTurnPowers(){
        this.battleKakaGroup.monsters.forEach(AbstractCreature::applyTurnPowers);
    }

    public void onPlayerApplyStartOfTurnPostDrawPowers(){
        this.battleKakaGroup.monsters.forEach(AbstractCreature::applyStartOfTurnPostDrawPowers);
    }

    public void onPlayerApplyStartOfTurnPowers(){
        this.battleKakaGroup.monsters.forEach(AbstractCreature::applyStartOfTurnPowers);
        this.battleKakaGroup.monsters.forEach(AbstractCreature::loseBlock);

        // decide next aggro target
        setNextTurnAggroTarget(this.maxAggroTarget);
        // clear all aggro at start of turn
        clearAggro();
    }

    public void onPlayerApplyStartOfCombatPreDrawLogic(){
        // copy some random
        cardRandomRng = AbstractDungeon.cardRandomRng.copy();

        dogTags.forEach(KakaDogTag::SpawnKaka);

        // Set first turn aggro target
        setNextTurnAggroTarget(this.player);

        // Add misc buff to all kaka and player
        this.battleKakaGroup.monsters.forEach(monster->{
            BaseFriendlyKaka kaka = (BaseFriendlyKaka)monster;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                    kaka, kaka, (AbstractPower)new KakaMinionMiscPower(kaka)
            ));
        });
        AbstractPlayer p = this.player;
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
//                p, p, (AbstractPower)new KakaMinionMiscPower(p)
//        ));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, (AbstractPower)new KakaMinionHookPower(p)
        ));
    }

    public void onPlayerUpdatePowers(){
        this.battleKakaGroup.monsters.forEach(AbstractCreature::updatePowers);
    }

    // Add aggro when gaining block
    public void onCreatureAddBlock(AbstractCreature c, int blockAmount){
        if(c instanceof AbstractPlayer || c instanceof  BaseFriendlyKaka){
            addAggro(c, blockAmount * this.AGGRO_PER_BLOCK);
        }
    }


    // ----------------------------------------
    // Kaka Recruitment and Generation
    // ----------------------------------------
    public void AddRecruitableKakaReward(){
        KakaDogTag dogTag = new KakaDogTag();
        AbstractDungeon.getCurrRoom().rewards.add(new RecruitableKakaReward(dogTag));
    }

    public void generateRecruitableKakaForRelic(KakaDogTag dogTag) {
        // TODO: random generate
        KakaMinionData kakaData = new KakaMinionData();
        kakaData.name = generateRandomKakaName();

        kakaData.alive = true;
        kakaData.maxHealth = INIT_MAX_HEALTH;
        kakaData.currentHealth = INIT_MAX_HEALTH;
        kakaData.level = 0;
        kakaData.exp = 0;
        kakaData.aiType = KakaAIFactory.KakaAIType.DEFAULT;

        CardGroup kakaDeck = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
        // Add base stat
        kakaDeck.addToTop(new KakaStatEnergyCard(INIT_ENERGY_PER_TURN, 0));
        kakaDeck.addToTop(new KakaStatDrawCard(INIT_DRAW_PER_TURN, 0));

        // Add kaka base cards
        kakaDeck.addToTop(new KakaStrikeCard());
        kakaDeck.addToTop(new KakaDefendCard());


        dogTag.initializeKaka(kakaData, kakaDeck);
    }

    public String generateRandomKakaName(){
        UIStrings kakaNamesLibrary = CardCrawlGame.languagePack.getUIString("StarBreaker:KakaNames");
        int count = kakaNamesLibrary.TEXT.length;
        return kakaNamesLibrary.TEXT[AbstractDungeon.cardRandomRng.random(0, count - 1)];
    }

    public AbstractKakaAI generateKakaAIForRelic(KakaDogTag dogTag, KakaAIFactory.KakaAIType aiType){
        return this.kakaAIFactory.getAI(dogTag, aiType);
    }


    // ----------------------------------------
    // Spawn interface
    // ----------------------------------------
    public BaseFriendlyKaka spawnBattleKaka(KakaDogTag dogTag, KakaMinionData kakaData, CardGroup kakaDeck, AbstractKakaAI kakaAI){
        int index = this.battleKakaGroup.monsters.size();
        BaseFriendlyKaka kaka = new BaseFriendlyKaka(index, kakaData, kakaDeck, kakaAI);
        kaka.SetDogTagRelic(dogTag);
        kaka.AI.onKakaSpawn();

        kaka.init();
        kaka.usePreBattleAction();
        //minionToAdd.useUniversalPreBattleAction();
        kaka.showHealthBar();
        this.battleKakaGroup.add(kaka);
        return kaka;
    }

    public boolean removeBattleKaka(BaseFriendlyKaka kakaToRemove) {
        return this.battleKakaGroup.monsters.remove(kakaToRemove);
    }

    public void clearBattleKaka(){
        this.battleKakaGroup = new MonsterGroup(new AbstractMonster[]{});
    }


    // ----------------------------------------
    // Battle interface
    // ----------------------------------------
    public void playCard(AbstractCard card, BaseFriendlyKaka kaka, AbstractMonster target){
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new KakaPlayCardAction(kaka, target, card));
    }

    public void onKakaKill(BaseFriendlyKaka kaka, AbstractCreature target){
        giveKakaExp(kaka.dogTag, this.EXP_PER_KILL);
    }

    // ----------------------------------------
    // Aggro
    // ----------------------------------------
    public boolean isAggroTarget(AbstractCreature c){
        return this.aggroTarget == c;
    }

    public void addAggro(AbstractCreature p, int deltaAggro) {
        if (p instanceof AbstractPlayer) {
            this.playerAggro += deltaAggro;
            updateAggroInfo(p, this.playerAggro);
        } else if (p instanceof BaseFriendlyKaka) {
            BaseFriendlyKaka kaka = ((BaseFriendlyKaka) p);
            kaka.aggro += deltaAggro;
            this.updateAggroInfo(p, kaka.aggro);
        }
    }

    public int getAggro(AbstractCreature p){
        if (p instanceof AbstractPlayer) {
            return this.playerAggro;
        } else if (p instanceof BaseFriendlyKaka) {
            BaseFriendlyKaka kaka = ((BaseFriendlyKaka) p);
            return kaka.aggro;
        }
        return 0;
    }

    public void setNextTurnAggroTarget(AbstractCreature target){
        StarBreakerMod.logger.info("set next turn aggro target: " + this.aggroTarget);
        // TODO: add buff
        this.aggroTarget = target;

        final String aggroPowerName = "KakaMinionAggroPower";
        // clear and add buffs
        for(AbstractMonster kaka : this.battleKakaGroup.monsters){

            if(kaka.hasPower(aggroPowerName))
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(kaka, kaka, aggroPowerName));
        }
        if(this.player.hasPower(aggroPowerName))
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.player, this.player, aggroPowerName));
        // Apply powers if we have at least one kaka companion
        if(!this.battleKakaGroup.monsters.isEmpty())
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new KakaMinionAggroPower(target)));
    }

    public void updateAggroInfo(AbstractCreature p, int newAggro){
        if(p == this.maxAggroTarget){
            this.maxAggro = newAggro;
        }
        else if(newAggro > this.maxAggro){
            this.maxAggroTarget = p;
            this.maxAggro = newAggro;
        }
    }

    public void clearAggro(){
        this.maxAggro = 0;
        this.playerAggro = 0;
        this.maxAggroTarget = this.player;

        this.battleKakaGroup.monsters.forEach(monster -> ((BaseFriendlyKaka)monster).ClearAggro());
    }

    // ----------------------------------------
    // AI
    // ----------------------------------------
    public AbstractKakaAI getKakaAI(int id){
        return this.dogTags.get(id).kakaAI;
    }

    public void replaceKakaAI(KakaDogTag dogTag, KakaAIFactory.KakaAIType aiType){
        // TODO
    }

    // ----------------------------------------
    // Exp and level
    // ----------------------------------------
    public void giveKakaExp(KakaDogTag dogTag, int exp){
        if(!dogTag.kakaData.alive)
            return;
        StarBreakerMod.logger.info("giveKakaExp: " + dogTag.kakaData.name);
        KakaMinionData kakaData = dogTag.kakaData;
        kakaData.exp += exp;

        AbstractCreature kaka = dogTag.kaka;
        if(kaka != null) {
            AbstractDungeon.topLevelEffects.add(new TextAboveCreatureEffect(kaka.hb.cX - kaka.animX, kaka.hb.cY + kaka.hb.height / 2.0F, this.TIP_STRINGS.TEXT[0] + exp + this.TIP_STRINGS.TEXT[1], Color.WHITE));
        }
        while(kakaData.exp >= this.EXP_TO_UPGRADE){
            upgradeKaka(dogTag);
            kakaData.exp -= this.EXP_TO_UPGRADE;
        }
        kakaData.exp = Math.max(0, kakaData.exp);
    }

    public void upgradeKaka(KakaDogTag dogTag){
        StarBreakerMod.logger.info("Kaka upgraded: " + dogTag.kakaData.name + ", lv:" + dogTag.kakaData.level);
        dogTag.kakaData.level++;
        dogTag.kakaAI.onKakaUpgrade();
        if(dogTag.kaka != null){
//            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VFXAction((AbstractGameEffect)new LightBulbEffect(dogTag.kaka.hb), 0.2F));
            AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(dogTag.kaka, this.TIP_STRINGS.TEXT[2]));
        }
    }


    // ----------------------------------------
    // Talk
    // ----------------------------------------
    public void KakaInstantTalk(AbstractCreature kaka, String msg, float bubbleDuration, boolean isTopLevel) {
        if(isTopLevel){
            AbstractDungeon.topLevelEffects.add(new TopLevelSpeechBubble(kaka.hb.cX + kaka.dialogX, kaka.hb.cY +kaka.dialogY, bubbleDuration, msg, false));
        }
        else {
            AbstractDungeon.effectList.add(new SpeechBubble(kaka.hb.cX + kaka.dialogX, kaka.hb.cY + kaka.dialogY, bubbleDuration, msg, false));
        }
    }

    public void playInstantKakaTalkSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_CULTIST_1A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_CULTIST_1B");
        } else {
            CardCrawlGame.sound.play("VO_CULTIST_1C");
        }
    }

    public void playInstantKakaDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_CULTIST_2A");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("VO_CULTIST_2B");
        } else {
            CardCrawlGame.sound.play("VO_CULTIST_2C");
        }
    }
}