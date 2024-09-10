import org.jspecify.annotations.NullMarked;

/**
 * Provide an easy to include {@code TestApplication} implementation for {@code com.xenoterracide} Spring Boot
 * Applications. Simply include the module on your classpath.
 */
@NullMarked module com.xenoterracide {
  exports com.xenoterracide to spring.beans, spring.context;
  opens com.xenoterracide to spring.core;
  requires static org.jspecify;
  requires spring.boot.autoconfigure;
  requires spring.context;
}
