package srpattif.encantamentos.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Utils {
	
	public static HashMap<Long, Enchantment> getToolEnchantmentsAvailable(Material tool) {
		HashMap<Long, Enchantment> map = new HashMap<>();
		if(!getTools().contains(tool)) return null;
		
		if(tool.toString().contains("SWORD")) {
			ArrayList<String> values = (ArrayList<String>) FileManager.getFile("encantamentos.yml").getStringList("Encantamentos.Espada");
			
			for(String v : values) {
				String[] s = v.split(":");
				map.put(Long.valueOf(s[0]), Enchantment.getByName(s[1]));
			}
		}
		
		if(tool.toString().contains("AXE")) {
			ArrayList<String> values = (ArrayList<String>) FileManager.getFile("encantamentos.yml").getStringList("Encantamentos.Machado");
			
			for(String v : values) {
				String[] s = v.split(":");
				map.put(Long.valueOf(s[0]), Enchantment.getByName(s[1]));
			}
		}
		
		if(tool.toString().contains("HOE")) {
			ArrayList<String> values = (ArrayList<String>) FileManager.getFile("encantamentos.yml").getStringList("Encantamentos.Enxada");
			
			for(String v : values) {
				String[] s = v.split(":");
				map.put(Long.valueOf(s[0]), Enchantment.getByName(s[1]));
			}
		}
		
		if(tool.toString().contains("PICAXE")) {
			ArrayList<String> values = (ArrayList<String>) FileManager.getFile("encantamentos.yml").getStringList("Encantamentos.Picareta");
			
			for(String v : values) {
				String[] s = v.split(":");
				map.put(Long.valueOf(s[0]), Enchantment.getByName(s[1]));
			}
		}
		
		if(tool.toString().contains("SPADE")) {
			ArrayList<String> values = (ArrayList<String>) FileManager.getFile("encantamentos.yml").getStringList("Encantamentos.Pa");
			
			for(String v : values) {
				String[] s = v.split(":");
				map.put(Long.valueOf(s[0]), Enchantment.getByName(s[1]));
			}
		}
		
		
		return map;
	}
	
	public static String enchantmentToLegible(Enchantment ench) {
		switch (ench.getName()) {
		case "DAMAGE_ALL":
			return "Afiação";
		case "DURABILITY":
			return "Inquebrável";
		case "DAMAGE_ARTHROPODS":
			return "Ruína dos Artrópodes";
		case "FIRE_ASPECT":
			return "Aspecto Flamejante";
		case "KNOCKBACK":
			return "Repulsão";
		case "LOOT_BONUS_MOBS":
			return "Pilhagem";
			
		case "LOOT_BONUS_BLOCKS":
			return "Fortuna";
		case "DIG_SPEED":
			return "Eficiência";
		case "SILK_TOUCH":
			return "Toque Suave";
			
		default:
			return ench.getName();
		}
	}
	
	public static ArrayList<Material> getTools() {
		ArrayList<Material> r = new ArrayList<>();
		
		r.add(Material.DIAMOND_SWORD);
		r.add(Material.DIAMOND_AXE);
		r.add(Material.DIAMOND_HOE);
		r.add(Material.DIAMOND_PICKAXE);
		r.add(Material.DIAMOND_SPADE);
		
		r.add(Material.IRON_SWORD);
		r.add(Material.IRON_AXE);
		r.add(Material.IRON_HOE);
		r.add(Material.IRON_PICKAXE);
		r.add(Material.IRON_SPADE);
		
		return r;
	}
	
	public static ItemStack getEnchantedBook(Enchantment ench, int level) {
		ItemStack r = new ItemStack(Material.ENCHANTED_BOOK, 1);
		EnchantmentStorageMeta esm = (EnchantmentStorageMeta) r.getItemMeta();
		esm.addStoredEnchant(ench, level, true);
		r.setItemMeta(esm);
		return r;
	}
	
	public static ItemStack getUnknownItem() {
		String materialName = FileManager.getFile("itens.yml").getString("Itens.Dust.Material");
		String nomeItem = FileManager.getFile("itens.yml").getString("Itens.Dust.Nome");
		short id = (short) FileManager.getFile("itens.yml").getInt("Itens.Dust.IdEspecial");
		
		ArrayList<String> itemLore = Utils.parseArrayColors((ArrayList<String>) FileManager.getFile("itens.yml").getStringList("Itens.Dust.Descricao"));
		ItemStack item = new ItemStack(Utils.getMaterialByName(materialName), 1, id);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nomeItem));
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getSecondItem() {
		String materialName = FileManager.getFile("itens.yml").getString("Itens.PosDust.Material");
		String nomeItem = FileManager.getFile("itens.yml").getString("Itens.PosDust.Nome");
		short id = (short) FileManager.getFile("itens.yml").getInt("Itens.PosDust.IdEspecial");
		System.out.println(id);
		
		ArrayList<String> itemLore = Utils.parseArrayColors((ArrayList<String>) FileManager.getFile("itens.yml").getStringList("Itens.PosDust.Descricao"));
		ItemStack item = new ItemStack(Utils.getMaterialByName(materialName), 1, id);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nomeItem));
		itemMeta.setLore(itemLore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static void sendHotbarMessage(Player p, String message) {
		String s = ChatColor.translateAlternateColorCodes('&', message);
        IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	}
	
	public static Sound getSoundByName(String name) {
		for(Sound s : Sound.values()) {
			if(s.toString().equalsIgnoreCase(name)) {
				return s;
			}
		}
		return null;
	}
	
	public static Material getMaterialByName(String name) {
		for(Material m : Material.values()) {
			if(m.toString().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	
	public static ArrayList<String> parseArrayColors(ArrayList<String> arr) {
		ArrayList<String> r = new ArrayList<>();
		for(String s : arr) {
			r.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return r;
	}

}
