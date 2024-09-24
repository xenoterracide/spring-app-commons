import org.jspecify.annotations.NullMarked;

/**
 * Security model.
 */
@NullMarked module com.xenoterracide.security {
  exports com.xenoterracide.security;
  opens com.xenoterracide.security;
  opens com.xenoterracide.security.user;
  exports com.xenoterracide.security.user;

  requires static org.jmolecules.architecture.layered;
  requires static org.jspecify;
  requires static com.xenoterracide.tools.java;
  requires static org.immutables.value.annotations;
  requires static jakarta.annotation;
  requires static org.immutables.builder;
  requires static org.immutables.annotate;
  requires org.jmolecules.ddd;
  requires jakarta.persistence;
  requires jakarta.validation;
  requires com.xenoterracide.commons.jpa;
  requires spring.data.commons;
  requires spring.data.jpa;
  requires org.hibernate.orm.envers;
  requires com.github.f4b6a3.uuid;
}
