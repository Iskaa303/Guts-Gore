/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

/**
 * A limb profile defines which limbs an entity type has.
 *
 * Loaded from JSON at {@code data/{namespace}/guts_gore/limb_profiles/{entity_id}.json}.
 *
 * <pre>{@code
 * {
 *   "replace": false,
 *   "limbs": [
 *     { "id": "head",     "type": "head",    "max_health": 20, "critical": true, "model_parts": ["head"] },
 *     { "id": "left_arm", "type": "arm",     "max_health": 12, "model_parts": ["left_arm"] }
 *   ]
 * }
 * }</pre>
 */
public record GutsGoreLimbProfile(
    boolean replace,
    List<GutsGoreLimbDefinition> limbs
) {
    public static final Codec<GutsGoreLimbProfile> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.BOOL.optionalFieldOf("replace", false).forGetter(GutsGoreLimbProfile::replace),
        GutsGoreLimbDefinition.CODEC.listOf().fieldOf("limbs").forGetter(GutsGoreLimbProfile::limbs)
    ).apply(inst, GutsGoreLimbProfile::new));
}
