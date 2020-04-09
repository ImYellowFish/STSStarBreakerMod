package StarBreakerMod.powers;
import StarBreakerMod.StarBreakerMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

public class BloodScalesPower extends AbstractPower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("StarBreaker:BloodScalesPower");
    public static final String POWER_ID = "BloodScalesPower";
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BloodScalesPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "BloodScalePower";
        this.owner = owner;
        this.amount = -1;
        updateDescription();
        loadRegion("sharpHide");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        StarBreakerMod.logger.info("BloodScales damage:" + damageAmount + ", type:" + info.type + ", flag:" + AbstractDungeon.actionManager.turnHasEnded);
        if(damageAmount >= 2 && AbstractDungeon.actionManager.turnHasEnded && target != this.owner){
            if (info.type == DamageInfo.DamageType.THORNS)
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new HealAction(this.owner, this.owner, (int)(damageAmount / 2)));
            //else if(info.type == DamageInfo.DamageType.HP_LOSS && damageAmount >= 4) {
                // AbstractDungeon.actionManager.addToTop((AbstractGameAction)new HealAction(this.owner, this.owner, (int)(damageAmount / 4)));
            //}
        }

    }
}
