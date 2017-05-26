package book1.ch5.future;

/**
 * Author by darcy
 * Date on 17-5-26 下午9:11.
 * Description:
 * Client主要是实现了获取FutureRealData,并且开启构造RealData的线程, 并且在接收请求后, 很快的返回FutreData。
 * 立即返回的FutureData中并没有真实数据。
 */
public class Client {
    public Data request(final String query) {
        final FutureData futureData = new FutureData();
        // 启动一个单独的线程构造RealData对象。
        new Thread() {
            @Override
            public void run() {
                // 构造RealData是一个耗时的过程。
                RealData realData = new RealData(query);
                // 将构造好的RealData传入到futureData中; 此时它可能阻塞在了getResult方法处;
                futureData.setRealData(realData);
            }
        }.start();
        return futureData; // FutureData会立刻返回.
    }
}
