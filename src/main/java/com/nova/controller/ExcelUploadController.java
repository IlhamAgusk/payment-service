package com.nova.controller;

import com.nova.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.InputStream;
import java.nio.file.Files;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/api/excel")
public class ExcelUploadController {

    private final PaymentService paymentService;

    @Inject
    public ExcelUploadController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadExcel(FileUploadForm form) {
        try (InputStream inputStream = Files.newInputStream(form.file.uploadedFile())) {
            paymentService.importFromExcel(inputStream);
            return Response.ok("File imported successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Failed to process Excel file: " + e.getMessage()).build();
        }
    }

    public static class FileUploadForm {

        @RestForm("file")
        public FileUpload file;
    }
}