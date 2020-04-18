package StarBreakerMod.minions;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TestKaka extends AbstractFriendlyMonster {
    public static final String NAME = "TestKaka";
    public static final String ID = "TestKaka";

    public TestKaka(int x, int y){
        super(NAME, ID, 20, -8.0F, 10.0F, 230.0F, 240.0F, null, x, y);
        loadAnimation("images/monsters/theBottom/cultist/skeleton.atlas", "images/monsters/theBottom/cultist/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "waving", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn(){
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_CULTIST_1A"));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, "Kaka!", 1.0F, 2.0F));

        AbstractMonster target = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(
                (AbstractGameAction)new DamageAction(
                        target,
                        new DamageInfo(this, 5, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
}