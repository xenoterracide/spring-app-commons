import org.jspecify.annotations.NullMarked;

/**
 * Provide our default Application
 */
@NullMarked module com.xenoterracide {
  exports com.xenoterracide to spring.beans, spring.context, com.xenoterracide.test;
  opens com.xenoterracide to spring.core;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.boot;
}
