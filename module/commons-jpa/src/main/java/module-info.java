// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.commons.jpa {
  exports com.xenoterracide.commons.jpa.annotation;
  exports com.xenoterracide.commons.jpa.transaction to spring.beans, spring.context;
  exports com.xenoterracide.commons.jpa.util;
  exports com.xenoterracide.commons.jpa;
  opens com.xenoterracide.commons.jpa to org.hibernate.orm.core, spring.core, org.hibernate.validator;
  opens com.xenoterracide.commons.jpa.transaction to spring.core;
  requires org.apache.commons.lang3;
  requires org.hibernate.orm.envers;
  requires spring.beans;
  requires spring.context;
  requires spring.data.commons;
  requires spring.tx;
  requires static com.xenoterracide.tools.java;
  requires static jakarta.annotation;
  requires static org.jmolecules.architecture.layered;
  requires static org.jspecify;
  requires transitive com.xenoterracide.commons.model;
  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires transitive org.jmolecules.ddd;
}
