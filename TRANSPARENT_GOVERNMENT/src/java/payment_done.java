/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author VIVEK BHINGRADIYA
 */
public class payment_done extends HttpServlet {

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
            throws ServletException, IOException, ConnectionException {

        PrintWriter out = response.getWriter();
        Properties prop = new Properties();
        InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
        //FileReader reader = new FileReader("tgb_information.properties");
        prop.load(reader);
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Instamojo api = null;
                api = InstamojoImpl.getApi("flRJhnWSVIR2j5QhYddwPxKfifc5cKhr5pHFNdkV", "JFLRMO0GwWnQGIOdk28Q6GuUedIU5caxx70AMzWzoQStf9RstR9tzTBetmSrpksAV9Xqv2uK62hncJhE4JTIvxUQaOvfhnzprqplqknYLaFewOEt9dt9tK1ZqEMXWdtx", "https://test.instamojo.com/v2/", "https://test.instamojo.com/oauth2/token/");

                PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId(request.getParameter("transaction_id"));
                String n = (String) session.getAttribute("uname");
                String t = (String) session.getAttribute("ttype");
                String a = (String) session.getAttribute("amt");
                int as = Integer.valueOf(a);
                String c = (String) session.getAttribute("city");
                String d = (String) session.getAttribute("date");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate localDate = LocalDate.now();
                String id = (String) session.getAttribute("tid") + dtf.format(localDate);
                String[] array = d.split("/");

                try {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                    PreparedStatement ps = con.prepareStatement("select state_name from transparent_governance.city_name where city_name=?");
                    ps.setString(1, c);
                    String state = null;
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        state = rs.getString("state_name");
                    }
                    ps = con.prepareStatement("select * from transparent_governance.spend");
                    rs = ps.executeQuery();
                    int paid_amount = as;
                    while (rs.next()) {
                        String department = rs.getString("department");
                        if (!"other".equals(department)) {
                            int percent = rs.getInt("spentper");
                            int maxspend = rs.getInt("maxspend");
                            int amount = as * percent / 100;
                            as = as - amount;
                            maxspend = maxspend + amount;
                            ps = con.prepareStatement("update transparent_governance.spend set maxspend=? where department=?");
                            ps.setInt(1, maxspend);
                            ps.setString(2, department);
                            ps.executeUpdate();
                        } else {
                            int maxspend = rs.getInt("maxspend");
                            maxspend = maxspend + as;
                            ps = con.prepareStatement("update transparent_governance.spend set maxspend=? where department=?");
                            ps.setInt(1, maxspend);
                            ps.setString(2, department);
                            ps.executeUpdate();
                        }
                    }

                    ps = con
                            .prepareStatement("insert into transparent_governance.tax_details values(?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, t);
                    ps.setString(2, c);
                    ps.setInt(3, paid_amount);
                    ps.setString(4, n);
                    ps.setString(5, array[1]);
                    ps.setString(6, array[2]);
                    ps.setString(7, id);
                    ps.setString(8, state);
                    ps.setString(9, array[0]);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        int tax = (int)Integer.valueOf(session.getAttribute("total_tax1").toString());
                        int tax_paid =(int)Integer.valueOf(session.getAttribute("tax_paid").toString());
                        tax_paid = tax_paid + as;
                        session.setAttribute("tax_paid", tax_paid);
                        tax_paid = tax - tax_paid;
                        session.setAttribute("tax_left", tax_paid);
                        response.sendRedirect("payment_done.jsp?transaction_id=" + id);
                        // session.invalidate();
                    }
                } catch (ClassNotFoundException | SQLException e2) {
                    out.println(e2.toString());
                }
            } else {
                response.sendRedirect("sign_in?status=login first");
            }
        } catch (ConnectionException e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ConnectionException ex) {
            Logger.getLogger(payment_done.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ConnectionException ex) {
            Logger.getLogger(payment_done.class.getName()).log(Level.SEVERE, null, ex);
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
