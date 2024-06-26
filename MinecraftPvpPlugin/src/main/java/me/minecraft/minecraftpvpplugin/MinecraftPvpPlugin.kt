package me.minecraft.minecraftpvpplugin

import me.minecraft.minecraftpvpplugin.refs.Items
import me.minecraft.minecraftpvpplugin.refs.Locations
import me.minecraft.minecraftpvpplugin.refs.Skills
import me.minecraft.minecraftpvpplugin.refs.Worlds
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class MinecraftPvpPlugin : JavaPlugin(), Listener {

    override fun onEnable() {
        logger.level = Level.ALL

        // Plugin startup logic
        logger.info("SKYJET PVP v.3.0.0")
        server.pluginManager.registerEvents(this, this)

        Worlds.lobby.pvp = false

        Worlds.lobby.setGameRuleValue("doMobSpawning", "false")
        Worlds.lobby.setGameRuleValue("doDaylightCycle", "true")
        Worlds.lobby.setGameRuleValue("doWeatherCycle", "true")

        Worlds.pvpWorlds.map {
            it.pvp = true
            it.setGameRuleValue("doMobSpawning", "false")
            it.setGameRuleValue("doDaylightCycle", "false")
            it.setGameRuleValue("doWeatherCycle", "false")
            it.setGameRuleValue("mobGriefing", "false")
            it.setGameRuleValue("fireTick", "false")
        }



        instance = this
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name.equals("lobby", ignoreCase = true)) {
            val player = sender as Player
            PvpPlaceManager.removePlayer(player)
            onPlayerToLobby(player)
        }

        return true
    }

    @EventHandler
    fun handleInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        if (event.clickedInventory == null) return

        if (event.clickedInventory.title.equals("${ChatColor.AQUA}Join Game", ignoreCase = true)) {
            when (event.currentItem.type) {
                Material.DIAMOND_AXE -> {
                    val selectGui = Bukkit.createInventory(player, 9, "${ChatColor.AQUA}Select skill")
                    selectGui.contents = Skills.skills
                    player.openInventory(selectGui)
                }

                else -> {}
            }

            event.isCancelled = true
        }

        if (event.clickedInventory.title.equals("${ChatColor.AQUA}Select skill", ignoreCase = true)) {
            Skills.skills.forEach {
                if (it.name == event.currentItem.itemMeta.displayName) {
                    PvpPlaceManager.addPlayer(player, it)
                }
            }

            event.isCancelled = true
        }
    }

    @EventHandler
    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "${ChatColor.AQUA}Welcome ${player.name}!"
        logger.info("${player.name} joined the server")
        player.teleport(Locations.lobbySpawn)
        onPlayerToLobby(player)
    }

    @EventHandler
    fun handlePlayerInteract(event: PlayerInteractEvent) {
        val player = event.player

        if (player.world == Worlds.lobby) {
            if (event.item == null) return
            if (event.item == Items.diamondSword && event.action != Action.PHYSICAL) {
                val startGui = Bukkit.createInventory(player, 9, "${ChatColor.AQUA}Join Game")
                val joinGameBtn = Items.diamondPickaxe

                startGui.contents = arrayOf(joinGameBtn)

                player.openInventory(startGui)
            }
        }
    }

    @EventHandler
    fun handlePlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player

        if (player.world == Worlds.lobby) {
            player.sendMessage("U cant drop any item ok?")
            event.itemDrop.remove()
            player.inventory.setItem(0, event.itemDrop.itemStack)
        }
    }

    @EventHandler
    fun handlePlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity

        player.spigot().respawn()
        PvpPlaceManager.removePlayer(player)
        event.keepInventory = true
    }

    @EventHandler
    fun handlePlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (Worlds.isInPvp(player)) {
            PvpPlaceManager.removePlayer(player)
        }
    }

    companion object {
        var instance: JavaPlugin? = null

        fun onPlayerToLobby(player: Player) {
            println("To Lobby " + player.name)
            player.inventory.clear()
            Items.swordItemMeta.displayName = "${ChatColor.AQUA}Join"
            Items.swordItemMeta.spigot().isUnbreakable = true
            Items.diamondSword.setItemMeta(Items.swordItemMeta)
            player.inventory.setItem(0, Items.diamondSword)
            player.inventory.helmet = null
            player.inventory.chestplate = null
            player.inventory.leggings = null
            player.inventory.boots = null
            player.health = 20.0
            player.foodLevel = 20
            clearEffects(player)
            player.gameMode = GameMode.ADVENTURE
        }

        fun onPlayerToPvp(player: Player, skillItem: ItemStack) {
            player.inventory.clear()
            player.inventory.setItem(0, Items.ironSword)
            player.inventory.setItem(1, Items.fishingRod)
            player.inventory.setItem(2, skillItem)

            player.inventory.setItem(8, Items.gapple)
            player.health = 20.0
            player.foodLevel = 20
            clearEffects(player)
            player.inventory.helmet = Items.ironHelmet
            player.inventory.chestplate = Items.ironChestplate
            player.inventory.leggings = Items.ironLeggings
            player.inventory.boots = Items.ironBoots
            player.gameMode = GameMode.ADVENTURE
        }

        private fun clearEffects(player: Player) {
            for (effect in player.activePotionEffects) {
                player.removePotionEffect(effect.type)
            }
        }
    }
}