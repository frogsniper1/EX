package com.rs.game.player.content.custom;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class BossHighlight {


	public boolean questionAnswered;
	public boolean questionActive;
	public int correctAnswer;
	
	public static ArrayList<Player> wrongAnswers = new ArrayList<Player>();
	
	public static BossHighlight instance;
	public static BossHighlight getInstance() {
		if (instance == null)
			instance = new BossHighlight();
		return instance;
	}
	
	public void start() {
		CoresManager.slowExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (World.getPlayers().size() >= 1) {
					wrongAnswers.clear();
					sendTrivia(generateQuestion());
					questionAnswered = false;
				}
			}
		}, 1, 60, TimeUnit.MINUTES);
	}
	
	public void sendTrivia(int message) {
		for (Player p : World.getPlayers()) {
			if (p == null || p.hasFinished() || !p.hasStarted() || p.hasDisabledTrivia()) {
				continue;
			}
			p.sm("<img=5> <col=123abc>[Boss Highlight] The current boss is "+ NPCDefinitions.getNPCDefinitions(message).getName()+"<col=123abc>. A 1.5x modifier for Spell power has been applied.");
		}
	}

	
	public void setAnswer(int answer) {
		if (answer == 0)
			return;
		getInstance().correctAnswer = answer;
	}
	
	private static int[] boss = {6260,6247,6203,6222,12878,18872,8133,50,3334,11751,15222,15172,15002,15003,1900,15006,15009,15208,1895,11,13447,5561,15961,6260};
	
	public int getBoss() {
		return correctAnswer;
	}
	
	public void reroll() {
		wrongAnswers.clear();
		sendTrivia(generateQuestion());
		questionAnswered = false;
	}
	
	public int generateQuestion() {
		int random = Utils.random(0, boss.length - 1);
		setAnswer(boss[random]);
		return boss[random];
	}
	
}