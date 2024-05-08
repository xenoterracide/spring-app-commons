/**
 * This module provides a test authorization server.
 */
module com.xenoterracide.test.authorization.server {
  requires java.sql;
  requires spring.beans;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.security.oauth2.core;
  requires spring.security.oauth2.authorization.server;
  requires static com.xenoterracide.tools.java;
  opens com.xenoterracide.test.authorization.server;
  exports com.xenoterracide.test.authorization.server;
}
