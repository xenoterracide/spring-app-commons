// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

/**
 * Common Domain Model classes.
 */
@NullMarked module com.xenoterracide.commons.model {
  exports com.xenoterracide.commons.model;
  opens com.xenoterracide.commons.model;
  requires org.jmolecules.ddd;
  requires spring.data.commons;
  requires static org.jmolecules.architecture.layered;
  requires static org.jspecify;
}
