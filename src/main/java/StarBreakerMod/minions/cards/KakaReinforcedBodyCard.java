package StarBreakerMod.minions.cards;
import StarBreakerMod.actions.KakaReinforcedBodyAction;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KakaReinforcedBodyCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Reinforced Body");
    public static final String ID = "StarBreaker:KakaReinforcedBodyCard";

    public KakaReinforcedBodyCard() {
        super(ID, cardStrings.NAME, "blue/skill/reinforced_body", -1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.Hand_KeyDefensive;
        this.cardDrawGain = 0;
        this.energyGain = 0;
        this.baseBlock = 7;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        addToBot((AbstractGameAction)new KakaReinforcedBodyAction(p, this.block, this.energyOnUse));}

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaReinforcedBodyCard();
    }

}