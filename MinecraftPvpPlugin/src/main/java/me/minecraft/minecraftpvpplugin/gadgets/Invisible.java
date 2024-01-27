package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Effect;
import me.minecraft.minecraftpvpplugin.Gadget;
import me.minecraft.minecraftpvpplugin.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class Invisible extends Gadget {

    public Invisible() {
        super(Material.STAINED_GLASS_PANE, "虛影斗篷", 5);
    }

    public void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(Effect.InvisibleEffect);
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void onDeactivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setHelmet(Items.IronHelmet);
        player.getInventory().setChestplate(Items.IronChestplate);
        player.getInventory().setLeggings(Items.IronLeggings);
        player.getInventory().setBoots(Items.IronBoots);
    }
}