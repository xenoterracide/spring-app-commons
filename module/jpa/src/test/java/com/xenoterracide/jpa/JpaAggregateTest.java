// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = "logging.level.org.hibernate.orm.jdbc.bind = trace")
public class JpaAggregateTest {

  @Autowired
  TestAggregateRepository repository;

  @Autowired
  EntityManager entityManager;

  @Test
  void overallTest() {
    var newAgg = new Foo(Foo.Id.create(), "new");
    newAgg.getBars().add(Bar.create("new"));

    repository.save(newAgg);
    entityManager.flush();
    entityManager.clear();
    var f0 = repository.findById(newAgg.getId()).orElse(null);

    assertThat(f0)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isEqualTo(newAgg)
      .satisfies(agg -> assertThat(agg.getBars()).hasSize(1))
      .extracting(Identifiable::getId, AbstractEntity::getVersion, Foo::getName)
      .containsExactly(newAgg.getId(), 0, "new");

    f0.setName("updating");

    repository.save(f0);
    entityManager.flush();
    entityManager.clear();
    var f1 = repository.findById(newAgg.getId()).orElse(null);

    assertThat(f1)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> assertThat(agg.getBars()).hasSize(1))
      .extracting(Identifiable::getId, AbstractEntity::getVersion, Foo::getName)
      .containsExactly(f0.getId(), 1, "updating");

    f0.getBars().add(Bar.create("new1"));
    f0.getBars().add(Bar.create("new2"));
    f0.getBars().add(Bar.create("new3"));

    repository.save(f0);
    entityManager.flush();
    entityManager.clear();

    var f2 = repository.findById(newAgg.getId()).orElse(null);

    assertThat(f2)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> {
        assertThat(Hibernate.isInitialized(agg.getBars())).isFalse().describedAs("initialized");
        assertThat(agg.getBars())
          .hasSize(4)
          .extracting(Bar::getName)
          .containsExactlyInAnyOrder("new", "new1", "new2", "new3");
        assertThat(Hibernate.isInitialized(agg.getBars())).isTrue().describedAs("initialized");
      })
      .extracting(Identifiable::getId, AbstractEntity::getVersion, Foo::getName)
      .containsExactly(f0.getId(), 2, "updating");
  }

  @Test
  void noId() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> repository.save(new Foo()));
  }

  @Test
  void identityMustHaveUuid() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
      var agg = new Foo(new Foo.Id(), "new");
      repository.save(agg);
    });
  }
}
