// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NameableTest {

  @Test
  void namesAreEqual() {
    var t1 = new NameImpl("name");
    var t2 = new GetNameImpl("name");
    assertThat(t1.getName()).isEqualTo(t2.getName());
    assertThat(t1.name()).isEqualTo(t2.name());
    assertThat(t1.getName()).isEqualTo(t1.name());
    assertThat(t1.getName()).isEqualTo(t2.name());
  }

  record NameImpl(String name) implements Nameable {}

  static class GetNameImpl implements Nameable {

    private final String name;

    GetNameImpl(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return this.name;
    }
  }
}
