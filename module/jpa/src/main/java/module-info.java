import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  requires static org.jspecify;
  requires transitive jakarta.validation;
  requires transitive jakarta.persistence;
  requires java.base;
  requires com.github.f4b6a3.uuid;
  requires org.hibernate.orm.core;
  opens com.xenoterracide.jpa; // TODO: figure out how to do only for jpa vendors
}
