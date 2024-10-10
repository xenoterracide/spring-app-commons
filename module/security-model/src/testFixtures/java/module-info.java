import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.model.security.fixtures {
  exports com.xenoterracide.model.security.fixtures;
  opens com.xenoterracide.model.security.fixtures to org.hibernate.orm.core, spring.core;
  requires com.xenoterracide.security;
  requires jakarta.annotation;

  requires static org.immutables.value.annotations;
  requires static org.immutables.builder;
  requires static org.jspecify;
}
