package sonar.core.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public interface ISpecialTooltip {
	
    /**
     * add information to the tool tip if the stack has a TagCompound
     * @param tag TODO
     */
    void addSpecialToolTip(ItemStack stack, World world, List<String> list, @Nullable NBTTagCompound tag);

}
