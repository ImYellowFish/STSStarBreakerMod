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
import com.megacrit.cardcrawl.powers.*;

public class KakaCorpseExplosionCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Crippling Poison");
    public static final String ID = "StarBreaker:KakaCorpseExplosionCard";

    public KakaCorpseExplosionCard() {
        super(ID, cardStrings.NAME, "green/skill/corpse_explosion", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_OptOffensive;
        this.cardDrawGain = 0;
        this.energyGain = 0;

        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new PoisonPower((AbstractCreature)m, (AbstractCreature)p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new CorpseExplosionPower((AbstractCreature)m), 1, AbstractGameAction.AttackEffect.POISON));
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new PoisonPower((AbstractCreature)m, (AbstractCreature)p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new CorpseExplosionPower((AbstractCreature)m), 1, AbstractGameAction.AttackEffect.POISON));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaCorpseExplosionCard();
    }

}