package com.rs.game.player.dialogues;

public class PumpkinPete3 extends Dialogue {
	
	/**
	 * @author Something
	 **/

	private int npcId;

  @Override
	public void start() {
	npcId = (Integer) parameters[0];
			stage = -1;
			sendNPCDialogue(npcId, 9827, "Do you have the item from boss you killed?");
	}

  @Override
	public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(9827, "Yes");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue(npcId, 9827, "Thanks! Here is a gift for you.");
			player.getInventory().deleteItem(18092, 1);
			player.getInventory().addItem(6829, 1);
			player.drink = 3;
			player.doneevent = 1;
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(9827, "Bye and Happy Halloween!");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue(npcId, 9827, "Bye");
			player.sendMessage("You have completed the Halloween event!");
			player.sendMessage("Use the item to teleport to a very Hard Boss to get Halloween Box!");
			player.getInterfaceManager().sendInterface(1244);
			end();
			break;
		}
	}

  @Override
	public void finish() {

	}

}