import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.commons.jpa.test {
  opens com.xenoterracide.commons.jpa.test
    to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;
  opens com.xenoterracide.commons.jpa.test.transaction
    to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;
  requires org.assertj.core;
  requires com.xenoterracide.commons.jpa;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires spring.beans;
  requires spring.boot.test;
  requires spring.tx;
  requires org.hibernate.orm.envers;
  requires org.apache.commons.lang3;
  requires com.xenoterracide.jpa.fixtures;
  requires spring.test;
  requires spring.boot.test.autoconfigure;
  requires spring.orm;
  requires static org.jspecify;
}
