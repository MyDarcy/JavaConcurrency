package book1.ch5.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Author by darcy
 * Date on 17-5-26 下午4:59.
 * Description:
 */
public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
