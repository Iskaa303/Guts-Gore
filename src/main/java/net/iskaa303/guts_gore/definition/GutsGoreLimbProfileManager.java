/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.definition;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

/**
 * Singleton cache of loaded limb profiles.
 *
 * Populated by {@link GutsGoreLimbReloadListener} during datapack reload.
 * Provides fast entity-type → profile lookups for limb initialisation.
 */
public final class GutsGoreLimbProfileManager
{
    private static final GutsGoreLimbProfileManager INSTANCE = new GutsGoreLimbProfileManager();

    private final Map<ResourceLocation, GutsGoreLimbProfile> profiles = new HashMap<>();

    public static GutsGoreLimbProfileManager getInstance() {
        return INSTANCE;
    }

    /** Called during datapack reload. Replaces the entire profile map. */
    public void reload(Map<ResourceLocation, GutsGoreLimbProfile> newProfiles) {
        profiles.clear();
        profiles.putAll(newProfiles);
    }

    /** Get the limb profile for an entity type, or null if none is defined. */
    public @Nullable GutsGoreLimbProfile getProfile(EntityType<?> entityType) {
        return profiles.get(EntityType.getKey(entityType));
    }

    /** True if at least one profile is loaded. */
    public boolean hasAnyProfiles() {
        return !profiles.isEmpty();
    }

    /**
     * Get the merged profile for an entity type, respecting {@code replace} flags.
     * If {@code replace: true} is set, it overrides; if multiple datapacks define
     * the same entity and none has replace, limbs are merged (last wins per id).
     *
     * For now this returns the raw profile — merge logic will be added when
     * multiple datapack sources are supported. @see GutsGoreLimbReloadListener
     */
    public @Nullable GutsGoreLimbProfile getMergedProfile(EntityType<?> entityType) {
        return getProfile(entityType);
    }
}
