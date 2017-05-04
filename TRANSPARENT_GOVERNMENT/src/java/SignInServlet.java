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
//import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import 

/**
 *
 * @author VIVEK BHINGRADIYA
 */
public class SignInServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        Properties prop = new Properties();
        InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
        //FileReader reader = new FileReader("tgb_information.properties");
        prop.load(reader);
        try (PrintWriter out = response.getWriter()) {
            String s = request.getParameter("chooseone");
            HttpSession session = request.getSession();
            session.setAttribute("choose", s);
            String s1 = request.getParameter("user");
            String s2 = request.getParameter("pwd");
            if (s == null) {
                response.sendRedirect("sign_in.jsp?status=choose any one radio button");
            } else if (s1.matches("") || s2.matches("")) {
                response.sendRedirect("sign_in.jsp?status=username or password must not null");
            } else {
                s = request.getParameter("chooseone");

                if (s.equals("Government_Officer")) {
                    try {
                        out.print("a");
                        Class.forName("oracle.jdbc.OracleDriver");
                        Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                        String selectSQL = "select * from transparent_governance.government_officer where username = ?";
                        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
                        preparedStatement.setString(1, s1);
                        ResultSet rs = preparedStatement.executeQuery();
                        out.print("b");
                        if (rs.next()) {
                            if (rs.getString("password").trim().equals(s2)) {
                                String department = rs.getString("department");
                                int salary = rs.getInt("salary");
                                selectSQL = "select * from transparent_governance.spend where department = ?";
                                preparedStatement = con.prepareStatement(selectSQL);
                                preparedStatement.setString(1, department);
                                rs = preparedStatement.executeQuery();
                                if (rs.next()) {
                                    int maxspend = rs.getInt("maxspend");
                                    session.setAttribute("uname", s1);
                                    session.setAttribute("department", department);
                                    session.setAttribute("type", "gov");
                                    session.setAttribute("budget", maxspend);
                                    int tax = 0;
                                    Statement st1 = con.createStatement();
                                    ResultSet rs1 = st1.executeQuery("select * from transparent_governance.tax");
                                    while (rs1.next()) {
                                        out.print("asd");
                                        int ss = rs1.getInt("startsalary");
                                        int es = rs1.getInt("endsalary");
                                        if (salary > ss) {

                                            if (es < salary) {
                                                out.print("w");
                                                int v = 0;
                                                int tp = rs1.getInt("taxper");
                                                v = ((int) ((es - ss) * tp / 100));
                                                tax = tax + v;
                                            } else {
                                                out.print("q");
                                                int v = 0;
                                                int tp = rs1.getInt("taxper");
                                                v = ((int) ((salary - ss) * tp / 100));
                                                tax = tax + v;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                    out.print(tax);
                                    selectSQL = "select sum(amount) as amount from transparent_governance.tax_details where tax_payer_username = ?";
                                    preparedStatement = con.prepareStatement(selectSQL);
                                    preparedStatement.setString(1, s1);
                                    rs = preparedStatement.executeQuery();

                                    if (rs.next()) {
                                        out.print(tax - rs.getInt("amount"));
                                        session.setAttribute("tax_paid", rs.getInt("amount"));
                                        session.setAttribute("tax_left", tax - rs.getInt("amount"));
                                        //out.print(session.getAttribute("tax_paid"));
                                        //out.print(session.getAttribute("tax_left"));
                                    } else {
                                        session.setAttribute("tax_paid", 0);
                                        session.setAttribute("tax_left", tax);
                                    }

                                    session.setAttribute("total_tax1", tax);

                                    response.sendRedirect("gov_home.jsp");
                                } else {
                                    response.sendRedirect("gov_home.jsp?status=your departement not have money to spend");
                                }
                            } else {
                                response.sendRedirect("sign_in.jsp?status=password incorrect");
                            }
                        } else {
                            response.sendRedirect("sign_in.jsp?status=username incorrect");
                        }
                    } catch (ClassNotFoundException | SQLException e2) {
                        out.println(e2.toString());
                    }

                } else if (s.equals("Tax_Payer")) {

                    try {
                        Class.forName("oracle.jdbc.OracleDriver");
                        Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                        String selectSQL = "select * from transparent_governance.tax_payer where username = ?";
                        PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
                        preparedStatement.setString(1, s1);
                        ResultSet rs = preparedStatement.executeQuery();
                        if (rs.next()) {
                            if (rs.getString("password").equals(s2)) {
                                int salary = rs.getInt("salary");
                                session.setAttribute("username", s1);
                                session.setAttribute("type", "payer");
                                int tax = 0;
                                Statement st1 = con.createStatement();
                                ResultSet rs1 = st1.executeQuery("select * from transparent_governance.tax");
                                while (rs1.next()) {
                                    out.print("asd");
                                    int ss = rs1.getInt("startsalary");
                                    int es = rs1.getInt("endsalary");
                                    if (salary > ss) {

                                        if (es < salary) {

                                            int v = 0;
                                            int tp = rs1.getInt("taxper");
                                            v = ((int) ((es - ss) * tp / 100));
                                            tax = tax + v;
                                        } else {
                                            int v = 0;
                                            int tp = rs1.getInt("taxper");
                                            v = ((int) ((salary - ss) * tp / 100));
                                            tax = tax + v;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                session.setAttribute("total_tax1", tax);
                                selectSQL = "select sum(amount) as amount from transparent_governance.tax_details where tax_payer_username = ?";
                                preparedStatement = con.prepareStatement(selectSQL);
                                preparedStatement.setString(1, s1);
                                rs = preparedStatement.executeQuery();
                                if (rs.next()) {
                                    session.setAttribute("tax_paid", rs.getInt("amount"));
                                    session.setAttribute("tax_left", tax - rs.getInt("amount"));
                                    session.setAttribute("uname", s1);
                                    response.sendRedirect("payer_home.jsp");
                                } else {
                                    session.setAttribute("tax_left", tax);
                                    session.setAttribute("uname", s1);
                                    response.sendRedirect("payer_home.jsp");
                                }
                            } else {
                                response.sendRedirect("sign_in.jsp?status=password incorrect");
                            }
                        } else {
                            response.sendRedirect("sign_in.jsp?status=username incorrect");
                        }

                    } catch (ClassNotFoundException | SQLException e2) {
                        out.println(e2.toString());
                    }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SignInServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(SignInServlet.class.getName()).log(Level.SEVERE, null, ex);
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
