package book3.chapter03;

class VolatileFeaturesExample1 {
    long vl = 0L; //

    public synchronized void set(long l) {//�Ե�������ͨ������д��ͬһ����ͬ��
        vl = l;
    }

    public void getAndIncrement() { //
        long temp = get(); //
        temp += 1L; //
        set(temp); //
    }

    public synchronized long get() { //�Ե�������ͨ�����Ķ���ͬһ����ͬ��
        return vl;
    }
}
