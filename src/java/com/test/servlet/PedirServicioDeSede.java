/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
//import java.lang.ProcessBuilder.Redirect.Type;
import java.lang.reflect.Type;
/**
 *
 * 
 * @author Joako
 * WebServlet Pedir servicio de sede
 * Solicita al web service relacionado a la base de datos los servicios asociados a una sede
 * recibe como parametro el id de la sede
 * Retorna un Gson con la informacion solicitada
 */

@WebServlet(name = "PedirServicioDeSede", urlPatterns = {"/PedirServicioDeSede"})
public class PedirServicioDeSede extends HttpServlet {

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
            String idSede = request.getParameter("idSede");
            List<Servicio> respuesta = buscarServiciosSede(idSede);
            //Type tipo = new TypeToken<List<Sede>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonRespuesta =  gson.toJson(respuesta);
            System.out.println(jsonRespuesta);
            out.write(jsonRespuesta);
            
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
/**
 * Funcion buscar servicios sede 
 * Esta funcion se conecta al metodo del webservice que busca los servicios de acuerdo a un id de sede recibido por parmaetro
 * @param codigoSede
 * @return retorna una lista de servicios 
 */
    private java.util.List<com.test.servlet.Servicio> buscarServiciosSede(java.lang.String codigoSede) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.test.servlet.AsignarTurnos port = service.getAsignarTurnosPort();
        return port.buscarServiciosSede(codigoSede);
    }

    
}
