package io.github.composecatalog.atlas.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

private val atlasEntryClass = ClassName("io.github.composecatalog.atlas.runtime", "AtlasEntry")
private val atlasRegistryClass = ClassName("io.github.composecatalog.atlas.runtime", "AtlasRegistry")
private val listClass = ClassName("kotlin.collections", "List")
private val suppressAnnotation = AnnotationSpec.builder(Suppress::class)
    .addMember("%S", "RedundantVisibilityModifier")
    .build()

public class RegistryGenerator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) {
    public fun generate(functions: List<KSFunctionDeclaration>) {
        val packageName = "io.github.composecatalog.atlas.generated"
        val entriesCode = functions.joinToString(separator = ",\n") { function ->
            val title = function.atlasString("title").ifBlank { function.simpleName.asString() }
            val group = PackageGroupName.fromPackage(function.packageName.asString())
            val description = function.atlasString("description")
            val route = function.atlasString("route").ifBlank { function.defaultRoute() }
            val order = function.atlasInt("order")
            val callable = function.qualifiedName?.asString()
            if (callable == null) {
                logger.error("@AtlasScreen function must have a qualified name.", function)
            }
            """
                AtlasEntry(
                    title = ${title.quote()},
                    group = ${group.quote()},
                    description = ${description.quote()},
                    route = ${route.quote()},
                    order = $order,
                    content = { ${callable ?: function.simpleName.asString()}() },
                )
            """.trimIndent()
        }

        val entriesProperty = PropertySpec.builder("entries", listClass.parameterizedBy(atlasEntryClass))
            .addModifiers(KModifier.OVERRIDE)
            .initializer("listOf(\n$entriesCode\n)")
            .build()

        val registry = TypeSpec.objectBuilder("GeneratedAtlasRegistry")
            .addSuperinterface(atlasRegistryClass)
            .addAnnotation(suppressAnnotation)
            .addProperty(entriesProperty)
            .build()

        FileSpec.builder(packageName, "GeneratedAtlasRegistry")
            .addImport("io.github.composecatalog.atlas.runtime", "AtlasEntry")
            .addType(registry)
            .build()
            .writeTo(codeGenerator, Dependencies(aggregating = false, *functions.mapNotNull { it.containingFile }.toTypedArray()))
    }
}

private fun KSFunctionDeclaration.atlasString(name: String): String =
    annotations.first { it.annotationType.resolve().declaration.qualifiedName?.asString() == "io.github.composecatalog.atlas.annotation.AtlasScreen" }
        .arguments.firstOrNull { it.name?.asString() == name }?.value as? String ?: ""

private fun KSFunctionDeclaration.atlasInt(name: String): Int =
    annotations.first { it.annotationType.resolve().declaration.qualifiedName?.asString() == "io.github.composecatalog.atlas.annotation.AtlasScreen" }
        .arguments.firstOrNull { it.name?.asString() == name }?.value as? Int ?: 0

private fun KSFunctionDeclaration.defaultRoute(): String {
    val groupParts = PackageGroupName.fromPackage(packageName.asString())
        .split('/')
        .filter { it.isNotBlank() }
    val name = simpleName.asString()
        .removeSuffix("Screen")
        .removeSuffix("Demo")
        .replace(Regex("([a-z])([A-Z])"), "\$1-\$2")
        .lowercase()
    return (groupParts + name).joinToString("/")
}

private fun String.quote(): String = buildString {
    append('"')
    this@quote.forEach { ch ->
        when (ch) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            '\t' -> append("\\t")
            else -> append(ch)
        }
    }
    append('"')
}
