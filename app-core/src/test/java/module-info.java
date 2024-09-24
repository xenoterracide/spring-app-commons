import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.test {
  opens com.xenoterracide.test to org.junit.platform.commons;
  requires static org.jspecify;

  requires spring.boot.test;
  requires org.junit.jupiter.api;
  requires com.xenoterracide;
  requires org.assertj.core;
}
