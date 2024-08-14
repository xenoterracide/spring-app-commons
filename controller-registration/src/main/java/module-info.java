/**
 * This module provides the registration controller.
 */
module com.xenoterracide.controller.registration {
  requires spring.graphql;
  requires spring.context;
  requires com.xenoterracide.model.security;
  opens com.xenoterracide.controller.registration to spring.core;
}
