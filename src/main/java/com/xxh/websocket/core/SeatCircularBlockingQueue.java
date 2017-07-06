package com.xxh.websocket.core;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wulongtao on 2017/7/6.
 *
 */
public class SeatCircularBlockingQueue extends CircularBlockingQueue<Seat> {

    public SeatCircularBlockingQueue() {
    }

    /**
     * 获取队列中所有坐席服务的人数总和
     * @return
     */
    public int getQueueServiceCount() {
        int count = 0;
        for (Seat seat : this) {
            count += seat.getSeatServiceCount();
        }

        return count;
    }

    /**
     * 将咨询用户从相应坐席队列的相应坐席中删除
     * @param userId
     * @return
     */
    public String removeClientUser(String userId) {
        if (size() == 0) return null;

        final ReentrantLock putLock = this.putLock;
        try {
            putLock.lockInterruptibly();

            for (Seat seat : this) {
                if (seat.removeClient(userId)) {
                    return seat.getUserId();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            putLock.unlock();
        }

        return null;
    }

    public String pushToUser(String userId, int limit) {
        if (size() == 0) return null;

        final ReentrantLock putLock = this.putLock;
        try {
            putLock.lockInterruptibly();

            Seat seat = null;
            int evg = getQueueServiceCount() / size();
            for (int i = 0; i < size(); i++) {
                seat = peek();

                if (seat.getSeatServiceCount() <= evg && seat.getSeatServiceCount() < limit) {
                    //加入解答队列
                    seat.addClient(userId);
                    return seat.getUserId();
                }

                next();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            putLock.unlock();
        }

        return null;
    }

    public String pushLastServiceToUser(String userId, int limit, String lastToUserId) {
        if (size() == 0) return null;

        final ReentrantLock putLock = this.putLock;
        try {
            putLock.lockInterruptibly();

            Seat seat = null;
            for (int i = 0; i < size(); i++) {
                seat = peek();

                if (Objects.equals(seat.getUserId(), lastToUserId) && seat.getSeatServiceCount() < limit) {
                    seat.addClient(userId);
                    return seat.getUserId();
                }

                next();
            }
        } catch (InterruptedException exp) {
            exp.printStackTrace();
            return null;
        } finally {
            putLock.unlock();
        }

        return pushToUser(userId, limit);
    }

}
