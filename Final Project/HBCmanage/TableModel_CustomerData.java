package HBCmanage;

import javafx.beans.property.*;

public class TableModel_CustomerData {
    public IntegerProperty Customer_IDProperty() {return Customer_ID;}
    public StringProperty Customer_NameProperty() {return Customer_Name;}
    public StringProperty Customer_PhoneNumberProperty() {return Customer_PhoneNumber;}
    public StringProperty Customer_EmailAddressProperty() {return Customer_EmailAddress;}

    private final IntegerProperty Customer_ID = new SimpleIntegerProperty();
    private final StringProperty Customer_Name = new SimpleStringProperty();
    private final StringProperty Customer_PhoneNumber = new SimpleStringProperty();
    private final StringProperty Customer_EmailAddress = new SimpleStringProperty();

    public TableModel_CustomerData(int ID, String name, String phone, String email){
        Customer_ID.set(ID);
        Customer_Name.set(name);
        Customer_PhoneNumber.set(phone);
        Customer_EmailAddress.set(email);
    }

    public int getCustomer_ID(){return Customer_ID.get();}
    public void setCustomer_ID(int ID){Customer_ID.set(ID);}

    public String getCustomer_Name(){return Customer_Name.get();}
    public void setCustomer_Name(String fname){Customer_Name.set(fname);}

    public String getCustomer_PhoneNumber() {return Customer_PhoneNumber.get();}
    public void setCustomer_PhoneNumber(String phone){Customer_PhoneNumber.set(phone);}

    public String getCustomer_EmailAddress(){return Customer_EmailAddress.get();}
    public void setCustomer_EmailAddress(String email){Customer_EmailAddress.set(email);}
}
