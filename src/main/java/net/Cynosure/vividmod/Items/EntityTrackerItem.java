package net.Cynosure.vividmod.Items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EntityTrackerItem extends Item {

	private LivingEntity target = null;

	public EntityTrackerItem() {
		super(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
	
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		target = entity;
		return super.useOnEntity(stack, user, entity, hand);
	}

	public LivingEntity getTarget()
	{
		return target;
	}
}