// SPDX-FileCopyrightText: Copyright © 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

import org.jspecify.annotations.NullMarked;

/**
 * The main module for the authn controller.
 */
@NullMarked module com.xenoterracide.controller.authn.main {
  requires spring.context;
  requires spring.security.config;
  requires spring.security.web;
  requires static org.jspecify;
  requires spring.core;
  opens com.xenoterracide.controller.authn to spring.core;
  exports com.xenoterracide.controller.authn;
}
