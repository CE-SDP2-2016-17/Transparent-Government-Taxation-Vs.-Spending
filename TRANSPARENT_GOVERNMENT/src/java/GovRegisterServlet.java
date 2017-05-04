/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VIVEK BHINGRADIYA
 */
public class GovRegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Properties prop = new Properties();
        InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
        //FileReader reader = new FileReader("tgb_information.properties");
        prop.load(reader);
        
         int i=0;
        try (PrintWriter out = response.getWriter()) {
            String n = request.getParameter("unm");
            String p = request.getParameter("pwd");
            String rp = request.getParameter("repwd");
            String e = request.getParameter("eid");
            String l = request.getParameter("lnm");
            String f = request.getParameter("fnm");
            String c = request.getParameter("city");
            String id=request.getParameter("id");
            String s=request.getParameter("salary");
            String m=request.getParameter("mno");
            String d;
            d = request.getParameter("dep");
           //out.println(id+" "+n+" "+f+" "+" "+l+" "+e+" "+c+" "+p+" "+m+" "+s+" "+d);
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"),prop.getProperty("username"),prop.getProperty("password"));
                        
                PreparedStatement ps;
                ps = con
                        .prepareStatement("insert into transparent_governance.government_officer values(?,?,?,?,?,?,?,?,?,?)");
                
                ps.setString(1, id);
                ps.setString(2, n);
                ps.setString(3, f);
                ps.setString(4, l);
                ps.setString(5, e);
                ps.setString(9, c);
                ps.setString(7, p);
                ps.setString(6, m);
                ps.setString(8, s);
               ps.setString(10, d);
             ///out.println(id+" "+n+" "+f+" "+" "+l+" "+e+" "+c+" "+p+" "+m+" "+s+" "+d);
                
                if(p.equals(rp))
                {
                    i = ps.executeUpdate();
                }
                else
                {
                    response.sendRedirect("gov_officer_registration.jsp?status=both password not matched");
                }
                if (i > 0)
                    response.sendRedirect("gov_officer_registration.jsp?status=You are successfully registered");
            }
           catch (ClassNotFoundException | SQLException e2)
            {
                out.println(e2.toString());
            }
 
        }    
        
    }

 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
     @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}