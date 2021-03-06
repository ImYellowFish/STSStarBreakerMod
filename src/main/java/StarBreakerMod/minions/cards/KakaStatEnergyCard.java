package StarBreakerMod.minions.cards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
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
        super(ID, cardStrings.NAME, new RegionName("green/skill/tactician"), -2, cardStrings.DESCRIPTION, CardType.POWER, AbstractCardEnumPatches.SBM_KAKA_BLUE, CardRarity.BASIC, AbstractCard.CardTarget.SELF);
        this.kakaCardType = KakaCardType.BaseStat_Energy;
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = this.baseDamage + timesUpgraded;
        this.timesUpgraded = timesUpgraded;

        if(timesUpgraded > 0){
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeTitle();
        }
    }

    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature m){
        // reset energy
        kaka.energy = this.magicNumber;
    }

    public void upgrade() {
        this.timesUpgraded++;
        this.baseMagicNumber = this.magicNumber = this.baseDamage + this.timesUpgraded;
        this.upgraded = true;
        this.upgradedMagicNumber = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        // nothing here
    }

    public AbstractCard makeCopy() {
        return new KakaStatEnergyCard(this.baseDamage, 0);
    }

    public boolean canUpgrade() {
        return true;
    }
}