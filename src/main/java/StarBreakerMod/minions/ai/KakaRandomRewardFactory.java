package StarBreakerMod.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.relics.KakaDogTag;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import java.util.ArrayList;

public class KakaRandomRewardFactory {
    public CardGroup keyCardPool;
    public CardGroup optionalCardPool;
    public CardGroup energyCardPool;
    public CardGroup drawCardPool;

    public KakaRandomRewardFactory() {

    }

    // todo: drop link cards
    public AbstractCard getRandomKeyCardDrop() {
        if (!this.keyCardPool.isEmpty())
            return this.keyCardPool.getRandomCard(KakaMinionManager.getInstance().cardRandomRng).makeCopy();
        return null;
    }

    public AbstractCard getRandomEnergyCardDrop() {
        if (!this.energyCardPool.isEmpty())
            return this.energyCardPool.getRandomCard(KakaMinionManager.getInstance().cardRandomRng).makeCopy();
        return null;
    }

    public AbstractCard getRandomOptionalCardDrop() {
        if (!this.optionalCardPool.isEmpty())
            return this.optionalCardPool.getRandomCard(KakaMinionManager.getInstance().cardRandomRng).makeCopy();
        return null;
    }

    public void initialize() {
        keyCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        optionalCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        energyCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        drawCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

        ArrayList<AbstractCard> customCards = BaseMod.getCustomCardsToAdd();
        for (AbstractCard c : customCards) {
            if (!(c instanceof KakaPlayableCard)) {
                continue;
            }
            KakaPlayableCard card = (KakaPlayableCard) c;
            if (card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_KeyDefensive ||
                    card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_KeyOffensive ||
                    card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_KeyPower) {

                keyCardPool.addToBottom(c);
            } else if (card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_OptDefensive ||
                    card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_OptOffensive ||
                    card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_OptPower) {

                optionalCardPool.addToBottom(c);
            } else if (card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_EnergyGain) {
                energyCardPool.addToBottom(c);
            } else if (card.kakaCardType == KakaPlayableCard.KakaCardType.Hand_DrawGain) {
                drawCardPool.addToBottom(c);
            }
        }
    }

}
