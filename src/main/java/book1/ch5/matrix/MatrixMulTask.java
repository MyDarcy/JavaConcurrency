package book1.ch5.matrix;


import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.operator.MatrixOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * Author by darcy
 * Date on 17-5-27 上午9:55.
 * Description:
 */
public class MatrixMulTask extends RecursiveTask<Matrix> {

    Matrix m1;
    Matrix m2;
    // pos代表的计算结果位于结果父矩阵中的位置。
    String pos;
    private static final int THRESHOLD = 3;

    public MatrixMulTask(Matrix m1, Matrix m2, String pos) {
        this.m1 = m1;
        this.m2 = m2;
        this.pos = pos;
    }

    @Override
    protected Matrix compute() {
        System.out.println(Thread.currentThread().getId() + ":" + Thread.currentThread().getName() + " Start...");
        // 如果粒度太大，那么需要先进行分解再运算。
        if (m1.rows() <= THRESHOLD || m2.cols() <= THRESHOLD) {
            Matrix matrix = MatrixOperator.multiply(m1, m2);
            return matrix;
        } else {
            int rows;
            rows = m1.rows();
            // 左乘的矩阵水平分割
            Matrix m11 = m1.getSubMatrix(1, 1, rows / 2, m1.cols());
            Matrix m12 = m1.getSubMatrix(rows / 2 + 1, 1, m1.rows(), m1.cols());

            // 右乘的矩阵垂直分割
            Matrix m21 = m2.getSubMatrix(1, 1, m2.rows(), m2.cols() / 2);
            Matrix m22 = m2.getSubMatrix(1, m2.cols() / 2 + 1, m2.rows(), m2.cols());

            ArrayList<MatrixMulTask> subTasks = new ArrayList<>();
            MatrixMulTask temp = null;
            // 参数用于后续的拼接。
            // 左上
            temp = new MatrixMulTask(m11, m21, "m1");
            subTasks.add(temp);
            // 右上
            temp = new MatrixMulTask(m11, m22, "m2");
            subTasks.add(temp);
            // 左下
            temp = new MatrixMulTask(m12, m21, "m3");
            subTasks.add(temp);
            // 右下
            temp = new MatrixMulTask(m12, m22, "m4");
            subTasks.add(temp);

            for (MatrixMulTask task : subTasks) {
                task.fork();
            }

            Map<String, Matrix> matrixMap = new HashMap<>();
            for (MatrixMulTask task : subTasks) {
                // task.pos映射到子矩阵计算的结果。
                matrixMap.put(task.pos, task.join());
            }

            // 上半部分的水平拼接
            Matrix temp1 = MatrixOperator.horizontalConcatenation(matrixMap.get("m1"),
                    matrixMap.get("m2"));
            // 下办部分的水平拼接
            Matrix temp2 = MatrixOperator.horizontalConcatenation(matrixMap.get("m3"),
                    matrixMap.get("m4"));
            // 结果的垂直拼接
            Matrix result = MatrixOperator.verticalConcatenation(temp1, temp2);
            return result;
        }
    }
}
