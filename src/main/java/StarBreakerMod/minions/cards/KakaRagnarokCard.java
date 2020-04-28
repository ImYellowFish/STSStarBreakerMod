package StarBreakerMod.minions.cards;

import StarBreakerMod.actions.KakaAttackRandomEnemyAction;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;

public class KakaRagnarokCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Ragnarok");
    public static final String ID = "StarBreaker:KakaRiddleWithHoles";

    public KakaRagnarokCard() {
        super("StarBreaker:KakaRiddleWithHoles", cardStrings.NAME, new RegionName("purple/attack/ragnarok"), 3, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 5;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new KakaAttackRandomEnemyAction(p,this, AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new KakaAttackRandomEnemyAction(p,this, AbstractGameAction.AttackEffect.LIGHTNING));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaRagnarokCard();
    }
}