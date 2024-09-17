import org.jspecify.annotations.NullMarked;

/**
 * This module provides the registration controller.
 */
@NullMarked module com.xenoterracide.controller.registration {
  opens com.xenoterracide.security.controller to spring.core, org.hibernate.validator;
  requires spring.graphql;
  requires spring.context;
  requires com.xenoterracide.model.security;
  requires jakarta.validation;
  requires org.jmolecules.architecture.layered;
  requires static org.jspecify;
}
