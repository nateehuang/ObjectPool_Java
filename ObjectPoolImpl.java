package edu.nyu.compfin19.homework3;

import java.util.function.Supplier;

/**
 * An implementation of an {@code ObjectPool} for objects that implements the {@code
 * PointerableObject} interface.
 *
 * <p>This object pool implements a growing object pool. If the pool do not have any more objects
 * to serve it will create a new object.
 *
 * <p>This pool implementation takes advantage of the objects in the pool are {@code
 * PointerableObject} and do not use any additional backing list or an array in the pool
 * implementation.
 *
 * @param <T>
 */
public class ObjectPoolImpl<T extends PointerableObject<T>> implements ObjectPool<T> {

    /**
     * field _item keep track of current available object in the pool
     * field _supplier supply new object slot and extend the pool
     */
    private T           _item;
    private Supplier<T> _supplier;

    /**
     * Constructor for the class
     *
     * @param supplier
     */
    public ObjectPoolImpl(Supplier<T> supplier){
        _supplier = supplier;
        _item = supplier.get();
    } // test_8xiz0d


    /**
     * Returns an object of type T
     *
     * @return an object of type T
     */
    @Override
    public T borrowObject() {
        // create a new object in case
        // there is no object in the pool
        if (_item.next() == null){
            _item.next(_supplier.get());
            _item.next().previous( _item);
        }
        var lend = _item;
        // move on to the next available object
        _item = _item.next();
        // set the _item previous to be null
        // since the previous is lent out
        _item.previous(null);
        lend.next(null);
        // lend out the object
        return lend;
    } // test_8xiz0d

    /**
     * Returns on object of type T to the pool.
     *
     * @param t the object to return to the pool
     */
    @Override
    public void returnObject(T t) {
        // return the object after _item
        _item.next(t);
        t.previous( _item);
        t.next(null);
    } // test_8xiz0d
}
