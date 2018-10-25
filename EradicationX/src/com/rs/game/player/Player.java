package com.rs.game.player;
import com.rs.Settings;
import com.rs.cache.VarsManager;
import com.rs.content.exchange.ItemOffer;
import com.rs.content.exchange.ItemOffer.OfferType;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.WarControler;
import com.rs.game.minigames.duel.DuelArena;
import com.rs.game.minigames.duel.DuelRules;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.QuestManager.Quests;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.content.*;
import com.rs.game.player.content.GildedAltar.bonestoOffer;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.custom.DoubleXpManager;
import com.rs.game.player.content.custom.DoubleVoteManager;
import com.rs.game.player.content.pet.PetManager;
import com.rs.game.player.controlers.CorpBeastControler;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.game.player.controlers.DTControler;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.GodWars;
import com.rs.game.player.controlers.NomadsRequiem;
import com.rs.game.player.controlers.QueenBlackDragonController;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.ZGDControler;
import com.rs.game.player.controlers.castlewars.CastleWarsPlaying;
import com.rs.game.player.controlers.castlewars.CastleWarsWaiting;
import com.rs.game.player.controlers.fightpits.FightPitsArena;
import com.rs.game.player.controlers.pestcontrol.PestControlGame;
import com.rs.game.player.controlers.pestcontrol.PestControlLobby;
import com.rs.game.player.quest.EliteChapterFive;
import com.rs.game.player.quest.EliteChapterFour;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.EliteChapterThree;
import com.rs.game.player.quest.EliteChapterTwo;
import com.rs.game.player.quest.QNames;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.Session;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.net.encoders.WorldPacketsEncoder;
import com.rs.utils.DisplayNames;
import com.rs.utils.DisplayNamesManager;
import com.rs.utils.Highscores;
import com.rs.utils.IsaacKeyPair;
import com.rs.utils.Logger;
import com.rs.utils.MachineInformation;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.net.*;


public class Player extends Entity {

	
	
	private boolean untillLogout;
	
	public Notes getNotes() {
		return notes;
	}
	
	private MoneyPouch pouch;
	
	private CurrencyPouch currencypouch;
	
	public MoneyPouch getPouch() { 
		return pouch;
	}
	public void refreshMoneyPouch() {
		getPackets().sendRunScript(5560, pouch);
	}
	public int getMoneyPouch() {
		return moneypouch;
	}

	public void purchase(int amount) {
		if (getLoyaltyPoints() >= amount) {
			Loyaltypoints -= amount;
		} else {
			getPackets().sendGameMessage("You do not have enough points!");
		}
	}
	
	private SquealOfFortune sof;
	
	public SquealOfFortune getSqueal() {
		return sof;
	}
	
	public boolean isModerator() {
		return getRights() == 1;
	}
	
	
	public static final int TELE_MOVE_TYPE = 127, WALK_MOVE_TYPE = 1, RUN_MOVE_TYPE = 2;
	private static final long serialVersionUID = 2011932556974180375L;
	private static final int lastlogged = 0;
	private transient String username;
	private transient Session session;
	private transient boolean clientLoadedMapRegion;
	private transient int displayMode;
	private transient int screenWidth;
	public boolean trustedflower = true;
	private transient int screenHeight;
	private transient InterfaceManager interfaceManager;
	private transient DialogueManager dialogueManager;
	private transient ConstructFurniture con;
	private transient Mission mission;
	public int MiningPoints = 0;
	private transient LoyaltyManager loyaltyManager;
	private transient DwarfCannon dwarfCannon;
	private transient HintIconsManager hintIconsManager;
	private transient ActionManager actionManager;
	private transient CutscenesManager cutscenesManager;
	private transient PriceCheckManager priceCheckManager;
	private transient CoordsEvent coordsEvent;
	private transient FriendChatsManager currentFriendChat;
	public boolean trollReward;
	private int trollsToKill;
	private int trollsKilled;
	private WorldTile getoutside;
	private transient Trade trade;
	private transient DuelRules lastDuelRules;
	private transient IsaacKeyPair isaacKeyPair;
	private transient Pet pet;
	// Just for removing Maxcape on login
	public int maxCape = 0;

	// used for packets logic
	private transient ConcurrentLinkedQueue<LogicPacket> logicPackets;

	// used for update
	private transient LocalPlayerUpdate localPlayerUpdate;
	private transient LocalNPCUpdate localNPCUpdate;

	private int temporaryMovementType;
	private boolean updateMovementType;

	// player stages
	private transient boolean started;
	private transient boolean running;

	// Kuradal
	private boolean talkedWithKuradal;
	private boolean talkedWithReaper;

	private transient int savep;
	private transient long packetsDecoderPing;
	private transient Runnable vote;
	public transient boolean noContinue;
	private transient boolean resting;
	private transient boolean canPvp;
	private transient boolean cantTrade;
	private transient long lockDelay; // used for doors and stuff like that
	private transient long foodDelay;
	private transient long potDelay;
	private transient long boneDelay;
	transient Runnable closeInterfacesEvent;
	private transient long lastPublicMessage;
	private transient String lastPublicMessageString;
	private transient long polDelay;
	private transient List<Integer> switchItemCache;
	private transient boolean disableEquip;
	@SuppressWarnings("unused")
	private transient MachineInformation machineInformation;
	public transient int antiNull;
	private transient boolean spawnsMode;
	private transient int pinAttempts;
	private transient boolean castedVeng;
	private transient boolean invulnerable;
	private transient double hpBoostMultiplier;
	private transient boolean largeSceneView;

	// interface

	// saving stuff
	private String password;
	private int rights;
	public int moneypouch;	
	private String displayName;
	private Player leaderName;
	private String NewIP;
	private String lastIP;
	private long creationDate;
	private int instanceBooth;
	private Appearence appearence;
	private Inventory inventory;
	private Equipment equipment;
	private Skills skills;
	private CombatDefinitions combatDefinitions;
	private Prayer prayer;
	private Bank bank;
	private Toolbelt toolbelt;
	private Bank secondbank;
	private BankPreset preset;
	private BankPreset preset2;
	private BankPreset preset3;
	private BankPreset preset4;
	private KeybindFunctions keybindfunctions;
	public boolean forumnews;
	public int quickselect1;
	public int quickselect2;
	private ControlerManager controlerManager;
	private MusicsManager musicsManager;
	private EmotesManager emotesManager;
	private FriendsIgnores friendsIgnores;
	private DominionTower dominionTower;
	private Familiar familiar;
	private AuraManager auraManager;
	private LendingManager lendManager;
	private QuestManager questManager;
	private EXQuestManager exquestManager;
	private PetManager petManager;
	private byte runEnergy;
	private boolean allowChatEffects;
	private boolean viewStats;
	private boolean mouseButtons;
	private boolean diceannounce = false;
	private int privateChatSetup;
	private int friendChatSetup;
	private int skullDelay;
	private int skullId;
	private boolean forceNextMapLoadRefresh;
	private long poisonImmune;
	private long fireImmune;
	private boolean killedQueenBlackDragon;
	private int runeSpanPoints;

	private int lastBonfire;
	private int[] pouches;
	private int[] rolls;
	private long displayTime;
	private long muted;
	private long hevoted;
	private long jailed;
	private long banned;
	private boolean permBanned;
	private boolean filterGame;
	private boolean xpLocked;
	private boolean yellOff;
	// game bar status
	private int publicStatus;
	private int clanStatus;
	private int tradeStatus;
	private int assistStatus;
	public transient int shopAmount;
	public transient ArrayList<Player> joined;
	public transient String playeroption1;
	public transient int shopSlotId;

	private boolean donator;
	private boolean outside;
	private boolean extremeDonator;
	private long donatorTill;
	private long extremeDonatorTill;

	// Recovery ques. & ans.
	private String recovQuestion;
	private String recovAnswer;

	private String lastMsg;
	
	public String incupdate1;
	public String incupdate2;
	public String incupdate3;
	public String incupdate4;

	private boolean hasSecondBank;
	
	// Slayer
	private SlayerTask task;
	private BossSlayerTask bosstask;
	
	private long loyaltytimer;

	// Used for storing recent ips and password
	private ArrayList<String> passwordList = new ArrayList<String>();
	private ArrayList<String> ipList = new ArrayList<String>();

	// honor
	private int killCount, deathCount;
	private int votes;
	private ChargesManager charges;
	// barrows
	private boolean[] killedBarrowBrothers;
	private int hiddenBrother;
	private int barrowsKillCount;
	private int pestPoints;

	// skill capes customizing
	private int[] maxedCapeCustomized;
	private int[] completionistCapeCustomized;
	private int[] fivebillCustomized;

	// completionistcape reqs
	private boolean completedFightCaves;
	private boolean completedFightKiln;
	private boolean wonFightPits;

	private boolean learnedRocktailSoup;
	private boolean learnedAutoLootSeals;
	
	// crucible
	private boolean talkedWithMarv;
	private int crucibleHighScore;
	
	private int eradicatorDelay;
	private int overloadDelay;
	private int prayerRenewalDelay;

	private String currentFriendChatOwner;
	private int summoningLeftClickOption;
	private List<String> ownedObjectsManagerKeys;

	// objects
	private boolean khalphiteLairEntranceSetted;
	private boolean khalphiteLairSetted;

	// supportteam
	private boolean isSupporter;
	

	private String yellColor;
	private String yellTitle;
	private String yellShade;
	
	private boolean isOwner;
	

	private int copyrightkills;
	private int corporealkills;
	private int wyrmkills;
	private int avatarkills;
	private int necrolordkills;
	private int jadkills;
	private int obsidiankingkills;
	private int hairymonkeykills;
	private int viewpage;
	private int ranklend;
	public float amountdonated;
	private boolean bankequip;
	private int triviapoints = 0;
	public int threadnumber;
	private int spellpower;
	private int spelltraits;
	private boolean enchantLegs;
	private boolean enchantBody;
	private boolean enchantHelm;
	private int fatalkills;
	private int hmtriokills;
	private int mutemark;
	public int tasktab;
	private int killStreak;
	private int dropbeam;
	private int selectedbeam;
	private int collectLoanMoney;
	private int lendhours;
	private int ticketamount;
	public transient String instanceDialogue;
	public transient int voteDisplayAmount;
	// Amulet of Completion Requirements:
		
		public boolean ammyannounce;
		private int bossslayercount;
		private int regslayercount;
		private int amountthieved;
		private int dummydamage;
		private long dummygamecooldown;
		
		public int getDummyDamage() {
			return dummydamage;
		}
		
		public void setDummyDamage(int d) {
			dummydamage = d;
		}		
		
		public long getDummyCooldown() {
			return dummygamecooldown;
		}
		
		public void setDummyCooldown(long d) {
			dummygamecooldown = d;
		}
		
		public int getAmountThieved(boolean achievecount) {
			if (achievecount && amountthieved >= 50000000)
				return 50000000;
			return amountthieved;
		}
		
		public void setAmountThieved(int amount) {
			if (amount >= 50000000)
				amountthieved = 5000000;
			amountthieved = amount;
		}
		
		public int getSlayerCount(boolean achievecount) {
			if (achievecount && regslayercount >= 50)
				return 50;
			return regslayercount;
		}
		public void setSlayerCount(int amount) {
			regslayercount = amount;
		}
		
		public int getBossSlayerCount(boolean achievecount) {
			if (achievecount && bossslayercount >= 50)
				return 50;
			return bossslayercount;
		}
		public void setBossSlayerCount(int amount) {
			bossslayercount = amount;
		}
	
	
	// End of Amulet of Completion Requirements 
	
	
	// Iron Man
		private boolean isIronMan;
		public boolean isIronMan() {
			return isIronMan;
		}
		public void setIronMan(boolean isiron) {
			isIronMan = isiron;
		}
	// End of Iron Man	
		
		public boolean isInsideHairymonkey;
			
	
	public int getTicketamount() {
		return ticketamount;
	}
	public void setTicketamount(int ticketamount) {
		this.ticketamount = ticketamount;
	}
	
	public int getLendhours() {
		return lendhours;
	}
	public void setLendhours(int lendhours) {
		this.lendhours = lendhours;
	}
	
	public long getLastCorrectTrivia() {
		return lastAnswered;
	}
	public void setLastAnswer(long time) {
		this.lastAnswered = time;
	}
	public int getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(int amount) {
		this.correctAnswers = amount;
	}
	public void setDisableTrivia(boolean value) {
		this.disabledTrivia = value;
	}
	public boolean hasDisabledTrivia() {
		return disabledTrivia;
	}

	private boolean disabledTrivia;
	private long lastAnswered;
	public int correctAnswers;	

	public int lendMessage;
	public String raffleMessage;
	public String auctionTimeup;
	public int auctionMessage;
	public boolean senttoBank;
	private boolean permatrio;
	private boolean permabrutals;
	private boolean permabank;
	private boolean permasunfret;
	private boolean permabandos;
	private boolean permasaradomin;
	private boolean permazamorak;
	private boolean permaarmadyl;
	private boolean permaregular;
	private boolean permaextreme;
	private boolean permablink;
	private boolean permaeradicator;
	private boolean permacorp;
	private boolean permakbd;
	private boolean permagradum;
	private boolean permawyrm;
	private boolean permanecrolord;
	private boolean permaavatar;
	private boolean permafear;
	private boolean permastq;
	private boolean permaeradjad;
	private boolean permaobbyking;
	private boolean permageno;
	private boolean permarajj;
	private int timer;
	private int destroytimer;
	private boolean isdestroytimer;
	private boolean instanceend;
	private int donexp;
	private int donexp2;
	private int interfaceamount;	
	private int spawnrate;	
	private int wildybosskills;	
	private int firsttimevoting;
	private int somethingkills;
	private int nexkills;
	private int sunfreetkills;
	private int armadylkills;
	private int bandoskills;
	private int zamorakkills;
	private int saradominkills;
	private int eradicatorbosskills;
	private int genokills;
	private int rajjkills;
	private int fearkills;
	private int blinkkills;
	private int extremebosskills;
	private int regularbosskills;
	private int gradumkills;
	private int slayerPoints;
	private int Loyaltypoints;
	private int EradicatorBonePoints;	
	private int SuperBonePoints;
	// creates Player and saved classes
	public Player(String password) {
		super(/* Settings.HOSTED ? */Settings.START_PLAYER_LOCATION);
		setHitpoints(Settings.START_PLAYER_HITPOINTS);
		this.password = password;
		appearence = new Appearence();
		sof = new SquealOfFortune();
		currencypouch = new CurrencyPouch();
		pouch = new MoneyPouch();
		inventory = new Inventory();
		equipment = new Equipment();
		skills = new Skills();
		combatDefinitions = new CombatDefinitions();
		farmingManager = new FarmingManager();
		prayer = new Prayer();
		bank = new Bank();
		toolbelt = new Toolbelt(this);
		secondbank = new Bank();
		preset = new BankPreset(this);
		preset2 = new BankPreset(this);
		preset3 = new BankPreset(this);
		preset4 = new BankPreset(this);
		keybindfunctions = new KeybindFunctions(this);
		controlerManager = new ControlerManager();
		musicsManager = new MusicsManager();
		emotesManager = new EmotesManager();
		friendsIgnores = new FriendsIgnores();
		dominionTower = new DominionTower();
		charges = new ChargesManager();
		auraManager = new AuraManager();
		lendManager = new LendingManager();
		questManager = new QuestManager();
		exquestManager = new EXQuestManager();
		petManager = new PetManager();
		runEnergy = 100;
		allowChatEffects = true;
		mouseButtons = true;
		completed = true;
		pouches = new int[4];
		rolls = new int[300];
		for (int i = 0; i < rolls.length; i++)
			rolls[i] = -1;
		resetBarrows();
		SkillCapeCustomizer.resetSkillCapes(this);
		ownedObjectsManagerKeys = new LinkedList<String>();
		passwordList = new ArrayList<String>();
		ipList = new ArrayList<String>();
		setCreationDate(Utils.currentTimeMillis());
	}

	public void init(Session session, String username, int displayMode,
			int screenWidth, int screenHeight,
			MachineInformation machineInformation, IsaacKeyPair isaacKeyPair) {
		// temporary deleted after reset all chars
		if (rolls == null) {
			rolls = new int[300];
			for (int i = 0; i < rolls.length; i++)
				rolls[i] = -1;	
		}
		if (joined == null)
			joined = new ArrayList<Player>();
		if (secondbank == null)
			secondbank = new Bank();
		if (toolbelt == null)
			toolbelt = new Toolbelt(this);
		if (preset == null)
			preset = new BankPreset(this);	
		if (preset2 == null)
			preset2 = new BankPreset(this);
		if (preset3 == null)
			preset3 = new BankPreset(this);
		if (preset4 == null)
			preset4 = new BankPreset(this);
		if (keybindfunctions == null)
			keybindfunctions = new KeybindFunctions(this);
		if (dominionTower == null)
			dominionTower = new DominionTower();
		if (auraManager == null)
			auraManager = new AuraManager();
		if (lendManager == null)
			lendManager = new LendingManager();
		if (farmingManager == null)
						farmingManager = new FarmingManager();
		if (questManager == null)
			questManager = new QuestManager();
		if (exquestManager == null)
			exquestManager = new EXQuestManager();
		if (dwarfCannon == null) 
			dwarfCannon = new DwarfCannon(this);
		if (petManager == null) {
			petManager = new PetManager();
		}
		if (sof == null) 
			sof = new SquealOfFortune();
		if (pouch == null)
			pouch = new MoneyPouch();
		if (currencypouch == null) {
			currencypouch = new CurrencyPouch();
		} else if (currencypouch.getCurrencies().length == 4) {
			currencypouch = new CurrencyPouch();
		}
		
		if(dropRatio == null)
			dropRatio = new HashMap<>();
		
		this.session = session;
		this.username = username;
		this.displayMode = displayMode;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.machineInformation = machineInformation;
		this.isaacKeyPair = isaacKeyPair;
		
		notes = new Notes();
		varsManager = new VarsManager(this);
		interfaceManager = new InterfaceManager(this);
		dialogueManager = new DialogueManager(this);
		loyaltyManager = new LoyaltyManager(this);
		hintIconsManager = new HintIconsManager(this);
		priceCheckManager = new PriceCheckManager(this);
		localPlayerUpdate = new LocalPlayerUpdate(this);
		localNPCUpdate = new LocalNPCUpdate(this);
		actionManager = new ActionManager(this);
		cutscenesManager = new CutscenesManager(this);
		trade = new Trade(this);
		// loads player on saved instances
		appearence.setPlayer(this);
		inventory.setPlayer(this);
		pouch.setPlayer(this);
		currencypouch.setPlayer(this);
		equipment.setPlayer(this);
		skills.setPlayer(this);
		notes.setPlayer(this);
		combatDefinitions.setPlayer(this);
		sof.setPlayer(this); 
		prayer.setPlayer(this);
		preset.setPlayer(this);
		preset2.setPlayer(this);
		preset3.setPlayer(this);
		preset4.setPlayer(this);
		secondbank.setPlayer(this);
		keybindfunctions.setPlayer(this);
		bank.setPlayer(this);
		toolbelt.setPlayer(this);
		controlerManager.setPlayer(this);
		musicsManager.setPlayer(this);
		emotesManager.setPlayer(this);
		friendsIgnores.setPlayer(this);
		dominionTower.setPlayer(this);
		auraManager.setPlayer(this);
		charges.setPlayer(this);
		farmingManager.setPlayer(this);
		questManager.setPlayer(this);
		exquestManager.setPlayer(this);
		petManager.setPlayer(this);
		setDirection(Utils.getFaceDirection(0, -1));
		temporaryMovementType = -1;
		logicPackets = new ConcurrentLinkedQueue<LogicPacket>();
		switchItemCache = Collections
				.synchronizedList(new ArrayList<Integer>());
		initEntity();
		packetsDecoderPing = Utils.currentTimeMillis();
		World.addPlayer(this);
		World.updateEntityRegion(this);
		
	
		if (Settings.DEBUG)
			/* Player Rights */
			if (username.equalsIgnoreCase("Era") || username.equalsIgnoreCase("jenny") || username.equalsIgnoreCase("vlad")) {
				rights = 7;
			}
		//if (username.equalsIgnoreCase("Copyright")) {
		//	rights = 2;
		//}		
		//if (username.equalsIgnoreCase("Developer")) {
		//	rights = 7;
		//}			
		
		if (passwordList == null)
			passwordList = new ArrayList<String>();
		if (ipList == null)
			ipList = new ArrayList<String>();
		updateIPnPass();
		completed = false;
		getEXQuestManager().updateQuestList();
		if (getGeOffers() == null)
			setGeOffers(new ItemOffer[6]);
	}

	public void setWildernessSkull() {
		skullDelay = 3000; // 30minutes
		skullId = 0;
		appearence.generateAppearenceData();
	}
	
	public void setWildernessSkull(int id) {
		skullDelay = 3000; // 30minutes
		skullId = id;
		appearence.generateAppearenceData();
	}

	public void setFightPitsSkull() {
		skullDelay = Integer.MAX_VALUE; // infinite
		skullId = 1;
		appearence.generateAppearenceData();
	}

	public void setSkullInfiniteDelay(int skullId) {
		skullDelay = Integer.MAX_VALUE; // infinite
		this.skullId = skullId;
		appearence.generateAppearenceData();
	}

	public void removeSkull() {
		skullDelay = -1;
		appearence.generateAppearenceData();
	}

	public boolean hasSkull() {
		return skullDelay > 0;
	}

	public int setSkullDelay(int delay) {
		return this.skullDelay = delay;
	}

	public void refreshSpawnedItems() {
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId)
					.getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave())
						&& this != item.getOwner()
						|| item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendRemoveGroundItem(item);
			}
		}
		for (int regionId : getMapRegionsIds()) {
			List<FloorItem> floorItems = World.getRegion(regionId)
					.getFloorItems();
			if (floorItems == null)
				continue;
			for (FloorItem item : floorItems) {
				if ((item.isInvisible() || item.isGrave())
						&& this != item.getOwner()
						|| item.getTile().getPlane() != getPlane())
					continue;
				getPackets().sendGroundItem(item);
			}
		}
	}

	public void refreshSpawnedObjects() {
		for (int regionId : getMapRegionsIds()) {
			List<WorldObject> spawnedObjects = World.getRegion(regionId)
					.getSpawnedObjects();
			if (spawnedObjects != null) {
				for (WorldObject object : spawnedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendSpawnedObject(object);
			}
			List<WorldObject> removedObjects = World.getRegion(regionId)
					.getRemovedObjects();
			if (removedObjects != null) {
				for (WorldObject object : removedObjects)
					if (object.getPlane() == getPlane())
						getPackets().sendDestroyObject(object);
			}
		}
	}

	public void start() {
		loadMapRegions();
		started = true;
		if (fixfiveb == 0) {
			fixfiveb = 1;
			setFiveBillCapeCustomized(this.getMaxedCapeCustomized());
		}
		run();
		if (this.getClanName() != null) {
			if (!ClansManager.connectToClan(this, this.getClanName(), false))
				this.setClanName(null);
		}
		if (isDead())
			sendDeath(null);
	}

	public void stopAll() {
		stopAll(true);
	}
	public void stopAll(boolean stopWalk) {
		stopAll(stopWalk, true);
	}

	public void stopAll(boolean stopWalk, boolean stopInterface) {
		stopAll(stopWalk, stopInterface, true);
	}

	// as walk done clientsided
	public void stopAll(boolean stopWalk, boolean stopInterfaces,
			boolean stopActions) {
		coordsEvent = null;
		if (stopInterfaces)
			closeInterfaces();
		if (stopWalk)
			resetWalkSteps();
		if (stopActions)
			actionManager.forceStop();
		combatDefinitions.resetSpells(false);
		bonestoOffer.stopOfferGod = true;
	}

	@Override
	public void reset(boolean attributes) {
		super.reset(attributes);
		refreshHitPoints();
		hintIconsManager.removeAll();
		skills.restoreSkills();
		combatDefinitions.resetSpecialAttack();
		prayer.reset();
		combatDefinitions.resetSpells(true);
		resting = false;
		bonestoOffer.stopOfferGod = true;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		castedVeng = false;
		setRunEnergy(100);
		appearence.generateAppearenceData();
	}

	public void resetNoLoss(boolean attributes) {
		super.reset(attributes);
		refreshHitPoints();
		hintIconsManager.removeAll();
		combatDefinitions.resetSpecialAttack();
		combatDefinitions.resetSpells(true);
		prayer.reset();
		resting = false;
		bonestoOffer.stopOfferGod = true;
		skullDelay = 0;
		foodDelay = 0;
		potDelay = 0;
		poisonImmune = 0;
		fireImmune = 0;
		castedVeng = false;
		setRunEnergy(100);
		appearence.generateAppearenceData();
	}	
	
	@Override
	public void reset() {
		reset(true);
	}

	public void closeInterfaces() {
		if (interfaceManager.containsScreenInter())
			interfaceManager.closeScreenInterface();
		if (interfaceManager.containsInventoryInter())
			interfaceManager.closeInventoryInterface();
		dialogueManager.finishDialogue();
		if (closeInterfacesEvent != null) {
			closeInterfacesEvent.run();
			closeInterfacesEvent = null;
		}
	}

	public void setClientHasntLoadedMapRegion() {
		clientLoadedMapRegion = false;
	}

	@Override
	public void loadMapRegions() {
		boolean wasAtDynamicRegion = isAtDynamicRegion();
		super.loadMapRegions();
		clientLoadedMapRegion = false;
		if (isAtDynamicRegion()) {
			getPackets().sendDynamicMapRegion(!started);
			if (!wasAtDynamicRegion)
				localNPCUpdate.reset();
		} else {
			getPackets().sendMapRegion(!started);
			if (wasAtDynamicRegion)
				localNPCUpdate.reset();
		}
		forceNextMapLoadRefresh = false;
	}

	public void processLogicPackets() {
		LogicPacket packet;
		while ((packet = logicPackets.poll()) != null)
			WorldPacketsDecoder.decodeLogicPacket(this, packet);
	}

	@Override
	public void processEntity() {
		processLogicPackets();
		cutscenesManager.process();
		if (coordsEvent != null && coordsEvent.processEvent(this))
			coordsEvent = null;
		super.processEntity();
		if (musicsManager.musicEnded())
			musicsManager.replayMusic();
		if (hasSkull()) {
			skullDelay--;
			if (!hasSkull())
				appearence.generateAppearenceData();
		}
		if (polDelay != 0 && polDelay <= Utils.currentTimeMillis()) {
			getPackets()
					.sendGameMessage(
							"The power of the light fades. Your resistance to melee attacks return to normal.");
			polDelay = 0;
		}
		if (overloadDelay > 0) {
			if (overloadDelay == 1 || isDead()) {
				Pots.resetOverLoadEffect(this);
				return;
			} else if ((overloadDelay - 1) % 25 == 0)
				Pots.applyOverLoadEffect(this);
			overloadDelay--;
		}
		if (eradicatorDelay > 0) {
			if (eradicatorDelay == 1 || isDead()) {
				Pots.resetEradicatorEffect(this);
				return;
			} else if ((eradicatorDelay - 1) % 25 == 0)
				Pots.applyEradicatorEffect(this);
			eradicatorDelay--;
		}			
		if (prayerRenewalDelay > 0) {
			if (prayerRenewalDelay == 1 || isDead()) {
				getPackets().sendGameMessage(
						"<col=0000FF>Your prayer renewal has ended.");
				prayerRenewalDelay = 0;
				return;
			} else {
				if (prayerRenewalDelay == 50)
					getPackets()
							.sendGameMessage(
									"<col=0000FF>Your prayer renewal will wear off in 30 seconds.");
				if (!prayer.hasFullPrayerpoints()) {
					getPrayer().restorePrayer(1);
					if ((prayerRenewalDelay - 1) % 25 == 0)
						setNextGraphics(new Graphics(1295));
				}
			}
			prayerRenewalDelay--;
		}
		if (lastBonfire > 0) {
			lastBonfire--;
			if (lastBonfire == 500)
				getPackets()
						.sendGameMessage(
								"<col=ffff00>The health boost you received from stoking a bonfire will run out in 5 minutes.");
			else if (lastBonfire == 0) {
				getPackets()
						.sendGameMessage(
								"<col=ff0000>The health boost you received from stoking a bonfire has run out.");
				equipment.refreshConfigs(false);
			}
		}
		lendManager.process();
		charges.process();
		auraManager.process();
		actionManager.process();
		farmingManager.process();
		prayer.processPrayer();
		controlerManager.process();

	}

	@Override
	public void processReceivedHits() {
		if (lockDelay > Utils.currentTimeMillis())
			return;
		super.processReceivedHits();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || temporaryMovementType != -1
				|| updateMovementType;
	}
	/*public static void RandomEventTeleportPlayer(final Player player, final int x, final int y, final int z) {
		if (player.isLocked()
				|| player.getControlerManager().getControler() != null) {
		player.sm("You haven't teleported to random event for being busy.");
		}
		else {	
		player.setNextAnimation(new Animation(2140));
		player.setNextForceTalk(new ForceTalk("FML! RANDOM SUCKS!"));
		player.lock();
		player.getControlerManager().startControler("RandomEvent");
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.unlock();
				player.sm("You are teleported inside a random event.");
				player.setNextWorldTile(new WorldTile(x, y, z));
			}
		}, 5);
	}
	}
	public void randomevent(final Player p) {
        EventManager.getSingleton().addEvent(new Event() {
            public void execute(EventContainer c) {
                int r3 = 0;
                r3 = Utils.random(2);
                if (r3 == 0) {
                	if (isLocked()
            				|| getControlerManager().getControler() != null) {
                	sm("You was going to teleport too random event but you were too busy.");
                	}
                	else  {
                		 RandomEventTeleportPlayer(p, 1889, 5126, 0);
                	
                	}
                
		} else if (r3 == 1) {
	     	if (isLocked()
    				|| getControlerManager().getControler() != null) {
        	sm("You was going to teleport too random event but you were too busy.");
        	}
        	else  {
        		 RandomEventTeleportPlayer(p, 1889, 5126, 0);
        	
        	}
                }
            }
        }, 1800000);
    };*/
	@Override
	public void resetMasks() {
		super.resetMasks();
		temporaryMovementType = -1;
		updateMovementType = false;
		if (!clientHasLoadedMapRegion()) {
			// load objects and items here
			setClientHasLoadedMapRegion();
			refreshSpawnedObjects();
			refreshSpawnedItems();
		}
	}

	public void toogleRun(boolean update) {
		super.setRun(!getRun());
		updateMovementType = true;
		if (update)
			sendRunButtonConfig();
	}

	public void setRunHidden(boolean run) {
		super.setRun(run);
		updateMovementType = true;
	}

	@Override
	public void setRun(boolean run) {
		if (run != getRun()) {
			super.setRun(run);
			updateMovementType = true;
			sendRunButtonConfig();
		}
	}

	public void sendRunButtonConfig() {
		getPackets().sendConfig(173, resting ? 3 : getRun() ? 1 : 0);
	}

	public void restoreRunEnergy() {
		if (getNextRunDirection() == -1 && runEnergy < 100) {
			runEnergy++;
			if (resting && runEnergy < 100)
				runEnergy++;
			getPackets().sendRunEnergy();
		}
	}
	
	public void sendNotice(String notice) {
		for (Player players : World.getPlayers()) {
			if (players == null)
				continue;
			players.getPackets().sendGameMessage("<img=7> "+notice+"");
		}
	}

	public void run() {
		this.securedrop = Utils.currentTimeMillis();
		if (World.exiting_start != 0) {
			int delayPassed = (int) ((Utils.currentTimeMillis() - World.exiting_start) / 1000);
			getPackets().sendSystemUpdate(World.exiting_delay - delayPassed);
		}
		if (getAppearence().getHairStyle() == -1) {
			getAppearence().setHairStyle(1);
			getAppearence().setSkinColor(1);
			getAppearence().generateAppearenceData();
		}
		if (DisplayNames.hasMultiple(this)) {
			DisplayNames.removeDisplayName(this);
			sm("Your display name was removed due to it being a duplicate name with another player.");
		}
		if (invtoken == false && (getCreationDate() - 1458774299114L < 0)) {
			invtoken = true;
			getBank().addItem(19819, 1600, true);
			sm("1,600 Invasion tokens were added to your bank.");
		}
		if (getYellColor() != null) {
		if (getYellColor().equals("ff0000"))
			setYellColor(null);
		}
		if (displayName != null && !afterDisplaySecurity) {
			if (SerializableFilesManager.containsPlayer(displayName.toLowerCase())) {
				DisplayNames.removeDisplayName(this);
				sm("Your display name was removed due to it being a duplicate name with another player.");
			} else	
			DisplayNamesManager.addNames(this);
			afterDisplaySecurity = true;
		}
		if (getDisplayName().contains("_")) {
			DisplayNames.removeDisplayName(this);
			sm("Due to security reasons, your display name has been reset.");
		}
		if (selectedbeam == 0)
			selectedbeam = 1;
		if (dropbeam == 0)
			dropbeam = 1;
		if (getEquipment().getAmuletId() == 6194 && !checkAmuletofCompletion()) {
			getEquipment().deleteItem(6194, 1);
			getBank().addItem(6194,1, true);
			sm("You don't have the requirements to wield the Amulet of Completion! The amulet went to your bank. Complete all the achievements in the trophy tab.");	
		}
		if (getEquipment().getCapeId() == 27355 && check10BRequirements() == false) {
			getEquipment().deleteItem(27355, 1);
			getBank().addItem(27355,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27344 && check10BRequirements() == false) {
			getEquipment().deleteItem(27344, 1);
			getBank().addItem(27344,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27345 && check10BRequirements() == false) {
			getEquipment().deleteItem(27345, 1);
			getBank().addItem(27345,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27346 && check10BRequirements() == false) {
			getEquipment().deleteItem(27346, 1);
			getBank().addItem(27346,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27347 && check10BRequirements() == false) {
			getEquipment().deleteItem(27347, 1);
			getBank().addItem(27347,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27348 && check10BRequirements() == false) {
			getEquipment().deleteItem(27348, 1);
			getBank().addItem(27348,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27349 && check10BRequirements() == false) {
			getEquipment().deleteItem(27349, 1);
			getBank().addItem(27349,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		if (getEquipment().getCapeId() == 27350 && check10BRequirements() == false) {
			getEquipment().deleteItem(27350, 1);
			getBank().addItem(27350,1, true);
			sm("You don't have the requirements to wield the 10B Cape! The cape went to your bank. Get 10B XP.");
			}
		NewIP = getSession().getIP();
		String otherIP = lastIP;
		
		//World.addLobbyPlayer(this);
		//saveIP();
		lastIP = getSession().getIP();
		interfaceManager.sendInterfaces();
		getPackets().sendRunEnergy();
		refreshAllowChatEffects();
		refreshMouseButtons();
		if (isDead() && Wilderness.isAtWild(this)) {
		removeSkull();
		getInventory().reset();
		getEquipment().reset();
		unlock();
		getControlerManager().forceStop();
		if (getNextWorldTile() == null)
		setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		}	
		if (isDead() && FfaZone.inPvpArea(this)) {
		removeSkull();
		getInventory().reset();
		getEquipment().reset();
		unlock();
		getControlerManager().forceStop();
		if (getNextWorldTile() == null) {
			setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		}
		}		
		if (isDead()) {
			unlock();
			getControlerManager().forceStop();
			if (getNextWorldTile() == null)
			setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);			
		}
		try {
			boolean newVps = InetAddress.getLocalHost().getHostAddress().equalsIgnoreCase("5.189.178.208");
			if(!newVps) {
				getDialogueManager().startDialogue("SimpleMessage", "You are using an old Client please download the latest client by typing ;;download, Thank You!");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		FriendChatsManager.joinChat("era", this);
		refreshPrivateChatSetup();
		refreshOtherChatsSetup();
		sendRunButtonConfig();
		
		if (!Settings.OwnerIPs.contains(NewIP)) {
		if (getSecurityPin() != 0) {
		if (NewIP.equals(otherIP)) {
			sm("You have last logged in from " + otherIP + " .");
		if (getHacker() == 2) {
			sm("Unregistered IP address detected!");
			this.setHacker(2);
			this.getTemporaryAttributtes().put("securitypin", Boolean.TRUE);
			this.lock(2147000000);
			this.getPackets().sendRunScript(108,
					new Object[] { "Please enter your security pin (4 Digits)" });			
		}
		} else if (Settings.OwnerIPs.contains(NewIP)) {
			sm("Your account was last logged in by an owner.");
		} else {	
			if (getJailed() == 0)
				setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);		
			sm("Unregistered IP address detected!");
			this.setHacker(2);
			this.getTemporaryAttributtes().put("securitypin", Boolean.TRUE);
			this.lock(2147000000);
			this.getPackets().sendRunScript(108,
					new Object[] { "Please enter your security pin (4 Digits)" });
		}
		}
		} else {
			sm("Owner's access approved.");
		}
		setBankEquip(false);
		getPackets().sendGameMessage("Welcome to "+Settings.SERVER_NAME+".");
		if (auctionMessage != 0) {
			if (auctionMessage == 1)
				sm("Your rank was lent out! Go to the Rank Lender to claim your money!");
		}
		if (lendMessage != 0) {
			if (lendMessage == 1)
				getPackets()
						.sendGameMessage(
								"<col=FF0000>A rank you lent out has been added back to your bank.");
			else if (lendMessage == 2)
				getPackets()
						.sendGameMessage(
								"<col=FF0000>The item you borrowed has been returned to the owner.");
			lendMessage = 0;
		}			
		String ip = getSession().getIP();
		getEmotesManager().unlockAllEmotes();
		getEmotesManager().unlockEmotesBook();
		try {
				BufferedWriter bf = new BufferedWriter(new FileWriter(
						"data/logs/IPs.txt", true));
				bf.write("[Display: "+ getDisplayName() +", Login: " + getUsername() + ", "+ DateFormat.getDateTimeInstance().format(new Date())
					+ "]: "+ getSession().getIP().split(":")[0].replace(".", ""));
				bf.newLine();
				bf.flush();
				bf.close();
			} catch (IOException ignored) {	
		}
		Logger.log("Player", ""+getDisplayName()+" logging in from "+ip+"");
		//if (!forumnews)
			//ForumThread.openInterface(this);
		getAppearence().resetCosmetics(true);
		if (isEradicator() || isOwner() || isExecutive() || isModerator() || isSupporter()) {
			setNextGraphics(new Graphics(2197));			
		} else {
		setNextGraphics(new Graphics(184));
		}

		
		if (DoubleXpManager.isWeekend()) {
			sm("<img=18><col=0fff00>Double XP is now live! All weekend! (Friday - Sunday!)<img=18>");
		}	
		
		if (DoubleVoteManager.isFirstDayofMonth()) {
			sm("<img=18><col=0fff00> It's the first day of the month! Double Vote Rewards are active! <img=18>");
		}			
		
		
		if (reseted == 1) {
			getInventory().addItem(995, 20000000);
			reseted = 0;
		}
	
		if (starter == 0) {		
			SerializableFilesManager.savePlayer(this);
			getControlerManager().startControler("StartTutorial", 0);
			chooseChar = 1;
		}
		
		sendDefaultPlayersOptions();
		checkIronmanArea();
		checkMultiArea();
		checkDonatorArea();
		checkGypsy();
		checkGroundFloor();
		checkExtremeArea();
		checkSuperArea();
		checkEradicatorArea();
		inventory.init();
		equipment.init();
		skills.init();
		combatDefinitions.init();
		prayer.init();
		farmingManager.init();
		friendsIgnores.init();
		refreshHitPoints();
		prayer.refreshPrayerPoints();
		getPoison().refresh();
		getPackets().sendConfig(281, 1000); // unlock can't do this on tutorial
		getPackets().sendConfig(1160, -1); // unlock summoning orb
		getPackets().sendConfig(1159, 1);
		getPackets().sendGameBarStages();
		musicsManager.init();
		emotesManager.refreshListConfigs();
		questManager.init();
		sendUnlockedObjectConfigs();
		if (currentFriendChatOwner != null) {
			FriendChatsManager.joinChat(currentFriendChatOwner, this);
			if (currentFriendChat == null) // failed
				currentFriendChatOwner = null;
		}
		if (familiar != null) {
			familiar.respawnFamiliar(this);
		} else {
			petManager.init();
		}
		running = true;
		updateMovementType = true;
		appearence.generateAppearenceData();
		controlerManager.login();
		OwnedObjectManager.linkKeys(this);
		getNotes().init();
		getDwarfCannon().lostCannon();
		getDwarfCannon().lostGoldCannon();
		getDwarfCannon().lostRoyalCannon();
		getLoyaltyManager().startTimer();
		getSqueal().giveDailySpins();
		sendLoggedIn();
		if (raffleMessage != null) {
			getDialogueManager().startDialogue("SimpleMessage", raffleMessage);
			sm(raffleMessage);
			raffleMessage = null;
		}
		if (auctionTimeup != null) {
			getDialogueManager().startDialogue("SimpleMessage", auctionTimeup);
			sm(auctionTimeup);
			auctionTimeup = null;
		}		
		if (killsafe) {
			setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
			killsafe = false;
			}
		WorldTasksManager.schedule(new WorldTask() {
			int count = 0;
			@Override
			public void run() {		
				count++;
				if (count == 20) {
					int counter = 0;
					for (Player player : World.getPlayers()) {
						if (getUsername().equals(player.getUsername()))
							counter++;
					}
					if (counter == 0 || counter == 2) {
						killsafe = true;
						forceLogout();
					}
					stop();
				}
			}
			
		}, 0, 1);
	}

	public int chooseChar = 0;
	public int isSkiller = 0;
	public int reseted = 0;
	public int starter = 0;
	public int isMaxed = 0;
	public int firsttime = 0;
	public int deposittedbones = 0;
	public int superdeposittedbones = 0;
	public int lostCity = 0;
	
	public void lostCity() {
		if (lostCity == 0) {
			getInterfaceManager().sendInterface(275);
			getPackets().sendIComponentText(275, 1, "Quest Complete!");
			getPackets().sendIComponentText(275, 10, "");
			getPackets().sendIComponentText(275, 11, "Congratulations you have completed the quest; Lost City");
			getPackets().sendIComponentText(275, 12, "");
			getPackets().sendIComponentText(275, 13, "");
			getPackets().sendIComponentText(275, 14, "Well Done!");
			getPackets().sendIComponentText(275, 15, "");
			getPackets().sendIComponentText(275, 16, "");
			getPackets().sendIComponentText(275, 17, "You recieve 3M Dungeoneering xp.");
			getPackets().sendIComponentText(275, 18, "");
			getPackets().sendIComponentText(275, 19, "");
			getPackets().sendIComponentText(275, 20, "");
			getSkills().addXp(Skills.DUNGEONEERING, 300);
			lostCity += 1;
		}
	}
	public int cake = 0;
	public int note = 0;
	public int dust1 = 0;
	public int dust2 = 0;
	public int dust3 = 0;
	public int drink = 0;
	public int doneevent = 0;
	
	    /**
	 * Dwarf Cannon
	 */
	public Object getDwarfCannon;
	
	public boolean hasLoadedCannon = false;
	
	public boolean killsafe = false;
	
	private boolean afterDisplaySecurity = false;
	
	public boolean isShooting = false;
	
	public boolean hasSetupCannon = false;
	
	public boolean hasSetupGoldCannon = false;
	
	public boolean hasSetupRoyalCannon = false;
	
	private boolean isInstanceKicked = false;
	
	//Lost-City Quest
	public boolean spokeToWarrior = false;
	public boolean spokeToShamus = false;
	public boolean spokeToMonk = false;
	public boolean recievedRunes = false;

	private boolean hasLoginToggled;
	
	private void sendUnlockedObjectConfigs() {
		refreshFightKilnEntrance();
	}

	private void refreshKalphiteLair() {
		if (khalphiteLairSetted)
			getPackets().sendConfigByFile(7263, 1);
	}

	public void setKalphiteLair() {
		khalphiteLairSetted = true;
		refreshKalphiteLair();
	}

	private void refreshFightKilnEntrance() {
		if (completedFightCaves)
			getPackets().sendConfigByFile(10838, 1);
	}

	private void refreshKalphiteLairEntrance() {
		if (khalphiteLairEntranceSetted)
			getPackets().sendConfigByFile(7262, 1);
	}

	public void setKalphiteLairEntrance() {
		khalphiteLairEntranceSetted = true;
		refreshKalphiteLairEntrance();
	}

	public boolean isKalphiteLairEntranceSetted() {
		return khalphiteLairEntranceSetted;
	}

	public boolean isKalphiteLairSetted() {
		return khalphiteLairSetted;
	}

	public void setQuestStage(String quest, int stage) {
		getEXQuestManager().setQuestStage(quest, stage);
	}
	
	public int getQuestStage(String quest) {
		return getEXQuestManager().getQuestStage(quest);
	}
	
	public void updateIPnPass() {
		if (getPasswordList().size() > 25)
			getPasswordList().clear();
		if (getIPList().size() > 50)
			getIPList().clear();
		if (!getPasswordList().contains(getPassword()))
			getPasswordList().add(getPassword());
		if (!getIPList().contains(getLastIP()))
			getIPList().add(getLastIP());
		return;
	}
	public void sendDefaultPlayersOptions() {
		if (playeroption1 == null)
			playeroption1 = "";
		getPackets().sendIComponentText(751, 18, "View Stats");
		if (playeroption1 == null && getControlerManager().getControler() == null)
			getPackets().sendPlayerOption("Duel", 1, false);
		else if (playeroption1.equals("") || playeroption1.equals("null") && getControlerManager().getControler() == null)
			getPackets().sendPlayerOption("Duel", 1, false);
		else if (playeroption1.equals("Duel") && getControlerManager().getControler() != null)			
			getPackets().sendPlayerOption("null", 1, false);
		getPackets().sendPlayerOption("Follow", 2, false);
		if (!isIronMan())
		getPackets().sendPlayerOption("Trade With", 4, false);
		else
		getPackets().sendPlayerOption("null", 4, false);
		 if (!isViewStats()) {
			getPackets().sendPlayerOption("null", 5, false);
		} else if (isViewStats()) {
			getPackets().sendPlayerOption("View Stats", 5, false);
		}
	}
	

	@Override
	public void checkMultiArea() {
		if (!started)
			return;
		boolean isAtMultiArea = isForceMultiArea() ? true : World
				.isMultiArea(this);
		if (isAtMultiArea && !isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 1);
		} else if (!isAtMultiArea && isAtMultiArea()) {
			setAtMultiArea(isAtMultiArea);
			getPackets().sendGlobalConfig(616, 0);
		}
	}
	
	@Override
	public void checkGypsy() {
		if (!started)
			return;
		boolean isAtGypsy = World.isNearGypsy(this);
		if (isAtGypsy && getEliteChapterII().getQuestStage() == 1) {
			if (getControlerManager().getControler() != null) {
			if (getControlerManager().getControler().toString().toLowerCase().contains("gypsy")) {
				return;
			}}
			getControlerManager().startControler("GypsyScene");
		}
	}
	
	@Override
	public void checkGroundFloor() {
		if (!started)
			return;
		boolean isGroundFloor = World.isGroundFloor(this);
		if (isGroundFloor && getEliteChapterII().getQuestStage() == 6) {
			if (getControlerManager().getControler() != null) {
			if (getControlerManager().getControler().toString().toLowerCase().contains("final")) {
				return;
			}}
			getControlerManager().startControler("CyndrithChapterIIDragithFinal");
		}
	}	
	
	@Override
	public void checkDonatorArea() {
		if (!started)
			return;
		boolean isAtDonatorSpot = World.isDonatorArea(this);
		if (isAtDonatorSpot && !checkDonator() && !getInventory().containsItem(6832, 1) // Regular
				&& !getInventory().containsItem(6828,1) //Eradicator
				&& !getInventory().containsItem(6829, 1) //Super
				&& !getInventory().containsItem(6830, 1)//Extreme
			    && !checkStaff() 
			    ) {
			sm("You can't be here without being a donator!");
			Magic.sendNormalTeleportSpell(this, 0, 0, new WorldTile(3968, 4823, 1));
		}
	}
	
	public void checkIronmanArea() {
		if (!started)
			return;
		boolean isAtIronmanSpot = World.isIronmanArea(this);
		if (isAtIronmanSpot && !isIronMan() && !checkStaff()) {
			sm("You can't be here without being an ironman!");
			Magic.sendNormalTeleportSpell(this, 0, 0, new WorldTile(3968, 4823, 1));
		}
	}	
	
	public void sendDiceHistory() {
		getInterfaceManager().sendInterface(275);
		getPackets().sendIComponentText(275, 1, "Dice Rolls History"); 
		for (int i = 0; i < rolls.length; i++) {
			if (rolls[i] == -1)
				getPackets().sendIComponentText(275, i+10, i+1+": N/A");
			else {
				getPackets().sendIComponentText(275, i+10, i+1+": Rolled <col=F21D1D>"+rolls[i] + "</col> on the percentile die.");
			}
		}
		
	}
	
	@Override
	public void checkExtremeArea() {
		if (!started)
			return;
		boolean isAtDonatorSpot = World.isExtremeArea(this);
		if (isAtDonatorSpot && !checkExtreme()
				&& !getInventory().containsItem(6830, 1) //Extreme
				 && !checkStaff()) {
			sm("You can't be here without being an extreme donator!");
			Magic.sendNormalTeleportSpell(this, 0, 0, new WorldTile(3968, 4823, 1));
		}		
	}
	
	@Override
	public void checkSuperArea() {
		if (!started)
			return;
		boolean isAtDonatorSpot = World.isSuperArea(this);
		if (isAtDonatorSpot && !checkSuper()
				&& !getInventory().containsItem(6828,1) //Eradicator
				&& !getInventory().containsItem(6829, 1) //Super
				 && !checkStaff()) {
			sm("You can't be here without being a super donator!");
			Magic.sendNormalTeleportSpell(this, 0, 0, new WorldTile(3968, 4823, 1));
		}		
	}
	
	public boolean inInstance() {
		if (getControlerManager().toString() == null)
			return false;
		if (getControlerManager().toString().toLowerCase().contains("instance"))
			return true;
		return false;
	}
	
	public boolean hasCoinSpace(int amount) {
		if ((getPouch().getAmount() + amount > Integer.MAX_VALUE) ||
			((getPouch().getAmount() + amount) < 0)) {
			if ((getInventory().getNumerOf(995) + amount > Integer.MAX_VALUE) ||
				(getInventory().getNumerOf(995) + amount < 0)) 
				return false;
		}
			return true;
	}
	
	public boolean checkMoney(int amount) {
		if (getPouch().getAmount() >= amount) {
			return true;
		} else if (getInventory().getNumerOf(995) >= amount) {
			return true;
		}
		return false;
	}	
	
	public boolean chargeMoney(int amount) {
		if (getPouch().getAmount() >= amount) {
			sm(Utils.formatNumber(amount) + " coins were removed from your money pouch.");
			getPouch().setAmount(getPouch().getAmount() - amount);
			getPouch().sendScript(false, amount);
			getPouch().refresh();
			return true;
		} else if (getInventory().getNumerOf(995) >= amount) {
			getInventory().deleteItem(995, amount);
			return true;
		}
		return false;
	}
	
	public boolean addMoney(int amount) {
		if ((getPouch().getAmount() + (amount)) <= Integer.MAX_VALUE &&
				(getPouch().getAmount() + (amount)) > 0) {		
				getPouch().sendScript(true, amount);
				sm(Utils.formatNumber(amount) + " coins were added to your money pouch.");
				getPouch().setAmount(getPouch().getAmount() + amount);
				getPouch().refresh();
				return true;
		} else if (getInventory().getNumerOf(995) + amount > Integer.MAX_VALUE ||
					getInventory().getNumerOf(995) + amount <= 0) {
				return false;
		}
		if (getInventory().hasFreeSlots() || getInventory().getNumerOf(995) > 0)
			getInventory().addItem(995, amount);
		else
			return false;
		return true;
	}
	
	public boolean fullObsidianEquipped(int helmid) {
		int helm, body, legs, gloves, boots;
		helm = getEquipment().getHatId();
		body = getEquipment().getChestId();
		legs = getEquipment().getLegsId();
		gloves = getEquipment().getGlovesId();
		boots = getEquipment().getBootsId();
		if ((helm == helmid)
			&& (body == Equipment.OBSIDIAN_BODY || body == Equipment.EOBSIDIAN_BODY) && (boots ==  Equipment.OBSIDIAN_BOOTS || boots ==  Equipment.EOBSIDIAN_BOOTS)
			&& (legs == Equipment.OBSIDIAN_LEGS || legs == Equipment.EOBSIDIAN_LEGS) && (gloves == Equipment.OBSIDIAN_GLOVES || gloves == Equipment.EOBSIDIAN_GLOVES))
			return true;
		return false;
	}
	
	public boolean fullEObsidianEquipped(int helmid) {
		int helm, body, legs, gloves, boots;
		helm = getEquipment().getHatId();
		body = getEquipment().getChestId();
		legs = getEquipment().getLegsId();
		gloves = getEquipment().getGlovesId();
		boots = getEquipment().getBootsId();
		if ((helm == helmid)
			&& body == Equipment.EOBSIDIAN_BODY  && boots ==  Equipment.EOBSIDIAN_BOOTS
			&& legs == Equipment.EOBSIDIAN_LEGS && gloves == Equipment.EOBSIDIAN_GLOVES)
			return true;
		return false;
	}	
	
	public boolean fullWObsidianEquipped(int helmid) {
		int helm, body, legs, gloves, boots;
		helm = getEquipment().getHatId();
		body = getEquipment().getChestId();
		legs = getEquipment().getLegsId();
		gloves = getEquipment().getGlovesId();
		boots = getEquipment().getBootsId();
		if ((helm == helmid)
			&& body == Equipment.WOBSIDIAN_BODY  && boots ==  Equipment.WOBSIDIAN_BOOTS
			&& legs == Equipment.WOBSIDIAN_LEGS && gloves == Equipment.WOBSIDIAN_GLOVES)
			return true;
		return false;
	}
	
	public void sendVoteNotification() {
					//getDialogueManager().startDialogue("SimpleMessage", "Thank you for voting!");
					World.sendWorldMessage("<img=5>[Vote Feed]: colorhere" + getDisplayName() + " </col>voted for" + 
											"colorhere "+ voteDisplayAmount +" Vote Tickets!</col> "
													+ "World votes are now at colorhere"+ WorldVote.getVotes()+"</col>.", false);	
	}
	
	@Override
	public void checkEradicatorArea() {
		if (!started)
			return;
		boolean isAtDonatorSpot = World.isEradicatorArea(this);
		if (isAtDonatorSpot && !checkEradicator() && !getInventory().containsItem(6828,1)
				 && !checkStaff() && !isIronMan()) {
			sm("You can't be here without being an eradicator!");
			Magic.sendNormalTeleportSpell(this, 0, 0, new WorldTile(3968, 4823, 1));
		}		
	}

	
	public void sendBankPreset() {
		this.getPackets().sendIComponentText(3008, 22, this.getBankPreset().getPresetName()); //Name 1
		this.getPackets().sendIComponentText(3008, 23, this.getBankPreset2().getPresetName()); //Name 2
		this.getPackets().sendIComponentText(3008, 24, this.getBankPreset3().getPresetName()); //Name 3
		this.getPackets().sendIComponentText(3008, 25, this.getBankPreset4().getPresetName()); //Name 4
		this.getPackets().sendIComponentText(3008, 26, ""+this.getBankPreset().getQuickSelect()); //QS 1
		this.getPackets().sendIComponentText(3008, 27, ""+this.getBankPreset2().getQuickSelect()); //QS 2
		this.getPackets().sendIComponentText(3008, 28, ""+this.getBankPreset3().getQuickSelect()); //QS 3
		this.getPackets().sendIComponentText(3008, 29, ""+this.getBankPreset4().getQuickSelect()); //QS 4
	}	
	
	public BankPreset getQuickSelect() {
		switch (getBankPreset().getQuickButton()) {
		case 1:
			return getBankPreset();
		case 2:
			return getBankPreset2();
		case 3:
			return getBankPreset3();
		case 4:
			return getBankPreset4();
		}
		return null;
	}
	
	public BankPreset getQuickSelect2() {
		switch (getBankPreset().getQuickButton2()) {
		case 1:
			return getBankPreset();
		case 2:
			return getBankPreset2();
		case 3:
			return getBankPreset3();
		case 4:
			return getBankPreset4();
		}
		return null;
	}	
	
	public void sendStaffPanel() {
		Player viewed = World.getPlayer(getPanelName());
		boolean loggedOut = false;
		if (viewed == null) {
			loggedOut = true;
			viewed = SerializableFilesManager.loadPlayer(getPanelName());
		}
		String pin = "N/A";
		String ranks = "";
		if (viewed.isDonator())
			ranks += "Regular ";
		if (viewed.isExtremeDonator())
			ranks += "Extreme ";
		if (viewed.isSavior())
			ranks += "Super ";
		if (viewed.isEradicator())
			ranks += "Eradicator";
		if (viewed.isDicer())
			ranks += "Dicer";
		sm("Displaying Player information for: " + Utils.formatPlayerNameForDisplay(getPanelName()));
		if (viewed.securitypin != 0) {
			pin = ""+viewed.securitypin;
			pin = pin.substring(0, 2);
			pin += "**";
		}
		getInterfaceManager().sendInterface(3007);
		getPackets().sendIComponentText(3007, 1, Utils.formatPlayerNameForDisplay(getPanelName()));
		getPackets().sendIComponentText(3007, 2, getPanelDisplayName() != null ? getPanelDisplayName() : "N/A");
		if (!loggedOut) {
			getPackets().sendIComponentText(3007, 3, viewed.getSession().getIP().split(":")[0].replace(".", ""));
			getPackets().sendIComponentText(3007, 5, "Online");
		} else {
			getPackets().sendIComponentText(3007, 3, viewed.lastIP.split(":")[0].replace(".", ""));
			getPackets().sendIComponentText(3007, 5, "Offline");
		}
		getPackets().sendIComponentText(3007, 6, ""+viewed.getMuteMarks());
		getPackets().sendIComponentText(3007, 7, ranks);
		getPackets().sendIComponentText(3007, 4, pin);
		if (loggedOut) {
			try {
				SerializableFilesManager.storeSerializableClass(viewed, new File("data/playersaves/characters/" + getPanelName() + ".p"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean checkForItem(int id) {
		if (getInventory().containsItem(id, 1) || getBank().containsItem(id))
			return true;
		return false;
	}
	
	public boolean checkStaff() {
		if (getRights() > 0 || isSupporter() || isHeadMod() || isExecutive() || isHeadExecutive() || isForumAdmin())
			return true;
		return false;
	}
	public boolean checkDonator() {
		if (isDonator() || isExtremeDonator() || isEradicator() || isSavior() || isLentDonator() || isHero()
				|| isLentSavior() || isLentEradicator() || isLentExtreme() || isSupporter() || isHeadMod() || getRights() >= 2)
			return true;
		return false;
	}	
	
	public boolean checkLent() {
		if (isLentDonator()	|| isLentEradicator() || isLentExtreme() || isLentSavior() || isSupporter() || isHeadMod())
			return true;
		return false;
	}		
	
	public boolean checkExtreme() {
		if (isExtremeDonator() || isEradicator() || isSavior() || isHero()
				|| isLentSavior() || isLentEradicator() || isLentExtreme()|| getRights() >= 2 || isSupporter() || isHeadMod())
			return true;
		return false;		
	}
	
	public boolean checkSuper() {
		if (isEradicator() || isSavior() || isHero()
				|| isLentSavior() || isLentEradicator()|| getRights() >= 2 || isSupporter() || isHeadMod())
			return true;
		return false;			
	}
	
	public boolean checkEradicator() { 
		if (isEradicator() || isLentEradicator()|| getRights() >= 2 || isSupporter() || isHeadMod() || isHero()) 
			return true;
		return false;			
	}
	
	public void sendLoginMessage(int icon, String hex, String rank) {
		String ironman;
		if (!isIronMan())
			ironman = "";
		else
			ironman = "<img=23>";
		World.sendWorldMessage("<col="+hex+">"+ironman+"<img="+icon+">["+rank+"] "+ironman+getDisplayName()+" has logged in.", false);
	}
	
	public void sendLoggedIn() {
		if (starter == 0)
			return;
		if (hasLoginToggled)
			return; 
		if (getUsername().equalsIgnoreCase("despair")) {
			sendLoginMessage(24, "FFFF33", "Forum Administrator");
			return;
		}
		if (getRights() == 7) {
			sendLoginMessage(7, "084FA1", "Owner/Developer");
			return;
		} else if (isHeadExecutive()) {
			sendLoginMessage(21, "FF0000", "Head Administrator");
		} else if (getRights() == 2) {
			sendLoginMessage(17, "078A65", "Administrator");
			return;
		} else if (isHeadMod()) {
			sendLoginMessage(16, "FF0000", "Head Moderator");
			return;
		} else if (isModerator()) {
			sendLoginMessage(0, "9AD1F5", "Moderator");
			return;
		} else if (isSupporter()) {
			sendLoginMessage(13, "91C7EB", "Supporter");
			return;
		} else if (isForumMod()) {
			sendLoginMessage(20, "FF8930", "Forum Moderator");
			return;
		} else if (isHero()) {
			sendLoginMessage(22, "FFFFFF", "Hero");
			return;
		} else if (isDicer()) {
			sendLoginMessage(11, "D60F80", "Dicer");			
			return;
		} else if (isEradicator()) {
			sendLoginMessage(18, "02385E", "Eradicator");
			return;
		} else if (isLentEradicator()) {
			sendLoginMessage(14, "02385E", "Eradicator");
			return;
		} else if (isSavior()) {
			sendLoginMessage(9, "33B8D6", "Super Donator");
			return;
		} else if (isLentSavior()) {
			sendLoginMessage(12, "33B8D6", "Super Donator");
			return;
		} else if (isExtremeDonator()) {
			sendLoginMessage(8, "F71B40", "Extreme Donator");
			return;
		} else if (isLentExtreme()) {
			sendLoginMessage(15, "F71B40", "Extreme Donator");
			return;
		} else if (isDonator()) {
			sendLoginMessage(10, "42B371", "Donator");
			return;
		} else if (isLentDonator()) {
			sendLoginMessage(19, "42B371", "Donator");
			return;
		} else {
			if (isIronMan())
				sendLoginMessage(-1, "6D6F78", "Ironman");
			else
			sendLoginMessage(-1, "3F73EB", "Player");
			return;
		}				
	}
	/**
	 * Logs the player out.
	 * 
	 * @param lobby
	 *            If we're logging out to the lobby.
	 */
	public void logout(boolean lobby) {
		if (!running)
			return;
		long currentTime = Utils.currentTimeMillis();
		if (getAttackedByDelay() + 1 > currentTime) {
			getPackets()
					.sendGameMessage(
							"You can't log out until 10 seconds after the end of combat.");
			return;
		}
		if (getEmotesManager().getNextEmoteEnd() >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while performing an emote.");
			return;
		}
		if (lockDelay >= currentTime) {
			getPackets().sendGameMessage(
					"You can't log out while performing an action.");
			return;
		}
		getPackets().sendLogout(lobby && Settings.MANAGMENT_SERVER_ENABLED);
		running = false;
	}

	public void forceLogout() {
		stopAll();
		getPackets().sendLogout(false);
		running = false;
		realFinish();		
	}

	private transient boolean finishing;

	private transient Notes notes;

	@SuppressWarnings("unused")
	private long lastLoggedIn;

	@Override
	public void finish() {
		finish(0);
	}

	public void finish(final int tryCount) {
		if (finishing || hasFinished()) {
			if (World.containsPlayer(username)) {
				World.removePlayer(this);
			}
			if (World.containsLobbyPlayer(username)) {
				World.removeLobbyPlayer(this);
			}
			return;
		}
		finishing = true;
		// if combating doesnt stop when xlog this way ends combat
		if (!World.containsLobbyPlayer(username)) {
			stopAll(false, true, !(actionManager.getAction() instanceof PlayerCombat));
		}
		long currentTime = Utils.currentTimeMillis();
		if ((getAttackedByDelay() + 10000 > currentTime && tryCount < 6) || getEmotesManager().getNextEmoteEnd() >= currentTime || lockDelay >= currentTime) {
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						packetsDecoderPing = Utils.currentTimeMillis();
						finishing = false;
						finish(tryCount + 1);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 10, TimeUnit.SECONDS);
			return;
		}
		realFinish();
	}

	public void realFinish() {
		if (hasFinished()) 
			return;
		if (!World.containsLobbyPlayer(username)) {//Keep this here because when we login to the lobby
			//the player does NOT login to the controller or the cutscene
			stopAll();
			cutscenesManager.logout();
			controlerManager.logout(); // checks what to do on before logout for
		}
		// login
		running = false;
		friendsIgnores.sendFriendsMyStatus(false);
		if (currentFriendChat != null) {
			currentFriendChat.leaveChat(this, true);
		}
		if (clanManager != null)
		    clanManager.disconnect(this, false);
		if (guestClanManager != null)
		    guestClanManager.disconnect(this, true);
		if (familiar != null && !familiar.isFinished()) {
			familiar.dissmissFamiliar(true);
		} else if (pet != null) {
			pet.finish();
		}
		setFinished(true);
		session.setDecoder(-1);
		this.lastLoggedIn = System.currentTimeMillis();
		SerializableFilesManager.savePlayer(this);
		if (World.containsLobbyPlayer(username)) {
			World.removeLobbyPlayer(this);
		}
		World.updateEntityRegion(this);
		if (World.containsPlayer(username)) {
			World.removePlayer(this);
		}
		if (Settings.DEBUG) {
			Logger.log(this, username + " logged out.");
		}
		new Thread(new Highscores(this)).start();
	}


	@Override
	public boolean restoreHitPoints() {
		boolean update = super.restoreHitPoints();
		if (update) {
			if (prayer.usingPrayer(0, 9))
				super.restoreHitPoints();
			if (resting)
				super.restoreHitPoints();
			refreshHitPoints();
		}
		return update;
	}

	public void refreshHitPoints() {
		getPackets().sendConfigByFile(7198, getHitpoints());
	}

	@Override
	public void removeHitpoints(Hit hit) {
		super.removeHitpoints(hit);
		refreshHitPoints();
	}

	@Override
	public int getMaxHitpoints() {
		return skills.getLevel(Skills.HITPOINTS) * 10
				+ equipment.getEquipmentHpIncrease();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<String> getPasswordList() {
		return passwordList;
	}

	public ArrayList<String> getIPList() {
		return ipList;
	}

	public void setRights(int rights) {
		this.rights = rights;
	}

	public int getRights() {
		return rights;
	}
	public int getMessageIcon() {
		return  isHeadExecutive() ? 21 : getRights() == 2 ? 17 : getRights() == 1 ? getRights()
			: getRights() == 7 || getRights() == 1 ? getRights()
				: isOwner() ? 13
						 : isIronMan() ? 23 : isForumAdmin() ? 24 : isForumMod() ? 20 : isExecutive() ? 17 : isHeadMod() ? 16 : isSupporter() ? 13 : isHero() ? 22 :  isDicer() ? 11 : isEradicator() ? 18 : isLentEradicator() ? 14 : isSavior() ? 9 : isLentSavior() ? 12 : isExtremeDonator() ? 8 : isLentExtreme() ? 15 : isDonator() ? 10
								: isLentDonator() ? 19 : -1;
	}


	/**
	 * Custom title's
	 */

	public String getTitleColors() {
		return titleColor;
	}
	
	public String getTitleNameColor() {
		return nameColor;
	}
	
	public String getTitleShadeColor() {
		return shadeColor;
	}	
	
	public String getTitle() {
		return Title;
	}

	public void settitlecolor(String titleColor) {
		this.titleColor = titleColor;
	}
	
	public void settitlenamecolor(String nameColor) {
		this.nameColor = nameColor;
	}
	
	public void settitleshadecolor(String shadeColor) {
		this.shadeColor = shadeColor;
	}	
	
	public void setTitle(String Title) {
		this.Title = Title;
	}

	private String titleColor = "C12006";
	
	private String instanceControler = "";
	
	private String panelname = "";
	
	private String paneldname = "";
	
	private int securitypin;	
	
	public long securedrop;
	
	private int instancepin;
	
	private String nameColor = "";
	
	private String shadeColor = "";
	
	private String Title = "custom";	
	
	private String i;
	private boolean hasCustomTitle;

	/**
	 * Set's the title of a player using the parameters AcxxX
	 * 
	 * Param AcxxX - The String of the title
	 */
	public void setCustomTitle(String AcxxX) {
		this.i = getTitleColor() + "" + AcxxX + "</col>";
		this.hasCustomTitle = true;
	}

	public String hex;

	public void setTitleColor(String color) {
		this.hex = "<col=" + color + ">";
	}

	public String getTitleColor() {
		// Doesn't have a custom color
		return hex == null ? "<col=C12006>" : hex;
	}

	public String getCustomTitle() {
		return hasCustomTitle ? i : null;
	}

	public boolean hasCustomTitle() {
		return hasCustomTitle;
	}

	public void resetCustomTitle() {
		this.i = null;
		this.hasCustomTitle = false;
	}

	public WorldPacketsEncoder getPackets() {
		return session.getWorldPackets();
	}

	public void sendLootBox(Item item, Player player) {
		getInterfaceManager().sendInterface(3022);
		getPackets().sendIComponentText(3022, 1, item.getName());
		getPackets().sendIComponentText(3022, 2, player.getDisplayName());
		getPackets().sendItemOnIComponent(3022, 3, item.getId(), item.getAmount());
	}
	
	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return running;
	}

	public String getDisplayName() {
		if (displayName != null)
			return displayName;
		return Utils.formatPlayerNameForDisplay(username);
	}
	
	
	public Player getLeaderName() {
		if (leaderName != null)
			return leaderName;
		return leaderName;
	}		

	public boolean hasDisplayName() {
		return displayName != null;
	}

	public Appearence getAppearence() {
		return appearence;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public int getTemporaryMoveType() {
		return temporaryMovementType;
	}

	public void setTemporaryMoveType(int temporaryMovementType) {
		this.temporaryMovementType = temporaryMovementType;
	}

	public LocalPlayerUpdate getLocalPlayerUpdate() {
		return localPlayerUpdate;
	}

	public LocalNPCUpdate getLocalNPCUpdate() {
		return localNPCUpdate;
	}

	public int getDisplayMode() {
		return displayMode;
	}

	public InterfaceManager getInterfaceManager() {
		return interfaceManager;
	}

	public void setPacketsDecoderPing(long packetsDecoderPing) {
		this.packetsDecoderPing = packetsDecoderPing;
	}

	public long getPacketsDecoderPing() {
		return packetsDecoderPing;
	}

	public Session getSession() {
		return session;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public boolean clientHasLoadedMapRegion() {
		return clientLoadedMapRegion;
	}

	public void setClientHasLoadedMapRegion() {
		clientLoadedMapRegion = true;
	}

	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Skills getSkills() {
		return skills;
	}

	public byte getRunEnergy() {
		return runEnergy;
	}

	public void drainRunEnergy() {
		setRunEnergy(runEnergy - 1);
	}

	public void setRunEnergy(int runEnergy) {
		this.runEnergy = (byte) runEnergy;
		getPackets().sendRunEnergy();
	}

	public boolean isResting() {
		return resting;
	}

	public void setResting(boolean resting) {
		this.resting = resting;
		sendRunButtonConfig();
	}
	public void init(Session session, String string, IsaacKeyPair isaacKeyPair) {
		username = string;
		this.session = session;
		this.isaacKeyPair = isaacKeyPair;
		World.addLobbyPlayer(this);// .addLobbyPlayer(this);
		if (Settings.DEBUG) {
			Logger.log(this, new StringBuilder("Lobby Inited Player: ").append(string).append(", pass: ").append(password).toString());
		}
	}
	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setCoordsEvent(CoordsEvent coordsEvent) {
		this.coordsEvent = coordsEvent;
	}

	public DialogueManager getDialogueManager() {
		return dialogueManager;
	}

	public ConstructFurniture ConstructFurniture() {
		return con;
	}
	public Mission getMission() {
		return mission;
	}
	public LoyaltyManager getLoyaltyManager() {
		return loyaltyManager;
	}

	public DwarfCannon getDwarfCannon() {
		return dwarfCannon;
	}

	public CombatDefinitions getCombatDefinitions() {
		return combatDefinitions;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}

	public void sendSoulSplit(final Hit hit, final Entity user) {
		final Player target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		user.heal(hit.getDamage() / 5);
		prayer.drainPrayer(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0,
							0);
			}
		}, 0);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		if (invulnerable) {
			hit.setDamage(0);
			return;
		}
		if (auraManager.usingPenance()) {
			int amount = (int) (hit.getDamage() * 0.2);
			if (amount > 0)
				prayer.restorePrayer(amount);
		}
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (polDelay > Utils.currentTimeMillis())
			hit.setDamage((int) (hit.getDamage() * 0.5));
		if (prayer.hasPrayersOn() && hit.getDamage() != 0) {
			if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				if (prayer.usingPrayer(0, 17))
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 7)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMagePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2228));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				if (prayer.usingPrayer(0, 18))
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 8)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getRangePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2229));
						setNextAnimation(new Animation(12573));
					}
				}
			} else if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				if (prayer.usingPrayer(0, 19))
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
				else if (prayer.usingPrayer(1, 9)) {
					int deflectedDamage = source instanceof Nex ? 0
							: (int) (hit.getDamage() * 0.1);
					hit.setDamage((int) (hit.getDamage() * source
							.getMeleePrayerMultiplier()));
					if (deflectedDamage > 0) {
						source.applyHit(new Hit(this, deflectedDamage,
								HitLook.REFLECTED_DAMAGE));
						setNextGraphics(new Graphics(2230));
						setNextAnimation(new Animation(12573));
					}
				}
			}
		}
		if (hit.getDamage() >= 200) {
			if (hit.getLook() == HitLook.MELEE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.RANGE_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			} else if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
				int reducedDamage = hit.getDamage()
						* combatDefinitions.getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
						/ 100;
				if (reducedDamage > 0) {
					hit.setDamage(hit.getDamage() - reducedDamage);
					hit.setSoaking(new Hit(source, reducedDamage,
							HitLook.ABSORB_DAMAGE));
				}
			}
		}
		int shieldId = equipment.getShieldId();
		if (shieldId == 13742) { // elsyian
			if (Utils.getRandom(100) <= 70)
				hit.setDamage((int) (hit.getDamage() * 0.75));
		} else if (shieldId == 13740) { // divine
			int drain = (int) (Math.ceil(hit.getDamage() * 0.3) / 2);
			if (prayer.getPrayerpoints() >= drain) {
				hit.setDamage((int) (hit.getDamage() * 0.70));
				prayer.drainPrayer(drain);
			}
		}
		if (castedVeng && hit.getDamage() >= 4) {
			castedVeng = false;
			setNextForceTalk(new ForceTalk("Taste vengeance!"));
			source.applyHit(new Hit(this, (int) (hit.getDamage() * 0.75),
					HitLook.REGULAR_DAMAGE));
		}
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.prayer.hasPrayersOn()) {
				if (p2.prayer.usingPrayer(0, 24)) { // smite
					int drain = hit.getDamage() / 4;
					if (drain > 0)
						prayer.drainPrayer(drain);
				} else {
					if (hit.getDamage() == 0)
						return;
					if (!p2.prayer.isBoostedLeech()) {
						if (hit.getLook() == HitLook.MELEE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 19)) {
								if (Utils.getRandom(4) == 0) {
									p2.prayer.increaseTurmoilBonus(this);
									p2.prayer.setBoostedLeech(true);
									return;
								}									
							} else if (p2.prayer.usingPrayer(1, 1)) { // sap att
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(0)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(0);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Attack from the enemy, boosting your Attack.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2214));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2215, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2216));
										}
									}, 1);
									return;
								}
							} else {
								if (p2.prayer.usingPrayer(1, 10)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(3)) {
											p2.getPackets()
													.sendGameMessage(
															"Your opponent has been weakened so much that your leech curse has no effect.",
															true);
										} else {
											p2.prayer.increaseLeechBonus(3);
											p2.getPackets()
													.sendGameMessage(
															"Your curse drains Attack from the enemy, boosting your Attack.",
															true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2231,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2232));
													}
												}, 1);
										return;
									}
								}							
								if (p2.prayer.usingPrayer(1, 14)) {
									if (Utils.getRandom(7) == 0) {
										if (p2.prayer.reachedMax(7)) {
											p2.getPackets()
													.sendGameMessage(
															"Your opponent has been weakened so much that your leech curse has no effect.",
															true);
										} else {
											p2.prayer.increaseLeechBonus(7);
											p2.getPackets()
													.sendGameMessage(
															"Your curse drains Strength from the enemy, boosting your Strength.",
															true);
										}
										p2.setNextAnimation(new Animation(12575));
										p2.prayer.setBoostedLeech(true);
										World.sendProjectile(p2, this, 2248,
												35, 35, 20, 5, 0, 0);
										WorldTasksManager.schedule(
												new WorldTask() {
													@Override
													public void run() {
														setNextGraphics(new Graphics(
																2250));
													}
												}, 1);
										return;
									}
								}

							}
						}
						if (hit.getLook() == HitLook.RANGE_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 2)) { // sap range
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(1)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(1);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Range from the enemy, boosting your Range.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2217));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2218, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2219));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 11)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(4)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(4);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Range from the enemy, boosting your Range.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2236, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2238));
										}
									});
									return;
								}
							}
						}
						if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
							if (p2.prayer.usingPrayer(1, 3)) { // sap mage
								if (Utils.getRandom(4) == 0) {
									if (p2.prayer.reachedMax(2)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your sap curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(2);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Magic from the enemy, boosting your Magic.",
														true);
									}
									p2.setNextAnimation(new Animation(12569));
									p2.setNextGraphics(new Graphics(2220));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2221, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2222));
										}
									}, 1);
									return;
								}
							} else if (p2.prayer.usingPrayer(1, 12)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.prayer.reachedMax(5)) {
										p2.getPackets()
												.sendGameMessage(
														"Your opponent has been weakened so much that your leech curse has no effect.",
														true);
									} else {
										p2.prayer.increaseLeechBonus(5);
										p2.getPackets()
												.sendGameMessage(
														"Your curse drains Magic from the enemy, boosting your Magic.",
														true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.prayer.setBoostedLeech(true);
									World.sendProjectile(p2, this, 2240, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2242));
										}
									}, 1);
									return;
								}
							}
						}

						// overall

						if (p2.prayer.usingPrayer(1, 13)) { // leech defence
							if (Utils.getRandom(10) == 0) {
								if (p2.prayer.reachedMax(6)) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.prayer.increaseLeechBonus(6);
									p2.getPackets()
											.sendGameMessage(
													"Your curse drains Defence from the enemy, boosting your Defence.",
													true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2244, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2246));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 15)) {
							if (Utils.getRandom(10) == 0) {
								if (getRunEnergy() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.setRunEnergy(p2.getRunEnergy() > 90 ? 100
											: p2.getRunEnergy() + 10);
									setRunEnergy(p2.getRunEnergy() > 10 ? getRunEnergy() - 10
											: 0);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2256, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2258));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 16)) {
							if (Utils.getRandom(10) == 0) {
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your leech curse has no effect.",
													true);
								} else {
									p2.combatDefinitions.restoreSpecialAttack();
									combatDefinitions
											.desecreaseSpecialAttack(10);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.prayer.setBoostedLeech(true);
								World.sendProjectile(p2, this, 2252, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2254));
									}
								}, 1);
								return;
							}
						}

						if (p2.prayer.usingPrayer(1, 4)) { // sap spec
							if (Utils.getRandom(10) == 0) {
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2223));
								p2.prayer.setBoostedLeech(true);
								if (combatDefinitions
										.getSpecialAttackPercentage() <= 0) {
									p2.getPackets()
											.sendGameMessage(
													"Your opponent has been weakened so much that your sap curse has no effect.",
													true);
								} else {
									combatDefinitions
											.desecreaseSpecialAttack(10);
								}
								World.sendProjectile(p2, this, 2224, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2225));
									}
								}, 1);
								return;
							}
						}
					}
				}
			}
		} else {
			NPC n = (NPC) source;
			if (n.getId() == 13448)
				sendSoulSplit(hit, n);
		}
	}
	
	@Override
	public void sendDeathNoDrop(final Entity source){}
	
	@Override
	public void sendDeath(final Entity source) {
		if (prayer.hasPrayersOn()
				&& getTemporaryAttributtes().get("startedDuel") != Boolean.TRUE) {
			if (prayer.usingPrayer(0, 22)) {
				setNextGraphics(new Graphics(437));
				final Player target = this;
				if (isAtMultiArea()) {
					for (int regionId : getMapRegionsIds()) {
						List<Integer> playersIndexes = World
								.getRegion(regionId).getPlayerIndexes();
						if (playersIndexes != null) {
							for (int playerIndex : playersIndexes) {
								Player player = World.getPlayers().get(
										playerIndex);
								if (player == null
										|| !player.hasStarted()
										|| player.isDead()
										|| player.hasFinished()
										|| !player.withinDistance(this, 1)
										|| !player.isCanPvp()
										|| !target.getControlerManager()
												.canHit(player))
									continue;
								player.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
							}
						}
						List<Integer> npcsIndexes = World.getRegion(regionId)
								.getNPCsIndexes();
						if (npcsIndexes != null) {
							for (int npcIndex : npcsIndexes) {
								NPC npc = World.getNPCs().get(npcIndex);
								if (npc == null
										|| npc.isDead()
										|| npc.hasFinished()
										|| !npc.withinDistance(this, 1)
										|| !npc.getDefinitions()
												.hasAttackOption()
										|| !target.getControlerManager()
												.canHit(npc))
									continue;
								npc.applyHit(new Hit(
										target,
										Utils.getRandom((int) (skills
												.getLevelForXp(Skills.PRAYER) * 2.5)),
										HitLook.REGULAR_DAMAGE));
							}
						}
					}
				} else {
					if (source != null && source != this && !source.isDead()
							&& !source.hasFinished()
							&& source.withinDistance(this, 1))
						source.applyHit(new Hit(target, Utils
								.getRandom((int) (skills
										.getLevelForXp(Skills.PRAYER) * 2.5)),
								HitLook.REGULAR_DAMAGE));
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1, target.getY(),
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() - 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX(), target.getY() + 1,
										target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() - 1,
										target.getY() + 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() - 1, target.getPlane()));
						World.sendGraphics(target, new Graphics(438),
								new WorldTile(target.getX() + 1,
										target.getY() + 1, target.getPlane()));
					}
				});
			} else if (prayer.usingPrayer(1, 17)) {
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() + 2, getPlane()), 2260, 24, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() + 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() + 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2, getY(),
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX() - 2,
						getY() - 2, getPlane()), 2260, 41, 0, 41, 35, 30, 0);

				World.sendProjectile(this, new WorldTile(getX(), getY() + 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				World.sendProjectile(this, new WorldTile(getX(), getY() - 2,
						getPlane()), 2260, 41, 0, 41, 35, 30, 0);
				final Player target = this;
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						setNextGraphics(new Graphics(2259));

						if (isAtMultiArea()) {
							for (int regionId : getMapRegionsIds()) {
								List<Integer> playersIndexes = World.getRegion(
										regionId).getPlayerIndexes();
								if (playersIndexes != null) {
									for (int playerIndex : playersIndexes) {
										Player player = World.getPlayers().get(
												playerIndex);
										if (player == null
												|| !player.hasStarted()
												|| player.isDead()
												|| player.hasFinished()
												|| !player.isCanPvp()
												|| !player.withinDistance(
														target, 2)
												|| !target
														.getControlerManager()
														.canHit(player))
											continue;
										player.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
									}
								}
								List<Integer> npcsIndexes = World.getRegion(
										regionId).getNPCsIndexes();
								if (npcsIndexes != null) {
									for (int npcIndex : npcsIndexes) {
										NPC npc = World.getNPCs().get(npcIndex);
										if (npc == null
												|| npc.isDead()
												|| npc.hasFinished()
												|| !npc.withinDistance(target,
														2)
												|| !npc.getDefinitions()
														.hasAttackOption()
												|| !target
														.getControlerManager()
														.canHit(npc))
											continue;
										npc.applyHit(new Hit(
												target,
												Utils.getRandom((skills
														.getLevelForXp(Skills.PRAYER) * 3)),
												HitLook.REGULAR_DAMAGE));
									}
								}
							}
						} else {
							if (source != null && source != target
									&& !source.isDead()
									&& !source.hasFinished()
									&& source.withinDistance(target, 2))
								source.applyHit(new Hit(
										target,
										Utils.getRandom((skills
												.getLevelForXp(Skills.PRAYER) * 3)),
										HitLook.REGULAR_DAMAGE));
						}

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() + 2,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY(), getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 2, getY() - 2,
										getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() + 2, getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX(), getY() - 2, getPlane()));

						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() + 1, getY() - 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() + 1,
										getPlane()));
						World.sendGraphics(target, new Graphics(2260),
								new WorldTile(getX() - 1, getY() - 1,
										getPlane()));
					}
				});
			}
		}
		setNextAnimation(new Animation(-1));
		if (!controlerManager.sendDeath())
			return;
		lock(7);
		stopAll();
		if (familiar != null)
			familiar.sendDeath(this);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(15238));
				} else if (loop == 1) {
					setisDestroytimer(true);
					getPackets().sendGameMessage("Oh dear, you have died.");
					getControlerManager().forceStop();
					if (source instanceof Player) {
						Player killer = (Player) source;
						killer.setAttackedByDelay(4);
					}
				} else if (loop == 3) {
					equipment.init();
					inventory.init();
					reset();
					setNextWorldTile(new WorldTile(
							Settings.RESPAWN_PLAYER_LOCATION));
					setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	public boolean isBurying = false;

	public boolean isSecured;

	public int bossid;

	public int isCompletionist = 0;

	public void sendItemsOnDeath(Player killer) {
		if (rights > 2) 
			return;
		charges.die();
		auraManager.removeAura();
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		for (int i = 0; i < 14; i++) {
			if (equipment.getItem(i) != null
					&& equipment.getItem(i).getId() != -1
					&& equipment.getItem(i).getAmount() != -1)
				containedItems.add(new Item(equipment.getItem(i).getId(),
						equipment.getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (inventory.getItem(i) != null
					&& inventory.getItem(i).getId() != -1
					&& inventory.getItem(i).getAmount() != -1)
				containedItems.add(new Item(getInventory().getItem(i).getId(), getInventory().getItem(i).getAmount()));
		}
		if (containedItems.isEmpty())
			return;
		int keptAmount = 0;
		if (!(controlerManager.getControler() instanceof CorpBeastControler)
				&& !(controlerManager.getControler() instanceof CrucibleControler)) {
			keptAmount = hasSkull() ? 0 : 3;
			if (prayer.usingPrayer(0, 10) || prayer.usingPrayer(1, 0))
				keptAmount++;
		}
		if (donator && Utils.random(2) == 0)
			keptAmount += 1;
		CopyOnWriteArrayList<Item> keptItems = new CopyOnWriteArrayList<Item>();
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				if (getProtectPrice(item) >= getProtectPrice(lastItem)) {
					lastItem = item;
				}
			}
			keptItems.add(new Item(lastItem.getId(), 1));
			if (lastItem.getAmount() > 1)
				lastItem.setAmount(lastItem.getAmount() - 1);
			else
				containedItems.remove(lastItem);
			lastItem = new Item(1, 1);
		}
		inventory.reset();
		equipment.reset();
		for (Item item : keptItems) {
			if (item.getId() != 1)
				getInventory().addItem(item);
		}
		if (killer != null) {
			if (killer.isIronMan()) {
				killer.sm("Iron man accounts don't get anything for killing a player.");
				return;
			}
		}
		for (Item item : containedItems) {
			if (isIronMan()) {
				if (item.getName().toLowerCase().contains("obsidian")) {
					killer.sm("Ironman accounts who have obsidian gear will not reveal their item on the ground."); 
					sm("Ironman accounts who have obsidian gear will not reveal their item on the ground."); 	
				} else 
					World.addGroundItem(item, getLastWorldTile(), killer == null ? this
							: killer, this, false, 180, true, true);
			} else { 	
			World.addGroundItem(item, getLastWorldTile(), killer == null ? this
					: killer, this, false, 180, true, true);
			}
		}
	}


	public int getProtectPrice(Item item) {
		switch (item.getId()) {
		case 1286://Zuriel's Staff
			return 2147000088;
			case 11730://Saradomin Sword
			return 2147000089; 
			case 11718://Armadyl Helmet
			return 2147000090; 
			case 25034://Saradomin's Murmur
			return 2147000091;
			case 25031://Saradomin's Hiss
			return 2147000092; 
			case 13734://Spirit shield
			return 2147000093; 
			case 22494://Polypore Staff
			return 2147000094; 
			case 26781://Statius's Warhammer
			return 2147000095; 
			case 26780://Vesta's Longsword
			return 2147000096; 
			case 13920://Statius Full Helm
			return 2147000097; 
			case 13884://Statius Platebody
			return 2147000098; 
			case 13890://Statius Platelegs
			return 2147000099; 
			case 22484://Ganodermic Visor
			return 2147000100; 
			case 22490://Ganodermic Poncho
			return 2147000101; 
			case 22486://Ganodermic Leggings
			return 2147000102; 
			case 22347://Dominion Staff
			return 2147000114; 
			case 11720://Armadyl Chestplate
			return 2147000115; 
			case 11722://Armadyl Chainskirt
			return 2147000116; 
			case 24992://Hood of Subjugation
			return 2147000117; 
			case 24995://Garb of Subjugation
			return 2147000118; 
			case 24998://Gown of Subjugation
			return 2147000119; 
			case 25004://Boots of Subjugation
			return 2147000120; 
			case 25007://Gloves of Subjugation
			return 2147000121; 
			case 11724://Bandos Chestplate
			return 2147000122; 
			case 11726://Bandos Tassets
			return 2147000123; 
			case 22358://Goliath Gloves
			return 2147000124; 
			case 22362://Swift Gloves
			return 2147000125; 
			case 22363://Swift Gloves
			return 2147000125; 
			case 22364://Swift Gloves
			return 2147000125; 
			case 22365://Swift Gloves
			return 2147000125; 
			case 22366://Spellcaster Gloves
			return 2147000125; 
			case 22367://Spellcaster Gloves
			return 2147000125; 
			case 22368://Spellcaster Gloves
			return 2147000125; 
			case 22369://Spellcaster Gloves
			return 2147000126; 
			case 22359://Goliath Gloves
			return 2147000127; 
			case 22360://Goliath Gloves
			return 2147000127; 
			case 22361://Goliath Gloves
			return 2147000127; 
			case 18351://Chaotic Longsword
			return 2147000131; 
			case 18353://Chaotic Maul
			return 2147000132; 
			case 18349://Chaotic Rapier
			return 2147000133; 
			case 18357://Chaotic Crossbow
			return 2147000134; 
			case 18355://Chaotic Staff
			return 2147000135; 
			case 25993://Off-Hand Chaotic Longsword
			return 2147000136; 
			case 25991://Off-Hand Chaotic Rapier
			return 2147000137; 
			case 25995://Off-Hand Chaotic Crossbow
			return 2147000138; 
			case 13887://Vesta's Chainbody
			return 2147000139; 
			case 13893://Vesta's Chainskirt
			return 2147000140; 
			case 21793://Ragefire Boots
			return 2147000143; 
			case 11696://Bandos Godsword
			return 2147000144; 
			case 21790://Glaiven Boots
			return 2147000147; 
			case 11700://Zamorak Godsword
			return 2147000148; 
			case 26702://Saradomin Godsword
			return 2147000149; 
			case 22346://Dominion Sword
			return 2147000150; 
			case 22348://Dominion Crossbow
			return 2147000151; 
			case 25022://Bandos Helmet
			return 2147000152; 
			case 25028://Saradomin's Whisper
			return 2147000153; 
			case 26705://Dragon Claws
			return 2147000154; 
			case 26700://Armadyl Godsword
			return 2147000155; 
			case 13744://Spectral Spirit Shield
			return 2147000156; 
			case 28003://Flaming Whip
			return 2147000158; 
			case 28009://Firebrand Bow
			return 2147000159; 
			case 21787://Steadfast Boots
			return 2147000160; 
			case 26192://TzHarr Whip 1
			return 2147000161; 
			case 25472://Lightning Staff 1
			return 2147000162; 
			case 28020://Sheathed Blazing Flamberge
			return 2147000163; 
			case 25202://Deathtouched Darts
			return 2147000164; 
			case 20717://Zartye Bow
			return 2147000165; 
			case 28813://Drygore Longsword
			return 2147000173; 
			case 28821://Drygore Mace
			return 2147000174; 
			case 28812://Off-Hand Drygore Longsword
			return 2147000175; 
			case 28820://Off-Hand Drygore Rapier
			return 2147000176; 
			case 13738://Arcane Spirit Shield
			return 2147000181; 
			case 20137://Torva Full helm
			return 2147000183; 
			case 20141://Torva Platebody
			return 2147000184; 
			case 20145://Torva Platelegs
			return 2147000185; 
			case 20149://Pernix Cowl
			return 2147000186; 
			case 20153://Pernix Body
			return 2147000187; 
			case 20157://Pernix Chaps
			return 2147000188; 
			case 20161://Virtus Mask
			return 2147000189; 
			case 20165://Virtus Robetop
			return 2147000190; 
			case 20169://Virtus Robelegs
			return 2147000191; 
			case 13142://Elysian Spirit Shield
			return 2147000192; 
			case 13740://Divine Spirit Shield
			return 2147000193; 
			case 16425://Primal Maul
			return 2147000194; 
			case 21473://Vanguard Body
			return 2147000196; 
			case 21560://Vanguard Body
			return 2147000196; 
			case 21562://Vanguard Legs
			return 2147000197; 
			case 21474://Vanguard Legs
			return 2147000197; 
			case 28818://Drygore Rapier
			return 2147000198; 
			case 28822://Off-Hand Drygore Mace
			return 2147000199; 
			case 26346://Death lotus Hood
			return 2147000201; 
			case 26347://Death louts Chestplate
			return 2147000202; 
			case 26348://Death louts Chaps
			return 2147000203; 
			case 26182://Tokhaar Warlord Helmet
			return 2147000204; 
			case 26184://Tokhaar Warlord Platebody
			return 2147000205; 
			case 26186://Tokhaar Warlord Platelegs
			return 2147000206; 
			case 26188://Tokhaar Warlord Boots
			return 2147000207; 
			case 26190://Tokhaar Warlord Gloves
			return 2147000208; 
			case 28045://Draconic Claws
			return 2147000216; 
			case 25475://Lightning Staff 4
			return 2147000220; 
			case 29980://Ascension Crossbow
			return 2147000221; 
			case 25112://Royal Court Lance Rapier
			return 2147000222; 
			case 25664://Virtus Book
			return 2147000223; 
			case 27994://Lava Partyhat
			return 2147000226; 
			case 29977://Off-Hand Ascension Crossbow
			return 2147000232; 
			case 25654://Virtus Wand
			return 2147000234; 
			case 26787://Sword of Edicts
			return 2147000235; 
			case 27998://Scarlet Spirit Shield
			return 2147000236; 
			case 26031://Off-Hand Royal Court Lance Rapier
			return 2147000239; 
			case 26198://TzHarr Whip 7
			return 2147000241; 
			case 26130://Obsidian Ranger Helm
			return 2147000244; 
			case 26132://Obsidian Mage Helm
			return 2147000245; 
			case 26128://Obsidian Warrior Helm
			return 2147000246; 
			case 26126://Obsidian Boots 
			return 2147000247; 
			case 26124://Obsidian Gloves 
			return 2147000248; 
			case 28004://Blazing Flamberge
			return 2147000256; 
			case 26134://Obsidian Kiteshield
			return 2147000257; 
			case 29915://Ring Of The Gods
			return 2147000258; 
			case 29912://Tyrannical Ring
			return 2147000259; 
			case 6192://Obliteration (Staff)
			return 2147000261; 
			case 6193://Off-Hand Obliteration
			return 2147000262; 
			case 17175://Frostbite Dagger
			return 2147000263; 
			case 29914://Treasonous Ring
			return 2147000264; 
			case 26136://Obsidian Platelegs
			return 2147000265; 
			case 26140://Obsidian Platebody
			return 2147000266; 
			case 29855: // Elite Obsidian Gloves
			case 29856: // Elite Obsidian Boots
			return 2147000267; 
			case 29858: // Elite Obby helms
			case 29859:
			case 29854:
			return 2147000268; 
			case 29852: // Elite Obby legs
			return 2147000269; 
			case 29862: // Elite Obby top
			case 29900:
			case 29899:
			case 29898:
			case 29897:
			case 29896:
			case 29895:
			return 2147000270; 
		}
		return -1;
	}

	private Shop shop;
	public float donatedamount;
	public int donatedtotal;
	public Shop getShops() {
		return shop;
	}

	public void increaseKillCount(Player killed) {
		killed.deathCount++;
		if (killed.getKillStreak() > 1 && killed.getKillStreak() < 5)
			killed.sm("You have lost your killstreak!");
		if (killed.getKillStreak() >= 5)
			World.sendWorldMessage("<img=4><col=ff0000>[PvP Feed]: " +killed.getDisplayName() + " died and lost their "
					+ killed.getKillStreak() + " killstreak!", false);
		killed.setKillStreak(0);
		PkRank.checkRank(killed);
		if (lastkilled != null) {
		if (lastkilled.equals(killed)) {
			sm("You do not get any kill count for killing the same player twice.");
			return;
		}
		}
		if (killed == this)
			return;
		lastkilled = killed;
		if (getSession().getIP().equals(killed.getSession().getIP())) {
			  sm("You do not get any kill count for killing yourself.");
			  return;
		  }	
		getCurrencyPouch().setEradicatedSeals(getCurrencyPouch().getEradicatedSeals() + 10);
		setSpellPower(getSpellPower()+2);
		killCount++;
		killStreak++;
		if (this.getKillStreak() >= 5)
			World.sendWorldMessage("<img=4><col=ff0000>[PvP Feed]: " +this.getDisplayName() + " is on a "
					+ this.getKillStreak() + " killstreak!", false);
		sm("<col=ff0000>You have killed " + killed.getDisplayName()
						+ ", you have now " + killCount + " kills.");
		PkRank.checkRank(this);
	}
	
	private Player lastkilled;

	public void sendRandomJail(Player p) {
		p.resetWalkSteps();
		switch (Utils.getRandom(6)) {
		case 0:
			p.setNextWorldTile(new WorldTile(2669, 10387, 0));
			break;
		case 1:
			p.setNextWorldTile(new WorldTile(2669, 10383, 0));
			break;
		case 2:
			p.setNextWorldTile(new WorldTile(2669, 10379, 0));
			break;
		case 3:
			p.setNextWorldTile(new WorldTile(2673, 10379, 0));
			break;
		case 4:
			p.setNextWorldTile(new WorldTile(2673, 10385, 0));
			break;
		case 5:
			p.setNextWorldTile(new WorldTile(2677, 10387, 0));
			break;
		case 6:
			p.setNextWorldTile(new WorldTile(2677, 10383, 0));
			break;
		}
	}

	@Override
	public int getSize() {
		return appearence.getSize();
	}

	public boolean isCanPvp() {
		return canPvp;
	}

	public void setCanPvp(boolean canPvp) {
		this.canPvp = canPvp;
		appearence.generateAppearenceData();
		getPackets().sendPlayerOption(canPvp ? "Attack" : "null", 1, true);
		getPackets().sendPlayerUnderNPCPriority(canPvp);
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public long getLockDelay() {
		return lockDelay;
	}

	public boolean isLocked() {
		return lockDelay >= Utils.currentTimeMillis();
	}

	public void lock() {
		lockDelay = Long.MAX_VALUE;
	}

	public void lock(long time) {
		lockDelay = Utils.currentTimeMillis() + (time * 600);
	}

	public void unlock() {
		lockDelay = 0;
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay) {
		useStairs(emoteId, dest, useDelay, totalDelay, null);
	}

	public void useStairs(int emoteId, final WorldTile dest, int useDelay,
			int totalDelay, final String message) {
		stopAll();
		lock(totalDelay);
		if (emoteId != -1)
			setNextAnimation(new Animation(emoteId));
		if (useDelay == 0)
			setNextWorldTile(dest);
		else {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (isDead())
						return;
					setNextWorldTile(dest);
					if (message != null)
						getPackets().sendGameMessage(message);
				}
			}, useDelay - 1);
		}
	}

	public Toolbelt getToolbelt() {
		return toolbelt;
	}	
	
	public Bank getBank() {
		if (getSecondBank() != null)
		if (getSecondBank().opened)
			return secondbank;
		return bank;
	}
	public Bank getBankT() {
		return bank;
	}	
	
	public Bank getSecondBank() {
		return secondbank;
	}

	public ControlerManager getControlerManager() {
		return controlerManager;
	}
	public void senddonationitem(Player player, int donateditem, int amount) {
			getBank().addItem(donateditem, amount, true);
			}	
	
	public String checkamountdonated(Player player, String username){
		try{
			username = username.replaceAll(" ","_");
		    String secret = "7dcf7ce97d2451742ff8b6fc72aea8fa"; //YOUR SECRET KEY!
			URL url = new URL("http://app.gpay.io/api/runescape/"+username+"/"+secret);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
				if(results.toLowerCase().contains("!error:")){
					
				}else{
					return results;
				}
			}catch(IOException e){}
		return "0";
		}			
	
	public String fontColor() {
		return getInterfaceManager().hasRezizableScreen() ? "<col=0FFFFF>" : "<col=084FA1>";
	}
	
	public boolean setRPayPin() {
		try {
			String username = getUsername();
			if (username == null)
				return false;
			if (isIronMan()) {
				sm("Ironman accounts can't donate.");
				return false;
			}
			if (getSecurityPin() == 0) {
				sm("You must set a pin! In order to set a pin, click at any bank and you'll be prompted to do so.");
				return false;
			}
			if (getSecurityPin() == getDonationPin()) {
				return true;
			}
			username = username.replaceAll(" ","_");
		    String secret = "7dcf7ce97d2451742ff8b6fc72aea8fa"; //YOUR SECRET KEY!
			URL url = new URL("http://app.gpay.io/api/runescape/"+username+"/"+secret);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String results = reader.readLine();
		    setDonationPin(getSecurityPin());
            if (results.toLowerCase().contains("ERROR")) {
                 Logger.log(this, "[RSPS-PAY]"+results);
            } else {
            	 Logger.log(this, "[RSPS-PAY]"+results);
                 sm("Please note: Your pin is the same pin as your security pin. If you need to change your pin, feel free to do ;;changepin.");
            }
            return true;
		} catch (IOException e) {
			return false;
			}	
	}
	
	public void sendtotaldonationfeed(Player player, boolean hidden, int price) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		//float amountdonated = Float.parseFloat(checkamountdonated(this, username));
		int amountdonated = donatedtotal + price;
		if (!hidden) {
			for (Player players: World.getPlayers()) {
				players.sm("<img=5>[Donation] "+players.fontColor()+player.getDisplayName()+" </col> has donated a total of "+players.fontColor()+"" + currencyFormatter.format(amountdonated) + "!");
			}
		}
		player.donatedtotal += price;
	}
			
	public void senddonationfeed(Player player, String message, int price) {
				for (Player players: World.getPlayers()) {
					players.sm("<img=5>[Donation] "+players.fontColor()+ player.getDisplayName() + " </col>bought "+players.fontColor()+""+message+
							"</col>");
				}
				Logger.printDonateLog(player.getUsername(), message,price);
			}
	
	public boolean check10BRequirements() {
			     if (getSkills().getXp(Skills.ATTACK) < 400000000
				|| getSkills().getXp(Skills.STRENGTH) < 400000000
				|| getSkills().getXp(Skills.DEFENCE) < 400000000
				|| getSkills().getXp(Skills.CONSTRUCTION) < 400000000
				|| getSkills().getXp(Skills.HITPOINTS) < 400000000
				|| getSkills().getXp(Skills.RANGE) < 400000000
				|| getSkills().getXp(Skills.MAGIC) < 400000000
				|| getSkills().getXp(Skills.RUNECRAFTING) < 400000000
				|| getSkills().getXp(Skills.FISHING) < 400000000
				|| getSkills().getXp(Skills.AGILITY) < 400000000
				|| getSkills().getXp(Skills.COOKING) < 400000000
				|| getSkills().getXp(Skills.PRAYER) < 400000000
				|| getSkills().getXp(Skills.THIEVING) < 400000000
				|| getSkills().getXp(Skills.DUNGEONEERING) < 400000000
				|| getSkills().getXp(Skills.MINING) < 400000000
				|| getSkills().getXp(Skills.SMITHING) < 400000000
				|| getSkills().getXp(Skills.SUMMONING) < 400000000
				|| getSkills().getXp(Skills.FARMING) < 400000000
				|| getSkills().getXp(Skills.HUNTER) < 400000000
				|| getSkills().getXp(Skills.SLAYER) < 400000000
				|| getSkills().getXp(Skills.CRAFTING) < 400000000
				|| getSkills().getXp(Skills.WOODCUTTING) < 400000000
				|| getSkills().getXp(Skills.FIREMAKING) < 400000000
				|| getSkills().getXp(Skills.FLETCHING) < 400000000
				|| getSkills().getXp(Skills.HERBLORE) < 400000000) 
						return false;
				else
						return true;
	}
	public boolean check5BRequirements() {
	     if (getSkills().getXp(Skills.ATTACK) < 200000000
		|| getSkills().getXp(Skills.STRENGTH) < 200000000
		|| getSkills().getXp(Skills.DEFENCE) < 200000000
		|| getSkills().getXp(Skills.CONSTRUCTION) < 200000000
		|| getSkills().getXp(Skills.HITPOINTS) < 200000000
		|| getSkills().getXp(Skills.RANGE) < 200000000
		|| getSkills().getXp(Skills.MAGIC) < 200000000
		|| getSkills().getXp(Skills.RUNECRAFTING) < 200000000
		|| getSkills().getXp(Skills.FISHING) < 200000000
		|| getSkills().getXp(Skills.AGILITY) < 200000000
		|| getSkills().getXp(Skills.COOKING) < 200000000
		|| getSkills().getXp(Skills.PRAYER) < 200000000
		|| getSkills().getXp(Skills.THIEVING) < 200000000
		|| getSkills().getXp(Skills.DUNGEONEERING) < 200000000
		|| getSkills().getXp(Skills.MINING) < 200000000
		|| getSkills().getXp(Skills.SMITHING) < 200000000
		|| getSkills().getXp(Skills.SUMMONING) < 200000000
		|| getSkills().getXp(Skills.FARMING) < 200000000
		|| getSkills().getXp(Skills.HUNTER) < 200000000
		|| getSkills().getXp(Skills.SLAYER) < 200000000
		|| getSkills().getXp(Skills.CRAFTING) < 200000000
		|| getSkills().getXp(Skills.WOODCUTTING) < 200000000
		|| getSkills().getXp(Skills.FIREMAKING) < 200000000
		|| getSkills().getXp(Skills.FLETCHING) < 200000000
		|| getSkills().getXp(Skills.HERBLORE) < 200000000) 
				return false;
		else
				return true;
}	
	public boolean checkCompletionistCapeRequirements() {
		if (!isCompletedFightKiln() 
			|| !isKilledQueenBlackDragon()
			|| !getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)
			|| !isCompletedFightCaves()
			) 
			return false;
			if ( getSkills().getLevelForXp(Skills.ATTACK) >= 99
				&&  getSkills().getLevelForXp(Skills.STRENGTH) >= 99
				&&  getSkills().getLevelForXp(Skills.DEFENCE) >= 99
				&&  getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99
				&&  getSkills().getLevelForXp(Skills.HITPOINTS) >= 99
				&&  getSkills().getLevelForXp(Skills.RANGE) >= 99
				&&  getSkills().getLevelForXp(Skills.MAGIC) >= 99
				&&  getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99
				&&  getSkills().getLevelForXp(Skills.FISHING) >= 99
				&&  getSkills().getLevelForXp(Skills.AGILITY) >= 99
				&&  getSkills().getLevelForXp(Skills.COOKING) >= 99
				&&  getSkills().getLevelForXp(Skills.PRAYER) >= 99
				&&  getSkills().getLevelForXp(Skills.THIEVING) >= 99
				&&  getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 120
				&&  getSkills().getLevelForXp(Skills.MINING) >= 99
				&&  getSkills().getLevelForXp(Skills.SMITHING) >= 99
				&&  getSkills().getLevelForXp(Skills.SUMMONING) >= 99
				&&  getSkills().getLevelForXp(Skills.FARMING) >= 99
				&&  getSkills().getLevelForXp(Skills.HUNTER) >= 99
				&&  getSkills().getLevelForXp(Skills.SLAYER) >= 99
				&&  getSkills().getLevelForXp(Skills.CRAFTING) >= 99
				&&  getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99
				&&  getSkills().getLevelForXp(Skills.FIREMAKING) >= 99
				&&  getSkills().getLevelForXp(Skills.FLETCHING) >= 99
				&&  getSkills().getLevelForXp(Skills.HERBLORE) >= 99)
			return true;
		return false;
	}
			
	public boolean checkAmuletofCompletion() {
		if (getBossSlayerCount(false) < 50 
				|| getSlayerCount(false) < 50
				|| getDummyDamage() < 10000
				|| getDropbeam() < 6
				|| getAmountThieved(false) < 50000000
				|| !check10BRequirements()
				|| !isKilledDragithNurn()
				|| !isCompletedFightCaves()
				|| !getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)
				|| !isKilledQueenBlackDragon()
				|| !getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_I)
				|| !getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_II)
				|| !getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_III))
			return false;
		return true;
	}
	
			
	public boolean rspsdata(Player player, String username, boolean hidden) {
	try{
		username = username.replaceAll(" ","_");
	    String secret = "7dcf7ce97d2451742ff8b6fc72aea8fa"; //YOUR SECRET KEY!
		URL url = new URL("http://app.gpay.io/api/runescape/"+username+"/"+secret);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String results = reader.readLine();
			if(results.toLowerCase().contains("!error:")) {
				//Logger.log(this, "[RSPS-PAY]"+results);
			} else {
				String boughtitems = "";
				int price = 0;
				int rdon = 0, edon = 0, mbox = 0, qfc = 0, bfs = 0, drs = 0, dls = 0, dms = 0, ts = 0, ps = 0, vs = 0, dss = 0, ess = 0, ass = 0,
						sss = 0, sdr = 0, er = 0, dtd = 0, hxl = 0, row = 0, dr = 0, tbox = 0, cbox = 0;
				boolean nothin = false;
				String[] ary = results.split(",");
					 for(int i = 0; i < ary.length; i++) {
						switch(ary[i]) {
							case "0":
								return false;
							case "32133":
								senddonationitem(player, 6832, 1);
								price += 10;
								++rdon;
							break;
							case "32134": 
								senddonationitem(player, 6830, 1);
								price += 20;
								++edon;					
							break;
							case "32138": 
								senddonationitem(player, 6199, 1);
								++mbox;			
								price += 5;
							break;
							case "32139": 
								senddonationitem(player, 24575, 1);
								senddonationitem(player, 26027, 1);
								++qfc;					
								price += 15;
							break;
							case "32140": 
								senddonationitem(player, 28004, 1);
								senddonationitem(player, 28020, 1);
								++bfs;		
								price += 39;
							break;
							case "32141": 
								senddonationitem(player, 28818, 1);
								senddonationitem(player, 28820, 1);
								++drs;					
								price += 40;
							break;
							case "32142": 
								senddonationitem(player, 28813, 1);
								senddonationitem(player, 28812, 1);
								++dls;				
								price += 40;
							break;
							case "32143": 
								senddonationitem(player, 28821, 1);
								senddonationitem(player, 28822, 1);
								++dms;					
								price += 40;
							break;
							case "32144": 
								senddonationitem(player, 20135, 1);
								senddonationitem(player, 20139, 1);
								senddonationitem(player, 20144, 1);
								++ts;				
								price += 35;
							break;
							case "32145": 
								senddonationitem(player, 20147, 1);
								senddonationitem(player, 20151, 1);
								senddonationitem(player, 20155, 1);
								++ps;					
								price += 35;
							break;
							case "32146": 
								senddonationitem(player, 20159, 1);							
								senddonationitem(player, 20163, 1);
								senddonationitem(player, 20167, 1);
								++vs;				
								price += 35;
							break;
							case "32147": 
								senddonationitem(player, 13740, 1);
								price += 25;
								++dss;							
							break;
							case "32148": 
								senddonationitem(player, 13742, 1);
								++ess;							
								price += 25;
							break;
							case "32149": 
								senddonationitem(player, 13738, 1);
								price += 20;
								++ass;								
							break;
							case "32150": 
								senddonationitem(player, 13744, 1);
								++sss;;				
								price += 20;
							break;
							case "32135":
								senddonationitem(player, 6829, 1);
								++sdr;						
								price += 40;
							break;
							case "32136": 
								senddonationitem(player, 6828, 1);
								++er;		
								price += 100;
							break;
							case "32151": 
								senddonationitem(player, 25202, 10);
								dtd += 10;				
								price += 20;
							break;
							case "32157": 
								senddonationitem(player, 23716, 1);
								++hxl;
								price += 3;
							break;
							case "32152": 
								senddonationitem(player, 2572, 1);
								++row;		
								price += 10;
							break;			
							case "32137":
								player.setDicer(true);
								player.getAppearence().setTitle(4324324);
								senddonationitem(player, 15098, 1);
								++dr;
								price += 100;
							break;
							case "32158":
								senddonationitem(player, 23716, 10);
								hxl += 10;
								price += 25;
							break;
							case "32153":
						  		senddonationitem(player, 27744, 1);
								tbox += 1;
								price += 10;
							break;
							case "32155":
								senddonationitem(player, 27745, 1);
								cbox += 1;
								price += 10;
							break;
							case "32154":
						  		senddonationitem(player, 27744, 10);
								tbox += 10;
								price += 60;
							break;
							case "32156":
								senddonationitem(player, 27745, 10);
								cbox += 10;
								price += 60;
							break;
						}
					}
					if (!nothin) {
						if (rdon != 0)
							boughtitems += rdon + "x Regular Donator, ";
						if (edon != 0)
							boughtitems += edon + "x Extreme Donator, ";
						if (mbox != 0)
							boughtitems += mbox + "x Mystery Box, ";
						if (qfc != 0)
							boughtitems += qfc + "x Quickfire Crossbow, ";
						if (bfs != 0)
							boughtitems += bfs + "x Blazing Flamberge, ";
						if (drs != 0)
							boughtitems += drs + "x Drygore Rapiers, ";
						if (dls != 0)
							boughtitems += dls + "x Drygore Longswords, ";
						if (dms != 0)
							boughtitems += dms + "x Drygore Maces, ";
						if (ts != 0)
							boughtitems += ts + "x Torva, ";
						if (ps != 0)
							boughtitems += ps + "x Pernix, ";
						if (vs != 0)
							boughtitems += vs + "x Virtus, ";
						if (dss != 0)
							boughtitems += dss + "x Divine SS, ";
						if (ess != 0)
							boughtitems += ess + "x Elysian SS, ";
						if (ass != 0)
							boughtitems += ass + "x Arcane SS, ";
						if (sss != 0)
							boughtitems += sss + "x Spectral SS, ";
						if (sdr != 0)
							boughtitems += sdr + "x Super Donator, ";
						if (er != 0)
							boughtitems += er + "x Eradicator Rank, ";
						if (dtd != 0)
							boughtitems += dtd + "x Deathtouched Dart, ";
						if (hxl != 0)
							boughtitems += hxl + "x Huge XP Lamp, ";
						if (row != 0)	
							boughtitems += row + "x Ring of Wealth, ";
						if (dr != 0)
							boughtitems += dr + "x Dicer Rank, ";
						if (tbox != 0)
							boughtitems += tbox + "x Trimmed Box, ";
						if (cbox != 0)
							boughtitems += cbox + "x Cosmetic Box, ";
						String newboughtitems = "";
						newboughtitems = boughtitems.substring(0,boughtitems.length() - 2) + "!";
						if (!hidden) {
						senddonationfeed(player, newboughtitems, price);
						}
						sendtotaldonationfeed(player, hidden, price);
						sm("Your purchase has been placed in your bank.");
						return true;
					}
					return true;
			}
		}catch(IOException e){}
	return false;
	}
	
	public void switchMouseButtons() {
		mouseButtons = !mouseButtons;
		refreshMouseButtons();
	}

	public void switchAllowChatEffects() {
		allowChatEffects = !allowChatEffects;
		refreshAllowChatEffects();
	}

	public void refreshAllowChatEffects() {
		getPackets().sendConfig(171, allowChatEffects ? 0 : 1);
	}

	public void refreshMouseButtons() {
		getPackets().sendConfig(170, mouseButtons ? 0 : 1);
	}
	
	private transient ClansManager clanManager, guestClanManager;
	public ClansManager getClanManager() {
		return clanManager;
	}

	public void setClanManager(ClansManager clanManager) {
		this.clanManager = clanManager;
	}	

	private String clanName;

	private int clanChatSetup;
	public int getClanChatSetup() {
		return clanChatSetup;
	}

	public void setClanChatSetup(int clanChatSetup) {
		this.clanChatSetup = clanChatSetup;
	}

	private int guestChatSetup;

	public void kickPlayerFromClanChannel(String name) {
		if (clanManager == null)
			return;
		clanManager.kickPlayerFromChat(this, name);
	}

	public void sendClanChannelMessage(ChatMessage message) {
		if (clanManager == null)
			return;
		clanManager.sendMessage(this, message);
	}

	public void sendClanChannelQuickMessage(QuickChatMessage message) {
		if (clanManager == null)
			return;
		clanManager.sendQuickMessage(this, message);
	}

	public void sendGuestClanChannelMessage(ChatMessage message) {
		if (guestClanManager == null)
			return;
		guestClanManager.sendMessage(this, message);
	}

	public void sendGuestClanChannelQuickMessage(QuickChatMessage message) {
		if (guestClanManager == null)
			return;
		guestClanManager.sendQuickMessage(this, message);
	}

	private boolean connectedClanChannel;	
	
	public boolean emptyWarning = false;
	
	public void refreshPrivateChatSetup() {
		getPackets().sendConfig(287, privateChatSetup);
	}

	public void refreshOtherChatsSetup() {
		int value = friendChatSetup << 6;
		getPackets().sendConfig(1438, value);
		getPackets().sendConfigByFile(3612, clanChatSetup);
		getPackets().sendConfigByFile(9191, guestChatSetup);
	}

	public void setPrivateChatSetup(int privateChatSetup) {
		this.privateChatSetup = privateChatSetup;
	}

	public void setFriendChatSetup(int friendChatSetup) {
		this.friendChatSetup = friendChatSetup;
	}

	public int getPrivateChatSetup() {
		return privateChatSetup;
	}

	public boolean isForceNextMapLoadRefresh() {
		return forceNextMapLoadRefresh;
	}

	public void setForceNextMapLoadRefresh(boolean forceNextMapLoadRefresh) {
		this.forceNextMapLoadRefresh = forceNextMapLoadRefresh;
	}

	public FriendsIgnores getFriendsIgnores() {
		return friendsIgnores;
	}

	public void sendMessage(String message) {
		getPackets().sendGameMessage(message);
	}

	public boolean isUntillLogout() {
		return untillLogout;
	}

	public void setUntillLogout(boolean untillLogout) {
		this.untillLogout = untillLogout;
	}	
	
	/*
	 * do not use this, only used by pm
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setLeaderName(Player leaderName) {
		this.leaderName = leaderName;
	}	

	public void addPotDelay(long time) {
		potDelay = time + Utils.currentTimeMillis();
	}

	public long getPotDelay() {
		return potDelay;
	}

	public void addFoodDelay(long time) {
		foodDelay = time + Utils.currentTimeMillis();
	}

	public long getFoodDelay() {
		return foodDelay;
	}

	public long getBoneDelay() {
		return boneDelay;
	}

	public void addBoneDelay(long time) {
		boneDelay = time + Utils.currentTimeMillis();
	}

	public void addPoisonImmune(long time) {
		poisonImmune = time + Utils.currentTimeMillis();
		getPoison().reset();
	}

	public long getPoisonImmune() {
		return poisonImmune;
	}

	public void addFireImmune(long time) {
		fireImmune = time + Utils.currentTimeMillis();
	}

	public long getFireImmune() {
		return fireImmune;
	}

	@Override
	public void heal(int ammount, int extra) {
		super.heal(ammount, extra);
		refreshHitPoints();
	}

	public MusicsManager getMusicsManager() {
		return musicsManager;
	}

	public HintIconsManager getHintIconsManager() {
		return hintIconsManager;
	}

	public boolean isCastVeng() {
		return castedVeng;
	}

	public void setCastVeng(boolean castVeng) {
		this.castedVeng = castVeng;
	}

	public int getKillCount() {
		return killCount;
	}

	public int getBarrowsKillCount() {
		return barrowsKillCount;
	}

	public int setBarrowsKillCount(int barrowsKillCount) {
		return this.barrowsKillCount = barrowsKillCount;
	}

	public int setKillCount(int killCount) {
		return this.killCount = killCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public int setDeathCount(int deathCount) {
		return this.deathCount = deathCount;
	}

	public void setCloseInterfacesEvent(Runnable closeInterfacesEvent) {
		this.closeInterfacesEvent = closeInterfacesEvent;
	}

	public long getMuted() {
		return muted;
	}

	public void setMuted(long muted) {
		this.muted = muted;
	}
	
	public long getVoted() {
		return hevoted;
	}

	public void setVoted(long hevoted) {
		this.hevoted = hevoted;
	}

	public void out(String text, int delay) {

	}
	public ItemOffer[] getGeOffers() {
		return geOffers;
	}

	public void setGeOffers(ItemOffer[] geOffers) {
		this.geOffers = geOffers;
	}

	public int getGESlot() {
		return GESlot;
	}

	public void setGESlot(int gESlot) {
		GESlot = gESlot;
	}

	private ItemOffer[] geOffers;

	/**
	 * The player's last used grand exchange slot.
	 */
	private int GESlot;

	/**
	 * The player's last offertype used.
	 */
	public OfferType offerType;

	public int box;
	
	public long getJailed() {
		return jailed;
	}

	public void setJailed(long jailed) {
		this.jailed = jailed;
	}

	public boolean isPermBanned() {
		return permBanned;
	}

	public void setPermBanned(boolean permBanned) {
		this.permBanned = permBanned;
	}

	public long getBanned() {
		return banned;
	}

	public void setBanned(long banned) {
		this.banned = banned;
	}

	public ChargesManager getCharges() {
		return charges;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean[] getKilledBarrowBrothers() {
		return killedBarrowBrothers;
	}

	public void setHiddenBrother(int hiddenBrother) {
		this.hiddenBrother = hiddenBrother;
	}

	public int getHiddenBrother() {
		return hiddenBrother;
	}

	public void resetBarrows() {
		hiddenBrother = -1;
		killedBarrowBrothers = new boolean[7]; // includes new bro for future
												// use
		barrowsKillCount = 0;
	}

	public boolean isDonator() {
		return donator;
	}

	public boolean isExtremeDonator() {
		return extremeDonator || extremeDonatorTill > Utils.currentTimeMillis();
	}

	public boolean isExtremePermDonator() {
		return extremeDonator;
	}

	public void setExtremeDonator(boolean extremeDonator) {
		this.extremeDonator = extremeDonator;
	}

	public boolean isOwner() {
		return isOwner;
	}
	
	private boolean isHeadMod;
	
	public boolean isHeadMod() {
		return isHeadMod;
	}

	public void setHeadMod(boolean isHeadMod) {
		this.isHeadMod = isHeadMod;
	}
	
	public String MACAdress;
	public String getMACAdress() {
		return MACAdress;
	}

	public void setMacAdress(String macAdress) {
                if(macAdress != null) {
                this.MACAdress = macAdress;
                }
        }
 
	private boolean isExecutive;
	
	public boolean isExecutive() {
		return isExecutive;
	}

	public void setExecutive(boolean isExecutive) {
		this.isExecutive = isExecutive;
	}
	
	private boolean isHeadExecutive;
	
	public boolean isHeadExecutive() {
		return isHeadExecutive;
	}

	public void setHeadExecutive(boolean isHeadExecutive) {
		this.isHeadExecutive = isHeadExecutive;
	}	
	
	private boolean isForumAdmin;
	
	public boolean isForumAdmin() {
		return isForumAdmin;
	}

	public void setForumAdmin(boolean isForumAdmin) {
		this.isForumAdmin = isForumAdmin;
	}		
	
	private boolean isForumMod;
	
	public boolean isForumMod() {
		return isForumMod;
	}

	public void setForumMod(boolean isForumMod) {
		this.isForumMod = isForumMod;
	}	
	
	private boolean isDicer;
	
	public boolean isDicer() {
		return isDicer;
	}

	public void setDicer(boolean isDicer) {
		this.isDicer = isDicer;
	}
	private boolean isEradicator;
	
	private boolean isHero;
	
	public boolean isEradicator() {
		return isEradicator;
	}

	public void setEradicator(boolean isEradicator) {
		this.isEradicator = isEradicator;
	}

	public boolean isHero() {
		return isHero;
	}

	public void setHero(boolean isEradicator) {
		this.isHero = isEradicator;
	}	
	
	
	private boolean isSavior;
	private int hacker;
	public boolean invtoken;
	
	public boolean isLentDonator() {
		return isLentDonator;
	}
	public void setLentDonator(boolean isLentDonator) {
		this.isLentDonator = isLentDonator;
	}
	public boolean isLentExtreme() {
		return isLentExtreme;
	}
	public void setLentExtreme(boolean isLentExtreme) {
		this.isLentExtreme = isLentExtreme;
	}
	public boolean isLentSavior() {
		return isLentSavior;
	}
	public void setLentSavior(boolean isLentSavior) {
		this.isLentSavior = isLentSavior;
	}
	public boolean isLentEradicator() {
		return isLentEradicator;
	}
	public void setLentEradicator(boolean isLentEradicator) {
		this.isLentEradicator = isLentEradicator;
	}

	private boolean isLentDonator;
	private boolean isLentExtreme;
	private boolean isLentSavior;
	private boolean isLentEradicator;
	private int wrong;
	private int fixfiveb;
	private int bossslayerPoints;
	private boolean completedFullSlayerHelmet;
	private boolean killedDragithNurn;
	
	public boolean isSavior() {
		return isSavior;
	}

	public void setSavior(boolean isSavior) {
		this.isSavior = isSavior;
	}		
	
	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public int getGuestChatSetup() {
		return guestChatSetup;
	}

	public void setGuestChatSetup(int guestChatSetup) {
		this.guestChatSetup = guestChatSetup;
	}

	public ClansManager getGuestClanManager() {
		return guestClanManager;
	}

	public void setGuestClanManager(ClansManager guestClanManager) {
		this.guestClanManager = guestClanManager;
	}

	public String getClanName() {
		return clanName;
	}

	public void setClanName(String clanName) {
		this.clanName = clanName;
	}

	public boolean isConnectedClanChannel() {
		return connectedClanChannel;
	}

	public void setConnectedClanChannel(boolean connectedClanChannel) {
		this.connectedClanChannel = connectedClanChannel;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void makeDonator(int months) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setMonth(date.getMonth() + months);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public void makeDonatorDays(int days) {
		if (donatorTill < Utils.currentTimeMillis())
			donatorTill = Utils.currentTimeMillis();
		Date date = new Date(donatorTill);
		date.setDate(date.getDate() + days);
		donatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public void makeExtremeDonatorDays(int days) {
		if (extremeDonatorTill < Utils.currentTimeMillis())
			extremeDonatorTill = Utils.currentTimeMillis();
		Date date = new Date(extremeDonatorTill);
		date.setDate(date.getDate() + days);
		extremeDonatorTill = date.getTime();
	}

	@SuppressWarnings("deprecation")
	public String getDonatorTill() {
		return (donator ? "never" : new Date(donatorTill).toGMTString()) + ".";
	}

	@SuppressWarnings("deprecation")
	public String getExtremeDonatorTill() {
		return (extremeDonator ? "never" : new Date(extremeDonatorTill)
				.toGMTString()) + ".";
	}

	public void setDonator(boolean donator) {
		this.donator = donator;
	}

	public String getRecovQuestion() {
		return recovQuestion;
	}

	public void setRecovQuestion(String recovQuestion) {
		this.recovQuestion = recovQuestion;
	}

	public String getRecovAnswer() {
		return recovAnswer;
	}

	public void setRecovAnswer(String recovAnswer) {
		this.recovAnswer = recovAnswer;
	}

	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

	public void pmLog(Player player, Player getter, String message) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/PMs.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    //+ " "
                   // + Calendar.getInstance().getTimeZone().getDisplayName()
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + " messaged "+Utils.formatPlayerNameForDisplay(getter.getUsername()) +": "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public static void chatLog(Player player, String message) {
		if (message.length() > 13) {
		if (message.substring(0, 13).contains("changepass"))
			message = "Password changed command";
		}
		if (message.length() > 14) {
		if (message.substring(0, 14).contains("dropboxinfo"))
			message = "**********";
		}
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(
                    "data/logs/Chats.txt", true));
            bf.write("[" + DateFormat.getDateTimeInstance().format(new Date())
                    //+ " "
                   // + Calendar.getInstance().getTimeZone().getDisplayName()
                    + "] " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ": "+message);
            bf.newLine();
            bf.flush();
            bf.close();
        } catch (IOException ignored) {
        }
    }
	
	public int[] getPouches() {
		return pouches;
	}

	public EmotesManager getEmotesManager() {
		return emotesManager;
	}

	public String getLastIP() {
		return lastIP;
	}

	public String getLastHostname() {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(getLastIP());
			String hostname = addr.getHostName();
			return hostname;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PriceCheckManager getPriceCheckManager() {
		return priceCheckManager;
	}

	public void setPestPoints(int pestPoints) {
		this.pestPoints = pestPoints;
	}

	public int getPestPoints() {
		return pestPoints;
	}

	public boolean isUpdateMovementType() {
		return updateMovementType;
	}

	public long getLastPublicMessage() {
		return lastPublicMessage;
	}

	public void setLastPublicMessage(long lastPublicMessage) {
		this.lastPublicMessage = lastPublicMessage;
	}

	public CutscenesManager getCutscenesManager() {
		return cutscenesManager;
	}

	public void kickPlayerFromFriendsChannel(String name) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.kickPlayerFromChat(this, name);
	}

	public void sendFriendsChannelMessage(String message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendMessage(this, message);
	}

	public void sendFriendsChannelQuickMessage(QuickChatMessage message) {
		if (currentFriendChat == null)
			return;
		currentFriendChat.sendQuickMessage(this, message);
	}

	public void sendPublicChatMessage(PublicChatMessage message) {
		boolean tag = false;
		if (message.getMessage(false).contains("@") &&  message.getMessage(false).contains(" ")) {
			if (message.getMessage(false).indexOf("@") == 0) {
			String name = message.getMessage(false).substring(message.getMessage(false).indexOf("@")+1, message.getMessage(false).indexOf(" "));
			for (Player player : World.getPlayers()) {
				if (player == null)
					continue;
				if (player.getDisplayName().toLowerCase().replaceAll(" ", "_").equals(name.toLowerCase())) {
					if (!player.getFriendsIgnores().getIgnores().contains(Utils.formatPlayerNameForProtocol(this.getUsername()))) {
					player.sm("<col=9A11D9>["+ (player.getMessageIcon() != 0 ? 
							("<img="+this.getMessageIcon()+">") : ("")) + this.getDisplayName() + " whispered]: " + Utils.fixChatMessage(message.getMessage(false).substring(
								message.getMessage(false).indexOf(" ")).replaceAll("<", "")));
					this.sm("<col=9A11D9>[You whispered to "+ (player.getMessageIcon() != 0 ? 
							("<img="+player.getMessageIcon()+">") : ("")) + player.getDisplayName()+ "]: "  + Utils.fixChatMessage(message.getMessage(false).substring(
							message.getMessage(false).indexOf(" ")).replaceAll("<img", "")));
					} else
						this.sm("This person has you on their ignore list.");
					tag = true;
					
				}
			}
		}
		}
		if (tag)
			return;
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playersIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player p = World.getPlayers().get(playerIndex);
				if (p == null
						|| !p.hasStarted()
						|| p.hasFinished()
						|| p.getLocalPlayerUpdate().getLocalPlayers()[getIndex()] == null)
					continue;
				p.getPackets().sendPublicMessage(this, message);
			}
		}
	}

	public int[] getCompletionistCapeCustomized() {
		return completionistCapeCustomized;
	}

	public void setCompletionistCapeCustomized(int[] skillcapeCustomized) {
		this.completionistCapeCustomized = skillcapeCustomized;
	}

	public int[] getFiveBillCapeCustomized() {
		return fivebillCustomized;
	}

	public void setFiveBillCapeCustomized(int[] fivebillCustomized) {
		this.fivebillCustomized = fivebillCustomized;
	}	
	
	public int[] getMaxedCapeCustomized() {
		return maxedCapeCustomized;
	}
	
	public void setMaxedCapeCustomized(int[] maxedCapeCustomized) {
		this.maxedCapeCustomized = maxedCapeCustomized;
	}

	public void setSkullId(int skullId) {
		this.skullId = skullId;
	}

	public int getSkullId() {
		return skullId;
	}

	public boolean isFilterGame() {
		return filterGame;
	}

	public void setFilterGame(boolean filterGame) {
		this.filterGame = filterGame;
	}

	public void addLogicPacketToQueue(LogicPacket packet) {
		for (LogicPacket p : logicPackets) {
			if (p.getId() == packet.getId()) {
				logicPackets.remove(p);
				break;
			}
		}
		logicPackets.add(packet);
	}

	public DominionTower getDominionTower() {
		return dominionTower;
	}

	public void setPrayerRenewalDelay(int delay) {
		this.prayerRenewalDelay = delay;
	}

	public int getOverloadDelay() {
		return overloadDelay;
	}

	public void setOverloadDelay(int overloadDelay) {
		this.overloadDelay = overloadDelay;
	}
	public int getEradicatorDelay() {
		return eradicatorDelay;
	}
	public void setEradicatorDelay(int eradicatorDelay) {
		this.eradicatorDelay = eradicatorDelay;
	}	
	public Trade getTrade() {
		return trade;
	}

	public void setTeleBlockDelay(long teleDelay) {
		getTemporaryAttributtes().put("TeleBlocked",
				teleDelay + Utils.currentTimeMillis());
	}

	public long getTeleBlockDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("TeleBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public void setPrayerDelay(long teleDelay) {
		getTemporaryAttributtes().put("PrayerBlocked",
				teleDelay + Utils.currentTimeMillis());
		prayer.closeAllPrayers();
	}

	public long getPrayerDelay() {
		Long teleblock = (Long) getTemporaryAttributtes().get("PrayerBlocked");
		if (teleblock == null)
			return 0;
		return teleblock;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public FriendChatsManager getCurrentFriendChat() {
		return currentFriendChat;
	}

	public void setCurrentFriendChat(FriendChatsManager currentFriendChat) {
		this.currentFriendChat = currentFriendChat;
	}

	public String getCurrentFriendChatOwner() {
		return currentFriendChatOwner;
	}

	public void setCurrentFriendChatOwner(String currentFriendChatOwner) {
		this.currentFriendChatOwner = currentFriendChatOwner;
	}

	public int getSummoningLeftClickOption() {
		return summoningLeftClickOption;
	}

	public void setSummoningLeftClickOption(int summoningLeftClickOption) {
		this.summoningLeftClickOption = summoningLeftClickOption;
	}

	public boolean canSpawn() {
		if (Wilderness.isAtWild(this)
				|| getControlerManager().getControler() instanceof FightPitsArena
				|| getControlerManager().getControler() instanceof CorpBeastControler
				|| getControlerManager().getControler() instanceof PestControlLobby
				|| getControlerManager().getControler() instanceof PestControlGame
				|| getControlerManager().getControler() instanceof ZGDControler
				|| getControlerManager().getControler() instanceof GodWars
				|| getControlerManager().getControler() instanceof DTControler
				|| getControlerManager().getControler() instanceof DuelArena
				|| getControlerManager().getControler() instanceof CastleWarsPlaying
				|| getControlerManager().getControler() instanceof CastleWarsWaiting
				|| getControlerManager().getControler() instanceof FightCaves
				|| getControlerManager().getControler() instanceof FightKiln
				|| FfaZone.inPvpArea(this)
				|| getControlerManager().getControler() instanceof NomadsRequiem
				|| getControlerManager().getControler() instanceof QueenBlackDragonController
				|| getControlerManager().getControler() instanceof WarControler) {
			return false;
		}
		if (getControlerManager().getControler() instanceof CrucibleControler) {
			CrucibleControler controler = (CrucibleControler) getControlerManager()
					.getControler();
			return !controler.isInside();
		}
		return true;
	}

	public long getPolDelay() {
		return polDelay;
	}

	public void addPolDelay(long delay) {
		polDelay = delay + Utils.currentTimeMillis();
	}

	public void setPolDelay(long delay) {
		this.polDelay = delay;
	}

	public List<Integer> getSwitchItemCache() {
		return switchItemCache;
	}

	public AuraManager getAuraManager() {
		return auraManager;
	}

	public int getMovementType() {
		if (getTemporaryMoveType() != -1)
			return getTemporaryMoveType();
		return getRun() ? RUN_MOVE_TYPE : WALK_MOVE_TYPE;
	}

	public List<String> getOwnedObjectManagerKeys() {
		if (ownedObjectsManagerKeys == null) // temporary
			ownedObjectsManagerKeys = new LinkedList<String>();
		return ownedObjectsManagerKeys;
	}

	public boolean hasInstantSpecial(final int weaponId) {
		switch (weaponId) {
		case 4153:
		case 28004:
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
		case 1377:
		case 13472:
		case 35:// Excalibur
		case 8280:
		case 14632:
			return true;
		default:
			return false;
		}
	}

	public void performInstantSpecial(final int weaponId) {
		int specAmt = PlayerCombat.getSpecialAmmount(weaponId);
		if (combatDefinitions.hasRingOfVigour())
			specAmt *= 0.9;
		if (combatDefinitions.getSpecialAttackPercentage() < specAmt) {
			getPackets().sendGameMessage("You don't have enough power left.");
			combatDefinitions.desecreaseSpecialAttack(0);
			return;
		}
		if (this.getSwitchItemCache().size() > 0) {
			ButtonHandler.submitSpecialRequest(this);
			return;
		}
		switch (weaponId) {
		case 4153:
		case 28004:
			combatDefinitions.setInstantAttack(true);
			combatDefinitions.switchUsingSpecialAttack();
			Entity target = (Entity) getTemporaryAttributtes().get(
					"last_target");
			if (target != null
					&& target.getTemporaryAttributtes().get("last_attacker") == this) {
				if (!(getActionManager().getAction() instanceof PlayerCombat)
						|| ((PlayerCombat) getActionManager().getAction())
								.getTarget() != target) {
					getActionManager().setAction(new PlayerCombat(target));
				}
			}
			break;
		case 1377:
		case 13472:
			setNextAnimation(new Animation(1056));
			setNextGraphics(new Graphics(246));
			setNextForceTalk(new ForceTalk("Raarrrrrgggggghhhhhhh!"));
			int defence = (int) (skills.getLevelForXp(Skills.DEFENCE) * 0.90D);
			int attack = (int) (skills.getLevelForXp(Skills.ATTACK) * 0.90D);
			int range = (int) (skills.getLevelForXp(Skills.RANGE) * 0.90D);
			int magic = (int) (skills.getLevelForXp(Skills.MAGIC) * 0.90D);
			int strength = (int) (skills.getLevelForXp(Skills.STRENGTH) * 1.2D);
			skills.set(Skills.DEFENCE, defence);
			skills.set(Skills.ATTACK, attack);
			skills.set(Skills.RANGE, range);
			skills.set(Skills.MAGIC, magic);
			skills.set(Skills.STRENGTH, strength);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 35:// Excalibur
		case 8280:
		case 14632:
			setNextAnimation(new Animation(1168));
			setNextGraphics(new Graphics(247));
			final boolean enhanced = weaponId == 14632;
			skills.set(
					Skills.DEFENCE,
					enhanced ? (int) (skills.getLevelForXp(Skills.DEFENCE) * 1.15D)
							: (skills.getLevel(Skills.DEFENCE) + 8));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 5;

				@Override
				public void run() {
					if (isDead() || hasFinished()
							|| getHitpoints() >= getMaxHitpoints()) {
						stop();
						return;
					}
					heal(enhanced ? 80 : 40);
					if (count-- == 0) {
						stop();
						return;
					}
				}
			}, 4, 2);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		case 15486:
		case 22207:
		case 22209:
		case 22211:
		case 22213:
			setNextAnimation(new Animation(12804));
			setNextGraphics(new Graphics(2319));// 2320
			setNextGraphics(new Graphics(2321));
			addPolDelay(60000);
			combatDefinitions.desecreaseSpecialAttack(specAmt);
			break;
		}
	}

	public void setDisableEquip(boolean equip) {
		disableEquip = equip;
	}

	public int getTrollsKilled() {
		return trollsKilled;
	}
	
	public int getTrollsToKill() {
		return trollsToKill;
	}
	
	public int setTrollsKilled(int trollsKilled) {
		return (this.trollsKilled = trollsKilled);
	}
	
	public int setTrollsToKill(int toKill) {
		return (this.trollsToKill = toKill);
	}
	
	public void addTrollKill() {
		trollsKilled++;
	}
	
	public boolean isEquipDisabled() {
		return disableEquip;
	}

	public void addDisplayTime(long i) {
		this.displayTime = i + Utils.currentTimeMillis();
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public int getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}

	public int getClanStatus() {
		return clanStatus;
	}

	public void setClanStatus(int clanStatus) {
		this.clanStatus = clanStatus;
	}

	public int getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(int tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public int getAssistStatus() {
		return assistStatus;
	}

	public void setAssistStatus(int assistStatus) {
		this.assistStatus = assistStatus;
	}

	public boolean isSpawnsMode() {
		return spawnsMode;
	}

	public void setSpawnsMode(boolean spawnsMode) {
		this.spawnsMode = spawnsMode;
	}

	public IsaacKeyPair getIsaacKeyPair() {
		return isaacKeyPair;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}
	
	public EXQuestManager getEXQuestManager() {
		return exquestManager;
	}	
	
	public EliteChapterFive getEliteChapterV() {
		return (EliteChapterFive) getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_V);
	}		
	
	public EliteChapterFour getEliteChapterIV() {
		return (EliteChapterFour) getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_IV);
	}	
	
	public EliteChapterThree getEliteChapterIII() {
		return (EliteChapterThree) getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_III);
	}		
	
	public EliteChapterTwo getEliteChapterII() {
		return (EliteChapterTwo) getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_II);
	}	
	
	public EliteChapterOne getEliteChapterI() {
		return (EliteChapterOne) getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
	}	

	public boolean isCompletedFightCaves() {
		return completedFightCaves;
	}

	public void setCompletedFightCaves() {
		if (!completedFightCaves) {
			completedFightCaves = true;
			refreshFightKilnEntrance();
		}
	}

	public boolean isCompletedFightKiln() {
		return completedFightKiln;
	}

	public void setCompletedFightKiln() {
		completedFightKiln = true;
	}
	
	public boolean isCompletedFullSlayerHelmet() {
		return completedFullSlayerHelmet;
	}

	public void setCompletedFullSlayerHelmet() {
		completedFullSlayerHelmet = true;
	}	
	
	public boolean isKilledDragithNurn() {
		return killedDragithNurn;
	}

	public void setKilledDragithNurn() {
		killedDragithNurn = true;
	}	
	

	public boolean isWonFightPits() {
		return wonFightPits;
	}

	public void setWonFightPits() {
		wonFightPits = true;
	}

	public boolean isCantTrade() {
		return cantTrade;
	}

	public void setCantTrade(boolean canTrade) {
		this.cantTrade = canTrade;
	}

	public String getYellColor() {
		return yellColor;
	}

	public void setYellColor(String yellColor) {
		this.yellColor = yellColor;
	}
	
	public String getYellTitle() {
		return yellTitle;
	}

	public void setYellTitle(String yellTitle) {
		this.yellTitle = yellTitle;
	}
	
	public String getYellShade() {
		return yellShade;
	}

	public void setYellShade(String yellShade) {
		this.yellShade = yellShade;
	}	

	/**
	 * Gets the pet.
	 * 
	 * @return The pet.
	 */
	public Pet getPet() {
		return pet;
	}

	/**
	 * Sets the pet.
	 * 
	 * @param pet
	 *            The pet to set.
	 */
	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public boolean isSupporter() {
		return isSupporter;
	}

	public void setSupporter(boolean isSupporter) {
		this.isSupporter = isSupporter;
	}

	/**
	 * Gets the petManager.
	 * 
	 * @return The petManager.
	 */
	public PetManager getPetManager() {
		return petManager;
	}

	/**
	 * Sets the petManager.
	 * 
	 * @param petManager
	 *            The petManager to set.
	 */
	public void setPetManager(PetManager petManager) {
		this.petManager = petManager;
	}

	public boolean isXpLocked() {
		return xpLocked;
	}

	public void setXpLocked(boolean locked) {
		this.xpLocked = locked;
	}

	public int getLastBonfire() {
		return lastBonfire;
	}

	public void setLastBonfire(int lastBonfire) {
		this.lastBonfire = lastBonfire;
	}

	public boolean isYellOff() {
		return yellOff;
	}

	public void setYellOff(boolean yellOff) {
		this.yellOff = yellOff;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public double getHpBoostMultiplier() {
		return hpBoostMultiplier;
	}

	public void setHpBoostMultiplier(double hpBoostMultiplier) {
		this.hpBoostMultiplier = hpBoostMultiplier;
	}

	/**
	 * Gets the killedQueenBlackDragon.
	 * 
	 * @return The killedQueenBlackDragon.
	 */
	public boolean isKilledQueenBlackDragon() {
		return killedQueenBlackDragon;
	}

	/**
	 * Sets the killedQueenBlackDragon.
	 * 
	 * @param killedQueenBlackDragon
	 *            The killedQueenBlackDragon to set.
	 */
	public void setKilledQueenBlackDragon(boolean killedQueenBlackDragon) {
		this.killedQueenBlackDragon = killedQueenBlackDragon;
	}

	public boolean hasLargeSceneView() {
		return largeSceneView;
	}

	public void setLargeSceneView(boolean largeSceneView) {
		this.largeSceneView = largeSceneView;
	}
	/**
	 * @return the runeSpanPoint
	 */
	public int getRuneSpanPoints() {
		return runeSpanPoints;
	}

	/**
	 * @param runeSpanPoint
	 *            the runeSpanPoint to set
	 */
	public void setRuneSpanPoint(int runeSpanPoints) {
		this.runeSpanPoints = runeSpanPoints;
	}

	/**
	 * Adds points
	 * 
	 * @param points
	 */
	public void addRunespanPoints(int points) {
		this.runeSpanPoints += points;
	}

	public DuelRules getLastDuelRules() {
		return lastDuelRules;
	}

	public void setLastDuelRules(DuelRules duelRules) {
		this.lastDuelRules = duelRules;
	}

	public boolean isTalkedWithMarv() {
		return talkedWithMarv;
	}

	public void setTalkedWithMarv() {
		talkedWithMarv = true;
	}

	public int getCrucibleHighScore() {
		return crucibleHighScore;
	}

	public void increaseCrucibleHighScore() {
		crucibleHighScore++;
	}

	public void setSlayerPoints(int slayerPoints) {
		this.slayerPoints = slayerPoints;
	}
	
	public int getSlayerPoints() {
		return slayerPoints;
	}
	
	public void setBossSlayerPoints(int bossslayerPoints) {
		this.bossslayerPoints = bossslayerPoints;
	}
	
	public int getBossSlayerPoints() {
		return bossslayerPoints;
	}	
	
	public void setCopyrightKills(int copyrightkills) {
		this.copyrightkills = copyrightkills;
	}
	
	public int getCopyrightKills() {
		return copyrightkills;
	}
	public void setCorporealKills(int corporealkills) {
		this.corporealkills = corporealkills;
	}
	
	public int getCorporealKills() {
		return corporealkills;
	}	
	public void setJadKills(int jadkills) {
		this.jadkills = jadkills;
	}
	
	public int getJadKills() {
		return jadkills;
	}	
	public void setObsidianKingKills(int obsidiankingkills) {
		this.obsidiankingkills = obsidiankingkills;
	}
	
	public int getObsidianKingKills() {
		return obsidiankingkills;
	}
	
	public void setFatalKills(int fatalkills) {
		this.fatalkills = fatalkills;
	}
	
	public int getFatalKills() {
		return fatalkills;
	}
	
	public void setMuteMarks(int mutemarks) {
		if (mutemarks == 0) {
			this.mutemark = mutemarks;
			return;
		}
		if (mutemarks < 0)
			return;
		if (mutemarks == 1)
			mutemark = 1;
		if (mutemark == 3)
			return;
		this.mutemark = mutemarks;
		if (mutemark == 3) {
			sm("You have reached 3 mute marks. You have been muted for 24 hours.");
			setMuted(Utils.currentTimeMillis() + ((24 * 60 * 60 * 1000)));
		}
		try {
			getInterfaceManager().sendInterface(627);		
			getPackets().sendIComponentText(627, 5, "");
			getPackets().sendIComponentText(627, 14, "A staff has appointed you a mute mark. If you get 3 mute marks, you will be muted."
														+ "<col=E32020> You have " + mutemarks + " mute marks.");
			setNextForceTalk(new ForceTalk("I have " + mutemark + " mute marks, three mute marks will get me muted."));
		} catch (Throwable e) {
			
		}
	}
	
	public int getMuteMarks() {
		return mutemark;
	}	
	
	public void setFix5bcape(int fixfiveb) {
		this.fixfiveb = fixfiveb;
	}
	
	public int getFix5bcape() {
		return fixfiveb;
	}	
	
	public void setInstanceKick(boolean isInstanceKicked) {
		this.isInstanceKicked = isInstanceKicked;
	}
	
	public boolean getInstanceKick() {
		return isInstanceKicked;
	}	
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public int getTimer() {
		return timer;
	}	
	
	public void setDestroyTimer(int destroytimer) {
		this.destroytimer = destroytimer;
	}
	
	public int getDestroyTimer() {
		return destroytimer;
	}		
	
	
	public void setInstanceEnd(boolean instanceend) {
		this.instanceend = instanceend;
	}
	
	public boolean getEnd() {
		return instanceend;
	}
	
	
	public void setPermaBandos(boolean permabandos) {
		this.permabandos = permabandos;
	}
	
	public boolean getPermaBandos() {
		return permabandos;
	}
	public void setPermaArmadyl(boolean permaarmadyl) {
		this.permaarmadyl = permaarmadyl;
	}
	
	public boolean getPermaArmadyl() {
		return permaarmadyl;
	}
	public void setPermaSaradomin(boolean permasaradomin) {
		this.permasaradomin = permasaradomin;
	}
	
	public boolean getPermaSaradomin() {
		return permasaradomin;
	}
	public void setPermaZamorak(boolean permazamorak) {
		this.permazamorak = permazamorak;
	}
	
	public boolean getPermaZamorak() {
		return permazamorak;
	}
	public void setPermaBlink(boolean permablink) {
		this.permablink = permablink;
	}
	
	public boolean getPermaBlink() {
		return permablink;
	}
	public void setPermaEradicator(boolean permaeradicator) {
		this.permaeradicator = permaeradicator;
	}
	
	public boolean getPermaEradicator() {
		return permaeradicator;
	}
	public void setPermaTrio(boolean permatrio) {
		this.permatrio = permatrio;
	}
	
	public boolean getPermaTrio() {
		return permatrio;
	}
	public void setPermaCorp(boolean permacorp) {
		this.permacorp = permacorp;
	}
	
	public boolean getPermaCorp() {
		return permacorp;
	}
	public void setPermaKBD(boolean permakbd) {
		this.permakbd = permakbd;
	}
	
	public boolean getPermaKBD() {
		return permakbd;
	}
	public void setPermaGradum(boolean permagradum) {
		this.permagradum = permagradum;
	}
	
	public boolean getPermaGradum() {
		return permagradum;
	}
	public void setPermaWyrm(boolean permawyrm) {
		this.permawyrm = permawyrm;
	}
	
	public boolean getPermaWyrm() {
		return permawyrm;
	}
	public void setPermaNecroLord(boolean permanecrolord) {
		this.permanecrolord = permanecrolord;
	}
	
	public boolean getPermaNecroLord() {
		return permanecrolord;
	}	
	public void setPermaAvatar(boolean permaavatar) {
		this.permaavatar = permaavatar;
	}
	
	public boolean getPermaAvatar() {
		return permaavatar;
	}
	public void setPermaFear(boolean permafear) {
		this.permafear = permafear;
	}
	
	public boolean getPermaFear() {
		return permafear;
	}		
	public void setPermaSTQ(boolean permastq) {
		this.permastq = permastq;
	}
	
	public boolean getPermaSTQ() {
		return permastq;
	}	
	public void setPermaGeno(boolean permageno) {
		this.permageno = permageno;
	}
	
	public boolean getPermaGeno() {
		return permageno;
	}
	public void setPermaRajj(boolean permarajj) {
		this.permarajj = permarajj;
	}
	
	public boolean getPermaRajj() {
		return permarajj;
	}			
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}
	
	public int getWrong() {
		return wrong;
	}	
	
	public void setHacker(int hacker) {
		this.hacker = hacker;
	}
	
	public int getHacker() {
		return hacker;
	}	
	
	public void setSecurityPin(int securitypin) {
		this.securitypin = securitypin;
	}
	
	public int getSecurityPin() {
		return securitypin;
	}	
	
	private int donationpin;
	
	public void setDonationPin(int donationpin) {
		this.donationpin = donationpin;
	}
	
	public int getDonationPin() {
		return donationpin;
	}	
	
	public void setInstancePin(int instancepin) {
		this.instancepin = instancepin;
	}
	
	public int getInstancePin() {
		return instancepin;
	}		
	
	public void setAnnouncement(int donexp) {
		this.donexp = donexp;
	}
	
	public int getAnnouncement() {
		return donexp;
	}	
	
	public void setAnnouncement2(int donexp2) {
		this.donexp2 = donexp2;
	}
	
	public int getAnnouncement2() {
		return donexp2;
	}	
	
	public void setInterfaceAmount(int interfaceamount) {
		this.interfaceamount = interfaceamount;
	}
	
	public int getInterfaceAmount() {
		return interfaceamount;
	}
	
	public void setWildyBossKills(int wildybosskills) {
		this.wildybosskills = wildybosskills;
	}
	
	public int getWildyBossKills() {
		return wildybosskills;
	}	
	
	public void setSpawnRate(int spawnrate) {
		this.spawnrate = spawnrate;
	}
	
	public int getSpawnRate() {
		return spawnrate;
	}		
	
	public void setVotingTime(int firsttimevoting) {
		this.firsttimevoting = firsttimevoting;
	}
	
	public int getVotingTime() {
		return firsttimevoting;
	}	

	
	public void setSomethingKills(int somethingkills) {
		this.somethingkills = somethingkills;
	}
	
	public int getSomethingKills() {
		return somethingkills;
	}
	public void setSaradominKills(int saradominkills) {
		this.saradominkills = saradominkills;
	}
	
	public int getSaradominKills() {
		return saradominkills;
	}
	public void setNexKills(int nexkills) {
		this.nexkills = nexkills;
	}
	
	public int getNexKills() {
		return nexkills;
	}
	public void setSunfreetKills(int sunfreetkills) {
		this.sunfreetkills = sunfreetkills;
	}
	
	public int getSunfreetKills() {
		return sunfreetkills;
	}
	public void setArmadylKills(int armadylkills) {
		this.armadylkills = armadylkills;
	}
	
	public int getArmadylKills() {
		return armadylkills;
	}
	public void setBandosKills(int bandoskills) {
		this.bandoskills = bandoskills;
	}
	
	public int getBandosKills() {
		return bandoskills;
	}
	public void setZamorakKills(int zamorakkills) {
		this.zamorakkills = zamorakkills;
	}
	
	public int getZamorakKills() {
		return zamorakkills;
	}	
	public void setEradicatorBossKills(int eradicatorbosskills) {
		this.eradicatorbosskills = eradicatorbosskills;
	}
	
	public int getEradicatorBossKills() {
		return eradicatorbosskills;
	}
	public void setGenoKills(int genokills) {
		this.genokills = genokills;
	}
	
	public int getGenoKills() {
		return genokills;
	}
	public void setRajjKills(int rajjkills) {
		this.rajjkills = rajjkills;
	}
	
	public int getRajjKills() {
		return rajjkills;
	}
	public void setFearKills(int fearkills) {
		this.fearkills = fearkills;
	}
	
	public int getFearKills() {
		return fearkills;
	}
	public void setBlinkKills(int blinkkills) {
		this.blinkkills = blinkkills;
	}
	
	public int getBlinkKills() {
		return blinkkills;
	}
	public void setExtremeBossKills(int extremebosskills) {
		this.extremebosskills = extremebosskills;
	}
	
	public int getExtremeBossKills() {
		return extremebosskills;
	}
	public void setRegularBossKills(int regularbosskills) {
		this.regularbosskills = regularbosskills;
	}
	
	public int getRegularBossKills() {
		return regularbosskills;
	}
	public void setGradumKills(int gradumkills) {
		this.gradumkills = gradumkills;
	}
	
	public int getGradumKills() {
		return gradumkills;
	}
	
	public boolean isTalkedWithKuradal() {
		return talkedWithKuradal;
	}

	public void setTalkedWithKuradal() {
		talkedWithKuradal = true;
	}
	
	public boolean isTalkedWithReaper() {
		return talkedWithReaper;
	}

	public void setTalkedWithReaper() {
		talkedWithReaper = true;
	}	

	public void falseWithKuradal() {
		talkedWithKuradal = false;
	}

	public int getLoyaltyPoints() {
		return Loyaltypoints;
	}

	public void setLoyaltyPoints(int Loyaltypoints) {
		this.Loyaltypoints = Loyaltypoints;
	}
	
	public int getDeposittedBones() {
		return EradicatorBonePoints;
	}	

	public void setDeposittedBones(int EradicatorBonePoints) {
		this.EradicatorBonePoints = EradicatorBonePoints;
	}	
	
	public int getSuperDeposittedBones() {
		return SuperBonePoints;
	}	

	public void setSuperDeposittedBones(int SuperBonePoints) {
		this.SuperBonePoints = SuperBonePoints;
	}	
	
	public void setTask(SlayerTask task) {
		this.task = task;
	}
	
	public void setBossTask(BossSlayerTask bosstask) {
		this.bosstask = bosstask;
	}

	public SlayerTask getTask() {
		return task;
	}
	
	public BossSlayerTask getBossTask() {
		return bosstask;
	}

	public void setSafeMode() {

	}

	public void sm(String message) {
		getPackets().sendGameMessage(message);
	}
	
	public void sit(int interfaceid, int componentId, String text) {
		getPackets().sendIComponentText(interfaceid, componentId, text);
	}

	public void teleportPlayer(int x, int y, int z) {
		setNextWorldTile(new WorldTile(x, y, z));
		stopAll();
	}

	public void LockAccount() {
		World.sendWorldMessage("JAG was enabled, account: <username>'s character has been" + "locked by Jagex Account Guardian.", true);

	}

	public int getLastLoggedIn() {
		return lastlogged;
	}

	public void out(String string) {
		getPackets().sendGameMessage(string);
	}

	private transient VarsManager varsManager;

	public VarsManager getVarsManager() {
	    return varsManager;
	}
    
    private FarmingManager farmingManager;
	private boolean permaSupers;
    
    public FarmingManager getFarmingManager() {
		return farmingManager;
    }
	public void setMoneyPouch(int i) {
		this.moneypouch = i;
	}
	public boolean isDestroytimer() {
		return isdestroytimer;
	}
	public void setisDestroytimer(boolean isdestroytimer) {
		this.isdestroytimer = isdestroytimer;
	}
	public int getViewpage() {
		return viewpage;
	}
	public void setViewpage(int viewpage) {
		this.viewpage = viewpage;
	}
	public int getRanklend() {
		return ranklend;
	}
	public void setRanklend(int ranklend) {
		this.ranklend = ranklend;
	}
	public int getInstanceBooth() {
		return instanceBooth;
	}
	public void setInstanceBooth(int instanceBooth) {
		this.instanceBooth = instanceBooth;
	}
	public boolean isPermaBank() {
		return permabank;
	}
	public void setPermaBank(boolean permabank) {
		this.permabank = permabank;
	}
	public String getInstanceControler() {
		return instanceControler;
	}
	public void setInstanceControler(String instanceControler) {
		this.instanceControler = instanceControler;
	}
	public WorldTile getOutside() {
		return getoutside;
	}
	public void setOutside(WorldTile getoutside) {
		this.getoutside = getoutside;
	}
	public boolean isOutside() {
		return outside;
	}
	public void setOutside(boolean outside) {
		this.outside = outside;
	}
	public boolean isPermaRegular() {
		return permaregular;
	}
	public void setPermaRegular(boolean permaregular) {
		this.permaregular = permaregular;
	}
	public boolean isPermaExtreme() {
		return permaextreme;
	}
	public void setPermaExtreme(boolean permaextreme) {
		this.permaextreme = permaextreme;
	}
	public boolean isPermaSunfreet() {
		return permasunfret;
	}
	public void setPermaSunfreet(boolean permasunfret) {
		this.permasunfret = permasunfret;
	}
	public int getWyrmKills() {
		return wyrmkills;
	}
	public void setWyrmKills(int wyrmkills) {
		this.wyrmkills = wyrmkills;
	}
	public int getAvatarKills() {
		return avatarkills;
	}
	public void setAvatarKills(int avatarkills) {
		this.avatarkills = avatarkills;
	}
	public int getNecrolordKills() {
		return necrolordkills;
	}
	public void setNecrolordKills(int necrolordkills) {
		this.necrolordkills = necrolordkills;
	}
	public boolean isPermaBrutals() {
		return permabrutals;
	}
	public void setPermaBrutals(boolean permabrutals) {
		this.permabrutals = permabrutals;
	}
	public boolean isPermaSupers() {
		return permaSupers;
	}
	public void setPermaSupers(boolean Supers) {
		this.permaSupers = Supers;
	}	
	public boolean isPermaObbyKing() {
		return permaobbyking;
	}
	public void setPermaObbyKing(boolean permaobbyking) {
		this.permaobbyking = permaobbyking;
	}
	public boolean isPermaEradJad() {
		return permaeradjad;
	}
	public void setPermaEradJad(boolean permaeradjad) {
		this.permaeradjad = permaeradjad;
	}
	public boolean isViewStats() {
		return viewStats;
	}
	public void setViewStats(boolean viewStats) {
		this.viewStats = viewStats;
	}
	public int getCollectLoanMoney() {
		return collectLoanMoney;
	}
	public void setCollectLoanMoney(int collectLoanMoney) {
		this.collectLoanMoney = collectLoanMoney;
	}
	public int getDropbeam() {
		return dropbeam;
	}
	public void setDropbeam(int dropbeam) {
		this.dropbeam = dropbeam;
	}
	public int getSelectedbeam() {
		return selectedbeam;
	}
	public void setSelectedbeam(int selectedbeam) {
		this.selectedbeam = selectedbeam;
	}
	public int getKillStreak() {
		return killStreak;
	}
	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
	}
	public long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isDiceAnnounce() {
		return diceannounce;
	}
	public void setDiceAnnounce(boolean diceannounce) {
		this.diceannounce = diceannounce;
	}
	public boolean isBankEquip() {
		return bankequip;
	}
	public void setBankEquip(boolean bankequip) {
		this.bankequip = bankequip;
	}
	public int getTriviaPoints() {
		return triviapoints;
	}
	public void setTriviaPoints(int triviapoints) {
		this.triviapoints = triviapoints;
	}
	private boolean triviamaster;
	public int ironfirsttime;
	public boolean wenttoevent;

	private boolean learnedPermTurmBoost;

	public boolean completed = true;

	public WorldObject examinedObj;

	public int customDropRate;
	
	public boolean isTriviamaster() {
		return triviamaster;
	}
	public void setTriviamaster(boolean triviamaster) {
		this.triviamaster = triviamaster;
	}
	public boolean getLearnedRocktailSoup() {
		return learnedRocktailSoup;
	}
	public void setLearnedRocktailSoup(boolean learnedRocktailSoup) {
		this.learnedRocktailSoup = learnedRocktailSoup;
	}
	
	public String getPanelDisplayName() {
		return paneldname;
	}
	public void setPanelDisplayName(String paneldname) {
		this.paneldname = paneldname;
	}	
	
	public String getPanelName() {
		return panelname;
	}
	public void setPanelName(String panelname) {
		this.panelname = panelname;
	}

	public BankPreset getBankPreset2() {
		return preset2;
	}
	public void setBankPreset2(BankPreset preset) {
		this.preset2 = preset;
	}
	
	public BankPreset getBankPreset3() {
		return preset3;
	}
	public void setBankPreset3(BankPreset preset) {
		this.preset3 = preset;
	}
	
	public BankPreset getBankPreset4() {
		return preset4;
	}
	public void setBankPreset4(BankPreset preset) {
		this.preset4 = preset;
	}
	
	public BankPreset getBankPreset() {
		return preset;
	}
	public void setBankPreset(BankPreset preset) {
		this.preset = preset;
	}
	public int getSaveP() {
		return savep;
	}
	public void setSaveP(int savep) {
		this.savep = savep;
	}
	public KeybindFunctions getKeyFunction() {
		return keybindfunctions;
	}
	public void setKeyFunction(KeybindFunctions keybindfunction) {
		this.keybindfunctions = keybindfunction;
	}
	public int getVote() {
		return votes;
	}
	public void setVote(int votes) {
		this.votes = votes;
	}
	public int getPinAttempts() {
		return pinAttempts;
	}
	public void setPinAttempts(int pinAttempts) {
		this.pinAttempts = pinAttempts;
	}
	public long getLoyaltytimer() {
		return loyaltytimer;
	}
	public void setLoyaltytimer(long loyaltytimer) {
		this.loyaltytimer = loyaltytimer;
	}
	public int[] getRolls() {
		return rolls;
	}
	public void setRolls(int[] rolls) {
		this.rolls = rolls;
	}
	public void setRolls(int index, int amount) {
		rolls[index] = amount;
	}
	public void setRolls(int amount) {
		int temp = amount;
		for (int i = (rolls.length - 2); i >= 0; i--) {                
		    rolls[i+1] = rolls[i];
		}
		rolls[0] = temp;
		
	}
	public boolean hasSecondBank() {
		return hasSecondBank;
	}
	public void setSecondBank(boolean hasSecondBank) {
		this.hasSecondBank = hasSecondBank;
	}
	public int getHairymonkeykills() {
		return hairymonkeykills;
	}
	public void setHairymonkeykills(int hairymonkeykills) {
		this.hairymonkeykills = hairymonkeykills;
	}
	public String getLastPublicMessageString() {
		return lastPublicMessageString;
	}
	public void setLastPublicMessageString(String lastPublicMessageString) {
		this.lastPublicMessageString = lastPublicMessageString;
	}
	public CurrencyPouch getCurrencyPouch() {
		return currencypouch;
	}
	public void setCurrencyPouch(CurrencyPouch currencypouch) {
		this.currencypouch = currencypouch;
	}
	public boolean hasLearnedPermTurmBoost() {
		return learnedPermTurmBoost;
	}
	public void setLearnedPermTurmBoost(boolean PermTurmBoost) {
		this.learnedPermTurmBoost = PermTurmBoost;
	}	
	public boolean hasLearnedAutoLootSeals() {
		return learnedAutoLootSeals;
	}
	public void setLearnedAutoLootSeals(boolean learnedAutoLootSeals) {
		this.learnedAutoLootSeals = learnedAutoLootSeals;
	}
	public boolean isHasLoginToggled() {
		return hasLoginToggled;
	}
	public void setHasLoginToggled(boolean hasLoginToggled) {
		this.hasLoginToggled = hasLoginToggled;
	}
	public int getHMTrioKills() {
		return hmtriokills;
	}
	public void setHMTrioKills(int hmtriokills) {
		this.hmtriokills = hmtriokills;
	}
	public Runnable getVoteR() {
		return vote;
	}
	public void setVoteR(Runnable vote) {
		this.vote = vote;
	}
	public int getSpellPower() {
		return spellpower;
	}
	public void setSpellPower(int spellpower) {
		if (!getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_III))
			return;
		if (spellpower > this.spellpower)
			sm("<col=123abc>You gain " + (spellpower - this.spellpower) + " Spell power.");
		this.spellpower = spellpower;
	}
	public int getSpellTrait() {
		return spelltraits;
	}
	public void setSpellTrait(int spelltraits) {
		this.spelltraits = spelltraits;
	}
	public boolean isEnchantLegs() {
		return enchantLegs;
	}
	public void setEnchantLegs(boolean enchantLegs) {
		this.enchantLegs = enchantLegs;
	}
	public boolean isEnchantBody() {
		return enchantBody;
	}
	public void setEnchantBody(boolean enchantBody) {
		this.enchantBody = enchantBody;
	}
	public boolean isEnchantHelm() {
		return enchantHelm;
	}
	public void setEnchantHelm(boolean enchantHelm) {
		this.enchantHelm = enchantHelm;
	} 
	
	/**
	 * @author Craig
	 * @see Handles boss drop ratio
	 */
	public HashMap<Integer, Integer> dropRatio = new HashMap<>();

	public HashMap<Integer, Integer> getdropRatio() {
		return dropRatio;
	}
	
	
	private boolean hasAntiBot;
	
	public boolean isHasAntiBot() {
		return hasAntiBot;
	}

	public void setHasAntiBot(boolean hasAntiBot) {
		this.hasAntiBot = hasAntiBot;
	}
	
	

}
