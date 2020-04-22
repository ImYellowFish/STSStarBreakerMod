package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KakaStatDrawCard extends KakaPlayableCard {
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("StarBreaker:KakaStatDrawCard");
    public static final String ID = "StarBreaker:KakaStatDrawCard";

    public KakaStatDrawCard(int initDraw, int timesUpgraded){
        super(ID, cardStrings.NAME, new RegionName("green/skill/reflex"), -2, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);
        this.kakaCardType = KakaCardType.BaseStat_Draw;
        this.magicNumber = this.baseMagicNumber = initDraw + timesUpgraded;
        this.timesUpgraded = timesUpgraded;

        if(timesUpgraded > 0){
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            initializeTitle();
        }
    }

    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature m){
        // reset draw
        kaka.cardsInHand = this.magicNumber;
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
        return new KakaStatDrawCard(this.magicNumber - this.timesUpgraded, this.timesUpgraded);
    }

    public boolean canUpgrade() {
        return true;
    }
}