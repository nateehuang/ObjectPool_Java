package edu.nyu.compfin19.homework3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerContainerTest {

    @Test
    void test_fk8x01() {
        SchedulerContainer s1 = new SchedulerContainer();
        SchedulerContainer s2 = new SchedulerContainer();
        SchedulerContainer s3 = new SchedulerContainer();

        /**
         *  null <- s1 -> null
         *  null <- s2 -> null
         *  null <- s3 -> null
         */
        assertTrue(s1.previous() == null);
        assertTrue(s1.next() == null);

        /**
         *  null <- s1 -> s2
         *  null <- s2 -> null
         */

        s1.next(s2);
        assertTrue(s1.previous() == null);
        assertTrue(s1.next().equals(s2));
        assertTrue(s2.previous() == null);

        /**
         *  null <- s1 -> s2
         *  s1 <- s2 -> s3
         *  s2 <- s3 -> null
         */
        s2.previous(s1);
        s2.next(s3);
        s3.previous(s2);
        assertTrue(s2.previous().equals(s1));
        assertTrue(s2.next().equals(s3));
        assertTrue(s3.previous().equals(s2));
        assertTrue(s3.next() == null);

        /**
         * Set the time and stub runnable to check the functionality
         */
        Runnable r = new Runnable() {
              @Override
              public void run() {
                  System.out.println("run");
              }
          };
        s1.set(100, r);
        assertTrue(s1.get_time() == 100);
        s1.get_runnable().run();

        /**
         *  null <- s1 -> s3
         *  s1 <- s3 -> null
         */
        s2.cancel();
        assertTrue(s1.next().equals(s3));
        assertTrue(s3.previous().equals(s1));

    }
}