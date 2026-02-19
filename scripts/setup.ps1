$projectRoot = Join-Path $PSScriptRoot ".."

New-Item -ItemType SymbolicLink `
    -Path "$env:APPDATA\Minecraft Bedrock\Users\Shared\games\com.mojang\development_behavior_packs\beatpet_bp" `
    -Target "$projectRoot\addon\behavior_pack"
New-Item -ItemType SymbolicLink `
    -Path "$env:APPDATA\Minecraft Bedrock\Users\Shared\games\com.mojang\development_resource_packs\beatpet_rp" `
    -Target "$projectRoot\addon\resource_pack"
