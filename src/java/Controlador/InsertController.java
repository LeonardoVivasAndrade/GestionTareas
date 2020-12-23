package Controlador;

import DAO.Conexion;
import DAO.DocumentoJpaController;
import DAO.MateriaJpaController;
import DTO.Documento;
import DTO.Materia;
import DTO.Usuario;
import java.io.IOException;
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

public class InsertController extends HttpServlet {

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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            Conexion conexion = new Conexion();
            MateriaJpaController materiaDao = new MateriaJpaController(conexion.getBd());
            DocumentoJpaController documentoDao = new DocumentoJpaController(conexion.getBd());
            Usuario user = (Usuario) request.getSession().getAttribute("user");

            String id = request.getParameter("id");
            String nombre = request.getParameter("titulo");
            String url = request.getParameter("url");
            String iconUrl = request.getParameter("iconUrl");
            String embedUrl = request.getParameter("embedUrl");
            String sizeBytes = request.getParameter("sizeBytes");
            int idMateria = Integer.valueOf(request.getParameter("materia"));
            Materia m = materiaDao.findMateria(idMateria);

            int idDocumento = documentoDao.getDocumentoLast().getIdDocumento()+1;
            Documento d = new Documento(idDocumento);
            d.setFechaReg(new Date());
            d.setNombre(nombre);
            d.setIdDrive(id);
            d.setIconUrl(iconUrl);
            d.setIdUsuario(user);
            d.setSizeBytes(sizeBytes);
            d.setScore(0);
            d.setEmbedUrl(embedUrl);
            d.setUrl(url);
            d.setIdMateria(m);
            
            documentoDao.create(d);
            
            user.getDocumentoList1().add(d);
            request.getSession().setAttribute("user", user);
        } catch (Exception ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
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

    protected JSONArray convertirJsonPrograma(String msg) throws JSONException {
        JSONArray rta = new JSONArray();
        JSONObject m = new JSONObject();
        m.put("msg", msg);
        rta.put(m);
        return rta;
    }

}
