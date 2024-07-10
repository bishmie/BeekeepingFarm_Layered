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
        CUSTOMER,USER,PRODUCT,SUPPLIER,QUEENBEE,COLLECTOR
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

            default:
                return null;
        }
    }
}
