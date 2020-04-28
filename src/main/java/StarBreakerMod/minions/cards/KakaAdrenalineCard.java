package StarBreakerMod.minions.cards;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdrenalineEffect;

public class KakaAdrenalineCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Adrenaline");
    public static final String ID = "StarBreaker:KakaAdrenalineCard";

    public KakaAdrenalineCard() {
        super(ID, cardStrings.NAME, "green/skill/adrenaline", 0, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.Hand_EnergyGain;
        this.cardDrawGain = 2;
        this.energyGain = 1;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new AdrenalineEffect(), 0.15F));
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new AdrenalineEffect(), 0.15F));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.energyGain += 1;
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return new KakaAdrenalineCard();
    }

}