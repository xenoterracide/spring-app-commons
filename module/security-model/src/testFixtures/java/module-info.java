// SPDX-FileCopyrightText: Copyright © 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.model.security.fixtures {
  exports com.xenoterracide.model.security.fixtures;
  opens com.xenoterracide.model.security.fixtures to org.hibernate.orm.core, spring.core;
  requires com.xenoterracide.model.security;
  requires jakarta.annotation;

  requires static org.immutables.value.annotations;
  requires static org.immutables.builder;
  requires static org.jspecify;
}
