// SPDX-FileCopyrightText: Copyright © 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.test {
  opens com.xenoterracide.test to org.junit.platform.commons;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.boot.test;
  requires org.junit.jupiter.api;
}
