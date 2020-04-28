package StarBreakerMod.minions.cards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class KakaConsecrateCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Consecrate");
    public static final String ID = "StarBreaker:KakaConsecrateCard";

    public KakaConsecrateCard() {
        super("StarBreaker:KakaConsecrateCard", cardStrings.NAME, new RegionName("purple/attack/consecrate"), 0, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_OptOffensive;
        this.baseDamage = 5;
        this.isMultiDamage = true;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        addToBot((AbstractGameAction)new SFXAction("ATTACK_HEAVY"));
        addToBot((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new CleaveEffect(), 0.1F));
        addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new SFXAction("ATTACK_HEAVY"));
        addToBot((AbstractGameAction)new VFXAction((AbstractCreature)p, (AbstractGameEffect)new CleaveEffect(), 0.1F));
        addToBot((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaConsecrateCard();
    }
}