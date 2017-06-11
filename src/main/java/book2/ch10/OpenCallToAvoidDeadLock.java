package book2.ch10;

import java.util.HashSet;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-6-11 下午7:41.
 * Description:
 *
 * 通过开放调用来消除死锁的风险.
 */
public class OpenCallToAvoidDeadLock {

    class Taxi {
        private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public Point getLocation() {
            return location;
        }

        /**
         * 开放调用的方式去调用其他外部方法(可能同步,也可能不同步)
         * @param location
         */
        public synchronized void setLocation(Point location) {
            boolean reachDestination;
            synchronized (this) {
                this.location = location;
                reachDestination = location.equals(destination);
            }
            if (reachDestination) {
                dispatcher.notifyAvailable(this);
            }
        }
    }

    class Dispatcher {
        private final Set<Taxi> taxis;
        private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<>();
            availableTaxis = new HashSet<>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        /**
         * 同步方法取调用其他同步方法,可能产生活跃性问题.
         * @return
         */
        public synchronized Image getImage() {

            Set<Taxi> copy;
            synchronized (this) {
                copy = new HashSet<>(taxis);
            }

            Image image = new Image();
            for (Taxi taxi : copy) {
                image.drawMarket(taxi.getLocation());
            }
            return image;
        }
    }

    class Point {

    }

    class Image{

        public void drawMarket(Point location) {

        }
    }

}
