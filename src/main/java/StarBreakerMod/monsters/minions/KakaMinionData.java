package StarBreakerMod.monsters.minions;

import StarBreakerMod.monsters.minions.ai.KakaAIFactory;
import com.megacrit.cardcrawl.cards.CardSave;

import java.util.ArrayList;

public class KakaMinionData{
    // Saved in individual kaka relic.
    public String name;
    public boolean alive;
    public int currentHealth;
    public int maxHealth;
    public int energyPerTurn;
    public int cardDrawPerTurn;
    public KakaAIFactory.KakaAIType aiType;

    public int level;
    public int exp;
    public ArrayList<CardSave> cards;

    public void cloneTo(KakaMinionData newData){
        newData.name = this.name;
        newData.alive = this.alive;
        newData.currentHealth = this.currentHealth;
        newData.maxHealth = this.maxHealth;
        newData.energyPerTurn = this.energyPerTurn;
        newData.cardDrawPerTurn = this.cardDrawPerTurn;

        newData.aiType = this.aiType;
        newData.level = this.level;
        newData.exp = this.exp;

        // Cards are not cloned, instead directly load/save to deck
        // newData.cards = (ArrayList<CardSave>) this.cards.clone();
    }

    public String toString(){
        return "alive:" + alive + " NL ai:" + aiType + " NL level:" + level + " NL exp:" + exp;
    }
}