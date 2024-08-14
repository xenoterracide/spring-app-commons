module test.app {
  requires spring.boot.test;
  requires org.junit.jupiter.api;
  opens com.xenoterracide.test to org.junit.platform.commons;
}
