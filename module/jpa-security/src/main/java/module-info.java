import org.jspecify.annotations.NullMarked;

/**
 * Security model.
 */
@NullMarked module com.xenoterracide.model.security {
  requires static org.jspecify;
  requires static com.xenoterracide.tools.java;
  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires com.xenoterracide.jpa;
  requires spring.data.commons;
  requires spring.data.jpa;
  requires org.hibernate.orm.envers;
  exports com.xenoterracide.model.security;
  opens com.xenoterracide.model.security to spring.core;
}