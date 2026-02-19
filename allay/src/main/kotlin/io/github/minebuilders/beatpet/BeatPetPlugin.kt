package io.github.minebuilders.beatpet

import io.github.minebuilders.beatpet.triggers.EventListener
import io.github.minebuilders.beatpet.triggers.PetCommand
import org.allaymc.api.plugin.Plugin
import org.allaymc.api.registry.Registries
import org.allaymc.api.server.Server

@Suppress("unused")
class BeatPetPlugin : Plugin() {
    override fun onLoad() {
        Registries.COMMANDS.register(PetCommand)
        Server.getInstance().eventBus.registerListener(EventListener)
    }
}
