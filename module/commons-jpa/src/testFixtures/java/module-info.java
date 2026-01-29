// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.jpa.fixtures {
  exports com.xenoterracide.jpa.fixtures;
  opens com.xenoterracide.jpa.fixtures
    to
      org.eclipse.persistence.core,
      org.eclipse.persistence.jpa,
      org.eclipse.persistence.asm,
      org.eclipse.persistence.jpa.jpql;
  requires jakarta.persistence;
  requires com.xenoterracide.commons.jpa;
  requires com.xenoterracide.tools.java;
  requires com.github.f4b6a3.uuid;
  requires transitive io.helidon.data;
  requires transitive io.helidon.data.jakarta.persistence;
  requires static org.jspecify;
}
