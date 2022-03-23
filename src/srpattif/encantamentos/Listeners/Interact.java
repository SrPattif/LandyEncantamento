package srpattif.encantamentos.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import srpattif.encantamentos.Utils.FileManager;
import srpattif.encantamentos.Utils.Utils;

public class Interact implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack hand = p.getItemInHand();
			ItemStack drop1 = Utils.getUnknownItem();
			ItemStack drop2 = Utils.getSecondItem();
			
			if(hand == null) return;
			
			if(hand.isSimilar(drop1)) {
				String som = FileManager.getFile("configuracoes.yml").getString("Drops.ItemDesconhecido.SomAoUsar");
				if (som != null) p.playSound(p.getLocation(), Utils.getSoundByName(som), 1.0F, 10.0F);
				
				p.getInventory().removeItem(drop1);
				p.getInventory().addItem(drop2);
				
				
			} else if(hand.isSimilar(drop2)) {
				p.getInventory().removeItem(drop2);
				
				long valor = FileManager.getFile("jogadores.yml").getLong("Jogadores." + p.getUniqueId() + ".Pontos");
				FileManager.getFile("jogadores.yml").set("Jogadores." + p.getUniqueId() + ".Pontos", (valor + 1));
				FileManager.saveFile("jogadores.yml");
				
				String som = FileManager.getFile("configuracoes.yml").getString("Drops.PontoDeEncantamento.SomAoUsar");
				if (som != null) p.playSound(p.getLocation(), Utils.getSoundByName(som), 1.0F, 10.0F);
				
				String message = FileManager.getFile("configuracoes.yml").getString("Drops.PontoDeEncantamento.MensagemHotbarAoUsar");
				if (message != null) Utils.sendHotbarMessage(p, message);
			}
			
		}
	}

}
