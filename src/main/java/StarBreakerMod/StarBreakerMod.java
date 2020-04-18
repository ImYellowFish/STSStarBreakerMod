package StarBreakerMod;

import StarBreakerMod.cards.*;
import StarBreakerMod.cards.kakaCards.KakaDefendCard;
import StarBreakerMod.cards.kakaCards.KakaStrikeCard;
import StarBreakerMod.events.BountyHunterEvent;
import StarBreakerMod.events.HadesTrialEvent;
import StarBreakerMod.minions.KakaMinionManager;
import StarBreakerMod.helpers.StarBreakerSetupHelper;
import StarBreakerMod.monsters.BookOfNursing;
import StarBreakerMod.monsters.GayCenturion;
import StarBreakerMod.relics.*;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.monsters.city.Healer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class StarBreakerMod implements  PostExhaustSubscriber, PostInitializeSubscriber,
    PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber {

    private int count, totalCount;

    private void resetCounts(){
        totalCount = count = 0;
    }

    public static final Logger logger = LogManager.getLogger(StarBreakerMod.class.getName());

    public StarBreakerMod(){
        BaseMod.subscribe(this);
        resetCounts();
    }

    public static void initialize(){
        new StarBreakerMod();
        KakaMinionManager.InitializeInstance();
        // BaseMod.addColor(AbstractCardEnumPatches.SBM_KAKA_BLUE, Color.BLUE, Color.BLACK, Color.DARK_GRAY, Color.BLACK, Color descBoxColor, Color trailVfxColor, Color glowColor, String attackBg, String skillBg, String powerBg, String energyOrb, String attackBgPortrait, String skillBgPortrait, String powerBgPortrait, String energyOrbPortrait, String cardEnergyOrb);
    }

    @Override
    public void receivePostExhaust(AbstractCard c){
        count++;
        totalCount++;
    }

    @Override
    public void receivePostBattle(AbstractRoom r){
        System.out.println((count + " cards were exhausted this battle, " +
                totalCount + " cards have been exhausted so far"));
        count = 0;
    }

    @Override
    public void receivePostDungeonInitialize(){
        resetCounts();
    }

    @Override
    public void receiveEditCards(){
//        BaseMod.addCard(new TestCard());
//        UnlockTracker.unlockCard(TestCard.ID);
//
//        BaseMod.addCard(new TestBottomCard());
//        UnlockTracker.unlockCard(TestBottomCard.ID);

        BaseMod.addCard(new SpireTheoryBufferCard());
        UnlockTracker.unlockCard(SpireTheoryBufferCard.ID);

        BaseMod.addCard(new SpireTheoryFinaleCard());
        UnlockTracker.unlockCard(SpireTheoryFinaleCard.ID);

        BaseMod.addCard(new SetThemUpCard());
        UnlockTracker.unlockCard(SetThemUpCard.ID);

        BaseMod.addCard(new ThornyRoseCard());
        UnlockTracker.unlockCard(ThornyRoseCard.ID);

        BaseMod.addCard(new BadJokeCard());
        UnlockTracker.unlockCard(BadJokeCard.ID);

        BaseMod.addCard(new KingsEdgeCard());
        UnlockTracker.unlockCard(KingsEdgeCard.ID);

        BaseMod.addCard(new BlackMailCard());
        UnlockTracker.unlockCard(BlackMailCard.ID);

        BaseMod.addCard(new ClashMasteryCard());
        UnlockTracker.unlockCard(ClashMasteryCard.ID);

        // Kaka cards
        BaseMod.addCard(new KakaStrikeCard());
        UnlockTracker.unlockCard(KakaStrikeCard.ID);

        BaseMod.addCard(new KakaDefendCard());
        UnlockTracker.unlockCard(KakaDefendCard.ID);
    }

    @Override
    public void receiveEditRelics(){
//        BaseMod.addRelic(new TestRelic(), RelicType.SHARED);
//        BaseMod.addRelic(new TestSummonRelic(), RelicType.SHARED);

        BaseMod.addRelic(new BloodScales(), RelicType.SHARED);
        BaseMod.addRelic(new BloodBoot(), RelicType.SHARED);
        BaseMod.addRelic(new SpireTheoryGamblingChip(), RelicType.SHARED);
        BaseMod.addRelic(new SpireTheoryAbacus(), RelicType.SHARED);
        BaseMod.addRelic(new SpireTheoryEye(), RelicType.SHARED);
        BaseMod.addRelic(new DarkVanArmor(), RelicType.SHARED);
        BaseMod.addRelic(new VampireMask(), RelicType.SHARED);
        BaseMod.addRelic(new DogStick(), RelicType.SHARED);
        BaseMod.addRelic(new BountyHuntersBadge(), RelicType.SHARED);

        // Special relics
        BaseMod.addRelic(new HadesTrialErebusMark(), RelicType.SHARED);
        BaseMod.addRelic(new HadesTrialTroveMark(), RelicType.SHARED);
        BaseMod.addRelic(new KakaCommanderBadge(), RelicType.SHARED);
        BaseMod.addRelic(new KakaDogTag(), RelicType.SHARED);
    }

    @Override
    public void receiveEditStrings() {
        // TODO: Localization
        BaseMod.loadCustomStringsFile(RelicStrings.class, "StarBreakerLocalization/StarBreaker-RelicStrings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, "StarBreakerLocalization/StarBreaker-CardStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "StarBreakerLocalization/StarBreaker-PowerStrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "StarBreakerLocalization/StarBreaker-MonsterStrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "StarBreakerLocalization/StarBreaker-UIStrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, "StarBreakerLocalization/StarBreaker-EventStrings.json");
    }

    public void receivePostInitialize() {
        // Add monsters and encounters here

        // Add monsters
        BaseMod.addMonster(BookOfNursing.ID, () -> new BookOfNursing());

        BaseMod.addMonster("GayCenturions", () -> new MonsterGroup(new AbstractMonster[]{
                new Healer(150.0F, 0.0F),
                new GayCenturion(-300.0F, 24.0F),
                new GayCenturion(-150.0F, 16.0F),
                new GayCenturion(0.0F, 8.0F),
        }));

        // Add special monsters
        StarBreakerSetupHelper.SetupBountyHunterMonsters();

        // Add encounters here
        BaseMod.addEliteEncounter(Exordium.ID, new MonsterInfo(BookOfNursing.ID, 5));
        BaseMod.addStrongMonsterEncounter(TheCity.ID, new MonsterInfo("GayCenturions", 5));

        // Add events
        BaseMod.addEvent(HadesTrialEvent.ID, HadesTrialEvent.class);

        // Add special events (will not be randomly generated)
        BaseMod.addEvent(BountyHunterEvent.ID, BountyHunterEvent.class, "Special");
    }
}
