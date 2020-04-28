package StarBreakerMod;

import StarBreakerMod.cards.*;
import StarBreakerMod.minions.cards.*;
import StarBreakerMod.events.BountyHunterEvent;
import StarBreakerMod.events.HadesTrialEvent;
import StarBreakerMod.minions.system.KakaMinionManager;
import StarBreakerMod.helpers.StarBreakerSetupHelper;
import StarBreakerMod.monsters.BookOfNursing;
import StarBreakerMod.monsters.GayCenturion;
import StarBreakerMod.patches.AbstractCardEnumPatches;
import StarBreakerMod.patches.RewardTypePatches;
import StarBreakerMod.relics.*;
import StarBreakerMod.rewards.KakaSingleCardReward;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.monsters.city.Healer;
import com.megacrit.cardcrawl.rewards.RewardSave;
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
        addCustomColor();
        KakaMinionManager.InitializeInstance();
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
        // Base
        BaseMod.addCard(new KakaStatEnergyCard(0,0));
        UnlockTracker.unlockCard(KakaStatEnergyCard.ID);

        BaseMod.addCard(new KakaStatDrawCard(0,0));
        UnlockTracker.unlockCard(KakaStatDrawCard.ID);

        BaseMod.addCard(new KakaStrikeCard());
        UnlockTracker.unlockCard(KakaStrikeCard.ID);

        BaseMod.addCard(new KakaDefendCard());
        UnlockTracker.unlockCard(KakaDefendCard.ID);

        // Attacks
        BaseMod.addCard(new KakaClashCard());
        UnlockTracker.unlockCard(KakaClashCard.ID);

        BaseMod.addCard(new KakaRampageCard());
        UnlockTracker.unlockCard(KakaRampageCard.ID);

        BaseMod.addCard(new KakaSearingBlowCard());
        UnlockTracker.unlockCard(KakaSearingBlowCard.ID);

        BaseMod.addCard(new KakaCarnageCard());
        UnlockTracker.unlockCard(KakaCarnageCard.ID);

        BaseMod.addCard(new KakaBludgeonCard());
        UnlockTracker.unlockCard(KakaBludgeonCard.ID);

        BaseMod.addCard(new KakaImmolateCard());
        UnlockTracker.unlockCard(KakaImmolateCard.ID);

        BaseMod.addCard(new KakaBackstabCard());
        UnlockTracker.unlockCard(KakaBackstabCard.ID);

        // Weak attacks
        BaseMod.addCard(new KakaConsecrateCard());
        UnlockTracker.unlockCard(KakaConsecrateCard.ID);

        BaseMod.addCard(new KakaClawCard());
        UnlockTracker.unlockCard(KakaClawCard.ID);

        BaseMod.addCard(new KakaBeamCellCard());
        UnlockTracker.unlockCard(KakaBeamCellCard.ID);

        BaseMod.addCard(new KakaNeutralizeCard());
        UnlockTracker.unlockCard(KakaNeutralizeCard.ID);

        // Opt attacks
        BaseMod.addCard(new KakaRageCard());
        UnlockTracker.unlockCard(KakaRageCard.ID);

        BaseMod.addCard(new KakaDoubleTapCard());
        UnlockTracker.unlockCard(KakaDoubleTapCard.ID);

        // Key Def
        BaseMod.addCard(new KakaFlameBarrierCard());
        UnlockTracker.unlockCard(KakaFlameBarrierCard.ID);

        BaseMod.addCard(new KakaLegSweepCard());
        UnlockTracker.unlockCard(KakaLegSweepCard.ID);

        BaseMod.addCard(new KakaImperviousCard());
        UnlockTracker.unlockCard(KakaImperviousCard.ID);

        BaseMod.addCard(new KakaEntrenchCard());
        UnlockTracker.unlockCard(KakaImperviousCard.ID);

        BaseMod.addCard(new KakaReinforcedBodyCard());
        UnlockTracker.unlockCard(KakaReinforcedBodyCard.ID);

        // Opt Def
        BaseMod.addCard(new KakaBootSequenceCard());
        UnlockTracker.unlockCard(KakaBootSequenceCard.ID);

        BaseMod.addCard(new KakaBodySlamCard());
        UnlockTracker.unlockCard(KakaBodySlamCard.ID);

        // Power
        BaseMod.addCard(new KakaEchoFormCard());
        UnlockTracker.unlockCard(KakaEchoFormCard.ID);

        BaseMod.addCard(new KakaRitualFormCard());
        UnlockTracker.unlockCard(KakaRitualFormCard.ID);

        BaseMod.addCard(new KakaInFlameCard());
        UnlockTracker.unlockCard(KakaInFlameCard.ID);

        BaseMod.addCard(new KakaDevaFormCard());
        UnlockTracker.unlockCard(KakaDevaFormCard.ID);

        BaseMod.addCard(new KakaBarricadeCard());
        UnlockTracker.unlockCard(KakaBarricadeCard.ID);

        // Poison
        BaseMod.addCard(new KakaCripplingPoisonCard());
        UnlockTracker.unlockCard(KakaCripplingPoisonCard.ID);

        BaseMod.addCard(new KakaNoxiousFumesCard());
        UnlockTracker.unlockCard(KakaNoxiousFumesCard.ID);

        BaseMod.addCard(new KakaEnvenomCard());
        UnlockTracker.unlockCard(KakaEnvenomCard.ID);

        BaseMod.addCard(new KakaCorpseExplosionCard());
        UnlockTracker.unlockCard(KakaCorpseExplosionCard.ID);

        BaseMod.addCard(new KakaPoisonedStabCard());
        UnlockTracker.unlockCard(KakaPoisonedStabCard.ID);

        // Strength
        BaseMod.addCard(new KakaFlexCard());
        UnlockTracker.unlockCard(KakaFlexCard.ID);

        BaseMod.addCard(new KakaLimitBreakCard());
        UnlockTracker.unlockCard(KakaLimitBreakCard.ID);

        BaseMod.addCard(new KakaSwordBoomerangCard());
        UnlockTracker.unlockCard(KakaSwordBoomerangCard.ID);

        BaseMod.addCard(new KakaRiddleWithHolesCard());
        UnlockTracker.unlockCard(KakaRiddleWithHolesCard.ID);

        BaseMod.addCard(new KakaRagnarokCard());
        UnlockTracker.unlockCard(KakaRagnarokCard.ID);

        BaseMod.addCard(new KakaWhirlwindCard());
        UnlockTracker.unlockCard(KakaWhirlwindCard.ID);

        BaseMod.addCard(new KakaWreathOfFlameCard());
        UnlockTracker.unlockCard(KakaWreathOfFlameCard.ID);

        // Energy
        BaseMod.addCard(new KakaAdrenalineCard());
        UnlockTracker.unlockCard(KakaAdrenalineCard.ID);

        BaseMod.addCard(new KakaOfferingCard());
        UnlockTracker.unlockCard(KakaOfferingCard.ID);

        BaseMod.addCard(new KakaBloodLettingCard());
        UnlockTracker.unlockCard(KakaBloodLettingCard.ID);

        BaseMod.addCard(new KakaTurboCard());
        UnlockTracker.unlockCard(KakaTurboCard.ID);

        BaseMod.addCard(new KakaDeusExMachinaCard());
        UnlockTracker.unlockCard(KakaDeusExMachinaCard.ID);

        // Add kaka cards to reward drop pool
        KakaMinionManager.getInstance().kakaRewardFactory.initialize();
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


        // add rewards
        BaseMod.registerCustomReward(RewardTypePatches.SBM_NEW_KAKA_CARD, (save)->{
            KakaDogTag dogTag = KakaMinionManager.getInstance().getDogTagByID(save.amount);
            AbstractCard card = CardLibrary.getCard(save.id).makeCopy();
            return new KakaSingleCardReward(dogTag, card);
        }, (reward)->{
            KakaSingleCardReward r = (KakaSingleCardReward)reward;
            return new RewardSave( r.type.toString(), r.card.cardID, r.dogTag.dogTagID, 0);
        });

    }


    private static void addCustomColor(){
        final Color BLUE_BG_COLOR = CardHelper.getColor(19, 45, 40);
        final Color BLUE_TYPE_BACK_COLOR = CardHelper.getColor(32, 91, 43);
        final Color BLUE_FRAME_COLOR = CardHelper.getColor(52, 123, 8);
        final Color BLUE_RARE_OUTLINE_COLOR = new Color(1.0F, 0.75F, 0.43F, 1.0F);
        final Color BLUE_DESC_BOX_COLOR = CardHelper.getColor(53, 58, 64);

        final Color BLUE_TRAIL_VFX_COLOR = CardHelper.getColor(53, 58, 84);
        final Color BLUE_BORDER_GLOW_COLOR = new Color(0.2F, 0.9F, 1.0F, 0.25F);

        BaseMod.addColor(AbstractCardEnumPatches.SBM_KAKA_BLUE,
                BLUE_BG_COLOR,
                BLUE_TYPE_BACK_COLOR,
                BLUE_FRAME_COLOR,
                BLUE_RARE_OUTLINE_COLOR,
                BLUE_DESC_BOX_COLOR,
                BLUE_TRAIL_VFX_COLOR,
                BLUE_BORDER_GLOW_COLOR,
                "StarBreakerImages/kaka_color/bg_attack_kaka.png",
                "StarBreakerImages/kaka_color/bg_skill_kaka.png",
                "StarBreakerImages/kaka_color/bg_power_kaka.png",
                "StarBreakerImages/kaka_color/card_kaka_orb.png",
                "StarBreakerImages/kaka_color/bg_attack_kaka_large.png",
                "StarBreakerImages/kaka_color/bg_skill_kaka_large.png",
                "StarBreakerImages/kaka_color/bg_power_kaka_large.png",
                "StarBreakerImages/kaka_color/card_kaka_orb_large.png",
                "StarBreakerImages/kaka_color/card_small_kaka_orb.png"
        );
    }
}
