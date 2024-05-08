// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test.authorization.server.client;

import org.springframework.stereotype.Repository;
import org.springframework.web.service.annotation.GetExchange;

public interface PkceClient {
  @GetExchange("/")
  Repository get();
}
