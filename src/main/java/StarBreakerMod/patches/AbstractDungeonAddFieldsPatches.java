package StarBreakerMod.patches;

import StarBreakerMod.minions.AbstractFriendlyMonster;
import StarBreakerMod.screens.KakaDebugScreen;
import StarBreakerMod.screens.KakaStatScreen;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon",
        method = SpirePatch.CLASS
)
public class AbstractDungeonAddFieldsPatches {
    // Custom screen data
    private static KakaStatScreen kakaStatScreen = new KakaStatScreen();
    private static KakaDebugScreen kakaDebugScreen = new KakaDebugScreen();

    public static SpireField<KakaStatScreen> f_kakaStatScreen = new SpireField<>(() -> kakaStatScreen);
    public static SpireField<KakaDebugScreen> f_kakaDebugScreen = new SpireField<>(() -> kakaDebugScreen);
}
