package com.rs.game.player.content.custom;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import com.rs.cores.CoresManager;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class TriviaBot {


	public boolean questionAnswered;
	public boolean questionActive;
	public String correctAnswer;
	
	public static ArrayList<Player> wrongAnswers = new ArrayList<Player>();
	
	public static TriviaBot instance;
	public static TriviaBot getInstance() {
		if (instance == null)
			instance = new TriviaBot();
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
		}, 1, Utils.random(9,12), TimeUnit.MINUTES);
	}
	
	public void sendTrivia(String message) {
		for (Player p : World.getPlayers()) {
			if (p == null || p.hasFinished() || !p.hasStarted() || p.hasDisabledTrivia()) {
				continue;
			}
			p.sm("<img=5> <col=108F4D>[Trivia] "+ message +"");
		}
	}
	
	public void sendMessage(Player player, String message) {
		player.sendMessage("<img=5> <col=108F4D>[Trivia] " + message);
	}
	
	public boolean verify(Player player, String answer) {
		if (questionAnswered) {
			sendMessage(player, "The question has already been answered.");
			return false;
		}
		
		if (correctAnswer == null) {
			sendMessage(player, "There is currently no question to be answered.");
			return false;
		}
		
		if (wrongAnswers.contains(player)) {
			sendMessage(player, "<col=108F4D>You've already guess this question wrong.");
			return false;
		}
		
		if (answer.equalsIgnoreCase(correctAnswer)) {
				player.setTriviaPoints(player.getTriviaPoints() + 1);
				questionAnswered = true;
				player.setLastAnswer(Utils.currentTimeMillis());
				sendTrivia("<col=108F4D>"+player.getDisplayName()+"</col><col=108F4D> has answered correctly! Well done!");
				sendMessage(player, "You've won "+Utils.formatNumber(1)+" trivia points! Good Job!");
				sendMessage(player, "You now have "+player.getTriviaPoints()+" trivia points.");
			return true;
		}
		wrongAnswers.add(player);
		sendMessage(player, "Sorry, you've answered the trivia incorrectly!");
		return false;
	}
	
	public void setAnswer(String answer) {
		if (answer == null)
			return;
		getInstance().correctAnswer = answer;
	}
	
	private static String[][] questions = {
		{ "Dragon Claws", "What weapon can be upgraded for 25,000 Eradicated Seals?" },
		{ "2013", "In what year was EradicationX released?" },
		{ "August", "What month was the Eradicator rank released?" },
		{ "IPB", "What script do we use for our forums?" },
		{ "Dragith nurn", "What is the Zombie Invasion boss's name?" },
		{ "50M", "How much coins do you receive when you start EradicationX?" },
		{ "MMORPG", "What gaming genre is EradicationX?" },
		{ "Administrator", "What rank is this? <img=17>" },
		{ "Eradicator", "What rank is this? <img=18>" },
		{ "Owner", "What rank is this? <img=7>" },
		{ "400M", "What is the maximum experience possible in a skill?" },
		{ "2000", "How many kills do you need for a pet boss?" },
		{ "10B", "What is the maximum Total XP?" },
		{ "27", "How many NPCs are in shops?" },
		{ "23", "How many bosses do we have?" },
		{ "6", "How many vote sites do we have?" },
		{ "2", "How many bankers are at home?" },
		{ "Blazing Flamberge", "What is the strongest one-handed melee weapon ingame?" },
		{ "Scarlet Spirit Shield", "What is the best spirit shield called?" },
		{ "95", "At what prayer level do you unlock turmoil?" },
		{ "70", "What combat level do you need to be able to fight in red portal?" },
		{ "100", "How kills do you need for a boss title?" },
		{ "138", "What is maximum combat level in EradicationX?" },
		{ "400", "What is the total level required to trade?" },
		{ "183", "How old does an account have to be to use the vet title?" },
		{ "800", "How many boss slayer points is the perma-turm ability?" },
		{ "7", "How many parts are required to make a full slayer helmet?" },
		{ "wildywyrm", "Which boss drops demonflesh armor?" },
		{ "maximum gradum", "Which boss has the lowest combat level?" },
		{ "10", "How many rakes are in the sell shop at home?" },
		{ "32", "What is the strength boost percent with charged turmoil?" },
		{ "10", "How many colors of h'ween masks are there?" },
		{ "eradicator", "What is highest tier donator rank?" },
		{ "35", "How many eradicated bones are used to make huge xp lamps?" },
		{ "fatal resort", "Who is the owner of EradicationX?" },
		{ "77", "What mining level is required to mine tokkul at the lava flow?" },
		{ "10k", "How many loyalty points does vampyrism cost?" },
		{ "63", "How many waves is the fight caves minigame?" },
		{ "30k", "How many hitpoints does Nex have?" },
		{ "cest", "What timezone does the server use?" },
		{ "19.5b", "How much is it to upgrade a tzhaar whip completely?" },
		{ "8", "How many color choices does the 10b xp cape have?" },
		{ "20", "How many chairs can be sat in at home?" },
		{ "cyndrith", "Who do you speak to in order to start the elite obsidian questline?" },
		{ "16", "How many obsidian shards are required to buy the full set?" },
		{ "@", "What symbol must you use before a message to whisper to a player?" },
		{ "dominion sword", "What weapon has the highest strength bonus?" },
	};

	
	public String generateQuestion() {
		int random = Utils.random(0, questions.length - 1);
		setAnswer(questions[random][0]);
		return questions[random][1] + " (Use ;;answer answerhere)";
	}
	
}