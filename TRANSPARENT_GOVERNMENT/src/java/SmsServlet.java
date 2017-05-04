/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author VIVEK BHINGRADIYA
 */
public class SmsServlet extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        Properties prop = new Properties();
        InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
        //FileReader reader = new FileReader("tgb_information.properties");
        prop.load(reader);

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            String n = (String) session.getAttribute("uname");
            String m = (String) session.getAttribute("mno");
            String c = (String) session.getAttribute("choose");
            out.println(m);
            char[] chars = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
            StringBuilder sb = new StringBuilder();

            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                char v = chars[random.nextInt(chars.length)];
                sb.append(v);
            }
            String output = sb.toString();

            /* TODO output your page here. You may use following sample code. */
            String username = "7046926618";
            String password = "vivek258";
            String mobile_number = m;
            String message = output;
            //out.println(username+password+mobile_number+message);
            String url = "http://localhost/sms/send.php?username=" + username + "&password=" + password + "&mobile_number=" + mobile_number + "&message=" + message + "";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            if (c.matches("Government_Officer")) {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection conn = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                PreparedStatement ps = conn
                        .prepareStatement("update transparent_governance.Government_Officer set password=? where username=?");
                ps.setString(1, output);
                ps.setString(2, n);
                int i = ps.executeUpdate();
                if (i > 0) {
                    session.invalidate();
                    response.sendRedirect("sign_in.jsp?status=login with new password");
                }

            }
            if (c.matches("Tax_Payer")) {
                Class.forName("oracle.jdbc.OracleDriver");
                Connection conn = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                PreparedStatement ps = conn
                        .prepareStatement("update transparent_governance.Tax_Payer set password=? where username=?");
                ps.setString(1, output);
                ps.setString(2, n);
                int i = ps.executeUpdate();
                if (i > 0) {
                    session.invalidate();
                    response.sendRedirect("sign_in.jsp?status=login with new password");
                    

                } else {
                    response.sendRedirect("forget_password.jsp?status=error occures");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SmsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SmsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SmsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SmsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
