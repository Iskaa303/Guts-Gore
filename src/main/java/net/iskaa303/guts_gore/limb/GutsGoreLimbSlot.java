/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.limb;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * Connection points where limbs attach to each other.
 * These create the limb hierarchy (torso → arm → hand, etc.)
 * and enable grafting: any limb with matching slots can be attached.
 *
 * Drafted for future use — not wired into any game logic yet.
 */
public enum GutsGoreLimbSlot implements StringRepresentable {
    HEAD_SOCKET     ("head_socket",      GutsGoreLimbType.HEAD),
    NECK_SOCKET     ("neck_socket",      GutsGoreLimbType.NECK),
    TORSO_SOCKET    ("torso_socket",     GutsGoreLimbType.TORSO),
    ARM_SOCKET_L    ("arm_socket_l",     GutsGoreLimbType.ARM),
    ARM_SOCKET_R    ("arm_socket_r",     GutsGoreLimbType.ARM),
    HAND_SOCKET_L   ("hand_socket_l",    GutsGoreLimbType.HAND),
    HAND_SOCKET_R   ("hand_socket_r",    GutsGoreLimbType.HAND),
    LEG_SOCKET_L    ("leg_socket_l",     GutsGoreLimbType.LEG),
    LEG_SOCKET_R    ("leg_socket_r",     GutsGoreLimbType.LEG),
    FOOT_SOCKET_L   ("foot_socket_l",    GutsGoreLimbType.FOOT),
    FOOT_SOCKET_R   ("foot_socket_r",    GutsGoreLimbType.FOOT),
    TAIL_SOCKET     ("tail_socket",      GutsGoreLimbType.TAIL),
    WING_SOCKET_L   ("wing_socket_l",    GutsGoreLimbType.WING),
    WING_SOCKET_R   ("wing_socket_r",    GutsGoreLimbType.WING),
    FIN_SOCKET_L    ("fin_socket_l",     GutsGoreLimbType.FIN),
    FIN_SOCKET_R    ("fin_socket_r",     GutsGoreLimbType.FIN),
    HORN_SOCKET_L   ("horn_socket_l",    GutsGoreLimbType.HORN),
    HORN_SOCKET_R   ("horn_socket_r",    GutsGoreLimbType.HORN);

    private final String serializedName;
    private final GutsGoreLimbType acceptedType;

    GutsGoreLimbSlot(String serializedName, GutsGoreLimbType acceptedType) {
        this.serializedName = serializedName;
        this.acceptedType = acceptedType;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }

    /** The limb type that fits this slot. */
    public GutsGoreLimbType getAcceptedType() {
        return acceptedType;
    }
}
