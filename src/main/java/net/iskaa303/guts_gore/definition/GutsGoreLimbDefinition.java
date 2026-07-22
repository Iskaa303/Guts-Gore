/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.iskaa303.guts_gore.limb.GutsGoreLimbType;

/**
 * JSON-serialisable definition of one limb entry in a profile.
 *
 * <pre>{@code
 * {
 *   "id": "head",
 *   "type": "head",
 *   "max_health": 20.0,
 *   "critical": true,
 *   "model_parts": ["head"],
 *   "parent": "torso"
 * }
 * }</pre>
 */
public record GutsGoreLimbDefinition(
    String id,
    GutsGoreLimbType type,
    float maxHealth,
    boolean critical,
    List<String> modelParts,
    Optional<String> parent
) {
    public static final Codec<GutsGoreLimbDefinition> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.STRING.fieldOf("id").forGetter(GutsGoreLimbDefinition::id),
        GutsGoreLimbType.CODEC.fieldOf("type").forGetter(GutsGoreLimbDefinition::type),
        Codec.FLOAT.optionalFieldOf("max_health", 0.0f).forGetter(GutsGoreLimbDefinition::maxHealth),
        Codec.BOOL.optionalFieldOf("critical", false).forGetter(GutsGoreLimbDefinition::critical),
        Codec.STRING.listOf().optionalFieldOf("model_parts", List.of()).forGetter(GutsGoreLimbDefinition::modelParts),
        Codec.STRING.optionalFieldOf("parent").forGetter(GutsGoreLimbDefinition::parent)
    ).apply(inst, GutsGoreLimbDefinition::new));

    public GutsGoreLimbDefinition {
        modelParts = modelParts == null ? List.of() : Collections.unmodifiableList(modelParts);
    }

    /** Resolved max health: explicit value if set, otherwise the type default. */
    public float effectiveMaxHealth() {
        return maxHealth > 0 ? maxHealth : type.getDefaultMaxHealth();
    }
}
