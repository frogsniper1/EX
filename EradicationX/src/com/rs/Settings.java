package com.rs;

import java.math.BigInteger;

import com.rs.game.WorldTile;
import com.rs.rss.Feed;
import com.rs.rss.RSSFeedParser;

public final class Settings {

	public static final String SERVER_NAME = "Eradication Reborn";
	public static final String OWNER = "Era,Jenny,Vlad";		
	public static final int PORT_ID = 43594;
	public static final int CLIENT_BUILD = 718;
	public static final int CUSTOM_CLIENT_BUILD = 466239142;
	/**
	 * General client and server settings.
	 */
	public static final String CACHE_PATH = "data/cache/";
	public static final int RECEIVE_DATA_LIMIT = 7500;
	public static final int PACKET_SIZE_LIMIT = 7500;

	public static final int droppedItemDelay = 180;
	
	/**
	 * Link settings
	 */
	public static final String WEBSITE_LINK = "http://eradication-reborn.com";
	public static final String ITEMLIST_LINK = "http://itemdb.biz";
	public static final String ITEMDB_LINK = "http://itemdb.biz";
	public static final String VOTE_LINK = "http://eradication-reborn.com/vote";
	public static final String OwnerIPs = "127.0.0.1"; // Add comma after every IP
	public static final String RED = "E6293F";
	public static final String GREEN = "15CF0E";
	public static final String validLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-=_+[{]}: ;.,><?!@#$%^&*()~|\\";
	public static  RSSFeedParser parser;
	public static Feed feed;

        /**
         * Rare Drop Names
         */
        public static String[] RARE_DROPS = { 
        	"Slayer Helm Part Key", "Dragonfire", "Razer", "Zamorak hilt", "Zamorakian spear", "Pernix", "Claws", "Lightning", "Torva", "Virtus", "Bandos", "Armadyl ch", "Armadyl pl", "Armadyl he",
            "Partyhat", "Tzhaar", "Subjugation", "Steadfast", "Glaiven", "Ragefire", "Spirit Shield", "Death lotus", "Seasinger", "Rainbow", "Armadyl hilt",
            "Fury", "Chaotic", "Armadyl battle", "Drygore", "Ticket", "Flaming", "Brutal", "Obsidian Shard", "Enchanting Potion", "battle-mage", "flash",
			"Pactbreaker", "Tokhaar Warlord", "Necromancer", "Firebrand", "Ascension", "Royal", "Vanguard", "Trickster", "Dominion", "Razor", "Frostbite",
			"Shadow", "Scythe", "Ring of the gods", "Annihilation", "Decimation", "Tyrannical", "Treasonous", "Saradomin hilt", "sigil", "Saradomin's whisper", 
			"Saradomin's murmur", "Saradomin's hiss", "Goliath", "Spellcaster", "Swift"
        };
        
        public static int[] RARES = { 1038, 1040, 1042, 1044, 1046, 1048, 28014, 29983, 29984, 29985, 29992, 29993, 
        	29994, 29995, 29996, 29997, 28015, 28016, 29981, 29986, 29987, 29988, 29989, 29921, 29920, 29923, 29924,
        	29925, 29926, 29930, 29931, 29932, 29933, 29934, 29935, 29936, 29937, 4565, 27343, 24387,
        	};        
        
	/**
	 * Launching settings
	 */
	public static boolean DEBUG;
	public static boolean HOSTED;
	public static boolean ECONOMY;


	/**
	 * If the use of the managment server is enabled.
	 */
	public static boolean MANAGMENT_SERVER_ENABLED = true;

	/**
	 * Graphical User Interface settings
	 */
	public static final String GUI_SIGN = "Artisticy GUI";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/**
	 * Player settings
	 */
	public static final int START_PLAYER_HITPOINTS = 100;
	public static final WorldTile START_PLAYER_LOCATION = new WorldTile(3108, 3160, 3);
	public static final String START_CONTROLER = "null";
	public static final WorldTile RESPAWN_PLAYER_LOCATION = new WorldTile(3968, 4823, 1);
	public static final long MAX_PACKETS_DECODER_PING_DELAY = 30000; // 30seconds
	public static final int COMBAT_XP_RATE = 115;
	public static final int SKILLING_XP_RATE = 115;
	public static final int DROP_RATE = 1;
	public static final double ERADICATOR_DROP_RATE = 1.3;	
	public static final double ROW_DROP_RATE = 1.1;		
	public static final double IRONMAN_DROP_RATE = 1.2;
	public static final double TENBILL_DROP_RATE = 1.35;

	/**
	 * World settings
	 */
	public static final int WORLD_CYCLE_TIME = 525; // the speed of world in ms

	/**
	 * Music & Emote settings
	 */
	public static final int AIR_GUITAR_MUSICS_COUNT = 10;

	/**
	 * Quest settings
	 */
	public static final int QUESTS = 183;

	/**
	 * Memory settings
	 */
	public static final int PLAYERS_LIMIT = 2000;
	public static final int LOCAL_PLAYERS_LIMIT = 250;
	public static final int NPCS_LIMIT = Short.MAX_VALUE;
	public static final int LOCAL_NPCS_LIMIT = 250;
	public static final int MIN_FREE_MEM_ALLOWED = 30000000; // 30mb

	/**
	 * Game constants
	 */
	public static final int[] MAP_SIZES = { 104, 120, 136, 168, 72 };

	public static final String GRAB_SERVER_TOKEN = "hAJWGrsaETglRjuwxMwnlA/d5W6EgYWx";
	public static final int[] GRAB_SERVER_KEYS = { 1441, 78700, 44880, 39771,
			363186, 44375, 0, 16140, 7316, 271148, 810710, 216189, 379672,
			454149, 933950, 21006, 25367, 17247, 1244, 1, 14856, 1494, 119,
			882901, 1818764, 3963, 3618 };

	// an exeption(grab server has his own keyset unlike rest of client)
	public static final BigInteger GRAB_SERVER_PRIVATE_EXPONENT = new BigInteger("95776340111155337321344029627634178888626101791582245228586750697996713454019354716577077577558156976177994479837760989691356438974879647293064177555518187567327659793331431421153203931914933858526857396428052266926507860603166705084302845740310178306001400777670591958466653637275131498866778592148380588481");
	public static final BigInteger GRAB_SERVER_MODULUS = new BigInteger("119555331260995530494627322191654816613155476612603817103079689925995402263457895890829148093414135342420807287820032417458428763496565605970163936696811485500553506743979521465489801746973392901885588777462023165252483988431877411021816445058706597607453280166045122971960003629860919338852061972113876035333");

	public static final BigInteger PRIVATE_EXPONENT = new BigInteger("90587072701551327129007891668787349509347630408215045082807628285770049664232156776755654198505412956586289981306433146503308411067358680117206732091608088418458220580479081111360656446804397560752455367862620370537461050334224448167071367743407184852057833323917170323302797356352672118595769338616589092625");
	public static final BigInteger MODULUS = new BigInteger("102876637271116124732338500663639643113504464789339249490399312659674772039314875904176809267475033772367707882873773291786014475222178654932442254125731622781524413208523465520758537060408541610254619166907142593731337618490879831401461945679478046811438574041131738117063340726565226753787565780501845348613");
	public static boolean SQUEAL_OF_FORTUNE_ENABLED = true; // if not, people will be able to spin but not claim
    
    // sof chances:
    // 100% for common (It's a must to have 100% for common due to at least one
    // reward must be picked)
    // 35% for uncommon
    // 5% for rare (0.08% was originally)
    // 0.01% for jackpot
    public static final double[] SOF_CHANCES = new double[] { 1.0D, 0.35D, 0.05D, 0.001D };
    public static final int[] SOF_COMMON_CASH_AMOUNTS = new int[] { 100, 250, 500, 1000, 5000 };
    public static final int[] SOF_UNCOMMON_CASH_AMOUNTS = new int[] { 10000, 25000, 50000, 100000, 500000 };
    public static final int[] SOF_RARE_CASH_AMOUNTS = new int[] { 1000000, 2500000, 5000000, 10000000, 50000000 };
    public static final int[] SOF_JACKPOT_CASH_AMOUNTS = new int[] { 100 * 1000000, 250 * 1000000, 500 * 1000000, 1000 * 1000000 };
    public static final int[] SOF_UNCOMMON_LAMPS = new int[] { 23714, 23718, 23722, 23726, 23730, 23738, 23734, 23742, 23746, 23750, 23754, 23758, 23762, 23766, 23770, 23779, 23775, 23787, 23783, 23795, 23791, 23803, 23799, 23811, 23807, 23815 };
    public static final int[] SOF_RARE_LAMPS = new int[] { 23715, 23719, 23723, 23727, 23731, 23739, 23735, 23743, 23747, 23751, 23755, 23759, 23763, 23767, 23771, 23780, 23776, 23788, 23784, 23796, 23792, 23804, 23800, 23812, 23808, 23816 };
    public static final int[] SOF_JACKPOT_LAMPS = new int[] { 23716, 23720, 23724, 23728, 23732, 23740, 23736, 23744, 23748, 23752, 23756, 23760, 23764, 23768, 23773, 23781, 23777, 23789, 23785, 23797, 23793, 23805, 23801, 23813, 23809, 23817 };
    public static final int[] SOF_COMMON_OTHERS = new int[] { 1965, 1925, 618, 1955, 1514, 15271, 52, 2364, 18831, 8779, 26743, 15273, 8779, 15273, 2002 };
    public static final int[] SOF_UNCOMMON_OTHERS = new int[] { 24154, 24154, 24155 };
    public static final int[] SOF_RARE_OTHERS = new int[] { 995, 995, 995, 995, 23665, 23666, 23667, 23668, 23669, 23670, 23671, 23672, 23673, 23674, 23675, 23676, 23677, 23678, 23679, 23680, 23681, 23682, 23691, 23692, 23693, 23694, 23695, 23696, 23687, 23688, 23689, 23684, 23686, 23685, 23697, 23690, 23699, 23700, 23683, 23698, 27370, 27371, 27372, 26743 };
    public static final int[] SOF_JACKPOT_OTHERS = new int[] { 995, 995, 995, 995, 18349, 18351, 18353, 18355, 18333, 18335, 21777, 22494, 23659, 27359, 27358, 27356 };
    
	private Settings() {

	}
}
