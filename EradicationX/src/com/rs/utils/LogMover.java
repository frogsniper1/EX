package com.rs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.rs.game.player.Player;

public class LogMover implements Runnable {
	private Player player;
    public LogMover(Player player) {
        this.player = player;
    }
    public void run() {
			String[] files = {"Chats.txt", "IPs.txt", "PlayerLoots.txt", "PMs.txt", "Trades.txt", "ShopLogs.txt", "AlchLogs.txt", "DiceRolls.txt", "Empties.txt"};
			InputStream inStream = null;
			OutputStream outStream = null;
			
			try{
				for (int i = 0;i < files.length;i++) {
					File f1 =new File("data/logs/" + files[i]);
					File f2 =new File("C://Users/root/Dropbox/Logs/" +files[i]);
					
					inStream = new FileInputStream(f1);
					outStream = new FileOutputStream(f2);
					
					byte[] buffer = new byte[1024];
					
					int length;
					while ((length = inStream.read(buffer)) > 0){
				  
						outStream.write(buffer, 0, length);
				 
					}
				}
			 
				inStream.close();
				outStream.close();			
				player.sm("Files were copied successfully!");
				return;
			}catch(IOException e){
				player.sm("File transfer failed!");
				e.printStackTrace();
				return;
			}
		}
}
