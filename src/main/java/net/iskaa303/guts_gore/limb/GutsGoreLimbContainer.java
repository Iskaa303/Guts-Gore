/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.limb;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

/**
 * Runtime limb-data container attached to every living entity.
 *
 * Stores all limb instances and provides fast lookups
 * by limb ID or by model-part name.
 *
 * Drafted as pure infrastructure — no entity actually gets
 * limbs assigned until a {@code LimbProfile} is loaded.
 */
public class GutsGoreLimbContainer
{
    private final Map<ResourceLocation, GutsGoreLimb> limbs = new HashMap<>();
    private final Map<String, ResourceLocation> modelPartIndex = new HashMap<>();

    /** Add or replace a limb. */
    public void setLimb(GutsGoreLimb limb) {
        Objects.requireNonNull(limb);
        limbs.put(limb.id(), limb);
        for (String partName : limb.modelParts()) {
            modelPartIndex.put(partName, limb.id());
        }
    }

    /** Remove a limb by ID. */
    public void removeLimb(ResourceLocation id) {
        GutsGoreLimb old = limbs.remove(id);
        if (old != null) {
            for (String partName : old.modelParts()) {
                modelPartIndex.remove(partName, id);
            }
        }
    }

    /** Damage a limb by a flat amount. Clamps to zero. Returns the actual damage dealt. */
    public float damageLimb(ResourceLocation id, float amount) {
        GutsGoreLimb limb = limbs.get(id);
        if (limb == null || limb.isFunctional()) return 0;
        float actual = Math.min(amount, limb.currentHealth());
        setLimb(limb.withHealth(limb.currentHealth() - actual));
        return actual;
    }

    /** Heal a limb. Returns the actual health restored. */
    public float healLimb(ResourceLocation id, float amount) {
        GutsGoreLimb limb = limbs.get(id);
        if (limb == null) return 0;
        float missing = limb.maxHealth() - limb.currentHealth();
        float actual = Math.min(amount, missing);
        setLimb(limb.withHealth(limb.currentHealth() + actual));
        return actual;
    }

    /** Look up a limb by its unique ID. */
    public GutsGoreLimb getLimb(ResourceLocation id) {
        return limbs.get(id);
    }

    /** Find which limb owns a given model-part name. */
    public GutsGoreLimb getLimbByModelPart(String partName) {
        ResourceLocation id = modelPartIndex.get(partName);
        return id != null ? limbs.get(id) : null;
    }

    /** All limbs, unmodifiable. */
    public Collection<GutsGoreLimb> getAllLimbs() {
        return Collections.unmodifiableCollection(limbs.values());
    }

    /** Sum of current health across all limbs. */
    public float getTotalHealth() {
        float total = 0;
        for (GutsGoreLimb limb : limbs.values()) {
            total += limb.currentHealth();
        }
        return total;
    }

    /** Sum of max health across all limbs. */
    public float getMaxHealth() {
        float total = 0;
        for (GutsGoreLimb limb : limbs.values()) {
            total += limb.maxHealth();
        }
        return total;
    }

    /** True if this entity has any limb data at all. */
    public boolean isEmpty() {
        return limbs.isEmpty();
    }
}
