/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Documento;
import java.util.ArrayList;
import java.util.List;
import DTO.Comentario;
import DTO.Registro;
import DTO.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getDocumentoList() == null) {
            usuario.setDocumentoList(new ArrayList<Documento>());
        }
        if (usuario.getDocumentoList1() == null) {
            usuario.setDocumentoList1(new ArrayList<Documento>());
        }
        if (usuario.getComentarioList() == null) {
            usuario.setComentarioList(new ArrayList<Comentario>());
        }
        if (usuario.getRegistroList() == null) {
            usuario.setRegistroList(new ArrayList<Registro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Documento> attachedDocumentoList = new ArrayList<Documento>();
            for (Documento documentoListDocumentoToAttach : usuario.getDocumentoList()) {
                documentoListDocumentoToAttach = em.getReference(documentoListDocumentoToAttach.getClass(), documentoListDocumentoToAttach.getIdDocumento());
                attachedDocumentoList.add(documentoListDocumentoToAttach);
            }
            usuario.setDocumentoList(attachedDocumentoList);
            List<Documento> attachedDocumentoList1 = new ArrayList<Documento>();
            for (Documento documentoList1DocumentoToAttach : usuario.getDocumentoList1()) {
                documentoList1DocumentoToAttach = em.getReference(documentoList1DocumentoToAttach.getClass(), documentoList1DocumentoToAttach.getIdDocumento());
                attachedDocumentoList1.add(documentoList1DocumentoToAttach);
            }
            usuario.setDocumentoList1(attachedDocumentoList1);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : usuario.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getIdComentario());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            usuario.setComentarioList(attachedComentarioList);
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : usuario.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getIdRegistro());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            usuario.setRegistroList(attachedRegistroList);
            em.persist(usuario);
            for (Documento documentoListDocumento : usuario.getDocumentoList()) {
                documentoListDocumento.getUsuarioList().add(usuario);
                documentoListDocumento = em.merge(documentoListDocumento);
            }
            for (Documento documentoList1Documento : usuario.getDocumentoList1()) {
                Usuario oldIdUsuarioOfDocumentoList1Documento = documentoList1Documento.getIdUsuario();
                documentoList1Documento.setIdUsuario(usuario);
                documentoList1Documento = em.merge(documentoList1Documento);
                if (oldIdUsuarioOfDocumentoList1Documento != null) {
                    oldIdUsuarioOfDocumentoList1Documento.getDocumentoList1().remove(documentoList1Documento);
                    oldIdUsuarioOfDocumentoList1Documento = em.merge(oldIdUsuarioOfDocumentoList1Documento);
                }
            }
            for (Comentario comentarioListComentario : usuario.getComentarioList()) {
                Usuario oldIdUsuarioOfComentarioListComentario = comentarioListComentario.getIdUsuario();
                comentarioListComentario.setIdUsuario(usuario);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldIdUsuarioOfComentarioListComentario != null) {
                    oldIdUsuarioOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldIdUsuarioOfComentarioListComentario = em.merge(oldIdUsuarioOfComentarioListComentario);
                }
            }
            for (Registro registroListRegistro : usuario.getRegistroList()) {
                Usuario oldIdUsuarioOfRegistroListRegistro = registroListRegistro.getIdUsuario();
                registroListRegistro.setIdUsuario(usuario);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldIdUsuarioOfRegistroListRegistro != null) {
                    oldIdUsuarioOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldIdUsuarioOfRegistroListRegistro = em.merge(oldIdUsuarioOfRegistroListRegistro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<Documento> documentoListOld = persistentUsuario.getDocumentoList();
            List<Documento> documentoListNew = usuario.getDocumentoList();
            List<Documento> documentoList1Old = persistentUsuario.getDocumentoList1();
            List<Documento> documentoList1New = usuario.getDocumentoList1();
            List<Comentario> comentarioListOld = persistentUsuario.getComentarioList();
            List<Comentario> comentarioListNew = usuario.getComentarioList();
            List<Registro> registroListOld = persistentUsuario.getRegistroList();
            List<Registro> registroListNew = usuario.getRegistroList();
            List<String> illegalOrphanMessages = null;
            for (Documento documentoList1OldDocumento : documentoList1Old) {
                if (!documentoList1New.contains(documentoList1OldDocumento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documento " + documentoList1OldDocumento + " since its idUsuario field is not nullable.");
                }
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioListOldComentario + " since its idUsuario field is not nullable.");
                }
            }
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Documento> attachedDocumentoListNew = new ArrayList<Documento>();
            for (Documento documentoListNewDocumentoToAttach : documentoListNew) {
                documentoListNewDocumentoToAttach = em.getReference(documentoListNewDocumentoToAttach.getClass(), documentoListNewDocumentoToAttach.getIdDocumento());
                attachedDocumentoListNew.add(documentoListNewDocumentoToAttach);
            }
            documentoListNew = attachedDocumentoListNew;
            usuario.setDocumentoList(documentoListNew);
            List<Documento> attachedDocumentoList1New = new ArrayList<Documento>();
            for (Documento documentoList1NewDocumentoToAttach : documentoList1New) {
                documentoList1NewDocumentoToAttach = em.getReference(documentoList1NewDocumentoToAttach.getClass(), documentoList1NewDocumentoToAttach.getIdDocumento());
                attachedDocumentoList1New.add(documentoList1NewDocumentoToAttach);
            }
            documentoList1New = attachedDocumentoList1New;
            usuario.setDocumentoList1(documentoList1New);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getIdComentario());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            usuario.setComentarioList(comentarioListNew);
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getIdRegistro());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            usuario.setRegistroList(registroListNew);
            usuario = em.merge(usuario);
            for (Documento documentoListOldDocumento : documentoListOld) {
                if (!documentoListNew.contains(documentoListOldDocumento)) {
                    documentoListOldDocumento.getUsuarioList().remove(usuario);
                    documentoListOldDocumento = em.merge(documentoListOldDocumento);
                }
            }
            for (Documento documentoListNewDocumento : documentoListNew) {
                if (!documentoListOld.contains(documentoListNewDocumento)) {
                    documentoListNewDocumento.getUsuarioList().add(usuario);
                    documentoListNewDocumento = em.merge(documentoListNewDocumento);
                }
            }
            for (Documento documentoList1NewDocumento : documentoList1New) {
                if (!documentoList1Old.contains(documentoList1NewDocumento)) {
                    Usuario oldIdUsuarioOfDocumentoList1NewDocumento = documentoList1NewDocumento.getIdUsuario();
                    documentoList1NewDocumento.setIdUsuario(usuario);
                    documentoList1NewDocumento = em.merge(documentoList1NewDocumento);
                    if (oldIdUsuarioOfDocumentoList1NewDocumento != null && !oldIdUsuarioOfDocumentoList1NewDocumento.equals(usuario)) {
                        oldIdUsuarioOfDocumentoList1NewDocumento.getDocumentoList1().remove(documentoList1NewDocumento);
                        oldIdUsuarioOfDocumentoList1NewDocumento = em.merge(oldIdUsuarioOfDocumentoList1NewDocumento);
                    }
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Usuario oldIdUsuarioOfComentarioListNewComentario = comentarioListNewComentario.getIdUsuario();
                    comentarioListNewComentario.setIdUsuario(usuario);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldIdUsuarioOfComentarioListNewComentario != null && !oldIdUsuarioOfComentarioListNewComentario.equals(usuario)) {
                        oldIdUsuarioOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldIdUsuarioOfComentarioListNewComentario = em.merge(oldIdUsuarioOfComentarioListNewComentario);
                    }
                }
            }
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Usuario oldIdUsuarioOfRegistroListNewRegistro = registroListNewRegistro.getIdUsuario();
                    registroListNewRegistro.setIdUsuario(usuario);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldIdUsuarioOfRegistroListNewRegistro != null && !oldIdUsuarioOfRegistroListNewRegistro.equals(usuario)) {
                        oldIdUsuarioOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldIdUsuarioOfRegistroListNewRegistro = em.merge(oldIdUsuarioOfRegistroListNewRegistro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Documento> documentoList1OrphanCheck = usuario.getDocumentoList1();
            for (Documento documentoList1OrphanCheckDocumento : documentoList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Documento " + documentoList1OrphanCheckDocumento + " in its documentoList1 field has a non-nullable idUsuario field.");
            }
            List<Comentario> comentarioListOrphanCheck = usuario.getComentarioList();
            for (Comentario comentarioListOrphanCheckComentario : comentarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Comentario " + comentarioListOrphanCheckComentario + " in its comentarioList field has a non-nullable idUsuario field.");
            }
            List<Registro> registroListOrphanCheck = usuario.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Documento> documentoList = usuario.getDocumentoList();
            for (Documento documentoListDocumento : documentoList) {
                documentoListDocumento.getUsuarioList().remove(usuario);
                documentoListDocumento = em.merge(documentoListDocumento);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Usuario autenticacionGoogle(Usuario usuario) {
        Usuario usuarioBD = findUsuarioByEmail(usuario.getCorreo());        
        return usuarioBD;
    }
    
    
    
    
    public Usuario findUsuarioByEmail(String correo) {
        EntityManager em = getEntityManager();
        try {
            return (Usuario) em.createNamedQuery("Usuario.findByCorreo", Usuario.class).setParameter("correo", correo).getResultList().get(0);
        } finally {
            em.close();
        }
    }
    
}
