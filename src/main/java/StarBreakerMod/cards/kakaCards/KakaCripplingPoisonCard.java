package StarBreakerMod.cards.kakaCards;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class KakaCripplingPoisonCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Crippling Poison");
    public static final String ID = "StarBreaker:KakaCripplingPoison";

    public KakaCripplingPoisonCard() {
        super(ID, cardStrings.NAME, "green/skill/crippling_poison", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        this.kakaCardType = KakaCardType.Hand_OptOffensive;
        this.cardDrawGain = 0;
        this.energyGain = 0;

        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) monster, (AbstractCreature) p, (AbstractPower) new PoisonPower((AbstractCreature) monster, (AbstractCreature) p, this.magicNumber), this.magicNumber));
                    addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) monster, (AbstractCreature) p, (AbstractPower) new WeakPower((AbstractCreature) monster, 2, false), 2));
                }
            }
        }
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) monster, (AbstractCreature) p, (AbstractPower) new PoisonPower((AbstractCreature) monster, (AbstractCreature) p, this.magicNumber), this.magicNumber));
                    addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) monster, (AbstractCreature) p, (AbstractPower) new WeakPower((AbstractCreature) monster, 2, false), 2));
                }
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaCripplingPoisonCard();
    }

}