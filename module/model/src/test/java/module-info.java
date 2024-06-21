module spring.app.commons.model.test {
  requires org.junit.jupiter.api;
  requires org.assertj.core;
  requires com.xenoterracide.model;
  requires java.base;
  requires spring.boot.test.autoconfigure;
  requires spring.beans;
  requires jakarta.persistence;
  requires static com.xenoterracide.tools.java;
  opens com.xenoterracide.tm.model to org.junit.platform.commons, org.hibernate.orm.core, net.bytebuddy, spring.core;
}
