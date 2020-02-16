package Main.Entitys;

import java.time.LocalDate;

public class OperationsEntity {

    public int id;

    public String description;

    public float cost;

    public String status; //Completed inProgress Project

    public LocalDate endDate;

    public OperationsEntity(int id, String description, float cost, String status, LocalDate endDate) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.status = status;
        this.endDate = endDate;
    }


}
