Add-Type -AssemblyName System.IO.Compression.FileSystem

$projectRoot = Join-Path $PSScriptRoot ".."
$distDir = Join-Path $projectRoot "dist"
if (!(Test-Path $distDir)) { New-Item -ItemType Directory -Path $distDir }

$packageJson = Get-Content (Join-Path $projectRoot "package.json") -Raw | ConvertFrom-Json
$version = $packageJson.version

function Build-McPack {
    param($Name, $SourcePath)
    $packPath = Join-Path $distDir "$Name-$version.mcpack"
    if (Test-Path $packPath) { Remove-Item $packPath }

    Compress-Archive -Path "$SourcePath\*" -DestinationPath $packPath

    $manifestPath = Join-Path $SourcePath "manifest.json"
    $manifestTemp = [System.IO.Path]::GetTempFileName()
    (Get-Content $manifestPath -Raw) -replace "999\.999\.999", $version | Set-Content $manifestTemp
    $zip = [System.IO.Compression.ZipFile]::Open($packPath, 'Update')
    $zip.GetEntry("manifest.json").Delete()
    [System.IO.Compression.ZipFileExtensions]::CreateEntryFromFile($zip, $manifestTemp, "manifest.json")
    $zip.Dispose()
    Remove-Item $manifestTemp
}
Build-McPack -Name "beatpet-bp" -SourcePath (Join-Path $projectRoot "addon/behavior_pack")
Build-McPack -Name "beatpet-rp" -SourcePath (Join-Path $projectRoot "addon/resource_pack")

$gradlew = if ($IsWindows) { "gradlew.bat" } else { "gradlew" }
$gradlew = Join-Path $projectRoot $gradlew
& $gradlew :allay:shadowJar
Copy-Item -Path (Join-Path $projectRoot "allay/build/libs/*") -Destination $distDir -Force
