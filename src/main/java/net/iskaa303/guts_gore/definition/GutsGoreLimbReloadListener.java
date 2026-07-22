/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.definition;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import java.util.HashMap;
import java.util.Map;
import net.iskaa303.guts_gore.GutsGoreMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

/**
 * Loads limb profiles from datapack JSON files.
 *
 * Files located at: {@code data/{namespace}/guts_gore/limb_profiles/{entity_id}.json}
 *
 * No profiles are shipped with the mod — this is pure infrastructure.
 * Drop a datapack with the right path and the system picks it up.
 */
public class GutsGoreLimbReloadListener extends SimpleJsonResourceReloadListener
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DIRECTORY = "guts_gore/limb_profiles";

    public GutsGoreLimbReloadListener() {
        super(new Gson(), DIRECTORY);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceLocation, GutsGoreLimbProfile> profiles = new HashMap<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
            ResourceLocation fileId = entry.getKey();
            JsonElement json = entry.getValue();

            GutsGoreLimbProfile.CODEC.parse(JsonOps.INSTANCE, json)
                .resultOrPartial(error -> LOGGER.warn("Failed to parse limb profile {}: {}", fileId, error))
                .ifPresent(profile -> {
                    // The file path is like "guts_gore/limb_profiles/zombie.json"
                    // The key is the file path without extension: "zombie"
                    // We resolve it as a resource location under minecraft namespace
                    // Actually, the key is like "namespace:path" already
                    // We want the entity ID from the file path
                    ResourceLocation entityId = fileId;
                    profiles.put(entityId, profile);
                    LOGGER.debug("Loaded limb profile for {} ({} limbs)", entityId, profile.limbs().size());
                });
        }

        GutsGoreLimbProfileManager.getInstance().reload(profiles);
        LOGGER.info("Loaded {} limb profiles from datapacks", profiles.size());
    }
}
