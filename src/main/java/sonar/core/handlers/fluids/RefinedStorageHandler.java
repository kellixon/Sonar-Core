package sonar.core.handlers.fluids;

import java.util.Collection;
import java.util.List;

import com.raoulvdberge.refinedstorage.api.network.INetworkMaster;
import com.raoulvdberge.refinedstorage.api.network.INetworkNode;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import sonar.core.api.SonarAPI;
import sonar.core.api.StorageSize;
import sonar.core.api.asm.FluidHandler;
import sonar.core.api.asm.InventoryHandler;
import sonar.core.api.fluids.ISonarFluidHandler;
import sonar.core.api.fluids.StoredFluidStack;
import sonar.core.api.inventories.ISonarInventoryHandler;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.api.utils.ActionType;
import sonar.core.handlers.inventories.DrawersInventoryHandler;

@FluidHandler(modid = "refinedstorage", handlerID = RefinedStorageHandler.name, priority = 2)
public class RefinedStorageHandler implements ISonarFluidHandler {

	public static final String name = "RefinedStorage";

	@Override
	public boolean canHandleFluids(TileEntity tile, EnumFacing dir) {
		return tile instanceof INetworkNode;
	}

	@Override
	public StoredFluidStack addStack(StoredFluidStack add, TileEntity tile, EnumFacing dir, ActionType action) {
		INetworkNode node = (INetworkNode) tile;
		INetworkMaster network = node.getNetwork();
		if (network != null) {
			int toAdd = (int) Math.min(Integer.MAX_VALUE, add.stored);
			FluidStack stack = network.insertFluid(add.getFullStack(), toAdd, action.shouldSimulate());
			add.stored -= stack == null ? toAdd : toAdd - stack.amount;
		}
		return add;
	}

	@Override
	public StoredFluidStack removeStack(StoredFluidStack remove, TileEntity tile, EnumFacing dir, ActionType action) {
		INetworkNode node = (INetworkNode) tile;
		INetworkMaster network = node.getNetwork();
		if (network != null) {
			int toRemove = (int) Math.min(Integer.MAX_VALUE, remove.stored);
			FluidStack stack = network.extractFluid(remove.getFullStack(), toRemove, action.shouldSimulate());
			remove.stored -= stack == null ? 0 : stack.amount;
		}
		return remove;
	}

	@Override
	public StorageSize getFluids(List<StoredFluidStack> fluids, TileEntity tile, EnumFacing dir) {
		INetworkNode node = (INetworkNode) tile;
		INetworkMaster network = node.getNetwork();
		if (network != null) {
			Collection<FluidStack> stacks = network.getFluidStorageCache().getList().getStacks();
			for (FluidStack stack : stacks) {
				SonarAPI.getFluidHelper().addFluidToList(fluids, new StoredFluidStack(stack));
			}
		}
		return new StorageSize(0, 0); // doesn't show storage yet
	}

}