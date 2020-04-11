package StarBreakerMod.events;

import StarBreakerMod.relics.BountyHuntersBadge;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BountyHunterEvent extends AbstractImageEvent {
    public static final String ID = "StarBreaker:BountyHunterEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("StarBreaker:BountyHunterEvent");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DIALOG_1 = DESCRIPTIONS[0];

    public static final int BOUNTY_TYPE_EASY = 0;
    public static final int BOUNTY_TYPE_NORMAL = 1;
    public static final int BOUNTY_TYPE_HARD = 2;
    public static final int BOUNTY_TYPE_IMPOSSIBLE = 3;
    public static final int BOUNTY_TYPE_FINAL = 4;

    public boolean decisionMade = false;

    public static final String[] EASY_ENCOUNTER_LIST = {
            "BountyHunter_Easy01",
            "BountyHunter_Easy02",
            "BountyHunter_Easy03",
            "BountyHunter_Easy04",
            "BountyHunter_Easy05",
            "BountyHunter_Easy06",
    };
    public static final String[] NORMAL_ENCOUNTER_LIST = {
            "BountyHunter_Normal01",
            "BountyHunter_Normal02",
            "BountyHunter_Normal03",
            "BountyHunter_Normal04",
            "BountyHunter_Normal05",
            "BountyHunter_Normal06",
    };
    public static final String[] HARD_ENCOUNTER_LIST = {
            "BountyHunter_Hard01",
            "BountyHunter_Hard02",
            "BountyHunter_Hard03",
            "BountyHunter_Hard04",
            "BountyHunter_Hard05",
            "BountyHunter_Hard06",
    };
    public static final String[] IMPOSSIBLE_ENCOUNTER_LIST = {
            "BountyHunter_Insane01",
            "BountyHunter_Insane02",
            "BountyHunter_Insane03",
            "BountyHunter_Insane04",
    };

    public static final String FINAL_ENCOUNTER = "3 Louse";
    public static final int[] GOLD_REWARDS = {50, 100, 200, 500, 500};
    public static final int[] EXP_REWARDS = {1, 2, 4, 8, 0};
    public static final int[] LEVEL_EXP_THRESHOLD = {1, 2, 4, 8, 16};

    public int currentBattleOptionsCount = 0;
    public String[] currentBattleOptions = {"", "", ""};
    public int[] currentBattleOptionsDiffculty = {0, 0, 0};

    public BountyHuntersBadge badgeRelic;

    public BountyHunterEvent() {
        super(NAME, DIALOG_1, "images/events/beggar.jpg");
        this.badgeRelic = (BountyHuntersBadge)AbstractDungeon.player.getRelic("StarBreaker:BountyHuntersBadge");

        generateBattleOptions();
    }

    protected void buttonEffect(int buttonPressed) {
        if(buttonPressed == currentBattleOptionsCount){
            // leave
            openMap();
        }
        else{
            int difficulty = currentBattleOptionsDiffculty[buttonPressed];
            // Add gold
            AbstractDungeon.getCurrRoom().addGoldToRewards(GOLD_REWARDS[difficulty]);
            // Add exp
            badgeRelic.counter += EXP_REWARDS[difficulty];
            // If final scene
            // Add relic
            if(difficulty == this.BOUNTY_TYPE_FINAL){
                badgeRelic.usedUp();
                AbstractRelic relicReward = RelicLibrary.getRelic("StarBreaker:DogStick").makeCopy();
                AbstractDungeon.getCurrRoom().addRelicToRewards(relicReward);
            }
            // fight
            BeginFight(currentBattleOptions[buttonPressed]);
        }
        this.imageEventText.clearRemainingOptions();
        this.decisionMade = true;
    }

    private void generateBattleOptions(){
        if(badgeRelic.counter >= LEVEL_EXP_THRESHOLD[4]){
            // Level 5
            currentBattleOptionsCount = 1;
            GenerateBattleOption(0, BOUNTY_TYPE_FINAL);
        }
        else if(badgeRelic.counter >= LEVEL_EXP_THRESHOLD[3]){
            // Level 4
            currentBattleOptionsCount = 3;
            GenerateBattleOption(0, BOUNTY_TYPE_NORMAL);
            GenerateBattleOption(1, BOUNTY_TYPE_HARD);
            GenerateBattleOption(2, BOUNTY_TYPE_IMPOSSIBLE);
        }
        else if(badgeRelic.counter >= LEVEL_EXP_THRESHOLD[2]){
            // Level 3
            currentBattleOptionsCount = 3;
            GenerateBattleOption(0, BOUNTY_TYPE_EASY);
            GenerateBattleOption(1, BOUNTY_TYPE_NORMAL);
            GenerateBattleOption(2, BOUNTY_TYPE_HARD);
        }
        else if(badgeRelic.counter >= LEVEL_EXP_THRESHOLD[1]){
            // Level 2
            currentBattleOptionsCount = 2;
            GenerateBattleOption(0, BOUNTY_TYPE_EASY);
            GenerateBattleOption(1, BOUNTY_TYPE_NORMAL);
        }
        else{
            // Level 1
            currentBattleOptionsCount = 1;
            GenerateBattleOption(0, BOUNTY_TYPE_EASY);
        }
        // Leave button
        this.imageEventText.setDialogOption(OPTIONS[9]);

    }

    private void GenerateBattleOption(int index, int difficulty){
        currentBattleOptionsDiffculty[index] = difficulty;
        switch (difficulty){
            case BOUNTY_TYPE_EASY:
                currentBattleOptions[index] = GetRandomEncounterID(EASY_ENCOUNTER_LIST);
                this.imageEventText.setDialogOption(OPTIONS[0] + GOLD_REWARDS[0] + OPTIONS[1]);
                break;
            case BOUNTY_TYPE_NORMAL:
                currentBattleOptions[index] = GetRandomEncounterID(NORMAL_ENCOUNTER_LIST);
                this.imageEventText.setDialogOption(OPTIONS[2] + GOLD_REWARDS[1] + OPTIONS[3]);
                break;
            case BOUNTY_TYPE_HARD:
                currentBattleOptions[index] = GetRandomEncounterID(HARD_ENCOUNTER_LIST);
                this.imageEventText.setDialogOption(OPTIONS[4] + GOLD_REWARDS[2] + OPTIONS[5]);
                break;
            case BOUNTY_TYPE_IMPOSSIBLE:
                currentBattleOptions[index] = GetRandomEncounterID(IMPOSSIBLE_ENCOUNTER_LIST);
                this.imageEventText.setDialogOption(OPTIONS[6] + GOLD_REWARDS[3] + OPTIONS[7]);
                break;
            case BOUNTY_TYPE_FINAL:
                currentBattleOptions[index] = FINAL_ENCOUNTER;
                this.imageEventText.setDialogOption(OPTIONS[8]);
                break;
        }
    }

    private String GetRandomEncounterID(String[] options){
        int length = options.length;
        int index = AbstractDungeon.cardRng.random(length - 1);
        return options[index];
    }

    private void BeginFight(String encounter){
        // Add gold counter relic
        (AbstractDungeon.getCurrRoom()).monsters = MonsterHelper.getEncounter(encounter);
        enterCombatFromImage();
        AbstractDungeon.lastCombatMetricKey = "Bounty Battle Lv" + currentBattleOptionsDiffculty;
    }

    public void reopen() {
        if(this.decisionMade) {
            AbstractDungeon.overlayMenu.proceedButton.show();
            AbstractDungeon.overlayMenu.proceedButton.setLabel(AbstractDungeon.TEXT[0]);
            AbstractDungeon.combatRewardScreen.clear();
        }
    }
}
