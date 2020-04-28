package StarBreakerMod.minions.cards;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

public class KakaOfferingCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Offering");
    public static final String ID = "StarBreaker:KakaOfferingCard";
    private static final int HP_LOSS = 4;

    public KakaOfferingCard() {
        super(ID, cardStrings.NAME, "red/skill/offering", 0, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.Hand_EnergyGain;
        this.cardDrawGain = 3;
        this.energyGain = 2;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new OfferingEffect(), 0.1F));
        } else {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new OfferingEffect(), 0.5F));
        }
        addToBot((AbstractGameAction)new LoseHPAction((AbstractCreature)p, (AbstractCreature)p, HP_LOSS));

    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new OfferingEffect(), 0.1F));
        } else {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new OfferingEffect(), 0.5F));
        }
        addToBot((AbstractGameAction)new LoseHPAction((AbstractCreature)p, (AbstractCreature)p, HP_LOSS));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            this.cardDrawGain += 2;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return new KakaOfferingCard();
    }

}