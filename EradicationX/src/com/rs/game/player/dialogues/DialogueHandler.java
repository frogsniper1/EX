package com.rs.game.player.dialogues;

import java.util.HashMap;

import com.rs.game.player.dialogues.instances.*;
import com.rs.utils.Logger;

public final class DialogueHandler {

	private static final HashMap<Object, Class<Dialogue>> handledDialogues = new HashMap<Object, Class<Dialogue>>();

	@SuppressWarnings("unchecked")
	public static final void init() {
		try {
			Class<Dialogue> value1 = (Class<Dialogue>) Class
					.forName(LevelUp.class.getCanonicalName());
			handledDialogues.put("LevelUp", value1);
			Class<Dialogue> value2 = (Class<Dialogue>) Class
					.forName(ZarosAltar.class.getCanonicalName());
			handledDialogues.put("ZarosAltar", value2);
			Class<Dialogue> value3 = (Class<Dialogue>) Class
					.forName(ClimbNoEmoteStairs.class.getCanonicalName());
			handledDialogues.put("ClimbNoEmoteStairs", value3);
			Class<Dialogue> value4 = (Class<Dialogue>) Class
					.forName(Banker.class.getCanonicalName());
			handledDialogues.put("Banker", value4);
			Class<Dialogue> value5 = (Class<Dialogue>) Class
					.forName(DestroyItemOption.class.getCanonicalName());
			handledDialogues.put("DestroyItemOption", value5);
			Class<Dialogue> value6 = (Class<Dialogue>) Class
					.forName(FremennikShipmaster.class.getCanonicalName());
			handledDialogues.put("FremennikShipmaster", value6);
			Class<Dialogue> value8 = (Class<Dialogue>) Class
					.forName(NexEntrance.class.getCanonicalName());
			handledDialogues.put("NexEntrance", value8);
			Class<Dialogue> value9 = (Class<Dialogue>) Class
					.forName(MagicPortal.class.getCanonicalName());
			handledDialogues.put("MagicPortal", value9);
			Class<Dialogue> value10 = (Class<Dialogue>) Class
					.forName(LunarAltar.class.getCanonicalName());
			handledDialogues.put("LunarAltar", value10);
			Class<Dialogue> value11 = (Class<Dialogue>) Class
					.forName(AncientAltar.class.getCanonicalName());
			handledDialogues.put("AncientAltar", value11);
			// TODO 12 and 13
			Class<Dialogue> value12 = (Class<Dialogue>) Class
					.forName(FletchingD.class.getCanonicalName());
			handledDialogues.put("FletchingD", value12);
			Class<Dialogue> value14 = (Class<Dialogue>) Class
					.forName(RuneScapeGuide.class.getCanonicalName());
			handledDialogues.put("RuneScapeGuide", value14);
			Class<Dialogue> value15 = (Class<Dialogue>) Class
					.forName(SurvivalExpert.class.getCanonicalName());
			handledDialogues.put("SurvivalExpert", value15);
			Class<Dialogue> value16 = (Class<Dialogue>) Class
					.forName(SimpleMessage.class.getCanonicalName());
			handledDialogues.put("SimpleMessage", value16);
			Class<Dialogue> value17 = (Class<Dialogue>) Class
					.forName(ItemMessage.class.getCanonicalName());
			handledDialogues.put("ItemMessage", value17);
			Class<Dialogue> value18 = (Class<Dialogue>) Class
					.forName(ClimbEmoteStairs.class.getCanonicalName());
			handledDialogues.put("ClimbEmoteStairs", value18);
			Class<Dialogue> value19 = (Class<Dialogue>) Class
					.forName(QuestGuide.class.getCanonicalName());
			handledDialogues.put("QuestGuide", value19);
			Class<Dialogue> value20 = (Class<Dialogue>) Class
					.forName(GemCuttingD.class.getCanonicalName());
			handledDialogues.put("GemCuttingD", value20);
			Class<Dialogue> value21 = (Class<Dialogue>) Class
					.forName(CookingD.class.getCanonicalName());
			handledDialogues.put("CookingD", value21);
			Class<Dialogue> value22 = (Class<Dialogue>) Class
					.forName(HerbloreD.class.getCanonicalName());
			handledDialogues.put("HerbloreD", value22);
			Class<Dialogue> value23 = (Class<Dialogue>) Class
					.forName(BarrowsD.class.getCanonicalName());
			handledDialogues.put("BarrowsD", value23);
			Class<Dialogue> value24 = (Class<Dialogue>) Class
					.forName(SmeltingD.class.getCanonicalName());
			handledDialogues.put("SmeltingD", value24);
			Class<Dialogue> value25 = (Class<Dialogue>) Class
					.forName(LeatherCraftingD.class.getCanonicalName());
			handledDialogues.put("LeatherCraftingD", value25);
			Class<Dialogue> value26 = (Class<Dialogue>) Class
					.forName(EnchantedGemDialouge.class.getCanonicalName());
			handledDialogues.put("EnchantedGemDialouge", value26);
			Class<Dialogue> value27 = (Class<Dialogue>) Class
					.forName(ForfeitDialouge.class.getCanonicalName());
			handledDialogues.put("ForfeitDialouge", value27);
			Class<Dialogue> value28 = (Class<Dialogue>) Class
					.forName(Transportation.class.getCanonicalName());
			handledDialogues.put("Transportation", value28);
			Class<Dialogue> value30 = (Class<Dialogue>) Class
					.forName(SimpleNPCMessage.class.getCanonicalName());
			handledDialogues.put("SimpleNPCMessage", value30);
			Class<Dialogue> value31 = (Class<Dialogue>) Class
					.forName(Transportation.class.getCanonicalName());
			handledDialogues.put("Transportation", value31);
			Class<Dialogue> value32 = (Class<Dialogue>) Class
					.forName(DTSpectateReq.class.getCanonicalName());
			handledDialogues.put("DTSpectateReq", value32);
			Class<Dialogue> value33 = (Class<Dialogue>) Class
					.forName(StrangeFace.class.getCanonicalName());
			handledDialogues.put("StrangeFace", value33);
			Class<Dialogue> value34 = (Class<Dialogue>) Class
					.forName(AncientEffigiesD.class.getCanonicalName());
			handledDialogues.put("AncientEffigiesD", value34);
			Class<Dialogue> value35 = (Class<Dialogue>) Class
					.forName(DTClaimRewards.class.getCanonicalName());
			handledDialogues.put("DTClaimRewards", value35);
			Class<Dialogue> value36 = (Class<Dialogue>) Class
					.forName(SetSkills.class.getCanonicalName());
			handledDialogues.put("SetSkills", value36);
			Class<Dialogue> value37 = (Class<Dialogue>) Class
					.forName(DismissD.class.getCanonicalName());
			handledDialogues.put("DismissD", value37);
			Class<Dialogue> value38 = (Class<Dialogue>) Class
					.forName(MrEx.class.getCanonicalName());
			handledDialogues.put("MrEx", value38);
			Class<Dialogue> value39 = (Class<Dialogue>) Class
					.forName(MakeOverMage.class.getCanonicalName());
			handledDialogues.put("MakeOverMage", value39);
			Class<Dialogue> value40 = (Class<Dialogue>) Class
					.forName(KaramjaTrip.class.getCanonicalName());
			handledDialogues.put("KaramjaTrip", value40);
			Class<Dialogue> value42 = (Class<Dialogue>) Class
					.forName(DagonHai.class.getCanonicalName());
			handledDialogues.put("DagonHai", value42);
			Class<Dialogue> value43 = (Class<Dialogue>) Class
					.forName(ExtremeShop.class.getCanonicalName());
			handledDialogues.put("ExtremeShop", value43);
			Class<Dialogue> value44 = (Class<Dialogue>) Class
					.forName(ExtremeShop1.class.getCanonicalName());
			handledDialogues.put("ExtremeShop1", value44);
			Class<Dialogue> value45 = (Class<Dialogue>) Class
					.forName(PumpkinPete.class.getCanonicalName());
			handledDialogues.put("PumpkinPete", value45);
			Class<Dialogue> value46 = (Class<Dialogue>) Class
					.forName(PumpkinPete2.class.getCanonicalName());
			handledDialogues.put("PumpkinPete2", value46);
			Class<Dialogue> value47 = (Class<Dialogue>) Class
					.forName(Zabeth.class.getCanonicalName());
			handledDialogues.put("Zabeth", value47);
			Class<Dialogue> value48 = (Class<Dialogue>) Class
					.forName(Zabeth2.class.getCanonicalName());
			handledDialogues.put("Zabeth2", value48);
			Class<Dialogue> value49 = (Class<Dialogue>) Class
					.forName(GrimReaper.class.getCanonicalName());
			handledDialogues.put("GrimReaper", value49);
			Class<Dialogue> value50 = (Class<Dialogue>) Class
					.forName(GrimReaper2.class.getCanonicalName());
			handledDialogues.put("GrimReaper2", value50);
			Class<Dialogue> value51 = (Class<Dialogue>) Class
					.forName(GrimReaper3.class.getCanonicalName());
			handledDialogues.put("GrimReaper3", value51);
			Class<Dialogue> value52 = (Class<Dialogue>) Class
					.forName(Zabeth3.class.getCanonicalName());
			handledDialogues.put("Zabeth3", value52);
			Class<Dialogue> value53 = (Class<Dialogue>) Class
					.forName(PumpkinPete3.class.getCanonicalName());
			handledDialogues.put("PumpkinPete3", value53);
			Class<Dialogue> value54 = (Class<Dialogue>) Class
					.forName(CashTicket.class.getCanonicalName());
			handledDialogues.put("CashTicket", value54);
			Class<Dialogue> value55 = (Class<Dialogue>) Class			
					.forName(DraconicClaws.class.getCanonicalName());
			handledDialogues.put("DraconicClaws", value55);
			Class<Dialogue> value56 = (Class<Dialogue>) Class
					.forName(SouthWestStairs.class.getCanonicalName());
			handledDialogues.put("SouthWestStairs", value56);
			Class<Dialogue> value57 = (Class<Dialogue>) Class			
					.forName(SouthEastStairs.class.getCanonicalName());
			handledDialogues.put("SouthEastStairs", value57);	
			Class<Dialogue> value58 = (Class<Dialogue>) Class
					.forName(NorthWestStairs.class.getCanonicalName());
			handledDialogues.put("NorthWestStairs", value58);	
			Class<Dialogue> value59 = (Class<Dialogue>) Class
					.forName(NorthEastStairs.class.getCanonicalName());
			handledDialogues.put("NorthEastStairs", value59);
			Class<Dialogue> value60 = (Class<Dialogue>) Class
					.forName(MeetingDoorOne.class.getCanonicalName());
			handledDialogues.put("MeetingDoorOne", value60);			
			Class<Dialogue> value61 = (Class<Dialogue>) Class
					.forName(MeetingDoorTwo.class.getCanonicalName());
			handledDialogues.put("MeetingDoorTwo", value61);
			Class<Dialogue> value62 = (Class<Dialogue>) Class
					.forName(MeetingDoorThree.class.getCanonicalName());
			handledDialogues.put("MeetingDoorThree", value62);
			Class<Dialogue> value63 = (Class<Dialogue>) Class
					.forName(MeetingDoorFour.class.getCanonicalName());
			handledDialogues.put("MeetingDoorFour", value63);
			Class<Dialogue> value64 = (Class<Dialogue>) Class
					.forName(FirstFloorGate.class.getCanonicalName());
			handledDialogues.put("FirstFloorGate", value64);
			Class<Dialogue> value65 = (Class<Dialogue>) Class
					.forName(TicketTableLeft.class.getCanonicalName());
			handledDialogues.put("TicketTableLeft", value65);		
	
			
			handledDialogues.put("Ned", (Class<Dialogue>) Class
					.forName(Ned.class.getCanonicalName()));
			handledDialogues.put("CrystalKey1", (Class<Dialogue>) Class
					.forName(CrystalKey1.class.getCanonicalName()));
			handledDialogues.put("Manager", (Class<Dialogue>) Class
					.forName(Manager.class.getCanonicalName()));
			handledDialogues.put("Hairymonkey", (Class<Dialogue>) Class
					.forName(Hairymonkey.class.getCanonicalName()));
			handledDialogues.put("HairyPortal", (Class<Dialogue>) Class
					.forName(HairyPortal.class.getCanonicalName()));
			handledDialogues.put("CombineKey", (Class<Dialogue>) Class
					.forName(CombineKey.class.getCanonicalName()));
			handledDialogues.put("Bartender", (Class<Dialogue>) Class
					.forName(Bartender.class.getCanonicalName()));
			
			
			handledDialogues.put("PortalTeleport", (Class<Dialogue>) Class
					.forName(PortalTeleport.class.getCanonicalName()));
			handledDialogues.put("Switcher", (Class<Dialogue>) Class
					.forName(Switcher.class.getCanonicalName()));
			handledDialogues.put("MissionMaster", (Class<Dialogue>) Class
					.forName(MissionMaster.class.getCanonicalName()));
			
			handledDialogues.put("Klarense", (Class<Dialogue>) Class
					.forName(Klarense.class.getCanonicalName()));

			handledDialogues.put("MaxedPlayer", (Class<Dialogue>) Class
					.forName(MaxedPlayer.class.getCanonicalName()));
			handledDialogues.put("Morgan", (Class<Dialogue>) Class
					.forName(Morgan.class.getCanonicalName()));
			handledDialogues.put("Bouquet", (Class<Dialogue>) Class
					.forName(Bouquet.class.getCanonicalName()));
		
			handledDialogues.put("Noticeboard", (Class<Dialogue>) Class
					.forName(Noticeboard.class.getCanonicalName()));
			handledDialogues.put("Veteran", (Class<Dialogue>) Class
					.forName(Veteran.class.getCanonicalName()));

			handledDialogues.put("MissionGive", (Class<Dialogue>) Class
					.forName(MissionGive.class.getCanonicalName()));

			handledDialogues.put("XPBook", (Class<Dialogue>) Class
					.forName(XPBook.class.getCanonicalName()));

			handledDialogues.put("Pikkupstix", (Class<Dialogue>) Class
					.forName(Pikkupstix.class.getCanonicalName()));

			handledDialogues.put("Frank", (Class<Dialogue>) Class
					.forName(Frank.class.getCanonicalName()));

			handledDialogues.put("DungeonTutorial", (Class<Dialogue>) Class
					.forName(DungeonTutorial.class.getCanonicalName()));

			handledDialogues.put("EstateAgent", (Class<Dialogue>) Class
					.forName(EstateAgent.class.getCanonicalName()));

			handledDialogues.put("SandwichLady", (Class<Dialogue>) Class
					.forName(SandwichLady.class.getCanonicalName()));
			
			handledDialogues.put("100mticket", (Class<Dialogue>) Class
					.forName(SandwichLady.class.getCanonicalName()));			

			
			handledDialogues.put("Starter", (Class<Dialogue>) Class
					.forName(Starter.class.getCanonicalName()));

			handledDialogues.put("Ariane", (Class<Dialogue>) Class
					.forName(Ariane.class.getCanonicalName()));
	
			handledDialogues.put("MineShop", (Class<Dialogue>) Class
					.forName(MineShop.class.getCanonicalName()));

			handledDialogues.put("FlowerPickup", (Class<Dialogue>) Class
					.forName(FlowerPickup.class.getCanonicalName()));
			handledDialogues.put("SkillRack", (Class<Dialogue>) Class
					.forName(SkillRack.class.getCanonicalName()));
		
			// teleports for donator zone made by fatal resort
			handledDialogues.put("clan_wars_view", (Class<Dialogue>) Class
					.forName(ClanWarsViewing.class.getCanonicalName()));
			handledDialogues.put("Stele", (Class<Dialogue>) Class
					.forName(Stele.class.getCanonicalName()));
			handledDialogues.put("RepairChest", (Class<Dialogue>) Class
					.forName(RepairChest.class.getCanonicalName()));						
			handledDialogues.put("ATele", (Class<Dialogue>) Class
					.forName(ATele.class.getCanonicalName()));			
			handledDialogues.put("CTele", (Class<Dialogue>) Class
					.forName(CTele.class.getCanonicalName()));			
			handledDialogues.put("BTele", (Class<Dialogue>) Class
					.forName(BTele.class.getCanonicalName()));			
			handledDialogues.put("GradTele", (Class<Dialogue>) Class
					.forName(GradTele.class.getCanonicalName()));			
			handledDialogues.put("ZTele", (Class<Dialogue>) Class
					.forName(ZTele.class.getCanonicalName()));
			handledDialogues.put("ExTele", (Class<Dialogue>) Class
					.forName(ExTele.class.getCanonicalName()));	
			handledDialogues.put("NecTele", (Class<Dialogue>) Class
					.forName(NecTele.class.getCanonicalName()));						
			handledDialogues.put("SupTele", (Class<Dialogue>) Class
					.forName(SupTele.class.getCanonicalName()));
			handledDialogues.put("BlinkTele", (Class<Dialogue>) Class
					.forName(BlinkTele.class.getCanonicalName()));	
			handledDialogues.put("EraTele", (Class<Dialogue>) Class
					.forName(EraTele.class.getCanonicalName()));
			handledDialogues.put("KBDTele", (Class<Dialogue>) Class
					.forName(KBDTele.class.getCanonicalName()));					
			handledDialogues.put("DiceBag", (Class<Dialogue>) Class
					.forName(DiceBag.class.getCanonicalName()));
			handledDialogues.put("PartyPete", (Class<Dialogue>) Class
					.forName(PartyPete.class.getCanonicalName()));
			handledDialogues.put("PartyRoomLever", (Class<Dialogue>) Class
					.forName(PartyRoomLever.class.getCanonicalName()));
			handledDialogues.put("DrogoDwarf", (Class<Dialogue>) Class
					.forName(DrogoDwarf.class.getCanonicalName()));
			handledDialogues.put("GeneralStore", (Class<Dialogue>) Class
					.forName(GeneralStore.class.getCanonicalName()));
			handledDialogues.put("Nurmof", (Class<Dialogue>) Class
					.forName(Nurmof.class.getCanonicalName()));
			handledDialogues.put("BootDwarf", (Class<Dialogue>) Class
					.forName(BootDwarf.class.getCanonicalName()));
			handledDialogues.put("MiningGuildDwarf", (Class<Dialogue>) Class
					.forName(MiningGuildDwarf.class.getCanonicalName()));
			handledDialogues.put("Man", (Class<Dialogue>) Class
					.forName(Man.class.getCanonicalName()));
			handledDialogues.put("Guildmaster", (Class<Dialogue>) Class
					.forName(Guildmaster.class.getCanonicalName()));
			handledDialogues.put("Scavvo", (Class<Dialogue>) Class
					.forName(Scavvo.class.getCanonicalName()));
			handledDialogues.put("Valaine", (Class<Dialogue>) Class
					.forName(Valaine.class.getCanonicalName()));
			handledDialogues.put("Hura", (Class<Dialogue>) Class
					.forName(Hura.class.getCanonicalName()));
			handledDialogues.put("TzHaarMejJal", (Class<Dialogue>) Class
					.forName(TzHaarMejJal.class.getCanonicalName()));
			handledDialogues.put("TzHaarMejKah", (Class<Dialogue>) Class
					.forName(TzHaarMejKah.class.getCanonicalName()));
			handledDialogues.put("LanderD", (Class<Dialogue>) Class
					.forName(LanderDialouge.class.getCanonicalName()));
			handledDialogues.put("MasterOfFear", (Class<Dialogue>) Class
					.forName(MasterOfFear.class.getCanonicalName()));
			handledDialogues.put("TENBColor", (Class<Dialogue>) Class
					.forName(TENBColor.class.getCanonicalName()));
			handledDialogues.put("TENBColorInv", (Class<Dialogue>) Class
					.forName(TENBColorInv.class.getCanonicalName()));
			handledDialogues.put("TokHaarHok", (Class<Dialogue>) Class
					.forName(TokHaarHok.class.getCanonicalName()));
			handledDialogues.put("NomadThrone", (Class<Dialogue>) Class
					.forName(NomadThrone.class.getCanonicalName()));
			handledDialogues.put("SimplePlayerMessage", (Class<Dialogue>) Class
					.forName(SimplePlayerMessage.class.getCanonicalName()));
			handledDialogues.put("BonfireD", (Class<Dialogue>) Class
					.forName(BonfireD.class.getCanonicalName()));
			handledDialogues.put("MasterChef", (Class<Dialogue>) Class
					.forName(MasterChef.class.getCanonicalName()));
			handledDialogues.put("FightKilnDialogue", (Class<Dialogue>) Class
					.forName(FightKilnDialogue.class.getCanonicalName()));
			handledDialogues.put("RewardChest", (Class<Dialogue>) Class
					.forName(RewardChest.class.getCanonicalName()));
			handledDialogues.put("WizardFinix", (Class<Dialogue>) Class
					.forName(WizardFinix.class.getCanonicalName()));
			handledDialogues.put("RunespanPortalD", (Class<Dialogue>) Class
					.forName(RunespanPortalD.class.getCanonicalName()));
			handledDialogues.put("SorceressGardenNPCs", (Class<Dialogue>) Class
					.forName(SorceressGardenNPCs.class.getCanonicalName()));
			handledDialogues.put("Marv", (Class<Dialogue>) Class
					.forName(Marv.class.getCanonicalName()));
			handledDialogues.put("FlamingSkull", (Class<Dialogue>) Class
					.forName(FlamingSkull.class.getCanonicalName()));
			handledDialogues.put("Hairdresser", (Class<Dialogue>) Class
					.forName(Hairdresser.class.getCanonicalName()));
			handledDialogues.put("Thessalia", (Class<Dialogue>) Class
					.forName(Thessalia.class.getCanonicalName()));
			handledDialogues.put("GrilleGoatsD", (Class<Dialogue>) Class
					.forName(GrilleGoatsDialogue.class.getCanonicalName()));
			handledDialogues.put("SlayerTeleports", (Class<Dialogue>) Class
					.forName(SlayerTeleports.class.getCanonicalName()));
			handledDialogues.put("TrainingTeleports", (Class<Dialogue>) Class
					.forName(TrainingTeleports.class.getCanonicalName()));
			handledDialogues.put("BasicTeleports", (Class<Dialogue>) Class
					.forName(BasicTeleports.class.getCanonicalName()));
			handledDialogues.put("BossTeleports", (Class<Dialogue>) Class
					.forName(BossTeleports.class.getCanonicalName()));
			handledDialogues.put("SkillingTeleports", (Class<Dialogue>) Class
					.forName(SkillingTeleports.class.getCanonicalName()));
			handledDialogues.put("MinigameTeleports", (Class<Dialogue>) Class
					.forName(MinigameTeleports.class.getCanonicalName()));
			handledDialogues.put("SummoningShop", (Class<Dialogue>) Class
					.forName(SummoningShop.class.getCanonicalName()));
			handledDialogues.put("Kuradal", (Class<Dialogue>) Class
					.forName(Kuradal.class.getCanonicalName()));
			handledDialogues.put("Xuan", (Class<Dialogue>) Class
					.forName(Xuans.class.getCanonicalName()));
			handledDialogues.put("Cleanbank", (Class<Dialogue>) Class
					.forName(Cleanbank.class.getCanonicalName()));
			handledDialogues.put("LegendsMinigameD", (Class<Dialogue>) Class
					.forName(LegendsMinigameD.class.getCanonicalName()));
			handledDialogues.put("LendingList", (Class<Dialogue>) Class
					.forName(LendingList.class.getCanonicalName()));
			handledDialogues.put("CollectMoney", (Class<Dialogue>) Class
					.forName(CollectMoney.class.getCanonicalName()));			
			handledDialogues.put("LendaRank", (Class<Dialogue>) Class
					.forName(LendaRank.class.getCanonicalName()));
			handledDialogues.put("LendaRankb", (Class<Dialogue>) Class
					.forName(LendaRankb.class.getCanonicalName()));
			handledDialogues.put("BinEradicator", (Class<Dialogue>) Class
					.forName(BinEradicator.class.getCanonicalName()));	
			handledDialogues.put("IronBinEradicator", (Class<Dialogue>) Class
					.forName(IronBinEradicator.class.getCanonicalName()));
			handledDialogues.put("BinSuper", (Class<Dialogue>) Class
					.forName(BinSuper.class.getCanonicalName()));	
			handledDialogues.put("TheEradicator", (Class<Dialogue>) Class
					.forName(TheEradicator.class.getCanonicalName()));	
			handledDialogues.put("IronmanNPC", (Class<Dialogue>) Class
					.forName(IronmanNPC.class.getCanonicalName()));	
			handledDialogues.put("SuperMan", (Class<Dialogue>) Class
					.forName(SuperMan.class.getCanonicalName()));	
			handledDialogues.put("LotteryDialogue", (Class<Dialogue>) Class
					.forName(LotteryDialogue.class.getCanonicalName()));			
			handledDialogues.put("Hans", (Class<Dialogue>) Class
					.forName(Hans.class.getCanonicalName()));			
			handledDialogues.put("WaterFillingD", (Class<Dialogue>) Class
					.forName(WaterFillingD.class.getCanonicalName()));	
			handledDialogues.put("FarmingManager", (Class<Dialogue>) Class
					.forName(FarmingManager.class.getCanonicalName()));		
			handledDialogues.put("EradicatorRank", (Class<Dialogue>) Class
					.forName(EradicatorRank.class.getCanonicalName()));
			handledDialogues.put("ExtremeRank", (Class<Dialogue>) Class
					.forName(ExtremeRank.class.getCanonicalName()));
			handledDialogues.put("UpgradeOne", (Class<Dialogue>) Class
					.forName(UpgradeOne.class.getCanonicalName()));
			handledDialogues.put("UpgradeTwo", (Class<Dialogue>) Class
					.forName(UpgradeTwo.class.getCanonicalName()));
			handledDialogues.put("UpgradeThree", (Class<Dialogue>) Class
					.forName(UpgradeThree.class.getCanonicalName()));
			handledDialogues.put("UpgradeFour", (Class<Dialogue>) Class
					.forName(UpgradeFour.class.getCanonicalName()));
			handledDialogues.put("UpgradeFive", (Class<Dialogue>) Class
					.forName(UpgradeFive.class.getCanonicalName()));
			handledDialogues.put("UpgradeSix", (Class<Dialogue>) Class
					.forName(UpgradeSix.class.getCanonicalName()));
			handledDialogues.put("UpgradeLS1", (Class<Dialogue>) Class
					.forName(UpgradeLS1.class.getCanonicalName()));
			handledDialogues.put("UpgradeLS2", (Class<Dialogue>) Class
					.forName(UpgradeLS2.class.getCanonicalName()));
			handledDialogues.put("UpgradeLS3", (Class<Dialogue>) Class
					.forName(UpgradeLS3.class.getCanonicalName()));
			handledDialogues.put("SaviorRank", (Class<Dialogue>) Class
					.forName(SaviorRank.class.getCanonicalName()));
			handledDialogues.put("EmptyWarning", (Class<Dialogue>) Class
					.forName(EmptyWarning.class.getCanonicalName()));
			handledDialogues.put("DonatorRank", (Class<Dialogue>) Class
					.forName(DonatorRank.class.getCanonicalName()));
			handledDialogues.put("Potato", (Class<Dialogue>) Class
					.forName(Potato.class.getCanonicalName()));
			handledDialogues.put("WildernessDitch", (Class<Dialogue>) Class
					.forName(WildernessDitch.class.getCanonicalName()));	
			handledDialogues.put("MasterCapes", (Class<Dialogue>) Class
					.forName(MasterCapes.class.getCanonicalName()));		
			handledDialogues.put("Cosmetic", (Class<Dialogue>) Class
					.forName(Cosmetic.class.getCanonicalName()));
			handledDialogues.put("PartnerPortal", (Class<Dialogue>) Class
					.forName(PartnerPortal.class.getCanonicalName()));	
			handledDialogues.put("TriviaShop", (Class<Dialogue>) Class
					.forName(TriviaShop.class.getCanonicalName()));		
			handledDialogues.put("TrioPortal", (Class<Dialogue>) Class
					.forName(TrioPortal.class.getCanonicalName()));
			handledDialogues.put("HardModeTrioPortal", (Class<Dialogue>) Class
					.forName(HardModeTrioPortal.class.getCanonicalName()));
			handledDialogues.put("Trio", (Class<Dialogue>) Class
					.forName(Trio.class.getCanonicalName()));
			handledDialogues.put("Bandos", (Class<Dialogue>) Class
					.forName(Bandos.class.getCanonicalName()));
			handledDialogues.put("Armadyl", (Class<Dialogue>) Class
					.forName(Armadyl.class.getCanonicalName()));
			handledDialogues.put("Saradomin", (Class<Dialogue>) Class
					.forName(Saradomin.class.getCanonicalName()));
			handledDialogues.put("Blink", (Class<Dialogue>) Class
					.forName(Blink.class.getCanonicalName()));
			handledDialogues.put("Brutals", (Class<Dialogue>) Class
					.forName(Brutals.class.getCanonicalName()));
			handledDialogues.put("Supers", (Class<Dialogue>) Class
					.forName(Supers.class.getCanonicalName()));
			handledDialogues.put("Corp", (Class<Dialogue>) Class
					.forName(Corp.class.getCanonicalName()));
			handledDialogues.put("EradicatorBoss", (Class<Dialogue>) Class
					.forName(EradicatorBoss.class.getCanonicalName()));
			handledDialogues.put("EradJadKing", (Class<Dialogue>) Class
					.forName(EradJadKing.class.getCanonicalName()));
			handledDialogues.put("EradKing", (Class<Dialogue>) Class
					.forName(EradKing.class.getCanonicalName()));
			handledDialogues.put("Fear", (Class<Dialogue>) Class
					.forName(Fear.class.getCanonicalName()));
			handledDialogues.put("Geno", (Class<Dialogue>) Class
					.forName(Geno.class.getCanonicalName()));
			handledDialogues.put("Gradum", (Class<Dialogue>) Class
					.forName(Gradum.class.getCanonicalName()));
			handledDialogues.put("PickIronman", (Class<Dialogue>) Class
					.forName(PickIronman.class.getCanonicalName()));
			handledDialogues.put("Necrolord", (Class<Dialogue>) Class
					.forName(Necrolord.class.getCanonicalName()));
			handledDialogues.put("Rajj", (Class<Dialogue>) Class
					.forName(Rajj.class.getCanonicalName()));
			handledDialogues.put("Regular", (Class<Dialogue>) Class
					.forName(Regular.class.getCanonicalName()));
			handledDialogues.put("Extreme", (Class<Dialogue>) Class
					.forName(Extreme.class.getCanonicalName()));
			handledDialogues.put("STQ", (Class<Dialogue>) Class
					.forName(STQ.class.getCanonicalName()));
			handledDialogues.put("Sunfreet", (Class<Dialogue>) Class
					.forName(Sunfreet.class.getCanonicalName()));
			handledDialogues.put("Wyrm", (Class<Dialogue>) Class
					.forName(Wyrm.class.getCanonicalName()));
			handledDialogues.put("Zamorak", (Class<Dialogue>) Class
					.forName(Zamorak.class.getCanonicalName()));
			handledDialogues.put("BossSlayer", (Class<Dialogue>) Class
					.forName(BossSlayer.class.getCanonicalName()));
			handledDialogues.put("DamageDealtGame", (Class<Dialogue>) Class
					.forName(DamageDealtGame.class.getCanonicalName()));
			handledDialogues.put("ResetP1", (Class<Dialogue>) Class
					.forName(ResetBankPreset.class.getCanonicalName()));	
			handledDialogues.put("ResetP2", (Class<Dialogue>) Class
					.forName(ResetBankPreset2.class.getCanonicalName()));	
			handledDialogues.put("ResetP3", (Class<Dialogue>) Class
					.forName(ResetBankPreset3.class.getCanonicalName()));	
			handledDialogues.put("ResetP4", (Class<Dialogue>) Class
					.forName(ResetBankPreset4.class.getCanonicalName()));	
			handledDialogues.put("ClanMotto", (Class<Dialogue>) Class
					.forName(ClanMotto.class.getCanonicalName()));	
			handledDialogues.put("ClanCreateD", (Class<Dialogue>) Class
					.forName(ClanCreateD.class.getCanonicalName()));			
			handledDialogues.put("ClanInvite", (Class<Dialogue>) Class
					.forName(ClanInvite.class.getCanonicalName()));	
			handledDialogues.put("LeaveClan", (Class<Dialogue>) Class
					.forName(LeaveClan.class.getCanonicalName()));		
			handledDialogues.put("AssignKeyFunction", (Class<Dialogue>) Class
					.forName(AssignKeyFunction.class.getCanonicalName()));	
			handledDialogues.put("AnniversaryEvent", (Class<Dialogue>) Class
					.forName(AnniversaryEvent.class.getCanonicalName()));		
			handledDialogues.put("SkillerCapePurchase", (Class<Dialogue>) Class
					.forName(SkillerCapePurchase.class.getCanonicalName()));	
			handledDialogues.put("MaxCapePurchase", (Class<Dialogue>) Class
					.forName(MaxCapePurchase.class.getCanonicalName()));	
			handledDialogues.put("SkillerMasterPurchase", (Class<Dialogue>) Class
					.forName(SkillerMasterPurchase.class.getCanonicalName()));	
			handledDialogues.put("CompletionistPurchase", (Class<Dialogue>) Class
					.forName(CompletionistPurchase.class.getCanonicalName()));	
			handledDialogues.put("10BPurchase", (Class<Dialogue>) Class
					.forName(TenBPurchase.class.getCanonicalName()));	
			handledDialogues.put("AmuletofCompletionPurchase", (Class<Dialogue>) Class
					.forName(AmuletofCompletionPurchase.class.getCanonicalName()));	
			handledDialogues.put("200mMasterPurchase", (Class<Dialogue>) Class
					.forName(TwoHundredMMasterPurchase.class.getCanonicalName()));		
			handledDialogues.put("CyndrithReveal", (Class<Dialogue>) Class
					.forName(CyndrithReveal.class.getCanonicalName()));			
			handledDialogues.put("CyndrithsChest", (Class<Dialogue>) Class
					.forName(CyndrithsChest.class.getCanonicalName()));			
			handledDialogues.put("GypsyAris", (Class<Dialogue>) Class
					.forName(GypsyAris.class.getCanonicalName()));		
			handledDialogues.put("GiveUp", (Class<Dialogue>) Class
					.forName(GiveUp.class.getCanonicalName()));		
			handledDialogues.put("ExtraBanker", (Class<Dialogue>) Class
					.forName(ExtraBanker.class.getCanonicalName()));	
			handledDialogues.put("CheckBank", (Class<Dialogue>) Class
					.forName(CheckBank.class.getCanonicalName()));		
			handledDialogues.put("WithdrawCurrencyPouch", (Class<Dialogue>) Class
					.forName(WithdrawCurrencyPouch.class.getCanonicalName()));
			handledDialogues.put("WithdrawCurrencyTrade", (Class<Dialogue>) Class
					.forName(WithdrawCurrencyTrade.class.getCanonicalName()));		
			handledDialogues.put("CyndrithChapterII", (Class<Dialogue>) Class
					.forName(CyndrithChapterII.class.getCanonicalName()));		
			handledDialogues.put("CyndrithChapterIII", (Class<Dialogue>) Class
				.forName(CyndrithChapterIII.class.getCanonicalName()));		
			handledDialogues.put("CyndrithEnchantments", (Class<Dialogue>) Class
				.forName(CyndrithEnchantments.class.getCanonicalName()));		
			handledDialogues.put("HeadEnchantments", (Class<Dialogue>) Class
				.forName(HeadEnchantments.class.getCanonicalName()));		
			handledDialogues.put("TrioKey", (Class<Dialogue>) Class
					.forName(TrioKey.class.getCanonicalName()));		
			handledDialogues.put("HardModeTrio", (Class<Dialogue>) Class
					.forName(HardModeTrio.class.getCanonicalName()));		
			handledDialogues.put("LogAction", (Class<Dialogue>) Class
					.forName(LogAction.class.getCanonicalName()));			
			handledDialogues.put("PetKeeper", (Class<Dialogue>) Class
					.forName(PetKeeper.class.getCanonicalName()));		
			handledDialogues.put("PartnerPortalHM", (Class<Dialogue>) Class
					.forName(PartnerPortalHM.class.getCanonicalName()));			
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static final void reload() {
		handledDialogues.clear();
		init();
	}

	public static final Dialogue getDialogue(Object key) {
		if (key instanceof Dialogue)
			return (Dialogue) key;
		Class<Dialogue> classD = handledDialogues.get(key);
		if (classD == null)
			return null;
		try {
			return classD.newInstance();
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}

	private DialogueHandler() {

	}
}
