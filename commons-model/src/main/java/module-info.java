import org.jspecify.annotations.NullMarked;

/**
 * Common Domain Model classes.
 */
@NullMarked module com.xenoterracide.model {
  exports com.xenoterracide.commons.model;
  opens com.xenoterracide.commons.model;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.data.commons;
  requires org.jmolecules.ddd;
}
