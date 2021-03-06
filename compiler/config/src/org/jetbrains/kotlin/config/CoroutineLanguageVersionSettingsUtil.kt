/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.config

import org.jetbrains.kotlin.builtins.StandardNames
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

fun LanguageVersionSettings.coroutinesPackageFqName(): FqName {
    return coroutinesPackageFqName(isReleaseCoroutines())
}

fun LanguageVersionSettings.isReleaseCoroutines() = supportsFeature(LanguageFeature.ReleaseCoroutines)

private fun coroutinesPackageFqName(isReleaseCoroutines: Boolean): FqName {
    return if (isReleaseCoroutines)
        StandardNames.COROUTINES_PACKAGE_FQ_NAME_RELEASE
    else
        StandardNames.COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL
}

fun LanguageVersionSettings.coroutinesIntrinsicsPackageFqName() =
    coroutinesPackageFqName().child(Name.identifier("intrinsics"))

fun LanguageVersionSettings.continuationInterfaceFqName() =
    coroutinesPackageFqName().child(Name.identifier("Continuation"))

fun LanguageVersionSettings.restrictsSuspensionFqName() =
    coroutinesPackageFqName().child(Name.identifier("RestrictsSuspension"))

fun FqName.isBuiltInCoroutineContext(languageVersionSettings: LanguageVersionSettings) =
    if (languageVersionSettings.supportsFeature(LanguageFeature.ReleaseCoroutines))
        this == StandardNames.COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier("coroutineContext"))
    else
        this == StandardNames.COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier("coroutineContext")) ||
                this == StandardNames.COROUTINES_INTRINSICS_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier("coroutineContext"))
