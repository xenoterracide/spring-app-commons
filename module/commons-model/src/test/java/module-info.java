import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.commons.model.test {
  opens com.xenoterracide.commons.model.test
    to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;

  requires static com.xenoterracide.tools.java;
  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires org.junit.jupiter.api;
  requires org.assertj.core;
  requires com.xenoterracide.commons.model;
  requires spring.boot.test.autoconfigure;
  requires spring.beans;
  requires jakarta.persistence;
  requires org.jmolecules.ddd;
}
