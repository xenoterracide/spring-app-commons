/**
 * Provide an easy to include {@code TestApplication} implementation for {@code com.xenoterracide} Spring Boot
 * Applications. Simply include the module on your classpath.
 */
module com.xenoterracide {
  requires spring.boot.autoconfigure;
  opens com.xenoterracide to spring.core;
}
