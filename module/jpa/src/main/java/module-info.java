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
  requires org.hibernate.orm.envers;
  requires spring.beans;
  requires spring.context;
  requires spring.data.commons;
  requires spring.tx;
  exports com.xenoterracide.jpa;
  opens com.xenoterracide.jpa; // TODO: figure out how to do only for jpa vendors
}
