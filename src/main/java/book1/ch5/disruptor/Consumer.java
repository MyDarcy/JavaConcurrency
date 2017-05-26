package book1.ch5.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Author by darcy
 * Date on 17-5-26 下午4:55.
 * Description:
 */

// 消费者需要实现WorkHandler接口, 消费者的作用就是读取数据就是处理，具体的数据读取就是由Disruptor框架进行了封装，
public class Consumer implements WorkHandler<PCData> {
    // onEvent作为框架的回调方法, 在这里对数据进行处理。
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event: --" + pcData.get() * pcData.get() + "--");
    }
}
