package book2.ch9;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-6-11 下午5:01.
 * Description:
 */
public class LongTimeDemo {
    private JButton jButton;
    private JTextField jTextField;
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public LongTimeDemo() {
        this.jButton = new JButton("Button");
        this.jTextField = new JTextField("JTextFiled");
    }

    /**
     * 线程接力的方式是处理长时间任务的典型方法...
     */
    public void init() {
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 第一个子任务更新用户界面
                jButton.setEnabled(false);
                jTextField.setToolTipText("Busy");
                // 第二个子任务中进行复杂计算..
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doBigComputation();
                        } finally {
                            // 第二个子任务完成的时候,第三个子任务进行界面的更新..
                            GUIExecutor.getInstance().execute(new Runnable() {
                                @Override
                                public void run() {
                                    jButton.setEnabled(true);
                                    jTextField.setText("idle");
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void doBigComputation() {

    }
}
