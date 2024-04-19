import org.jspecify.annotations.NullMarked;

/**
 * The main module for the authn controller.
 */
@NullMarked module com.xenoterracide.controller.authn.main {
  requires spring.context;
  requires spring.security.config;
  requires spring.security.web;
  requires static org.jspecify;
  opens com.xenoterracide.controller.authn to spring.core;
}
