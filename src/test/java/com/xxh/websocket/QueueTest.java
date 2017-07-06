package com.xxh.websocket;

import com.xxh.websocket.core.CircularBlockingQueue;
import com.xxh.websocket.core.CircularQueue;
import com.xxh.websocket.core.Seat;
import com.xxh.websocket.core.SeatCircularBlockingQueue;
import org.junit.Test;

import java.util.Iterator;
import java.util.Objects;

/**
 * Created by wulongtao on 2017/7/6.
 */
public class QueueTest {
    public static void main(String[] args) {
//        CircularQueue<Integer> queue = new CircularQueue<>();
        CircularQueue<Seat> queue = new CircularBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.add(new Seat(i+""));
        }


        for (Seat i : queue) {
            System.out.print(i.getUserId() + " ");
        }
        System.out.println();

        Iterator<Seat> it = queue.iterator();
        while (it.hasNext()) {
            Seat i = it.next();
            if (Objects.equals(i.getUserId(), "0") || Objects.equals(i.getUserId(), "4") || Objects.equals(i.getUserId(), "9")) it.remove();
        }

        for (Seat i : queue) {
            System.out.print(i.getUserId() + " ");
        }
        System.out.println("\nsze="+queue.size());

        System.out.println(queue.add(new Seat("9")));
        System.out.println(queue.add(new Seat("9")));
        for (Seat i : queue) {
            System.out.print(i.getUserId() + " ");
        }
        System.out.println();

        for (int i = 0; i < queue.size(); i++) {
            System.out.print(queue.next().getUserId() + " ");
        }
        System.out.println();

        for (Seat i : queue) {
            System.out.print(i.getUserId() + " ");
        }
        System.out.println();

        SeatCircularBlockingQueue queue1 = new SeatCircularBlockingQueue();
        Seat s1 = new Seat("1");
        s1.addClient("1");
        s1.addClient("1");
        s1.addClient("1");
        Seat s2 = new Seat("1");
        s2.addClient("1");
        s2.addClient("1");
        queue1.add(s1);
        queue1.add(s2);
        System.out.println("total="+queue1.getQueueServiceCount());



    }

    @Test
    public void testSeatQueue() throws InterruptedException {
        SeatCircularBlockingQueue queue = new SeatCircularBlockingQueue();
        queue.add(new Seat("1"));
        for (int i = 2; i < 1000; i++) {
            int type = (int)(Math.random()*2);

            if (type == 0) {
                queue.add(new Seat(i+""));
            } else {
                int count = 1+(int)(Math.random()*5);
                for (int j = 0; j < count; j++) {
                    String pushUserId = queue.pushToUser("c"+i+""+j, 10);
                }
            }

            for (Seat seat : queue) {
                System.out.println(seat.getUserId()+"---"+seat.getSeatServiceCount()+"--"+seat.getAllClient());
            }

            System.out.println();
            System.out.println();

            Thread.sleep(5000);
        }
    }


}
