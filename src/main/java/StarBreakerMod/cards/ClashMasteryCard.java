package StarBreakerMod.cards;

import StarBreakerMod.powers.ClashMasteryPower;
import StarBreakerMod.powers.CunningPower;
import StarBreakerMod.powers.LoseCunningPower;
import StarBreakerMod.powers.ThornyRosePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.AngerPower;

import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.abstracts.CustomCard;

public class ClashMasteryCard
        extends CustomCard {
    public static final String ID = "StarBreaker:ClashMasteryCard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "red/attack/blood_for_blood";
    private static final int COST = 2;
    private static final int LIFE_STEAL = 50;
    private static final int UPGRADED_PLUS_LIFE_STEAL = 50;
    private static final int NO_BLOCK_TURNS = 99;

    public ClashMasteryCard() {
        super(ID, cardStrings.NAME, new CustomCard.RegionName(IMG_PATH), COST, cardStrings.DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = LIFE_STEAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ClashMasteryPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoBlockPower(p, this.NO_BLOCK_TURNS, false), this.NO_BLOCK_TURNS));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ClashMasteryCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(this.UPGRADED_PLUS_LIFE_STEAL);
        }
    }
}