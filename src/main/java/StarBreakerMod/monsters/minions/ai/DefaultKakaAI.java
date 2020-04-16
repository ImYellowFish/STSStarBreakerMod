package StarBreakerMod.monsters.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.helpers.KakaMinionManager;
import StarBreakerMod.monsters.minions.BaseFriendlyKaka;
import StarBreakerMod.relics.KakaDogTag;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class DefaultKakaAI extends AbstractKakaAI {
    public DefaultKakaAI(KakaDogTag dogTag) {
        super(dogTag);
    }
    // ----------------------------------------
    // Interfaces
    // ----------------------------------------
    public void onKakaTakeTurn() {
        BaseFriendlyKaka owner = GetOwner();
        StarBreakerMod.logger.info("takeTurn: Kaka master deck:" + owner.masterDeck);
        StarBreakerMod.logger.info("takeTurn: Kaka energy:" + owner.kakaData.cardDrawPerTurn + ", " + owner.energy);
        if (owner.masterDeck.isEmpty())
            return;
        int cardPlayedThisTurn = 0;
        AbstractCard nextCard = owner.masterDeck.getRandomCard(AbstractDungeon.cardRandomRng);
        // TODO: draw cards
        while (cardPlayedThisTurn < owner.kakaData.cardDrawPerTurn) {
            if (nextCard.cost > owner.energy) {
                break;
            }
            owner.energy -= nextCard.cost;
            if (nextCard.target == AbstractCard.CardTarget.ENEMY) {
                PlayCard(nextCard, GetRandomMonster());
            } else {
                PlayCard(nextCard, null);
            }
        }
    }
}