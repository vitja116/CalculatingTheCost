package Main.Entities;

public class TaskOperationsEntity {
    public int id;

    public int operationID;

    public String info;

    public int planedCount;

    public int factCount;

    public float price;

    public float cost;

    public boolean isCompleted;

    public TaskOperationsEntity(int id, int operationID, String info, int planedCount, int factCount, float price, float cost, boolean isCompleted) {
        this.id = id;
        this.operationID = operationID;
        this.info = info;
        this.planedCount = planedCount;
        this.factCount = factCount;
        this.price = price;
        this.cost = cost;
        this.isCompleted = isCompleted;
    }
}
