// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

import net.ltgt.gradle.errorprone.errorprone
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
  id("our.bom")
  id("net.ltgt.errorprone")
}

val libs = the<LibrariesForLibs>()

dependencies {
  errorprone(libs.bundles.ep)
  compileOnly(libs.bundles.compile)
}

java {
  withJavadocJar()
  withSourcesJar()
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

tasks.javadoc {
  // because classes is what runs source generation
  dependsOn(tasks.classes)
  // so we can reference generated classes in our javadoc
  source(sourceSets.main.map { it.output.generatedSourcesDirs })
  // because jpamodelgen puts non java sources in java source dirs https://hibernate.atlassian.net/browse/HHH-18676
  include("**/*.java")

  (options as StandardJavadocDocletOptions).apply {
    addMultilineStringsOption("tag").value =
      listOf(
        "apiSpec:a:API Spec:",
        "apiNote:a:API Note:",
        "implSpec:a:Implementation Spec:",
        "implNote:a:Implementation Note:",
      )
  }
}

tasks.withType<Jar> {
  archiveBaseName.set(project.path.substring(1).replace(":", "-"))
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = "UTF-8"
  options.compilerArgs.addAll(
    listOf(
      "-parameters",
      "-implicit:class",
      "-g",
      "-Xdiags:verbose",
      "-Xlint:all",
      "-Xlint:-processing",
      "-Xlint:-exports",
      "-Xlint:-requires-transitive-automatic",
      "-Xlint:-requires-automatic",
      "-Xlint:-fallthrough", // handled by error-prone in a smarter way
    ),
  )

  options.errorprone {
    disable(
      "InvalidInlineTag", // https://github.com/google/error-prone/issues/4308
      "MultipleNullnessAnnotations", // https://github.com/google/error-prone/issues/4334
    )
    disableWarningsInGeneratedCode.set(true)
    excludedPaths.set(".*/build/generated/sources/annotationProcessor/.*")
    option("NullAway:AnnotatedPackages", "com.xenoterracide")

    val errors =
      mutableListOf(
        "AmbiguousMethodReference",
        "ArgumentSelectionDefectChecker",
        "ArrayAsKeyOfSetOrMap",
        "AssertEqualsArgumentOrderChecker",
        "AssertThrowsMultipleStatements",
        "AssertionFailureIgnored",
        "BadComparable",
        "BadImport",
        "BadInstanceof",
        "BigDecimalEquals",
        "BigDecimalLiteralDouble",
        "BoxedPrimitiveConstructor",
        "BoxedPrimitiveEquality",
        "ByteBufferBackingArray",
        "CacheLoaderNull",
        "CannotMockFinalClass",
        "CanonicalDuration",
        "CatchFail",
        "CatchAndPrintStackTrace",
        "ClassCanBeStatic",
        "ClassNewInstance", // sketchy
        "CollectionUndefinedEquality",
        "CollectorShouldNotUseState",
        "ComparableAndComparator",
        "CompareToZero",
        "ComplexBooleanConstant",
        "DateFormatConstant",
        "DefaultCharset",
        "DefaultPackage",
        "DoubleBraceInitialization",
        "DoubleCheckedLocking",
        "EmptyCatch",
        "EqualsGetClass",
        "EqualsIncompatibleType",
        "EqualsUnsafeCast",
        "EqualsUsingHashCode",
        "ExtendingJUnitAssert",
        "FallThrough",
        "Finally",
        "FloatCast",
        "FloatingPointLiteralPrecision",
        "FutureReturnValueIgnored",
        "GetClassOnEnum",
        "HidingField",
        "ImmutableAnnotationChecker",
        "ImmutableEnumChecker",
        "InconsistentCapitalization",
        "InconsistentHashCode",
        "IncrementInForLoopAndHeader",
        "InlineFormatString",
        "InputStreamSlowMultibyteRead",
        "InstanceOfAndCastMatchWrongType",
        "InvalidThrows",
        "IterableAndIterator",
        "JavaDurationGetSecondsGetNano",
        "JavaDurationWithNanos",
        "JavaDurationWithSeconds",
        "JavaInstantGetSecondsGetNano",
        "JavaLangClash",
        "JavaLocalDateTimeGetNano",
        "JavaLocalTimeGetNano",
        "LockNotBeforeTry",
        "LockOnBoxedPrimitive",
        "LogicalAssignment",
        "MissingCasesInEnumSwitch",
        "Overrides",
        "MissingOverride",
        "MixedMutabilityReturnType",
        "ModifiedButNotUsed",
        "ModifyCollectionInEnhancedForLoop",
        "ModifySourceCollectionInStream",
        "MultipleParallelOrSequentialCalls",
        "MultipleUnaryOperatorsInMethodCall",
        "MutableConstantField",
        "MutablePublicArray",
        "NestedInstanceOfConditions",
        "NonAtomicVolatileUpdate",
        "NonOverridingEquals",
        "NullOptional",
        "NullableConstructor",
        "NullablePrimitive",
        "NullableVoid",
        "ObjectToString",
        "ObjectsHashCodePrimitive",
        "OperatorPrecedence",
        "OptionalMapToOptional",
        "OrphanedFormatString",
        "OverrideThrowableToString",
        "PreconditionsCheckNotNullRepeated",
        "PrimitiveAtomicReference",
        "ProtectedMembersInFinalClass",
        "PreconditionsCheckNotNullRepeated",
        "ReferenceEquality",
        "ReturnFromVoid",
        "RxReturnValueIgnored",
        "SameNameButDifferent",
        "ShortCircuitBoolean",
        "StaticAssignmentInConstructor",
        "StaticGuardedByInstance",
        "StreamResourceLeak",
        "StringSplitter",
        "SynchronizeOnNonFinalField",
        "ThreadJoinLoop",
        "ThreadLocalUsage",
        "ThreeLetterTimeZoneID",
        "TimeUnitConversionChecker",
        "ToStringReturnsNull",
        "TreeToString",
        "TypeEquals",
        "TypeNameShadowing",
        "TypeParameterShadowing",
        "TypeParameterUnusedInFormals", // sketchy
        "URLEqualsHashCode",
        "UndefinedEquals",
        "UnnecessaryAnonymousClass",
        "UnnecessaryLambda",
        "UnnecessaryMethodInvocationMatcher",
        "UnnecessaryParentheses", // sketchy
        "UnsafeFinalization",
        "UnsafeReflectiveConstructionCast",
        "UseCorrectAssertInTests",
        "VariableNameSameAsType",
        "WaitNotInLoop",
        "ClassName",
        "ComparisonContractViolated",
        "DeduplicateConstants",
        "EmptyIf",
        "FuzzyEqualsShouldNotBeUsedInEqualsMethod",
        "IterablePathParameter",
        "LongLiteralLowerCaseSuffix",
        "NumericEquality",
        "StaticQualifiedUsingExpression",
        "AssertFalse",
        "CheckedExceptionNotThrown",
        "EmptyTopLevelDeclaration",
        "EqualsBrokenForNull",
        "ExpectedExceptionChecker",
        "InconsistentOverloads",
        "InterruptedExceptionSwallowed",
        "InterfaceWithOnlyStatics",
        "NonCanonicalStaticMemberImport",
        "PreferJavaTimeOverload",
        "ClassNamedLikeTypeParameter",
        "ConstantField",
        "FieldCanBeLocal",
        "FieldCanBeStatic",
        "ForEachIterable",
        "MethodCanBeStatic",
        "MultiVariableDeclaration",
        "MultipleTopLevelClasses",
        "PackageLocation",
        "RemoveUnusedImports",
        "Var",
      )

    val inIdea = providers.systemProperty("idea.active").map { it.toBoolean() }
    if (!inIdea.getOrElse(false)) {
      errors.addAll(
        listOf(
          "WildcardImport",
          "UnusedVariable",
          "UnusedMethod",
          "UnusedNestedClass",
        ),
      )
    }

    if (name != "compileTestJava") {
      option("NullAway:CheckOptionalEmptiness", true)
      errors.addAll(listOf("NullAway", "JavaTimeDefaultTimeZone"))
    }

    if (name == "compileTestJava") {
      options.compilerArgs.addAll(
        listOf(
          "-Xlint:-unchecked",
          "-Xlint:-varargs",
        ),
      )
      disable("JavaTimeDefaultTimeZone")
      option("NullAway:HandleTestAssertionLibraries", true)
      option("NullAway:ExcludedFieldAnnotations", "org.junit.jupiter.api.io.TempDir")
    }

    error(*errors.toTypedArray())
  }
}
