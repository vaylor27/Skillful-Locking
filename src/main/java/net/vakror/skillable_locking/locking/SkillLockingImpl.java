package net.vakror.skillable_locking.locking;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.vakror.skillable_locking.SkillableLockingMod;

public class SkillLockingImpl implements SkillLocking {
    @Override
    public void createLock(String skillID, Item item, SkillLockType.Item type) {
        switch (type) {
            case USING -> createItemUsingLock(skillID, item);
            case CRAFTING -> createCraftingLock(skillID, item);
        }
    }

    private void createItemUsingLock(String skillID, Item item) {
        SkillableLockingMod.itemUsingLocks.put(item, skillID);
    }

    private void createBlockUsingLock(String skillID, Block block) {
        SkillableLockingMod.blockUseLocks.put(block, skillID);
    }

    private void createCraftingLock(String skillID, Item item) {
        SkillableLockingMod.itemCraftingLocks.put(item, skillID);
    }

    @Override
    public void createLock(String skillID, Block block, SkillLockType.Block type) {
        switch (type) {
            case USING -> createBlockUsingLock(skillID, block);
        }
    }
}
