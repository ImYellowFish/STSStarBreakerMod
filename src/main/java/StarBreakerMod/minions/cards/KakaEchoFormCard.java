package StarBreakerMod.minions.cards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import StarBreakerMod.minions.powers.KakaEchoFormPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KakaEchoFormCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Echo Form"); public static final String ID = "StarBreaker:KakaEchoFormCard";

    public KakaEchoFormCard() {
        super("StarBreaker:KakaEchoFormCard", cardStrings.NAME, new RegionName("blue/power/echo_form"), 3, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

        this.baseMagicNumber = this.magicNumber = 1;
        this.kakaCardType = KakaCardType.Hand_KeyPower;
        this.isEthereal = true;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new KakaEchoFormPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new KakaEchoFormPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.isEthereal = false;
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return new KakaEchoFormCard();
    }
}