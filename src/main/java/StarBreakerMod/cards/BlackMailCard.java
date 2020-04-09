package StarBreakerMod.cards;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.actions.BlackMailAction;
import StarBreakerMod.powers.CunningPower;
import StarBreakerMod.powers.LoseCunningPower;
import StarBreakerMod.powers.ThornyRosePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.AngerPower;

import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.abstracts.CustomCard;

public class BlackMailCard
        extends CustomCard {
    public static final String ID = "StarBreaker:BlackMailCard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "green/attack/endless_agony";
    private static final int COST = 1;
    private static final int GOLD_PER_DEBUFF = 1;

    public BlackMailCard() {
        super(ID, cardStrings.NAME, new CustomCard.RegionName(IMG_PATH), COST, cardStrings.DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = this.GOLD_PER_DEBUFF;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m != null){
            int count = 0;
            for(AbstractPower power : m.powers){
                if(power.type == AbstractPower.PowerType.DEBUFF && power.amount > 0){
                    count += power.amount;
                    StarBreakerMod.logger.info("Blackmail: " + power.name + ", " + power.amount);
                }
            }
            AbstractDungeon.actionManager.addToBottom(new BlackMailAction(m, p,count * this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlackMailCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}