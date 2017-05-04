/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class ForgetPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Properties prop = new Properties();
        InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
        //FileReader reader = new FileReader("tgb_information.properties");
        prop.load(reader);

        try (PrintWriter out = response.getWriter()) {

            String s = request.getParameter("chooseone");
            out.println(s);

            if (s.equals("Government_Officer")) {

                //   out.println(s1+" "+s2);
                try {
                    String s1 = request.getParameter("unm");
                    String s2 = request.getParameter("mno");
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from transparent_governance.government_officer");
                    while (rs.next()) {
                        String u = rs.getString("username");
                        String e = rs.getString("email_id");
                        String m = rs.getString("mobile_no");
                        //    String p=rs.getString("password");
                        //     out.println(u+" "+e+" "+p);
                        if (s1.matches(u.trim()) && s2.matches(m.trim())) {

                            HttpSession session = request.getSession();
                            session.setAttribute("uname", u);
                            session.setAttribute("choose", s);
                            session.setAttribute("mno", m);
                            //out.println("your password is :: bhindi" );
                            //  out.println("hello");
                            response.sendRedirect("/SmsServlet");

                        } else {
                            response.sendRedirect("forget_password.jsp?status=invalid username or password");
                        }
                    }

                } catch (ClassNotFoundException | SQLException e2) {
                    out.println(e2.toString());
                }

            }
            if (s.equals("Tax_Payer")) {

                //   out.println(s1+" "+s2);
                try {
                    String s1 = request.getParameter("unm");
                    String s2 = request.getParameter("mno");

                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from transparent_governance.tax_payer");

                    while (rs.next()) {
                        String u = rs.getString("username");
                        String e = rs.getString("email_id");
                        String m = rs.getString("mobile_no");
                        //    String p=rs.getString("password");
                        //     out.println(u+" "+e+" "+p);
                        if (s1.matches(u.trim()) && s2.matches(m.trim())) {

                            HttpSession session = request.getSession();
                            session.setAttribute("uname", u);
                            session.setAttribute("choose", s);
                            session.setAttribute("mno", m);
                            out.println(m);
                            response.sendRedirect("/SmsServlet");

                        } else {
                            response.sendRedirect("forget_password.jsp?status=invalid username or password");
                        }
                    }

                } catch (ClassNotFoundException | SQLException e2) {
                    out.println(e2.toString());
                }

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
