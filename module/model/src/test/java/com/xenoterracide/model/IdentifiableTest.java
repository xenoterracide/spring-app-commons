// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IdentifiableTest {

  @Test
  void idsAreEqual() {
    var t1 = new IdImpl(1L);
    var t2 = new GetIdImpl(1L);
    assertThat(t1.getId()).isEqualTo(t2.getId());
    assertThat(t1.id()).isEqualTo(t2.id());
    assertThat(t1.getId()).isEqualTo(t1.id());
    assertThat(t1.getId()).isEqualTo(t2.id());
  }

  record IdImpl(Long id) implements Identifiable<Long> {}

  static class GetIdImpl implements Identifiable<Long> {

    private final Long id;

    GetIdImpl(Long id) {
      this.id = id;
    }

    @Override
    public Long getId() {
      return this.id;
    }
  }
}
