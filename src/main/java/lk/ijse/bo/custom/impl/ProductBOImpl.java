package lk.ijse.bo.custom.impl;

import com.beust.ah.A;
import lk.ijse.bo.custom.ProductBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProductDAO;
import lk.ijse.entity.Product;
import lk.ijse.model.ProductDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {

   // ProductDAOIMpl productDAOIMpl = new ProductDAOIMpl();
ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);

    public boolean updateProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        return productDAO.update(new Product(productDTO.getProductId(),productDTO.getProductName(),productDTO.getSellingPrice(),productDTO.getNetWeight(),productDTO.getQty(),productDTO.getHarvestId()));
    }

    @Override
    public ArrayList<ProductDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> allProducts = new ArrayList<>();
        ArrayList<Product> all = productDAO.getAll();

        for(Product p : all){
            allProducts.add(new ProductDTO(p.getProductId(),p.getProductName(),p.getSellingPrice(),p.getNetWeight(),p.getQty(),p.getHarvestId()));
        }
        return allProducts;
    }

    @Override
    public boolean saveProduct(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        return productDAO.save(new Product(productDTO.getProductId(),productDTO.getProductName(),productDTO.getSellingPrice(),productDTO.getNetWeight(),productDTO.getQty(),productDTO.getHarvestId()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return productDAO.delete(id);
    }

    @Override
    public ProductDTO searchProduct(String id) throws SQLException, ClassNotFoundException {
      Product product =  productDAO.search(id);
    return new ProductDTO(product.getProductId(),product.getProductName(),product.getSellingPrice(),product.getNetWeight(),product.getQty(),product.getHarvestId());
    }

    @Override
    public ArrayList<String> getProductCodes() throws SQLException, ClassNotFoundException {
      ArrayList<String> allIds = new ArrayList<>();
        ArrayList<String>all = productDAO.getAllIds();
        for(String p: all){
            allIds.add(p);

        }
        return allIds;

    }
}
