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

            <div class="left">
                <div class="createProduct">
                    <div class="panel panel-default">
                        <div style="background-color:  #c7c9c8; border-radius: 10px 10px 0px 0px; color: white;" class="panel-heading">Create Product</div>
                        <div style="background-color: #d3d5d4; border-radius: 0px 0px 10px 10px;height: auto;" class="panel-body">

                            <form style="padding-top: 10px;" class="panel panel-default" role="form" action="controller" method="post">

                                <div class="form-group">
                                    <input style="height: 30px;"  type="text" class="form-control" name="productName" placeholder="Enter Product Name">
                                </div>

                                <div class="form-group">
                                    <input style="height: 30px;" type="text" class="form-control" name="productCost" placeholder="Enter Product Price">
                                </div>

                                <input type="hidden" name="command" value="createProduct" />
                                <button style="margin-bottom:  10px;" type="submit" class="btn btn-default">Create</button>

                            </form>

                        </div>
                    </div>
                </div>

                <div class="createStore">
                    <div class="panel panel-default">
                        <div style="background-color:  #c7c9c8; border-radius: 10px 10px 0px 0px; color: white;" class="panel-heading">Create Store</div>
                        <div style="background-color: #d3d5d4; border-radius: 0px 0px 10px 10px;height: auto;" class="panel-body">

                            <form style="padding-top: 10px;" class="panel panel-default" role="form" action="controller" method="post">

                                <div class="form-group">
                                    <input style="height: 30px;" type="text" name="storeName" class="form-control" placeholder="Enter Store Name">
                                </div>
                                <input type="hidden" name="command" value="createStore" />
                                <button style="margin-bottom:  10px;" type="submit" class="btn btn-default">Create</button>

                            </form>

                        </div>
                    </div>
                </div>

                <div class="changeStock">
                    <div class="panel panel-default">
                        <div style="background-color:#c7c9c8;border-radius:10px 10px 0px 0px;color: white;"class="panel-heading">Change Stock</div>
                        <div style="background-color:#d3d5d4;border-radius:0px 0px 10px 10px;height: auto;"class="panel-body">

                            <form style="padding-top: 10px;" class="panel panel-default" role="form" action="controller" method="post">

                                <div class="form-group">
                                    <select class="form-control" id="sel1" name="storeSelect">

                                        <% for (Store s : stores) {%>

                                        <option value="<%= s.getStoreID()%>"><%= s.getStoreName()%></option>

                                        <% } %>

                                    </select>
                                </div>

                                <div class="form-group">
                                    <select class="form-control" id="sel1" name="productSelect">

                                        <%for (Product p : products) {%>

                                        <option value="<%= p.getId()%>"><%= p.getProductName()%></option>

                                        <% } %>

                                    </select>

                                </div>

                                <input type="hidden" name="command" value="createInvLink" />
                                <button style="margin-bottom:  10px;" type="submit" class="btn btn-default">Create</button>

                            </form>

                        </div>
                    </div>
                </div>
            </div>

            <div class="right">

                <div class="containerRight">
                    <div class="panel panel-default">
                        <div style="background-color:  #c7c9c8; border-radius: 10px 10px 0px 0px; color: white;" class="panel-heading">
                            Live Information
                        </div>
                        <div style="background-color: #d3d5d4; border-radius: 0px 0px 10px 10px;height: auto;" class="panel-body">

                            <div class="insideContainer">

                                <div class="viewProducts">
                                    <div class="panel panel-default">
                                        <div style="background-color:#c7c9c8;border-radius:10px 10px 0px 0px;color: white;"class="panel-heading">
                                            Products</div>

                                        <div style="background-color:#dcdddd;border-radius:0px 0px 10px 10px;height: auto;"class="panel-body">

                                            <form class="panel panel-default" role="form" action="controller" method="post">

                                                <table class="table">
                                                    <% for (Product p : products) {%>
                                                    <tr>

                                                        <td>
                                                            <%=p.getProductName()%>  
                                                        </td>


                                                        <td>
                                                            £ <%=p.getCost()%>
                                                        </td>

                                                        <td>
                                                            <form action="controller" method="post">
                                                                <input type="hidden" name="productID" value="<%=p.getId()%>" />
                                                                <input type="hidden" name="command" value="delProduct" />
                                                                <input type="submit" class="btn btn-default" value="Delete">
                                                            </form>
                                                        </td>

                                                    </tr>
                                                    <% } %>

                                                </table>

                                            </form>

                                        </div>

                                    </div>
                                </div>

                                <div class="viewStores">
                                    <div class="panel panel-default">
                                        <div style="background-color:#c7c9c8;border-radius:10px 10px 0px 0px;color: white;" class="panel-heading">
                                            Stores</div>
                                        <div style="background-color:#dcdddd;border-radius:0px 0px 10px 10px;height: auto;" class="panel-body">
                                            <form class="panel panel-default" role="form" action="controller" method="post">

                                                <table class="table">
                                                    <% for (Store s : stores) {%>
                                                    <tr>
                                                        <td>
                                                            <%= s.getStoreName()%>
                                                        </td>

                                                        <td>
                                                            <form action="controller" method="post">
                                                                <input type="hidden" name="storeID" value="<%= s.getStoreID()%>" />
                                                                <input type="hidden" name="command" value="delStore" />
                                                                <input type="submit" class="btn btn-default" value="Delete"/>
                                                            </form>
                                                        </td>

                                                    </tr>
                                                    <% } %>

                                                </table>

                                            </form>


                                        </div>
                                    </div>
                                </div>

                                <div class="viewStock">
                                    <div class="panel panel-default">
                                        <div style="background-color:#c7c9c8;border-radius:10px 10px 0px 0px;color: white;" class="panel-heading">
                                            Stock</div>
                                        <div style="background-color:#dcdddd;border-radius:0px 0px 10px 10px;height: auto;" class="panel-body">
                                            <table class="table">
                                                <% for (StockLink sl : stocks) {%>
                                                <tr>
                                                    <td>
                                                        <%= sl.getStoreName()%>
                                                    </td>
                                                    <td>
                                                        <%= sl.getProductName()%>
                                                    </td>




                                                    <td>
                                                        <form action="controller" method="post">
                                                            <input type="hidden" name="stockLinkID" value="<%= sl.getStockLinkID()%>" />
                                                            <input type="hidden" name="command" value="delStockLink" />
                                                            <input type="submit" class="btn btn-default" value="Delete">
                                                        </form>
                                                    </td>

                                                </tr>
                                                <% }%>

                                            </table>

                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>

            </div>

        </div>

    </body>
</html>