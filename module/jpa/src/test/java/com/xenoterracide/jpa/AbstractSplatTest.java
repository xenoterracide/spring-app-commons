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
import org.junit.jupiter.api.Test;

class AbstractSplatTest {

  @Test
  void abstractAggregateEquality() {
    EqualsVerifier.forClass(Foo.class)
      .withRedefinedSuperclass()
      .withPrefabValues(AbstractIdentity.class, Bar.Id.create(), Bar.Id.create())
      .suppress(Warning.SURROGATE_KEY)
      .verify();
  }

  @Test
  void abstractEntityEquality() {
    EqualsVerifier.forClass(Bar.class)
      .withRedefinedSuperclass()
      .withPrefabValues(AbstractIdentity.class, Bar.Id.create(), Bar.Id.create())
      .suppress(Warning.SURROGATE_KEY)
      .verify();
  }

  @Test
  void validation() {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      var validator = factory.getValidator();
      var agg = new Foo();
      var res = validator.validate(agg);
      assertThat(res)
        .hasSize(2)
        .extracting(ConstraintViolation::getMessage, cv -> cv.getPropertyPath().toString())
        .containsOnly(Tuple.tuple("must not be null", "id"), Tuple.tuple("must not be null", "name"));
    }
  }

  @Test
  void abstractIdentitityEquality() {
    EqualsVerifier.forClass(Bar.Id.class).withRedefinedSuperclass().verify();
  }

  @Test
  void abstractEntityToString() {
    var bar = new Bar();
    assertThat(bar).hasToString("Bar@%s[%s]", ObjectUtils.identityHashCodeHex(bar), bar.getId());
  }
}
