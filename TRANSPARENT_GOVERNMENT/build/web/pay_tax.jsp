<%-- 
    Document   : pay_tax
    Created on : March 05, 2017, 10:12:21 AM
    Author     : BHAVIK BHALODIA
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.InputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            String s = (String)session.getValue("uname");
            if(s==null){
                response.sendRedirect("sign_in.jsp?status=please login first");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pay Tax</title>
        <link href="./css/header.css" rel='stylesheet' type='text/css' />
        <link href="./css/style.css" rel='stylesheet' type='text/css' />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster|Pacifico:400,700,300|Roboto:400,100,100italic,300,300italic,400italic,500italic,500' ' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100,500,600,700,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    </head>
   
    <body>
        <div style='float: right; padding-top: 10px; padding-right: 15px; '>
            <font style="font-weight:600;color:white;">Welcome <%=session.getAttribute("uname")%></font>
        </div>
        <div class="titl" style="background-color:black;">
            <em>TRANSPARENT GOVERNANCE : TAXATION VS SPENDING</em>
        </div>
        <div class="header-cont">
            <div class="header">
                <ul  class="side-nav" style="list-style: none;">
                   
                        <% if (session.getAttribute("type") != "payer") {
                                out.print("<li>");
                                out.print("<a href=\"gov_home.jsp\" id=\"focusmeplease\">HOME</a>");
                                out.print("</li>");
                                out.print("<li>");
                                out.print("<a href=\"spent_money.jsp\" id=\"focusmeplease\">SPENT MONEY</a>");
                                out.print("</li>");
                            }
                    else{
                                    out.print("<li>");
                                out.print("<a href=\"payer_home.jsp\" id=\"focusmeplease\">HOME</a>");
                                out.print("</li>");
                    
                    }
                        %>
                    <li>
                        <a href="change_password.jsp">CHANGE PASSWORD</a>
                    </li>
                    <li>
                        <a href="pay_tax.jsp">Pay Tax</a>
                    </li>
                    <li>
                        <a href="histroy.jsp" >HISTORY</a>
                    </li>
                    <li>
                        <a href="logout.jsp">LOG OUT</a>
                    </li>
                </ul>
            </div>
        </div>
        <div style="padding-top:40px"></div>
        <div class="login" style="padding-left: 70px;">
            <div class="heading">
                <h2>Pay Tax</h2>
            </div>
            <center> <label id="status" style="font-weight:400;font-size: 20px;color: black;">Your left tax is  &#x20b9; <%=session.getAttribute("tax_left")%></label></center>
            <form action="/transparent_government/PayTaxServlet" method="post">
                <div class="heading">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-user"></i></span>
                        <select id="dropdown" required name="ttype" style="background-color: gray; border-color: gray; color:black;" class="form-control" placeholder="tax type">
                            <option value="" style="color:black;" disabled selected>Select TAX TYPE</option>
                            <option value="individual">individual</option>
                            <option value="business">business</option>
                        </select>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-money"></i></span>
                        <input type="number" required class="form-control" name="amt" placeholder="Amount" >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-user"></i></span>
                        <select id="dropdown" name="city" required style="background-color: gray; border-color: gray; color:black;" class="form-control" placeholder="department">

                            <option value="" disabled selected>Select your City</option>
                            <%

                                Properties prop = new Properties();
                                InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
                                //FileReader reader = new FileReader("tgb_information.properties");
                                prop.load(reader);
                                Class.forName("oracle.jdbc.OracleDriver");
                                Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                                Statement st1 = con.createStatement();
                                ResultSet rs1 = st1.executeQuery("select * from transparent_governance.city_name");
                                while (rs1.next()) {
                                    out.print("<option value=\"" + rs1.getString("city_name") + "\">" + rs1.getString("city_name") + "</option>");
                                }
                            %>
                        </select>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                        <input type="text" class="form-control" required name="date" placeholder="DD/MM/YYYY">
                    </div>
                    <button type="submit" class="float">Pay</button>

                </div>
            </form>
        </div>
    </body>
</html>
