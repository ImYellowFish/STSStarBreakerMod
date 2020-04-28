package StarBreakerMod.minions.cards;

import StarBreakerMod.actions.KakaGashAction;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.GashAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

public class KakaClawCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Gash");
    public static final String ID = "StarBreaker:KakaClawCard";

    public KakaClawCard() {
        super("StarBreaker:KakaClawCard", cardStrings.NAME, new RegionName("blue/attack/claw"), 0, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_OptOffensive;
        this.baseDamage = 3;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        if (m != null) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }

        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot((AbstractGameAction)new KakaGashAction(p, this, this.magicNumber));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }

        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        addToBot((AbstractGameAction)new GashAction(this, this.magicNumber));

    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return new KakaClawCard();
    }
}