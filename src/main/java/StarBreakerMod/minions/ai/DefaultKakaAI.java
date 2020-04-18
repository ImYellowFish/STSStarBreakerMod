package StarBreakerMod.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
import StarBreakerMod.minions.KakaMinionManager;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.relics.KakaDogTag;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import javax.smartcardio.Card;


public class DefaultKakaAI extends AbstractKakaAI {
    // ----------------------------------------
    // Variables
    // ----------------------------------------
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

    // ----------------------------------------
    // Interfaces
    // ----------------------------------------
    public DefaultKakaAI(KakaDogTag dogTag) {
        super(dogTag);
    }

    public void onKakaSpawn(){
        intializeCardPiles();
    }

    public void createIntent(){
        intentPile.clear();
        intentBottomPile.clear();

        KakaMinionManager mgr = KakaMinionManager.getInstance();
        BaseFriendlyKaka kaka = GetOwner();
        this.intentEnergy = kaka.energy;
        this.intentCardsInHand = kaka.kakaData.cardDrawPerTurn;

        StarBreakerMod.logger.info("create intent:" + kaka.name +
                "card:" + this.intentCardsInHand + ", energy:" + this.intentEnergy);

        // play some of the energy cards
        planThroughCardPiles(energyCardPile, intentPile, NEXT_ENERGY_CARD_CHANCE);

        // try to play as much key power as possible
        planThroughCardPiles(keyPowerCardPile, intentPile, 100);

        // if we are the aggro target, play defense first
        if(mgr.isAggroTarget(kaka)){
            planThroughCardPiles(keyDefensiveCardPile, intentBottomPile, NEXT_KEY_CARD_CHANCE);

            // then we go through optional power cards
            planThroughCardPiles(optionalPowerCardPile, intentPile, NEXT_POWER_CARD_CHANCE);

            // then we go through optional defensive cards, play as many as possible
            planThroughCardPiles(optionalDefensiveCardPile, intentPile, 100);
        }
        else{
            planThroughCardPiles(keyOffensiveCardPile, intentBottomPile, NEXT_KEY_CARD_CHANCE);

            // then we go through optional power cards
            planThroughCardPiles(optionalPowerCardPile, intentPile, NEXT_POWER_CARD_CHANCE);

            // then we go through optional defensive cards, play as many as possible
            planThroughCardPiles(optionalOffensiveCardPile, intentPile, 100);
        }

        // at last, we copy all cards from intentBottomPile to intentPile
        for(AbstractCard card : intentBottomPile.group){
            intentPile.addToBottom(card);
        }
    }

    public void onKakaTakeTurn() {
        BaseFriendlyKaka owner = GetOwner();
        if (this.intentPile.isEmpty())
            return;
        StarBreakerMod.logger.info(owner.name + "_kaka play cards:" + this.intentPile);
        // play all cards in intent pile
        for(AbstractCard card : this.intentPile.group){
            // choose target
            AbstractMonster target = null;
            if(card.target == AbstractCard.CardTarget.ENEMY){
                target = getRandomMonsterTarget();
            }

            // play card
            KakaMinionManager.getInstance().playCard(card, owner, target);

            // update energy
            owner.energy -= card.costForTurn;
        }

    }

    public void postKakaPlayCard(AbstractCreature target, AbstractCard card){
        // TODO: play next card

    }

    public void onKakaUpgrade(){
        this.dogTag.kakaData.levelPoint += KakaMinionManager.LEVEL_POINT_PER_LEVEL_UP;
        int bonusHp = KakaMinionManager.MAX_HP_PER_LEVEL_UP;

        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && GetOwner() != null){
            GetOwner().increaseMaxHp(bonusHp, true);
            GetOwner().RecordHealth();
        }
        else{
            this.dogTag.kakaData.currentHealth += bonusHp;
            this.dogTag.kakaData.maxHealth += bonusHp;
        }
    }

    // ----------------------------------------
    // AI utilities
    // ----------------------------------------
    public void planThroughCardPiles(CardGroup candidatePile, CardGroup resultPile, int multipleCardChance){
        KakaMinionManager mgr = KakaMinionManager.getInstance();
        candidatePile.shuffle(mgr.cardRandomRng);
        for(AbstractCard card : candidatePile.group){
            // hand is empty, break;
            if(this.intentCardsInHand <= 0)
                break;

            KakaPlayableCard c = (KakaPlayableCard) card;
            if(predictCanPlayCard(c)){
                resultPile.addToBottom(c);
                updateIntentEnergyAndCardDrawAfterPlayCard(c);

                if(c.energyGain > 0 && c.cardDrawGain > 0){
                    // this is a free card
                    // always look for next card
                }
                else if(multipleCardChance >= 100){
                    // chance > 100/100
                    // always look for next card
                }
                else if(multipleCardChance <= 0 || mgr.cardRandomRng.random(100) > multipleCardChance){
                    // check whether we look for next card to play
                    break;
                }
            }
        }
    }


    // ----------------------------------------
    // Utilities
    // ----------------------------------------
    public boolean predictCanPlayCard(KakaPlayableCard card){
        // also works for X cards.
        return this.intentCardsInHand > 0 && card.costForTurn <= this.intentEnergy;
    }

    public void updateIntentEnergyAndCardDrawAfterPlayCard(KakaPlayableCard card){
        if(card.costForTurn == -1){
            // X cards
            // Always reserve 2 energy if possible, but when playing, may spend more energy.
            int predictedCost = Math.min(ENERGY_RESERVED_FOR_X, this.intentEnergy);
            this.intentEnergy = this.intentEnergy - predictedCost + card.energyGain;
        }
        else {
            this.intentEnergy = this.intentEnergy - card.costForTurn + card.energyGain;
        }
        this.intentCardsInHand = this.intentCardsInHand - 1 + card.cardDrawGain;
    }

    public void intializeCardPiles(){
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

        for(AbstractCard card : GetOwner().masterDeck.group){
            if(!(card instanceof KakaPlayableCard)){
                return;
            }
            KakaPlayableCard c = (KakaPlayableCard)card;
            switch (c.kakaCardType){
                case KeyPower:
                    keyPowerCardPile.addToBottom(c);
                    break;
                case KeyDefensive:
                    keyDefensiveCardPile.addToBottom(c);
                    break;
                case KeyOffensive:
                    keyOffensiveCardPile.addToBottom(c);
                    break;
                case OptPower:
                    optionalPowerCardPile.addToBottom(c);
                    break;
                case OptDefensive:
                    optionalDefensiveCardPile.addToBottom(c);
                    break;
                case OptOffensive:
                    optionalOffensiveCardPile.addToBottom(c);
                    break;
                case Energy:
                    energyCardPile.addToBottom(c);
                    break;
            }
        }
    }

}