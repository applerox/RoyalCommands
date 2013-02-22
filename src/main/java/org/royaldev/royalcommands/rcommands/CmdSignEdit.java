package org.royaldev.royalcommands.rcommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.royaldev.royalcommands.RUtils;
import org.royaldev.royalcommands.RoyalCommands;

public class CmdSignEdit implements CommandExecutor {

    private final RoyalCommands plugin;

    public CmdSignEdit(RoyalCommands instance) {
        plugin = instance;
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("signedit")) {
            if (!plugin.isAuthorized(cs, "rcmds.signedit")) {
                RUtils.dispNoPerms(cs);
                return true;
            }
            if (!(cs instanceof Player)) {
                cs.sendMessage(ChatColor.RED + "This command is only available to players!");
                return true;
            }
            Player p = (Player) cs;
            if (args.length < 1) {
                cs.sendMessage(cmd.getDescription());
                return false;
            }
            Block b = p.getTargetBlock(null, 100); // RUtils has signs as transparent
            if (b == null || !(b.getState() instanceof Sign)) {
                cs.sendMessage(ChatColor.RED + "The block in sight is not a sign!");
                return true;
            }
            /*if (!plugin.canAccessChest(p, b)) {
                cs.sendMessage(ChatColor.RED + "You cannot access that sign!");
                return true;
            }*/
            Sign s = (Sign) b.getState();
            int lineNumber;
            try {
                lineNumber = Integer.parseInt(args[0]);
                lineNumber--;
            } catch (NumberFormatException e) {
                cs.sendMessage(ChatColor.RED + "The line number was not a number!");
                return true;
            }
            if (lineNumber < 0 || lineNumber > 3) {
                cs.sendMessage(ChatColor.RED + "The line number can't be less than one or greater than four.");
                return true;
            }
            if (args.length < 2) {
                s.setLine(lineNumber, "");
                cs.sendMessage(ChatColor.BLUE + "Cleared line " + ChatColor.GRAY + (lineNumber + 1) + ChatColor.BLUE + ".");
                return true;
            }
            String text = RoyalCommands.getFinalArg(args, 1);
            if (plugin.isAuthorized(cs, "rcmds.signedit.color")) text = RUtils.colorize(text);
            s.setLine(lineNumber, text);
            s.update();
            cs.sendMessage(ChatColor.BLUE + "Set line " + ChatColor.GRAY + (lineNumber + 1) + ChatColor.BLUE + ".");
            return true;
        }
        return false;
    }

}
