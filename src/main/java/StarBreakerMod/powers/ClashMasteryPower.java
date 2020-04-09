package StarBreakerMod.powers;
import StarBreakerMod.StarBreakerMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ClashMasteryPower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:ClashMasteryPower");
    public static final String POWER_ID = "ClashMasteryPower";
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean isLastCardClash;


    public ClashMasteryPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = "ClashMasteryPower";
        this.owner = owner;
        this.amount = newAmount;
        this.isLastCardClash = false;
        updateDescription();
        loadRegion("doubleDamage");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.isLastCardClash = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.isLastCardClash = card.cardID.equals("Clash");
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (this.isLastCardClash && damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL && target != this.owner){
            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new HealAction(this.owner, this.owner, (int)(damageAmount * this.amount / 100)));
        }
    }
}
