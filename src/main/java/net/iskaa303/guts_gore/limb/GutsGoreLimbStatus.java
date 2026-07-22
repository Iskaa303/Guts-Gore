/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.limb;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * Describes the current physical state of a limb instance.
 */
public enum GutsGoreLimbStatus implements StringRepresentable {
    INTACT   ("intact"),
    INJURED  ("injured"),
    SEVERED  ("severed"),
    SHATTERED("shattered");

    private final String serializedName;

    GutsGoreLimbStatus(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }

    /** True if this limb is non-functional (gone or destroyed). */
    public boolean isDisabled() {
        return this == SEVERED || this == SHATTERED;
    }
}
