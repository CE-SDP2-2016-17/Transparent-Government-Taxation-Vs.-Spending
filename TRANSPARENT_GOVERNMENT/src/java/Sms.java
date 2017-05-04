/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Sanket_b
 */
public class Sms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
		String _username = "7046926618";
		String _password = "vivek258";
		String mobile_number = "8460497794";
		String message = "hii gaga";
		
        String url = "http://localhost/sms/send.php?username="+_username+"&password="+_password+"&mobile_number="+mobile_number+"&message="+message+"";
        URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	int responseCode = con.getResponseCode();
    }
    
}
