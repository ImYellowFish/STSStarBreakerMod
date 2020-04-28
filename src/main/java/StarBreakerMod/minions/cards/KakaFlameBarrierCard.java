package StarBreakerMod.minions.cards;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import StarBreakerMod.minions.powers.KakaFlameBarrierPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

public class KakaFlameBarrierCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Flame Barrier");
    public static final String ID = "StarBreaker:KakaFlameBarrierCard";

    public KakaFlameBarrierCard() {
        super(ID, cardStrings.NAME, "red/skill/flame_barrier", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

        this.kakaCardType = KakaCardType.Hand_KeyDefensive;
        this.cardDrawGain = 0;
        this.energyGain = 0;
        this.baseBlock = 12;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            addToBot((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }

        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new KakaFlameBarrierPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            addToBot((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }

        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new KakaFlameBarrierPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(2);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaFlameBarrierCard();
    }

}