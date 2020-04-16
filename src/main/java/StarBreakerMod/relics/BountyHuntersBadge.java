 package StarBreakerMod.relics;
 
 import StarBreakerMod.helpers.BountyHuntersBadgeData;
 import basemod.CustomEventRoom;
 import basemod.abstracts.CustomSavable;
 import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
 import com.google.gson.reflect.TypeToken;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.events.AbstractImageEvent;
 import com.megacrit.cardcrawl.helpers.EventHelper;
 import com.megacrit.cardcrawl.map.MapEdge;
 import com.megacrit.cardcrawl.map.MapRoomNode;
 import com.megacrit.cardcrawl.relics.AbstractRelic;
 import com.megacrit.cardcrawl.rooms.AbstractRoom;

 import java.lang.reflect.Type;
 import java.util.ArrayList;
 import java.util.Iterator;

 public class BountyHuntersBadge extends AbstractRelic implements ClickableRelic, CustomSavable<BountyHuntersBadgeData> {
     public static final String ID = "StarBreaker:BountyHuntersBadge";
     public int clue;

     public static final int MIN_CLUE_REQUIREMENT = 3;
     public static final int MAX_CLUE_REQUIREMENT = 5;

     public BountyHuntersBadge() {
         super("StarBreaker:BountyHuntersBadge", "darkstone.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
         this.counter = 1;
         this.clue = -1;
         // updateClue();
     }

     public AbstractRelic makeCopy() {
         return new BountyHuntersBadge();
     }

     public void onEquip() {
         updateClue();
     }

     @Override
     public BountyHuntersBadgeData onSave()
     {
         BountyHuntersBadgeData data = new BountyHuntersBadgeData();
         data.counter = this.counter;
         data.clue = this.clue;
         data.usedUp = this.usedUp;
         return data;
     }

     @Override
     public void onLoad(BountyHuntersBadgeData data)
     {
        this.counter = data.counter;
        this.clue = data.clue;
        this.usedUp = data.usedUp;

        if(this.usedUp)
            usedUp();
     }

     public void onEnterRoom(AbstractRoom room) {
         updateClue();
     }

     public void updateClue(){
         this.clue--;
         if(this.clue < 0){
             this.clue = AbstractDungeon.cardRandomRng.random(this.MIN_CLUE_REQUIREMENT, this.MAX_CLUE_REQUIREMENT);
         }
         if(this.clue == 0){
             beginPulse();
         }
         else{
             stopPulse();
         }
     }

     @Override
     public Type savedType()
     {
         return new TypeToken<BountyHuntersBadgeData>(){}.getType();
     }

     public void onRightClick() {
         if(this.usedUp)
             return;
         flash();
         stopPulse();
         // debug
//         this.clue = 0;
         if(this.clue == 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMPLETE)
            InvokeEvent("StarBreaker:BountyHunterEvent");
     }

     public void InvokeEvent(String eventName) {
         AbstractDungeon.eventList.add(0, eventName);
         MapRoomNode cur = AbstractDungeon.currMapNode;
         MapRoomNode node = new MapRoomNode(cur.x, cur.y);
         node.room = new CustomEventRoom();
         ArrayList<MapEdge> curEdges = cur.getEdges();
         Iterator var8 = curEdges.iterator();

         while(var8.hasNext()) {
             MapEdge edge = (MapEdge)var8.next();
             node.addEdge(edge);
         }

         AbstractDungeon.player.releaseCard();
         AbstractDungeon.overlayMenu.hideCombatPanels();
         AbstractDungeon.previousScreen = null;
         AbstractDungeon.dynamicBanner.hide();
         AbstractDungeon.dungeonMapScreen.closeInstantly();
         AbstractDungeon.closeCurrentScreen();
         AbstractDungeon.topPanel.unhoverHitboxes();
         AbstractDungeon.fadeIn();
         AbstractDungeon.effectList.clear();
         AbstractDungeon.topLevelEffects.clear();
         AbstractDungeon.topLevelEffectsQueue.clear();
         AbstractDungeon.effectsQueue.clear();
         AbstractDungeon.dungeonMapScreen.dismissable = true;
         AbstractDungeon.nextRoom = node;
         AbstractDungeon.setCurrMapNode(node);
         AbstractDungeon.getCurrRoom().onPlayerEntry();
         AbstractDungeon.scene.nextRoom(node.room);
         AbstractDungeon.rs = node.room.event instanceof AbstractImageEvent ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;
     }
 }
