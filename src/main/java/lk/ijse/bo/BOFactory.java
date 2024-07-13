package lk.ijse.bo;

import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,USER,PRODUCT,SUPPLIER,QUEENBEE,COLLECTOR,HIVE,PO,ORDER,HARVEST,BEEKEEPER,TASK,INVENTORY
    }
    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case USER:
                return new UserBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case QUEENBEE:
                return new QueenBeeBOImpl();
            case COLLECTOR:
                return new CollectorBOImpl();
            case HIVE:
                return new HiveBOImpl();
            case PO:
                return new PlaceOrderBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case HARVEST:;
                return new HarvestBOImpl();
            case BEEKEEPER:
                return new BeekeeperBOImpl();
            case TASK:
                return new TaskBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();

            default:
                return null;
        }
    }
}
