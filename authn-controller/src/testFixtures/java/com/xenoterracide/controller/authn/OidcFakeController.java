// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.authn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OidcFakeController {

  private final Logger log = LogManager.getLogger(this.getClass());

  @GetMapping("/api/external")
  Greeting index(Authentication details) {
    this.log.info("{}", details);
    var name = details != null ? details.getName() : "Stranger";
    return new Greeting(name);
  }

  record Greeting(String name) {}
}
