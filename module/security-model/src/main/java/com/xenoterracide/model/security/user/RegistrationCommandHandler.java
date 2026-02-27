// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import org.axonframework.messaging.commandhandling.annotation.CommandHandler;
import org.axonframework.messaging.eventhandling.gateway.EventAppender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCommandHandler {

  @CommandHandler
  void handle(RegisterNewUser command, EventAppender eventAppender) {
    var event = new UserCreated(User.UserId.create());
    eventAppender.append(event);
  }
}
