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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class SpentMoneyServlet extends HttpServlet {

    int budget = 0;

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
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);
            String a = request.getParameter("amt");
            
            int amount = Integer.valueOf(a);
            out.print(amount);
            if(amount>0){
            out.print(Integer.valueOf(session.getAttribute("budget").toString()));
            if (amount < Integer.valueOf(session.getAttribute("budget").toString())) {
                String ds = request.getParameter("des");
                String c = request.getParameter("city");
                String d = request.getParameter("date");
                String[] array = d.split("/");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate localDate = LocalDate.now();
                char[] chars = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < 5; i++) {
                    char v = chars[random.nextInt(chars.length)];
                    sb.append(v);
                }
                String output = sb.toString();
                String id = output + dtf.format(localDate);
                
                try {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
                    PreparedStatement ps = con.prepareStatement("select state_name from transparent_governance.city_name where city_name=?");
                    ps.setString(1, c);
                    String state1 = null;
                    ResultSet rs1 = ps.executeQuery();
                    
                    if (rs1.next()) {
                        state1 = rs1.getString("state_name");
                    }
                    
                    String sqlString = "insert into transparent_governance.spending_details values(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement st = con.prepareStatement(sqlString);
                    
                    st.setString(1, id);
                    st.setString(2, (String) session.getAttribute("uname"));
                    st.setString(3, (String) session.getAttribute("department"));
                    st.setString(4, ds);
                    st.setString(5, c);
                    
                    st.setInt(6, amount);
                    st.setString(7, array[1]);
                    st.setString(8, array[2]);
                    
                    st.setString(9, array[0]);
                    
                    st.setString(10,state1);
                    out.print("kjdfgnjefkgfd");
                    int i = st.executeUpdate();
                    
                    sqlString = "select * from transparent_governance.spend where department = ?";
                    st = con.prepareStatement(sqlString);
                    st.setString(1, (String) session.getAttribute("department"));
                    ResultSet rs = st.executeQuery();
                    
                    if (rs.next()) {
                        
                        int amount1 = rs.getInt("maxspend");
                        if (amount > amount1) {
                            session.setAttribute("budget",amount1);
                            response.sendRedirect("status_page.jsp?status=entered amount greater than approved amount");
                    
                        }
                        else
                        {
                            amount1 = amount1 - amount;
                            st = con.prepareStatement("update transparent_governance.spend set maxspend=? where department=?");
                            st.setInt(1, amount1);
                            st.setString(2, (String) session.getAttribute("department"));
                            
                            st.executeUpdate();
                            out.print(id);
                            session.setAttribute("budget",amount1);
                            response.sendRedirect("status_page.jsp?status=money successfully spend and transaction id ==>"+id);
                        }

                    }

                } catch (ClassNotFoundException | SQLException e2) {
                       out.println(e2.toString());
                }
            } else {
                //out.print("skjfdsbf");
                response.sendRedirect("status_page.jsp?status=entered amount greater than approved amount");
            }}
            else{
            response.sendRedirect("status_page.jsp?status=entered amount greater than zero");
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
            Logger.getLogger(SpentMoneyServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SpentMoneyServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SpentMoneyServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SpentMoneyServlet.class.getName()).log(Level.SEVERE, null, ex);
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
