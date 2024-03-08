import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  requires java.base;
  requires org.jspecify;
  requires jakarta.validation;
  requires jakarta.persistence;
  requires com.github.f4b6a3.uuid;
  opens com.xenoterracide.jpa; // TODO: figure out how to do only for jpa vendors
}
