package com.rs.game.npc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;






import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.ForceTalk;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.custom.BossHighlight;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.combat.NPCCombat;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Player;
import com.rs.game.npc.others.LegendsNPCs;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.QNames;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.player.Skills;
import com.rs.utils.Logger;
import com.rs.utils.MapAreas;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

public class NPC extends Entity implements Serializable {

    private static final long serialVersionUID = -4794678936277614443L;
    public int id;
    private WorldTile respawnTile;
    private int mapAreaNameHash;
    private boolean canBeAttackFromOutOfArea;
    private boolean randomwalk;
    private int[] bonuses; // 0 stab, 1 slash, 2 crush,3 mage, 4 range, 5 stab
    // def, blahblah till 9
    private boolean spawned;
    public transient NPCCombat combat;
    public WorldTile forceWalk;
    private long lastAttackedByTarget;
    private boolean cantInteract;
    private int capDamage;
    private int lureDelay;
    private boolean cantFollowUnderCombat;
    private boolean forceAgressive;
    private int forceTargetDistance;
    private boolean forceFollowClose;
    private boolean forceMultiAttacked;
    private boolean noDistanceCheck;
	private int eradicationxShops[] = { 13929, 659, 11270, 11276, 15100, 14330, 3380, 6539, 3299, 3709, 14260,
			 6893, 12, 537, 6537, 8029, 8030, 8031, 8032, 8008, 8035, 8036, 8037, 8038, 8039, 8040, 1918, 524, 529, 2617, 
			14854, 4288, 6892, 13768, 3820, 538, 587, 5112, 15001, 7935, 11674, 1, 1699, 520, 1282, 1821, 15251, 15019, 
			2259, 552, 11678, 6070, 554, 551, 534, 456, 654, 519, 8864, 585, 1597, 548, 1167, 530, 13995, 11583, 13789, 
			7836, 528, 457, 576, 4247, 546, 549, 13727, 550, 9085, 15501, 14, 15102};
    private transient Transformation nextTransformation;
    private String name;
    private transient boolean changedName;
    private int combatLevel;
    private transient boolean changedCombatLevel;
    private transient boolean locked;

    public NPC(int id, WorldTile tile, int mapAreaNameHash,
            boolean canBeAttackFromOutOfArea) {
        this(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
    }


    public NPC(int id, WorldTile tile, int mapAreaNameHash,
            boolean canBeAttackFromOutOfArea, boolean spawned) {
        super(tile);
        this.id = id;
        this.respawnTile = new WorldTile(tile);
        this.mapAreaNameHash = mapAreaNameHash;
        this.canBeAttackFromOutOfArea = canBeAttackFromOutOfArea;
        this.setSpawned(spawned);
        combatLevel = -1;
        setHitpoints(getMaxHitpoints());
        setDirection(getRespawnDirection());
        setRandomWalk((getDefinitions().walkMask & 0x2) != 0 || false);
        for (int containsId: eradicationxShops) {
        	if (containsId == id) {
        		setRandomWalk(false);
        		setCombatLevel(0);
        	}
        }
        bonuses = NPCBonuses.getBonuses(id);
        combat = new NPCCombat(this);
        capDamage = -1;
        lureDelay = 12000;
        initEntity();
        World.addNPC(this);
        World.updateEntityRegion(this);
        loadMapRegions();
        checkMultiArea();
    }

    @Override
    public boolean needMasksUpdate() {
        return super.needMasksUpdate() || nextTransformation != null
                || changedCombatLevel || changedName;
    }

    public void transformIntoNPC(int id) {
        setNPC(id);
        nextTransformation = new Transformation(id);
    }

    public void setNPC(int id) {
        this.id = id;
        bonuses = NPCBonuses.getBonuses(id);
    }

    @Override
    public void resetMasks() {
        super.resetMasks();
        nextTransformation = null;
        changedCombatLevel = false;
        changedName = false;
    }

    public int getMapAreaNameHash() {
        return mapAreaNameHash;
    }

    public void setCanBeAttackFromOutOfArea(boolean b) {
        canBeAttackFromOutOfArea = b;
    }

    public boolean canBeAttackFromOutOfArea() {
        return canBeAttackFromOutOfArea;
    }

    public NPCDefinitions getDefinitions() {
        return NPCDefinitions.getNPCDefinitions(id);
    }

    public NPCCombatDefinitions getCombatDefinitions() {
        return NPCCombatDefinitionsL.getNPCCombatDefinitions(id);
    }

    @Override
    public int getMaxHitpoints() {
        return getCombatDefinitions().getHitpoints();
    }

    public int getId() {
        return id;
    }

    public void processNPC() {
        if (isDead() || locked) 
            return;
        if (!combat.process()) { // if not under combat
            if (!isForceWalking()) {
                if (!cantInteract) {
                    if (!checkAgressivity()) {
                        if (getFreezeDelay() < Utils.currentTimeMillis()) {
                            if (((hasRandomWalk()) && World.getRotation(
                                    getPlane(), getX(), getY()) == 0) // temporary
                                    // fix
                                    && Math.random() * 1000.0 < 100.0) {
                                int moveX = (int) Math
                                        .round(Math.random() * 10.0 - 5.0);
                                int moveY = (int) Math
                                        .round(Math.random() * 10.0 - 5.0);
                                resetWalkSteps();
                                if (getMapAreaNameHash() != -1) {
                                    if (!MapAreas.isAtArea(
                                            getMapAreaNameHash(), this)) {
                                        forceWalkRespawnTile();
                                        return;
                                    }
                                    addWalkSteps(getX() + moveX,
                                            getY() + moveY, 5);
                                } else {
                                    addWalkSteps(respawnTile.getX() + moveX,
                                            respawnTile.getY() + moveY, 5);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isForceWalking()) {
            if (getFreezeDelay() < Utils.currentTimeMillis()) {
                if (getX() != forceWalk.getX() || getY() != forceWalk.getY()) {
                    if (!hasWalkSteps()) {
                        addWalkSteps(forceWalk.getX(), forceWalk.getY(),
                                getSize(), true);
                    }
                    if (!hasWalkSteps()) { // failing finding route
                        setNextWorldTile(new WorldTile(forceWalk)); // force
                        forceWalk = null; // so ofc reached forcewalk place
                    }
                } else {
                    forceWalk = null;
                }
            }
        }
		if (id == 14413) {
			setName("Super Dragon");
		}
		if (id == 1900)
			setCombatLevel(138);
		if (id == 1895)
			setCombatLevel(138);
		if (id == 5080)
			setRandomWalk(true);
        if (id == 6970) {
            setRandomWalk(false);
			setName("<shad=00000f>Summoning Ingredients");
			setCombatLevel(0);
        }
        if (id == 15974)
        	setCombatLevel(0);
        if (id == 15973)
        	setCombatLevel(0);
        if (id == 15971) {
        	setRandomWalk(false);
        	setCombatLevel(0);
        }
        if (id == 15972)
        	setRandomWalk(false);
        if (id == 15101)
        	setCombatLevel(0);
        if (id == 14330) {
			setRandomWalk(false);
        }
        if (id == 15786) {
        	setRandomWalk(false);
        	setCombatLevel(0);
        }
        if (id == 3380) {
			setName("Cosmetics Manager");
        }        
        if (id == 6539) {
			setName("Vote Shop");
        }
        if (id == 3299) {
			setName("Farming Manager");
        }		
        if (id == 3709) {
			setName("PKing Teleports");
        }				
        if (id == 8591) {
			setNextForceTalk(new ForceTalk("Click the Green Portal to start fighting me!"));
        }	
        if (id == 14260) {
			setForceMultiAttacked(true);
			setName("<shad=00000f>Maximum Gradum (Low Leveled)");
        }		
		if (id == 8029) {
			setName("<shad=00000f>Saradomin");
			setNextForceTalk(new ForceTalk("Saradomin Teleport"));			
		}
		if (id == 8030) {
			setName("<shad=00000f>Zamorak");
			setNextForceTalk(new ForceTalk("Zamorak Teleport"));			
		}			
		if (id == 8031) {
			setName("<shad=00000f>Armadyl");
			setNextForceTalk(new ForceTalk("Armadyl Teleport"));			
		}			
		if (id == 8032) {
			setName("<shad=00000f>Bandos");
			setNextForceTalk(new ForceTalk("Bandos Teleport"));			
		}			
		if (id == 8033) {
			setName("<shad=00000f>Blink");
			setNextForceTalk(new ForceTalk("Blink Teleport"));			
		}			
		if (id == 8008) {
			setName("<shad=00000f>Eradicator");
			setNextForceTalk(new ForceTalk("Eradicator Teleport"));			
		}			
		if (id == 8035) {
			setName("<shad=00000f>Corp");
			setNextForceTalk(new ForceTalk("Corp Beast Teleport"));			
		}			
		if (id == 8036) {
			setName("<shad=00000f>KBD");
			setNextForceTalk(new ForceTalk("KBD Teleport"));			
		}			
		if (id == 8037) {
			setName("<shad=00000f>Maximum Gradum");
			setNextForceTalk(new ForceTalk("Maximum Gradum Teleport"));			
		}			
		if (id == 8038) {
			setName("<shad=00000f>Necrolord");
			setNextForceTalk(new ForceTalk("Necrolord Teleport"));			
		}			
		if (id == 8039) {
			setName("<shad=00000f>Regular Boss");
			setNextForceTalk(new ForceTalk("Regular Boss Teleport"));			
		}		
		if (id == 8040) {
			setName("<shad=00000f> Extreme Boss");
			setNextForceTalk(new ForceTalk("Extreme Boss Teleport"));			
		}	
		if (id == 12)
			setCombatLevel(0);
		if (id == 6893)
			setCombatLevel(0);
        if (id == 529) {
			setName("General Shop");
        }
        if (id == 14854) {
        	setName("Donator Potions");
        }
        if (id == 4288) {
			setName("PKing Shop");
        }
        if (id == 550) {
			setName("Ranged Shop");
        }
        if (id == 13768) {
			setName("Eradicated Seals and Character");
        }		
        if (id == 538) {
			setName("Weapons Shop");	
        }
        if (id == 587) {
			setName("Herblore");
        }
        if (id == 5112) {
			setName("Hunting and Crafting");
        }
        if (id == 15501) {
			setName("<col=fff000>100M Ticket Trader");
        }	
        if (id == 7935) {
			setName("<col=fff000>Reset Defence");
        }		 
		if (id == 1) {
			setName("Training Househusband");
		}
        if (id == 520) {
			setName("Smithing Shop");
        }
        if (id == 1282) {
			setName("Invasion Token Shop");
        }	      
		if (id == 1821) {
			setName("<col=0ffAff>Extreme Shop 1");
        }
        if (id == 15251) {
			setName("<col=0EDABC>Extreme Shop 2");
        }			
		if (id == 15019) {
			setNextForceTalk(new ForceTalk("Kill... Dagrith, you'll... find him west of here."));
        }		
        if (id == 551) {
			setName("Recolored Shop");
        }
        if (id == 456) {
			setName("Prayers & SpellBooks");			
        }
        if (id == 1840) {
			setName("Construction Supplies");
			setRandomWalk(false);
        }				
        if (id == 519) {
			setName("Woodcutting and Mining");
        }
        if (id == 8864) {
			setName("Fishing & Food");
        }				
        if (id == 530) {
			setName("Summoning Shop");
        }		
        if (id == 13955) {
			setRandomWalk(false);
			setName("The Eradicator");
		}
        if (id == 4279) {
			setRandomWalk(false);
		}      
        if (id == 11583) {
			setName("<img=9><col=2D90E0>Man");
		}        
        
        if (id == 13789) {
			setName("Tokkul Shop");
		}            
        
		if (id == 7836) {
			setName("Sell Shop");
		}
        if (id == 457)// Pure shop
        {
			setName("Pure Shop");
        }
        if (id == 576)// Pure shop	
        {
			setName("Potions Shop");			
        }
        if (id == 4247)// Pure shop
        {
			setName("Barrows Shop");			
        }
        if (id == 546)// Pure shop
        {
			setName("Magic Shop");
        }
        if (id == 549)// Pure shop
        {
			setName("Armour Shop");			
        }
		if (id == 13727)
		{
			setName ("Loyalty Shop");
		}	
		if (id == 3777) {
			setName("End of the World");			
        }
    }

    @Override
    public void processEntity() {
        super.processEntity();
        processNPC();
    }

    public int getRespawnDirection() {
        NPCDefinitions definitions = getDefinitions();
        if (definitions.getId() == 15102)
        	return 1;
        if (definitions.anInt853 << 32 != 0 && definitions.respawnDirection > 0
                && definitions.respawnDirection <= 8) {
            return (4 + definitions.respawnDirection) << 11;
        }
        return 0;
    }


    public void sendSoulSplit(final Hit hit, final Entity user) {
        final NPC target = this;
        if (hit.getDamage() > 0) {
            World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
        }
        user.heal(hit.getDamage() / 5);
        WorldTasksManager.schedule(new WorldTask() {
            @Override
            public void run() {
                setNextGraphics(new Graphics(2264));
                if (hit.getDamage() > 0) {
                    World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0,
                            0);
                }
            }
        }, 1);
    }

    @Override
    public void handleIngoingHit(final Hit hit) {
        if (capDamage != -1 && hit.getDamage() > capDamage) {
            hit.setDamage(capDamage);
        }
        if (hit.getLook() != HitLook.MELEE_DAMAGE
                && hit.getLook() != HitLook.RANGE_DAMAGE
                && hit.getLook() != HitLook.MAGIC_DAMAGE) {
            return;
        }
        Entity source = hit.getSource();
        if (source == null) {
            return;
        }
        if (source instanceof Player) {
            final Player p2 = (Player) source;
            if (p2.getPrayer().hasPrayersOn()) {
                if (p2.getPrayer().usingPrayer(1, 18)) {
                    sendSoulSplit(hit, p2);
                }
                if (hit.getDamage() == 0) {
                    return;
                }
                if (!p2.getPrayer().isBoostedLeech()) {
                    if (hit.getLook() == HitLook.MELEE_DAMAGE) {
                        if (p2.getPrayer().usingPrayer(1, 19)) {
                            p2.getPrayer().setBoostedLeech(true);
                            return;
                        } else if (p2.getPrayer().usingPrayer(1, 1)) { // sap
                            // att
                            if (Utils.getRandom(4) == 0) {
                                if (p2.getPrayer().reachedMax(0)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your sap curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(0);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Attack from the enemy, boosting your Attack.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12569));
                                p2.setNextGraphics(new Graphics(2214));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2215, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2216));
                                    }
                                }, 1);
                                return;
                            }
                        } else {
                            if (p2.getPrayer().usingPrayer(1, 10)) {
                                if (Utils.getRandom(7) == 0) {
                                    if (p2.getPrayer().reachedMax(3)) {
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your opponent has been weakened so much that your leech curse has no effect.",
                                                true);
                                    } else {
                                        p2.getPrayer().increaseLeechBonus(3);
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your curse drains Attack from the enemy, boosting your Attack.",
                                                true);
                                    }
                                    p2.setNextAnimation(new Animation(12575));
                                    p2.getPrayer().setBoostedLeech(true);
                                    World.sendProjectile(p2, this, 2231, 35,
                                            35, 20, 5, 0, 0);
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            setNextGraphics(new Graphics(2232));
                                        }
                                    }, 1);
                                    return;
                                }
                            }
                            if (p2.getPrayer().usingPrayer(1, 14)) {
                                if (Utils.getRandom(7) == 0) {
                                    if (p2.getPrayer().reachedMax(7)) {
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your opponent has been weakened so much that your leech curse has no effect.",
                                                true);
                                    } else {
                                        p2.getPrayer().increaseLeechBonus(7);
                                        p2.getPackets()
                                                .sendGameMessage(
                                                "Your curse drains Strength from the enemy, boosting your Strength.",
                                                true);
                                    }
                                    p2.setNextAnimation(new Animation(12575));
                                    p2.getPrayer().setBoostedLeech(true);
                                    World.sendProjectile(p2, this, 2248, 35,
                                            35, 20, 5, 0, 0);
                                    WorldTasksManager.schedule(new WorldTask() {
                                        @Override
                                        public void run() {
                                            setNextGraphics(new Graphics(2250));
                                        }
                                    }, 1);
                                    return;
                                }
                            }

                        }
                    }
                    if (hit.getLook() == HitLook.RANGE_DAMAGE) {
                        if (p2.getPrayer().usingPrayer(1, 2)) { // sap range
                            if (Utils.getRandom(4) == 0) {
                                if (p2.getPrayer().reachedMax(1)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your sap curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(1);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Range from the enemy, boosting your Range.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12569));
                                p2.setNextGraphics(new Graphics(2217));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2218, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2219));
                                    }
                                }, 1);
                                return;
                            }
                        } else if (p2.getPrayer().usingPrayer(1, 11)) {
                            if (Utils.getRandom(7) == 0) {
                                if (p2.getPrayer().reachedMax(4)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your leech curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(4);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Range from the enemy, boosting your Range.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12575));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2236, 35, 35,
                                        20, 5, 0, 0);
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
                        if (p2.getPrayer().usingPrayer(1, 3)) { // sap mage
                            if (Utils.getRandom(4) == 0) {
                                if (p2.getPrayer().reachedMax(2)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your sap curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(2);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Magic from the enemy, boosting your Magic.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12569));
                                p2.setNextGraphics(new Graphics(2220));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2221, 35, 35,
                                        20, 5, 0, 0);
                                WorldTasksManager.schedule(new WorldTask() {
                                    @Override
                                    public void run() {
                                        setNextGraphics(new Graphics(2222));
                                    }
                                }, 1);
                                return;
                            }
                        } else if (p2.getPrayer().usingPrayer(1, 12)) {
                            if (Utils.getRandom(7) == 0) {
                                if (p2.getPrayer().reachedMax(5)) {
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your opponent has been weakened so much that your leech curse has no effect.",
                                            true);
                                } else {
                                    p2.getPrayer().increaseLeechBonus(5);
                                    p2.getPackets()
                                            .sendGameMessage(
                                            "Your curse drains Magic from the enemy, boosting your Magic.",
                                            true);
                                }
                                p2.setNextAnimation(new Animation(12575));
                                p2.getPrayer().setBoostedLeech(true);
                                World.sendProjectile(p2, this, 2240, 35, 35,
                                        20, 5, 0, 0);
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

                    if (p2.getPrayer().usingPrayer(1, 13)) { // leech defence
                        if (Utils.getRandom(10) == 0) {
                            if (p2.getPrayer().reachedMax(6)) {
                                p2.getPackets()
                                        .sendGameMessage(
                                        "Your opponent has been weakened so much that your leech curse has no effect.",
                                        true);
                            } else {
                                p2.getPrayer().increaseLeechBonus(6);
                                p2.getPackets()
                                        .sendGameMessage(
                                        "Your curse drains Defence from the enemy, boosting your Defence.",
                                        true);
                            }
                            p2.setNextAnimation(new Animation(12575));
                            p2.getPrayer().setBoostedLeech(true);
                            World.sendProjectile(p2, this, 2244, 35, 35, 20, 5,
                                    0, 0);
                            WorldTasksManager.schedule(new WorldTask() {
                                @Override
                                public void run() {
                                    setNextGraphics(new Graphics(2246));
                                }
                            }, 1);
                            return;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void reset() {
        super.reset();
        setDirection(getRespawnDirection());
        combat.reset();
        bonuses = NPCBonuses.getBonuses(id);
        forceWalk = null;
    }

    @Override
    public void finish() {
        if (hasFinished()) {
            return;
        }
        setFinished(true);
        World.updateEntityRegion(this);
        World.removeNPC(this);
    }

    public void setRespawnTask() {
        if (!hasFinished()) {
            reset();
            setLocation(respawnTile);
            finish();
        }
        CoresManager.slowExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    spawn();
                } catch (Throwable e) {
                    Logger.handle(e);
                }
            }
        }, getCombatDefinitions().getRespawnDelay() * 600,
                TimeUnit.MILLISECONDS);
    }

    public void deserialize() {
        if (combat == null) {
            combat = new NPCCombat(this);
        }
        spawn();
    }

    public void spawn() {
        setFinished(false);
        World.addNPC(this);
        setLastRegionId(0);
        World.updateEntityRegion(this);
        loadMapRegions();
        checkMultiArea();
    }

    public NPCCombat getCombat() {
        return combat;
    }

    @Override
    public void sendDeath(Entity source) {
        final NPCCombatDefinitions defs = getCombatDefinitions();
        resetWalkSteps();
        combat.removeTarget();
        drop();
        setNextAnimation(null);
        WorldTasksManager.schedule(new WorldTask() {
            int loop;
            @Override
            public void run() {
                if (loop == 0) {
                    setNextAnimation(new Animation(defs.getDeathEmote()));
                } else if (loop >= defs.getDeathDelay()) {
                    reset();
                    setLocation(respawnTile);
                    finish();
                    if (!isSpawned()) {
                        setRespawnTask();
                    }
                    stop();
                }
                loop++;
            }
        }, 0, 1);
    }
    
    @Override
    public void sendDeathNoDrop(Entity source) {
        final NPCCombatDefinitions defs = getCombatDefinitions();
        resetWalkSteps();
        combat.removeTarget();
        setNextAnimation(null);
        WorldTasksManager.schedule(new WorldTask() {
            int loop;
            @Override
            public void run() {
                if (loop == 0) {
                    setNextAnimation(new Animation(defs.getDeathEmote()));
                } else if (loop >= defs.getDeathDelay()) {
                    reset();
                    setLocation(respawnTile);
                    finish();
                    stop();
                }
                loop++;
            }
        }, 0, 1);
    }


	

    
    public void drop() {
        try {
            Drop[] drops = NPCDrops.getDrops(id);
            if (drops == null) {
                return;
            }
            Player killer = getMostDamageReceivedSourcePlayer();
            if (killer == null) {
                return;
            }
			if (id == 15235) {	
			setNextForceTalk(new ForceTalk("I'll be back..."));
			for (Player players: getDamageReceivedSourcePlayers()) {
				players.sm("You receive invasion tokens for killing Dragith Nurn (Big).");
				players.setKilledDragithNurn();
				players.setSpellPower(players.getSpellPower() + 150);
				players.getCurrencyPouch().setInvasionTokens(players.getCurrencyPouch().getInvasionTokens() +  200);	
			}				
			}            
			if (id == 9622) {
			final WorldTile center = new WorldTile(this);	
	  		World.spawnNPC(15235, center, - 1, true, true); 		
    		setNextForceTalk(new ForceTalk("You think this is over!?"));	
			for (Player players: getDamageReceivedSourcePlayers()) {
				players.sm("You receive invasion tokens for killing Dragith Nurn.");
				players.setSpellPower(players.getSpellPower() + 75);
				players.getCurrencyPouch().setInvasionTokens(players.getCurrencyPouch().getInvasionTokens() +  200);		
			}				
			}
			if (id == 6260) {
				killer.setBandosKills(killer.getBandosKills() + 1);
				killer.sm(
                                    "You gain a Bandos kill count. You now have "
                                    + killer.getBandosKills()
                                    + " kills.");
				if (killer.getBandosKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getBandosKills() + " Bandos Bosses!</col>", false);
				if (killer.getBandosKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");		
				}		
			}
			if (id == 8133) {
				killer.setCorporealKills(killer.getCorporealKills() + 1);
				killer.sm(
                                    "You gain a Corporeal kill count. You now have "
                                    + killer.getCorporealKills()
                                    + " kills.");
				if (killer.getCorporealKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getCorporealKills() + " Corporeal Beasts!</col>", false);
				if (killer.getCorporealKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}			
			}
			if (id == 3334) {
				killer.setWyrmKills(killer.getWyrmKills() + 1);
				killer.sm(
                                    "You gain a Wyrm kill count. You now have "
                                    + killer.getWyrmKills()
                                    + " kills.");
				if (killer.getWyrmKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getWyrmKills() + " Wildywyrm Beasts!</col>", false);
				if (killer.getWyrmKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}				
			}			
			
			if (id == 15208) {
				killer.setJadKills(killer.getJadKills() + 1);
				killer.sm(
                                    "You gain a Jad kill count. You now have "
                                    + killer.getJadKills()
                                    + " kills.");
				if (killer.getJadKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getJadKills() + " Jad Bosses!</col>", false);
				if (killer.getJadKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}
			}
			if (id == 8596) {
				killer.setAvatarKills(killer.getAvatarKills() + 1);
				killer.sm(
                                    "You gain a Avatar kill count. You now have "
                                    + killer.getAvatarKills()
                                    + " kills.");
				if (killer.getAvatarKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getAvatarKills() + " Avatar Bosses!</col>", false);
				if (killer.getAvatarKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
					
				}			
			}
			if (id == 11751) {
				killer.setNecrolordKills(killer.getNecrolordKills() + 1);
				killer.sm(
                                    "You gain a Necrolord kill count. You now have "
                                    + killer.getNecrolordKills()
                                    + " kills.");
				if (killer.getNecrolordKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getNecrolordKills() + " Necrolord Bosses!</col>", false);
				if (killer.getNecrolordKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}	
			}				
			if (id == 1895) {
				killer.setObsidianKingKills(killer.getObsidianKingKills() + 1);
				killer.sm(
                                    "You gain a Obsidian King kill count. You now have "
                                    + killer.getObsidianKingKills()
                                    + " kills.");
				if (killer.getObsidianKingKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getObsidianKingKills() + " Obsidian King Bosses!</col>", false);
				if (killer.getObsidianKingKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
				
				}
			}			
			if (id == 6247) {
				killer.setSaradominKills(killer.getSaradominKills() + 1);
				killer.sm(
                                    "You gain a Saradomin kill count. You now have "
                                    + killer.getSaradominKills()
                                    + " kills.");
				if (killer.getSaradominKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getSaradominKills() + " Saradomin Bosses!</col>", false);
				if (killer.getSaradominKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}
			}
			if (id == 13448 || id == 13450 || id == 13447 || id == 13449 || id == 18880 || id == 15776) {
				killer.setNexKills(killer.getNexKills() + 1);
				killer.sm(
                                    "You gain a Nex kill count. You now have "
                                    + killer.getNexKills()
                                    + " kills.");
				if (killer.getNexKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getNexKills() + " Nex Bosses!</col>", false);
				if (killer.getNexKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}		
			}
			if (id == 15222) {
				killer.setSunfreetKills(killer.getSunfreetKills() + 1);
				killer.sm(
                                    "You gain a Sunfreet kill count. You now have "
                                    + killer.getSunfreetKills()
                                    + " kills.");
				if (killer.getSunfreetKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getSunfreetKills() + " Sunfreet Bosses!</col>", false);
				if (killer.getSunfreetKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}
			}			
			if (id == 6203) {
				killer.setZamorakKills(killer.getZamorakKills() + 1);
				killer.sm(
                                    "You gain a Zamorak kill count. You now have "
                                    + killer.getZamorakKills()
                                    + " kills.");
				if (killer.getZamorakKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getZamorakKills() + " Zamorak Bosses!</col>", false);
				if (killer.getZamorakKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}
			}
			if (id == 6222) {
				killer.setArmadylKills(killer.getArmadylKills() + 1);
				killer.sm(
                                    "You gain an Armadyl kill count. You now have "
                                    + killer.getArmadylKills()
                                    + " kills.");
				if (killer.getArmadylKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getArmadylKills() + " Armadyl Bosses!</col>", false);
				if (killer.getArmadylKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}
					
			}			
			if (id == 1900) {
				killer.setCopyrightKills(killer.getCopyrightKills() + 1);
				killer.sm(
                                    "You gain a Copyright kill count. You now have "
                                    + killer.getCopyrightKills()
                                    + " kills.");
				if (killer.getCopyrightKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getCopyrightKills() + " Copyright Bosses!</col>", false);
				if (killer.getCopyrightKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
						
				}	
			}			
			if (id == 15003) {
				killer.setFatalKills(killer.getFatalKills() + 1);
				killer.sm(
                                    "You gain a Fatal Resort kill count. You now have "
                                    + killer.getFatalKills()
                                    + " kills.");
				if (killer.getFatalKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getFatalKills() + " Fatal Resort Bosses!</col>", false);
				if (killer.getFatalKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");				
					
				}		
			}
			if (id == 15002) {
				killer.setSomethingKills(killer.getSomethingKills() + 1);
				killer.sm(
                                    "You gain a Something kill count. You now have "
                                    + killer.getSomethingKills()
                                    + " kills.");
				if (killer.getSomethingKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getSomethingKills() + " Something Bosses!</col>", false);
				if (killer.getSomethingKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");			
				
				}
			}
			if (id == 11872) {
				killer.setEradicatorBossKills(killer.getEradicatorBossKills() + 1);
				killer.sm(
                                    "You gain an Eradicator kill count. You now have "
                                    + killer.getEradicatorBossKills()
                                    + " kills.");
				if (killer.getEradicatorBossKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getEradicatorBossKills() + " Eradicator Bosses!</col>", false);
				if (killer.getEradicatorBossKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");
					
				}		
			}
			if (id == 15006) {
				killer.setGenoKills(killer.getGenoKills() + 1);
				killer.sm(
                                    "You gain a Deathlotus Ninja kill count. You now have "
                                    + killer.getGenoKills()
                                    + " kills.");
				if (killer.getGenoKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getGenoKills() + " Deathlotus Ninja Bosses!</col>", false);
				if (killer.getGenoKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");				
					
				}						
			}
			if (id == 15009) {
				killer.setRajjKills(killer.getRajjKills() + 1);
				killer.sm(
                                    "You gain a Seasinger Mage kill count. You now have "
                                    + killer.getRajjKills()
                                    + " kills.");
				if (killer.getRajjKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getRajjKills() + " Seasinger Mage Bosses!</col>", false);
				if (killer.getRajjKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");			
					
				}				
			}
			if (id == 15172) {
				killer.setFearKills(killer.getFearKills() + 1);
				killer.sm(
                                    "You gain a Fear kill count. You now have "
                                    + killer.getFearKills()
                                    + " kills.");
				if (killer.getFearKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getFearKills() + " Fear Bosses!</col>", false);
				if (killer.getFearKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");			
					
				}			
			}
			if (id == 12878) {
				killer.setBlinkKills(killer.getBlinkKills() + 1);
				killer.sm(
                                    "You gain a Blink kill count. You now have "
                                    + killer.getBlinkKills()
                                    + " kills.");
				if (killer.getBlinkKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getBlinkKills() + " Blink Bosses!</col>", false);
				if (killer.getBlinkKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");				
					
				}			
			}		
			if (id == 5561) {
				killer.setExtremeBossKills(killer.getExtremeBossKills() + 1);
				killer.sm(
                                    "You gain an Extreme Boss kill count. You now have "
                                    + killer.getExtremeBossKills()
                                    + " kills.");
				if (killer.getExtremeBossKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getExtremeBossKills() + " Warped Gulega Bosses!</col>", false);
				if (killer.getExtremeBossKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");			
					
				}			
			}
			if (id == 14246) {
				killer.setRegularBossKills(killer.getRegularBossKills() + 1);
				killer.sm(
                                    "You gain a Regular Boss kill count. You now have "
                                    + killer.getRegularBossKills()
                                    + " kills.");
				if (killer.getRegularBossKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getRegularBossKills() + " General Khazard Bosses!</col>", false);
				if (killer.getRegularBossKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");			
					
				}		
			}
			if (id == 14260) {
				killer.setGradumKills(killer.getGradumKills() + 1);
				killer.sm(
                                    "You gain a Maximum Gradum kill count. You now have "
                                    + killer.getGradumKills()
                                    + " kills.");
				if (killer.getGradumKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getGradumKills() + " Maximum Gradum Bosses!</col>", false);
				if (killer.getGradumKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");			
					
				}	
			}	
			if (id == 11) {
				killer.setWildyBossKills(killer.getWildyBossKills() + 1);
				killer.sm(
                                    "You gain a Wilderness Boss kill count. You now have "
                                    + killer.getWildyBossKills()
                                    + " kills.");
				if (killer.getWildyBossKills() % 100 == 0) {
				
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getWildyBossKills() + " Wilderness Bosses!</col>", false);
				if (killer.getWildyBossKills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");				
					
				}				
			}
			if (id == 15961) {
				killer.setHairymonkeykills(killer.getHairymonkeykills() + 1);
				killer.sm(
                                    "You gain a Hairymonkey kill count. You now have "
                                    + killer.getHairymonkeykills()
                                    + " kills.");
				if (killer.getHairymonkeykills() % 100 == 0) {
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + killer.getDisplayName() + " </col> has killed colorhere"+ killer.getHairymonkeykills() + " Hairymonkey Bosses!</col>", false);
				if (killer.getHairymonkeykills() == 100) killer.sm("<col=32CF13>You can now access a title by the KC Titles button in the EX Tab.</col>");					
				}	
			}
			int spower = 0;
			int[][] spellpower = {{6260, 3}, {6247, 3}, {6203, 3}, {6222, 3}, {1900, 6}, {15002, 4}, {15003, 4}, {15009, 4}, {15006, 3}, {14260, 1}, {11872, 4}, {15172, 4}, {11751, 1}, 
									{8133, 5}, {3334, 4}, {8596, 3}, {3847, 1}, {15961, 6}, {11, 6}, {12878, 5}, {15222, 5}, {5561, 5}, {14246, 4}, {15208, 4}, {1895, 4}, {50, 1}, {6, 4}};
			if (this.getName().equals("Nex")) {
				for (Player players: getDamageReceivedSourcePlayers()) {
					if (players.equals(killer)) 
						continue;
					players.sm("You receive a split of spell power from nex.");
					players.setSpellPower(killer.getSpellPower()+3);
				}			
				killer.setSpellPower(killer.getSpellPower()+20);
			}
		
			if (this.getId() == 14072) {
				for (Player players: getDamageReceivedSourcePlayers()) {
					if (players.equals(killer)) 
						continue;
					players.sm("You receive a split of spell power from nex.");
					players.setSpellPower(killer.getSpellPower()+5);
				}			
			}
			for (int i11 = 0; i11 < spellpower.length; i11++) {
				if (getId() == spellpower[i11][0]) {
					spower = spellpower[i11][1];
					if (BossHighlight.getInstance().getBoss() == getId())
					spower = (int) (spower * 1.5);
				}
			}
            if (killer.getBossTask() != null) {
                if (killer.getBossTask().getNPCId() == id) {
                    killer.getSkills().addXp(Skills.SLAYER, killer.getBossTask().getXPAmount());
                    killer.getBossTask().decreaseAmount();
                    spower = (int) (spower * 1.5);
                    if (killer.getBossTask().getTaskAmount() < 1) {
                    		if (!killer.getBossTask().isTripled()) {
                            killer.setBossSlayerPoints(killer.getBossSlayerPoints() + 20);
                            killer.sm("You have finished your slayer task, talk to Grim Reaper for a new task.");
                            killer.sm("Grim Reaper rewarded you 20 Boss Slayer Points. You now have "
                                    + killer.getBossSlayerPoints()
                                    + " Boss Slayer Points.");
                    		} else {
                                killer.setBossSlayerPoints(killer.getBossSlayerPoints() + 60);
                                killer.sm("You have finished your slayer task, talk to Grim Reaper for a new task.");
                                killer.sm("Grim Reaper rewarded you 60 Boss Slayer Points. You now have "
                                        + killer.getBossSlayerPoints()
                                        + " Boss Slayer Points.");                    			
                    		}
                    		killer.setBossSlayerCount(killer.getBossSlayerCount(false) + 1);
                    		killer.sm("You now have "+ killer.getBossSlayerCount(false) +" boss slayer tasks completed.");
                            killer.setBossTask(null);
                    } else {
                    killer.getBossTask().setAmountKilled(killer.getBossTask().getAmountKilled() + 1);
                    killer.sm("You need to defeat "+ killer.getBossTask().getTaskAmount() + " more "
                            + killer.getBossTask().getName().toLowerCase() + " to complete your task.");
                    }
                    }
            }		
			killer.setSpellPower(killer.getSpellPower() + spower);
            if (killer.getTask() != null) {
                if (getDefinitions().name.toLowerCase().equalsIgnoreCase(
                        killer.getTask().getName().toLowerCase())) {
                    killer.getSkills().addXp(Skills.SLAYER,
                            killer.getTask().getXPAmount());
                    killer.getTask().decreaseAmount();
                    if (killer.getTask().getTaskAmount() < 1) {
                        if (killer.getEquipment().getRingId() == 13281) {
                            killer.setSlayerPoints(killer.getSlayerPoints() + 40);
                            killer.getPackets()
                                    .sendGameMessage(
                                    "You have finished your slayer task, talk to Kuradal for a new task.");
                            killer.sm(
                                    "Kuradal rewarded you 40 Slayer Points! You now have "
                                    + killer.getSlayerPoints()
                                    + " Slayer Points.");
                        } else {
                            killer.setSlayerPoints(killer.getSlayerPoints() + 20);
                            killer.getPackets()
                                    .sendGameMessage(
                                    "You have finished your slayer task, talk to Kuradal for a new task.");
                            killer.sm(
                                    "Kuradal rewarded you 20 slayerPoints! You now have "
                                    + killer.getSlayerPoints()
                                    + " slayerPoints.");
                        }
                		killer.setSlayerCount(killer.getSlayerCount(false) + 1);
                		killer.sm("You now have "+ killer.getSlayerCount(false) +" slayer tasks completed.");
                        killer.setTask(null);
                        return;
                    }
                    killer.getTask().setAmountKilled(
                            killer.getTask().getAmountKilled() + 1);
                    killer.sm(
                            "You need to defeat "
                            + killer.getTask().getTaskAmount() + " "
                            + killer.getTask().getName().toLowerCase()
                            + " to complete your task.");
                }
            }
            EliteChapterOne ec1 = (EliteChapterOne) killer.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
            ec1.refresh(killer);
            if ((!ec1.isDidBones() || ec1.getQuestStage() == 6) && !ec1.isComplete()) {
            	if (id == 15003 && !killer.checkForItem(27735)) 
            		if (Utils.getRandom(8) == 4)
            			sendDrop(killer, new Drop(27735, 0,1,1,false));
            		else
            			killer.sm("No luck this time, I should try killing more for the bones.");
            	if (id == 15002 && !killer.checkForItem(27736))
            		if (Utils.getRandom(8) == 4)
            			sendDrop(killer, new Drop(27736, 0,1,1,false));
        			else
        				killer.sm("No luck this time, I should try killing more for the bones.");
            	if (id == 1900 && !killer.checkForItem(27737))
            		if (Utils.getRandom(8) == 4)
            			sendDrop(killer, new Drop(27737, 0,1,1,false));
        			else
        				killer.sm("No luck this time, I should try killing more for the bones.");
            }
        	if (noDrop()) return;				
            Drop[] possibleDrops = new Drop[drops.length];
            int possibleDropsCount = 0;
            for (Drop drop : drops) {        		               
    				Item item = ItemDefinitions.getItemDefinitions(drop.getItemId())
    						.isStackable() ? new Item(drop.getItemId(),
    						(drop.getMinAmount())
    								+ Utils.getRandom(drop.getExtraAmount())) : new Item(
    						drop.getItemId(), drop.getMinAmount()
    								+ Utils.getRandom(drop.getExtraAmount()));
    					if (killer.getInventory().containsItem(18337, 1) && item.getDefinitions().getName().toLowerCase().contains("bones")) {
    						killer.getSkills().addXp(Skills.PRAYER, Burying.Bone.forId(drop.getItemId()).getExperience());
    						continue;
    					}	
    					
                    if (drop.getRate() == 100) {
                        sendDrop(killer, drop);
                    } else {
                        if ((Utils.getRandomDouble(99) + 1) <= (drop.getRate() + killer.customDropRate + Settings.WorldDropRate) * getDropRate(killer, drop)) {
                            possibleDrops[possibleDropsCount++] = drop;
                        }
                    }            		
            	}
            if (possibleDropsCount > 0) {
                sendDrop(killer,
                        possibleDrops[Utils.getRandom(possibleDropsCount - 1)]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }
    
    public double getDropRate(Player player, Drop drop) {
    	if (drop.getRate() > 10.0)
    		return Settings.DROP_RATE;
    	int capeId = player.getEquipment().getCapeId();
    	int ringId = player.getEquipment().getRingId();
    	if (capeId == 27355 || capeId == 27344 || capeId == 27345
		|| capeId == 27346 || capeId == 27347 || capeId == 27348 || capeId == 27349
		|| capeId == 27350)
    		return Settings.TENBILL_DROP_RATE;
    	else if (player.isEradicator())
    		return Settings.ERADICATOR_DROP_RATE;
    	else if (player.isIronMan())
    		return Settings.IRONMAN_DROP_RATE;
    	else if (ringId == 2572)
    		return Settings.ROW_DROP_RATE;
		return Settings.DROP_RATE;
    }
    
    public void sendDropIncreasedChance(Player player) {
    	int capeId = player.getEquipment().getCapeId();
    	int ringId = player.getEquipment().getRingId();
    	if (capeId == 27355 || capeId == 27344 || capeId == 27345
		|| capeId == 27346 || capeId == 27347 || capeId == 27348 || capeId == 27349
		|| capeId == 27350)
    		player.sm("<col=348CC7> Your 10b XP Wings increased your drop chance.");
    	else if (player.isEradicator())
   		 player.sm("<img=18><col=348CC7> Your Eradicator Rank's power allows you to increase your drop chance.");
    	else if (player.isIronMan())
  		 player.sm("<img=23><col=348CC7> Your Ironman Rank's power allows you to increase your drop chance.");
    	else if (ringId == 2572)
    		player.sm("<col=F2A622>Your Ring of Wealth increased your drop chance.");
    		
    }
    
    public void sendLootBeam(Player player) {
    	int size = getSize();
    	switch (player.getSelectedbeam()) {
    	case 0:
    		  player.setSelectedbeam(1);
    		  sendLootBeam(player);
    	case 1:
       	  	  World.sendGraphics(player, new Graphics(383), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane())); 
       	  	  break;
    	case 2:
       	  	  World.sendGraphics(player, new Graphics(453), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
       	  	  World.sendGraphics(player, new Graphics(383), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane())); 
       	  	  break;
    	case 3:
       		  World.sendGraphics(player, new Graphics(531), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
       	  	  World.sendGraphics(player, new Graphics(453), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
       	  	  World.sendGraphics(player, new Graphics(383), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane())); 
       	  	  break;
    	case 4:
     		  World.sendGraphics(player, new Graphics(538), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
       		  World.sendGraphics(player, new Graphics(531), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
       	  	  World.sendGraphics(player, new Graphics(453), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
       	  	  World.sendGraphics(player, new Graphics(383), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane())); 
       	  	  break;
    	case 5:
	   		  World.sendGraphics(player, new Graphics(550), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
	   		  World.sendGraphics(player, new Graphics(538), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
	   		  World.sendGraphics(player, new Graphics(531), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
	   	  	  World.sendGraphics(player, new Graphics(453), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
	   	  	  World.sendGraphics(player, new Graphics(383), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));  	
       	  	  break;
    	case 6:
	    	  World.sendGraphics(player, new Graphics(574), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
			  World.sendGraphics(player, new Graphics(550), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
			  World.sendGraphics(player, new Graphics(538), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
			  World.sendGraphics(player, new Graphics(531), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
		  	  World.sendGraphics(player, new Graphics(453), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
		  	  World.sendGraphics(player, new Graphics(383), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));  		
       	  	  break;	
    	}
	}

    public void sendDrop(Player player, Drop drop) {
            int size = getSize();
    		String dropName = ItemDefinitions.getItemDefinitions(drop.getItemId())
    				.getName().toLowerCase();
            Item item = ItemDefinitions.getItemDefinitions(drop.getItemId()).isStackable() ? new Item(drop.getItemId(),
                    (drop.getMinAmount())  + Utils.getRandom(drop.getExtraAmount())) : new Item(
                    drop.getItemId(), drop.getMinAmount() + Utils.getRandom(drop.getExtraAmount()));
            if (player.hasLearnedAutoLootSeals() && item.getDefinitions().getName().toLowerCase().contains("eradicated seal")) {
            	player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() + item.getAmount());
			} else
            World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), player, null, false, 180, true);
           
            for (String itemName : Settings.RARE_DROPS) {   	
                if (dropName.contains(itemName.toLowerCase())) {  
                	sendDropIncreasedChance(player);
                	sendLootBeam(player);
                    if (player.getMessageIcon() != 0) {
                   	 World.sendWorldMessage("<img=5>[Drop Feed]<col=FF0000> <img=" + player.getMessageIcon() + ">"+ player.getDisplayName() + "</col> just recieved a <col=FF0000>"+ Utils.formatPlayerNameForDisplay(dropName) +"</col> drop!", false);            	 
                    } else {
                   	 World.sendWorldMessage("<img=5>[Drop Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just recieved a <col=FF0000>"+ Utils.formatPlayerNameForDisplay(dropName) +"</col> drop!", false);            	 
                    }
                }                 
            }    	
    	
    }

    @Override
    public int getSize() {
        return getDefinitions().size;
    }

    public int getMaxHit() {
        return getCombatDefinitions().getMaxHit();
    }

    public int[] getBonuses() {
        return bonuses;
    }

    @Override
    public double getMagePrayerMultiplier() {
    	if (id == 15208 || id == 14221 ||
    			id == 2734 || id == 2736 || id == 2739 || id == 2743 || id == 2741 || id == 2745)
    		return 0;
        return 0.2;
    }

    @Override
    public double getRangePrayerMultiplier() {
    	if (id == 15208 || id == 14221 ||
    			id == 2734 || id == 2736 || id == 2739 || id == 2743 || id == 2741 || id == 2745)
    		return 0;
        return 0.2;
    }

    @Override
    public double getMeleePrayerMultiplier() {
    	if (id == 15208 || id == 14221 ||
    			id == 2734 || id == 2736 || id == 2739 || id == 2743 || id == 2741 || id == 2745)
    		return 0;
        return 0.2;
    }

    public WorldTile getRespawnTile() {
        return respawnTile;
    }

    public boolean isUnderCombat() {
        return combat.underCombat();
    }

    @Override
    public void setAttackedBy(Entity target) {
        super.setAttackedBy(target);
        if (target == combat.getTarget()
                && !(combat.getTarget() instanceof Familiar)) {
            lastAttackedByTarget = Utils.currentTimeMillis();
        }
    }

    public boolean canBeAttackedByAutoRelatie() {
        return Utils.currentTimeMillis() - lastAttackedByTarget > lureDelay;
    }

    public boolean isForceWalking() {
        return forceWalk != null;
    }

    public void setTarget(Entity entity) {
        if (isForceWalking()) // if force walk not gonna get target
        {
            return;
        }
        combat.setTarget(entity);
        lastAttackedByTarget = Utils.currentTimeMillis();
    }

    public void removeTarget() {
        if (combat.getTarget() == null) {
            return;
        }
        combat.removeTarget();
    }

    public void forceWalkRespawnTile() {
        setForceWalk(respawnTile);
    }

    public void setForceWalk(WorldTile tile) {
        resetWalkSteps();
        forceWalk = tile;
    }

    public boolean hasForceWalk() {
        return forceWalk != null;
    }

    public ArrayList<Entity> getPossibleTargets() {
        ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
        for (int regionId : getMapRegionsIds()) {
            List<Integer> playerIndexes = World.getRegion(regionId)
                    .getPlayerIndexes();
            if (playerIndexes != null) {
                for (int playerIndex : playerIndexes) {
                    Player player = World.getPlayers().get(playerIndex);
                    if (player == null
                            || player.isDead()
                            || player.hasFinished()
                            || !player.isRunning()
                            || !player
                            .withinDistance(
                            this,
                            forceTargetDistance > 0 ? forceTargetDistance
                            : (getCombatDefinitions()
                            .getAttackStyle() == NPCCombatDefinitions.MELEE ? 4
                            : getCombatDefinitions()
                            .getAttackStyle() == NPCCombatDefinitions.SPECIAL ? 64
                            : 8))
                            || (!forceMultiAttacked
                            && (!isAtMultiArea() || !player
                            .isAtMultiArea())
                            && player.getAttackedBy() != this && (player
                            .getAttackedByDelay() > Utils
                            .currentTimeMillis() || player
                            .getFindTargetDelay() > Utils
                            .currentTimeMillis()))
                            || !clipedProjectile(player, false)
                            || (!forceAgressive && !Wilderness.isAtWild(this) && player
                            .getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2)) {
                        continue;
                    }
                    possibleTarget.add(player);
                }
            }
        }
        return possibleTarget;
    }

    public boolean checkAgressivity() {
        if (!forceAgressive) {
            NPCCombatDefinitions defs = getCombatDefinitions();
            if (defs.getAgressivenessType() == NPCCombatDefinitions.PASSIVE) {
                return false;
            }
        }
        ArrayList<Entity> possibleTarget = getPossibleTargets();
        if (!possibleTarget.isEmpty()) {
            Entity target = possibleTarget.get(Utils.random(possibleTarget.size()));
            setTarget(target);
            target.setAttackedBy(target);
            target.setFindTargetDelay(Utils.currentTimeMillis() + 10000);
            return true;
        }
        return false;
    }

    public boolean isCantInteract() {
        return cantInteract;
    }

    public void setCantInteract(boolean cantInteract) {
        this.cantInteract = cantInteract;
        if (cantInteract) {
            combat.reset();
        }
    }

    public int getCapDamage() {
        return capDamage;
    }

    public void setCapDamage(int capDamage) {
        this.capDamage = capDamage;
    }

    public int getLureDelay() {
        return lureDelay;
    }

    public void setLureDelay(int lureDelay) {
        this.lureDelay = lureDelay;
    }

    public boolean isCantFollowUnderCombat() {
        return cantFollowUnderCombat;
    }

    public void setCantFollowUnderCombat(boolean canFollowUnderCombat) {
        this.cantFollowUnderCombat = canFollowUnderCombat;
    }

    public Transformation getNextTransformation() {
        return nextTransformation;
    }

    @Override
    public String toString() {
        return getDefinitions().name + " - " + id + " - " + getX() + " " + getY() + " " + getPlane();
    }

    public boolean isForceAgressive() {
        return forceAgressive;
    }

    public void setForceAgressive(boolean forceAgressive) {
        this.forceAgressive = forceAgressive;
    }

    public int getForceTargetDistance() {
        return forceTargetDistance;
    }

    public void setForceTargetDistance(int forceTargetDistance) {
        this.forceTargetDistance = forceTargetDistance;
    }

    public boolean isForceFollowClose() {
        return forceFollowClose;
    }

    public void setForceFollowClose(boolean forceFollowClose) {
        this.forceFollowClose = forceFollowClose;
    }

    public boolean isForceMultiAttacked() {
        return forceMultiAttacked;
    }

    public void setForceMultiAttacked(boolean forceMultiAttacked) {
        this.forceMultiAttacked = forceMultiAttacked;
    }

    public boolean hasRandomWalk() {
        return randomwalk;
    }

    public void setRandomWalk(boolean forceRandomWalk) {
        this.randomwalk = forceRandomWalk;
    }

    public String getCustomName() {
        return name;
    }

    public void setName(String string) {
        this.name = getDefinitions().name.equals(string) ? null : string;
        changedName = true;
    }

    public int getCustomCombatLevel() {
        return combatLevel;
    }

    public int getCombatLevel() {
        return combatLevel >= 0 ? combatLevel : getDefinitions().combatLevel;
    }

    public String getName() {
        return name != null ? name : getDefinitions().name;
    }

    public void setCombatLevel(int level) {
        combatLevel = getDefinitions().combatLevel == level ? -1 : level;
        changedCombatLevel = true;
    }

    public boolean hasChangedName() {
        return changedName;
    }

    public boolean hasChangedCombatLevel() {
        return changedCombatLevel;
    }

    public WorldTile getMiddleWorldTile() {
        int size = getSize();
        return new WorldTile(getCoordFaceX(size), getCoordFaceY(size),
                getPlane());
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public boolean isNoDistanceCheck() {
        return noDistanceCheck;
    }

    public void setNoDistanceCheck(boolean noDistanceCheck) {
        this.noDistanceCheck = noDistanceCheck;
    }

    public boolean withinDistance(Player tile, int distance) {
        return super.withinDistance(tile, distance);
    }

    public boolean noDrop() {
    		if(this.getName().equalsIgnoreCase("nomad"))
    			return true;
    	return this instanceof LegendsNPCs;
    }	
    /**
     * Gets the locked.
     *
     * @return The locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked.
     *
     * @param locked The locked to set.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
