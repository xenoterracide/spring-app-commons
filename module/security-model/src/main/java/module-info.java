import org.jspecify.annotations.NullMarked;

/**
 * Security model.
 */
@NullMarked module com.xenoterracide.model.security {
  exports com.xenoterracide.model.security;
  exports com.xenoterracide.model.security.user;

  opens com.xenoterracide.model.security;
  opens com.xenoterracide.model.security.user;

  requires static com.xenoterracide.tools.java;
  requires static jakarta.annotation;
  requires static org.immutables.annotate;
  requires static org.immutables.builder;
  requires static org.immutables.value.annotations;
  requires static org.jmolecules.architecture.layered;
  requires static org.jspecify;

  requires transitive com.xenoterracide.commons.jpa;
  requires transitive com.xenoterracide.commons.model;

  requires com.github.f4b6a3.uuid;
  requires jakarta.persistence;
  requires jakarta.validation;
  requires spring.data.commons;
  requires spring.data.jpa;
  requires org.hibernate.orm.envers;
  requires org.jmolecules.ddd;
  requires org.springframework.modulith.api;
}
