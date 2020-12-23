package Controlador;

import DAO.*;
import DTO.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Conexion conexion = new Conexion();
        ProgramaJpaController jpc = new ProgramaJpaController(conexion.getBd());

        String filtro = request.getParameter("filtro");

        switch (filtro) {

            case "programa":
                List lista = jpc.findProgramaEntities();
                JSONArray respuesta;
                try {
                    respuesta = convertirJsonPrograma(lista);
                    response.getWriter().write(respuesta.toString());
                    //fin
                } catch (JSONException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "materia":
                try {
                    int progama = Integer.parseInt(request.getParameter("programa"));
                    Programa p = jpc.findPrograma(progama);
                    List<Materia> materias = p.getMateriaList();
                    JSONArray rpta = convertirJsonMateria(materias);
                    response.getWriter().write(rpta.toString());

                } catch (Exception e) {
                }
                break;

            case "buscar-titulo":
                try {
                    String titul = request.getParameter("titulo").toUpperCase();
                    List<Programa> programas = jpc.findProgramaEntities();
                    List<Documento> documentos = new ArrayList<>();

                    for (Programa p : programas) {
                        List<Materia> materias = p.getMateriaList();
                        for (Materia m : materias) {
                            List<Documento> documentosMateria = m.getDocumentoList();
                            for (Documento d : documentosMateria) {
                                if (d.getNombre().toUpperCase().contains(titul)) {
                                    if(!documentos.contains(d))
                                        documentos.add(d);
                                }
                            }
                        }
                    }
                    JSONArray rpta = convertirJsonDocumento(documentos);
                    response.getWriter().write(rpta.toString());

                } catch (Exception e) {
                    String a = e.getMessage();
                }
                break;

            case "buscar-titulo-programa":
                try {
                    String titulo = request.getParameter("titulo").toUpperCase();
                    int idProgama = Integer.parseInt(request.getParameter("programa"));
                    Programa p = jpc.findPrograma(idProgama);
                    List<Materia> materias = p.getMateriaList();
                    List<Documento> documentos = new ArrayList<>();

                    for (Materia m : materias) {
                        List<Documento> documentosMateria = m.getDocumentoList();
                        for (Documento d : documentosMateria) {
                            if (d.getNombre().toUpperCase().contains(titulo)) {
                                documentos.add(d);
                            }
                        }
                    }
                    JSONArray rpta = convertirJsonDocumento(documentos, p);
                    response.getWriter().write(rpta.toString());

                } catch (Exception e) {
                    String a = e.getMessage();
                }
                break;

            case "buscar-programa":
                try {
                    int idProgama = Integer.parseInt(request.getParameter("programa"));
                    Programa p = jpc.findPrograma(idProgama);
                    List<Materia> materias = p.getMateriaList();
                    List<Documento> documentos = new ArrayList<>();

                    for (Materia m : materias) {
                        List<Documento> documentosMateria = m.getDocumentoList();
                        for (Documento d : documentosMateria) {
                            documentos.add(d);
                        }
                    }
                    JSONArray rpta = convertirJsonDocumento(documentos, p);
                    response.getWriter().write(rpta.toString());

                } catch (Exception e) {
                }
                break;

            case "buscar-programa-materia":
                try {
                    int idProgama = Integer.parseInt(request.getParameter("programa"));
                    int idMateria = Integer.parseInt(request.getParameter("materia"));
                    Programa p = jpc.findPrograma(idProgama);
                    Materia materiaEntity = new Materia(idMateria);
                    int index = p.getMateriaList().indexOf(materiaEntity);
                    Materia m = p.getMateriaList().get(index);
                    List<Documento> documentos = m.getDocumentoList();

                    JSONArray rpta = convertirJsonDocumento(documentos, p);
                    response.getWriter().write(rpta.toString());

                } catch (Exception e) {
                }
                break;

            case "buscar-titulo-programa-materia":
                try {
                    String titulo = request.getParameter("titulo").toUpperCase();
                    int idProgama = Integer.parseInt(request.getParameter("programa"));
                    int idMateria = Integer.parseInt(request.getParameter("materia"));
                    Programa p = jpc.findPrograma(idProgama);
                    Materia materiaEntity = new Materia(idMateria);
                    int index = p.getMateriaList().indexOf(materiaEntity);
                    Materia m = p.getMateriaList().get(index);
                    List<Documento> documentosMateria = m.getDocumentoList();
                    List<Documento> documentos = new ArrayList<>();

                    for (Documento d : documentosMateria) {
                        if (d.getNombre().toUpperCase().contains(titulo)) {
                            documentos.add(d);
                        }
                    }

                    JSONArray rpta = convertirJsonDocumento(documentos, p);
                    response.getWriter().write(rpta.toString());

                } catch (Exception e) {
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected JSONArray convertirJsonPrograma(List<Programa> programas) throws JSONException {
        JSONArray rta = new JSONArray();
        for (Programa elemento : programas) {
            JSONObject programa = new JSONObject();
            programa.put("id", elemento.getIdPrograma());
            programa.put("nombre", elemento.getNombre());
            rta.put(programa);
        }

        return rta;
    }

    protected JSONArray convertirJsonMateria(List<Materia> materias) throws JSONException {
        JSONArray rta = new JSONArray();
        for (Materia elemento : materias) {
            JSONObject materia = new JSONObject();
            materia.put("id", elemento.getIdMateria());
            materia.put("nombre", elemento.getNombre());
            rta.put(materia);
        }

        return rta;
    }

    protected JSONArray convertirJsonDocumento(List<Documento> documentos, Programa programa) throws JSONException {
        JSONArray rta = new JSONArray();
        for (Documento elemento : documentos) {
            JSONObject documento = new JSONObject();
            documento.put("autor", elemento.getIdUsuario().getNombre());
            documento.put("nombre", elemento.getNombre());
            documento.put("fecha", dateToString(elemento.getFechaReg()));
            documento.put("materia", elemento.getIdMateria().getNombre());
            documento.put("url", elemento.getUrl());
            documento.put("iconUrl", elemento.getIconUrl());
            documento.put("size", elemento.getSizeBytes());
            documento.put("idDrive", elemento.getIdDrive());            
            documento.put("programa", programa.getNombre());
            
            rta.put(documento);
        }

        return rta;
    }

    protected JSONArray convertirJsonDocumento(List<Documento> documentos) throws JSONException {
        JSONArray rta = new JSONArray();
        for (Documento elemento : documentos) {
            JSONObject documento = new JSONObject();
            documento.put("autor", elemento.getIdUsuario().getNombre());
            documento.put("nombre", elemento.getNombre());
            documento.put("fecha", dateToString(elemento.getFechaReg()));
            documento.put("materia", elemento.getIdMateria().getNombre());
            documento.put("url", elemento.getUrl());
            documento.put("iconUrl", elemento.getIconUrl());
            documento.put("idDrive", elemento.getSizeBytes());
            documento.put("size", elemento.getIdDrive());  
            rta.put(documento);
        }

        return rta;
    }

    private static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MMM-dd");
        return formato.format(date);
    }

}
