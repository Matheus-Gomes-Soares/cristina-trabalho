package com.example.frota.solicitacaoDeTransporte;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SolicitacaoApi {

	
	 public String retornaInfoViagem(String origem, String destino) throws Exception {
		    String apiKey = System.getenv("GOOGLE_API_KEY");
		    

	        String endpoint = "https://routes.googleapis.com/directions/v2:computeRoutes?key=" + apiKey;
	        StringBuilder response = new StringBuilder();
	        
	        String jsonInputString = String.format("""
	        		{
	        		  "origin": {
	        		    "address": "%s"
	        		  },
	        		  "destination": {
	        		    "address": "%s"
	        		  },
	        		  "travelMode": "DRIVE",
	        		  "routingPreference": "TRAFFIC_AWARE",
	        		  "extraComputations": "TOLLS",
	        		  "computeAlternativeRoutes": false,
	        		  "routeModifiers": {
	        		    "avoidTolls": false,
	        		    "avoidHighways": false,
	        		    "avoidFerries": false
	        		  }
	        		}
	        		""", origem, destino);


	  

	        URL url = new URL(endpoint);
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-Type", "application/json");

	        // ✅ Pedimos distância, tempo e pedágio
	        con.setRequestProperty("X-Goog-FieldMask",
	        	    "routes.legs.distanceMeters,routes.legs.duration,routes.travelAdvisory.tollInfo");
	        con.setDoOutput(true);

	        try (OutputStream os = con.getOutputStream()) {
	            byte[] input = jsonInputString.getBytes("utf-8");
	            os.write(input, 0, input.length);
	        }

	        int status = con.getResponseCode();
	        System.out.println("Código HTTP: " + status);

	        InputStream responseStream = (status >= 200 && status < 300)
	                ? con.getInputStream()
	                : con.getErrorStream();

	        try (BufferedReader br = new BufferedReader(new InputStreamReader(responseStream, "utf-8"))) {
	          
	            String line;
	            while ((line = br.readLine()) != null) {
	                response.append(line.trim());
	            }
	            System.out.println("\n=== Resposta ===");
	            System.out.println(response);
	        }

	        con.disconnect();
	        return response.toString();
	    }
}