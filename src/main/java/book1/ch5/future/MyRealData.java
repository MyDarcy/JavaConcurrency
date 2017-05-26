package book1.ch5.future;

import java.util.concurrent.Callable;

/**
 * Author by darcy
 * Date on 17-5-26 下午9:45.
 * Description:
 */
public class MyRealData implements Callable<String> {
    private String data;

    public MyRealData(String data) {
        this.data = data;
    }

    /**
     * 会构造真实的数据并且返回,这个过程可能是缓慢的.
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(data + " ");
            try {
                // 模拟真实数据的缓慢构造
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        return sb.toString();
    }
}
