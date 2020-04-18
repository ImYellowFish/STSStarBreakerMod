package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;

public class KakaDefendCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_R"); public static final String ID = "StarBreaker:KakaDefendCard";

    public KakaDefendCard() {
        super("StarBreaker:KakaDefendCard", cardStrings.NAME, new RegionName("red/skill/defend"), 1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.OptDefensive;
        this.baseBlock = 5;
        this.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
    }

    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature m){
        addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) kaka, (AbstractCreature) kaka, this.block));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) p, (AbstractCreature) p, this.block));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaDefendCard();
    }
}