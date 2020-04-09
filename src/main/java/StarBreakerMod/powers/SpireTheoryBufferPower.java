package StarBreakerMod.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class SpireTheoryBufferPower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:SpireTheoryBufferPower");
    public static final String POWER_ID = "SpireTheoryBufferPower";
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SpireTheoryBufferPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "SpireTheoryBufferPower";
        this.owner = owner;
        this.amount = 0;
        updateDescription();
        loadRegion("buffer");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        this.amount += damageAmount;
        return 0;
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction(this.owner, this.owner, new SpireTheoryBufferDelayDmgPower(this.owner, this.amount), 1));
        AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this.ID, this.amount));
    }
}