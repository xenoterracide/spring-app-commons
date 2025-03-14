// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.controller.security;

import com.xenoterracide.model.security.user.IdentityProviderUser;
import com.xenoterracide.model.security.user.User;
import com.xenoterracide.model.security.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

/**
 * Registration controller.
 */
@Controller
public class RegistrationCtrlr {

  private final UserRepository userRepository;

  RegistrationCtrlr(UserRepository userRepository) {
    // constructor
    this.userRepository = userRepository;
  }

  @MutationMapping
  User registerUser(@Valid @Argument RegistrationInput input) {
    var user = User.builder().name(input.username()).build();

    user.linkIdentityProvider(IdentityProviderUser.IdP.valueOf(input.idp.name()), input.idpUserId());

    return this.userRepository.save(user);
  }

  enum IdP {
    AUTH0,
  }

  record RegistrationInput(@NotBlank String username, @NotBlank String idpUserId, @NotNull IdP idp) {}
}
