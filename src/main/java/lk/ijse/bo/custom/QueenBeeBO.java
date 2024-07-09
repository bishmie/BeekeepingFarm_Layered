package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.BeeQueenDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueenBeeBO extends SuperBO {
    boolean saveQueenBee(BeeQueenDTO beeQueenDTO) throws SQLException, ClassNotFoundException;

    boolean updateQueen(BeeQueenDTO beeQueenDTO) throws SQLException, ClassNotFoundException;

    boolean deleteQueen(String id) throws SQLException, ClassNotFoundException;

    BeeQueenDTO searchQueen(String id) throws SQLException, ClassNotFoundException;

    ArrayList<BeeQueenDTO> getAllQueens() throws SQLException, ClassNotFoundException;
}
