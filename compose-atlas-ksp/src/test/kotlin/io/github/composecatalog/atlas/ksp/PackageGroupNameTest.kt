package io.github.composecatalog.atlas.ksp

import org.junit.Assert.assertEquals
import org.junit.Test

class PackageGroupNameTest {
    @Test
    fun derivesGroupFromPackageSegmentsAfterAppRoot() {
        assertEquals(
            "components/surface",
            PackageGroupName.fromPackage("io.github.composecatalog.demo.components.surface"),
        )
        assertEquals(
            "components/input/forms",
            PackageGroupName.fromPackage("io.github.composecatalog.sample.components.input.forms"),
        )
    }

    @Test
    fun returnsEmptyGroupWhenPackageHasNoFeatureSegments() {
        assertEquals("", PackageGroupName.fromPackage("io.github.composecatalog.demo"))
    }
}
