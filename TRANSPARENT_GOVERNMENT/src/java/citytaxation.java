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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author BHAVIK
 */
public class citytaxation extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        Properties prop = new Properties();
        InputStream reader = getServletContext().getResourceAsStream("/WEB-INF/tgb_information.properties");
        //FileReader reader = new FileReader("tgb_information.properties");
        prop.load(reader);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = null;
            try {
                //Connection con;
                con = DriverManager.getConnection(prop.getProperty("connectionstring"), prop.getProperty("username"), prop.getProperty("password"));
            } catch (Exception e) {
                out.println(e);
                //response.sendRedirect("/home.jsp");

            }

            String state = request.getParameter("state");
            String city = request.getParameter("city");
            if ("nathi".equals(state) && "nathi".equals(city)) {
                String sqlstring = "select tax_type,SUM(AMOUNT) AS TOTAL_TAX_CITY from transparent_governance.tax_details group by tax_type";
                PreparedStatement st = con.prepareStatement(sqlstring);
                ResultSet rs = st.executeQuery();
                //out.print("fkjhf"); 
                out.print("{\n"
                        + "  \"cols\": [\n"
                        + "        {\"id\":\"\",\"label\":\"" + "Tax Type" + "\",\"type\":\"string\"},\n"
                        + "        {\"id\":\"\",\"label\":\"Rupees\",\"type\":\"number\"}\n"
                        + "      ],\n"
                        + "  \"rows\": [\n");
                while (rs.next()) {
                    out.print("{\"c\":[{\"v\":\"");//Mushrooms
                    out.print(rs.getString("tax_type"));
                    out.print("\",\"f\":null},{\"v\":");//3
                    out.print(rs.getInt("TOTAL_TAX_CITY"));
                    out.print(",\"f\":null}]},\n");
                }
                out.print("     ]\n"
                        + "}");
            } else if (!"nathi".equals(city)) {
                ResultSet rs;
                PreparedStatement st;
                st = con.prepareStatement("select tax_type,SUM(AMOUNT) AS TOTAL_TAX_CITY from transparent_governance.tax_details where city = ? group by tax_type");
                st.setString(1, city);
                rs = st.executeQuery();

                out.print("{\n"
                        + "  \"cols\": [\n"
                        + "        {\"id\":\"\",\"label\":\"" + "Tax Type" + "\",\"type\":\"string\"},\n"
                        + "        {\"id\":\"\",\"label\":\"Rupees\",\"type\":\"number\"}\n"
                        + "      ],\n"
                        + "  \"rows\": [\n");
                while (rs.next()) {
                    out.print("{\"c\":[{\"v\":\"");//Mushrooms
                    out.print(rs.getString("tax_type"));
                    out.print("\",\"f\":null},{\"v\":");//3
                    out.print(rs.getInt("TOTAL_TAX_CITY"));
                    out.print(",\"f\":null}]},\n");
                }
                out.print("     ]\n"
                        + "}");

            } else if ("nathi".equals(city) || !"nathi".equals(state)) {
                ResultSet rs;
                PreparedStatement st;
                st = con.prepareStatement("select tax_type,SUM(AMOUNT) AS TOTAL_TAX_CITY from transparent_governance.tax_details where state = ? group by tax_type");
                st.setString(1, state);
                rs = st.executeQuery();

                out.print("{\n"
                        + "  \"cols\": [\n"
                        + "        {\"id\":\"\",\"label\":\"" + "Tax Type" + "\",\"type\":\"string\"},\n"
                        + "        {\"id\":\"\",\"label\":\"Rupees\",\"type\":\"number\"}\n"
                        + "      ],\n"
                        + "  \"rows\": [\n");
                while (rs.next()) {
                    out.print("{\"c\":[{\"v\":\"");//Mushrooms
                    out.print(rs.getString("tax_type"));
                    out.print("\",\"f\":null},{\"v\":");//3
                    out.print(rs.getInt("TOTAL_TAX_CITY"));
                    out.print(",\"f\":null}]},\n");
                }
                out.print("     ]\n"
                        + "}");
            }
        } catch (Exception e) {
            //out.println(e);
            //response.sendRedirect("/home.jsp");

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
            Logger.getLogger(citytaxation.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(citytaxation.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(citytaxation.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(citytaxation.class
                    .getName()).log(Level.SEVERE, null, ex);
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
