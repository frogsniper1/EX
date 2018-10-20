package com.rs.net.decoders.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.Drop;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.others.FireSpirit;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.pet.Pet;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.player.BossPets;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Fishing;
import com.rs.game.player.actions.Fishing.FishingSpots;
import com.rs.game.player.actions.mining.LivingMineralMining;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.game.player.actions.runecrafting.SiphonActionCreatures;
import com.rs.game.player.actions.thieving.PickPocketAction;
import com.rs.game.player.actions.thieving.PickPocketableNPC;
import com.rs.game.player.content.Hunter;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.WorldVote;
import com.rs.game.player.dialogues.FremennikShipmaster;
import com.rs.io.InputStream;
import com.rs.rss.ForumThread;
import com.rs.utils.DummyRank;
import com.rs.utils.Logger;
import com.rs.utils.NPCDrops;
import com.rs.utils.NPCSpawns;
import com.rs.utils.RaffleWinner;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.utils.VotingBoard;

public class NPCHandler {

	public static void handleExamine(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		if(forceRun)
			player.setRun(forceRun);
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		sendConsoleDrops(npc,player);
		if (player.getRights() > 1) {
			player.getPackets().sendGameMessage(
					"NPC - [id=" + npc.getId() + ", loc=[" + npc.getX() + ", " + npc.getY() + ", " + npc.getPlane() + "]].");
		}
		player.getPackets().sendNPCMessage(0, npc, "It's a " + npc.getDefinitions().name + ".");
		if(player.isSpawnsMode()) {
			try {
				if(NPCSpawns.removeSpawn(npc)) {
					player.getPackets().sendGameMessage("Removed spawn!");
					return;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			player.getPackets().sendGameMessage("Failed removing spawn!");
		}
		if (Settings.DEBUG)
			Logger.log("NPCHandler", "examined npc: " + npcIndex+", "+npc.getId());
	}
	
	private static void sendConsoleDrops(NPC npc, Player player) {
		if (npc == null || npc.hasFinished() || !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		Drop[] drops = NPCDrops.getDrops(npc.getId());
		if(drops != null) {
			ArrayList<Drop> droplist = new ArrayList<Drop>();
		 for (Drop drop : drops) {
			 if(drops == null)
				 continue;
			 droplist.add(drop);
		 }
		 if(!droplist.isEmpty()) {
		 Collections.sort(droplist, new Comparator<Drop>() {
			    @Override
			    public int compare(Drop c1, Drop c2) {
			        return Double.compare(c2.getRate(), c1.getRate());
			    }
			});
		 int count = 0;
		 for(Drop drop : droplist) {
			 ItemDefinitions defs = ItemDefinitions.getItemDefinitions(drop.getItemId());
			 String dropName = defs.getName().toLowerCase();
			 count++;
			 player.getPackets().sendPanelBoxMessage("<col=00ff00>"+count+".)</col> ItemName: <col=ff0000>"+dropName+"</col> ItemID: <col=ff0000>"+drop.getItemId()+"</col> DropRate: <col=ff0000>"+drop.getRate()+"%");
		 }
		 player.getPackets().sendPanelBoxMessage("Total Drop Amount: <col=ff0000>"+count+"</col> NPCname: <col=ff0000>"+npc.getName()+"</col> NPClevel: <col=ff0000>"+npc.getCombatLevel());
		 droplist.clear();
		 }
		}
	}

	public static void handleOption1(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			if (npc.getId() == 15974) {
				player.getDialogueManager().startDialogue("ExtraBanker", npc.getId()); 
				npc.faceEntity(player);
				return;
			}
			if (npc.getId() != 12379 && npc.getId() != 15101 && npc.getId() != 15102 && npc.getId() != 15973)
			npc.faceEntity(player);
			player.getBankT().openBank();
			return;
		}
		if(SiphonActionCreatures.siphon(player, npc)) 
			return;
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				if (!player.getControlerManager().processNPCClick1(npc))
					return;
				FishingSpots spot = FishingSpots.forId(npc.getId() | 1 << 24);
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return; // its a spot, they wont face us
				}else if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					player.getActionManager().setAction(new LivingMineralMining((LivingRock) npc));
					return;
				}
				if (npc.getId() != 12379 && npc.getId() != 15101 && npc.getId() != 15102 && npc.getId() != 15973)
				npc.faceEntity(player);
				switch (npc.getId()) {
				case 6053:
				case 6054:
				case 6055:
				case 6056:
				case 6057:
				case 6058:
				case 6059:
				case 6060:
				case 6061:
				case 6062:
				case 6063:
				case 6064:
					Hunter.openJar(player, npc.getId());
					npc.setRespawnTask();
					break;
				}
				if (npc.getId() == 3709)
					player.getDialogueManager().startDialogue("MrEx", npc.getId());
				else if (npc.getId() == 949)
					player.getDialogueManager().startDialogue("QuestGuide", npc.getId(), null);
				else if (npc.getId() == 15451 && npc instanceof FireSpirit) {
					FireSpirit spirit = (FireSpirit) npc;
					spirit.giveReward(player);
				}
				else if (npc instanceof Pet) {
					Pet pet = (Pet) npc;
					if (pet != player.getPet()) {
						player.getPackets().sendGameMessage("This isn't your pet.");
						return;
					}
					if (player.getInventory().hasFreeSlots()) {
					player.setNextAnimation(new Animation(827));
					pet.pickup();
					} else 
						player.sm("Not enough inventory space.");
					
				}
				else if(npc.getId() == 12) {
					if (!player.isIronMan()) {
					if (player.getCollectLoanMoney() > 0)
						 player.getDialogueManager().startDialogue("CollectMoney", npc.getId());
					else
					 player.getDialogueManager().startDialogue("LendingList");	
					} else {
						npc.setNextAnimation(new Animation(2110));
						player.sm("The rank lender does not deal with ironmen.");
					}
				}
				else if (npc.getId() == 6893)
					player.getDialogueManager().startDialogue("PetKeeper");
                else if (npc.getId() == 14194)
                	player.getDialogueManager().startDialogue("MissionGive", npc.getId());
				else if (npc.getId() == 918)
					 player.sm("This guy had a minigame but whatever");
				else if (npc.getId() == 13955) {
					if (player.checkEradicator())
					  player.getDialogueManager().startDialogue("TheEradicator", npc.getId());	
					else if (player.isIronMan())
					 player.getDialogueManager().startDialogue("IronmanNPC", npc.getId());
				} else if (npc.getId() == 11583)
					  player.getDialogueManager().startDialogue("SuperMan", npc.getId());	
				else if (npc.getId() == 15972) {
					if (!player.getEliteChapterI().isComplete()) {
						player.getDialogueManager().startDialogue("CyndrithReveal", npc.getId());	
					}else if (!player.getEliteChapterII().isComplete()){
						player.getDialogueManager().startDialogue("CyndrithChapterII", npc.getId());
					}else if (!player.getEliteChapterIII().isComplete() || player.getEliteChapterIII().isComplete()){
						player.getDialogueManager().startDialogue("CyndrithChapterIII", npc.getId());
						}
				} else if (npc.getId() == 9361)
					player.getDialogueManager().startDialogue("GypsyAris", npc.getId());	
				else if (npc.getId() == 15971) 
					  player.getDialogueManager().startDialogue("LotteryDialogue", npc.getId());	
				else if (npc.getId() == 14) {
						npc.setNextForceTalk(new ForceTalk(""+player.getDisplayName() + " has "+ player.getTriviaPoints() + " trivia points!"));
		                player.setNextAnimation(new Animation(1374));
		                player.setNextGraphics(new Graphics(1702));
						player.getDialogueManager().startDialogue("TriviaShop");
				} else if (npc.getId() == 7836)
					ShopsHandler.openShop(player, 51);	
				else if (npc.getId() == 13789)
					ShopsHandler.openShop(player, 57);	
				else if (npc.getId() == 12379)
					player.getDialogueManager().startDialogue("BossSlayer", npc.getId());
				else if (npc.getId() == 15100)
					 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3968, 4822, 2));
				else if (npc.getId() == 3299)
					  player.getDialogueManager().startDialogue("FarmingManager", npc.getId());
				else if (npc.getId() == 734)
					  player.getDialogueManager().startDialogue("Bartender", npc.getId());	
				else if (npc.getId() == 7935)
					player.getDialogueManager().startDialogue("Hans", npc.getId());		
				else if (npc.getId() ==	340) 
					ShopsHandler.openShop(player, 51);					  
				else if (npc.getId() == 659)
					player.getDialogueManager().startDialogue("ExtremeShop", npc.getId());				
				else if (npc.getId() == 600)
					player.getDialogueManager().startDialogue("ExtremeShop1", npc.getId());
				else if (npc.getId() == 15101)
					player.sm("To donate something to help the economy, use an item on the fountain.");
				else if (npc.getId() == 15102)
					 player.getDialogueManager().startDialogue("DamageDealtGame");
				else if (npc.getId() == 375)
					 player.getDialogueManager().startDialogue("Frank", npc.getId());
				else if (npc.getId() == 8629)
					player.getDialogueManager().startDialogue("SandwichLady", npc.getId());
			   else if (npc.getId() == 5560)
				    player.getDialogueManager().startDialogue("ExpertDung", npc.getId());
				else if (npc.getId() == 9713)
					player.getDialogueManager().startDialogue("EnteringDung", npc.getId());
				else if (npc.getId() == 6988)
					player.getDialogueManager().startDialogue("Pikkupstix", npc.getId());
				else if (npc.getId() == 9085)
					player.getDialogueManager().startDialogue("Kuradal", false);
				else if (npc.getId() == 3374)
					player.getDialogueManager().startDialogue("Max", npc.getId(), null);
				else if (npc.getId() == 15786)
					ForumThread.openInterface(player);
				else if (npc.getId() == 9462)
					Strykewyrm.handleStomping(player, npc);
				else if (npc.getId() == 15402)
					player.getInventory().addItem(24227, 10000);
				else if (npc.getId() == 457)
					ShopsHandler.openShop(player, 27);
				else if (npc.getId() == 15251)
					ShopsHandler.openShop(player, 42);
				else if (npc.getId() == 1821)
					ShopsHandler.openShop(player, 43);
				else if (npc.getId() == 6971)
					ShopsHandler.openShop(player, 39);
				else if (npc.getId() == 1918)
                    ShopsHandler.openShop(player, 33);
				else if (npc.getId() == 15973)
					player.getDialogueManager().startDialogue("CyndrithsChest");
				else if (npc.getId() == 12377)
					  player.getDialogueManager().startDialogue("PumpkinPete", npc.getId());				  
				else if (npc.getId() == 12372)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());	
				else if (npc.getId() == 12375 && player.cake == 0)
					  player.getDialogueManager().startDialogue("Zabeth", npc.getId());
				else if (npc.getId() == 12375 && player.drink == 0)
					  player.getDialogueManager().startDialogue("Zabeth2", npc.getId());	
				else if (npc.getId() == 12375 && player.drink == 1)
					  player.getDialogueManager().startDialogue("Zabeth3", npc.getId());
				else if (npc.getId() == 8977 && player.drink == 0)
					  player.getDialogueManager().startDialogue("GrimReaper", npc.getId());
				else if (npc.getId() == 8977 && player.dust1 == 0)
					  player.getDialogueManager().startDialogue("GrimReaper2", npc.getId());
				else if (npc.getId() == 12378 && player.note == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete3", npc.getId());
				else if (npc.getId() == 8977 && player.dust1 == 1 && player.dust2 == 1 && player.dust3 == 1)
					  player.getDialogueManager().startDialogue("GrimReaper3", npc.getId());
				else if (npc.getId() == 12375 && player.doneevent == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 8977 && player.doneevent == 1)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				else if (npc.getId() == 12332)
					  player.getDialogueManager().startDialogue("PumpkinPete2", npc.getId());
				//
//| 1 << 24
else if (npc.getId() == 2600 << 2700) // 1 to 24 in java if im sure, change if not
player.getDialogueManager().startDialogue(
		"Man", npc.getId(), false);
				else if (npc.getId() == 3000 << 4000) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue("Man", npc.getId(), false);
			else if (npc.getId() == 9707)
					player.getDialogueManager().startDialogue("FremennikShipmaster", npc.getId(), true);
				else if (npc.getId() == 9708)
					player.getDialogueManager().startDialogue("FremennikShipmaster", npc.getId(), false);
				else if (npc.getId() == 8555)
					PlayerLook.openMageMakeOver(player);
				else if (npc.getId() == 320 << 400) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue(
							"Man", npc.getId(), false);
				else if (npc.getId() == 8080 << 8125) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue(
							"Man", npc.getId(), false);
				else if (npc.getId() == 13727)
                    player.getDialogueManager().startDialogue("Xuan", npc.getId());
				else if (npc.getId() == 13335)
                    player.getDialogueManager().startDialogue("MineShop", npc.getId());
					else if (npc.getId() == 13929)
                    player.getDialogueManager().startDialogue("Ariane", npc.getId());
				else if (npc.getId() == 13926)
                    player.getDialogueManager().startDialogue("Veteran", npc.getId());
				else if (npc.getId() == 230)
                    player.getDialogueManager().startDialogue("PortalTeleport", npc.getId());
					else if (npc.getId() == 456)
                    player.getDialogueManager().startDialogue("Switcher", npc.getId());
					else if (npc.getId() == 744)
						player.sm("The halloween quest is inactive.");
	                 //   player.getDialogueManager().startDialogue("Klarense", npc.getId());
					else if (npc.getId() == 13768)
                    player.getDialogueManager().startDialogue("Manager", npc.getId());
				else if (npc.getId() == 4526)
                    player.getDialogueManager().startDialogue("Bouquet", npc.getId());
				else if (npc.getId() == 755)
                    player.getDialogueManager().startDialogue("Morgan", npc.getId());
			else if (npc.getId() == 1918)
                    player.getDialogueManager().startDialogue("Mandrith", npc.getId());
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId(), false);
				else if (npc.getId() == 598)
					player.getDialogueManager().startDialogue("Hairdresser", npc.getId());
				else if (npc.getId() == 548)
					player.getDialogueManager().startDialogue("Thessalia", npc.getId());
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 4247)
					ShopsHandler.openShop(player, 29);
				else if (npc.getId() == 14330)
					ShopsHandler.openShop(player, 53);
				else if (npc.getId() == 3380)
					player.getDialogueManager().startDialogue("Cosmetic", npc.getId(), false);					
					else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 4288)
					ShopsHandler.openShop(player, 30);
				else if (npc.getId() == 587)
					ShopsHandler.openShop(player, 20);
				else if (npc.getId() == 1840)
					ShopsHandler.openShop(player, 38);
				else if (npc.getId() == 6537)
					ShopsHandler.openShop(player, 40);
				else if (npc.getId() == 6539)
					ShopsHandler.openShop(player, 41);					
				else if (npc.getId() == 1167)
					ShopsHandler.openShop(player, 35);
				else if (npc.getId() == 546)
					ShopsHandler.openShop(player, 10);
				else if (npc.getId() == 537)
					ShopsHandler.openShop(player, 9);
				else if (npc.getId() == 875)
					ShopsHandler.openShop(player, 13);
				else if (npc.getId() == 550)
					ShopsHandler.openShop(player, 14);
				else if (npc.getId() == 549)
					ShopsHandler.openShop(player, 15);
				else if (npc.getId() == 520)
					ShopsHandler.openShop(player, 5);						
				else if (npc.getId() == 13191)
					ShopsHandler.openShop(player, 17);
				else if (npc.getId() == 548)
					ShopsHandler.openShop(player, 18);
				else if (npc.getId() == 551)
					ShopsHandler.openShop(player, 35);
				else if (npc.getId() == 554)
					ShopsHandler.openShop(player, 36);
				else if (npc.getId() == 528 || npc.getId() == 529)
					ShopsHandler.openShop(player, 1);
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 2);		
				else if (npc.getId() == 538)
					ShopsHandler.openShop(player, 6);
				else if (npc.getId() == 14854)
					ShopsHandler.openShop(player, 7);
				else if (npc.getId() == 522 || npc.getId() == 523)
					ShopsHandler.openShop(player, 8);
				else if (npc.getId() == 1282)
					ShopsHandler.openShop(player, 44);	
				else if (npc.getId() == 530)
					ShopsHandler.openShop(player, 4);
				else if (npc.getId() == 5112)
					ShopsHandler.openShop(player, 26);
				else if (npc.getId() == 8864)
					ShopsHandler.openShop(player, 22);	
				else if (npc.getId() == 6892)
					ShopsHandler.openShop(player, 32);
				else if (npc.getId() == 519)
					ShopsHandler.openShop(player, 21);	
			    else if (npc.getId() == 15501)
					player.getDialogueManager().startDialogue("CashTicket", npc.getId(), 0);		
				else {
					for (Player players : World.getPlayers())
						if (Settings.DEBUG) {
							if (players.getUsername().equalsIgnoreCase("Ben")) {
						System.out.println("cliked 1 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
							}
						}
				}
			}
		}, npc.getSize()));
	}
	
	public static void handleOption2(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		if (npc.getDefinitions().name.contains("Banker")
				|| npc.getDefinitions().name.contains("banker")) {
			player.faceEntity(npc);
			if (!player.withinDistance(npc, 2))
				return;
			if (npc.getId() == 15974) {
				player.getDialogueManager().startDialogue("ExtraBanker", npc.getId()); 
				npc.faceEntity(player);
				return;
			}
			if (npc.getId() != 12379 && npc.getId() != 15101 && npc.getId() != 15102 && npc.getId() != 15973)
			npc.faceEntity(player);
			player.getBankT().openBank();
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				player.faceEntity(npc);
				FishingSpots spot = FishingSpots.forId(npc.getId() | (2 << 24));
				if (spot != null) {
					player.getActionManager().setAction(new Fishing(spot, npc));
					return;
				}
				PickPocketableNPC pocket = PickPocketableNPC.get(npc.getId());
				if (pocket != null) {
					player.getActionManager().setAction(
							new PickPocketAction(npc, pocket));
					return;
				}
				if (npc instanceof Familiar) {
					if (npc.getDefinitions().hasOption("store")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						player.getFamiliar().store();
					} else if (npc.getDefinitions().hasOption("cure")) {
						if (player.getFamiliar() != npc) {
							player.getPackets().sendGameMessage(
									"That isn't your familiar.");
							return;
						}
						if (!player.getPoison().isPoisoned()) {
							player.getPackets().sendGameMessage(
									"You aren't poisoned or diseased.");
							return;
						} else {
							player.getFamiliar().drainSpecial(2);
							player.addPoisonImmune(120);
						}
					}
					return;
				}
				if (npc.getId() != 12379 && npc.getId() != 15101 && npc.getId() != 15102)
				npc.faceEntity(player);
				if (!player.getControlerManager().processNPCClick2(npc))
					return;


				
				if (npc.getId() == 9707)
					FremennikShipmaster.sail(player, true);
				else if (npc.getId() == 9708)
					FremennikShipmaster.sail(player, false);
				else if (npc.getId() == 12379) {
					ShopsHandler.openShop(player, 58);
				} else if (npc.getId() == 15102)
					DummyRank.showRanks(player);
				else if (npc.getId() == 1 << 24) // 1 to 24 in java if im sure, change if not
					player.getDialogueManager().startDialogue(
							"Man", npc.getId(), false);
				else if (npc.getId() == 13455)
					player.getBankT().openBank();
				else if (npc.getId() == 598)
					PlayerLook.openHairdresserSalon(player);
				else if (npc.getId() == 6539) {
					if (WorldVote.isActive()) {
						long num = WorldVote.getTime() - (Utils.currentTimeMillis() - (1 * 60 * 60 * 1000));
						player.sm("The World Vote reward is currently active and will end in " + TimeUnit.MILLISECONDS.toMinutes(num) + " minutes.");
						} else {
						player.sm("World Vote count is currently at " + WorldVote.getVotes() + ". Hourly 1.5x xp starts when it reaches 200 votes.");
						}
				} else if (npc.getId() == 12) {
					if (!player.isIronMan()) {
					if (player.getCollectLoanMoney() > 0)
						 player.getDialogueManager().startDialogue("CollectMoney", npc.getId());
					else
					player.getDialogueManager().startDialogue("LendaRank");	
					} else {
						npc.setNextAnimation(new Animation(2110));
						player.sm("The rank lender does not deal with ironmen.");
					}
				} else if (npc.getId() == 585)
					ShopsHandler.openShop(player, 19);	
				else if (npc.getId() == 15786) {
					if (player.forumnews)
						player.forumnews = false;
					else
						player.forumnews = true;
					player.sm(player.forumnews ? "Forum news upon login is disabled." : "Forum news upon login is now enabled.");
				} else if (npc.getId() == 15971)
					RaffleWinner.showRanks(player);
				else if (npc.getId() == 526)
					ShopsHandler.openShop(player, 23);	
				else if (npc.getId() == 548)
					ShopsHandler.openShop(player, 18);
				else if (npc.getId() == 527)
					ShopsHandler.openShop(player, 24);	
				else if (npc.getId() == 576)
					ShopsHandler.openShop(player, 31);
				else if (npc.getId() == 13727)
                    player.getDialogueManager().startDialogue("Xuan", npc.getId());
				else if (npc.getId() == 6970)
					player.getDialogueManager().startDialogue("SummoningShop", npc.getId(), false);		
				else if (npc.getId() == 8555)
					PlayerLook.openMageMakeOver(player);
				else {
		
					if (Settings.DEBUG)
						System.out.println("cliked 2 at npc id : "
								+ npc.getId() + ", " + npc.getX() + ", "
								+ npc.getY() + ", " + npc.getPlane());
				}
			}
		}, npc.getSize()));
	}

	public static void handleOption3(final Player player, InputStream stream) {
		int npcIndex = stream.readUnsignedShort128();
		boolean forceRun = stream.read128Byte() == 1;
		final NPC npc = World.getNPCs().get(npcIndex);
		if (npc == null || npc.isCantInteract() || npc.isDead()
				|| npc.hasFinished()
				|| !player.getMapRegionsIds().contains(npc.getRegionId()))
			return;
		player.stopAll(false);
		if(forceRun)
			player.setRun(forceRun);
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				npc.resetWalkSteps();
				if (!player.getControlerManager().processNPCClick3(npc))
					return;
				player.faceEntity(npc);
				if (npc.getId() >= 8837 && npc.getId() <= 8839) {
					MiningBase.propect(player, "You examine the remains...", "The remains contain traces of living minerals.");
					return;
				}
				if (npc.getId() == 13727) {
				    player.getPackets().sendGameMessage("Title cleared.");
					player.getAppearence().setTitle(0);
					player.getDisplayName();
					player.getAppearence().generateAppearenceData();
				}	
				if (npc.getId() == 12) {
					if (player.senttoBank == false) {
						player.sm("You have no ranks waiting to be collected.");
					}
					if (player.senttoBank == true) {
						player.sm("<col=F21F1F> Your rank was sent to your bank.");
						player.senttoBank = false;
					}
				}
				if (npc.getId() == 6539) {
					VotingBoard.showRanks(player);
				}
				if (npc.getId() == 9085) {
					player.getInterfaceManager().sendInterface(164);
					player.getPackets().sendIComponentText(164, 20, "" + player.getSlayerPoints() + "");
					player.getPackets().sendIComponentText(164, 32, "(20 points)");
		            player.getPackets().sendIComponentText(164, 33, "(1500 points)");
		            player.getPackets().sendIComponentText(164, 34, "(35 points)");
		            player.getPackets().sendIComponentText(164, 35, "(35 points)");
		            player.getPackets().sendIComponentText(164, 36, "(35 points)");
				}
				if (npc.getId() == 3374) {
					ShopsHandler.openShop(player, 18);
				}
				if (npc.getId() != 12379 && npc.getId() != 15101 && npc.getId() != 15102)
				npc.faceEntity(player);
				if (npc.getId() == 548) {
					PlayerLook.openThessaliasMakeOver(player);
                }
				if (npc.getId() == 5532) {
					npc.setNextForceTalk(new ForceTalk("Senventior Disthinte Molesko!"));
					player.getControlerManager().startControler("SorceressGarden");
				} 
				if (npc.getId() == 15101)
					player.sm("xxx");
			}
		}, npc.getSize()));
		if (Settings.DEBUG)
			System.out.println("clicked 3 at npc id : "
					+ npc.getId() + ", " + npc.getX() + ", "
					+ npc.getY() + ", " + npc.getPlane());
		}
}
