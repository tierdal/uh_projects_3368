package HBCmanage;


import javafx.beans.property.*;

public class TableModel_OrderData {
    public IntegerProperty Order_IDProperty() {return Order_ID;}
    public IntegerProperty CustomerID_Property() {return Customer_ID;}
    public StringProperty Customer_Name_Property(){return Customer_Name;}
    public DoubleProperty OrderTotal_Property() {return Order_Total;}
    public StringProperty Tracking_Property() {return Order_TrackingNumber;}
    public StringProperty Status_Property() {return Order_Status;}
    public StringProperty Date_Property() {return Order_DateCreated;}
    public StringProperty EmployeeName() {return Employee_Name;}

    private final IntegerProperty Order_ID = new SimpleIntegerProperty();
    private final IntegerProperty Customer_ID = new SimpleIntegerProperty();
    private final StringProperty Customer_Name = new SimpleStringProperty();
    private final DoubleProperty Order_Total = new SimpleDoubleProperty();
    private final StringProperty Order_TrackingNumber = new SimpleStringProperty();
    private final StringProperty Order_Status = new SimpleStringProperty();
    private final StringProperty Order_DateCreated = new SimpleStringProperty();
    private final StringProperty Employee_Name = new SimpleStringProperty();


    public TableModel_OrderData(int OrderID, int CustID, String name, double total, String trackingnumber,
                                String Status, String date, String ename){
        Order_ID.set(OrderID);
        Customer_ID.set(CustID);
        Customer_Name.set(name);
        Order_Total.set(total);
        Order_TrackingNumber.set(trackingnumber);
        Order_Status.set(Status);
        Order_DateCreated.set(date);
        Employee_Name.set(ename);
    }

    public int getOrder_ID(){return Order_ID.get();}
    public void setOrder_ID(int RepairID){Order_ID.set(RepairID);}

    public int getCustomer_ID(){return Customer_ID.get();}
    public void setCustomer_ID(int CustID){Customer_ID.set(CustID);}

    public String getCustomer_Name(){return Customer_Name.get();}
    public void setCustomer_Name(String name){Customer_Name.set(name);}

    public double getOrder_Total(){return Order_Total.get();}
    public void setOrder_Total(double total){ Order_Total.set(total); }

    public String getOrder_TrackingNumber(){return Order_TrackingNumber.get();}
    public void setOrder_TrackingNumber(String trackingnumber){Order_TrackingNumber.set(trackingnumber);}

    public String getOrder_Status(){return Order_Status.get();}
    public void setOrder_Status(String Status){Order_Status.set(Status);}

    public String getOrder_DateCreated(){return Order_DateCreated.get();}
    public void setOrder_DateCreated(String date){Order_DateCreated.set(date);}

    public String getEmployee_Name(){return Employee_Name.get();}
    public void setEmployee_Name(String ename){Employee_Name.set(ename);}

}
