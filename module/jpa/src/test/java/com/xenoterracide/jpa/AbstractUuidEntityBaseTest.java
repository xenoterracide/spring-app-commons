// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class AbstractUuidEntityBaseTest {

  @Test
  void equality() {
    EqualsVerifier.forClass(TestUuidEntity.class).withRedefinedSuperclass().suppress(Warning.SURROGATE_KEY).verify();
  }
}
