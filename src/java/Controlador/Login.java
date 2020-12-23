package Controlador;

import DAO.Conexion;
import DAO.UsuarioJpaController;
import DTO.Usuario;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        try {
            EntityManagerFactory emf = Conexion.getConexion().getBd();
            UsuarioJpaController usuarioDao = new UsuarioJpaController(emf);

            Usuario usuario = new Usuario();
            String correo = "";
            String nombre = "";

            correo = req.getParameter("userEmail");
            nombre = req.getParameter("userName");

            usuario.setCorreo(correo);  
            JSONArray rpta = null;
            try {
                usuario = usuarioDao.autenticacionGoogle(usuario);                             
            } catch (Exception e) {
                usuario = new Usuario();
                usuario.setCorreo(correo);
                usuario.setNombre(nombre);
                usuario.setScore(0);
                usuarioDao.create(usuario);                
            }   
            
            rpta = convertirJsonUsuario(usuario);
            res.getWriter().write(rpta.toString());   
            req.getSession().setAttribute("user", usuario);

        } catch (Exception e) {

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

    protected JSONArray convertirJsonUsuario(Usuario u) throws JSONException {
        JSONArray rta = new JSONArray();

        JSONObject programa = new JSONObject();
        programa.put("id", u.getCorreo());
        programa.put("nombre", u.getNombre());
        programa.put("id", u.getId());
        programa.put("id", u.getScore());
        rta.put(programa);

        return rta;
    }

}
