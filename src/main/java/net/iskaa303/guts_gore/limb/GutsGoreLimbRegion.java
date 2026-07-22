/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.limb;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum GutsGoreLimbRegion implements StringRepresentable {
    CRANIAL  ("cranial"     , "Cranial"),
    CERVICAL ("cervical"    , "Cervical"),
    THORACIC ("thoracic"    , "Thoracic"),
    FORELIMB ("forelimb"    , "Forelimb"),
    HINDLIMB ("hindlimb"    , "Hindlimb"),
    POSTERIOR("posterior"   , "Posterior"),
    DORSAL   ("dorsal"      , "Dorsal"),
    LATERAL  ("lateral"     , "Lateral"),
    FACIAL   ("facial"      , "Facial"),
    CEPHALIC ("cephalic"    , "Cephalic");

    private final String serializedName;
    private final String displayName;

    GutsGoreLimbRegion(String serializedName, String displayName) {
        this.serializedName = serializedName;
        this.displayName = displayName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
