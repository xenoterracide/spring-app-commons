// SPDX-FileCopyrightText: Copyright © 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

/**
 * Common Domain Model classes.
 */
@NullMarked module com.xenoterracide.commons.model {
  exports com.xenoterracide.commons.model;
  opens com.xenoterracide.commons.model;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.data.commons;
  requires org.jmolecules.ddd;
}
