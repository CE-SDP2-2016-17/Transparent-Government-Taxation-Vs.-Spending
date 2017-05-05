/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;

/**
 *
 * @author VIVEK BHINGRADIYA
 */
public class ChangePasswordServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        Properties prop = new Properties();
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            String c = (String) session.getAttribute("type");
            String n = (String) session.getAttribute("uname");
            String s1 = request.getParameter("cp");
            String s2 = request.getParameter("np");
            String s3 = request.getParameter("cnp");
            out.println(c);
            out.print(s2 + s3);
            /* TODO output your page here. You may use following sample code. */
            if (c == "gov") {
                if (s2.trim() == s3.trim()) {

                    InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
                    //FileReader reader = new FileReader("tgb_information.properties");
                    prop.load(reader);

                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection conn = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));

                    PreparedStatement ps = conn
                            .prepareStatement("update transparent_governance.Government_Officer set password=? where username=? and password=?");
                    ps.setString(1, s2);
                    ps.setString(2, n);
                    ps.setString(3, s1);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        response.sendRedirect("change_password.jsp?status=successfully changed");
                    } else {
                        response.sendRedirect("change_password.jsp?status=error occured");
                    }
                } else {
                    response.sendRedirect("change_password.jsp?status=confirm password does not match");
                }
            } else if ("payer".equals(c)) {
                if (s2.equals(s3)) {
                    InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
                    //FileReader reader = new FileReader("tgb_information.properties");
                    prop.load(reader);

                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection conn = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                    PreparedStatement ps = conn
                            .prepareStatement("update transparent_governance.Tax_Payer set password=? where username=? and password=?");
                    ps.setString(1, s2);
                    ps.setString(2, n);
                    ps.setString(3, s1);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        response.sendRedirect("change_password.jsp?status=successfully changed");
                    } else {
                        response.sendRedirect("change_password.jsp?status=error occured");
                    }
                } else {
                    response.sendRedirect("change_password.jsp?status=confirm password does not match");
                }

            } else {
                response.sendRedirect("change_password.jsp?status=type does not match");
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
