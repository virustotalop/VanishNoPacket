package org.kitteh.vanish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Controller of announcing joins and quits that aren't their most honest.
 * Note that delayed announce methods can be called without checking
 * to see if it's enabled first. The methods confirm before doing anything
 * particularly stupid.
 */
public final class VanishAnnounceManipulator {
    private final List<String> delayedAnnouncePlayerList;
    private final VanishPlugin plugin;
    private final Map<String, Boolean> playerOnlineStatus;

    VanishAnnounceManipulator(VanishPlugin plugin) {
        this.plugin = plugin;
        this.playerOnlineStatus = new HashMap<String, Boolean>();
        this.delayedAnnouncePlayerList = new ArrayList<String>();
    }

    public void addToDelayedAnnounce(String player) {
        this.playerOnlineStatus.put(player, false);
        if (!Settings.getAutoFakeJoinSilent()) {
            return;
        }
        this.delayedAnnouncePlayerList.add(player);
    }

    /**
     * Removes a player's delayed announce
     *
     * @param player name of the player
     */
    public void dropDelayedAnnounce(String player) {
        this.delayedAnnouncePlayerList.remove(player);
    }

    /**
     * Gets the fake online status of a player
     * Called by JSONAPI
     *
     * @param playerName name of the player to query
     * @return true if player is considered online, false if not (or if not on server)
     */
    public boolean getFakeOnlineStatus(String playerName) {
        final Player player = this.plugin.getServer().getPlayerExact(playerName);
        if (player == null) {
            return false;
        }
        playerName = player.getName();
        if (this.playerOnlineStatus.containsKey(playerName)) {
            return this.playerOnlineStatus.get(playerName);
        } else {
            return true;
        }
    }

    /**
     * Marks a player as quit
     * Called when a player quits
     * 
     * @param player name of the player who just quit
     * @return the former fake online status of the player
     */
    public boolean playerHasQuit(String player) {
        if (this.playerOnlineStatus.containsKey(player)) {
            return this.playerOnlineStatus.remove(player);
        }
        return true;
    }

    private String injectPlayerInformation(String message, Player player) {
        message = message.replace("%p", player.getName());
        message = message.replace("%d", player.getDisplayName());
        return message;
    }

    void fakeJoin(Player player, boolean force) {
        if (force || !(this.playerOnlineStatus.containsKey(player.getName()) && this.playerOnlineStatus.get(player.getName()))) {
            this.plugin.getServer().broadcastMessage(ChatColor.YELLOW + this.injectPlayerInformation(Settings.getFakeJoin(), player));
            this.plugin.getLogger().info(player.getName() + " faked joining");
            this.playerOnlineStatus.put(player.getName(), true);
        }
    }

    void fakeQuit(Player player, boolean force) {
        if (force || !(this.playerOnlineStatus.containsKey(player.getName()) && !this.playerOnlineStatus.get(player.getName()))) {
            this.plugin.getServer().broadcastMessage(ChatColor.YELLOW + this.injectPlayerInformation(Settings.getFakeQuit(), player));
            this.plugin.getLogger().info(player.getName() + " faked quitting");
            this.playerOnlineStatus.put(player.getName(), false);
        }
    }

    void vanishToggled(Player player) {
        if (!Settings.getAutoFakeJoinSilent() || !this.delayedAnnouncePlayerList.contains(player.getName())) {
            return;
        }
        this.fakeJoin(player, false);
        this.dropDelayedAnnounce(player.getName());
    }
}