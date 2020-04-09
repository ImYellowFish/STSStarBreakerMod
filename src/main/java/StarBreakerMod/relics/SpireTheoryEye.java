 package StarBreakerMod.relics;
 import StarBreakerMod.StarBreakerMod;
 import basemod.abstracts.CustomRelic;
 import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
 import com.megacrit.cardcrawl.actions.animations.TalkAction;
 import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.CardLibrary;
 import com.megacrit.cardcrawl.potions.AbstractPotion;
 import com.megacrit.cardcrawl.random.Random;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.cards.CardQueueItem;

 import java.util.ArrayList;

 public class SpireTheoryEye extends CustomRelic implements ClickableRelic {
     public static final String ID = "StarBreaker:SpireTheoryEye";

     private Random cardRandom;
     private Random potionRandom;
     private Random cardDropRng;

     public SpireTheoryEye() {
         super("StarBreaker:SpireTheoryEye", "frozenEye.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.SOLID);
     }

     public String getUpdatedDescription() {
         this.description = this.DESCRIPTIONS[0];
         return this.description;
     }

     public void onRightClick() {
         flash();
         PredictMadness();
         PredictDiscovery();
         PredictWhiteNoise();
         PredictInfernalBlade();
         PredictDistraction();
         PredictForeignInfluence();
         PredictAlchemize();
         PredictColorlessPotion();
         PredictAttackPotion();
         PredictSkillPotion();
         PredictPowerPotion();
         PredictSpoon();
         PredictMummifiedHand();
     }

     public AbstractRelic makeCopy() {
         return new SpireTheoryEye();
     }

     // Preictions
     public void PredictDiscovery() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("Discovery");
         if (card != null) {
             SaveRandomState();
             ArrayList<AbstractCard> cards = generateCardChoices(null);
             String msg = GetNamesFromCards(card.name, cards);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictInfernalBlade() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("Infernal Blade");
         if (card != null) {
             SaveRandomState();
             AbstractCard tmpCard = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK);
             String msg = GetNamesFromCard(card.name, tmpCard);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictWhiteNoise() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("White Noise");
         if (card != null) {
             SaveRandomState();
             AbstractCard tmpCard = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER);
             String msg = GetNamesFromCard(card.name, tmpCard);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictDistraction() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("Distraction");
         if (card != null) {
             SaveRandomState();
             AbstractCard tmpCard = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL);
             String msg = GetNamesFromCard(card.name, tmpCard);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictForeignInfluence() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("ForeignInfluence");
         if (card != null) {
             SaveRandomState();
             ArrayList<AbstractCard> cards = generateFICardChoices();
             String msg = GetNamesFromCards(card.name, cards);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictColorlessPotion() {
         AbstractPotion potion = FindPotionByID("ColorlessPotion");
         if (potion != null) {
             SaveRandomState();
             ArrayList<AbstractCard> cards = generateColorlessCardChoices();
             String msg = GetNamesFromCards(potion.name, cards);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictAttackPotion() {
         AbstractPotion potion = FindPotionByID("AttackPotion");
         if (potion != null) {
             SaveRandomState();
             ArrayList<AbstractCard> cards = generateCardChoices(AbstractCard.CardType.ATTACK);
             String msg = GetNamesFromCards(potion.name, cards);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictSkillPotion() {
         AbstractPotion potion = FindPotionByID("SkillPotion");
         if (potion != null) {
             SaveRandomState();
             ArrayList<AbstractCard> cards = generateCardChoices(AbstractCard.CardType.SKILL);
             String msg = GetNamesFromCards(potion.name, cards);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictPowerPotion() {
         AbstractPotion potion = FindPotionByID("PowerPotion");
         if (potion != null) {
             SaveRandomState();
             ArrayList<AbstractCard> cards = generateCardChoices(AbstractCard.CardType.POWER);
             String msg = GetNamesFromCards(potion.name, cards);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     public void PredictSpoon() {
         if (AbstractDungeon.player.hasRelic("Strange Spoon")) {
             SaveRandomState();
             boolean proc = AbstractDungeon.cardRandomRng.randomBoolean();
             Talk(GetSpoonMsg(proc));
             RestoreRandomState();
         }
     }

     public void PredictMadness() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("Madness");
         if (card != null) {
             SaveRandomState();
             AbstractCard target = FindMadnessCard();
             if(target != null) {
                 Talk(GetNamesFromCard(card.name, target));
             }
             RestoreRandomState();
         }
     }

     public void PredictMummifiedHand() {
         if (AbstractDungeon.player.hasRelic("Mummified Hand")) {
             SaveRandomState();
             AbstractCard target = FindMummifiedHandCard();
             if(target != null) {
                 Talk(GetMummifiedHandMsg(target));
             }
             RestoreRandomState();
         }
     }

     public void PredictAlchemize() {
         AbstractCard card = AbstractDungeon.player.hand.findCardById("Venomology");
         if (card != null) {
             SaveRandomState();
             AbstractPotion potion = AbstractDungeon.returnRandomPotion(true);
             String msg = GetNamesFromPotion(card.name, potion);
             Talk(msg);
             StarBreakerMod.logger.info("Frozen eye:" + msg);
             RestoreRandomState();
         }
     }

     // Helpers
     // Tell the result
     private void Talk(String msg) {
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new RelicAboveCreatureAction((AbstractCreature) AbstractDungeon.player, this));
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new SFXAction("VO_CULTIST_1A"));
         AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new TalkAction(true, msg, 2.0F, 3.0F));
     }

     // Cards to string
     private String GetNamesFromCards(String srcName, ArrayList<AbstractCard> cards) {
         String msg = srcName + ": ";
         for (AbstractCard c : cards) {
             msg += (c.name + ",");
         }
         return msg;
     }

     private String GetNamesFromCard(String srcName, AbstractCard card) {
         String msg = srcName + ": " + card.name;
         return msg;
     }

     private String GetNamesFromPotion(String srcName, AbstractPotion potion) {
         String msg = srcName + ": " + potion.name;
         return msg;
     }

     // Get spoon msg
     private String GetSpoonMsg(boolean spoonProc) {
         AbstractRelic c = AbstractDungeon.player.getRelic("Strange Spoon");
         String msg;
         if(c != null)
             msg = c.name + ": ";
         else
             msg = "Spoon: ";
         if (spoonProc)
             msg += this.DESCRIPTIONS[1];
         else
             msg += this.DESCRIPTIONS[2];
         return msg;
     }

     private String GetMummifiedHandMsg(AbstractCard card){
         AbstractRelic c = AbstractDungeon.player.getRelic("Mummified Hand");
         String msg;
         if(c != null)
             msg = c.name + ": ";
         else
             msg = "MummifiedHand: ";
         return msg + card.name;
     }

     // Find potion by id
     private AbstractPotion FindPotionByID(String id) {
         for (AbstractPotion p : AbstractDungeon.player.potions) {
             if (p.ID == id)
                 return p;
         }
         return null;
     }

     // Functions for manipulating random seeds
     private void SaveRandomState() {
         cardRandom = AbstractDungeon.cardRandomRng.copy();
         potionRandom = AbstractDungeon.potionRng.copy();
         cardDropRng = AbstractDungeon.cardRng.copy();
     }

     private void RestoreRandomState() {
         AbstractDungeon.cardRandomRng = cardRandom.copy();
         AbstractDungeon.potionRng = AbstractDungeon.potionRng.copy();
         AbstractDungeon.cardRng = cardDropRng.copy();
     }


     // Functions for generating card choices or cards
     // Discovery, colorless
     private ArrayList<AbstractCard> generateColorlessCardChoices() {
         ArrayList<AbstractCard> derp = new ArrayList<>();

         while (derp.size() != 3) {
             boolean dupe = false;

             AbstractCard tmp = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();
             for (AbstractCard c : derp) {
                 if (c.cardID.equals(tmp.cardID)) {
                     dupe = true;
                     break;
                 }
             }
             if (!dupe) {
                 derp.add(tmp.makeCopy());
             }
         }

         return derp;
     }

     // Discovery, card type
     private ArrayList<AbstractCard> generateCardChoices(AbstractCard.CardType type) {
         ArrayList<AbstractCard> derp = new ArrayList<>();

         while (derp.size() != 3) {
             boolean dupe = false;
             AbstractCard tmp = null;
             if (type == null) {
                 tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
             } else {
                 tmp = AbstractDungeon.returnTrulyRandomCardInCombat(type);
             }
             for (AbstractCard c : derp) {
                 if (c.cardID.equals(tmp.cardID)) {
                     dupe = true;
                     break;
                 }
             }
             if (!dupe) {
                 derp.add(tmp.makeCopy());
             }
         }
         return derp;
     }

     // ForeignInfluence
     private ArrayList<AbstractCard> generateFICardChoices() {
         ArrayList<AbstractCard> derp = new ArrayList<>();


         while (derp.size() != 3) {
             AbstractCard.CardRarity cardRarity;
             boolean dupe = false;


             int roll = AbstractDungeon.cardRandomRng.random(99);
             if (roll < 55) {
                 cardRarity = AbstractCard.CardRarity.COMMON;
             } else if (roll < 85) {
                 cardRarity = AbstractCard.CardRarity.UNCOMMON;
             } else {
                 cardRarity = AbstractCard.CardRarity.RARE;
             }

             AbstractCard tmp = CardLibrary.getAnyColorCard(AbstractCard.CardType.ATTACK, cardRarity);

             for (AbstractCard c : derp) {
                 if (c.cardID.equals(tmp.cardID)) {
                     dupe = true;

                     break;
                 }
             }
             if (!dupe) {
                 derp.add(tmp.makeCopy());
             }
         }
         return derp;
     }

     // Madness
     public AbstractCard FindMadnessCard() {
         boolean betterPossible = false;
         boolean possible = false;
         for (AbstractCard c : AbstractDungeon.player.hand.group) {
             if (c.costForTurn > 0) {
                 betterPossible = true;
                 continue;
             }
             if (c.cost > 0) {
                 possible = true;
             }
         }
         if (betterPossible || possible) {
             return FindMadnessCardSearch(betterPossible);
         }
         return null;
     }

     private AbstractCard FindMadnessCardSearch(boolean better) {
         AbstractCard c = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
         if (better) {
             if (c.costForTurn > 0) {
                 return c;
             } else {
                 return FindMadnessCardSearch(better);
             }
         } else if (c.cost > 0) {
             return c;
         } else {
             return FindMadnessCardSearch(better);
         }
     }

     private AbstractCard FindMummifiedHandCard() {
         ArrayList<AbstractCard> groupCopy = new ArrayList<>();
         for (AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
             if (abstractCard.cost > 0 && abstractCard.costForTurn > 0 && !abstractCard.freeToPlayOnce) {
                 groupCopy.add(abstractCard);
                 continue;
             }
         }
         for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
             if (i.card != null) {
                 groupCopy.remove(i.card);
             }
         }
         if (!groupCopy.isEmpty()) {
             return groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
         } else {
             return null;
         }
     }
 }


