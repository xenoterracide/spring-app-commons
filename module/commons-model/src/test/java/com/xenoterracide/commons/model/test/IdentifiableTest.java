// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.commons.model.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.xenoterracide.commons.model.Identifiable;
import java.io.Serializable;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

class IdentifiableTest {

  @Test
  void idsAreEqual() {
    var t1 = new IdImpl(new IdentifierImpl(1L));
    var t2 = new GetIdImpl(1L);
    assertThat(t1.getId()).isEqualTo(t2.getId());
    assertThat(t1.id()).isEqualTo(t2.id());
    assertThat(t1.getId()).isEqualTo(t1.id());
    assertThat(t1.getId()).isEqualTo(t2.id());
  }

  record IdImpl(IdentifierImpl id) implements Identifiable<@NonNull IdentifierImpl> {}

  static class GetIdImpl implements Identifiable<@NonNull IdentifierImpl> {

    private final IdentifierImpl value;

    GetIdImpl(Long value) {
      this.value = new IdentifierImpl(value);
    }

    @Override
    public IdentifierImpl getId() {
      return this.value;
    }
  }

  record IdentifierImpl(Long value) implements Identifier, Serializable {}
}
