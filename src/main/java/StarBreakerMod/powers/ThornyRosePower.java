package StarBreakerMod.powers;
import StarBreakerMod.StarBreakerMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

public class ThornyRosePower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:ThornyRosePower");
    public static final String POWER_ID = "ThornyRosePower";
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ThornyRosePower(AbstractCreature owner, int maxDefense) {
        this.name = NAME;
        this.ID = "ThornyRosePower";
        this.owner = owner;
        this.amount = maxDefense;
        updateDescription();
        loadRegion("juggernaut");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type == DamageInfo.DamageType.THORNS && target != this.owner){
            int block = Math.min(this.amount, damageAmount);
            AbstractDungeon.actionManager.addToTop((AbstractGameAction) new GainBlockAction(this.owner, this.owner, block));
        }
    }
}
