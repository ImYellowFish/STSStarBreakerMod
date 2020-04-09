package StarBreakerMod.cards;

import StarBreakerMod.actions.SpireTheoryGamblingChipAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class TestCard
        extends CustomCard {
    public static final String ID = "StarBreaker:TestCard";
    // private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // Get object containing the strings that are displayed in the game.
    public static final String NAME = "TestCard"; // cardStrings.NAME;
    public static final String DESCRIPTION = "This is My Card"; // cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/absorball.png";
    private static final int COST = 0;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int VULNERABLE_AMT = 1;
    private static final int UPGRADE_PLUS_VULNERABLE = 1;
    private static final int HEAL_AMOUNT = 5;

    public TestCard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED,
                AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        this.magicNumber = this.baseMagicNumber = VULNERABLE_AMT;

        this.damage=this.baseDamage = ATTACK_DMG;

        // this.setBackgroundTexture("img/absorball.png", "img/absorball_p.png");

        // this.setOrbTexture("img/custom_orb_small.png", "img/custom_orb_large.png");

        // this.setBannerTexture("img/custom_banner_large.png", "img/custom_banner_large.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(p, p, HEAL_AMOUNT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, m, new AngerPower(p, 2), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        if (!AbstractDungeon.player.hand.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new SpireTheoryGamblingChipAction((AbstractCreature) AbstractDungeon.player));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new TestCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
        }
    }
}