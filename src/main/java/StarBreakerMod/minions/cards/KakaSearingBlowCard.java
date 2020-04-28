package StarBreakerMod.minions.cards;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

public class KakaSearingBlowCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Searing Blow");
    public static final String ID = "StarBreaker:KakaSearingBlowCard";

    public KakaSearingBlowCard() {
        this(0);
    }

    public KakaSearingBlowCard(int upgrades) {
        super("StarBreaker:KakaSearingBlowCard", cardStrings.NAME, "red/attack/searing_blow", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 12;
        this.timesUpgraded = upgrades;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new SearingBlowEffect(m.hb.cX, m.hb.cY, this.timesUpgraded), 0.2F));
        }

        addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }


    public void upgrade() {
        upgradeDamage(4 + this.timesUpgraded);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }


    public boolean canUpgrade() {
        return true;
    }


    public AbstractCard makeCopy() {
        return new KakaSearingBlowCard(this.timesUpgraded);
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m) {
        if (m != null) {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new SearingBlowEffect(m.hb.cX, m.hb.cY, this.timesUpgraded), 0.2F));
        }

        addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }
}