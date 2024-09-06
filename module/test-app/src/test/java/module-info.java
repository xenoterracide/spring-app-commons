module spring.app.commons.test.app.test {
  requires spring.boot.test;
  requires org.junit.jupiter.api;
  opens com.xenoterracide.tm to org.junit.platform.commons;
}
