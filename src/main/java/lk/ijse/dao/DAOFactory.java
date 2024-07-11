package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER, USER, PRODUCT, SUPPLIER,QUEENBEE, COLLECTOR,HIVE,ORDER,ORDERPRODUCT,HARVEST
    }

    public SuperDao getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case USER:
                return new UserDAOImpl();
            case PRODUCT:
                return new ProductDAOIMpl();
            case SUPPLIER:
                return new SupplierDAOIMPL();
            case QUEENBEE:
                return new QueenBeeDAOImpl();
            case COLLECTOR:
                return  new CollectorDAOImpl();
            case HIVE:
                return new HiveDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERPRODUCT:
                return new OrderDetailsDAOImpl();
            case HARVEST:
                return  new HarvestDAOImpl();


            default:
                return null;
        }
    }
}
