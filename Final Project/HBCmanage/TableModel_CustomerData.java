package HBCmanage;

import javafx.beans.property.*;

public class TableModel_CustomerData {
    public IntegerProperty IDProperty() {return Customer_ID;}
    public StringProperty PartNumber_Property() {return Customer_FirstName;}
    public StringProperty Desc_Property(){return Customer_LastName;}
    public StringProperty Price_Property() {return Customer_PhoneNumber;}
    public StringProperty Type_Property() {return Customer_EmailAddress;}

    private final IntegerProperty Customer_ID = new SimpleIntegerProperty();
    private final StringProperty Customer_FirstName = new SimpleStringProperty();
    private final StringProperty Customer_LastName = new SimpleStringProperty();
    private final StringProperty Customer_PhoneNumber = new SimpleStringProperty();
    private final StringProperty Customer_EmailAddress = new SimpleStringProperty();

    public TableModel_CustomerData(int ID, String fname, String lname, String phone, String email){
        Customer_ID.set(ID);
        Customer_FirstName.set(fname);
        Customer_LastName.set(lname);
        Customer_PhoneNumber.set(phone);
        Customer_EmailAddress.set(email);
    }

    public int getCustomer_ID(){return Customer_ID.get();}
    public void setCustomer_ID(int ID){Customer_ID.set(ID);}

    public String getCustomer_FirstName(){return Customer_FirstName.get();}
    public void setCustomer_FirstName(String fname){Customer_FirstName.set(fname);}

    public String getCustomer_LastName() {return Customer_LastName.get();}
    public void setCustomer_LastName(String lname){Customer_LastName.set(lname); }

    public String getCustomer_PhoneNumber() {return Customer_PhoneNumber.get();}
    public void setCustomer_PhoneNumber(String phone){Customer_PhoneNumber.set(phone);}

    public String getCustomer_EmaiAddress(){return Customer_EmailAddress.get();}
    public void setCustomer_EmaiAddress(String email){Customer_EmailAddress.set(email);}
}
