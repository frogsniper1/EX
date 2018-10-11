package com.rs.utils;
 
import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;
 
import com.rs.game.player.Player;
 
public final class MacBanL {
 
        public static CopyOnWriteArrayList<String> macList;
 
        private static final String PATH = "data/bannedMACIPS.ser";
        private static boolean edited;
 
        @SuppressWarnings("unchecked")
        public static void init() {
                File file = new File(PATH);
                if (file.exists())
                        try {
                                macList = (CopyOnWriteArrayList<String>) SerializableFilesManager.loadSerializedFile(file);
                                return;
                        } catch (Throwable e) {
                                Logger.handle(e);
                        }
                macList = new CopyOnWriteArrayList<String>();
        }
 
        public static final void save() {
                if (!edited)
                        return;
                try {
                        SerializableFilesManager.storeSerializableClass(macList, new File(PATH));
                        edited = false;
                } catch (Throwable e) {
                        Logger.handle(e);
                }
        }
 
        public static boolean isMacBanned(Player player) {
                return macList.contains(player.getMACAdress());
        }
 
        public static void ban(Player player, boolean loggedIn) {
                player.setPermBanned(true);
                if (loggedIn) {
                        macList.add(player.getMACAdress());
                        player.getSession().getChannel().disconnect();
                } else {
                        macList.add(player.getMACAdress());
                        SerializableFilesManager.savePlayer(player);
                }
                edited = true;
        }
 
        public static void unban(Player player) {
                macList.remove(player.getMACAdress());
                edited = true;
                save();
        }
 
        public static void checkCurrent() {
                for (String list : macList) {
                        System.out.println(list);
                }
        }
 
        public static CopyOnWriteArrayList<String> getList() {
                return macList;
        }
 
}