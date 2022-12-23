package net.vakror.skillable_locking.locking;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface SkillLocking {
    public void createLock(String skillID, Item item, SkillLockType.Item type);
    public void createLock(String skillID, Block block, SkillLockType.Block type);
}
