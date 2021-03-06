 package StarBreakerMod.relics;
 import StarBreakerMod.StarBreakerMod;
 import StarBreakerMod.effects.KakaStatScreenEffect;
 import StarBreakerMod.minions.system.KakaMinionManager;
 import StarBreakerMod.minions.BaseFriendlyKaka;
 import StarBreakerMod.minions.ai.AbstractKakaAI;
 import StarBreakerMod.minions.system.KakaMinionData;
 import StarBreakerMod.patches.AbstractDungeonPatches;
 import basemod.abstracts.CustomRelic;
 import basemod.abstracts.CustomSavable;
 import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
 import com.megacrit.cardcrawl.cards.CardGroup;
 import com.megacrit.cardcrawl.cards.CardSave;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.CardLibrary;
 import com.megacrit.cardcrawl.helpers.PowerTip;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

 public class KakaDogTag extends CustomRelic implements ClickableRelic, CustomSavable<KakaMinionData> {
     public static final String ID = "StarBreaker:KakaDogTag";

     public int dogTagID = -1;
     public BaseFriendlyKaka kaka = null;
     public KakaMinionData kakaData;
     public CardGroup kakaDeck;
     public AbstractKakaAI kakaAI;

     public KakaDogTag() {
         super("StarBreaker:KakaDogTag", "cultistMask.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.SOLID);
//         kakaData = new KakaMinionData();
//         kakaDeck = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
//         kakaAI = KakaAIFactory.getAI(this, KakaAIFactory.KakaAIType.DEFAULT);
     }

     public void initializeKaka(KakaMinionData initData, CardGroup initDeck) {
         this.kakaData = initData;
         this.kakaDeck = initDeck;

         // Record
         AbstractPlayer p = AbstractDungeon.player;
         if (p != null && this.dogTagID < 0) {
             KakaMinionManager kakaMgr = KakaMinionManager.getInstance(p);
             this.dogTagID = kakaMgr.getNewKakaID();
             this.kakaAI = kakaMgr.generateKakaAIForRelic(this, this.kakaData.aiType);
             kakaMgr.registerKaka(this, this.kakaData, this.kakaDeck);
         }

         // Check alive
         if(!initData.alive){
             this.usedUp();
         }

         // Update name and description
         this.tips.clear();
         this.description = getUpdatedDescription();
         this.tips.add(new PowerTip(this.kakaData.name, this.description));
     }

     public String getUpdatedDescription() {
         if(this.kakaData == null) {
             return this.DESCRIPTIONS[0];
         }
         else{
             return kakaData.toString();
         }
     }

     public void onPlayerEndTurn(){
         this.description = getUpdatedDescription();
     }

     public void onEquip(){
         // When obtain, generate random kaka data for this
         KakaMinionManager.getInstance(AbstractDungeon.player).generateRecruitableKakaForRelic(this);
     }

     public void onVictory() {
         // Clean up
         this.kaka = null;
         this.description = getUpdatedDescription();
     }

     // Test
     public void onRightClick() {
         // TODO change to effect
         if(AbstractDungeon.isScreenUp && AbstractDungeon.screen == AbstractDungeonPatches.SBM_KakaStat) {
             for(AbstractGameEffect ge : AbstractDungeon.effectList){
                 if(ge instanceof KakaStatScreenEffect){
                     ge.isDone = true;
                 }
             }
             KakaMinionManager.getKakaStatScreen().closeSelf();
         }
         AbstractDungeon.effectList.add(new KakaStatScreenEffect(this));
     }

     public void SpawnKaka() {
         AbstractPlayer p = AbstractDungeon.player;
         if (p != null && !this.usedUp && this.kakaData.alive) {
             flash();
             BaseFriendlyKaka kaka = KakaMinionManager.getInstance(p).spawnBattleKaka(this,
                     this.kakaData, this.kakaDeck, this.kakaAI);
         }
         else{
             this.kaka = null;
         }
     }


     // Utilities
     public AbstractRelic makeCopy() {
         return new KakaDogTag();
     }

     @Override
     public KakaMinionData onSave() {
         KakaMinionData data = new KakaMinionData();
         this.kakaData.cloneTo(data);
         data.cards = kakaDeck.getCardDeck();
         return data;
     }

     @Override
     public void onLoad(KakaMinionData data) {
         StarBreakerMod.logger.info("Load kaka:" + data.name);
         KakaMinionData newData = new KakaMinionData();
         data.cloneTo(newData);

         // Load deck
         CardGroup newDeck = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
         for (CardSave s : data.cards) {
             newDeck.addToTop(CardLibrary.getCopy(s.id, s.upgrades, s.misc));
         }

         initializeKaka(newData, newDeck);
     }
 }


