// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.registration;

import com.xenoterracide.model.security.IdentityProviderUser;
import com.xenoterracide.model.security.User;
import com.xenoterracide.model.security.UserRepository;
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
    var user = User.builder()
      .name(input.username())
      .addIdentityProviderUsers(
        IdentityProviderUser.builder()
          .idPUserId(input.idpUserId())
          .idP(IdentityProviderUser.IdP.valueOf(input.idp.name()))
      )
      .build();
    return this.userRepository.save(user);
  }

  enum IdP {
    AUTH0,
  }

  record RegistrationInput(@NotBlank String username, @NotBlank String idpUserId, @NotNull IdP idp) {}
}
