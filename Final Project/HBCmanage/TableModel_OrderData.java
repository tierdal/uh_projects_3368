package HBCmanage;


import javafx.beans.property.*;

public class TableModel_OrderData {
    private final IntegerProperty RepairOrder_ID = new SimpleIntegerProperty();
    private final IntegerProperty Customer_ID = new SimpleIntegerProperty();
    private final StringProperty Customer_FirstName = new SimpleStringProperty();
    private final StringProperty Customer_LastName = new SimpleStringProperty();
    private final DoubleProperty RepairOrder_Total = new SimpleDoubleProperty();
    private final StringProperty TrackingOrder_TrackingNumber = new SimpleStringProperty();
    private final StringProperty RepairOrder_Status = new SimpleStringProperty();
    private final StringProperty RepairOrder_DateCreated = new SimpleStringProperty();
    private final IntegerProperty Employee_ID = new SimpleIntegerProperty();
    private final StringProperty Employee_Name = new SimpleStringProperty();

    public IntegerProperty Repair_IDProperty() {return RepairOrder_ID;}
    public IntegerProperty CustomerID_Property() {return Customer_ID;}
    public StringProperty Fname_Property(){return Customer_FirstName;}
    public StringProperty LName_Property() {return Customer_LastName;}
    public DoubleProperty RepiarOrderTotal_Property() {return RepairOrder_Total;}
    public StringProperty Tracking_Property() {return TrackingOrder_TrackingNumber;}
    public StringProperty Status_Property() {return RepairOrder_Status;}
    public StringProperty Date_Property() {return RepairOrder_DateCreated;}
    public IntegerProperty EmployeeID() {return Employee_ID;}
    public StringProperty EmployeeName() {return Employee_Name;}

    public TableModel_OrderData(int RepairID, int CustID, String fname, String lname, double total, String trackingnumber,
                                String Status, String date, int EmployeeID, String ename){
        RepairOrder_ID.set(RepairID);
        Customer_ID.set(CustID);
        Customer_FirstName.set(fname);
        Customer_LastName.set(lname);
        RepairOrder_Total.set(total);
        TrackingOrder_TrackingNumber.set(trackingnumber);
        RepairOrder_Status.set(Status);
        RepairOrder_DateCreated.set(date);
        Employee_ID.set(EmployeeID);
        Employee_Name.set(ename);
    }

    public int getRepairOrder_ID(){return RepairOrder_ID.get();}
    public void setRepairOrder_ID(int RepairID){RepairOrder_ID.set(RepairID);}

    public int getCustomer_ID(){return Customer_ID.get();}
    public void setCustomer_ID(int CustID){Customer_ID.set(CustID);}

    public String getCustomer_FirstName(){return Customer_FirstName.get();}
    public void setCustomer_FirstName(String fname){Customer_FirstName.set(fname);}

    public String getCustomer_LastName(){return Customer_LastName.get();}
    public void setCustomer_LastName(String lname){Customer_LastName.set(lname);}

    public double getRepairOrder_Total(){return RepairOrder_Total.get();}
    public void setRepairOrder_Total(double total){ RepairOrder_Total.set(total); }

    public String getTrackingOrder_TrackingNumber(){return TrackingOrder_TrackingNumber.get();}
    public void setTrackingOrder_TrackingNumber(String trackingnumber){TrackingOrder_TrackingNumber.set(trackingnumber);}

    public String getRepairOrder_Status(){return RepairOrder_Status.get();}
    public void setRepairOrder_Status(String Status){RepairOrder_Status.set(Status);}

    public String getRepairOrder_DateCreated(){return RepairOrder_DateCreated.get();}
    public void setRepairOrder_DateCreated(String date){RepairOrder_DateCreated.set(date);}

    public int getEmployee_ID(){return Employee_ID.get();}
    public void setEmployee_ID(int EmployeeID){Employee_ID.set(EmployeeID);}

    public String getEmployee_Name(){return Employee_Name.get();}
    public void setEmployee_Name(String ename){Employee_Name.set(ename);}

}
