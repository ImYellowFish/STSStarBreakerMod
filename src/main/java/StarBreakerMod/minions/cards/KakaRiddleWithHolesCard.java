package StarBreakerMod.minions.cards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;

public class KakaRiddleWithHolesCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Riddle With Holes");
    public static final String ID = "StarBreaker:KakaRiddleWithHoles";

    public KakaRiddleWithHolesCard() {
        super("StarBreaker:KakaRiddleWithHoles", cardStrings.NAME, new RegionName("green/attack/riddle_with_holes"), 2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 3;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaRiddleWithHolesCard();
    }
}