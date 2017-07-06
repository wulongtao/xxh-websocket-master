package com.xxh.websocket.core;


import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by giant039 on 2017/3/17.
 *
 */
public class CircularBlockingQueue<E> extends CircularQueue<E> {
    /** 对添加，删除，指针移动操作加锁 */
    protected final static ReentrantLock putLock = new ReentrantLock();

	private QueueListener<E> listener;

    public CircularBlockingQueue() {
        super();
    }

	public CircularBlockingQueue(QueueListener<E> listener) {
        super();
        this.listener = listener;
    }

	public void setListener(QueueListener<E> listener) {
        this.listener = listener;
    }

	@Override
    public boolean add(E e) {
        boolean success = true;
        final ReentrantLock putLock = CircularBlockingQueue.putLock;
        try {
            putLock.lockInterruptibly();
            success = super.add(e);

            if (listener != null) listener.afterAdd(e);
        } catch (InterruptedException exp) {
            exp.printStackTrace();
            return false;
        } finally {
            putLock.unlock();
        }

        return success;

    }

    @Override
    public E next() {
        final ReentrantLock putLock = CircularBlockingQueue.putLock;
        try {
            putLock.lockInterruptibly();
            return super.next();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            putLock.unlock();
        }

    }

    @Override
    public E prev() {
        final ReentrantLock putLock = CircularBlockingQueue.putLock;
        try {
            putLock.lockInterruptibly();
            return super.prev();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            putLock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public E remove(E e) {
        final ReentrantLock putLock = CircularBlockingQueue.putLock;
        try {
            putLock.lockInterruptibly();
            
            E retE = super.remove(e);

            if (listener != null) listener.afterRemove(e);

            return retE;
        } catch (InterruptedException exp) {
            exp.printStackTrace();
            return null;
        } finally {
            putLock.unlock();
        }
    }


    /**
     * 监听器监听插入，删除，等操作之后需要实现的功能
     */
    public interface QueueListener<E> {
        void afterAdd(E e);
        void afterRemove(E e);
    }

}
