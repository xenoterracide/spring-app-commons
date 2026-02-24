// SPDX-FileCopyrightText: Copyright © 2024, 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.security;

import com.xenoterracide.model.security.user.User;
import com.xenoterracide.model.security.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
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
    this.userRepository = userRepository;
  }

  @MutationMapping
  User registerUser(@Valid @Argument RegistrationInput input) {
    var user = User.builder().name(input.email()).build();

    user.linkIdentityProvider(input.issuer(), input.subject(), input.email(), input.emailVerified());

    return this.userRepository.save(user);
  }

  record RegistrationInput(
    @NotNull URI issuer,
    @NotBlank String subject,
    @NotBlank @Email String email,
    boolean emailVerified
  ) {}
}
