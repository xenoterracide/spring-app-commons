// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

/**
 * Provide our default Application
 */
@NullMarked module com.xenoterracide {
  exports com.xenoterracide;

  opens com.xenoterracide to spring.core;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.boot;
}
