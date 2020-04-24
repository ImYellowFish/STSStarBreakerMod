package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.actions.KakaAttackRandomEnemyAction;
import StarBreakerMod.actions.KakaWhirlwindAction;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;

public class KakaWhirlwindCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Whirlwind");
    public static final String ID = "StarBreaker:KakaWhirlwindCard";

    public KakaWhirlwindCard() {
        super("StarBreaker:KakaWhirlwindCard", cardStrings.NAME, new RegionName("red/attack/whirlwind"), -1, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 5;
        this.isMultiDamage = true;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        addToBot((AbstractGameAction)new KakaWhirlwindAction(p, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new KakaWhirlwindAction(p, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaWhirlwindCard();
    }
}