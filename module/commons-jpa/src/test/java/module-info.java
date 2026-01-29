// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.commons.jpa.test {
  opens com.xenoterracide.commons.jpa.test
    to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;
  requires org.assertj.core;
  requires com.xenoterracide.commons.jpa;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires org.apache.commons.lang3;
  requires com.xenoterracide.jpa.fixtures;
  requires static org.jspecify;
  requires io.helidon.service.registry;
  requires io.helidon.data.jakarta.persistence;
  requires spring.beans;
}
