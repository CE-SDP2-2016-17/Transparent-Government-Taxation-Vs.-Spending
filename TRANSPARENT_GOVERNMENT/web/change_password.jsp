<%-- 
    Document   : spent_money
    Created on : Feb 24, 2017, 2:35:59 PM
    Author     : VIVEK BHINGRADIYA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <%
        String s = (String) session.getValue("uname");
        if (s == null) {
            response.sendRedirect("sign_in.jsp?status=please login first");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <link href="./css/header.css" rel='stylesheet' type='text/css' />
        <link href="./css/style.css" rel='stylesheet' type='text/css' />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster|Pacifico:400,700,300|Roboto:400,100,100italic,300,300italic,400italic,500italic,500' ' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100,500,600,700,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    </head>
    <script>
        document.getElementById('focusmeplease').focus();
        a:active{
            color:blue;
        }
        var fname = document.getElementById("AMOUNT").value;
        function check() {

            if (fname < <%session.getAttribute("budget"); %>)
            {

            } else
            {
                document.getElementById("status").innerHTML = "You can maximum spend " + <%session.getAttribute("budget");%>;
                //this.parentNode.insertBefore(warningSpan, this.nextSibling);
            }
        }
    </script>
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
        <div class="login" style="padding-left: 70px;">
            <div class="heading">
                <h2 style="font-size: 2em">Change password</h2>
            </div>
            <center> <label id="status" style="font-weight:400;font-size: 20px;color: black;"></label></center>
            <form action="/transparent_government/ChangePasswordServlet" method="post">

                <div class="input-group input-group-lg">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <input type="password" class="form-control" name="cp" placeholder="Current Password">
                </div>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <input type="password" class="form-control" name="np" placeholder="New Password">
                </div>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <input type="password" class="form-control" name="cnp" placeholder="Confirm Password">
                </div>

                <button type="submit" class="float">Apply</button>
            </form>
        </div>
    </body>
</html>