package net.Cynosure.vividmod;

import net.Cynosure.vividmod.Items.EntityTrackerItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class VividClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		FabricModelPredicateProviderRegistry.register(VividMod.ENTITY_TRACKER_ITEM, new Identifier("angle"),
				new ModelPredicateProvider() {
					private double rotation;
					private double rota;
					private long lastUpdateTick;

					@Override
					public float call(ItemStack itemStack, ClientWorld world, LivingEntity livingEntity) {
						if (livingEntity == null && !itemStack.isInFrame()) {
							return 0.0F;
						} else {
							final boolean entityExists = livingEntity != null;
							final Entity entity = (Entity) (entityExists ? livingEntity : itemStack.getFrame());
							if (world == null && entity.world instanceof ClientWorld) {
								world = (ClientWorld) entity.world;
							}

							double rotation = entityExists ? (double) entity.yaw
									: getFrameRotation((ItemFrameEntity) entity);
							rotation = rotation % 360.0D;
							double adjusted = Math.PI
									- ((rotation - 90.0D) * 0.01745329238474369D - getAngle(world, entity, itemStack));

							if (entityExists) {
								adjusted = wobble(world, adjusted);
							}

							final float f = (float) (adjusted / (Math.PI * 2D));
							return MathHelper.floorMod(f, 1.0F);
						}
					}

					private double wobble(ClientWorld world, double amount) {
						if (world.getTime() != lastUpdateTick) {
							lastUpdateTick = world.getTime();
							double d0 = amount - rotation;
							d0 = d0 % (Math.PI * 2D);
							d0 = MathHelper.clamp(d0, -1.0D, 1.0D);
							rota += d0 * 0.1D;
							rota *= 0.8D;
							rotation += rota;
						}

						return rotation;
					}

					private double getFrameRotation(ItemFrameEntity itemFrame) {
						return (double) MathHelper
								.wrapDegrees(180 + itemFrame.getHorizontalFacing().getHorizontal() * 90);
					}

					private double getAngle(ClientWorld world, Entity entity, ItemStack itemStack) {
						if (itemStack.getItem() == VividMod.ENTITY_TRACKER_ITEM) {
							EntityTrackerItem tracker = (EntityTrackerItem) itemStack.getItem();
							BlockPos pos;
							if(tracker.getTarget() == null)
								pos = world.getSpawnPos();
							else
								pos = tracker.getTarget().getBlockPos();
							return Math.atan2((double) pos.getZ() - entity.getPos().z,
									(double) pos.getX() - entity.getPos().x);
						}
						return 0.0D;
					}
				});
	}
}