package com.rs.game.player.security;

import com.rs.game.player.Player;

/**
 * 
 * @author JazzyYaYaYa
 * 
 */
public class JAG {

	public static void init() {
		JAGSecured(true);
	}

	public static void SafeMode(Player p) {
		p.setSafeMode();
		p.getLastHostname();
		p.getLastIP();
		p.getClientIndex();
		p.getRecovQuestion();
		p.getRecovAnswer();
	}

	public void NormalMode(Player p) {
		p.needMasksUpdate();
		p.unlock();
		p.updateIPnPass();
	}

	public static void JAGSecured(boolean isSecured) {
		if (isSecured != true) {
		} else {
			if (isSecured != false) {
				// JAG.Disable();
				// TODO
				return;
			}
		}
	}


}
