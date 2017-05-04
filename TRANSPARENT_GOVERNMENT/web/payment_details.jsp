<%-- 
    Document   : payment_details
    Created on : Feb 24, 2017, 10:58:52 AM
    Author     : VIVEK BHINGRADIYA
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PAYMENT DONE</title>
        <link href="./css/header.css" rel='stylesheet' type='text/css' />
        <link href="./css/style.css" rel='stylesheet' type='text/css' />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster|Pacifico:400,700,300|Roboto:400,100,100italic,300,300italic,400italic,500italic,500' ' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100,500,600,700,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    </head>
    <script>
        function getParameterByName(name, url) {
            if (!url) {
                url = window.location.href;
            }
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                    results = regex.exec(url);
            if (!results)
                return null;
            if (!results[2])
                return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }
        function check() {
            var foo = getParameterByName('status');
            document.getElementById("status").innerHTML = foo;
        }
    </script>
    <body onload="check()">
        <div style='float: right; padding-top: 10px; padding-right: 15px; '>
            <font style="font-weight:600;color:white;">Welcome <%session.getAttribute("uname");%></font>
        </div>
        <div class="titl" style="background-color:black;">
            <em>TRANSPARENT GOVERNANCE : TAXATION VS SPENDING</em>
        </div>
        <div class="header-cont">
            <div class="header">
                <ul  class="side-nav" style="list-style: none;">
                    <li><a href="welcome.jsp">HOME</a></li>
                    <li>
                        <a href="spent_money.jsp" id="focusmeplease">SPENT MONEY</a>
                    </li>
                    <li>
                        <a href="change_password.jsp">CHANGE PASSWORD</a>
                    </li>
                    <% if (session.getAttribute("type") == "gov") {
                            out.print("<li>");
                            out.print("<a href=\"spent_money.jsp\" id=\"focusmeplease\">SPENT MONEY</a>");
                            out.print("</li>");
                        }
                    %>
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
            <form action="register" method="post">
                <div class="heading">
                    <h2>Payment Details</h2>
                </div>
                <div   style="border-top:1px solid black;">
                    <div class="text--center2">
                        <input type="radio" name="NET BANKING" value="1">&nbsp;&nbsp;&nbsp;&nbsp;<label for="HTML" class="text--center1" style="color:black;font-weight: 400">NET BANKING</label><br/>
                        <input type="radio" name="NET BANKING" value="2">&nbsp;&nbsp;&nbsp;&nbsp;<label for="HTML" class="text--center1" style="color:black; font-weight: 400">BY DEBIT/CREDIT CARD</label><br/>
                    </div>
                </div>


                <div class="heading">
                    <div class="input-group input-group-lg"> 
                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                        <input type="text" class="form-control" name="cnm" placeholder="NAME ON CARD"  >
                    </div>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-credit-card"></i></span>
                        <input type="number" class="form-control" name="fnm" name="number" placeholder="card number">
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="Date" name="date" class="form-control" placeholder="expiry date"  >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-envelope-o"></i></span>
                        <input type="text" class="form-control" name="cvv" placeholder="CVV"  >
                    </div>
                    <button type="submit" class="float">PAY</button>
                </div>
            </form>
        </div>
    </body>
</html>
