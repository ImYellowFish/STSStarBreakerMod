package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.actions.KakaAttackRandomEnemyAction;
import StarBreakerMod.actions.KakaModifyDamageAction;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class KakaCarnageCard extends KakaPlayableCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Carnage");
    public static final String ID = "StarBreaker:KakaCarnageCard";

    public KakaCarnageCard() {
        super("StarBreaker:KakaCarnageCard", cardStrings.NAME, new RegionName("red/attack/carnage"), 2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnumPatches.SBM_KAKA_BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);

        this.kakaCardType = KakaCardType.Hand_KeyOffensive;
        this.baseDamage = 20;
        this.isEthereal = true;
    }

    public void OnKakaUseCard(BaseFriendlyKaka p, AbstractCreature m){
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
            for (int i = 0; i < 5; i++) {
                addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
            for (int i = 0; i < 5; i++) {
                addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
            for (int i = 0; i < 5; i++) {
                addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
            for (int i = 0; i < 5; i++) {
                addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }
        addToBot((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(8);
        }
    }


    public AbstractCard makeCopy() {
        return new KakaCarnageCard();
    }
}