/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.limb;

import java.util.Collections;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

/**
 * One limb instance on a specific living entity.
 *
 * Each limb carries:
 * <ul>
 *   <li>A unique ID for identification across the container</li>
 *   <li>A type (head, arm, leg, …)</li>
 *   <li>Its own health pool (current + max)</li>
 *   <li>A functional status</li>
 *   <li>A list of model-part names this limb controls</li>
 *   <li>An optional parent limb (for the connection hierarchy)</li>
 * </ul>
 *
 * @param id          Unique identifier (e.g. {@code guts_gore:zombie/left_arm})
 * @param type        General category of limb
 * @param maxHealth   Maximum health pool for this specific limb
 * @param currentHealth Current health
 * @param status      INTACT / INJURED / SEVERED / SHATTERED
 * @param critical    If true, the entity dies when this limb is destroyed
 * @param modelParts  Names of ModelPart cuboids this limb maps to in the entity's model
 * @param parentLimb  ResourceLocation of the parent limb (null = attaches to torso)
 */
public record GutsGoreLimb(
    ResourceLocation id,
    GutsGoreLimbType type,
    float maxHealth,
    float currentHealth,
    GutsGoreLimbStatus status,
    boolean critical,
    List<String> modelParts,
    ResourceLocation parentLimb
) {
    public GutsGoreLimb {
        // Defensive copy for immutability
        modelParts = modelParts == null ? List.of() : Collections.unmodifiableList(modelParts);
    }

    /** Convenience: is this limb functional? */
    public boolean isFunctional() {
        return !status.isDisabled() && currentHealth > 0;
    }

    /** Health ratio 0.0 – 1.0 */
    public float healthFraction() {
        return maxHealth <= 0 ? 0 : Math.clamp(currentHealth / maxHealth, 0, 1);
    }

    /** Create a copy with updated health. */
    public GutsGoreLimb withHealth(float newHealth) {
        return new GutsGoreLimb(id, type, maxHealth, Math.clamp(newHealth, 0, maxHealth), status, critical, modelParts, parentLimb);
    }

    /** Create a copy with updated status. */
    public GutsGoreLimb withStatus(GutsGoreLimbStatus newStatus) {
        return new GutsGoreLimb(id, type, maxHealth, currentHealth, newStatus, critical, modelParts, parentLimb);
    }
}
