package StarBreakerMod.powers;
import StarBreakerMod.StarBreakerMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DeepDarkFantasyPower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:DeepDarkFantasyPower");
    public static final String POWER_ID = "DeepDarkFantasyPower";
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int blockPlus = 0;

    public DeepDarkFantasyPower(AbstractCreature owner, int newAmount, int blockPlus) {
        this.name = NAME;
        this.ID = "DeepDarkFantasyPower";
        this.owner = owner;
        this.amount = newAmount;
        this.blockPlus = blockPlus;
        updateDescription();
        loadRegion("fading");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +  this.blockPlus + DESCRIPTIONS[2];
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && source == this.owner && target != this.owner &&
                !target.hasPower("Artifact")) {
            flash();
            addToTop((AbstractGameAction) new GainBlockAction(this.owner, this.owner, this.amount));
            addToTop((AbstractGameAction) new ApplyPowerAction(this.owner, this.owner, new DeepDarkFantasyPower(this.owner, this.blockPlus, this.blockPlus), this.blockPlus));
        }
    }
}
