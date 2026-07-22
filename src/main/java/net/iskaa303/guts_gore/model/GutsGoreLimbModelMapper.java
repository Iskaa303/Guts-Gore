/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.model;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.iskaa303.guts_gore.attachment.GutsGoreAttachments;
import net.iskaa303.guts_gore.limb.GutsGoreLimb;
import net.iskaa303.guts_gore.limb.GutsGoreLimbContainer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/**
 * Reads a {@link GutsGoreLimbContainer} from an entity and applies
 * the limb state to the entity's {@link EntityModel} at render time.
 *
 * Limbs that are {@link net.iskaa303.guts_gore.limb.GutsGoreLimbStatus#SEVERED SEVERED}
 * or {@link net.iskaa303.guts_gore.limb.GutsGoreLimbStatus#SHATTERED SHATTERED}
 * have their corresponding model parts hidden.
 *
 * <h3>Model accessor registry</h3>
 * Because model structures vary (HumanoidModel has fields, HierarchicalModel
 * uses a root with named children, etc.), each model class needs a registered
 * {@link GutsGoreModelAccessor} to locate parts by name.
 *
 * Drafted for future use — the accessor registry starts empty.
 * No render hooks are wired in this phase.
 */
public final class GutsGoreLimbModelMapper
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<Class<?>, GutsGoreModelAccessor> ACCESSORS = new HashMap<>();

    private GutsGoreLimbModelMapper() {}

    /**
     * Register a model accessor for a specific model class.
     * The most specific superclass match wins at lookup time.
     */
    public static void registerAccessor(Class<?> modelClass, GutsGoreModelAccessor accessor) {
        ACCESSORS.put(modelClass, accessor);
    }

    /**
     * Apply limb state to the model:
     * hides ModelParts for severed/shattered limbs.
     *
     * Safe to call every frame. Does nothing if:
     * <ul>
     *   <li>The entity has no {@code LimbContainer} attachment</li>
     *   <li>The container is empty (no limbs assigned)</li>
     *   <li>No {@link GutsGoreModelAccessor} is registered for this model class</li>
     * </ul>
     */
    public static void apply(LivingEntity entity, EntityModel<?> model) {
        if (!entity.hasData(GutsGoreAttachments.LIMB_CONTAINER.get())) return;

        GutsGoreLimbContainer container = entity.getData(GutsGoreAttachments.LIMB_CONTAINER.get());
        if (container == null || container.isEmpty()) return;

        GutsGoreModelAccessor accessor = findAccessor(model.getClass());
        if (accessor == null) {
            return;
        }

        for (GutsGoreLimb limb : container.getAllLimbs()) {
            if (!limb.status().isDisabled()) continue;

            for (String partName : limb.modelParts()) {
                ModelPart part = accessor.getPart(model, partName);
                if (part != null) {
                    part.visible = false;
                }
            }
        }
    }

    /** Walk the class hierarchy to find the most specific registered accessor. */
    @Nullable
    private static GutsGoreModelAccessor findAccessor(Class<?> modelClass) {
        // Exact match
        GutsGoreModelAccessor accessor = ACCESSORS.get(modelClass);
        if (accessor != null) return accessor;

        // Walk superclass chain
        Class<?> current = modelClass;
        while (current != null) {
            // Check interfaces
            for (Class<?> iface : current.getInterfaces()) {
                accessor = ACCESSORS.get(iface);
                if (accessor != null) return accessor;
            }
            // Check superclass
            current = current.getSuperclass();
            if (current != null) {
                accessor = ACCESSORS.get(current);
                if (accessor != null) return accessor;
            }
        }
        return null;
    }
}
