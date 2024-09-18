module com.xenoterracide.test {
  opens com.xenoterracide.test to org.junit.platform.commons;

  requires spring.boot.test;
  requires org.junit.jupiter.api;
  requires org.springframework.modulith.core;
  requires com.xenoterracide;
}
