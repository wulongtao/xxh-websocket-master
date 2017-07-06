package com.xxh.websocket.core;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by wulongtao on 2017/3/16.
 */
public class CircularQueue<E> implements Iterable<E> {
    private int size;

    //指针
    private Node<E> node;

    private Node<E> first;
    private Node<E> last;

    public CircularQueue() {

    }

    /**
     * 加入队列
     * @param e
     */
    public boolean add(E e){
        /*if (node == null) {
            insertLast(e);
        } else {
            insertCurrent(e);
        }*/
        if (e == null) return false;

        if (existItem(e)) {
            System.out.println("eeee");
            return false;
        }
    	insertLast(e);

        size++;
        return true;
    }

    private boolean existItem(E e) {
        int len = 0;
        for (Node<E> x = first; x != null && len < size(); x = x.next) {

            if (Objects.equals(e, x.item)) {
                System.out.println("eq:"+Objects.equals(e, x.item) + ",item="+x.item);
                return true;
            }
            len++;
        }
        return false;
    }

    private void insertCurrent(E e) {
        final Node<E> l = node.next;
        final Node<E> newNode = new Node<>(node, e, l);
        l.prev = newNode;
        node.next = newNode;
    }

    private void insertLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, first);
        last = newNode;

        if (node == null) node = newNode; //指针
        if (l == null) {
            first = newNode;
            first.prev = first;
            first.next = first;
        }
        else {
            l.next = newNode;
            first.prev = l.next;
        }
    }

    /**
     * 返回当前指针元素并把指针指向下一个元素
     * @return
     */
    public E next() {
        if (node == null) {
            return null;
        }
        E e = node.item;
        node = node.next;

        return e;
    }

    /**
     * 返回当前元素，并把指针指向上一个元素
     * @return
     */
    public E prev() {
        if (node == null) {
            return  null;
        }
        E e = node.item;
        node = node.prev;

        return e;
    }

    /**
     * 删除队列中某一个元素
     * @param e
     * @return
     */
    public E remove(E e) {
        if (e == null) {
        	int len = 0;
            for (Node<E> x = first; x != null && len < size(); x = x.next) {
                if (x.item == null) {
                    return unlink(x);
                }
                len++;
            }
        } else {
        	int len = 0;
            for (Node<E> x = first; x != null && len < size(); x = x.next) {
                if (Objects.equals(e, x.item)|| x.item == null) {
                    return unlink(x);
                }
                len++;
            }
        }

        return null;
    }

    public E peek(){
        return node.item;
    }

    /**
     * 删除节点
     */
    E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (this.first == x) {
			this.first = next;
		}
        if (this.last == x) {
			this.last = prev;
		}
        
        if (prev == x || next == x) {
            this.first = null;
            this.last = null;
            this.node = null;
        } else {
        	next.prev = prev;
            prev.next = next;
            this.node = next;
        }
        x.item = null;
        x = null;
        size--;
        
        return element;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;

        ListIterator() {
            this.next = first;
            this.nextIndex = 0;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }
    }


    /**
     * 节点类
     * @param <E>
     */
    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
        
        
    }

}
