package Controlador;

import DTO.Documento;
import DTO.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DocumentosByUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Usuario user = (Usuario) request.getSession().getAttribute("user");

        try {
            List<Documento> documentos = user.getDocumentoList1();
            
            JSONArray rpta = convertirJsonDocumento(documentos);
            response.getWriter().write(rpta.toString());
        } catch (Exception e) {            
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
            documento.put("size", elemento.getSizeBytes());
            documento.put("idDrive", elemento.getIdDrive());
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
