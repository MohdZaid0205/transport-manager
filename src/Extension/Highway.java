package Extension;

public class Highway {
    private final String id;
    private final String name;
    private volatile int mileage;

    public Highway(String id, String name, int mileage){
        this.id = id;
        this.name = name;
        this.mileage = mileage;
    }

    public int getMileage(){ return this.mileage; }
    public String getId(){ return this.id; }
    public String getName(){ return this.name; }

    public void setMileage(int mileage){
        this.mileage = mileage;
    }

    public void unsynchronizedIncrementMileage(int by){
        this.mileage += by;
    }

    public synchronized void synchronizedIncrementMileage(int by){
        this.mileage += by;
    }
}
