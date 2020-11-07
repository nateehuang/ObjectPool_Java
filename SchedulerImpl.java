package edu.nyu.compfin19.homework3;


import java.util.function.Supplier;

/**
 * An implementation of {@code Scheduler}.
 *
 * <p>The implementation uses an {@code ObjectPool} to ensure that there is no continues
 * generation of garbage.
 */

public class SchedulerImpl implements Scheduler {

    /**
     * field _objectPool is an implementation to ensure no continues generation of garbage
     * field _head keeps track on the event with
     *      the smallest time
     * field _last keeps track on the event with
     *      the largest time
     */
    private ObjectPool<SchedulerContainer> _objectPool;
    private SchedulerContainer _head;
    private SchedulerContainer _last;

    /**
     * Constructor
     */
    public SchedulerImpl(){
        // A supplier that supplies SchedulerContainer
        Supplier<SchedulerContainer> s = SchedulerContainer::SchedulerContainerSupply;
        _objectPool = new ObjectPoolImpl<>(s);
        _head = null;
        _last = null;
    } // test_9ck84y()

    @Override
    public SchedulerTask scheduleAt(long time, Runnable runnable) {

        // If time is negative, throw IllegalArgumentException
        if (time < 0){
            throw new IllegalArgumentException();
        }

        // If runnable is null, throw NullPointerException
        if (runnable == null){
            throw new NullPointerException();
        }

        // Borrow an object from the pool
        SchedulerContainer container = _objectPool.borrowObject();
        // set the time and runnable in the container
        container.set(time, runnable);

        // If there is no event in the schedule,
        // set this event to be the first
        // else, compare the time and
        // put the event in the right place.
        // The SchedulerContainer is linked by
        // the time: i.e., the first event (_head)
        // has the smallest time.
        // The next event always has larger time
        // than the current event
        if (_head == null){
            _head = container;
            _last = container;
        }else{
            SchedulerContainer pointer = _head;
            while (pointer != null){
                if (pointer.get_time()>time){
                    /**
                     * pointer.previous -> pointer
                     * pointer.previous <- pointer
                     */

                    // When container is the first one,
                    // skip below operation
                    if (pointer.previous() != null){
                        container.previous(pointer.previous());
                        /**
                         * pointer.previous <- container
                         * pointer.previous -> pointer
                         * pointer.previous <- pointer
                         */
                        container.previous().next(container);
                        /**
                         * pointer.previous <- container
                         * pointer.previous -> container
                         * pointer.previous <- pointer
                         */
                    }else{
                        _head = container;
                    }

                    container.next(pointer);
                    /**
                     * pointer.previous <- container
                     * pointer.previous -> container -> pointer
                     * pointer.previous <- pointer
                     */
                    pointer.previous(container);
                    /**
                     * pointer.previous <- container <- pointer
                     * pointer.previous -> container -> pointer
                     */
                    break;
                }
                pointer = pointer.next();
            }
            // When container is put at the end
            if (pointer == null){
                _last.next(container);
                container.previous(_last);
                _last = container;
            }
        }
        return container;
    } // test_9ck84y()

    /**
     * Executes all the scheduled task before or at the specified time.
     *
     * @param time the time.
     */
    public void setTimeAndFire(long time) {
        // execute all the tasks with time lower or equal of the current time
        SchedulerContainer pointer = _head;
        SchedulerContainer pre;
        while (pointer != null){
            if (pointer.get_time() <= time){
                pointer.get_runnable().run();
                pre = pointer;
                pointer = pointer.next();
                _objectPool.returnObject(pre);
            }else{
                break;
            }
        }
        if (pointer != null){
            pointer.previous(null);
        }
        _head = pointer;
    } // test_9ck84y()
}
