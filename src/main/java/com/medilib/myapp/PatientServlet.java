package com.medilib.myapp;

import java.io.IOException;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PatientPortal", value = "/patientportal")
public class PatientServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.sendRedirect("static/patientportal.html");
    }

    public void doPosst(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

    }
}
