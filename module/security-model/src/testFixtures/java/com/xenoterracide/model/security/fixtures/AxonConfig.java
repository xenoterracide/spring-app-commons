// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.fixtures;

import com.xenoterracide.model.security.user.RegistrationCommandHandler;
import org.axonframework.eventsourcing.configuration.EventSourcingConfigurer;
import org.axonframework.messaging.commandhandling.configuration.CommandHandlingModule;

public class AxonConfig {

  public static EventSourcingConfigurer configure(EventSourcingConfigurer configurer) {
    var commandHandlingModule = CommandHandlingModule.named("User")
      .commandHandlers()
      .annotatedCommandHandlingComponent(c -> new RegistrationCommandHandler());
    return configurer.registerCommandHandlingModule(commandHandlingModule);
  }
}
