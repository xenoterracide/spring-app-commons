// SPDX-FileCopyrightText: Copyright © 2024, 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
// SPDX-License-Identifier: AGPL-3.0-or-later

import org.jspecify.annotations.NullMarked;

/**
 * Provide an easy to include {@code TestApplication} implementation for {@code com.xenoterracide} Spring Boot
 * Applications. Simply include the module on your classpath.
 */
@NullMarked module com.xenoterracide {
  exports com.xenoterracide to spring.beans, spring.context, com.xenoterracide.security.test;
  opens com.xenoterracide to spring.core;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.boot.autoconfigure;
  requires spring.context;
}
