package StarBreakerMod.minions.cards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import StarBreakerMod.minions.powers.KakaDoubleTapPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KakaDoubleTapCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Double Tap"); public static final String ID = "StarBreaker:KakaDoubleTapCard";

    public KakaDoubleTapCard() {
        super("StarBreaker:KakaDoubleTapCard", cardStrings.NAME, new RegionName("red/skill/double_tap"), 1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.Hand_OptOffensive;
        this.baseMagicNumber = 1;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new KakaDoubleTapPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new KakaDoubleTapPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return new KakaDoubleTapCard();
    }
}