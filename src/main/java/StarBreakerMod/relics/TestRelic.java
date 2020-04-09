package StarBreakerMod.relics;

import StarBreakerMod.StarBreakerMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import StarBreakerMod.cards.TestCard;
import StarBreakerMod.cards.TestBottomCard;

public class TestRelic extends CustomRelic {
    public static final String ID = "StarBreaker:TestRelic";
    public static final String IMG_PATH = "StarBreakerImages/scrapOoze.png";
    public static final String OUTLINE_IMG_PATH = "StarBreakerImages/scrapOozeOutline.png";
    private static final int HP_PER_CARD = 1;

    public TestRelic(){
        super(ID, new Texture(IMG_PATH), RelicTier.UNCOMMON, LandingSound.CLINK);
        this.description = this.getUpdatedDescription();
        this.counter = 0;

        this.tips.remove(0);
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public String getUpdatedDescription(){
        return "This is a Test Relic, count = " + this.counter;
    }

    @Override
    public void atBattleStartPreDraw(){
        this.counter += 2;
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(new TestCard(), false));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(new TestBottomCard(), false));
    }

    @Override
    public void atTurnStart(){
        StarBreakerMod.logger.info("Test relic atTurnStart");
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.counter));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TestRelic();
    }
}
