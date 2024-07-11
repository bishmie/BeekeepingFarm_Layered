package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CollectorDAO;
import lk.ijse.entity.Collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectorDAOImpl implements CollectorDAO {
    @Override
    public boolean save(Collector entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO honeyCollector (collectorId,name,address,contact,email,salary) VALUES (?,?,?,?,?,?)",entity.getCollectorId(),entity.getName(),entity.getAddress(),entity.getContact(),entity.getEmail(),entity.getSalary());
    }

    @Override
    public Collector search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM honeyCollector WHERE collectorId =?",id);
        if(resultSet.next()){
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);
            String email = resultSet.getString(5);
            Double salary = Double.valueOf(String.valueOf(resultSet.getDouble(6)));
            return new Collector(resultSet.getString(1),name,address,contact,email,salary);
        }
        return null;
    }

    @Override
    public boolean update(Collector entity) throws SQLException, ClassNotFoundException {
return SQLUtil.execute("UPDATE honeyCollector SET name =? , address=? , contact=?, email=? , salary=? WHERE collectorId =?", entity.getName(),entity.getAddress(),entity.getContact(),entity.getEmail(),entity.getSalary(),entity.getCollectorId());   }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM honeyCollector WHERE collectorId =?",id);
    }

    @Override
    public ArrayList<Collector> getAll() throws SQLException, ClassNotFoundException {
       ArrayList<Collector> getAllCollectors = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM honeyCollector");
        while (resultSet.next()){

        Collector collector = new Collector(resultSet.getString("collectorId"),resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("contact"),resultSet.getString("email"),resultSet.getDouble("salary"));
    getAllCollectors.add(collector);}
    return getAllCollectors;
    }

    @Override
    public ArrayList<String> getCollectorIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT collectorId FROM honeyCollector");
         while (resultSet.next()){
             String  id = resultSet.getString("collectorId");
             ids.add(id);
         }
         return ids;
    }
}
