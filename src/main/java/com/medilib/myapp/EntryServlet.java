package com.medilib.myapp;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.security.GeneralSecurityException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.Collections;

@WebServlet(name = "EntryPortal", value = "/")
public class EntryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.sendRedirect("static/entryportal.html");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        HttpTransport transport = new UrlFetchTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList("101678558796-he7teotdh66mk6s4hrdea62u8e4lj8hk.apps.googleusercontent.com"))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();

        String idTokenString = request.getParameter("id-token");

        GoogleIdToken idToken = null;
        
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException e) {
            System.out.println("auth error");
        }

        if (idToken != null) {
        Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);

        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // Use or store profile information
        // ...
        /*
           >(1)  organizations (all)

                >(2) organization (individual)

                    >(3) doctors (all for org)

                        >(4) patients (all for doctor)

                            >(5) visible docs

                            >(5) hidden docs

                    >(3) patients (all for org)

                        >(4) visible docs

                        >(4) hidden docs

           >(1)  users (all)

                >(2) patients(all)

                >(2) doctors (all)
        */
        // If a new user, create a new user inside the main directory alongside the organizations

        String json = "{";
        json += "name: " + name;
        json += "}";

        System.out.println(json);
        
        response.setContentType("application/json;");
        response.getWriter().println(json);

        } else {
        System.out.println("Invalid ID token.");
        }
    }
}