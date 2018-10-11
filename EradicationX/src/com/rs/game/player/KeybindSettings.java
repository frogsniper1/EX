package com.rs.game.player;

public class KeybindSettings {
	
	
	public static void openInterface(Player player) {
		player.getInterfaceManager().sendInterface(3012);
		player.getPackets().sendIComponentText(3012, 2, player.getKeyFunction().getKeybind(Keybind.F6).getName());
		player.getPackets().sendIComponentText(3012, 3, player.getKeyFunction().getKeybind(Keybind.F7).getName());
		player.getPackets().sendIComponentText(3012, 4, player.getKeyFunction().getKeybind(Keybind.F8).getName());
		player.getPackets().sendIComponentText(3012, 5, player.getKeyFunction().getKeybind(Keybind.F9).getName());
		player.getPackets().sendIComponentText(3012, 6, player.getKeyFunction().getKeybind(Keybind.F10).getName());
		player.getPackets().sendIComponentText(3012, 7, player.getKeyFunction().getKeybind(Keybind.F11).getName());
		player.getPackets().sendIComponentText(3012, 8, player.getKeyFunction().getKeybind(Keybind.F12).getName());
		player.getPackets().sendIComponentText(3012, 9, player.getKeyFunction().getKeybind(Keybind.HOME).getName());
		player.getPackets().sendIComponentText(3012, 10, player.getKeyFunction().getKeybind(Keybind.INSERT).getName());
		player.getPackets().sendIComponentText(3012, 11, player.getKeyFunction().getKeybind(Keybind.DELETE).getName());
		player.getPackets().sendIComponentText(3012, 12, player.getKeyFunction().getKeybind(Keybind.END).getName());
		player.getPackets().sendIComponentText(3012, 14, "Keybinds: "  + (player.getKeyFunction().getKeybindsToggle() ? "[On]" : "[Off]"));
		player.getPackets().sendIComponentText(3012, 15, "Escape closes interfaces/opens options menu: "  + (player.getKeyFunction().hasEscapeToggle() ? "[On]" : "[Off]"));
	}
	public static void handleButtons(Player player, int componentId) {
		switch (componentId) {
		case 2:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F6);
			break;
		case 3:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F7);
			break;
		case 4:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F8);
			break;
		case 5:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F9);
			break;
		case 6:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F10);
			break;
		case 7:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F11);
			break;
		case 8:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.F12);
			break;
		case 9:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.HOME);
			break;
		case 10:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.INSERT);
			break;
		case 11:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.DELETE);
			break;
		case 12:
			player.getDialogueManager().startDialogue("AssignKeyFunction", Keybind.END);
			break;
		case 13:
			player.sm("Re-download the client via our site if your client is older than December 23rd, 2015.");
			break;
		case 14:
			player.sm("Toggled " + (player.getKeyFunction().setKeybindsToggle() ? "on. Hotkeys will now function." : "off. Hotkeys will no longer function."));
			player.getPackets().sendIComponentText(3012, 14, "Keybinds: "  + (player.getKeyFunction().getKeybindsToggle() ? "[On]" : "[Off]") );
			break;
		case 15:
			player.sm("Toggled " + (player.getKeyFunction().setEscapeToggle() ? "on. Esc key will close in priority of: Removing text, dialogues, interfaces." : "off. The properties of escape will no longer function."));
			player.getPackets().sendIComponentText(3012, 15, "Escape closes interfaces/opens options menu: "  + (player.getKeyFunction().hasEscapeToggle() ? "[On]" : "[Off]"));
			break;
		}
	}
	
}