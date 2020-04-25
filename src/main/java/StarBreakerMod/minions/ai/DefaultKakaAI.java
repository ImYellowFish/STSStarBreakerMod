package StarBreakerMod.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.actions.KakaShowCardAction;
import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
import StarBreakerMod.cards.kakaCards.KakaStatDrawCard;
import StarBreakerMod.cards.kakaCards.KakaStatEnergyCard;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.relics.KakaDogTag;
import StarBreakerMod.rewards.KakaSingleCardReward;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;


public class DefaultKakaAI extends AbstractKakaAI {
    // ----------------------------------------
    // Variables
    // ----------------------------------------
    public KakaPlayableCard baseStatEnergyCard;
    public KakaPlayableCard baseStatDrawCard;

    public CardGroup keyOffensiveCardPile;
    public CardGroup keyDefensiveCardPile;
    public CardGroup keyPowerCardPile;
    public CardGroup optionalOffensiveCardPile;
    public CardGroup optionalDefensiveCardPile;
    public CardGroup optionalPowerCardPile;
    public CardGroup energyCardPile;

    // changes at start of turn, shows how kaka will move this turn
    public CardGroup intentPile;
    public CardGroup intentBottomPile;
    public int cardPlayedThisTurn = 0;

    private int intentEnergy;
    private int intentCardsInHand;

    // ----------------------------------------
    // Constants
    // ----------------------------------------
    // the chance that kaka will play multiple key cards rather than just one
    public static final int NEXT_ENERGY_CARD_CHANCE = 20;
    public static final int NEXT_KEY_CARD_CHANCE = 50;
    public static final int NEXT_POWER_CARD_CHANCE = 50;

    public static final int ENERGY_RESERVED_FOR_X = 2;

    // reward drop chance
    public static final int TRAIT_DROP_CHANCE = 10;
    public static final int SPACIAL_AI_DROP_CHANCE = 10;
    public static final int CARD_DROP_CHANCE = 50;

    public static final int FIRST_KEY_CARD_DROP_CHANCE = 50;
    public static final int KEY_CARD_DROP_CHANCE = 25;

    public static final int ENERGY_CARD_DROP_CHANCE = 25;

    // ----------------------------------------
    // Interfaces
    // ----------------------------------------
    public DefaultKakaAI(KakaDogTag dogTag) {
        super(dogTag);
    }

    public void onKakaSpawn() {
        intializeCardPiles();
    }

    public void updateEnergyAndDrawOnTurnStart() {
        StarBreakerMod.logger.info("update kaka energy: " + this.baseStatEnergyCard.magicNumber);
        StarBreakerMod.logger.info("update kaka draw: " + this.baseStatDrawCard.magicNumber);
        this.baseStatEnergyCard.OnKakaUseCard(GetOwner(), null);
        this.baseStatDrawCard.OnKakaUseCard(GetOwner(), null);
    }

    public void createIntent() {
        intentPile.clear();
        intentBottomPile.clear();
        cardPlayedThisTurn = 0;

        KakaMinionManager mgr = KakaMinionManager.getInstance();
        BaseFriendlyKaka kaka = GetOwner();
        this.intentEnergy = kaka.energy;
        this.intentCardsInHand = kaka.cardsInHand;

        StarBreakerMod.logger.info("create intent:" + kaka.name +
                "card:" + this.intentCardsInHand + ", energy:" + this.intentEnergy);

        // play some of the energy cards
        planThroughCardPiles(energyCardPile, intentPile, NEXT_ENERGY_CARD_CHANCE);

        // try to play as much key power as possible
        planThroughCardPiles(keyPowerCardPile, intentPile, 100);

        // if we are the aggro target, play defense first
        if (mgr.isAggroTarget(kaka)) {
            planThroughCardPiles(keyDefensiveCardPile, intentBottomPile, NEXT_KEY_CARD_CHANCE);

            // then we go through optional power cards
            planThroughCardPiles(optionalPowerCardPile, intentPile, NEXT_POWER_CARD_CHANCE);

            // then we go through optional defensive cards, play as many as possible
            planThroughCardPiles(optionalDefensiveCardPile, intentPile, 100);

            // then we go through offensive cards
            planThroughCardPiles(optionalPowerCardPile, intentPile, NEXT_POWER_CARD_CHANCE);
            planThroughCardPiles(optionalOffensiveCardPile, intentPile, 100);
            planThroughCardPiles(keyOffensiveCardPile, intentBottomPile, NEXT_KEY_CARD_CHANCE);

        } else {
            planThroughCardPiles(keyOffensiveCardPile, intentBottomPile, NEXT_KEY_CARD_CHANCE);

            // then we go through optional power cards
            planThroughCardPiles(optionalPowerCardPile, intentPile, NEXT_POWER_CARD_CHANCE);

            // then we go through optional defensive cards, play as many as possible
            planThroughCardPiles(optionalOffensiveCardPile, intentPile, 100);
        }

        // at last, we copy all cards from intentBottomPile to intentPile
        for (AbstractCard card : intentBottomPile.group) {
            intentPile.addToBottom(card);
        }
    }

    public void onKakaTakeTurn() {
        BaseFriendlyKaka owner = GetOwner();
        StarBreakerMod.logger.info(owner.name + "_kaka play cards:" + this.intentPile);
        playNextCardInIntentPile();
    }

    public void postKakaPlayCard(AbstractCreature target, AbstractCard card) {
        // TODO: play next card
        playNextCardInIntentPile();
    }

    public void onKakaUpgrade() {
        this.dogTag.kakaData.upgradePoint += KakaMinionManager.LEVEL_POINT_PER_LEVEL_UP;
        int bonusHp = KakaMinionManager.MAX_HP_PER_LEVEL_UP;

        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && GetOwner() != null) {
            GetOwner().increaseMaxHp(bonusHp, true);
            GetOwner().RecordHealth();
        } else {
            this.dogTag.kakaData.currentHealth += bonusHp;
            this.dogTag.kakaData.maxHealth += bonusHp;
        }
    }

    public void onVictory() {
    }


    public RewardItem getRandomDrops(){
        KakaMinionManager mgr = KakaMinionManager.getInstance();
        int roll = mgr.cardRandomRng.random(100);
        if(roll < CARD_DROP_CHANCE){
            return dropRandomCardReward();
        }
        return null;
    }

    public void onCardRemovedFromBattle(AbstractCard c){
        tryRemoveCardFromPile(c, keyPowerCardPile);
        tryRemoveCardFromPile(c, keyDefensiveCardPile);
        tryRemoveCardFromPile(c, keyOffensiveCardPile);
        tryRemoveCardFromPile(c, optionalPowerCardPile);
        tryRemoveCardFromPile(c, optionalDefensiveCardPile);
        tryRemoveCardFromPile(c, optionalOffensiveCardPile);
        tryRemoveCardFromPile(c, energyCardPile);
    }


    public void playNextCardInIntentPile(){
        int index = this.intentPile.group.size() - 1 - this.cardPlayedThisTurn;
        this.cardPlayedThisTurn++;

        if(this.GetOwner().isDead){
            KakaMinionManager.getInstance().onKakaFinishedPlayingCards(this.GetOwner());
            return;
        }

        if(index >= this.intentPile.group.size() || index < 0) {
            tryRemoveAllUnplayedEtherealCards();
            KakaMinionManager.getInstance().onKakaFinishedPlayingCards(this.GetOwner());
            return;
        }

        BaseFriendlyKaka owner = GetOwner();
        KakaPlayableCard card = (KakaPlayableCard)this.intentPile.group.get(index);
        // choose target
        AbstractMonster target = null;
        if (card.target == AbstractCard.CardTarget.ENEMY) {
            target = getRandomMonsterTarget();
        }

        // play card
        card.energyOnUse = owner.energy;
        KakaMinionManager.getInstance().playCard(card, owner, target);

        // update energy and draw
        owner.energy = owner.energy - card.costForTurn + ((KakaPlayableCard)card).energyGain;
        owner.cardsInHand = owner.cardsInHand - 1 + + ((KakaPlayableCard)card).cardDrawGain;
    }

    public int getCardPlayedThisTurn(){
        return cardPlayedThisTurn;
    }

    // ----------------------------------------
    // Reward drops
    // ----------------------------------------
    public RewardItem dropRandomCardReward(){
        KakaMinionManager mgr = KakaMinionManager.getInstance();
        AbstractCard result = null;

        int roll = mgr.cardRandomRng.random(100);

        int keyCardDropChance = KEY_CARD_DROP_CHANCE;
        if(!hasAnyKeyCard()){
            keyCardDropChance = FIRST_KEY_CARD_DROP_CHANCE;
        }
        if(roll < keyCardDropChance){
            return new KakaSingleCardReward(this.dogTag, mgr.kakaRewardFactory.getRandomKeyCardDrop());
        }

        roll = mgr.cardRandomRng.random(100);
        if(roll < ENERGY_CARD_DROP_CHANCE){
            return new KakaSingleCardReward(this.dogTag, mgr.kakaRewardFactory.getRandomEnergyCardDrop());
        }

        return new KakaSingleCardReward(this.dogTag, mgr.kakaRewardFactory.getRandomOptionalCardDrop());
    }

    // ----------------------------------------
    // AI utilities
    // ----------------------------------------
    public void planThroughCardPiles(CardGroup candidatePile, CardGroup resultPile, int multipleCardChance) {
        KakaMinionManager mgr = KakaMinionManager.getInstance();
        candidatePile.shuffle(mgr.cardRandomRng);
        for (AbstractCard card : candidatePile.group) {
            // hand is empty, break;
            if (this.intentCardsInHand <= 0)
                break;

            KakaPlayableCard c = (KakaPlayableCard) card;
            if (predictCanPlayCard(c)) {
                resultPile.addToBottom(c);
                updateIntentEnergyAndCardDrawAfterPlayCard(c);

                if(c.costForTurn == -1){
                    // X cost card
                    // always stop
                    break;
                }

                if ((c.energyGain - c.costForTurn) >= 0 && c.cardDrawGain > 0) {
                    // this is a free card
                    // always look for next card
                } else if (multipleCardChance >= 100) {
                    // chance > 100/100
                    // always look for next card
                } else if (multipleCardChance <= 0 || mgr.cardRandomRng.random(100) > multipleCardChance) {
                    // check whether we look for next card to play
                    break;
                }
            }
        }
    }

    public void tryRemoveCardFromPile(AbstractCard c, CardGroup pile){
        if(pile.contains(c)) {
            StarBreakerMod.logger.info("Remove card from kaka pile:" + c);
            pile.removeCard(c);
        }
    }

    public void tryRemoveAllUnplayedEtherealCards() {
        tryRemoveEtherealCards(keyPowerCardPile);
        tryRemoveEtherealCards(keyDefensiveCardPile);
        tryRemoveEtherealCards(keyOffensiveCardPile);
        tryRemoveEtherealCards(optionalPowerCardPile);
        tryRemoveEtherealCards(optionalDefensiveCardPile);
        tryRemoveEtherealCards(optionalOffensiveCardPile);
        tryRemoveEtherealCards(energyCardPile);
    }

    public void tryRemoveEtherealCards(CardGroup pile){
        ArrayList<AbstractCard> toRemove = new ArrayList<>();
        for(AbstractCard c : pile.group){
            if(c.isEthereal && !this.intentPile.contains(c)){
                AbstractDungeon.actionManager.addToBottom(
                        (AbstractGameAction) new KakaShowCardAction(
                                this.GetOwner(), c, true, true));
                toRemove.add(c);
            }
        }

        for(AbstractCard c : toRemove){
            pile.removeCard(c);
        }
    }


    // ----------------------------------------
    // Utilities
    // ----------------------------------------
    public int getEnergyPerTurn(){
        if(this.baseStatEnergyCard != null)
            return this.baseStatEnergyCard.magicNumber;
        for (AbstractCard card : GetOwner().masterDeck.group) {
            if (!(card instanceof KakaPlayableCard)) {
                continue;
            }
            KakaPlayableCard c = (KakaPlayableCard) card;
            if(c.kakaCardType == KakaPlayableCard.KakaCardType.BaseStat_Energy)
                return c.magicNumber;
        }
        return 0;
    }

    public int getCardDrawPerTurn(){
        if(this.baseStatDrawCard != null)
            return this.baseStatDrawCard.magicNumber;
        for (AbstractCard card : GetOwner().masterDeck.group) {
            if (!(card instanceof KakaPlayableCard)) {
                continue;
            }
            KakaPlayableCard c = (KakaPlayableCard) card;
            if(c.kakaCardType == KakaPlayableCard.KakaCardType.BaseStat_Draw)
                return c.magicNumber;
        }
        return 0;
    }

    public boolean predictCanPlayCard(KakaPlayableCard card) {
        // also works for X cards.
        if(card.cost == -1){
            return this.intentCardsInHand > 0;
        }
        return this.intentCardsInHand > 0 && card.costForTurn <= this.intentEnergy;
    }

    public void updateIntentEnergyAndCardDrawAfterPlayCard(KakaPlayableCard card) {
        if (card.costForTurn == -1) {
            // X cards
            // Always reserve 2 energy if possible, but when playing, may spend more energy.
            int predictedCost = Math.min(ENERGY_RESERVED_FOR_X, this.intentEnergy);
            this.intentEnergy = this.intentEnergy - predictedCost + card.energyGain;
        }
        else if(card.costForTurn == -2){
            this.intentEnergy = this.intentEnergy + card.energyGain;
        }
        else {
            this.intentEnergy = this.intentEnergy - card.costForTurn + card.energyGain;
        }
        this.intentCardsInHand = this.intentCardsInHand - 1 + card.cardDrawGain;
    }

    public boolean hasAnyKeyCard(){
        for(AbstractCard card : dogTag.kakaDeck.group) {
            KakaPlayableCard c = (KakaPlayableCard)card;
            if(c.kakaCardType == KakaPlayableCard.KakaCardType.Hand_KeyDefensive ||
                    c.kakaCardType == KakaPlayableCard.KakaCardType.Hand_KeyOffensive ||
                    c.kakaCardType == KakaPlayableCard.KakaCardType.Hand_KeyPower){
                return true;
            }
        }
        return false;
    }

    public void intializeCardPiles() {
        StarBreakerMod.logger.info("intializeCardPiles:" + GetOwner().name);
        StarBreakerMod.logger.info("master deck:" + GetOwner().masterDeck);
        intentPile = new CardGroup(CardGroup.CardGroupType.HAND);
        intentBottomPile = new CardGroup(CardGroup.CardGroupType.HAND);

        keyPowerCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        keyDefensiveCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        keyOffensiveCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        optionalPowerCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        optionalDefensiveCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        optionalOffensiveCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
        energyCardPile = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);

        if (GetOwner().masterDeck.isEmpty())
            return;

        for (AbstractCard card : GetOwner().masterDeck.group) {
            if (!(card instanceof KakaPlayableCard)) {
                continue;
            }
            KakaPlayableCard c = (KakaPlayableCard) card.makeSameInstanceOf();
            switch (c.kakaCardType) {
                case BaseStat_Energy:
                    this.baseStatEnergyCard = c;
                    break;
                case BaseStat_Draw:
                    this.baseStatDrawCard = c;
                    break;
                case Hand_KeyPower:
                    keyPowerCardPile.addToBottom(c);
                    break;
                case Hand_KeyDefensive:
                    keyDefensiveCardPile.addToBottom(c);
                    break;
                case Hand_KeyOffensive:
                    keyOffensiveCardPile.addToBottom(c);
                    break;
                case Hand_OptPower:
                    optionalPowerCardPile.addToBottom(c);
                    break;
                case Hand_OptDefensive:
                    optionalDefensiveCardPile.addToBottom(c);
                    break;
                case Hand_OptOffensive:
                    optionalOffensiveCardPile.addToBottom(c);
                    break;
                case Hand_EnergyGain:
                    energyCardPile.addToBottom(c);
                    break;
            }
        }

        if(this.baseStatDrawCard == null){
            this.baseStatDrawCard = new KakaStatDrawCard(0,0);
        }
        if(this.baseStatEnergyCard == null){
            this.baseStatEnergyCard = new KakaStatEnergyCard(0, 0);
        }
    }

}