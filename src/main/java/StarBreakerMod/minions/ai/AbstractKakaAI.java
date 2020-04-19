package StarBreakerMod.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.minions.BaseFriendlyKaka;
import StarBreakerMod.relics.KakaDogTag;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public abstract class AbstractKakaAI {
    public KakaDogTag dogTag;

    public AbstractKakaAI(KakaDogTag dogTag) {
        this.dogTag = dogTag;
    }

    // ----------------------------------------
    // Interfaces
    // ----------------------------------------
    public abstract void updateEnergyAndDrawOnTurnStart();

    public abstract void onKakaSpawn();

    public abstract void createIntent();

    public abstract void onKakaTakeTurn();

    public abstract void postKakaPlayCard(AbstractCreature target, AbstractCard card);

    public abstract void onKakaUpgrade();

    public abstract void onVictory();

    // ----------------------------------------
    // Helpers
    // ----------------------------------------
    public void PlayCard(AbstractCard card, AbstractMonster target) {
        StarBreakerMod.logger.info("Try play card " + card.name);
        KakaMinionManager mgr = KakaMinionManager.getInstance();
        mgr.playCard(card, this.GetOwner(), target);
    }

    public AbstractMonster getRandomMonsterTarget() {
        return AbstractDungeon.currMapNode.room.monsters.getRandomMonster((AbstractMonster) null, true, KakaMinionManager.getInstance().cardRandomRng);
    }

    public BaseFriendlyKaka GetOwner() {
        return dogTag.kaka;
    }

}