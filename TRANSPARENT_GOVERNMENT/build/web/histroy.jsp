<%-- 
    Document   : history
    Created on : Apr 6, 2017, 9:58:42 PM
    Author     : BHAVIK
--%>

<%@page import="java.sql.PreparedStatement"%>
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
            String s = (String) session.getValue("uname");
            if (s == null) {
                response.sendRedirect("sign_in.jsp?status=please login first");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HISTORY</title>
        <link href="./css/header.css" rel='stylesheet' type='text/css' />
        <link href="./css/style.css" rel='stylesheet' type='text/css' />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster|Pacifico:400,700,300|Roboto:400,100,100italic,300,300italic,400italic,500italic,500' ' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100,500,600,700,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    </head>
    <style>
        th{
            border-width: 3px ;
            background-color: #4CAF50;
            border-color: black;
            color: white;
            border-style: solid;
            height: 60px;
            font-family: "times new roman";
            text-align: center;
            width: 150px;
        }
        td{
            border-width: 3px ;
            border-color: black;
            border-style: solid;
            height: 40px;
            color: black;
            text-align: center;
            width: 150px;

        }
    </style>
    
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

                    <%
                        if (session.getAttribute("type") != "payer") {
                            out.print("<li>");
                            out.print("<a href=\"gov_home.jsp\" id=\"focusmeplease\">HOME</a>");
                            out.print("</li>");
                            out.print("<li>");
                            out.print("<a href=\"spent_money.jsp\" id=\"focusmeplease\">SPENT MONEY</a>");
                            out.print("</li>");
                        } else {
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
        <div class="login">
            <%
                    
                    out.print("<center><h2 style=\"padding-left:15px;color:#ffe6e6;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PAID TAX DETAILS</h2>");

                    Properties prop = new Properties();
                    InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
                    //FileReader reader = new FileReader("tgb_information.properties");
                    prop.load(reader);
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                    String selectSQL = "select * from transparent_governance.tax_details where tax_payer_username= ?";
                    PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
                    preparedStatement.setString(1, (String) session.getAttribute("uname"));
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs.next()) {
                        out.print("<table style=\"width:700px;\"><tr><th>USERNAME</th><th>TAX_TYPE</th><th>CITY</th><th>AMOUNT</th></tr>");
                        do {
                            out.print("<tr>");
                            out.print("<td>");
                            out.print(rs.getString("tax_payer_username"));
                            out.print("</td>");
                            out.print("<td>");
                            out.print(rs.getString("tax_type"));
                            out.print("</td>");
                            out.print("<td>");
                            out.print(rs.getString("CITY"));
                            out.print("</td>");
                            out.print("<td>");
                            out.print(rs.getInt("AMOUNT"));
                            out.print("</td>");
                            out.print("</tr>");

                        } while (rs.next());
                        out.print("</table></center>");
                    } else {
                        out.print("<center><h3>YOU HAVE NOT PAID ANY TAX TILL</h3></center>");
                    }
                    out.print("<div style=\"padding:40px;\"></div>");
                    if (session.getAttribute("type") != "payer") {
                        out.print("<center><h2  style=\"padding-left:15px;color:#ffe6e6;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SPENDING DETAILS</h2>");
                        selectSQL = "select * from transparent_governance.spending_details where officer_username= ?";
                        preparedStatement = con.prepareStatement(selectSQL);
                        preparedStatement.setString(1, (String) session.getAttribute("uname"));
                        rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            out.print("<center><table style=\"width:700px;\"><tr><th>USERNAME</th><th>DEPARTMENT</th><th>CITY</th><th>DESCRIPTION</th><th>AMOUNT</th></tr>");
                            do {
                                out.print("<tr>");
                                out.print("<td>");
                                out.print(rs.getString("officer_username"));
                                out.print("</td>");
                                out.print("<td>");
                                out.print(rs.getString("department"));
                                out.print("</td>");
                                out.print("<td style=\"width=170px;\">");
                                out.print(rs.getString("CITY"));
                                out.print("</td>");
                                out.print("<td>");
                                out.print(rs.getString("description"));
                                out.print("</td>");
                                out.print("<td>");
                                out.print(rs.getInt("AMOUNT"));
                                out.print("</td>");
                                out.print("</tr>");

                            } while (rs.next());
                            out.print("</table></center>");
                        } else {
                            out.print("<center><h3>YOU HAVE NOT SPEND ANY TAX TILL</h3></center>");
                        }

                    }


                %>
        </div>
    </body>
</html>
