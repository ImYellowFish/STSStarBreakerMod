package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KakaStatEnergyCard extends KakaPlayableCard {
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("StarBreaker:KakaStatEnergyCard");
    public static final String ID = "StarBreaker:KakaStatEnergyCard";

    public KakaStatEnergyCard(int initEnergy, int timesUpgraded){
        super(ID, cardStrings.NAME, new RegionName("green/skill/tactician"), -2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.BASIC, AbstractCard.CardTarget.SELF);
        this.kakaCardType = KakaCardType.BaseStat_Energy;
        this.magicNumber = this.baseMagicNumber = initEnergy + timesUpgraded;
        this.timesUpgraded = timesUpgraded;
    }

    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature m){
        // reset energy
        kaka.energy = this.magicNumber;
    }

    public void upgrade() {
        upgradeMagicNumber(1);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // nothing here
    }

    public AbstractCard makeCopy() {
        return new KakaStatEnergyCard(this.magicNumber - this.timesUpgraded, this.timesUpgraded);
    }
}