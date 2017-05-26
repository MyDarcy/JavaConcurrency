package book1.ch5.future;

/**
 * Author by darcy
 * Date on 17-5-26 下午9:07.
 * Description:
 */
public class FutureData implements Data {
    protected RealData realData;
    protected boolean isReady;

    // 传入构造好的RealData;
    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll(); // 真实数据已经注入了, 通知getResult方法可以来拿了.
    }

    @Override
    public synchronized String getResult() { // 等待RealData构造完成。
        while (!isReady) {
            try {
                wait(); // 一直等待，直到RealData注入, 收到了通知。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }
}
