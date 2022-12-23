package net.vakror.skillable_locking.locking;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.PowerType;
import io.github.thatrobin.skillful.components.SkillPointInterface;
import io.github.thatrobin.skillful.skill_trees.Skill;
import io.github.thatrobin.skillful.skill_trees.SkillManager;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.vakror.skillable_locking.SkillableLockingMod;

import java.util.Objects;
import java.util.stream.Stream;

public class LockingUtils {
    public static boolean canCraftItem(PlayerEntity player, Item item) {
        String skillString = SkillableLockingMod.itemCraftingLocks.get(item);
        return hasSkill(player, skillString);
    }

    public static boolean canUseBlock(PlayerEntity player, Block block) {
        String skillString = SkillableLockingMod.blockUseLocks.get(block);
        return hasSkill(player, skillString);
    }

    public static boolean canUseItem(PlayerEntity player, Item item) {
        String skillString = SkillableLockingMod.itemUsingLocks.get(item);
        return hasSkill(player, skillString);
    }

    public static boolean hasSkill(PlayerEntity player, String skillString) {
        SkillManager manager = new SkillManager();
        Skill skill = manager.get(new Identifier(skillString.split(":")[0], skillString.split(":")[1]));
        assert MinecraftClient.getInstance().player != null;
        PowerHolderComponent component = PowerHolderComponent.KEY.get(player);
        assert skill != null;
        if (skill.getPowers() != null) {
            Stream<PowerType<?>> stream = skill.getPowers().stream();
            Objects.requireNonNull(component);
            return stream.allMatch(component::hasPower);
        }
        return false;
    }
}
