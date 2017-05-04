<%-- 
    Document   : spent_money
    Created on : Feb 24, 2017, 2:35:59 PM
    Author     : BHAVIK BHALODIA
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME</title>
        <link href="./css/style.css" rel='stylesheet' type='text/css' />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
        <link href='http://fonts.googleapis.com/css?family=Lobster|Pacifico:400,700,300|Roboto:400,100,100italic,300,300italic,400italic,500italic,500' ' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100,500,600,700,300' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>


        <script type="text/javascript">
            var city;
            var state;
            var url_part;
            function getParameterByName(name, url) {
                if (!url) {
                    url = window.location.href;
                }
                name = name.replace(/[\[\]]/g, "\\$&");
                var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                        results = regex.exec(url);
                if (!results)
                    return "nathi";
                if (!results[2])
                    return "nathi";
                return decodeURIComponent(results[2].replace(/\+/g, " "));
            }
            function check() {
                state = getParameterByName('state');
                city = getParameterByName('city');
                url_part = "?state=" + state + "&city=" + city;

            }


            function check1(newvalue)
            {
                if (newvalue === "nathi") {
                    document.getElementById("dropdown").disabled = true;
                } else
                {
                    document.getElementById("dropdown").disabled = false;
                }
            }

            // Load the Visualization API and the piechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart);

            function pieChart() {
                check();
                var url = "/transparent_government/graphServlet" + url_part;
                var jsonData = $.ajax({
                    url: url,
                    dataType: "json",
                    async: false
                }).responseText;
                if (jsonData !== "") {
                    // Create our data table out of JSON data loaded from server.
                    var data = new google.visualization.DataTable(jsonData);

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
                    chart.draw(data, {width: 600, title: "SPEND MONEY", chartArea: {width: "600px", height: "600px"}, legend: "right", height: 500});
                }
            }


            // Load the Visualization API and the piechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart1);

            function pieChart1() {
                check();
                var url = "/transparent_government/taxgraphServlet" + url_part;
                var jsonData = $.ajax({
                    url: url,
                    dataType: "json",
                    async: false
                }).responseText;
                if (jsonData !== "") {
                    // Create our data table out of JSON data loaded from server.
                    var data1 = new google.visualization.DataTable(jsonData);

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.ColumnChart(document.getElementById('tax_div'));
//                    var chart = new google.visualization.PieChart(document.getElementById('tax_div'));
                    chart.draw(data1, {width: 600, title: "TAXATION", chartArea: {width: "550px", height: "550px"}, legend: "right", height: 500});
                }
            }

            // Load the Visualization API and the piechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart3);

            function pieChart3() {
                check();
                var jsonData = $.ajax({
                    url: "/transparent_government/barservlet" + url_part,
                    dataType: "json",
                    async: false
                }).responseText;

                // Create our data table out of JSON data loaded from server.
                var data2 = new google.visualization.DataTable(jsonData);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById('bar_div'));
                chart.draw(data2, {width: 600, title: "Comparision", chartArea: {width: "550px", height: "550px"}, height: 500});
            }

            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart4);

            function pieChart4() {
                check();
                var jsonData = $.ajax({
                    url: "/transparent_government/citytaxation" + url_part,
                    dataType: "json",
                    async: false
                }).responseText;
                if (jsonData !== "") {
                    // Create our data table out of JSON data loaded from server.
                    var data2 = new google.visualization.DataTable(jsonData);

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.ColumnChart(document.getElementById('citytax'));
                    chart.draw(data2, {width: 600, title: "Tax Typewise Taxation", chartArea: {width: "550px", height: "550px"}, height: 500});
                }
            }
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart5);

            function pieChart5() {
                check();
                var jsonData = $.ajax({
                    url: "/transparent_government/cityspend" + url_part,
                    dataType: "json",
                    async: false
                }).responseText;
                if (jsonData !== "")
                {                // Create our data table out of JSON data loaded from server.
                    var data2 = new google.visualization.DataTable(jsonData);

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.ColumnChart(document.getElementById('cityspend'));
                    chart.draw(data2, {width: 600, title: "Departmentwise spending", chartArea: {width: "550px", height: "550px"}, height: 500});
                }
            }

        </script>
        <style>
            body{
                background-color: grey;
            }
        </style>
    </head>
    <body style="overflow-wrap: break-word;">
        <div style='float: right; padding-top: 10px; padding-right: 30px; overflow-y: hidden;' >
            <a href ="sign_in.jsp" style="color:white;"><font style="font-weight:600;">SIGN IN / REGISTER</font></a>
        </div>
        <div class="titl" style="background-color:black; ">
            <em>TRANSPARENT GOVERNANCE : TAXATION VS SPENDING</em>
        </div>
        <form method="get" action="#" >

            <select id="dropdown1" name="state" onchange="check1(this.value)" class="form-control" style="width:200px;background-color: white; border-color: gray; color:black;"  placeholder="State">
                <option value="" disabled selected>SELECT STATE</option>
                <option value="nathi">All</option>
                <%
                    Properties prop1 = new Properties();
                    InputStream reader1 = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
                    prop1.load(reader1);
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection con = DriverManager.getConnection(prop1.getProperty("connectionstring"), prop1.getProperty("username"), prop1.getProperty("password"));
                    Statement st1 = con.createStatement();
                    ResultSet rs1 = st1.executeQuery("select distinct state_name from transparent_governance.city_name");
                    while (rs1.next()) {
                        out.print("<option value=\"" + rs1.getString("state_name") + "\">" + rs1.getString("state_name") + "</option>");
                    }
                %>
            </select>
            <select id="dropdown" name="city" class="form-control" style="width:200px;background-color: white; border-color: gray; color:black;"placeholder="City">
                <option value="" disabled selected>SELECT CITY</option>
                <option value="nathi">All</option>
                <%
                    st1 = con.createStatement();
                    rs1 = st1.executeQuery("select * from transparent_governance.city_name");
                    while (rs1.next()) {
                        out.print("<option value=\"" + rs1.getString("city_name") + "\">" + rs1.getString("city_name") + "</option>");
                    }
                %>
            </select>
            <button type="submit" class="float" style="width: 200px;height: 30px;">UPDATE</button>
        </form>
        <center>
            
            <div style="pandding-top:40px"></div>
            <div id="bar_div" ></div>
            
            <div style="padding-top:40px"></div>
            <div id="cityspend" ></div>
            <div style="padding-top:40px"></div>
            <div id="citytax" ></div>
            <div style="padding-top:40px"></div>
            <div id="chart_div" ></div>
            <div style="padding-top:40px"></div>
            <div id="tax_div" ></div>            
        </center>
</body>
</html>