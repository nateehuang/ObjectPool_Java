package edu.nyu.compfin19.homework3;

/**
 * A container class for a class of type T
 *
 * @param T the type this container hold
 */
public class SchedulerContainer implements PointerableObject<SchedulerContainer>, SchedulerTask {

    private SchedulerContainer _next;
    private SchedulerContainer _previous;
    private long               _time;
    private Runnable           _runnable;

    /**
     * A static method return a SchedulerContainer type
     *
     * @return SchedulerContainer
     */
    public static SchedulerContainer SchedulerContainerSupply(){
        return new SchedulerContainer();
    }

    /**
     *  Constructor for SchedulerContainer
     */
    SchedulerContainer(){
        _next = null;
        _previous = null;
    }// test_fk8x01

    /**
     * Set time and runnable in the SchedulerContainer
     *
     * @param time
     * @param runnable
     */
    public void set(long time, Runnable runnable){
        _time = time;
        _runnable = runnable;
    }// test_fk8x01

    /**
     *
     * @return time of the Schedule event
     */
    public long get_time(){
        return _time;
    }// test_fk8x01

    /**
     *
     * @return event to run
     */
    public Runnable get_runnable(){
        return _runnable;
    }// test_fk8x01

    /**
     * Returns the object this object points to.
     * @return the object this object points to or null
     */
    @Override
    public SchedulerContainer next() {
        return _next;
    }// test_fk8x01

    /**
     * Sets the object this object points to.
     *
     * @param t the object this object points to.
     */
    @Override
    public void next(SchedulerContainer schedulerContainer) {
        this._next = schedulerContainer;
    }// test_fk8x01


    /**
     * Returns the objects pointing to this object.
     *
     * @return the objects pointing to this objects or null
     */
    @Override
    public SchedulerContainer previous(){
        return _previous;
    }// test_fk8x01

    /**
     * Sets the object pointing to this object.
     *
     * @param t the object pointing to this object
     */
    @Override
    public void previous(SchedulerContainer schedulerContainer) {
        this._previous =  schedulerContainer;

    }// test_fk8x01

    /**
     * Cancel the scheduled task from being executed by a scheduler.
     */
    @Override
    public void cancel() {
        _previous.next(_next);
        _next.previous(_previous);
    }// test_fk8x01

}
