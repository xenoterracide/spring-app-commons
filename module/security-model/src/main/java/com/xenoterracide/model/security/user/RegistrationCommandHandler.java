// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import com.xenoterracide.model.security.user.Datatypes_UserCreated.UserCreated_;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.SourcingCondition;
import org.axonframework.messaging.commandhandling.annotation.CommandHandler;
import org.axonframework.messaging.core.unitofwork.ProcessingContext;
import org.axonframework.messaging.eventhandling.gateway.EventAppender;
import org.axonframework.messaging.eventstreaming.EventCriteria;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCommandHandler {

  @CommandHandler
  void handle(
    RegisterNewUser command,
    EventAppender eventAppender,
    EventStore eventStore,
    ProcessingContext processingContext
  ) {
    var email = command.email().getAddress();

    // Check if email already exists by querying EventStore
    var emailCriteria = EventCriteria.havingTags(UserCreated_.NAME_, email);
    var emailCondition = SourcingCondition.conditionFor(emailCriteria);

    var emailStream = eventStore.transaction(processingContext).source(emailCondition);

    // If any event exists with this email, skip creating a new user (idempotent)
    boolean emailExists = emailStream.reduce(false, (found, entry) -> true).join();

    if (emailExists) {
      return;
    }

    var userCreated = UserCreated.builder().id(User.UserId.create()).name(email).build();
    var identityProviderAssociated = IdentityProviderAssociated.builder()
      .id(userCreated.id())
      .issuer(command.issuer())
      .subject(command.subject())
      .email(command.email())
      .emailVerified(command.emailVerified())
      .build();
    eventAppender.append(userCreated, identityProviderAssociated);
  }
}
