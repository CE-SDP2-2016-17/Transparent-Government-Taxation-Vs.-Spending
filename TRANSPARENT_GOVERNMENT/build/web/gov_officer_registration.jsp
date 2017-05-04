<%-- 
    Document   : registration
    Created on : Feb 5, 2017, 2:32:46 PM
    Author     : VIVEK BHINGRADIYA
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>	
        <title>GOV-OFFICER REGISTRATION</title>
        <%--<link href="css/registration.css" rel='stylesheet' type='text/css' />--%>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <!--webfonts-->
        <link href='http://fonts.googleapis.com/css?family=Lobster|Pacifico:400,700,300|Roboto:400,100,100italic,300,300italic,400italic,500italic,500' ' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100,500,600,700,300' rel='stylesheet' type='text/css'>
        <!--webfonts-->
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
            <a href ="home.jsp" style="color:white;"><font style="font-weight:600;">HOME</font></a>
        </div>
        <div class="titl" style="background-color:black;">
            <em>TRANSPARENT GOVERNANCE : TAXATION VS SPENDING</em>
        </div>

        <div class="login">
            <div class="heading">
                <h2>Registration form</h2>
            </div>
            <center> <label id="status" style="font-weight:400;font-size: 20px;color: black;"></label></center>

            <form action="/transparent_government/GovRegisterServlet" method="post">
                <div class="heading">
                    <div class="input-group input-group-lg"> 
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-id-badge"></i></span>
                        <input type="number" class="form-control" name="id" placeholder="Id"  >
                    </div>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-user"></i></span>
                        <input type="text" class="form-control" name="fnm" placeholder="First Name">
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-user"></i></span>
                        <input type="text" name="lnm" class="form-control" placeholder="Last Name"  >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-envelope-o"></i></span>
                        <input type="text" class="form-control" name="eid" placeholder="Email Address"  >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-user"></i></span>
                        <select id="dropdown" name="dep" style="background-color: gray; border-color: gray; color:black;" class="form-control" placeholder="department">
                            
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
                                    out.print("<option value=\""+rs1.getString("city_name")+"\">"+rs1.getString("city_name")+"</option>");}
                            %>
                        </select>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-mobile"></i></span>
                        <input type="number" name="mno" class="form-control" placeholder="Mobile Number" >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-money"></i></span>
                        <input type="number" class="form-control" name="salary" placeholder="Salary" >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-caret-square-o-down"></i></span>
                        <select id="dropdown" name="dep" style="background-color: gray; border-color: gray;  color:black; " class="form-control" placeholder="department">
                            <option style="color: black;" value="" disabled selected>Select your department</option>
                            <%

                                rs1 = st1.executeQuery("select * from transparent_governance.spend");
                                while (rs1.next()) {
                                    out.print("<option value=\""+rs1.getString("department")+"\">"+rs1.getString("department")+"</option>");}
                            %>
                        </select>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-user"></i></span>
                        <input type="text" name="unm" class="form-control" placeholder="User Name"  >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-lock"></i></span>
                        <input type="password" name="pwd" style="color:black; " placeholder="Password"  class="form-control" >
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i style="color:black;" class="fa fa-lock"></i></span>
                        <input type="password" name="repwd" placeholder="Confirm Password" class="form-control"  >
                    </div>

                    <button type="submit" class="float">Registration</button>
                </div>


            </form>
        </div>
    </body>
</html>