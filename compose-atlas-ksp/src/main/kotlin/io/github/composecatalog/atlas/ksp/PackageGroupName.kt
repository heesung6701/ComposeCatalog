package io.github.composecatalog.atlas.ksp

internal object PackageGroupName {
    fun fromPackage(packageName: String): String {
        val segments = packageName.split('.').filter { it.isNotBlank() }
        val composeCatalogIndex = segments.indexOf("composecatalog")
        val featureSegments = if (composeCatalogIndex >= 0) {
            segments.drop(composeCatalogIndex + 2)
        } else {
            segments
        }
        return featureSegments.joinToString("/")
    }
}
