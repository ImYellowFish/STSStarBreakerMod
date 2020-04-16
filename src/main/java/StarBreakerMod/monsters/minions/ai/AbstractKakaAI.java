package StarBreakerMod.monsters.minions.ai;

import StarBreakerMod.StarBreakerMod;
import StarBreakerMod.actions.KakaShowCardAction;
import StarBreakerMod.cards.kakaCards.KakaPlayableCard;
import StarBreakerMod.helpers.KakaMinionManager;
import StarBreakerMod.monsters.minions.BaseFriendlyKaka;
import StarBreakerMod.relics.KakaDogTag;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;


public abstract class AbstractKakaAI {
    public KakaDogTag dogTag;

    public AbstractKakaAI(KakaDogTag dogTag) {
        this.dogTag = dogTag;
    }
    // ----------------------------------------
    // Interfaces
    // ----------------------------------------
    public abstract void onKakaTakeTurn();


    // ----------------------------------------
    // Helpers
    // ----------------------------------------
    public void PlayCard(AbstractCard card, AbstractMonster target) {
        KakaMinionManager mgr = KakaMinionManager.getInstance(AbstractDungeon.player);
        mgr.PlayCard(card, this.GetOwner(), target);
    }

    public AbstractMonster GetRandomMonster() {
        return AbstractDungeon.currMapNode.room.monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
    }

    public BaseFriendlyKaka GetOwner() {
        return dogTag.kaka;
    }
}