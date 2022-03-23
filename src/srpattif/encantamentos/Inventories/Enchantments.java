package srpattif.encantamentos.Inventories;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import srpattif.encantamentos.Main;
import srpattif.encantamentos.Utils.FileManager;
import srpattif.encantamentos.Utils.Utils;

public class Enchantments implements Listener {
	
	private static ArrayList<Player> closingInventory = new ArrayList<>();

	public static Inventory getInv(Player p, ItemStack tool) {

		long saldo = FileManager.getFile("jogadores.yml").getLong("Jogadores." + p.getUniqueId() + ".Pontos");
		
		Inventory inv = Bukkit.createInventory(null, 9 * 4, "Encantamentos:");

		
		tool = tool.clone();
		ArrayList<String> toolLore = (ArrayList<String>) tool.getItemMeta().getLore();
		if(toolLore == null) toolLore = new ArrayList<>();
		toolLore.add(" ");
		toolLore.add("§7Seus Pontos de Encantamento: §f" + saldo);
		ItemMeta toolMeta = tool.getItemMeta();
		toolMeta.setLore(toolLore);
		tool.setItemMeta(toolMeta);
		
		ItemStack nada = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta nadaMeta = nada.getItemMeta();
		nadaMeta.setDisplayName("§7");
		nada.setItemMeta(nadaMeta);

		inv.setItem(0, nada);
		inv.setItem(1, nada);
		inv.setItem(2, nada);
		inv.setItem(3, nada);
		inv.setItem(4, tool);
		inv.setItem(5, nada);
		inv.setItem(6, nada);
		inv.setItem(7, nada);
		inv.setItem(8, nada);

		inv.setItem(9, nada);
		inv.setItem(17, nada);
		inv.setItem(18, nada);
		inv.setItem(26, nada);

		inv.setItem(27, nada);
		inv.setItem(28, nada);
		inv.setItem(29, nada);
		inv.setItem(30, nada);
		inv.setItem(31, nada);
		inv.setItem(32, nada);
		inv.setItem(33, nada);
		inv.setItem(34, nada);
		inv.setItem(35, nada);

		int limitLevel = FileManager.getFile("encantamentos.yml").getInt("Encantamentos.NivelMaximo");
		
		HashMap<Long, Enchantment> enchs = Utils.getToolEnchantmentsAvailable(tool.getType());
		for (HashMap.Entry<Long, Enchantment> entry : enchs.entrySet()) {
			Long key = entry.getKey();
			Enchantment val = entry.getValue();
			
			int level = 1;
			if(tool.getItemMeta().hasEnchant(val)) {
				level = (tool.getItemMeta().getEnchantLevel(val) + 1);
			}

			ArrayList<String> bookLore = new ArrayList<>();
			ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta bookMeta = book.getItemMeta();
			bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			bookMeta.setDisplayName("§eEncantamento");
			bookLore.add("§7Encantamento: §f" + Utils.enchantmentToLegible(val));
			bookLore.add("§7Valor: §f" + key + " Pontos de Encantamento");
			bookLore.add(" ");
			if(level > limitLevel) bookLore.add("§eVocê chegou no limite deste encantamento.");
			if(!(level > limitLevel)) if (saldo >= key) bookLore.add("§aClique para evoluir este encantamento");
			if(!(level > limitLevel)) if (saldo < key) bookLore.add("§cVocê não possui Pontos de Encantamento para evoluir");
			bookLore.add("§8" + val.getName());
			bookMeta.setLore(bookLore);
			book.setItemMeta(bookMeta);

			inv.addItem(book);
		}

		return inv;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(!(e.getPlayer() instanceof Player)) return;
		if(closingInventory.contains(e.getPlayer())) return;
		
		if (e.getInventory().getTitle().equals("Encantamentos:")) {
			if (e.getInventory().getItem(4) != null) {
				ItemStack item = e.getInventory().getItem(4);
				ItemMeta itemMeta = item.getItemMeta();
				if(itemMeta.hasLore()) {
					ArrayList<String> lore = (ArrayList<String>) itemMeta.getLore();
					
					for(int i = 0; i < 2; i++) {
						lore.remove(lore.size() - 1);
					}
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
				}
				
				if (hasAvaliableSlot((Player) e.getPlayer())) {
					e.getPlayer().getInventory().addItem(item);
				} else {
					e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), item);
				}
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;

		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equals("Encantamentos:")) {
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;

			e.setCancelled(true);
			
			if(!e.getCurrentItem().hasItemMeta()) return;
			if(!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
			
			if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eEncantamento")) {
				Enchantment enc = Enchantment.getByName(e.getCurrentItem().getItemMeta().getLore().get(e.getCurrentItem().getItemMeta().getLore().size() - 1).replace("§8", ""));
				ItemStack tool = e.getInventory().getItem(4).clone();

				HashMap<Long, Enchantment> enchs = Utils.getToolEnchantmentsAvailable(tool.getType());
				long saldo = FileManager.getFile("jogadores.yml").getLong("Jogadores." + p.getUniqueId() + ".Pontos");
				long valor = 0;
				for (HashMap.Entry<Long, Enchantment> entry : enchs.entrySet()) {
					Long key = entry.getKey();
					Enchantment val = entry.getValue();
					
					if(val == enc) {
						if(saldo < key) {
							return;
						}
						
						valor = key;
					}
				}
				
				int level = 1;
				if(tool.getItemMeta().hasEnchant(enc)) {
					level = (tool.getItemMeta().getEnchantLevel(enc) + 1);
					tool.getItemMeta().removeEnchant(enc);
				}
				
				int limitLevel = FileManager.getFile("encantamentos.yml").getInt("Encantamentos.NivelMaximo");
				if(level > limitLevel) return;
				
				tool.addUnsafeEnchantment(enc, level);
				
				FileManager.getFile("jogadores.yml").set("Jogadores." + p.getUniqueId() + ".Pontos", (saldo - valor));
				FileManager.saveFile("jogadores.yml");
				
				closingInventory.add(p);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
					public void run() {
						closingInventory.remove(p);
					}
				}, 1 * 20L);
				ItemStack item = tool;
				ItemMeta itemMeta = item.getItemMeta();
				if(itemMeta.hasLore()) {
					ArrayList<String> lore = (ArrayList<String>) itemMeta.getLore();
					
					for(int i = 0; i < 2; i++) {
						lore.remove(lore.size() - 1);
					}
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
				}
				
				p.getInventory().addItem(item);
				
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0F, 10.0F);
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
