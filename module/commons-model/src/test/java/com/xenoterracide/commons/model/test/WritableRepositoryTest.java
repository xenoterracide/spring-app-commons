// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.model.test;

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
    foo.setId(new Foo.FooId(1L));
    assertThat(fooRepository.save(foo)).isNotNull();
  }

  @Test
  void saveAll() {
    var foo = new Foo();
    foo.setId(new Foo.FooId(1L));
    assertThat(fooRepository.saveAll(List.of(foo))).isNotEmpty();
  }
}
