<%-- 
    Document   : logout
    Created on : Apr 6, 2017, 6:30:44 PM
    Author     : BHAVIK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    ]
    <body>
        <% request.getSession().invalidate();
            response.sendRedirect("home.jsp");
        %>
    </body>
</html>
