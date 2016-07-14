package model;

import beans.User;
import beans.Product;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class userHandler {
    private final DatabaseManager dbMgr;
    
    public userHandler()
    {
        this.dbMgr = new DatabaseManager();
    }
    
    public boolean loginUser(HttpServletRequest request, HttpServletResponse response, String username, String password) throws SQLException, ServletException
    {   
        dbMgr.establishConnection();
        
        String SEARCH_USERS = "SELECT * FROM users";
        
        boolean foundUser = false;
        
        try
        {
            dbMgr.prepareStatement(SEARCH_USERS);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                
                while (rs.next())
                {
                    String usernameGet = rs.getString("userUsername");
                    String passwordGet = rs.getString("userPassword");
                    
                    if(usernameGet.equals(username) && passwordGet.equals(password))
                    {
                        foundUser = true;
                        System.out.println("FOUND");
                        User user = new User();
                        
                        user.setId(rs.getInt("userID"));
                        user.setFirstName(rs.getString("userFirstName"));
                        user.setLastName(rs.getString("userLastName"));
                        user.setUsername(rs.getString("userUsername"));
                        user.setEmail(rs.getString("userEmail"));
                        user.setPassword(rs.getString("userPassword"));
                        user.setIsAdmin(rs.getBoolean("isAdmin"));
                        user.setAddress(rs.getString("userAddress"));
                        
                        request.getSession().setAttribute("user", user);
                    }
                }
            }
            dbMgr.releaseConnection();
        }
        
        catch (SQLException sqle) 
        {
            throw new ServletException("Unable To Find User", sqle);
        }
        return foundUser; 
    }
    
    public Product getProduct(HttpServletRequest request, HttpServletResponse response, String productName){
        dbMgr.establishConnection();
        
        Product p = new Product();
        
        String SELECT_PRODUCT = "SELECT * FROM PRODUCTS WHERE PRODUCTS.\"productName\" = '"+productName+"'";
        
        boolean foundUser = false;
        
        try
        {
            dbMgr.prepareStatement(SELECT_PRODUCT);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                
                while (rs.next())
                {
                    p.setProductName(rs.getString("productName"));
                    p.setCost(Double.parseDouble(rs.getString("productCost")));
                    p.setId(rs.getInt("productID"));
                    
                }
                
                return p; 
            }
            dbMgr.releaseConnection();
        } catch(SQLException se){
            se.printStackTrace();
        }
        
        return null;
        
    }
   
}

