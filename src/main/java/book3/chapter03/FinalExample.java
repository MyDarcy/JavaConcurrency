package book3.chapter03;

public class FinalExample {
    int                 i;  //
    final int           j;  //final
    static FinalExample obj;

    public FinalExample() { //
        i = 1; //
        j = 2; //
    }

    public static void writer() { //д�߳�Aִ��
        obj = new FinalExample();
    }

    public static void reader() { //
        FinalExample object = obj; //
        int a = object.i; //
        int b = object.j; //
    }
}