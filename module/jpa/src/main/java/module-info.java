import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  requires static org.jspecify;
  requires java.base;
  requires jakarta.validation;
  requires jakarta.persistence;
  requires com.github.f4b6a3.uuid;
  requires org.hibernate.orm.core;
  opens com.xenoterracide.jpa; // TODO: figure out how to do only for jpa vendors
}
