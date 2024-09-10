/**
 * This module provides the registration controller.
 */
module com.xenoterracide.controller.registration {
  exports com.xenoterracide.controller.registration to spring.beans, spring.context; // maybe not needed
  opens com.xenoterracide.controller.registration to spring.core;
  requires spring.graphql;
  requires spring.context;
  requires com.xenoterracide.model.security;
}
