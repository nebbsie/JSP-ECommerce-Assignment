package model;

import beans.StockLink;
import beans.Store;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class stockHandler {
    
     private final DatabaseManager dbMgr;
     
     public stockHandler()
     {
         this.dbMgr = new DatabaseManager();
     }
     
     public void createStockLink(HttpServletRequest request, HttpServletResponse response, int storeID, int productID) throws SQLException
     {
        dbMgr.establishConnection();
        
        String CREATE_STOCK_LINK = "INSERT INTO ROOT.STORESTOCK (\"fkStoreID\", \"fkProductID\", DATEOFCHANGE) VALUES ("+storeID+","+productID+", CURRENT_DATE)";
        String CHECK_STOCKS = "SELECT * FROM STORESTOCK";
        
        boolean found = false;
         
         dbMgr.prepareStatement(CHECK_STOCKS);
         
         if(dbMgr.executePreparedStatement())
         {
             ResultSet rs = dbMgr.getResultSet();
             while(rs.next())
             {
                 int getStoreID = rs.getInt("fkStoreID");
                 int getProductID = rs.getInt("fkProductID");
                 
                 if(getStoreID == storeID && getProductID == productID )
                 {
                     found = true;
                 }
                 
             }
             
             if (found == false)
             {
                dbMgr.prepareStatement(CREATE_STOCK_LINK);
                dbMgr.executePreparedStatement();
             }
         }
        
        dbMgr.releaseConnection();
     }
     
     public void deleteStockLink(HttpServletRequest request, HttpServletResponse response, int stockLinkID)
     {
         dbMgr.establishConnection();
         String DEL_STOCKLINK = "DELETE FROM ROOT.STORESTOCK WHERE \"inventoryID\" ="+stockLinkID;

            dbMgr.prepareStatement(DEL_STOCKLINK);
            dbMgr.executePreparedStatement();

     }
     
     public void getAllStockLinks(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException
     {
         dbMgr.establishConnection();
         String GET_ALL_STOCK = "SELECT STORESTOCK.\"inventoryID\", STORES.\"storeName\",PRODUCTS.\"productName\", PRODUCTS.\"productCost\", PRODUCTS.\"productIMG\" \n"+
                                "FROM STORES INNER JOIN STORESTOCK ON STORES.\"storeID\"=STORESTOCK.\"fkStoreID\"\n" +
                                "INNER JOIN PRODUCTS ON STORESTOCK.\"fkProductID\"=PRODUCTS.\"productID\"";
         
         
         
         try
        {
            dbMgr.prepareStatement(GET_ALL_STOCK);
            
            if(dbMgr.executePreparedStatement())
            {
                ResultSet rs = dbMgr.getResultSet();
                ArrayList<StockLink> listOfLinks = new ArrayList<>();
                while (rs.next())
                {
                    StockLink sl = new StockLink();
                    sl.setProductName(rs.getString("productName"));
                    sl.setStoreName(rs.getString("storeName"));
                    sl.setProductCost(rs.getDouble("productCost"));
                    sl.setStockLinkID(rs.getInt("inventoryID"));
                    sl.setProductIMG(rs.getString("productIMG"));
                    listOfLinks.add(sl);
                }
                
                request.getSession().setAttribute("allStockLinks", listOfLinks);
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
