// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AbstractSplatTest {

  @Disabled
  void abstractAggregateEquality() {
    EqualsVerifier.forClass(FooAggregate.class)
      .withRedefinedSuperclass()
      .withPrefabValues(BarEntity.Id.class, BarEntity.Id.create(), BarEntity.Id.create())
      .withPrefabValues(FooAggregate.Id.class, FooAggregate.Id.create(), FooAggregate.Id.create())
      .suppress(Warning.SURROGATE_OR_BUSINESS_KEY)
      .withOnlyTheseFields("id", "version", "dirty")
      .verify();
  }

  @Disabled
  void abstractEntityEquality() {
    EqualsVerifier.forClass(BarEntity.class)
      .withRedefinedSuperclass()
      .withPrefabValues(AbstractIdentity.class, BarEntity.Id.create(), BarEntity.Id.create())
      .suppress(Warning.SURROGATE_OR_BUSINESS_KEY)
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
  }

  @Test
  void abstractEntityToString() {
    var bar = new BarEntity();
    assertThat(bar).hasToString(
      "com.xenoterracide.jpa.BarEntity@%s[name=<null>,id=<null>]",
      ObjectUtils.identityHashCodeHex(bar)
    );
  }
}
