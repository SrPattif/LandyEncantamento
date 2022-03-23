package srpattif.encantamentos.Inventories;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import srpattif.encantamentos.Main;
import srpattif.encantamentos.Utils.Skull;
import srpattif.encantamentos.Utils.Utils;

public class PutTool implements Listener {

	private static ArrayList<Player> closingInventory = new ArrayList<>();

	public static Inventory getInv() {
		Inventory inv = Bukkit.createInventory(null, 9 * 3, "Insira sua Ferramenta:");

		ItemStack nada = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta nadaMeta = nada.getItemMeta();
		nadaMeta.setDisplayName("§7");
		nada.setItemMeta(nadaMeta);

		inv.setItem(0, nada);
		inv.setItem(1, nada);
		inv.setItem(2, nada);
		inv.setItem(3, nada);
		inv.setItem(4, nada);
		inv.setItem(5, nada);
		inv.setItem(6, nada);
		inv.setItem(7, nada);
		inv.setItem(8, nada);

		inv.setItem(9, nada);
		inv.setItem(10, nada);
		inv.setItem(11, nada);
		inv.setItem(12, nada);
		// 14 - ferramenta
		inv.setItem(14, nada);
		inv.setItem(15, nada);
		inv.setItem(16, nada);
		inv.setItem(17, nada);

		inv.setItem(18, nada);
		inv.setItem(19, nada);
		inv.setItem(20, nada);
		inv.setItem(21, nada);
		inv.setItem(22, nada);
		inv.setItem(23, nada);
		inv.setItem(24, nada);
		inv.setItem(25, nada);
		// inv.setItem(26, nada);

		ArrayList<String> continuarLore = new ArrayList<>();
		ItemStack continuar = Skull.getCustomSkull("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf");
		ItemMeta continuarMeta = continuar.getItemMeta();
		continuarMeta.setDisplayName("§aContinuar");
		continuarLore.add("§7Adicione uma ferramenta no espaço vazio e clique aqui");
		continuarLore.add("§7para abrir o menu de encantamentos.");
		continuarMeta.setLore(continuarLore);
		continuar.setItemMeta(continuarMeta);

		inv.setItem(26, continuar);
		return inv;
	}

	@EventHandler
	public void invClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;

		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equals(getInv().getTitle())) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
				return;

			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§7")) {
					e.setCancelled(true);
					return;
				}
			}

			if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);

				if (e.getInventory().getItem(13) == null) {
					if (hasAvaliableSlot((Player) p)) {
						p.getInventory().addItem(e.getInventory().getItem(13));
					} else {
						p.getWorld().dropItemNaturally(p.getLocation(), e.getInventory().getItem(13));
					}

					closingInventory.add(p);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
						public void run() {
							closingInventory.remove(p);
						}
					}, 1 * 20L);

					p.closeInventory();
					p.sendMessage(" ");
					p.sendMessage("§c§lENCANTAMENTOS:");
					p.sendMessage(" §cInsira uma ferramenta no espaço vazio para encantá-la.");
					p.sendMessage(" ");
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 10.0F);
					return;
				}

				if (Utils.getTools().contains(e.getInventory().getItem(13).getType())) {
					closingInventory.add(p);

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
						public void run() {
							closingInventory.remove(p);
						}
					}, 1 * 20L);

					p.openInventory(Enchantments.getInv(p, e.getInventory().getItem(13)));
					p.playSound(p.getLocation(), Sound.CLICK, 1.0F, 10.0F);

				} else {
					closingInventory.add(p);

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
						public void run() {
							closingInventory.remove(p);
						}
					}, 1 * 20L);

					if (hasAvaliableSlot((Player) p)) {
						p.getInventory().addItem(e.getInventory().getItem(13));
					} else {
						p.getWorld().dropItemNaturally(p.getLocation(), e.getInventory().getItem(13));
					}

					p.closeInventory();
					p.sendMessage(" ");
					p.sendMessage("§c§lENCANTAMENTOS:");
					p.sendMessage(" §cFerramenta inválida.");
					p.sendMessage(" ");
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 10.0F);
					return;
				}
			}

			if (!Utils.getTools().contains(e.getCurrentItem().getType())) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player))
			return;
		if (closingInventory.contains(e.getPlayer()))
			return;

		if (e.getInventory().getTitle().equals(getInv().getTitle())) {
			if (e.getInventory().getItem(13) != null) {
				if (hasAvaliableSlot((Player) e.getPlayer())) {
					e.getPlayer().getInventory().addItem(e.getInventory().getItem(13));
				} else {
					e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), e.getInventory().getItem(13));
				}
			}
		}
	}

	public static boolean hasAvaliableSlot(Player player) {
		Inventory inv = player.getInventory();
		for (ItemStack item : inv.getContents()) {
			if (item == null) {
				return true;
			}
		}
		return false;
	}

}
