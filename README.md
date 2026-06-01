# ComposeCatalog / ComposeAtlas

Annotation-based Compose catalog and auto-navigation framework skeleton.

## Modules

- `compose-atlas-annotation`: `@AtlasScreen` source-retention annotation.
- `compose-atlas-runtime`: runtime model, catalog UI, and metadata search helpers.
- `compose-atlas-navigation`: Navigation Compose adapter via `AtlasNavHost`.
- `compose-atlas-ksp`: KSP processor that validates annotated composables and generates `GeneratedAtlasRegistry`.
- `sample-app`: Android sample app demonstrating generated registry + auto navigation.
- `demo-app`: richer demo app that consumes the library modules and exposes multiple Compose Catalog style component test screens.

## MVP usage

```kotlin
@AtlasScreen(
    title = "Button",
    group = "Components/Input",
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

## Route convention

If `route` is omitted, the current MVP generates a route from the last two package segments plus the composable name without `Screen`/`Demo` suffix, for example:

```text
io.github.composecatalog.sample.demos.ButtonDemo -> sample/demos/button
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
