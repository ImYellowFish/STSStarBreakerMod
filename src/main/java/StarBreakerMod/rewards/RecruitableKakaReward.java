package StarBreakerMod.rewards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class RecruitableKakaReward extends RewardItem {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("StarBreaker:RewardItem");
    public static final int GOLD_COST_PER_LEVEL = 20;
    public int goldCost;

    public RecruitableKakaReward(AbstractRelic kakaDogTag){
        super((AbstractRelic) kakaDogTag);
        this.goldCost = 20;
        this.text = uiStrings.TEXT[0] + this.goldCost + uiStrings.TEXT[1];
    }

    public boolean claimReward(){
        if(AbstractDungeon.player.gold < this.goldCost){
            // Cant afford
            CardCrawlGame.sound.play("VO_MERCHANT_2A");
            return false;
        }

        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            return false;
        }

        // Obtain kaka
        this.relic.instantObtain();
        CardCrawlGame.metricData.addRelicObtainData(this.relic);
        AbstractDungeon.player.loseGold(this.goldCost);
        return true;
    }
}