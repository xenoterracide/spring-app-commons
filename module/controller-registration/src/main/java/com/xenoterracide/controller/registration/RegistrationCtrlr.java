// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.registration;

import com.xenoterracide.model.security.User;
import com.xenoterracide.model.security.UserRepository;
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
  void register(User user) {
    this.userRepository.save(user);
  }
}
