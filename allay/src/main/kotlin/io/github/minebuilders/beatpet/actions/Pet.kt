package io.github.minebuilders.beatpet.actions

import org.allaymc.api.entity.Entity
import org.allaymc.api.entity.data.EntityAnimation
import org.allaymc.api.world.particle.CustomParticle
import org.joml.Vector3d

const val DEFAULT_PARTICLE = "beatpet:petpet"
const val DEFAULT_ANIMATION = "animation.beatpet.petpet"

fun Entity.pet(
    template: String = DEFAULT_PARTICLE,
    animation: String = DEFAULT_ANIMATION,
) {
    val location = Vector3d(location)
    location.y += (offsetAABBForCollisionCheck.maxY() - offsetAABBForCollisionCheck.minY())
    location.y += 0.3f
    dimension.addParticle(location, CustomParticle(template))

    applyAnimation(EntityAnimation(animation, "none", "anim1", ""))

    // TODO: camera shake if this is player
}
