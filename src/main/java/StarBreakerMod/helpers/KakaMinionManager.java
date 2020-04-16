package StarBreakerMod.helpers;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.actions.KakaShowCardAction;
import StarBreakerMod.cards.kakaCards.KakaDefendCard;
import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
import StarBreakerMod.cards.kakaCards.KakaStrikeCard;
import StarBreakerMod.monsters.minions.AbstractFriendlyMonster;
import StarBreakerMod.monsters.minions.BaseFriendlyKaka;
import StarBreakerMod.monsters.minions.ai.AbstractKakaAI;
import StarBreakerMod.monsters.minions.KakaMinionData;
import StarBreakerMod.monsters.minions.ai.KakaAIFactory;
import StarBreakerMod.powers.KakaMinionMiscPower;
import StarBreakerMod.relics.KakaDogTag;
import StarBreakerMod.rewards.RecruitableKakaReward;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
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
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KakaMinionManager{
    // ----------------------------------------
    // System values
    // ----------------------------------------
    protected static KakaMinionManager _instance;
    public AbstractPlayer player;

    public static final int AGGRO_PER_DAMAGE = 1;
    public static final int AGGRO_PER_BLOCK = 1;


    // Related relic instances
    public ArrayList<KakaDogTag> dogTags = new ArrayList<KakaDogTag>();
    // Related kaka data, save/load is done by relics
    public ArrayList<KakaMinionData> kakaDatas = new ArrayList<KakaMinionData>();
    // Persistent Kaka State, dogTagID to data
    public Map<Integer, CardGroup> kakaDecks = new HashMap<>();
    // Kaka instance in battle
    public MonsterGroup battleKakaGroup = new MonsterGroup(new AbstractMonster[]{});

    // Helpers
    KakaAIFactory kakaAIFactory = new KakaAIFactory();

    // ----------------------------------------
    // String values
    // ----------------------------------------


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
        _instance.player = player;
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
        // TODO add exp
    }

    public void onPlayerDead(){
        // TODO: add flee animation
        // TODO: add kaka sacrifice when player almost die
        StarBreakerMod.logger.info("OnPlayerDead kaka count:" + this.battleKakaGroup.monsters.size());
        if(this.battleKakaGroup.monsters.isEmpty()){
            return;
        }
        AbstractCreature kaka = this.battleKakaGroup.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_CULTIST_1A"));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction(kaka, "~Nooooooo,~ ~IronClad!~ !", 1.0F, 2.0F));
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, (AbstractPower)new KakaMinionMiscPower(p)
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
        kakaData.maxHealth = 20;
        kakaData.currentHealth = 20;
        kakaData.energyPerTurn = 2;
        kakaData.cardDrawPerTurn = 2;
        kakaData.level = 0;
        kakaData.exp = 0;
        kakaData.aiType = KakaAIFactory.KakaAIType.DEFAULT;

        CardGroup kakaDeck = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
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
    public BaseFriendlyKaka spawnBattleKaka(KakaMinionData kakaData, CardGroup kakaDeck, AbstractKakaAI kakaAI){
        int index = this.battleKakaGroup.monsters.size();
        BaseFriendlyKaka kaka = new BaseFriendlyKaka(index, kakaData, kakaDeck, kakaAI);
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
    public void PlayCard(AbstractCard card, BaseFriendlyKaka kaka, AbstractMonster target){
        ((KakaPlayableCard)card).calculateKakaCardDamage(kaka, target);
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction((AbstractCreature)kaka));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new KakaShowCardAction(kaka, card));
        ((KakaPlayableCard)card).OnKakaUseCard(kaka, target);
    }

    // ----------------------------------------
    // Aggro
    // ----------------------------------------
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
    }

    public void updateAggroInfo(AbstractCreature p, int newAggro){
        if(newAggro > this.maxAggro){
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

}