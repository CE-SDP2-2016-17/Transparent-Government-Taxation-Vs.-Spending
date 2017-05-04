<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
        <script type="text/javascript">

            // Load the Visualization API and the piechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart);

            function pieChart() {
                var jsonData = $.ajax({
                    url: "/graphServlet",
                    dataType: "json",
                    async: false
                }).responseText;

                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsonData);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                chart.draw(data, {width: 600, height: 600});
            }

        </script>
        <script type="text/javascript">

            // Load the Visualization API and the piechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart);

            function pieChart() {
                var jsonData = $.ajax({
                    url: "/taxgraphServlet?value=state",
                    dataType: "json",
                    async: false
                }).responseText;

                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsonData);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('tax_div'));
                chart.draw(data, {
                    legend: {position: "right"}, // turn off the legend
                    curveType: "function",
                    width: 600, height: 600,
                    title: "TAX SPEND",
                    titleTextStyle: {
                        color: '#a34f8b'
                    }

                });
            }

        </script>
        <script type="text/javascript">

            // Load the Visualization API and the piechart package.
            google.charts.load('current', {'packages': ['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(pieChart);

            function pieChart() {
                var jsonData = $.ajax({
                    url: "/barservlet?value=state",
                    dataType: "json",
                    async: false
                }).responseText;

                // Create our data table out of JSON data loaded from server.
                var data = new google.visualization.DataTable(jsonData);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById('bar_div'));
                chart.draw(data, {width: 500, height: 240});
            }
        </script>
    </head>
    <style>
        .navbar-fixed-left {
            width: 140px;
            position: fixed;
            border-radius: 0;
            height: 100%;
        }

        .navbar-fixed-left .navbar-nav > li {
            float: none;  /* Cancel default li float: left */
            width: 139px;
        }

        .navbar-fixed-left + .container {
            padding-left: 100px;

        }

        /* On using dropdown menu (To right shift popuped) */
        .navbar-fixed-left .navbar-nav > li > .dropdown-menu {
            margin-top: -50px;
            margin-left: 140px;
        }
    </style>
    <style>
        body{
            background-color: grey;
        }
    </style>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-left">
            <a class="navbar-brand" href="#">Brand</a>
            <ul class="nav navbar-nav">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Sub Menu1</a></li>
                        <li><a href="#">Sub Menu2</a></li>
                        <li><a href="#">Sub Menu3</a></li>
                        <li class="divider"></li>
                        <li><a href="#">Sub Menu4</a></li>
                        <li><a href="#">Sub Menu5</a></li>
                    </ul>
                </li>
                <li><input type="date"/></li>
                <li><a href="#">Link3</a></li>
                <li><a href="#">Link4</a></li>
                <li><a href="#">Link5</a></li>
            </ul>
        </div>
        <div class="container" >
            <div class="row">
                <h3>TRANSPARENT GOVERNANCE : TAXATION AND SPENDING</h3>


            </div>
        </div>
        <div style="padding-top:5% "></div>
        <div align="center" style="padding-left:16%" id="bar_div"></div>
        <div align="center" style="padding-left:16%" id="chart_div"></div>
        <div align="center" style="padding-left:16%" id="tax_div"></div>
    </body>
</html>