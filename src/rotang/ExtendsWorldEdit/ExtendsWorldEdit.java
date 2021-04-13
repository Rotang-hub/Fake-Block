package rotang.ExtendsWorldEdit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import net.md_5.bungee.api.ChatColor;
import rotang.ExtendsWorldEdit.Manager.Manager;

public class ExtendsWorldEdit extends JavaPlugin
{
	Manager manager;
	
	@Override
	public void onEnable() 
	{
		WorldEditPlugin worldedit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
		
		if(worldedit == null)
			Bukkit.broadcastMessage(ChatColor.RED + "WorldEdit Plugin is not exist");
		Bukkit.broadcastMessage(ChatColor.GREEN + "WorldEdit Plugin is exist");
		
		if(manager != null)
			return;
		manager = new Manager(worldedit);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{ 
		Player player = (Player) sender;
		
		if(label.equalsIgnoreCase("fakeblock"))
		{
			manager.createFakeBlock(player);
		}
		
		return false;
	}
}
