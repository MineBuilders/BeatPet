export const defaultParticle = "beatpet:petpet";
export const defaultAnimation = "animation.beatpet.petpet";

/**
 * @param {import('@minecraft/server').Entity} entity
 * @param {string} particle
 * @param {string} animation
 */
export function pet(entity, particle = defaultParticle, animation = defaultAnimation) {
    const location = entity.getHeadLocation();
    location.y += .3;
    entity.dimension.spawnParticle(particle, location);
    entity.playAnimation(animation, {
        blendOutTime: 0,
        controller: 'anim1',
        nextState: 'none',
        stopExpression: '',
    });

    if (entity.typeId === 'minecraft:player')
        entity.runCommand('camerashake add @s 1 0.2 positional');
}
