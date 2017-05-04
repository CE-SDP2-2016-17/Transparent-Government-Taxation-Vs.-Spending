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
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VIVEK BHINGRADIYA
 */
//@WebServlet(urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

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
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"),prop.getProperty("username"),prop.getProperty("password"));
                PreparedStatement ps = con
                        .prepareStatement("insert into transparent_governance.tax_payer values(?,?,?,?,?,?,?,?,?)");
                
                ps.setString(1, id);
                ps.setString(2, n);
                ps.setString(3, f);
                ps.setString(4, l);
                ps.setString(5, e);
                ps.setString(6, c);
                ps.setString(7, p);
                ps.setString(8, m);
                ps.setString(9, s);
                
                
                if(p.equals(rp))
                {
                    i = ps.executeUpdate();
                }
                else
                {
                    response.sendRedirect("tax_payer_registration.jsp?status=both password not matched");
                }
                if (i > 0)
                    response.sendRedirect("tax_payer_registration.jsp?status=You are successfully registered");
            }
           catch (ClassNotFoundException | SQLException e2)
            {
                out.println(e2.toString());
            } 

        }    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}







