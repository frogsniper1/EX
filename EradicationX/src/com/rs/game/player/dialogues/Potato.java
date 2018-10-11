package com.rs.game.player.dialogues;


import com.rs.game.player.content.Rotten_Commands;
import com.rs.game.player.dialogues.Dialogue;


public class Potato extends Dialogue {


    @Override
    public void start() {
        sendOptionsDialogue("Rotten Potato", "Start A meeting",
                "Nothing", "Server Menagement",
                "Your Account Menagement", "Some Dev Commands");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        if (stage == -1) {
            switch (componentId) {
                case OPTION_1:
                    stage = 0;
                    sendOptionsDialogue("Start A meeting", "Admin Meeting",
                            "Mod Meeting", "Cancel");
                    break;
				case OPTION_2:
					stage = 1;
					sendOptionsDialogue("-", "-",
                            "-", "Cancel");
                case OPTION_3:
                    stage = 4;
                    sendOptionsDialogue("Server Menagement", "ShutDown Server",
                            "-", "Reload Server Files",
                            "Unmute All Players", "More Option");
                    break;
                case OPTION_4:
                    stage = 9;
                    sendOptionsDialogue("Your Account Menagement", "Open Bank",
                            "200m Xp in all skill", "Reset all your skill to 1",
                            "Restore Special", "More Option");
                    break;
                case OPTION_5:
                    stage = 12;
                    sendOptionsDialogue("Some Dev Commands",
                            "-", "hitpoint healing test hit",
                            "Count all cache anim", "Cancel");
                    break;
            }
        } else if (stage == 0) {
            switch (componentId) {
                case OPTION_1:
                    Rotten_Commands.Staff_meeting(player);
                    end();
                    break;
                case OPTION_2:
                    Rotten_Commands.Mod_meeting(player);
                    end();
                    break;
                default:
                    end();
                    break;
            }
        } else if (stage == 1) {
            switch (componentId) {
                case OPTION_1:
                    stage = 2;
                    sendOptionsDialogue(
                            "-",
                            "-",
                            "-");
                    break;
                case OPTION_2:
                    stage = 3;
                    sendOptionsDialogue(
                            "-",
                            "-",
                            "-");
                    break;
                default:
                    end();
                    break;
            }
        } else if (stage == 2) {
            switch (componentId) {
                case OPTION_1:
                    end();
                    break;
                case OPTION_2:
                    end();
                    break;
            }
        } else if (stage == 3) {
            switch (componentId) {
                case OPTION_1:
                    end();
                    break;
                case OPTION_2:
                    end();
                    break;
            }
        } else if (stage == 4) {
            switch (componentId) {
                case OPTION_1:
                    stage = 5;
                    sendOptionsDialogue(
                            "Are You Sure You Wishe to close the server",
                            "Yes i wishe to close the server",
                            "No i don't wishe to close the server");
                    break;
                case OPTION_2:
                    stage = 6;
                    sendOptionsDialogue("-",
                            "-", "Cancel");
                    break;
                case OPTION_3:
                    Rotten_Commands.Reload_files();
                    end();
                    break;
                case OPTION_4:
                    Rotten_Commands.Unmute_All(player);
                    end();
                    break;
                case OPTION_5:
                    stage = 7;
                    sendOptionsDialogue("Server Menagement", "Kill all Player",
                            "Restart Fight Pit only do if mini game is glitched",
                            "Cancel");
                    break;
            }
        } else if (stage == 5) {
            switch (componentId) {
                case OPTION_1:
                    Rotten_Commands.Shutdown();
                    end();
                    break;
                case OPTION_2:
                    end();
                    break;
            }
        } else if (stage == 6) {
            switch (componentId) {
                case OPTION_1:
                    end();
                    break;
                case OPTION_2:
                    end();
                    break;
                default:
                    end();
                    break;
            }
        } else if (stage == 7) {
            switch (componentId) {
                case OPTION_1:
                    stage = 8;
                    sendOptionsDialogue(
                            "Are You Sure you wishe to kill all player online",
                            "Yes i wishe to kill all player",
                            "No i don't wishe to kill all player", "Cancel");
                    end();
                    break;
                case OPTION_2:
                    Rotten_Commands.Reset_fight_pit(player);
                    end();
                    break;
                default:
                    end();
                    break;
            }
        } else if (stage == 8) {
            switch (componentId) {
                case OPTION_1:
                    Rotten_Commands.Kill_All(player);
                    end();
                    break;
                default:
                    end();
                    break;
            }
        } else if (stage == 9) {
            switch (componentId) {
                case OPTION_1:
                    Rotten_Commands.Open_Bank(player);
                    end();
                    break;
                case OPTION_2:
                    Rotten_Commands.master(player);
                    end();
                    break;
                case OPTION_3:
                    Rotten_Commands.Reset_All(player);
                    end();
                    break;
                case OPTION_4:
                    Rotten_Commands.Restore_special(player);
                    end();
                    break;
                case OPTION_5:
                    stage = 10;
                    sendOptionsDialogue("Your Account Menagement",
                            "-", "Kill YourSelf", "God Mode",
                            "Switch your Prayer", "Switch your magic book");
                    break;
            }
        } else if (stage == 10) {
            switch (componentId) {
                case OPTION_1:
                    end();
                    break;
                case OPTION_2:
                    Rotten_Commands.Kill_Me(player);
                    end();
                    break;
                case OPTION_3:
                    Rotten_Commands.God_Mode(player);
                    end();
                    break;
                case OPTION_4:
                    Rotten_Commands.Curse(player);
                    end();
                    break;
                case OPTION_5:
                    stage = 11;
                    sendOptionsDialogue("Switch Your Magic Book", "Modern",
                            "Ancients", "Lunar", "Cancel");
                    break;
            }
        } else if (stage == 11) {
            switch (componentId) {
                case OPTION_1:
                    Rotten_Commands.Mage_Book(player, 0);
                    end();
                    break;
                case OPTION_2:
                    Rotten_Commands.Mage_Book(player, 1);
                    end();
                    break;
                case OPTION_3:
                    Rotten_Commands.Mage_Book(player, 2);
                    end();
                    break;
                default:
                    end();
                    break;
            }
        } else if (stage == 12) {
            switch (componentId) {
                case OPTION_1:
                    end();
                    break;
                case OPTION_2:
                    Rotten_Commands.Hit_test(player);
                    end();
                    break;
                case OPTION_3:
                    Rotten_Commands.Count_anim(player);
                    end();
                    break;
                default:
                    end();
                    break;
            }
        }
    }


    @Override
    public void finish() {


    }


}