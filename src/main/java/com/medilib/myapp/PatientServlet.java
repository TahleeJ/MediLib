package com.medilib.myapp;

import java.io.IOException;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
// import com.google.cloud.storage.BucketGetOption;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import javax.servlet.http.Part;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

@WebServlet(name = "PatientPortal", value = "/patientportal")
@MultipartConfig()
public class PatientServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
   
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        Part filePart = request.getPart("new-file");
        Boolean visible = Boolean.parseBoolean(request.getParameter("visible"));

        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get("org_library", Storage.BucketGetOption.metagenerationMatch(42));
        
        

        response.sendRedirect("static/patientportal.html");
    }
}
