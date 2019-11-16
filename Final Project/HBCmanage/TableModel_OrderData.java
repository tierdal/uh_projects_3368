package HBCmanage;


import javafx.beans.property.*;

public class TableModel_OrderData {
    private final IntegerProperty RepairOrder_ID = new SimpleIntegerProperty();
    private final IntegerProperty Customer_ID = new SimpleIntegerProperty();
    private final StringProperty Customer_FirstName = new SimpleStringProperty();
    private final StringProperty Customer_LastName = new SimpleStringProperty();
    private final DoubleProperty RepairOrder_Total = new SimpleDoubleProperty();
    private final IntegerProperty TrackingOrder_TrackingNumber = new SimpleIntegerProperty();
    private final StringProperty RepairOrder_Status = new SimpleStringProperty();
    private final StringProperty RepairOrder_DateCreated = new SimpleStringProperty();
    private final IntegerProperty Employee_ID = new SimpleIntegerProperty();
    private final StringProperty Employee_Name = new SimpleStringProperty();

    public IntegerProperty RepairIDProperty() {return RepairOrder_ID;}
    public IntegerProperty CustomerID_Property() {return Customer_ID;}
    public StringProperty Fname_Property(){return Customer_FirstName;}
    public StringProperty LNAme_Property() {return Customer_LastName;}
    public DoubleProperty RepiarOrderTotal_Property() {return RepairOrder_Total;}
    public IntegerProperty Tracking_Property() {return TrackingOrder_TrackingNumber;}
    public StringProperty Status_Property() {return RepairOrder_Status;}
    public StringProperty Date_Property() {return RepairOrder_DateCreated;}
    public IntegerProperty EmployeeID() {return Employee_ID;}
    public StringProperty EmployeeName() {return Employee_Name;}

    public TableModel_OrderData(int RepairID, int CustID, String fname, String lname, double total, int trackingnumber,
                                String Status, String date, int EmployeeID, String ename){
        RepairOrder_ID.set(RepairID);
        Customer_ID.set(CustID);
        Customer_FirstName.set(fname);
        Customer_LastName.set(lname);
        RepairOrder_Total.set(total);
        TrackingOrder_TrackingNumber.set(trackingnumber);
    }


}
