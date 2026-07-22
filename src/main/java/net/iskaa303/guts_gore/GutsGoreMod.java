/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore;

import com.mojang.logging.LogUtils;
import net.iskaa303.guts_gore.attachment.GutsGoreAttachments;
import net.iskaa303.guts_gore.definition.GutsGoreLimbReloadListener;
import net.iskaa303.guts_gore.event.GutsGoreEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(GutsGoreMod.MOD_ID)
public final class GutsGoreMod
{
    public static final String MOD_ID = "guts_gore";
    private static final Logger LOGGER = LogUtils.getLogger();

    public GutsGoreMod(IEventBus modBus)
    {
        LOGGER.info("Guts & Gore limb system initializing");

        // Register attachment type deferred register onto the mod bus
        GutsGoreAttachments.ATTACHMENT_TYPES.register(modBus);

        // Register event handlers on the mod bus and the NeoForge bus
        GutsGoreEvents.register(modBus);
    }
}
