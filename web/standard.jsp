<%@page import="beans.User"%>
<%@page import="beans.StockLink"%>
<%@page import="beans.Store"%>
<%@page import="java.util.ArrayList"%>
<%@page import="beans.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<Product> products = (ArrayList<Product>) request.getSession().getAttribute("allProducts");
    ArrayList<Store> stores = (ArrayList<Store>) request.getSession().getAttribute("allStores");
    ArrayList<StockLink> stocks = (ArrayList<StockLink>) request.getSession().getAttribute("allStockLinks");
    User u = new User();
    u = (User) request.getSession().getAttribute("user");
    ArrayList<Product> basket = u.getBasket();
    int i = 0;
%>
<html>
    <head>
        <title>onlineShopping-admin</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link href='https://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
    </head>
    <body>

        <div class="container">

            <ul>
                <li><a style="color: white;font-size: 150%;"href=>onlineShopping</a></li>
                <ul style="float:right;list-style-type:none;">
                    <li><a href="index.html">Home</a></li>
                    <li><a href="help.html">Help</a></li>
                    <li ><a href="signOut">Sign Out <%=u.getUsername()%></i></a></li>
                </ul>
            </ul>
            </ul>
            <p style="text-align: center;">STANDARD USER</p>
            <div class="left" style="overflow-y: scroll; height: 800px;">
                <% for (StockLink sl : stocks) {%>

                <div class="alert">
                    Product: <%= sl.getProductName()%>
                    <br>
                    Store: <%= sl.getStoreName()%>
                    <br>
                    Price: <%= sl.getProductCost()%>
                    <br>
                    <img src="<%=sl.getProductIMG()%>" style="width:200px;height:200px;">
                    <form action="controller" method="post">                                  
                        <input type="hidden" name="productID" value="<%= sl.getProductName()%>" />
                        <input type="hidden" name="command" value="addToBasket" />
                        <input type="submit" value="Add to Basket" />
                    </form>
                </div>



                <% } %>
            </div>

            <div class="right" style="overflow-y: scroll; height: 800px;">
                BASKET
                Total £ <%=u.totalBasket()%>
                <br>
                <% for (i = 0; i < basket.size(); i++) {%>

                <%= basket.get(i).getProductName()%>

                £:<%=basket.get(i).getCost()%>

                <form action="controller" method="post">   
                    <input type="hidden" name="basketID" value="<%= i%>">
                    <input type="hidden" name="command" value="deleteBasketItem">
                    <input type="submit" value="Delete" />
                </form>
                <hr>
                <%}%>
                
            </div>

            
        </div>

    </body>
</html>