package rotang.ExtendsWorldEdit.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;

public class Manager
{
	private WorldEditPlugin worldedit;
	
	public Manager(WorldEditPlugin worldedit) 
	{
		this.worldedit = worldedit;
	}
	
	public Region getSelection(Player player)
	{
		LocalSession session = worldedit.getSession(player);
		try 
		{
			return session.getSelection(session.getSelectionWorld());
		} 
		catch (IncompleteRegionException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Block> getBlocks(Player player)
	{
		World w = player.getWorld();
		List<Block> blocks = new ArrayList<>();
		Region selection = getSelection(player);
		
		int xMin = selection.getMinimumPoint().getBlockX();
		int yMin = selection.getMinimumPoint().getBlockY();
		int zMin = selection.getMinimumPoint().getBlockZ();
		
		int xMax = selection.getMaximumPoint().getBlockX();
		int yMax = selection.getMaximumPoint().getBlockY();
		int zMax = selection.getMaximumPoint().getBlockZ();
		
		for(int x = xMin; x <= xMax; x++)
		{
			for(int y = yMin; y <= yMax; y++)
			{
				for(int z = zMin; z <= zMax; z++)
				{
					blocks.add(w.getBlockAt(x, y, z));
				}
			}
		}
		
		return blocks;
	}
	
	@SuppressWarnings("deprecation")
	public void createFakeBlock(Player player)
	{
		List<Block> blocks = getBlocks(player);
		Region selection = getSelection(player);
		
		int width = selection.getWidth();
		int height = selection.getHeight();
		int length = selection.getLength();
		
		double gap = 0.625;
		
		int index = 0;
		
		for(int w = 0; w < width; w++)
		{
			for(int h = 0; h < height; h++)
			{
				for(int l = 0; l < length; l++)
				{
					if(blocks.get(index).getType() == Material.AIR)
					{
						index++;
						continue;
					}
					
					Location loc = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
					Location targetPos = loc.add(w * gap, h * gap, l * gap);
					
					ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(targetPos, EntityType.ARMOR_STAND);
					
					as.setVisible(false);
					as.setGravity(false);
					as.setSilent(true);
					
					Block b = blocks.get(index);
					ItemStack item = new ItemStack(b.getBlockData().getMaterial(), 1, b.getData());
					as.getEquipment().setHelmet(item);
					
					index++;
				}
			}
		}
	}
}
