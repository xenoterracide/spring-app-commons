/**
 * This module provides the registration controller.
 */
module com.xenoterracide.controller.registration {
  opens com.xenoterracide.controller.registration to spring.core, org.hibernate.validator;
  requires spring.graphql;
  requires spring.context;
  requires com.xenoterracide.model.security;
}
