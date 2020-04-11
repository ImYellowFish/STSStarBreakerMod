package StarBreakerMod.helpers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.exordium.*;

public class StarBreakerSetupHelper{
    public static void SetupBountyHunterMonsters(){
        // Easy monsters
        BaseMod.addMonster("BountyHunter_Easy01", () -> new MonsterGroup(new AbstractMonster[]{
                new GremlinFat(150.0F, 0.0F),
                new GremlinThief(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Easy02", () -> new MonsterGroup(new AbstractMonster[]{
                new GremlinWarrior(150.0F, 0.0F),
                new Sentry(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Easy03", () -> new MonsterGroup(new AbstractMonster[]{
                new SlaverBlue(150.0F, 0.0F),
        }));

        BaseMod.addMonster("BountyHunter_Easy04", () -> new MonsterGroup(new AbstractMonster[]{
                new Taskmaster(150.0F, 0.0F),
        }));

        BaseMod.addMonster("BountyHunter_Easy05", () -> new MonsterGroup(new AbstractMonster[]{
                new Byrd(150.0F, 0.0F),
                new LouseDefensive(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Easy06", () -> new MonsterGroup(new AbstractMonster[]{
                new SpikeSlime_M(150.0F, 0.0F)
        }));


        // Normal monsters
        BaseMod.addMonster("BountyHunter_Normal01", () -> new MonsterGroup(new AbstractMonster[]{
                new GremlinWizard(150.0F, 0.0F),
                new Mugger(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Normal02", () -> new MonsterGroup(new AbstractMonster[]{
                new GremlinFat(150.0F, 0.0F),
                new Cultist(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Normal03", () -> new MonsterGroup(new AbstractMonster[]{
                new JawWorm(150.0F, 0.0F),
                new GremlinWarrior(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Normal04", () -> new MonsterGroup(new AbstractMonster[]{
                new Cultist(150.0F, 0.0F),
                new Cultist(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Normal05", () -> new MonsterGroup(new AbstractMonster[]{
                new Byrd(150.0F, 0.0F),
                new Healer(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Normal06", () -> new MonsterGroup(new AbstractMonster[]{
                new Centurion(150.0F, 0.0F),
                new GremlinWizard(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Hard01", () -> new MonsterGroup(new AbstractMonster[]{
                new Chosen(150.0F, 0.0F),
                new Taskmaster(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Hard02", () -> new MonsterGroup(new AbstractMonster[]{
                new ShelledParasite(150.0F, 0.0F),
                new Mugger(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Hard03", () -> new MonsterGroup(new AbstractMonster[]{
                new ShelledParasite(150.0F, 0.0F),
                new GremlinWarrior(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Hard04", () -> new MonsterGroup(new AbstractMonster[]{
                new Chosen(150.0F, 0.0F),
                new Healer(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Hard05", () -> new MonsterGroup(new AbstractMonster[]{
                new GremlinNob(150.0F, 0.0F),
                new Healer(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Hard06", () -> new MonsterGroup(new AbstractMonster[]{
                new ShelledParasite(150.0F, 0.0F),
                new SphericGuardian(-150.0F, 4.0F),
        }));

        BaseMod.addMonster("BountyHunter_Insane01", () -> new MonsterGroup(new AbstractMonster[]{
                new Chosen(150.0F, 0.0F),
                new Taskmaster(-150.0F, 4.0F),
                new Healer(-50.0F, 8.0F),
        }));

        BaseMod.addMonster("BountyHunter_Insane02", () -> new MonsterGroup(new AbstractMonster[]{
                new ShelledParasite(150.0F, 0.0F),
                new SnakePlant(-150.0F, 4.0F),
                new Chosen(-50.0F, 8.0F),
        }));

        BaseMod.addMonster("BountyHunter_Insane03", () -> new MonsterGroup(new AbstractMonster[]{
                new ShelledParasite(150.0F, 0.0F),
                new SnakePlant(-150.0F, 4.0F),
                new Chosen(-50.0F, 8.0F),
        }));

        BaseMod.addMonster("BountyHunter_Insane04", () -> new MonsterGroup(new AbstractMonster[]{
                new Snecko(150.0F, 0.0F),
                new GremlinFat(-150.0F, 4.0F),
                new Chosen(-50.0F, 8.0F),
        }));
    }
}