/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.limb;

import com.mojang.serialization.Codec;
import java.util.Locale;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * Every possible limb type across all Minecraft mobs.
 * These are intentionally general — any mob's body can be
 * described using only these 14 types, and the same types
 * are used across species so grafting is meaningful.
 */
public enum GutsGoreLimbType implements StringRepresentable {
    HEAD     ("head"     , GutsGoreLimbRegion.CRANIAL,       20.0f),
    NECK     ("neck"     , GutsGoreLimbRegion.CERVICAL,       8.0f),
    TORSO    ("torso"    , GutsGoreLimbRegion.THORACIC,      30.0f),
    ARM      ("arm"      , GutsGoreLimbRegion.FORELIMB,      12.0f),
    HAND     ("hand"     , GutsGoreLimbRegion.FORELIMB,       4.0f),
    LEG      ("leg"      , GutsGoreLimbRegion.HINDLIMB,      15.0f),
    FOOT     ("foot"     , GutsGoreLimbRegion.HINDLIMB,       3.0f),
    TAIL     ("tail"     , GutsGoreLimbRegion.POSTERIOR,      6.0f),
    WING     ("wing"     , GutsGoreLimbRegion.DORSAL,        10.0f),
    FIN      ("fin"      , GutsGoreLimbRegion.LATERAL,        5.0f),
    TENTACLE ("tentacle" , GutsGoreLimbRegion.CEPHALIC,       4.0f),
    HORN     ("horn"     , GutsGoreLimbRegion.CRANIAL,        3.0f),
    SNOUT    ("snout"    , GutsGoreLimbRegion.FACIAL,         5.0f),
    SHELL    ("shell"    , GutsGoreLimbRegion.DORSAL,        20.0f);

    public static final Codec<GutsGoreLimbType> CODEC = StringRepresentable.fromEnum(GutsGoreLimbType::values);

    private final String serializedName;
    private final GutsGoreLimbRegion region;
    private final float defaultMaxHealth;

    GutsGoreLimbType(String serializedName, GutsGoreLimbRegion region, float defaultMaxHealth) {
        this.serializedName = serializedName;
        this.region = region;
        this.defaultMaxHealth = defaultMaxHealth;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }

    /** Translation key: limb.guts_gore.{name} */
    public String getTranslationKey() {
        return "limb.guts_gore." + serializedName;
    }

    public GutsGoreLimbRegion getRegion() {
        return region;
    }

    /** Suggested maximum health for this limb type when no explicit value is given. */
    public float getDefaultMaxHealth() {
        return defaultMaxHealth;
    }
}
