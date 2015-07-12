package org.kitteh.vanish;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class VanishPerms {
    private static Map<String, VanishUser> users = Collections.synchronizedMap(new HashMap<String, VanishUser>());

    public static boolean blockIncomingDamage(Player player) {
        return VanishPerms.getUser(player).getPreventIncomingDamage();
    }

    public static boolean blockOutgoingDamage(Player player) {
        return VanishPerms.getUser(player).getPreventOutgoingDamage();
    }

    public static boolean canFakeAnnounce(Player player) {
        return player.hasPermission("vanish.fakeannounce");
    }

    public static boolean canList(CommandSender sender) {
        return sender.hasPermission("vanish.list");
    }

    public static boolean canNotFollow(Player player) {
        return VanishPerms.getUser(player).getNoFollow();
    }

    public static boolean canNotHunger(Player player) {
        return VanishPerms.getUser(player).getNoHunger();
    }

    public static boolean canNotInteract(Player player) {
        return VanishPerms.getUser(player).getNoInteract();
    }

    public static boolean canNotPickUp(Player player) {
        return VanishPerms.getUser(player).getNoPickup();
    }

    public static boolean canNotTrample(Player player) {
        return player.hasPermission("vanish.notrample");
    }

    public static boolean canReceiveAdminAlerts(Player player) {
        return player.hasPermission("vanish.adminalerts");
    }

    public static boolean canReload(CommandSender sender) {
        return sender.hasPermission("vanish.reload");
    }

    public static boolean canSeeAll(Player player) {
        return VanishPerms.getUser(player).getSeeAll();
    }

    public static boolean canSeeSpoutStatus(Player player) {
        return player.hasPermission("vanish.spout.status");
    }

    public static boolean canSeeStatusUpdates(Player player) {
        return player.hasPermission("vanish.statusupdates");
    }

    public static boolean canToggleDamageIn(Player player) {
        return player.hasPermission("vanish.toggle.damagein");
    }

    public static boolean canToggleDamageOut(Player player) {
        return player.hasPermission("vanish.toggle.damageout");
    }

    public static boolean canToggleNoFollow(Player player) {
        return player.hasPermission("vanish.toggle.nofollow");
    }

    public static boolean canToggleNoHunger(Player player) {
        return player.hasPermission("vanish.toggle.nohunger");
    }

    public static boolean canToggleNoInteract(Player player) {
        return player.hasPermission("vanish.toggle.nointeract");
    }

    public static boolean canToggleNoPickup(Player player) {
        return player.hasPermission("vanish.toggle.nopickup");
    }

    public static boolean canToggleSee(Player player) {
        return player.hasPermission("vanish.toggle.see");
    }

    public static boolean canVanish(Player player) {
        return player.hasPermission("vanish.vanish");
    }

    public static boolean canVanishOff(Player player) {
        return player.hasPermission("vanish.vanish.off");
    }

    public static boolean canVanishOn(Player player) {
        return player.hasPermission("vanish.vanish.on");
    }

    public static boolean joinVanished(Player player) {
        return player.hasPermission("vanish.joinvanished");
    }

    public static boolean joinWithoutAnnounce(Player player) {
        return player.hasPermission("vanish.joinwithoutannounce");
    }

    public static boolean permTestOther(Player player) {
        return player.hasPermission("vanish.permtest.other");
    }

    public static boolean permTestSelf(Player player) {
        return player.hasPermission("vanish.permtest.self");
    }

    public static boolean silentQuit(Player player) {
        return player.hasPermission("vanish.silentquit");
    }

    public static boolean toggleDamageIn(Player player) {
        return VanishPerms.getUser(player).toggleIncomingDamage();
    }

    public static boolean toggleDamageOut(Player player) {
        return VanishPerms.getUser(player).toggleOutgoingDamage();
    }

    public static boolean toggleNoFollow(Player player) {
        return VanishPerms.getUser(player).toggleNoFollow();
    }

    public static boolean toggleNoHunger(Player player) {
        return VanishPerms.getUser(player).toggleNoHunger();
    }

    public static boolean toggleNoInteract(Player player) {
        return VanishPerms.getUser(player).toggleNoInteract();
    }

    public static boolean toggleNoPickup(Player player) {
        return VanishPerms.getUser(player).toggleNoPickup();
    }

    public static boolean toggleSeeAll(Player player) {
        return VanishPerms.getUser(player).toggleSeeAll();
    }

    public static void userQuit(Player player) {
        VanishPerms.users.remove(player.getName());
    }

    private static VanishUser getUser(Player player) {
        VanishUser user = VanishPerms.users.get(player.getName());
        if (user == null) {
            user = new VanishUser(player);
            VanishPerms.users.put(player.getName(), user);
        }
        return user;
    }
}