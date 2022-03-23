package srpattif.encantamentos.Listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import srpattif.encantamentos.Utils.FileManager;
import srpattif.encantamentos.Utils.Utils;

public class EntityDeath implements Listener {

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (!FileManager.getFile("configuracoes.yml").getBoolean("Drops.Dropar"))
			return;

		ArrayList<String> mobs = (ArrayList<String>) FileManager.getFile("configuracoes.yml").getStringList("Entidades");
		int amount = FileManager.getFile("configuracoes.yml").getInt("Drops.Quantidade");
		
		if (mobs.contains(e.getEntity().getType().toString())) {
			if (e.getEntity().getKiller() instanceof Player) {
				Player killer = (Player) e.getEntity().getKiller();

				ItemStack item = Utils.getUnknownItem();
				item.setAmount(amount);

				killer.getInventory().addItem(item);
				String som = FileManager.getFile("configuracoes.yml").getString("Drops.ItemDesconhecido.SomAoDropar");
				if (som != null) killer.playSound(killer.getLocation(), Utils.getSoundByName(som), 1.0F, 10.0F);
			}
		}
	}

}
