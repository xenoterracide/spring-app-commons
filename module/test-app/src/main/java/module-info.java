import org.jspecify.annotations.NullMarked;

/**
 * Provide an easy to include {@code TestApplication} implementation for {@code com.xenoterracide} Spring Boot
 * Applications. Simply include the module on your classpath.
 */
@NullMarked module com.xenoterracide {
  requires transitive static org.jspecify;
  requires spring.boot.autoconfigure;
  opens com.xenoterracide to spring.core;
}
