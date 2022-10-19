package edu.escuelaing.arep.secureappspark;

import static spark.Spark.*; 

public class SecureAppSpark {

    public static void main(String[] args) {
        
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure("keystores/ecikeystore.p12", "123456", null, null); 
        
        port(getPort());
        get("/hello", (req, res) -> "Hello Heroku");
    }
    
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    } 
}
