import org.jspecify.annotations.NullMarked;

/**
 * Provide an easy to include {@code TestApplication} implementation for {@code com.xenoterracide} Spring Boot
 * Applications. Simply include the module on your classpath.
 */
@NullMarked module com.xenoterracide {
  exports com.xenoterracide to spring.context, spring.beans;
  opens com.xenoterracide to spring.core;
  requires spring.context;
  requires spring.boot.autoconfigure;
  requires static org.jspecify;
}
