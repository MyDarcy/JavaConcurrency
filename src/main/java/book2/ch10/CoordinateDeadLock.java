package book2.ch10;

import java.util.HashSet;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-6-11 下午7:41.
 * Description:
 */
public class CoordinateDeadLock {

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
         * 同步方法去调用其他的同步方法.
         * @param location
         */
        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination)) {
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
            Image image = new Image();
            for (Taxi taxi : taxis) {
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
