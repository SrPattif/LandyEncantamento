package srpattif.encantamentos.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import srpattif.encantamentos.Inventories.PutTool;

public class Encantar implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("encantar")) {
			p.openInventory(PutTool.getInv());
			p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0F, 10.0F);
		}
		return false;
	}

}
