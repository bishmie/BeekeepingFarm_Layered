package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.BekeeperDAO;
import lk.ijse.entity.Beekeeper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BeekeeperDAOImpl implements BekeeperDAO {
    @Override
    public boolean save(Beekeeper entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO beekeeper(beekeeperId,name,address,contact,email,salary) VALUES(?,?,?,?,?,?)",entity.getBeekeeperId(),entity.getName(),entity.getAddress(),entity.getContact(),entity.getEmail(),entity.getSalary());
    }

    @Override
    public Beekeeper search(String id) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.execute("SELECT * FROM beekeeper WHERE beekeeperId=?",id);
       if (resultSet.next()){
           String name = resultSet.getString(2);
           String address = resultSet.getString(3);
           String contact = resultSet.getString(4);
           String email = resultSet.getString(5);
           String salary = resultSet.getString(6);
           return new Beekeeper(resultSet.getString(1),name,address,contact,email,salary);

       }
       return null;
    }

    @Override
    public boolean update(Beekeeper entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE beekeeper SET name =?, address =?, contact =?, email =?, salary =? WHERE beekeeperId =?",entity.getName(),entity.getAddress(),entity.getContact(),entity.getEmail(),entity.getSalary(),entity.getBeekeeperId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM beekeeper WHERE beekeeperId =?",id);
    }

    @Override
    public ArrayList<Beekeeper> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getBeekeeperIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT beekeeperId FROM beekeeper");
        while (resultSet.next()) {
            String id = resultSet.getString("beekeeperId");
            all.add(id);
        }
        return all;
    }
}
