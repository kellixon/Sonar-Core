package sonar.core.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sonar.core.api.inventories.StoredItemStack;
import sonar.core.inventory.SonarLargeInventory;
import sonar.core.utils.SonarCompat;

public class SlotLarge extends Slot {
	public SonarLargeInventory largeInv;

	public SlotLarge(SonarLargeInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(null, index, xPosition, yPosition);
		largeInv = inventoryIn;
	}

    @Override
	public int getSlotStackLimit() {
		return largeInv.numStacks * 64;
	}

    @Override
	public boolean isItemValid(ItemStack stack) {
		if (SonarCompat.isEmpty(stack))
			return false;
		StoredItemStack stored = largeInv.getLargeStack(getSlotIndex());
        return stored == null ? largeInv.isItemValidForSlot(getSlotIndex(), stack) : stored.equalStack(stack);
	}

    @Override
	public ItemStack getStack() {
		StoredItemStack stored = largeInv.getLargeStack(getSlotIndex());
		if (stored != null && stored.getStackSize()!=0) {
			ItemStack item = stored.getFullStack();
			item = SonarCompat.setCount(item, (int) stored.stored);
			return item;
		}
		return SonarCompat.getEmpty();
	}

    @Override
	public void putStack(ItemStack stack) {
		largeInv.slots[getSlotIndex()] = stack != null && SonarCompat.getCount(stack) != 0 ? new StoredItemStack(stack) : null;
		onSlotChanged();
	}

    @Override
	public ItemStack decrStackSize(int amount) {
		return this.inventory.decrStackSize(getSlotIndex() * largeInv.numStacks, amount);
	}

    @Override
	public void onSlotChanged() {
		largeInv.markDirty();
	}

    @Override
	public boolean isHere(IInventory inv, int slotIn) {
		return slotIn == this.getSlotIndex();
	}

    @Override
	public boolean isSameInventory(Slot other) {
        return other instanceof SlotLarge && this.largeInv == ((SlotLarge) other).largeInv;
	}
}
