module com.xenoterracide.test.authorization.server {
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.security.oauth2.core;
  requires spring.security.oauth2.authorization.server;
  requires java.sql;
  opens com.xenoterracide.test.authorization.server;
  exports com.xenoterracide.test.authorization.server;
}
