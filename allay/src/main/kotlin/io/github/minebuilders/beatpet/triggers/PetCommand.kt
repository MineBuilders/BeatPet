package io.github.minebuilders.beatpet.triggers

import io.github.minebuilders.beatpet.actions.DEFAULT_ANIMATION
import io.github.minebuilders.beatpet.actions.DEFAULT_PARTICLE
import io.github.minebuilders.beatpet.actions.pet
import org.allaymc.api.command.Command
import org.allaymc.api.command.tree.CommandTree
import org.allaymc.api.entity.Entity
import org.allaymc.api.permission.OpPermissionCalculator

object PetCommand : Command(
    "pet",
    "beatpet:command.description",
    "beatpet.command.pet"
) {
    init {
        OpPermissionCalculator.NON_OP_PERMISSIONS.addAll(this.permissions);
    }

    override fun prepareCommandTree(tree: CommandTree) = tree.root
        .target("targets")
        .optional()
        .str("template")
        .optional()
        .str("animation")
        .optional()
        .exec { context ->
            val targets: List<Entity> = context.getResult(0) ?: listOfNotNull(context.sender.asPlayer())
            val template = context.getResultOr(1, "").ifEmpty { DEFAULT_PARTICLE }
            val animation = context.getResultOr(2, "").ifEmpty { DEFAULT_ANIMATION }

            if (targets.isEmpty()) return@exec context.apply { addNoTargetMatchError() }.fail()
            targets.forEach { it.pet(template, animation) }
            context.success()
        }.run {}
}
