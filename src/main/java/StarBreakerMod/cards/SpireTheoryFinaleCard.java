package StarBreakerMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import StarBreakerMod.powers.SpireTheoryBufferPower;

import basemod.abstracts.CustomCard;

import java.util.ArrayList;
import java.util.Iterator;

public class SpireTheoryFinaleCard
        extends CustomCard {
    public static final String ID = "StarBreaker:SpireTheoryFinaleCard";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "green/attack/grand_finale";
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int DAMAGE = 50;
    private static final int UPGRADED_DELTA_DAMAGE = 10;

    public SpireTheoryFinaleCard() {
        super(ID, cardStrings.NAME, new CustomCard.RegionName(IMG_PATH), COST, cardStrings.DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new GrandFinalEffect(), 0.7F));
        } else {
            addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new GrandFinalEffect(), 1.0F));
        }
        addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public boolean checkFinaleCardPos(){
        AbstractPlayer p = AbstractDungeon.player;
        // Check whether this is the 4th or 5th card in hand.
        if (p.hand.size() >= 5) {
            int count = 0;
            boolean found = false;
            Iterator var2 = p.hand.group.iterator();

            while (var2.hasNext()) {
                AbstractCard card = (AbstractCard) var2.next();
                count++;
                if (card == this) {
                    found = true;
                    break;
                }
            }

            if(found) {
                if (count == 5) {
                    return true;
                }
                // When upgraded, can be played when 4th card in hand.
                if (this.upgraded && count == 4) {
                    return true;
                }
            }
        }
        return false;
    }

//    public boolean canPlay(AbstractCard c) {
//        boolean canPlay = super.canPlay(c);
//        if (!canPlay) {
//            return false;
//        }
//
//        if(this.checkFinaleCardPos())
//            return true;
//
//        // Check failed, show use message.
//        if (!this.upgraded) {
//            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
//        } else
//            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
//        return false;
//    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }

        if(this.checkFinaleCardPos())
            return true;

        // Check failed, show use message.
        if (!this.upgraded) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        } else
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
        // AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, this.cantUseMessage, true));
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpireTheoryFinaleCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DELTA_DAMAGE);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}