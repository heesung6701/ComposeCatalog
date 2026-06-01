# ComposeCatalog / ComposeAtlas

Annotation-based Compose catalog and auto-navigation framework skeleton.

## Modules

- `compose-atlas-annotation`: `@AtlasScreen` source-retention annotation.
- `compose-atlas-runtime`: runtime model, catalog UI, and metadata search helpers.
- `compose-atlas-navigation`: Navigation Compose adapter via `AtlasNavHost`.
- `compose-atlas-ksp`: KSP processor that validates annotated composables and generates `GeneratedAtlasRegistry`.
- `compose-atlas-bom`: Maven BOM that aligns all ComposeAtlas artifact versions.
- `sample-app`: Android sample app demonstrating generated registry + auto navigation.
- `demo-app`: richer demo app that consumes the library modules and exposes multiple Compose Catalog style component test screens.

## Dependency setup

Published artifacts share one version from `VERSION_NAME` in `gradle.properties`. Consumers can import the BOM once per dependency configuration and omit versions from individual artifacts:

```kotlin
dependencies {
    implementation(platform("io.github.composecatalog:compose-atlas-bom:0.1.0"))
    ksp(platform("io.github.composecatalog:compose-atlas-bom:0.1.0"))

    implementation("io.github.composecatalog:compose-atlas-annotation")
    implementation("io.github.composecatalog:compose-atlas-runtime")
    implementation("io.github.composecatalog:compose-atlas-navigation")
    ksp("io.github.composecatalog:compose-atlas-ksp")
}
```

`ksp(...)` has its own configuration, so import the BOM there too when using the KSP processor.

## Publishing

All publishable modules use the same Maven coordinates group and version from `gradle.properties`:

```properties
GROUP=io.github.composecatalog
VERSION_NAME=0.1.0-SNAPSHOT
```

Local publication verification:

```bash
./gradlew publishAllPublicationsToLocalBuildRepository
```

GitHub Actions publishes automatically to GitHub Packages and Maven Central when a `v*` tag is pushed, or manually through the `Publish` workflow with a version input. A tag like `v0.1.0` publishes artifacts as version `0.1.0`.

Maven Central publishing requires these repository secrets:

```text
MAVEN_CENTRAL_USERNAME
MAVEN_CENTRAL_PASSWORD
SIGNING_IN_MEMORY_KEY
SIGNING_IN_MEMORY_KEY_ID
SIGNING_IN_MEMORY_KEY_PASSWORD
```

Manual Maven Central publication uses the same version override:

```bash
./gradlew publishAndReleaseToMavenCentral -PVERSION_NAME=0.1.0
```

## MVP usage

```kotlin
@AtlasScreen(
    title = "Button",
    description = "Basic Material 3 button sample",
)
@Composable
fun ButtonDemo() { }
```

The KSP processor generates:

```kotlin
io.github.composecatalog.atlas.generated.GeneratedAtlasRegistry
```

Then the app can host every registered screen:

```kotlin
AtlasNavHost(registry = GeneratedAtlasRegistry)
```

## Route and group convention

`group` is derived from package segments after the app root package. If `route` is omitted, the route uses that package-derived group plus the composable name without the `Screen`/`Demo` suffix, for example:

```text
io.github.composecatalog.demo.components.surface.CardsDemo
  group -> components/surface
  route -> components/surface/cards
```

## Current scope

MVP 1 skeleton:

- `@AtlasScreen`
- KSP registry generation
- Catalog UI with multi-depth group browsing, sorting, and search field
- Navigation Compose integration with generated group routes
- Sample screens
- Demo app with component-by-component catalog screens

Future phases:

- Multi-module aggregation
- Generated deep-link manifest helpers
- Compose Multiplatform runtime split
- Parameterized, type-safe navigation API
