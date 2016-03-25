package sonar.core.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IGuiTile {

	public static int ID = -2;

	public Object getGuiContainer(EntityPlayer player);

	public Object getGuiScreen(EntityPlayer player);
}
