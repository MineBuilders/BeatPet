import { world } from "@minecraft/server";
import { pet } from "../actions/pet";

world.afterEvents.itemUse.subscribe(({ source }) => {
    if (!source.isSneaking) return;

    for (const { entity } of
        source.getEntitiesFromViewDirection({
            ignoreBlockCollision: false,
            includeLiquidBlocks: false,
            includePassableBlocks: true,
            maxDistance: 3,
        }))
        pet(entity);
});
