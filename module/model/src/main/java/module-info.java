import org.jspecify.annotations.NullMarked;

/**
 * Common Domain Model classes.
 */
@NullMarked module com.xenoterracide.model {
  requires static org.jspecify;
  requires spring.data.commons;
  exports com.xenoterracide.model;
}
