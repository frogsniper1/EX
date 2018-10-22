package com.rs;

import com.alex.store.Index;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.content.utils.IPMute;
import com.rs.cores.CoresManager;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.ServerChannelHandler;
import com.rs.utils.DTRank;
import com.rs.utils.DisplayNames;
import com.rs.utils.DummyRank;
import com.rs.utils.IPBanL;
import com.rs.utils.Logger;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.utils.spawning.NPCSpawning;
import com.rs.utils.spawning.ObjectSpawning;
import com.rs.utils.spawning.RemoveObjects;

import java.util.concurrent.TimeUnit;
public final class Launcher {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("USE: guimode(boolean) debug(boolean) hosted(boolean)");
            return;
        }
        boolean GUI_MODE =  Boolean.parseBoolean(args[0]);
        Settings.DEBUG = Boolean.parseBoolean(args[1]);
        Settings.HOSTED = Boolean.parseBoolean(args[2]);
       
        long currentTime = Utils.currentTimeMillis();
        if (Settings.HOSTED) { /* NOTHING */ }
        Logger.log("Launcher", "Loading " + Settings.SERVER_NAME + ", please wait...");
        Initializer.loadFiles();
        if(GUI_MODE)
        	Panel.main(null);
        try {
            ServerChannelHandler.init();
            NPCSpawning.spawnNpcs();
            cleanHome();
            ObjectSpawning.spawnObjects();
        } catch (Throwable e) {
            Logger.handle(e);
            Logger.log("Launcher", "Failed initing Server Channel Handler. Shutting down...");
            System.exit(1);
            return;
        }
		
        Logger.log("Launcher", "Server is now online. Took " + (Utils.currentTimeMillis() - currentTime) + " milliseconds to load.");
      /*  URL url = new URL("http://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        Logger.log("Launcher", "IP: " + br.readLine());
        Logger.log("Launcher", "Port: " + Settings.PORT_ID);*/
        addAccountsSavingTask();
        addCleanMemoryTask();
        MemoryManager.addMemoryManagementTask();
        MemoryManager.addUptimeTask();		
    }

	
    private static void addCleanMemoryTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    cleanMemory(true);
                } catch (Throwable e) {
                    Logger.handle(e);
                }                
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
    
    private static void addAccountsSavingTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                 saveFiles();
                } catch (Throwable e) {
                    Logger.handle(e);
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
    
    public static void saveFiles() {
        for (Player player : World.getPlayers()) {
            if (player == null || !player.hasStarted() || player.hasFinished()) {
                continue;
            }
        SerializableFilesManager.savePlayer(player);
        }
        DisplayNames.save();
        IPBanL.save();
        IPMute.save();
        PkRank.save();
        DummyRank.save();
        DTRank.save();
    }

    public static void cleanHome() {
    WorldTasksManager.schedule(new WorldTask() {
	@Override
            public void run() {
                if (World.getPlayers().size() > 0) {
                    RemoveObjects.removeObjects();
                    stop(); 
                }
            }
        }, 0, 10);
    }
    
    public static void cleanMemory(boolean force) {
        if (force) {
            ItemDefinitions.clearItemsDefinitions();
            NPCDefinitions.clearNPCDefinitions();
            ObjectDefinitions.clearObjectDefinitions();
            for (Region region : World.getRegions().values()) {
                region.removeMapFromMemory();
            }
        }
        for (Index index : Cache.STORE.getIndexes()) {
            index.resetCachedFiles();
        }
        CoresManager.fastExecutor.purge();
        System.gc();
    }

    public static void shutdown() {
        try {
            closeServices();
        } finally {
            System.exit(0);
        }
    }

    public static void closeServices() {
        ServerChannelHandler.shutdown();
        CoresManager.shutdown();
    }

    public static void restart() {
        closeServices();
        System.gc();
        try {
            Runtime.getRuntime().exec("java -server -Xms2048m -Xmx20000m -cp bin;/data/libs/netty-3.2.7.Final.jar;/data/libs/FileStore.jar Launcher false false true false");
            System.exit(0);
        } catch (Throwable e) {
            Logger.handle(e);
        }
    }

    private Launcher() {
    }
}
