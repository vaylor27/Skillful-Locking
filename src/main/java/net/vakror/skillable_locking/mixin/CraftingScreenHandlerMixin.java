package net.vakror.skillable_locking.mixin;

import io.github.thatrobin.skillful.components.SkillPointInterface;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.vakror.skillable_locking.SkillableLockingMod;
import net.minecraft.client.gui.screen.TitleScreen;
import net.vakror.skillable_locking.locking.LockingUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {
	@Inject(at=@At("HEAD"), method = "updateResult", cancellable = true)
	private static void onUpdateCraftingGrid(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci){
		if (!world.isClient) {
			ServerPlayerEntity serverplayer = (ServerPlayerEntity) player;
			ItemStack craftResult = ItemStack.EMPTY;
			Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
			if (optional.isPresent()) {
				CraftingRecipe craftingrecipe = optional.get();
				if (resultInventory.shouldCraftRecipe(world, serverplayer, craftingrecipe)) {
					craftResult = craftingrecipe.craft(craftingInventory);
				}
			}

			if (!craftResult.isEmpty() && !LockingUtils.canCraftItem(serverplayer, craftResult.getItem())){
				ci.cancel();
				resultInventory.setStack(0, ItemStack.EMPTY);
			}
		}
	}
}
