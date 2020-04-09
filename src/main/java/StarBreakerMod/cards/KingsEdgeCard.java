package StarBreakerMod.cards;

import StarBreakerMod.powers.YouLikeThatPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.abstracts.CustomCard;

public class KingsEdgeCard
        extends CustomCard {
    public static final String ID = "StarBreaker:KingsEdgeCard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "red/power/juggernaut";
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final int MAX_HP_INC = 1;


    public KingsEdgeCard() {
        super(ID, cardStrings.NAME, new CustomCard.RegionName(IMG_PATH), COST, cardStrings.DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = MAX_HP_INC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YouLikeThatPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new KingsEdgeCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}