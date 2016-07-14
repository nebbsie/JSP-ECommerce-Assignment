
package model;

import beans.Product;
import beans.Store;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class storeHandler {
    
    private final DatabaseManager dbMgr;
    
    public storeHandler()
    {
        this.dbMgr = new DatabaseManager();
    }
     public void deleteProduct(HttpServletRequest request, HttpServletResponse response, int storeID)
    {
        dbMgr.establishConnection();
        String DELETE_STORE = "DELETE FROM ROOT.STORES WHERE \"storeID\" = "+storeID;
        dbMgr.prepareStatement(DELETE_STORE);
        dbMgr.executePreparedStatement();
        dbMgr.releaseConnection();
    }
    
    public boolean createStore(HttpServletRequest request, HttpServletResponse response, String storeName) throws ServletException
    {
        dbMgr.establishConnection();
        
        String GET_ALL_STORES = "SELECT * FROM ROOT.STORES";
        String CREATE_STORE = "INSERT INTO ROOT.STORES (\"storeName\") VALUES ('"+storeName+"')";
        
        boolean foundStore = false;
        boolean isCreated;
        
        try
        {
            dbMgr.prepareStatement(GET_ALL_STORES);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                
                while (rs.next())
                {
                    String getStoreName = rs.getString("storeName");
                    
                    if (getStoreName.equals(storeName))
                    {
                        foundStore = true;
                    }
                }
                
                if(foundStore == false)
                {
                    dbMgr.prepareStatement(CREATE_STORE);
                    
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
            throw new ServletException("Unable To Find Product", sqle);
        }
        finally
        {
            dbMgr.releaseConnection();
        }
    }
    
    public void getAllStores(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException
    {
        dbMgr.establishConnection();
        String GET_ALL_STORES = "SELECT * FROM ROOT.STORES";
        
        try
        {
            dbMgr.prepareStatement(GET_ALL_STORES);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                ArrayList<Store> listOfStores = new ArrayList<>();
                while (rs.next())
                {
                    Store s = new Store();
                    s.setStoreID(rs.getInt("storeID"));
                    s.setStoreName(rs.getString("storeName"));
                    listOfStores.add(s);
                }
                
                request.getSession().setAttribute("allStores", listOfStores);
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
    
}
