/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Indunil
 */
public class TestServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("Hello<br/>");

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {
            out.println("You are not trying to upload<br/>");
            return;
        }
        out.println("You are trying to upload<br/>");

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setSizeMax(MAX_UPLOAD_IN_MEGS * 1024 * 1024);

        TestProgressListener testProgressListener = new TestProgressListener();
        upload.setProgressListener(testProgressListener);

        HttpSession session = request.getSession();
        session.setAttribute("testProgressListener", testProgressListener);

        try {
            List<FileItem> fields = upload.parseRequest(request);
            out.println("Number of fields: " + fields.size() + "<br/><br/>");
            Iterator<FileItem> it = fields.iterator();
            if (!it.hasNext()) {
                out.println("No fields found");
                return;
            }
            out.println("<table border=\"1\">");
            while (it.hasNext()) {
                out.println("<tr>");
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (isFormField) {
                    out.println("<td>regular form field</td><td>FIELD NAME: " + fileItem.getFieldName()
                            + "<br/>STRING: " + fileItem.getString()
                    );
                    out.println("</td>");
                } else {
                    out.println("<td>file form field</td><td>FIELD NAME: " + fileItem.getFieldName()
                            + //							"<br/>STRING: " + fileItem.getString() +
                            "<br/>NAME: " + fileItem.getName()
                            + "<br/>CONTENT TYPE: " + fileItem.getContentType()
                            + "<br/>SIZE (BYTES): " + fileItem.getSize()
                            + "<br/>TO STRING: " + fileItem.toString()
                    );
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (FileUploadException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(TestServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
