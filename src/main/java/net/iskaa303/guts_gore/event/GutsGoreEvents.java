/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.event;

import net.iskaa303.guts_gore.GutsGoreMod;
import net.iskaa303.guts_gore.attachment.GutsGoreAttachments;
import net.iskaa303.guts_gore.definition.GutsGoreLimbProfileManager;
import net.iskaa303.guts_gore.definition.GutsGoreLimbReloadListener;
import net.iskaa303.guts_gore.limb.GutsGoreLimbContainer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/**
 * Event bus handlers for the limb system.
 *
 * Handles:
 * <ul>
 *   <li>Registering the datapack reload listener</li>
 *   <li>Initialising the {@link GutsGoreLimbContainer} when an entity joins the world</li>
 * </ul>
 *
 * Currently no profiles are shipped, so the container stays empty
 * on all entities until a datapack provides a limb profile.
 */
public final class GutsGoreEvents
{
    private GutsGoreEvents() {}

    /** Register listeners on both the mod bus and the NeoForge event bus. */
    public static void register(IEventBus modBus) {
        var neoBus = net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

        // Wire the datapack reload listener
        neoBus.addListener(GutsGoreEvents::onAddReloadListeners);

        // Initialise limb data when an entity spawns
        neoBus.addListener(GutsGoreEvents::onEntityJoinLevel);
    }

    /** Add our datapack reload listener so JSON profiles are picked up. */
    private static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener((PreparableReloadListener) new GutsGoreLimbReloadListener());
    }

    /**
     * When a living entity joins the world, try to initialise a limb container
     * from its entity type's profile.
     *
     * Currently a no-op because no profiles are shipped.
     * When a datapack provides a profile, this creates the container
     * and populates it with the defined limbs.
     */
    private static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity living)) return;

        // Ensure the attachment exists (will be populated later when profiles are active)
        if (!living.hasData(GutsGoreAttachments.LIMB_CONTAINER.get())) {
            // The attachment is created lazily on first access via getData()
            // or we can initialise it here:
            living.setData(GutsGoreAttachments.LIMB_CONTAINER.get(), new GutsGoreLimbContainer());
        }
    }
}
