package io.github.minebuilders.beatpet.triggers

import io.github.minebuilders.beatpet.actions.pet
import org.allaymc.api.eventbus.EventHandler
import org.allaymc.api.eventbus.event.player.PlayerInteractEntityEvent

object EventListener {
    @EventHandler
    private fun on(event: PlayerInteractEntityEvent) {
        if (!event.player.isSneaking) return
        event.entity.pet()
    }
}
