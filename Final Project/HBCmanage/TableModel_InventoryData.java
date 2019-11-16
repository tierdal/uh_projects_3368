package HBCmanage;

import javafx.beans.property.*;

public class TableModel_InventoryData {
    public IntegerProperty IDProperty() {return Inventory_ID;}
    public StringProperty PartNumber_Property() {return Inventory_PartNumber;}
    public StringProperty Desc_Property(){return Inventory_Description;}
    public DoubleProperty Price_Property() {return Inventory_Price;}
    public StringProperty Type_Property() {return Inventory_Type;}
    public DoubleProperty Cost_Property() {return Inventory_Cost;}
    public IntegerProperty Qty_Property() {return Inventory_QtyOnHand; }

    private final IntegerProperty Inventory_ID = new SimpleIntegerProperty();
    private final StringProperty Inventory_PartNumber = new SimpleStringProperty();
    private final StringProperty Inventory_Description = new SimpleStringProperty();
    private final DoubleProperty Inventory_Price = new SimpleDoubleProperty();
    private final StringProperty Inventory_Type = new SimpleStringProperty();
    private final DoubleProperty Inventory_Cost = new SimpleDoubleProperty();
    private final IntegerProperty Inventory_QtyOnHand = new SimpleIntegerProperty();

    public TableModel_InventoryData(int ID, String PartNum, String Desc, double Price, String Type,
                                    double Cost, int QtyOnHand){
        Inventory_ID.set(ID);
        Inventory_PartNumber.set(PartNum);
        Inventory_Description.set(Desc);
        Inventory_Price.set(Price);
        Inventory_Type.set(Type);
        Inventory_Cost.set(Cost);
        Inventory_QtyOnHand.set(QtyOnHand);
    }

    public int getInventory_ID(){return Inventory_ID.get();}
    public void setInventory_ID(int ID){Inventory_ID.set(ID);}

    public String getInventory_PartNumber(){return Inventory_PartNumber.get();}
    public void setInventory_PartNumber(String PartNum){Inventory_PartNumber.set(PartNum);}

    public String getInventory_Description() {return Inventory_Description.get();}
    public void setInventory_Description(String Desc){Inventory_Description.set(Desc); }

    public Double getInventory_Price() {return Inventory_Price.get();}
    public void setInventory_Price(double Price){Inventory_Price.set(Price);}

    public String getInventory_Type(){return Inventory_Type.get();}
    public void setInventory_Type(String Type){Inventory_Type.set(Type);}

    public Double getInventory_Cost(){return Inventory_Cost.get();}
    public void setInventory_Cost(double Cost) {Inventory_Cost.set(Cost);}

    public int getInventory_QtyOnHand() {return Inventory_QtyOnHand.get();}
    public void setInventory_QtyOnHand(int QtyOnHand) {Inventory_QtyOnHand.set(QtyOnHand);}
}
