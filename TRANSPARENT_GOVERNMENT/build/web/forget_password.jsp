<%-- 
    Document   : forget_password
    Created on : Mar 2, 2017, 11:09:52 PM
    Author     : VIVEK BHINGRADIYA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>	
        <title>Forgot Password</title>
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
                <h2>Forgot Password</h2>
            </div>
            <center> <label id="status" style="font-weight:400;font-size: 20px;color: black;"></label></center>
            <form action="/transparent_government/ForgetPasswordServlet" method="post">
                <div   style="border-top:1px solid black;">
                    <div class="text--center2">
                        <input type="radio" name="chooseone" value="Government_Officer">&nbsp;&nbsp;&nbsp;&nbsp;<label for="HTML" class="text--center1" style="color:black;font-weight: 400">Government Officer</label><br/>
                        <input type="radio" name="chooseone" value="Tax_Payer">&nbsp;&nbsp;&nbsp;&nbsp;<label for="HTML" class="text--center1" style="color:black; font-weight: 400">Tax Payer</label><br/>
                    </div>
                </div>
                <div class="heading">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>
                        <input type="text" class="form-control" name="usr" placeholder="Username or email" required>
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-mobile"></i></span>
                        <input type="number" class="form-control" name="mno" placeholder="Mobile Number" required>
                    </div>
                    <button type="submit" class="float">APPLY</button>
            </form>
        </div>	
    </div>
</body>
</html>