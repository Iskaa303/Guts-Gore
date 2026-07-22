/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.attachment;

import java.util.function.Supplier;
import net.iskaa303.guts_gore.GutsGoreMod;
import net.iskaa303.guts_gore.limb.GutsGoreLimbContainer;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Deferred register for all entity attachment types.
 */
public final class GutsGoreAttachments
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, GutsGoreMod.MOD_ID);

    /**
     * Attachment holding all runtime limb state for a living entity.
     * Initialised to an empty container — profiles add limbs on join.
     */
    public static final Supplier<AttachmentType<GutsGoreLimbContainer>> LIMB_CONTAINER =
        ATTACHMENT_TYPES.register("limb_container",
            () -> AttachmentType.builder(GutsGoreLimbContainer::new).build()
        );
}
