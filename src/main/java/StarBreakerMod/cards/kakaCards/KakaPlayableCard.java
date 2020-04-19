package StarBreakerMod.cards.kakaCards;

import StarBreakerMod.minions.BaseFriendlyKaka;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;

import java.util.ArrayList;

public abstract class KakaPlayableCard extends CustomCard {
    public enum KakaCardType {
        BaseStat_Energy,
        BaseStat_Draw,
        Hand_KeyPower,
        Hand_OptPower,
        Hand_KeyOffensive,
        Hand_KeyDefensive,
        Hand_OptOffensive,
        Hand_OptDefensive,
        Hand_EnergyGain,
        Hand_DrawGain,
    }

    // ----------------------------------------
    // Variables
    // ----------------------------------------
    public KakaCardType kakaCardType;
    public int energyGain = 0;
    public int cardDrawGain = 0;

    // ----------------------------------------
    // Interface
    // ----------------------------------------
    public abstract void OnKakaUseCard(BaseFriendlyKaka kaka, AbstractCreature target);


    // ----------------------------------------
    // Constructors
    // ----------------------------------------

    public KakaPlayableCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }
    public KakaPlayableCard(String id, String name, CustomCard.RegionName img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    // ----------------------------------------
    // Kaka card utilities
    // ----------------------------------------
    protected void applyKakaPowersToBlock(BaseFriendlyKaka kaka) {
        this.isBlockModified = false;
        float tmp = this.baseBlock;
        for (AbstractPower p : kaka.powers) {
            tmp = p.modifyBlock(tmp, this);
        }

        if (this.baseBlock != MathUtils.floor(tmp)) {
            this.isBlockModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }
        this.block = MathUtils.floor(tmp);
    }

    public void calculateKakaCardDamage(BaseFriendlyKaka kaka, AbstractMonster mo) {
        applyKakaPowersToBlock(kaka);
        this.isDamageModified = false;


        if (!this.isMultiDamage && mo != null) {
            float tmp = this.baseDamage;

//            for (AbstractRelic r : player.relics) {
//                tmp = r.atDamageModify(tmp, this);
//                if (this.baseDamage != (int)tmp) {
//                    this.isDamageModified = true;
//                }
//            }

            for (AbstractPower p : kaka.powers) {
                tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this);
            }

//
//            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseDamage != (int)tmp) {
                this.isDamageModified = true;
            }


            for (AbstractPower p : mo.powers) {
                tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this);
            }


            for (AbstractPower p : kaka.powers) {
                tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this);
            }


            for (AbstractPower p : mo.powers) {
                tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this);
            }


            if (tmp < 0.0F) {
                tmp = 0.0F;
            }


            if (this.baseDamage != MathUtils.floor(tmp)) {
                this.isDamageModified = true;
            }
            this.damage = MathUtils.floor(tmp);

        }
        else {

            ArrayList<AbstractMonster> m = (AbstractDungeon.getCurrRoom()).monsters.monsters;
            float[] tmp = new float[m.size()];
            int i;
            for (i = 0; i < tmp.length; i++) {
                tmp[i] = this.baseDamage;
            }


            for (i = 0; i < tmp.length; i++) {


//                for (AbstractRelic r : player.relics) {
//                    tmp[i] = r.atDamageModify(tmp[i], this);
//                    if (this.baseDamage != (int)tmp[i]) {
//                        this.isDamageModified = true;
//                    }
//                }


                for (AbstractPower p : kaka.powers) {
                    tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn, this);
                }


//                tmp[i] = player.stance.atDamageGive(tmp[i], this.damageTypeForTurn, this);
                if (this.baseDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }


            for (i = 0; i < tmp.length; i++) {
                for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
                    if (!((AbstractMonster)m.get(i)).isDying && !((AbstractMonster)m.get(i)).isEscaping) {
                        tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }


            for (i = 0; i < tmp.length; i++) {
                for (AbstractPower p : kaka.powers) {
                    tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn, this);
                }
            }


            for (i = 0; i < tmp.length; i++) {
                for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
                    if (!((AbstractMonster)m.get(i)).isDying && !((AbstractMonster)m.get(i)).isEscaping) {
                        tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn, this);
                    }
                }
            }


            for (i = 0; i < tmp.length; i++) {
                if (tmp[i] < 0.0F) {
                    tmp[i] = 0.0F;
                }
            }


            this.multiDamage = new int[tmp.length];
            for (i = 0; i < tmp.length; i++) {
                if (this.baseDamage != MathUtils.floor(tmp[i])) {
                    this.isDamageModified = true;
                }
                this.multiDamage[i] = MathUtils.floor(tmp[i]);
            }
            this.damage = this.multiDamage[0];
        }
    }
}