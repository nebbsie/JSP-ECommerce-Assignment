
package servlets;

import beans.Product;
import beans.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.productHandler;
import model.stockHandler;
import model.storeHandler;
import model.userHandler;

public class controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        RequestDispatcher rd;
        
        String command = request.getParameter("command");
        
        if (command != null)
        {
            try
            {
                
                if (command.equals("login"))
                {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    request.getSession().setAttribute("Re Enter Username", "false");
                    request.getSession().setAttribute("Re Enter Password", "false");
                    int failed = 0;
                    
                    if(username.equals(""))
                    {
                        request.getSession().setAttribute("Re Enter Username", "true");
                        failed = 1;
                    }
                    if(password.equals(""))
                    {
                        request.getSession().setAttribute("Re Enter Password", "true");
                        failed = 1;
                    }
                    if(failed == 0)
                    {
                        loginUser(request, response, username, password);
                    }
                    else{
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
  
                }
                else if(command.equals("deleteBasketItem")){
                    int num = Integer.parseInt(request.getParameter("basketID"));
                    deleteBasket(request, response, num);
                    
                }
                else if(command.equals("addToBasket")){
                    
                    String productName = request.getParameter("productID");
                    
                    addToBasket(request, response, productName);
                    
                }
                else if (command.equals("delStockLink"))
                {
                    int stockLinkID = Integer.parseInt(request.getParameter("stockLinkID"));
                    delStockLink(request, response, stockLinkID);
                }
                else if (command.equals("createInvLink"))
                {
                    int StoreID = Integer.parseInt(request.getParameter("storeSelect"));
                    int productID = Integer.parseInt(request.getParameter("productSelect"));
                    createStockLink(request, response, StoreID, productID);
                }
                else if (command.equals("delProduct"))
                {
                    int productID = Integer.parseInt(request.getParameter("productID"));
                    deleteProduct(request, response, productID);
                }
                else if (command.equals("delStore"))
                {
                    int storeID = Integer.parseInt(request.getParameter("storeID"));
                    deleteStore(request, response, storeID);
                }
                else if (command.equals("createProduct"))
                {
                    String productName = request.getParameter("productName");
                    try {
                         double productCost = Double.parseDouble(request.getParameter("productCost"));
                         createProduct(request, response, productName, productCost);
                    } catch (Exception e) {
                        request.getRequestDispatcher("admin.jsp").forward(request, response);
                    }
                   
                }
                else if (command.equals("createStore"))
                {
                    String storeName = request.getParameter("storeName");
                    createStore(request, response, storeName);
                }
                else
                {
                    System.out.println("Error");
                }
            }
            catch (ServletException se)
            {
                request.setAttribute("exception", se);
                rd = request.getRequestDispatcher("Reporting/exceptionReporter.jsp");
                rd.forward(request, response);
            }
        
        }
    }
    protected void deleteBasket(HttpServletRequest request, HttpServletResponse response, int num) throws ServletException, IOException{
        User u = new User();
        u = (User)request.getSession().getAttribute("user");
        u.deleteItemInBacket(num);
        request.getSession().setAttribute("user", u);
        request.getRequestDispatcher("standard.jsp").forward(request, response);
    }
    protected void addToBasket(HttpServletRequest request, HttpServletResponse response, String productName) throws ServletException, IOException{
        userHandler uh =  new userHandler();
        Product p = uh.getProduct(request, response, productName);
        User u = new User();
        u = (User)request.getSession().getAttribute("user");
        u.addToBasket(p);
        request.getSession().setAttribute("user", u);
        request.getRequestDispatcher("standard.jsp").forward(request, response);
        
    }
    protected void delStockLink(HttpServletRequest request, HttpServletResponse response, int stockLinkID) throws ServletException, SQLException, IOException
    {
        productHandler ph = new productHandler();
        stockHandler sth = new stockHandler();
        storeHandler sh = new storeHandler();
        
        sth.deleteStockLink(request, response, stockLinkID);
        sth.getAllStockLinks(request, response);
        sh.getAllStores(request, response);
        ph.getAllProducts(request, response);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
        
    }
    protected void createStockLink(HttpServletRequest request, HttpServletResponse response, int storeID, int productID) throws SQLException, ServletException, IOException
    {
        stockHandler sth = new stockHandler();
        storeHandler sh = new storeHandler();
        productHandler ph = new productHandler();
        
        sth.createStockLink(request, response, storeID, productID);
        
        sth.getAllStockLinks(request, response);
        sh.getAllStores(request, response);
        ph.getAllProducts(request, response);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
    protected void deleteStore(HttpServletRequest request, HttpServletResponse response,int storeID) throws SQLException, ServletException, IOException
    {
        productHandler ph = new productHandler();
        stockHandler sth = new stockHandler();
        storeHandler sh = new storeHandler();
        sh.deleteProduct(request, response, storeID);
        sh.getAllStores(request, response);
        ph.getAllProducts(request, response);
        sth.getAllStockLinks(request, response);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
    protected void deleteProduct(HttpServletRequest request, HttpServletResponse response,int productID) throws SQLException, ServletException, IOException
    {
        productHandler ph = new productHandler();
        storeHandler sh = new storeHandler();
        stockHandler sth = new stockHandler();
        ph.deleteProduct(request, response, productID);
        ph.getAllProducts(request, response);
        sh.getAllStores(request, response);
        sth.getAllStockLinks(request, response);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
    protected void createStore(HttpServletRequest request, HttpServletResponse response, String storeName) throws ServletException, IOException, SQLException
    {
        storeHandler sh = new storeHandler();
        productHandler ph = new productHandler();
        stockHandler sth = new stockHandler();
        
        boolean isCreated = sh.createStore(request, response, storeName);
        ph.getAllProducts(request, response);
        sh.getAllStores(request, response);
        sth.getAllStockLinks(request, response);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
        
       
    }
    protected void loginUser(HttpServletRequest request, HttpServletResponse response, String username, String password) throws SQLException, ServletException, IOException
    {
        userHandler uh = new userHandler();
        storeHandler sh = new storeHandler();
        stockHandler sth = new stockHandler();
        boolean userLoggedIn;
        
        
        userLoggedIn = uh.loginUser(request, response, username, password);
        
        User u = new User(); 
        u = (User)request.getSession().getAttribute("user");

        if(userLoggedIn)
        {
            productHandler ph = new productHandler();
            ph.getAllProducts(request, response);
            sh.getAllStores(request, response);
            sth.getAllStockLinks(request, response);
            if(u.getIsAdmin())
            {
                request.getRequestDispatcher("admin.jsp").forward(request, response);
            }
            else
            {
                request.getRequestDispatcher("standard.jsp").forward(request, response);
            }
        }
        else
        {
            request.getSession().setAttribute("Re Enter Username", "true");
            request.getSession().setAttribute("Re Enter Password", "true");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    protected void createProduct(HttpServletRequest request, HttpServletResponse response, String productName, double productCost) throws SQLException, ServletException, IOException
    {
        productHandler ph = new productHandler();
        stockHandler sth = new stockHandler();
        boolean isCreated = ph.createProduct(request, response, productName, productCost);
        sth.getAllStockLinks(request, response);
        System.out.println(isCreated);
        
        ph.getAllProducts(request, response);
        request.getRequestDispatcher("admin.jsp").forward(request, response);
        
        
        
        
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
