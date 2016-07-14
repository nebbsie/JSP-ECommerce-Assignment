package model;

import beans.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class productHandler {
    private final DatabaseManager dbMgr;
    
    public productHandler()
    {
        this.dbMgr = new DatabaseManager();
    }
    
    public void deleteProduct(HttpServletRequest request, HttpServletResponse response, int productID)
    {
        dbMgr.establishConnection();
        String DELETE_PRODUCT = "DELETE FROM ROOT.PRODUCTS WHERE \"productID\" = "+productID;
        dbMgr.prepareStatement(DELETE_PRODUCT);
        dbMgr.executePreparedStatement();
        dbMgr.releaseConnection();
    }
    public void getAllProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException
    {
        dbMgr.establishConnection();
        String GET_ALL_PRODUCTS = "SELECT * FROM ROOT.PRODUCTS";
        
        try
        {
            dbMgr.prepareStatement(GET_ALL_PRODUCTS);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                ArrayList<Product> listOfProducts = new ArrayList<>();
                while (rs.next())
                {
                    
                    Product p = new Product();
                    p.setProductName(rs.getString("productName"));
                    p.setCost(rs.getDouble("productCost"));
                    p.setId(rs.getInt("productID"));
                    listOfProducts.add(p);
                }
                
                request.getSession().setAttribute("allProducts", listOfProducts);
            }
        }
        catch(SQLException sqle)
        {
            throw new ServletException("Problem extracting data from the database", sqle);
        }
        finally
        {
            dbMgr.releaseConnection();
        }
    }
    
    public boolean createProduct(HttpServletRequest request, HttpServletResponse response, String productName, double productCost) throws SQLException, ServletException
    {
        dbMgr.establishConnection();
        
        String SEARCH_PRODUCTS = "SELECT * FROM ROOT.PRODUCTS";
        String CREATE = "INSERT INTO ROOT.PRODUCTS (\"productName\", \"productCost\")VALUES ('"+productName+"', "+productCost+")";
        
        boolean foundProduct = false;
        boolean isCreated;
        
        try
        {
            dbMgr.prepareStatement(SEARCH_PRODUCTS);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                
                while (rs.next())
                {
                    String getProductName = rs.getString("productName");
                    
                    if(getProductName.equalsIgnoreCase(productName))
                    {
                        foundProduct = true;
                    }
                }
                
                if(foundProduct == false)
                {
                    dbMgr.prepareStatement(CREATE);
                    
                    if(dbMgr.executePreparedStatement())
                    {
                        isCreated = true;
                        return isCreated;
                    }
                    
                    
                }
            }
            return isCreated = false;
        }
        catch (SQLException sqle) 
        {
            return false;
            
        }
        finally
        {
            dbMgr.releaseConnection();
        }
        
        
    }
}
