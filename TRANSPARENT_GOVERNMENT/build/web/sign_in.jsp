<%-- 
    Document   : sign_in
    Created on : Feb 6, 2017, 3:55:18 PM
    Author     : VIVEK BHINGRADIYA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
    <head>
        <meta charset="UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <title>Sign in : Transparence Governance</title>
        <link rel="stylesheet" href="css/style.css">
        <script type="text/javascript">
            function AlertIt() {
                var answer = confirm("Please select any radio button")
                if (answer)
                    window.location = "localhost:8084/sign_in.jsp";
            }
        </script>

        <script>
            $(document).ready(function () {
                $('input[type=radio][name=chooseone]').change(function () {
                    if (this.value === 'Government_Officer') {

                        $("#bhindi").attr("href", "/transparent_government/gov_officer_registration.jsp");
                    } else if (this.value === 'Tax_Payer') {

                        $("#bhindi").attr("href", "/transparent_government/tax_payer_registration.jsp");
                    }
                });
            });

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
    </head>
    <body onload="check()">
        <div style='float: right; padding-top: 10px; padding-right: 15px; '>
            <a href ="home.jsp" style="color:white;"><font style="font-weight:600;">HOME</font></a>
        </div>
        <div class="titl" style="background-color:black;">
            <em>TRANSPARENT GOVERNANCE : TAXATION VS SPENDING</em>
        </div>
        <div class="login">
            <div class="heading">
                <h2>Sign In</h2>
            </div>
            <center> <label id="status" style="font-weight:400;font-size: 20px;color: black;"></label></center>
            <form action="/transparent_government/SignInServlet" method="post">
                <div   style="border-top:1px solid black;">
                    <div class="text--center2">
                        <input type="radio" name="chooseone" value="Government_Officer">&nbsp;&nbsp;&nbsp;&nbsp;<label for="HTML" class="text--center1" style="color:black;font-weight: 400">Government Officer</label><br/>
                        <input type="radio" name="chooseone" value="Tax_Payer">&nbsp;&nbsp;&nbsp;&nbsp;<label for="HTML" class="text--center1" style="color:black; font-weight: 400">Tax Payer</label><br/>
                    </div>
                </div>
                <div class="heading">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-user"></i></span>


                        <input type="text" class="form-control" name="user" required placeholder="Username or email">
                    </div>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                        <input type="password" class="form-control" required name="pwd" placeholder="Password">
                    </div>

                    <button type="submit" class="float">Login</button>

            </form>
            <p class="text--center">Forgot Password?&nbsp; <a id="abc" style="color:black; font-weight: 400" href="forget_password.jsp">Click Here</a> </p>

            <p class="text--center">Not a member? <a style="color:black; font-weight: 400" id="bhindi" href="javascript:AlertIt();">Sign up now</a> </p>
        </div>
    </div>
<svg xmlns="http://www.w3.org/2000/svg" class="icons"><symbol id="arrow-right" viewBox="0 0 1792 1792"><path d="M1600 960q0 54-37 91l-651 651q-39 37-91 37-51 0-90-37l-75-75q-38-38-38-91t38-91l293-293H245q-52 0-84.5-37.5T128 1024V896q0-53 32.5-90.5T245 768h704L656 474q-38-36-38-90t38-90l75-75q38-38 90-38 53 0 91 38l651 651q37 35 37 90z"/></symbol><symbol id="lock" viewBox="0 0 1792 1792"><path d="M640 768h512V576q0-106-75-181t-181-75-181 75-75 181v192zm832 96v576q0 40-28 68t-68 28H416q-40 0-68-28t-28-68V864q0-40 28-68t68-28h32V576q0-184 132-316t316-132 316 132 132 316v192h32q40 0 68 28t28 68z"/></symbol><symbol id="user" viewBox="0 0 1792 1792"><path d="M1600 1405q0 120-73 189.5t-194 69.5H459q-121 0-194-69.5T192 1405q0-53 3.5-103.5t14-109T236 1084t43-97.5 62-81 85.5-53.5T538 832q9 0 42 21.5t74.5 48 108 48T896 971t133.5-21.5 108-48 74.5-48 42-21.5q61 0 111.5 20t85.5 53.5 62 81 43 97.5 26.5 108.5 14 109 3.5 103.5zm-320-893q0 159-112.5 271.5T896 896 624.5 783.5 512 512t112.5-271.5T896 128t271.5 112.5T1280 512z"/></symbol></svg>

</body>
</html>



