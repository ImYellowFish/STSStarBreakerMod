package StarBreakerMod.minions.system;

import StarBreakerMod.minions.ai.KakaAIFactory;
import com.megacrit.cardcrawl.cards.CardSave;

import java.util.ArrayList;

public class KakaMinionData{
    // Saved in individual kaka relic.
    public String name;
    public boolean alive;
    public int currentHealth;
    public int maxHealth;
    public KakaAIFactory.KakaAIType aiType;

    public int level;
    public int exp;
    public int levelPoint;
    public ArrayList<CardSave> cards;

    public void cloneTo(KakaMinionData newData){
        newData.name = this.name;
        newData.alive = this.alive;
        newData.currentHealth = this.currentHealth;
        newData.maxHealth = this.maxHealth;

        newData.aiType = this.aiType;
        newData.level = this.level;
        newData.exp = this.exp;
        newData.levelPoint = this.levelPoint;

        // Cards are not cloned, instead directly load/save to deck
        // newData.cards = (ArrayList<CardSave>) this.cards.clone();
    }

    public String toString(){

        return "alive:" + alive +
                " NL ai:" + aiType +
                " NL level:" + level +
                " NL exp:" + exp +
                " NL lvp:" + levelPoint;
    }
}