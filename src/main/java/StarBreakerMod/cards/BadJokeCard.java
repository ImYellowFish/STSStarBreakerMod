package StarBreakerMod.cards;

import StarBreakerMod.powers.CunningPower;
import StarBreakerMod.powers.LoseCunningPower;
import StarBreakerMod.powers.ThornyRosePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.AngerPower;

import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.abstracts.CustomCard;

public class BadJokeCard
        extends CustomCard {
    public static final String ID = "StarBreaker:BadJokeCard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "purple/skill/vigilance";
    private static final int COST = 0;
    private static final int DRAW_COUNT = 2;
    private static final int UPGRADED_PLUS_DRAW_COUNT = 1;
    private static final int SELF_DAMAGE = 3;

    public BadJokeCard() {
        super(ID, cardStrings.NAME, new CustomCard.RegionName(IMG_PATH), COST, cardStrings.DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.magicNumber = this.baseMagicNumber = DRAW_COUNT;
        this.damage = this.baseDamage = SELF_DAMAGE;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster m2 : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                // add damage from monster to self
                addToBot((AbstractGameAction) new DamageAction((AbstractCreature) p, new DamageInfo((AbstractCreature) m2, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction((AbstractCreature) p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BadJokeCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(this.UPGRADED_PLUS_DRAW_COUNT);
        }
    }
}