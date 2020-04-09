package StarBreakerMod.events;

import StarBreakerMod.StarBreakerMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.WarpedTongs;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class HadesTrialEvent extends AbstractImageEvent {
    public static final String ID = "StarBreaker:HadesTrialEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("StarBreaker:HadesTrialEvent");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final int MAX_GOLD_AMOUNT = 500;
    private static final float GOLD_DECAY_PER_SEC = 8F;

    private static final String DIALOG_1 = DESCRIPTIONS[0];
    public float currentGold = -2;
    public AbstractRelic goldChestRelic = null;

    public HadesTrialEvent() {
        super(NAME, DIALOG_1, "images/events/blacksmith.jpg");

        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1] + this.MAX_GOLD_AMOUNT + OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[3]);

        this.noCardsInRewards = true;
    }


    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_FORGE");
        }
    }

    public void update() {
        super.update();
        float dt = Gdx.graphics.getDeltaTime();
        // StarBreakerMod.logger.info(currentGold + ", " + dt);
        if(currentGold >= 0 && goldChestRelic != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            currentGold = currentGold - this.GOLD_DECAY_PER_SEC * dt;
            int intGold = Math.min(Math.max(0, (int)currentGold), this.MAX_GOLD_AMOUNT);
            goldChestRelic.setCounter(intGold);
        }
        else if(goldChestRelic != null){
            goldChestRelic.setCounter(0);
        }
    }

    protected void buttonEffect(int buttonPressed) {
        switch (buttonPressed) {
            case 0:
                BeginRelicFight();
                break;
            case 1:
                BeginGoldFight();
                break;
            case 2:
                openMap();
                break;
        }
        this.imageEventText.clearRemainingOptions();
    }

    private void BeginRelicFight(){
        // TODO: more random
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
                RelicLibrary.getRelic("StarBreaker:HadesTrialErebusMark").makeCopy());
        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter("Gremlin Gang");
        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
        this.noCardsInRewards = true;
        enterCombatFromImage();
        AbstractDungeon.lastCombatMetricKey = "Erebus Battle";
    }

    private void BeginGoldFight(){
        // Add gold counter relic
        currentGold = this.MAX_GOLD_AMOUNT + 6;
        AbstractRelic trove = RelicLibrary.getRelic("StarBreaker:HadesTrialTroveMark").makeCopy();
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F,
                trove);
        trove.setCounter(this.MAX_GOLD_AMOUNT);
        this.goldChestRelic = trove;

        // TODO: healer and healer
        // TODO: different monsters for each floor
        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter("Centurion and Healer");
        AbstractDungeon.getCurrRoom().rewards.clear();
        this.noCardsInRewards = false;
        enterCombatFromImage();
        AbstractDungeon.lastCombatMetricKey = "Erebus Battle";

    }
}
