// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.within;

import com.xenoterracide.jpa.util.Constants;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TransactionBeanPostProcessorTest {

  @Autowired
  BeanFactory context;

  @Test
  void postProcessBeanFactory() {
    assertThat(context.getBean(TransactionBeanPostProcessor.class)).isNotNull();
  }

  @Transactional
  @ParameterizedTest
  @ArgumentsSource(TimeClassProvider.class)
  @SuppressWarnings("JavaTimeDefaultTimeZone")
  void timeAvailableInTransaction(Class<? extends Temporal> timeClass) {
    var now = context.getBean(timeClass);
    assertThat(now).isExactlyInstanceOf(timeClass);

    switch (now) {
      case Instant instant -> {
        assertThat(instant).isCloseTo(Instant.now(), within(1, ChronoUnit.SECONDS));
      }
      case ZonedDateTime zonedDateTime -> {
        assertThat(zonedDateTime).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
      }
      case OffsetDateTime offsetDateTime -> {
        assertThat(offsetDateTime).isCloseTo(OffsetDateTime.now(), within(1, ChronoUnit.SECONDS));
      }
      default -> throw new IllegalStateException("Unexpected value: " + now);
    }
  }

  @ParameterizedTest
  @ArgumentsSource(TimeClassProvider.class)
  void timeIsTransactional(Class<?> timeClass) {
    assertThatExceptionOfType(ScopeNotActiveException.class)
      .isThrownBy(() -> context.getBean(timeClass))
      .withMessageContaining(Constants.TRANSACTION_SCOPE);
  }

  static class TimeClassProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
        Arguments.of(Instant.class),
        Arguments.of(ZonedDateTime.class),
        Arguments.of(OffsetDateTime.class)
      );
    }
  }
}
