module com.xenoterracide.controller.registration.test {
  opens com.xenoterracide.controller.registration.test to org.junit.platform.commons, spring.core;
  requires org.assertj.core;
  requires com.xenoterracide.jpa;
  requires org.junit.jupiter.api;
  requires org.junit.jupiter.params;
  requires spring.beans;
  requires spring.boot.test;
  requires spring.test;
  requires spring.boot.test.autoconfigure;
  requires spring.orm;
  requires spring.graphql.test;
}
