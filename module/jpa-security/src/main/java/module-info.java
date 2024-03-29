import org.jspecify.annotations.NullMarked;

/**
 * Security model.
 */
@NullMarked module com.xenoterracide.model.security {
  requires static org.jspecify;
  requires static com.xenoterracide.tools.java;
  requires static org.immutables.value.annotations;
  requires static jakarta.annotation;
  requires static org.immutables.builder;
  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires com.xenoterracide.jpa;
  requires spring.data.commons;
  requires spring.data.jpa;
  requires org.hibernate.orm.envers;
  requires com.github.f4b6a3.uuid;
  exports com.xenoterracide.model.security;
  opens com.xenoterracide.model.security to spring.core;
}
