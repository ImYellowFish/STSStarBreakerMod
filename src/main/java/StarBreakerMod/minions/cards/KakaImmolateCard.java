package StarBreakerMod.minions.cards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import StarBreakerMod.minions.powers.KakaBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class KakaImmolateCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Immolate");
    public static final String ID = "StarBreaker:KakaImmolateCard";

    public KakaImmolateCard() {
        super("StarBreaker:KakaImmolateCard", cardStrings.NAME, new RegionName("red/attack/immolate"), 2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 21;
        this.isMultiDamage = true;
        this.baseMagicNumber = this.magicNumber = 1;
        this.cardsToPreview = (AbstractCard)new Burn();
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new KakaBurnPower((AbstractCreature) p, this.magicNumber), this.magicNumber));

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p, (AbstractPower) new KakaBurnPower((AbstractCreature) p, this.magicNumber), this.magicNumber));

    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(8);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaImmolateCard();
    }
}