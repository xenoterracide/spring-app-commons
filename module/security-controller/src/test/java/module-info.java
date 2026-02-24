// SPDX-FileCopyrightText: Copyright © 2024, 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.controller.registration.test {
  opens com.xenoterracide.model.security.controller.test to org.junit.platform.commons, spring.core;
  requires org.assertj.core;
  requires com.xenoterracide.commons.jpa;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires spring.beans;
  requires spring.boot.test;
  requires spring.test;
  requires spring.boot.test.autoconfigure;
  requires spring.graphql.test;
  requires static org.jspecify;
  requires spring.boot.graphql.test;
}
