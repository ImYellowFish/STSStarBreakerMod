package StarBreakerMod.minions.cards;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;

public class KakaBarricadeCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Barricade");
    public static final String ID = "StarBreaker:KakaBarricadeCard";

    public KakaBarricadeCard() {
        super(ID, cardStrings.NAME, "red/power/barricade", 3, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.Hand_KeyPower;
        this.cardDrawGain = 0;
        this.energyGain = 0;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean powerExists = false;
        for (AbstractPower pow : p.powers) {
            if (pow.ID.equals("Barricade")) {
                powerExists = true;

                break;
            }
        }
        if (!powerExists) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new BarricadePower((AbstractCreature)p)));
        }
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        boolean powerExists = false;
        for (AbstractPower pow : p.powers) {
            if (pow.ID.equals("Barricade")) {
                powerExists = true;

                break;
            }
        }
        if (!powerExists) {
            addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new BarricadePower((AbstractCreature)p)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaBarricadeCard();
    }

}