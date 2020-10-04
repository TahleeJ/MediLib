package com.medilib.myapp;

import java.io.IOException;

import com.google.api.gax.paging.Page;

import com.google.gson.Gson;
import com.structures.VisualFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
// import com.google.cloud.storage.BucketInfo;
// import com.google.cloud.storage.BucketGetOption;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.google.cloud.storage.Blob.Builder;

import javax.servlet.http.Part;
import java.io.InputStream;

import com.google.cloud.storage.CopyWriter;

import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

@WebServlet(name = "PatientPortal", value = "/patientportal")
@MultipartConfig()
public class PatientServlet extends HttpServlet {
    private String user1 = "Jane Doe";
    private String user2 = "Hillary Felton";
    private Storage storage = StorageOptions.getDefaultInstance().getService();
    private Bucket bucket = storage.get("org_library");
    private String name = null;
    private Blob currentUser = null;
    private String organization = null;
    private String doctor = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        System.out.println("get");

        if (request.getParameter("init").equals("true")) {
            String firstName = request.getParameter("first").trim();
            String lastName = request.getParameter("last").trim(); 
            name = firstName + " " + lastName;

            boolean present = false;
            Page<Blob> blobs = bucket.list(Storage.BlobListOption.prefix("/" + name),
            Storage.BlobListOption.currentDirectory());   

            for (Blob blob : blobs.iterateAll()) {
                if (new String(blob.getContent()).equals(name)) {
                    present = true;
                    break;
                }
            }

            organization = "Organizations/Medical Company/Patients/" + name;

            // User is not in database
            if (!present) {
                System.out.println(name);

                Blob newPatient = bucket.create("Patients/" + name, name.getBytes("UTF-8"));
                currentUser = newPatient;

                // User has an organization
                if (organization != null) {
                    bucket.create(organization + "/Visible/", new byte[0]);
                    bucket.create(organization + "/Hidden/", new byte[0]);
                }
            } 
        } else {
            if (request.getParameter("switch").equals("true")) {
                String fileName = request.getParameter("file-name");
                String visibility = request.getParameter("visibility");

                String blobName = null;
                Blob file = null;

                if (visibility.equals("true")) {
                    // hold = BlobId.of("org_library", organization + "/Visible/" + fileName);
                    blobName = organization + "/Visible/" + fileName;
                    file = bucket.get(blobName);
                    byte[] content = file.getContent();
                    bucket.create(organization + "/Hidden/" + fileName, content);
                } else {
                    // hold = BlobId.of("org_library", organization + "/Hidden/" + fileName);
                    blobName = organization + "/Hidden/" + fileName;
                    file = bucket.get(blobName);
                    byte[] content = file.getContent();
                    bucket.create(organization + "/Visible/" + fileName, content);
                }
                
                file.delete();
            } else {
                if (request.getParameter("delete").equals("true")) {
                    String fileName = request.getParameter("file-name");
                    String visibility = request.getParameter("visibility");
                    String blobName = null;

                    if (visibility.equals("true")) {
                        blobName = organization + "/Visible/" + fileName;
                    } else {
                        blobName = organization + "/Hidden/" + fileName;
                    }

                    bucket.get(blobName).delete();
                }
            }
        }

        ArrayList<VisualFile> files = new ArrayList<>();
        Page<Blob> blobs = bucket.list(Storage.BlobListOption.prefix(organization + "/Visible/"));

        boolean dirName = true;
        
        for (Blob blob : blobs.iterateAll()) {
            if (dirName) {
                dirName = false;
                continue;
            }

            files.add(new VisualFile(new String(blob.getName().split("/")[5]), true));
        }

        blobs = bucket.list(Storage.BlobListOption.prefix(organization + "/Hidden/"));
        dirName = true;

        for (Blob blob : blobs.iterateAll()) {
            if (dirName) {
                dirName = false;
                continue;
            }

            files.add(new VisualFile(new String(blob.getName().split("/")[5]), false));
        }

        // Tests to substitute
        // String userName = "Jane Doe";
        // Blob newPatient = bucket.create("Patients/Jane Doe", userName.getBytes());

        // Hillary Fenton to test for existing user (doesn't exist)
        // Jane Doe to test for existing user (does exist)

        // List all "folders"
        Page<Blob> blobMaster = bucket.list();

        for (Blob blob : blobMaster.iterateAll()) {
            System.out.println(blob.getName());
        }

        System.out.println(new Gson().toJson(files));
        System.out.println("test");

        response.setContentType("application/json;");
        response.getWriter().println(new Gson().toJson(files));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
            System.out.println("test1");

            if (request.getParameter("init").equals("false")) {
                Part filePart = request.getPart("new-file");

                InputStream fileInputStream = filePart.getInputStream();
                String fileName = filePart.getSubmittedFileName();

                if (organization != null) {
                    bucket.create(organization + "/Hidden/" + fileName, fileInputStream);
                }        
            }         

        response.sendRedirect("static/patientportal.html");
    }
}
