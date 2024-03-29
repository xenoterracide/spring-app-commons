import org.jspecify.annotations.NullMarked;

/**
 * JPA utilities.
 */
@NullMarked module com.xenoterracide.jpa {
  requires static org.jspecify;
  requires static com.xenoterracide.tools.java;
  requires transitive jakarta.persistence;
  requires transitive jakarta.validation;
  requires transitive com.xenoterracide.model;
  requires java.base;
  requires org.apache.commons.lang3;
  requires spring.data.commons;
  requires spring.beans;
  requires spring.context;
  requires spring.tx;
  requires org.hibernate.orm.envers;
  exports com.xenoterracide.jpa;
  opens com.xenoterracide.jpa to spring.core;
  exports com.xenoterracide.jpa.annotation;
}
