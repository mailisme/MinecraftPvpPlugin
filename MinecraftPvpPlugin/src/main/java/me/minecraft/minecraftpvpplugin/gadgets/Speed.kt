package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Speed : Gadget(Material.LEATHER_BOOTS, "風行之靴", duration = 10.0) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        player.addPotionEffect(Effects.speedEffect)
    }

    public override fun onDeactivate(event: PlayerInteractEvent) {
        val player = event.player
        player.removePotionEffect(Effects.speedEffect.type)
    }
}
