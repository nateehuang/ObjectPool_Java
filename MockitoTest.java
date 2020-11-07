package edu.nyu.compfin19.homework3;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

class MockitoTest {

    /**
     * A stub class of PointerableObject that tests ObjectPoolImpl
     */
    class stubPoint implements PointerableObject<stubPoint>{
        private stubPoint _previous;
        private stubPoint _next;

        public stubPoint(){
            _previous = null;
            _next = null;
        }

        @Override
        public stubPoint next(){
            return _next;
        }
        @Override
        public void next(stubPoint t){
            _next = t;
        }
        @Override
        public stubPoint previous(){
            return _previous;
        }
        @Override
        public void previous(stubPoint t){
            _previous = t;
        }

    }

    /**
     * A test of ObjectPoolImpl using Mockito
     */
    @Test
    void test_8xiz0d() {
        // Set up the supplier to pass in the objectPoolImpl class
        stubPoint s1 = new stubPoint();
        stubPoint s2 = new stubPoint();
        stubPoint s3 = new stubPoint();
        stubPoint s4 = new stubPoint();
        Supplier mockSup = mock(Supplier.class);
        when(mockSup.get()).thenReturn(s1);
        verify(mockSup, never()).get();

        // When creating the ObjectPool,
        // supplier.get() is called.
        ObjectPool mockPool = spy(new ObjectPoolImpl(mockSup));
        verify(mockSup).get();

        // Borrow a stubPoint from the pool,
        // call one time of supplier.get()
        when(mockSup.get()).thenReturn(s2);
        var i1 = mockPool.borrowObject();
        verify(mockSup, times(2)).get();

        // Borrow another stubPoint form the pool,
        // call one more time of supplier.get()
        when(mockSup.get()).thenReturn(s3);
        var i2 = mockPool.borrowObject();
        verify(mockSup, times(3)).get();

        // Return a Object and then borrow again
        // have enough to borrow
        // times of calling supplier.get() shouldn't change
        when(mockSup.get()).thenReturn(s4);
        mockPool.returnObject(i1);
        var i3 = mockPool.borrowObject();
        verify(mockSup, times(3)).get();

        // Borrow another one again,
        // this one should be the same as the first one
        var i4 = mockPool.borrowObject();
        assert(i1.equals(i4));
        verify(mockSup, times(4)).get();



    }

    /**
     * A test for SchedulerImpl
     */
    @Test
    void test_9ck84y(){

        //Set up
        long time1 = 10;
        Runnable runnable1 = mock(Runnable.class);
        long time2 = 100;
        Runnable runnable2 = mock(Runnable.class);
        long time3 = 100;
        Runnable runnable3 = mock(Runnable.class);
        long time4 = 120;
        Runnable runnable4 = mock(Runnable.class);
        long time5 = 200;
        Runnable runnable5 = mock(Runnable.class);

        var scheduler = new SchedulerImpl();

        // Test negative time
        boolean flag = false;
        try{
            scheduler.scheduleAt(-1, runnable1);
        } catch (Exception e){
            flag = true;
        }
        assert(flag);

        // Test null as runnable input
        flag = false;
        try{
            scheduler.scheduleAt(100, null);
        } catch (Exception e){
            flag = true;
        }
        assert(flag);

        // randomly schedule a bunch of events
        // not according to the time
        scheduler.scheduleAt(time5, runnable5);
        // If two events with the same time,
        // run the one scheduled first
        // In this case, runnable3 will runs first
        // than runnable2
        scheduler.scheduleAt(time3, runnable3);
        scheduler.scheduleAt(time1, runnable1);
        scheduler.scheduleAt(time2, runnable2);
        var cancel4 = scheduler.scheduleAt(time4, runnable4);

        // cancel one of the runnable
        // so that we won't see it run
        cancel4.cancel();

        // run all the events with time before 150,
        // the order should be runnable1, runnable3, runnable2
        scheduler.setTimeAndFire(150L);

        // verify the order is correct
        InOrder inOrder = inOrder(runnable1, runnable2, runnable3);
        inOrder.verify(runnable1).run();
        inOrder.verify(runnable3).run();
        inOrder.verify(runnable2).run();

        // verify that runnable4 and runnable5 never run
        verify(runnable4, never()).run();
        //verify(runnable5, never()).run();
    }

}