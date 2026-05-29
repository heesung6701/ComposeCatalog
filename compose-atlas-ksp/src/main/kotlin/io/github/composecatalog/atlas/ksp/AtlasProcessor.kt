package io.github.composecatalog.atlas.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.google.devtools.ksp.validate

private const val ATLAS_SCREEN = "io.github.composecatalog.atlas.annotation.AtlasScreen"
private const val COMPOSABLE = "androidx.compose.runtime.Composable"

public class AtlasProcessor(environment: com.google.devtools.ksp.processing.SymbolProcessorEnvironment) : SymbolProcessor {
    private val codeGenerator: CodeGenerator = environment.codeGenerator
    private val logger: KSPLogger = environment.logger
    private var generated = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(ATLAS_SCREEN).toList()
        val invalid = symbols.filterNot { it.validate() }
        val functions = symbols.filterIsInstance<KSFunctionDeclaration>().filter { it.validate() }.toList()

        functions.forEach(::validateAtlasFunction)

        if (!generated && functions.isNotEmpty()) {
            RegistryGenerator(codeGenerator, logger).generate(functions)
            generated = true
        }

        return invalid
    }

    private fun validateAtlasFunction(function: KSFunctionDeclaration) {
        if (function.parameters.isNotEmpty()) {
            logger.error("@AtlasScreen functions must not declare parameters.", function)
        }
        val hasComposable = function.annotations.any { annotation ->
            annotation.annotationType.resolve().declaration.qualifiedName?.asString() == COMPOSABLE
        }
        if (!hasComposable) {
            logger.error("@AtlasScreen functions must also be annotated with @Composable.", function)
        }
        if (Modifier.PRIVATE in function.modifiers) {
            logger.error("@AtlasScreen functions must be visible from generated registry code.", function)
        }
    }
}
