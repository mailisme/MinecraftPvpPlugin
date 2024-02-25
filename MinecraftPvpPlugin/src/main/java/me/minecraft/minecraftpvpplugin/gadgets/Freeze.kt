package me.minecraft.minecraftpvpplugin.gadgets

import me.minecraft.minecraftpvpplugin.MatWithData
import me.minecraft.minecraftpvpplugin.MinecraftPvpPlugin
import me.minecraft.minecraftpvpplugin.ThrowableGadget
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.util.Vector

class Freeze : ThrowableGadget(Material.SNOW_BALL, "冷陸氣團") {
    public override fun onHitObject(event: ProjectileHitEvent) {
        val locationMaterialMap: MutableMap<Location, MatWithData> = HashMap()

        val iceCenter = event.entity.location
        val entityVelocity = event.entity.velocity
        iceCenter.add(entityVelocity.normalize())
        val centerBlock = iceCenter.block

        val blockY = centerBlock.y

        for (i in -3..3) {
            for (j in -3..3) {
                if (Vector(i, j, 0).length() < 2.7) {
                    val blockX = centerBlock.x + i
                    val blockZ = centerBlock.z + j

                    val block = iceCenter.world.getBlockAt(blockX, blockY, blockZ)
                    if (block.type != Material.ICE) {
                        locationMaterialMap[block.location] = MatWithData(block.type, block.data)
                    }

                    block.type = Material.ICE
                }
            }
        }

        Bukkit.getScheduler().runTaskLater(MinecraftPvpPlugin.instance, {
            locationMaterialMap.forEach { (location: Location, matWithData: MatWithData) ->
                var block = iceCenter.world.getBlockAt(location)

                block.type = matWithData.material
                block.data = matWithData.data

                locationMaterialMap.remove(location, matWithData)
            }
        }, 40)
    }
}
