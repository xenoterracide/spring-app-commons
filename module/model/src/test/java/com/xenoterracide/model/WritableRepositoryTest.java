// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WritableRepositoryTest {

  @Autowired
  FooRepository fooRepository;

  @Test
  void save() {
    var foo = new Foo();
    foo.setId(1L);
    assertThat(fooRepository.save(foo)).isNotNull();
  }

  @Test
  void saveAll() {
    var foo = new Foo();
    foo.setId(1L);
    assertThat(fooRepository.saveAll(List.of(foo))).isNotEmpty();
  }
}
