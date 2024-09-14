module com.xenoterracide.model.security.test {
  opens com.xenoterracide.model.security.test
    to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;
  requires org.assertj.core;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires spring.beans;
  requires spring.boot.test;
  requires spring.tx;
  requires com.xenoterracide.model.security.fixtures;
  requires spring.test;
  requires spring.boot.test.autoconfigure;
  requires spring.orm;
  requires com.xenoterracide.model.security;
  requires jakarta.persistence;
}
