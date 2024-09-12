import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  exports com.xenoterracide.jpa;
  exports com.xenoterracide.jpa.annotation;
  exports com.xenoterracide.jpa.util;
  exports com.xenoterracide.jpa.transaction to spring.beans, spring.context;

  opens com.xenoterracide.jpa to org.hibernate.orm.core, spring.core, org.hibernate.validator;
  opens com.xenoterracide.jpa.transaction to spring.core;

  requires java.base;
  requires org.apache.commons.lang3;
  requires spring.data.commons;
  requires spring.beans;
  requires spring.context;
  requires spring.tx;
  requires org.hibernate.orm.envers;

  requires static org.jspecify;
  requires static com.xenoterracide.tools.java;
  requires static jakarta.annotation;

  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires transitive com.xenoterracide.model;
}
