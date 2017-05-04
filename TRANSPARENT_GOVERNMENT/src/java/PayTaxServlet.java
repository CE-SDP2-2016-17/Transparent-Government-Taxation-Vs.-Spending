/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author VIVEK BHINGRADIYA
 */
//@WebServlet(urlPatterns = {"/PayTaxServlet"})
public class PayTaxServlet extends HttpServlet {

    public static int i = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException, ConnectionException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //  out.println("TODO output your page here. You may use following sample code. ");
            HttpSession session = request.getSession(true);
            String n = (String) session.getAttribute("uname");
            String t = request.getParameter("ttype");
            String a = request.getParameter("amt");
            out.println(a);
            String c = request.getParameter("city");
            String d = request.getParameter("date");
            session.setAttribute("ttype", t);
            session.setAttribute("amt", a);
            session.setAttribute("city", c);
            session.setAttribute("date", d);

            PaymentOrder order = new PaymentOrder();
            //  out.println("TODO output your page here. You may use following sample code. ");
            order.setName("bhindi");
            order.setEmail("bhingaradiyavivek@gmail.com");
            order.setPhone("7046926618");
            order.setCurrency("INR");
            order.setAmount(Double.parseDouble(a));
            order.setDescription("This is a test transaction.");
            // out.println("TODO output your page here. You may use following sample code. ");
            order.setRedirectUrl("http://localhost:8085/transparent_government/payment_done");

            char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                char v = chars[random.nextInt(chars.length)];
                sb.append(v);
            }
            String output = sb.toString();
            order.setTransactionId(output);
            session.setAttribute("tid", output);
            Instamojo api = null;

            // gets the reference to the instamojo api
            api = InstamojoImpl.getApi("flRJhnWSVIR2j5QhYddwPxKfifc5cKhr5pHFNdkV", "JFLRMO0GwWnQGIOdk28Q6GuUedIU5caxx70AMzWzoQStf9RstR9tzTBetmSrpksAV9Xqv2uK62hncJhE4JTIvxUQaOvfhnzprqplqknYLaFewOEt9dt9tK1ZqEMXWdtx", "https://test.instamojo.com/v2/", "https://test.instamojo.com/oauth2/token/");

            boolean isOrderValid = order.validate();
            if (isOrderValid) {
                JSONObject obj = null;
                String link = null;

                try {
                    CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);

                    try {
                        obj = new JSONObject(createPaymentOrderResponse.getJsonResponse());
                    } catch (JSONException ex) {
                        Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        link = obj.getJSONObject("payment_options").getString("payment_url");
                    } catch (JSONException ex) {
                        Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //out.println("TODO output your page here. You may use following sample code. ");
                    //String link = order.getResourceUri();
                    // print the status of the payment order.
                    System.out.println(link);
                    response.sendRedirect(link);
                    System.out.println(createPaymentOrderResponse.getPaymentOrder().getStatus());
                } catch (InvalidPaymentOrderException e) {

                    if (order.isTransactionIdInvalid()) {
                        System.out.println("Transaction id is invalid. This is mostly due to duplicate  transaction id.");
                    }
                    if (order.isCurrencyInvalid()) {
                        System.out.println("Currency is invalid.");
                    }
                } catch (ConnectionException e) {

                }
            } else {
                // inform validation errors to the user.
                if (order.isTransactionIdInvalid()) {
                    System.out.println("Transaction id is invalid.");
                }
                if (order.isAmountInvalid()) {
                    System.out.println("Amount can not be less than 9.00.");
                }
                if (order.isCurrencyInvalid()) {
                    System.out.println("Please provide the currency.");
                }
                if (order.isDescriptionInvalid()) {
                    System.out.println("Description can not be greater than 255 characters.");
                }
                if (order.isEmailInvalid()) {
                    System.out.println("Please provide valid Email Address.");
                }
                if (order.isNameInvalid()) {
                    System.out.println("Name can not be greater than 100 characters.");
                }
                if (order.isPhoneInvalid()) {
                    System.out.println("Phone is invalid.");
                }
                if (order.isRedirectUrlInvalid()) {
                    System.out.println("Please provide valid Redirect url.");
                }

                if (order.isWebhookInvalid()) {
                    System.out.println("Provide a valid webhook url");
                }
            }

            //  out.println("</br><a href='/sign_in.jsp'>login page</a>");
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
            Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectionException ex) {
            Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConnectionException ex) {
            Logger.getLogger(PayTaxServlet.class.getName()).log(Level.SEVERE, null, ex);
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
