// © Copyright 2020-2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.authn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OidcTestController {

  private final Logger log = LogManager.getLogger(this.getClass());

  @CrossOrigin(originPatterns = "*")
  @GetMapping("/api/external")
  @NonNull
  public String index(@Nullable Authentication details) {
    this.log.info("{}", details);
    var name = details != null ? details.getName() : "world";
    return "Hello, " + name;
  }
}
