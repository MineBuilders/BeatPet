import {
    CommandPermissionLevel,
    CustomCommandParamType,
    CustomCommandStatus,
    system,
} from "@minecraft/server";
import { pet, defaultParticle, defaultAnimation } from "../actions/pet";

const command = {
    name: "beatpet:pet",
    description: "beatpet.command.description",
    permissionLevel: CommandPermissionLevel.Any,
    cheatsRequired: false,
    optionalParameters: [
        { name: "targets", type: CustomCommandParamType.EntitySelector },
        { name: "template", type: CustomCommandParamType.String },
        { name: "animation", type: CustomCommandParamType.String },
    ],
};

/**
 * @param {import('@minecraft/server').CustomCommandOrigin} origin
 * @param {import('@minecraft/server').Entity[]} targets
 * @param {string} template
 * @param {string} animation
 */
const callback = (origin, targets, template, animation) => {
    const source = origin.initiator ?? origin.sourceEntity;
    targets ??= [ source ];
    template ??= defaultParticle;
    animation ??= defaultAnimation;

    if (targets.length === 0) return {
        status: CustomCommandStatus.Failure,
        message: "commands.generic.noTargetMatch",
    };

    system.run(() => targets.forEach(entity =>
        pet(entity, template, animation)));
};

system.beforeEvents.startup.subscribe(({ customCommandRegistry }) => {
    customCommandRegistry.registerCommand(command, callback);
});
