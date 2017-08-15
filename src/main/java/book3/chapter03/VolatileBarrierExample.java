package book3.chapter03;

class VolatileBarrierExample {
    int          a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    void readAndWrite() {
        int i = v1; //
        int j = v2; //
        a = i + j; //
        v1 = i + 1; //
        v2 = j * 2; //
    }


}
