// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.fixtures;

import static org.assertj.core.api.Assertions.assertThat;

import com.xenoterracide.jpa.AbstractIdentitifier;
import com.xenoterracide.jpa.DomainEvent;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NullAway")
class AbstractSplatTest {

  @Test
  void abstractAggregateEquality() {
    var fooId1 = FooAggregate.Id.create();
    var fooId2 = FooAggregate.Id.create();
    var barId1 = BarEntity.Id.create();
    var barId2 = BarEntity.Id.create();

    EqualsVerifier.forClass(FooAggregate.class)
      .withRedefinedSuperclass()
      .withPrefabValues(AbstractIdentitifier.class, barId1, fooId1)
      .withPrefabValues(BarEntity.Id.class, barId1, barId2)
      .withPrefabValues(FooAggregate.Id.class, fooId1, fooId2)
      .withPrefabValues(BarEntity.class, BarEntity.create(null, "bar"), BarEntity.create(null, "baz"))
      .withPrefabValues(
        DomainEvent.class,
        FooEvent.create(fooId1, new BarEntity.NameChanged(barId1, "foo")),
        FooEvent.create(fooId2, new BarEntity.NameChanged(barId2, "bar"))
      )
      .suppress(Warning.SURROGATE_OR_BUSINESS_KEY)
      .suppress(Warning.TRANSIENT_FIELDS)
      .withOnlyTheseFields("id", "version", "dirty")
      .verify();
  }

  @Test
  void abstractEntityEquality() {
    EqualsVerifier.forClass(BarEntity.class)
      .withRedefinedSuperclass()
      .withPrefabValues(AbstractIdentitifier.class, FooAggregate.Id.create(), BarEntity.Id.create())
      .withPrefabValues(BarEntity.Id.class, BarEntity.Id.create(), BarEntity.Id.create())
      .withPrefabValues(FooAggregate.Id.class, FooAggregate.Id.create(), FooAggregate.Id.create())
      .withPrefabValues(FooAggregate.class, FooAggregate.create("foo"), FooAggregate.create("baz"))
      .suppress(Warning.SURROGATE_OR_BUSINESS_KEY)
      .suppress(Warning.TRANSIENT_FIELDS)
      .withOnlyTheseFields("id", "version", "dirty")
      .verify();
  }

  @Test
  void validation() {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      var validator = factory.getValidator();
      var agg = new FooAggregate();
      var res = validator.validate(agg);
      assertThat(res)
        .hasSize(2)
        .extracting(ConstraintViolation::getMessage, cv -> cv.getPropertyPath().toString())
        .containsOnly(Tuple.tuple("must not be null", "id"), Tuple.tuple("must not be null", "name"));
    }
  }

  @Test
  void abstractIdentitityEquality() {
    EqualsVerifier.forClass(BarEntity.Id.class).withRedefinedSuperclass().verify();
    EqualsVerifier.forClass(FooAggregate.Id.class).withRedefinedSuperclass().verify();
    EqualsVerifier.forClasses(BarEntity.Id.class, FooAggregate.Id.class).verify();
  }

  @Test
  void abstractEntityToString() {
    var bar = new BarEntity();
    assertThat(bar).hasToString(
      "com.xenoterracide.jpa.fixtures.BarEntity@%s[name=<null>,id=<null>]",
      ObjectUtils.identityHashCodeHex(bar)
    );
  }
}
