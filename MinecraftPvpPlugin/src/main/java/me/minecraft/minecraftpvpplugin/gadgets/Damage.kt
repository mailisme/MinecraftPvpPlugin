package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.refs.Effects
import me.minecraft.minecraftpvpplugin.Gadget
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

object Damage : Gadget(Material.LAPIS_ORE, "劍魂之石", duration = 10) {
    public override fun onActivate(event: PlayerInteractEvent) {
        val player = event.player
        player.addPotionEffect(Effects.damageEffect)
    }
}