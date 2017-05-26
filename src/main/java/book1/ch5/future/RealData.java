package book1.ch5.future;

/**
 * Author by darcy
 * Date on 17-5-26 下午9:09.
 * Description:
 */
public class RealData implements Data {
    protected final String result;

    // 构造RealData是一个耗时的过程。
    public RealData(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(string + " ");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = sb.toString();
    }
    @Override
    public String getResult() {
        return result;
    }
}
