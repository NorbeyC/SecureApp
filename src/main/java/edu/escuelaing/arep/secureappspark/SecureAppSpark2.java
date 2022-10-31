package edu.escuelaing.arep.secureappspark;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import static spark.Spark.*; 

public class SecureAppSpark2 {

    public static void main(String[] args) throws KeyStoreException, IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, KeyManagementException {
        
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure(getKeyStore(), getKeyStorePass(), null, null); 
        SecureUrlReader.trustStore(SecureAppSpark.getKeyStore(), SecureAppSpark.getKeyStorePass());
        port(getPort());
        get("/hello", (req, res) -> "Hello Heroku");
        get("/remote", (req, res) -> SecureUrlReader.readURL(SecureAppSpark.getUrl()));
    }
    
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5001; //returns default port if heroku-port isn't set (i.e. on localhost)
    } 
    
    static String getKeyStore() {
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore2.p12"; //returns default keystore if keystore isn't set (i.e. on localhost)
    }
    
    public static String getKeyStorePass(){
        if (System.getenv("KEYSTOREPWD") != null) {
            return System.getenv("KEYSTOREPWD");
        }
        return "654321";
    }
    
    public static String getUrl(){
        return "https://ec2-3-88-90-17.compute-1.amazonaws.com:5001/hello";
    }
}
