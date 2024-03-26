// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Indicates that a method is visible for JPA, and should not be called directly.
 */
@Documented
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
public @interface VisibleForJPA {
}
