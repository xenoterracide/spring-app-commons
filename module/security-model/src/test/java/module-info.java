// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.security.test {
  opens com.xenoterracide.model.security.user.test
    to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;
  requires static org.jspecify;

  requires org.assertj.core;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires spring.beans;
  requires spring.boot.test;
  requires spring.tx;
  requires com.xenoterracide.model.security.fixtures;
  requires spring.test;
  requires spring.boot.test.autoconfigure;
  requires spring.orm;
  requires com.xenoterracide.model.security;
  requires jakarta.persistence;
  requires org.antlr.antlr4.runtime;
  requires io.helidon.service.registry;
  requires io.helidon.data.jakarta.persistence;
  requires org.apache.logging.log4j;
  requires io.helidon.config;
}
