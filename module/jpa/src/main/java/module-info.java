import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  requires transitive static org.jspecify;
  requires transitive jakarta.validation;
  requires transitive jakarta.persistence;
  requires java.base;
  requires spring.beans;
  requires spring.context;
  requires spring.tx;
  exports com.xenoterracide.jpa;
  opens com.xenoterracide.jpa; // TODO: figure out how to do only for jpa vendors
}
