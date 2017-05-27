package book1.ch5.matrix;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Author by darcy
 * Date on 17-5-27 上午10:29.
 * Description:
 */
public class PMatrixMul {
    private static final int granularity = 3;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 300 × 300的随机矩阵
        Matrix m1 = MatrixFactory.getRandomMatrix(300, 300, null);
        Matrix m2 = MatrixFactory.getRandomMatrix(300, 300, null);
        // 构造矩阵并且提交给线程池。
        MatrixMulTask task = new MatrixMulTask(m1, m2, null);
        ForkJoinTask<Matrix> result = forkJoinPool.submit(task);
        Matrix pr = result.get();
        System.out.println(System.currentTimeMillis() - start);
        /*System.out.println(pr);*/
    }
}
