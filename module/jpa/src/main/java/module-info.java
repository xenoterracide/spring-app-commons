import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  requires static org.jspecify;
  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires java.base;
  requires org.apache.commons.lang3;
  requires spring.beans;
  requires spring.context;
  requires spring.tx;
  exports com.xenoterracide.jpa;
  opens com.xenoterracide.jpa;
  exports com.xenoterracide.jpa.annotation;
  opens com.xenoterracide.jpa.annotation; // TODO: figure out how to do only for jpa vendors
}
