package StarBreakerMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import StarBreakerMod.powers.SpireTheoryBufferPower;

import basemod.abstracts.CustomCard;

public class SpireTheoryBufferCard
        extends CustomCard {
    public static final String ID = "StarBreaker:SpireTheoryBufferCard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "blue/power/buffer";
    private static final int COST = 1;

    public SpireTheoryBufferCard() {
        super(ID, cardStrings.NAME, new CustomCard.RegionName(IMG_PATH), COST, cardStrings.DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.RARE, CardTarget.SELF);

        // this.setBackgroundTexture("img/absorball.png", "img/absorball1_p.png");

        // this.setOrbTexture("img/custom_orb_small.png", "img/custom_orb_large.png");

        // this.setBannerTexture("img/custom_banner_large.png", "img/custom_banner_large.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction(p, p, new SpireTheoryBufferPower(p), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpireTheoryBufferCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}