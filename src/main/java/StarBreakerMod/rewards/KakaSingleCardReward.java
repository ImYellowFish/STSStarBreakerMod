package StarBreakerMod.rewards;

import StarBreakerMod.patches.RewardTypePatches;
import StarBreakerMod.relics.KakaDogTag;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class KakaSingleCardReward extends CustomReward {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("StarBreaker:RewardItem");
    public KakaDogTag dogTag;
    public AbstractCard card;

    public KakaSingleCardReward(KakaDogTag kakaDogTag, AbstractCard card){
        super(kakaDogTag.img, "", RewardTypePatches.SBM_NEW_KAKA_CARD);

        this.dogTag = kakaDogTag;
        this.card = card;
        this.text = uiStrings.TEXT[2] + kakaDogTag.kakaData.name + uiStrings.TEXT[3] + card.name + uiStrings.TEXT[4];
    }


    public boolean claimReward(){
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            return false;
        }

        // Obtain kaka card
        this.dogTag.kakaDeck.addToBottom(this.card);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                this.card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));

        return true;
    }
}