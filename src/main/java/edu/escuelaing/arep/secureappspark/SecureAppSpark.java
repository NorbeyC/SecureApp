package edu.escuelaing.arep.secureappspark;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import static spark.Spark.*; 

public class SecureAppSpark {

    public static void main(String[] args) throws KeyStoreException, IOException, FileNotFoundException, NoSuchAlgorithmException, CertificateException, KeyManagementException {
        
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure(getKeyStore(), getKeyStorePass(), null, null); 
        SecureUrlReader.trustStore(SecureAppSpark2.getKeyStore(), SecureAppSpark2.getKeyStorePass());
        port(getPort());
        get("/hello", (req, res) -> "Hello secureSpark 1");
        get("/remote", (req, res) -> SecureUrlReader.readURL(SecureAppSpark2.getUrl()));
    }
    
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
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
        return "123456";
    }
    public static String getUrl(){
        return "https://ec2-18-209-67-252.compute-1.amazonaws.com:5000/hello";
    }
    
}
