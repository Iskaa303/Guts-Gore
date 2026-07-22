/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.Nullable;

/**
 * Functional interface for looking up a named {@link ModelPart} from an entity model.
 *
 * Each entity renderer type can register its own accessor so the
 * {@link GutsGoreLimbModelMapper} can hide parts for destroyed limbs.
 *
 * <h3>Example registration:</h3>
 * <pre>{@code
 * GutsGoreLimbModelMapper.registerAccessor(HumanoidModel.class, (model, name) -> {
 *     HumanoidModel<?> h = (HumanoidModel<?>) model;
 *     return switch (name) {
 *         case "head"      -> h.head;
 *         case "body"      -> h.body;
 *         case "right_arm" -> h.rightArm;
 *         case "left_arm"  -> h.leftArm;
 *         case "right_leg" -> h.rightLeg;
 *         case "left_leg"  -> h.leftLeg;
 *         default -> null;
 *     };
 * });
 * }</pre>
 *
 * Drafted for future use — no accessors are registered in this phase.
 */
@FunctionalInterface
public interface GutsGoreModelAccessor {
    /** Return the named ModelPart, or null if this model has no such part. */
    @Nullable ModelPart getPart(EntityModel<?> model, String partName);
}
