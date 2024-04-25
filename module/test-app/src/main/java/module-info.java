import org.jspecify.annotations.NullMarked;

/**
 * Provide an easy to include {@code TestApplication} implementation for {@code com.xenoterracide} Spring Boot
 * Applications. Simply include the module on your classpath.
 */
@NullMarked module com.xenoterracide {
  requires static org.jspecify;
  requires static spring.core;
  requires spring.boot.autoconfigure;
  requires spring.test;
  requires spring.context;
  opens com.xenoterracide to spring.core;
}
