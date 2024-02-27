package me.minecraft.minecraftpvpplugin

import org.bukkit.*
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
import org.bukkit.plugin.java.JavaPlugin

class MinecraftPvpPlugin : JavaPlugin(), Listener {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("SKYJET PVP v.3.0.0")
        server.pluginManager.registerEvents(this, this)

        Worlds.lobby.pvp = false
        Worlds.pvpWorlds.map { it.pvp = true }

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
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        if (event.clickedInventory == null) return

        if (event.clickedInventory.title.equals(ChatColor.AQUA.toString() + "Join Game", ignoreCase = true)) {
            when (event.currentItem.type) {
                Material.DIAMOND_AXE -> {
                    PvpPlaceManager.addPlayer(player)
                    player.closeInventory()
                }

                else -> {}
            }

            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "${ChatColor.AQUA}Welcome ${player.name}"
        logger.info("${player.name} joined the server")
        player.teleport(Locations.lobbySpawn)
        onPlayerToLobby(player)
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player

        if (player.world == Worlds.lobby) {
            val gui = Bukkit.createInventory(player, 9, "${ChatColor.AQUA}Join Game")
            val startGameBtn = Items.DiamondPickaxe

            gui.contents = arrayOf(startGameBtn)

            if (event.item == null) return
            if (event.item == Items.DiamondSword && event.action != Action.PHYSICAL) {
                player.openInventory(gui)
            }
        }
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val player = event.player

        if (player.world == Worlds.lobby) {
            player.sendMessage("U cant drop any item ok?")
            event.itemDrop.remove()
            player.inventory.setItem(0, event.itemDrop.itemStack)
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity

        player.spigot().respawn()
        PvpPlaceManager.removePlayer(player)
        event.keepInventory = true
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (Worlds.isInPvp(player)) {
            PvpPlaceManager.removePlayer(player)
        }
    }

    companion object {
        var instance: JavaPlugin? = null

        fun onPlayerToLobby(player: Player?) {
            if (player != null) {
                println("To Lobby " + player.name)
                player.inventory.clear()
                Items.SwordItemMeta.displayName = ChatColor.AQUA.toString() + "Join"
                Items.SwordItemMeta.spigot().isUnbreakable = true
                Items.DiamondSword.setItemMeta(Items.SwordItemMeta)
                player.inventory.setItem(0, Items.DiamondSword)
                player.inventory.helmet = null
                player.inventory.chestplate = null
                player.inventory.leggings = null
                player.inventory.boots = null
                player.health = 20.0
                player.foodLevel = 20
                clearEffects(player)
                player.gameMode = GameMode.ADVENTURE
            }
        }

        fun onPlayerToPvp(player: Player?) {
            if (player != null) {
                player.inventory.clear()
                player.inventory.setItem(0, Items.IronSword)
                player.inventory.setItem(1, Items.FishingRod)

                player.inventory.setItem(8, Items.Gapple)
                player.health = 20.0
                player.foodLevel = 20
                clearEffects(player)
                player.inventory.helmet = Items.IronHelmet
                player.inventory.chestplate = Items.IronChestplate
                player.inventory.leggings = Items.IronLeggings
                player.inventory.boots = Items.IronBoots
                player.gameMode = GameMode.ADVENTURE
            }
        }

        private fun clearEffects(player: Player) {
            for (effect in player.activePotionEffects) {
                player.removePotionEffect(effect.type)
            }
        }
    }
}