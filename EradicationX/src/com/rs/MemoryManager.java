package com.rs;

import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Logger;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MemoryManager {
    
    
    private static long MAX_MEMORY = (long) 16000.0;
    /**
     * Subtracts the server's free memory from the max available memory
     * to get you the amount being used in total (in bytes)
     */
    public static long getServerMemUsage() {
        return Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory();
    }
    
    /**
     * gets the max memory the server can use (in bytes)
     */
    public static long getServerMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }
    
    /**
     * Gets your computer's available RAM (in bytes)
     */
    public static long getSystemMemory() {
        return ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    }
    
    /**
     * Gets your computer's free RAM (RAM not being used (in bytes))
     */
    public static long getSystemFreeMem() {
        return ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize();
    }
    
    /**
     * subtracts the free memory, from the overall memory, to 
     * give you how much is being used (in bytes)
     */
    public static long systemMemoryUsage() {
        return getSystemMemory() - getSystemFreeMem();
    }
    
    /**
     * @return memory in MB
     */
    public static long serverUsageInMb() {
        return (getServerMemUsage() / 1048576);
    }
    public static long serverMaxMemoryInMb() {
        return (getServerMaxMemory() / 1048576);
    }
    
     /**
     * @return bytes into megabytes
     */
    public static long systemMemoryUsageInMb() {
        return (systemMemoryUsage() / 1048576);
    }
    public static long systemTotalMemoryInMb() {
        return (getSystemMemory() / 1048576);
    }
    
    /**
     * @return the sytem memory information 
     */
    public static String systemMemoryInformation() {
      return ""+ systemMemoryUsageInMb()+"MB/"+ systemTotalMemoryInMb() +"MB";
    }
    
    /**
     * @return the server memory information
     */
    public static String serverMemoryInformation() {
      return ""+ serverUsageInMb() +"MB/" + serverMaxMemoryInMb() + "MB";
    } 
    
    public static long serverUptime() {
       return ManagementFactory.getRuntimeMXBean().getUptime();
    }
    
     /**
      * Add's the task to the server for printing information every 3 minutes
      */
    public static void addMemoryManagementTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                if (systemMemoryUsageInMb() > MAX_MEMORY) {
                    System.err.println("CRITICAL MEMORY USAGE: "+systemMemoryUsageInMb()+"MB.");
                    return;
                }
                Logger.log(getDateTime(), "System Memory: "+systemMemoryInformation()+" (Server is using "+serverUsageInMb()+"MB)");
                } catch (Throwable e) {
                    Logger.handle(e);
                }
            }
        }, 0, 3, TimeUnit.MINUTES);
    }
    
    public static void addUptimeTask() {
        CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Player players : World.getPlayers()) {
                        if (World.getPlayers().size() > 0) {
                        	players.setLoyaltytimer(players.getLoyaltytimer()+1);
                        	if (players.getLoyaltytimer() >= 1800) {
                        		players.getPackets().sendGameMessage("<col=008000>You have recieved 250 loyalty points for playing for 30 minutes!");
            					players.getPackets().sendGameMessage("<col=008000>You now have " + players.getLoyaltyPoints() + " Loyalty Points!");
            					players.setSpellPower(players.getSpellPower()+1);
            					players.setLoyaltyPoints(players.getLoyaltyPoints() + 250);
            					players.setLoyaltytimer(0);
                        	}
                        	players.getInterfaceManager().updatePortal();
                        }
                    }
                } catch (Throwable e) {
                    Logger.handle(e);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);  
    }
    
    public static String getDateTime() { 
        SimpleDateFormat format = new SimpleDateFormat("MMM d '@' HH:mm:ss"); 
        return format.format(new Date());
    }
}
