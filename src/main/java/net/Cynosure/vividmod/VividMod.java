package net.Cynosure.vividmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.Cynosure.vividmod.Items.EntityTrackerItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class VividMod implements ModInitializer {

	public static final String MODID = "vividmod";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static final EntityTrackerItem ENTITY_TRACKER_ITEM = new EntityTrackerItem();

	@Override
	public void onInitialize() {
		System.out.println("Init Mod");
		Registry.register(Registry.ITEM, new Identifier(MODID, "entity_tracker"), ENTITY_TRACKER_ITEM);
	}
}