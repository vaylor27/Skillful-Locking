package net.vakror.skillable_locking;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.vakror.skillable_locking.locking.SkillLockType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SkillableLockingMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("skillable_locking");
	public static Map<Item, String> itemCraftingLocks = new HashMap<>();
	public static Map<Item, String> itemUsingLocks = new HashMap<>();
	public static Map<Block, String> blockUseLocks = new HashMap<>();

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
	}
}
