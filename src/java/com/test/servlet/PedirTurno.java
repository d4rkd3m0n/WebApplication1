/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Joako
 */
@WebServlet(name = "PedirTurno", urlPatterns = {"/PedirTurno"})
public class PedirTurno extends HttpServlet {

    @WebServiceRef(wsdlLocation = "http://192.168.40.1:8080/EsperApp/AsignarTurnos/AsignarTurnos?wsdl")
    private AsignarTurnos_Service service;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String idCorreo = request.getParameter("idCorreo");
            String idSede = request.getParameter("idSede");
            String idServicio = request.getParameter("idServicio");
            String turno = asignarTurno(idCorreo, idSede, idServicio);
            //Turno respuesta = buscarTurno(turno);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonRespuesta =  gson.toJson(turno);
            System.out.println(jsonRespuesta);
            out.append("{\"response\":\""+turno+"\"}");
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private String asignarTurno(java.lang.String correoUsuario, java.lang.String idSede, java.lang.String idServicio) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.test.servlet.AsignarTurnos port = service.getAsignarTurnosPort();
        return port.asignarTurno(correoUsuario, idSede, idServicio);
    }

    private Turno buscarTurno(java.lang.String turno) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.test.servlet.AsignarTurnos port = service.getAsignarTurnosPort();
        return port.buscarTurno(turno);
    }

}
