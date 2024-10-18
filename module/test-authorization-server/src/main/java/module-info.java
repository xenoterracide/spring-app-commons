import org.jspecify.annotations.NullMarked;

/**
 * This module provides a test authorization server.
 */
@NullMarked module com.xenoterracide.test.authorization.server {
  opens com.xenoterracide.authorization.server;

  requires static com.xenoterracide.tools.java;
  requires static org.jmolecules.architecture.layered;
  requires static org.jspecify;

  requires java.sql;
  requires spring.beans;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.security.oauth2.core;
  requires spring.security.oauth2.authorization.server;
  requires spring.core;
  requires spring.web;
  requires spring.security.config;
  requires spring.security.web;
}