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
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class KakaClashCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Clash");
    public static final String ID = "StarBreaker:KakaClashCard";

    public KakaClashCard() {
        super("StarBreaker:KakaClashCard", cardStrings.NAME, "red/attack/clash", 0, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);

        this.baseDamage = 14;
        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    public AbstractCard makeCopy() {
        return new KakaClashCard();
    }

    @Override
    public void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature m) {
        if (m != null) {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        addToBot((AbstractGameAction) new DamageAction((AbstractCreature) m, new DamageInfo((AbstractCreature) kaka, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }
}
