package StarBreakerMod.cards.kakaCards;

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

public class KakaSwordBoomerangCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Sword Boomerang");
    public static final String ID = "StarBreaker:KakaSwordBoomerangCard";

    public KakaSwordBoomerangCard() {
        super("StarBreaker:KakaSwordBoomerangCard", cardStrings.NAME, new RegionName("red/attack/sword_boomerang"), 1, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 3;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature m){
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new KakaAttackRandomEnemyAction(kaka,this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new KakaAttackRandomEnemyAction(p,this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaSwordBoomerangCard();
    }
}