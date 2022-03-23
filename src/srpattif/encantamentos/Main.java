package srpattif.encantamentos;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import srpattif.encantamentos.Commands.Encantar;
import srpattif.encantamentos.Inventories.Enchantments;
import srpattif.encantamentos.Inventories.PutTool;
import srpattif.encantamentos.Listeners.EntityDeath;
import srpattif.encantamentos.Listeners.Interact;
import srpattif.encantamentos.Utils.FileManager;

public class Main extends JavaPlugin {

	public static Main m;

	@Override
	public void onEnable() {
		m = this;

		FileManager.createFile(m, "configuracoes.yml");
		FileManager.createFile(m, "itens.yml");
		FileManager.createFile(m, "jogadores.yml");
		FileManager.createFile(m, "encantamentos.yml");

		this.getServer().getPluginManager().registerEvents(new EntityDeath(), this);
		this.getServer().getPluginManager().registerEvents(new Interact(), this);
		this.getServer().getPluginManager().registerEvents(new PutTool(), this);
		this.getServer().getPluginManager().registerEvents(new Enchantments(), this);
		
		this.getCommand("encantar").setExecutor(new Encantar());
		
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(" ");
		console.sendMessage("§aENCANTAMENTOS:");
		console.sendMessage(" §aPlugin iniciado com sucesso.");
		console.sendMessage(" ");
	}

	public static Main getMain() {
		return m;
	}

}
