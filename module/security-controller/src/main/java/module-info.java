import org.jspecify.annotations.NullMarked;

/**
 * This module provides the registration controller.
 */
@NullMarked module com.xenoterracide.controller.registration {
  opens com.xenoterracide.controller.security to org.hibernate.validator, spring.core;

  requires static org.jmolecules.architecture.layered;
  requires static org.jspecify;

  requires spring.graphql;
  requires spring.context;
  requires com.xenoterracide.model.security;
  requires jakarta.validation;

  requires org.jmolecules.ddd;
}
