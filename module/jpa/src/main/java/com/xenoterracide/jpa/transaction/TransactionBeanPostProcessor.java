// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.transaction;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.support.SimpleTransactionScope;

/**
 * The type Transaction bean post processor.
 */
@Configuration
class TransactionBeanPostProcessor implements BeanFactoryPostProcessor {

  private static final String TX_SCOPE = "transaction";
  private static final ZoneId UTC = ZoneId.of("UTC");

  /**
   * Zoned date time now zoned date time.
   *
   * @return the zoned date time
   */
  @Bean
  @Scope(TX_SCOPE)
  ZonedDateTime zonedDateTimeNow() {
    return ZonedDateTime.now(UTC);
  }

  /**
   * Offset date time now offset date time.
   *
   * @param zonedDateTimeNow the zoned date time now
   * @return the offset date time
   */
  @Bean
  @Scope(TX_SCOPE)
  OffsetDateTime offsetDateTimeNow(@NonNull ZonedDateTime zonedDateTimeNow) {
    return zonedDateTimeNow.toOffsetDateTime();
  }

  @Override
  public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
    beanFactory.registerScope(TX_SCOPE, new SimpleTransactionScope());
  }
}
