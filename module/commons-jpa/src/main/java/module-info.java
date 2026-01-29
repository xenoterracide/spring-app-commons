// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.commons.jpa {
  exports com.xenoterracide.commons.jpa;
  exports com.xenoterracide.commons.jpa.annotation;
  exports com.xenoterracide.commons.jpa.util;

  opens com.xenoterracide.commons.jpa to org.hibernate.orm.core, spring.core, org.hibernate.validator;

  requires org.apache.commons.lang3;

  requires static org.jspecify;
  requires static com.xenoterracide.tools.java;
  requires static jakarta.annotation;
  requires static org.jmolecules.architecture.layered;

  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires transitive com.xenoterracide.commons.model;
  requires transitive org.jmolecules.ddd;
}
