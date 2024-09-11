/**
 * This module provides the registration controller.
 */
module com.xenoterracide.controller.registration {
  exports com.xenoterracide.controller.registration to spring.beans, spring.context, spring.graphql; // maybe not needed
  opens com.xenoterracide.controller.registration to spring.core, com.graphqljava, spring.graphql;
  requires spring.graphql;
  requires spring.context;
  requires com.xenoterracide.model.security;
}
