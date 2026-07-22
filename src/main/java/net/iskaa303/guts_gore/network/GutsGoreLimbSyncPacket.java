/*
 * Licensed under the GPL, Version 3.0.
 * You may obtain a copy of the Licence at:
 * https://www.gnu.org/licenses/gpl-3.0.html
 */

package net.iskaa303.guts_gore.network;

/**
 * Stub for syncing limb state from server to client.
 *
 * When a limb is damaged, severed, or healed on the server,
 * this packet sends the updated state to clients so the model
 * mapper can react (hide parts, show stumps, etc.).
 *
 * Not implemented yet — all limb logic is server-authoritative
 * and will be synced in a future phase.
 */
public class GutsGoreLimbSyncPacket {
    // Future: record containing entity ID + limb ID + new health/status
    // Sent via NeoForge's PlayPayloadHandler
}
