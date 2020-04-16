package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.actions.KakaShowCardAction;
import StarBreakerMod.monsters.minions.BaseFriendlyKaka;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class KakaDefendCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_R"); public static final String ID = "StarBreaker:KakaDefendCard";

    public KakaDefendCard() {
        super("StarBreaker:KakaDefendCard", cardStrings.NAME, new RegionName("red/skill/defend"), 1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);

        this.baseBlock = 3;
        this.tags.add(AbstractCard.CardTags.STARTER_DEFEND);
    }

    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractMonster m){
        addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) kaka, (AbstractCreature) kaka, this.block));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction) new GainBlockAction((AbstractCreature) p, (AbstractCreature) p, this.block));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaDefendCard();
    }
}