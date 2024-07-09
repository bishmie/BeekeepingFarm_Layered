package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {
    boolean updateProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException;

    ArrayList<ProductDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean saveProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    ProductDTO searchProduct(String id) throws SQLException, ClassNotFoundException;
}
