package book3.chapter03;

public class UnsafeLazyInitialization {
    private static Instance instance;

    public static Instance getInstance() {
        if (instance == null) //
            instance = new Instance(); //2
        return instance;
    }

    static class Instance {
    }
}
