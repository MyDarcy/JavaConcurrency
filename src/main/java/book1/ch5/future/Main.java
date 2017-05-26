package book1.ch5.future;

/**
 * Author by darcy
 * Date on 17-5-26 下午9:19.
 * Description:
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client();

        Data data = client.request("java");
        System.out.println("Request over.");

        try {
            // 这里用sleep代替了对其他业务逻辑的处理。
            // 在主线程处理业务逻辑的时候, Client开启了一个线程构造RealData, 从而从分利用了时间。
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取真实的数据.
        // 如果真实的数据已经构造完毕, 那么这里会立即返回，
        // 但是如果真实的数据没有构造完毕, 那么这里仍然会阻塞。
        System.out.println("ReadData:" + data.getResult());

    }
}
