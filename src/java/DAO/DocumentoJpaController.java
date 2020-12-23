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
import DTO.Materia;
import DTO.Usuario;
import java.util.ArrayList;
import java.util.List;
import DTO.Comentario;
import DTO.Documento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class DocumentoJpaController implements Serializable {

    public DocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documento documento) {
        if (documento.getUsuarioList() == null) {
            documento.setUsuarioList(new ArrayList<Usuario>());
        }
        if (documento.getComentarioList() == null) {
            documento.setComentarioList(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia idMateria = documento.getIdMateria();
            if (idMateria != null) {
                idMateria = em.getReference(idMateria.getClass(), idMateria.getIdMateria());
                documento.setIdMateria(idMateria);
            }
            Usuario idUsuario = documento.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                documento.setIdUsuario(idUsuario);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : documento.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            documento.setUsuarioList(attachedUsuarioList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : documento.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getIdComentario());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            documento.setComentarioList(attachedComentarioList);
            em.persist(documento);
            if (idMateria != null) {
                idMateria.getDocumentoList().add(documento);
                idMateria = em.merge(idMateria);
            }
            if (idUsuario != null) {
                idUsuario.getDocumentoList().add(documento);
                idUsuario = em.merge(idUsuario);
            }
            for (Usuario usuarioListUsuario : documento.getUsuarioList()) {
                usuarioListUsuario.getDocumentoList().add(documento);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (Comentario comentarioListComentario : documento.getComentarioList()) {
                Documento oldIdDocumentoOfComentarioListComentario = comentarioListComentario.getIdDocumento();
                comentarioListComentario.setIdDocumento(documento);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldIdDocumentoOfComentarioListComentario != null) {
                    oldIdDocumentoOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldIdDocumentoOfComentarioListComentario = em.merge(oldIdDocumentoOfComentarioListComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documento documento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documento persistentDocumento = em.find(Documento.class, documento.getIdDocumento());
            Materia idMateriaOld = persistentDocumento.getIdMateria();
            Materia idMateriaNew = documento.getIdMateria();
            Usuario idUsuarioOld = persistentDocumento.getIdUsuario();
            Usuario idUsuarioNew = documento.getIdUsuario();
            List<Usuario> usuarioListOld = persistentDocumento.getUsuarioList();
            List<Usuario> usuarioListNew = documento.getUsuarioList();
            List<Comentario> comentarioListOld = persistentDocumento.getComentarioList();
            List<Comentario> comentarioListNew = documento.getComentarioList();
            List<String> illegalOrphanMessages = null;
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioListOldComentario + " since its idDocumento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idMateriaNew != null) {
                idMateriaNew = em.getReference(idMateriaNew.getClass(), idMateriaNew.getIdMateria());
                documento.setIdMateria(idMateriaNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                documento.setIdUsuario(idUsuarioNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            documento.setUsuarioList(usuarioListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getIdComentario());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            documento.setComentarioList(comentarioListNew);
            documento = em.merge(documento);
            if (idMateriaOld != null && !idMateriaOld.equals(idMateriaNew)) {
                idMateriaOld.getDocumentoList().remove(documento);
                idMateriaOld = em.merge(idMateriaOld);
            }
            if (idMateriaNew != null && !idMateriaNew.equals(idMateriaOld)) {
                idMateriaNew.getDocumentoList().add(documento);
                idMateriaNew = em.merge(idMateriaNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getDocumentoList().remove(documento);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getDocumentoList().add(documento);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getDocumentoList().remove(documento);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getDocumentoList().add(documento);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Documento oldIdDocumentoOfComentarioListNewComentario = comentarioListNewComentario.getIdDocumento();
                    comentarioListNewComentario.setIdDocumento(documento);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldIdDocumentoOfComentarioListNewComentario != null && !oldIdDocumentoOfComentarioListNewComentario.equals(documento)) {
                        oldIdDocumentoOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldIdDocumentoOfComentarioListNewComentario = em.merge(oldIdDocumentoOfComentarioListNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documento.getIdDocumento();
                if (findDocumento(id) == null) {
                    throw new NonexistentEntityException("The documento with id " + id + " no longer exists.");
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
            Documento documento;
            try {
                documento = em.getReference(Documento.class, id);
                documento.getIdDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comentario> comentarioListOrphanCheck = documento.getComentarioList();
            for (Comentario comentarioListOrphanCheckComentario : comentarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Documento (" + documento + ") cannot be destroyed since the Comentario " + comentarioListOrphanCheckComentario + " in its comentarioList field has a non-nullable idDocumento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Materia idMateria = documento.getIdMateria();
            if (idMateria != null) {
                idMateria.getDocumentoList().remove(documento);
                idMateria = em.merge(idMateria);
            }
            Usuario idUsuario = documento.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getDocumentoList().remove(documento);
                idUsuario = em.merge(idUsuario);
            }
            List<Usuario> usuarioList = documento.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getDocumentoList().remove(documento);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(documento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documento> findDocumentoEntities() {
        return findDocumentoEntities(true, -1, -1);
    }

    public List<Documento> findDocumentoEntities(int maxResults, int firstResult) {
        return findDocumentoEntities(false, maxResults, firstResult);
    }

    private List<Documento> findDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documento.class));
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

    public Documento findDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documento> rt = cq.from(Documento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Documento getDocumentoLast() {
        EntityManager em = getEntityManager();
        try {
            return (Documento) em.createNativeQuery("Select * from documento order by id_documento desc limit 1", Documento.class).getResultList().get(0);
        } finally {
            em.close();
        }
    }
    
}
