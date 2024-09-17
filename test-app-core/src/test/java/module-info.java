import org.jspecify.annotations.NullMarked;

@NullMarked module com.xenoterracide.test.app.test {
  opens com.xenoterracide.tm to org.junit.platform.commons;

  requires static org.jspecify;
  requires static org.jmolecules.architecture.layered;

  requires spring.boot.test;
  requires org.junit.jupiter.api;
}
