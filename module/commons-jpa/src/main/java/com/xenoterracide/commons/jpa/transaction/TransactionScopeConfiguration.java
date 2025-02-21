// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa.transaction;

import com.xenoterracide.commons.jpa.annotation.TransactionScope;
import com.xenoterracide.commons.jpa.util.Constants;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.SimpleTransactionScope;

/**
 * The type Transaction bean post processor.
 */
@Configuration
@SuppressWarnings({ "HideUtilityClassConstructor", "FinalClass" })
class TransactionScopeConfiguration {

  private static final ZoneId UTC = ZoneId.of("UTC");

  @Bean
  static BeanFactoryPostProcessor transactionBeanPostProcessor() {
    return beanFactory -> beanFactory.registerScope(Constants.TRANSACTION_SCOPE, new SimpleTransactionScope());
  }

  @Bean
  @TransactionScope
  static Instant instantNow() {
    return Instant.now();
  }

  @Bean
  @TransactionScope
  static ZonedDateTime zonedDateTimeNow(@NonNull Instant instantNow) {
    return instantNow.atZone(UTC);
  }

  /**
   * Offset date time now offset date time.
   *
   * @param zonedDateTimeNow
   *   the zoned date time now
   * @return the offset date time
   */
  @Bean
  @TransactionScope
  static OffsetDateTime offsetDateTimeNow(@NonNull ZonedDateTime zonedDateTimeNow) {
    return zonedDateTimeNow.toOffsetDateTime();
  }
}
