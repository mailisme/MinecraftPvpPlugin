package me.minecraft.minecraftpvpplugin.gadgets;

import me.minecraft.minecraftpvpplugin.Effect;
import me.minecraft.minecraftpvpplugin.Gadget;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Damage extends Gadget {
    public Damage() {
        super(Material.LAPIS_ORE, "劍魂之石", 10);
    }
    public void onActivate(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.addPotionEffect(Effect.DamageEffect);
    }

    public void onDeactivate(PlayerInteractEvent event) {
    }
}