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
import DTO.Programa;
import java.util.ArrayList;
import java.util.List;
import DTO.Documento;
import DTO.Materia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Leonardo
 */
public class MateriaJpaController implements Serializable {

    public MateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) {
        if (materia.getProgramaList() == null) {
            materia.setProgramaList(new ArrayList<Programa>());
        }
        if (materia.getDocumentoList() == null) {
            materia.setDocumentoList(new ArrayList<Documento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Programa> attachedProgramaList = new ArrayList<Programa>();
            for (Programa programaListProgramaToAttach : materia.getProgramaList()) {
                programaListProgramaToAttach = em.getReference(programaListProgramaToAttach.getClass(), programaListProgramaToAttach.getIdPrograma());
                attachedProgramaList.add(programaListProgramaToAttach);
            }
            materia.setProgramaList(attachedProgramaList);
            List<Documento> attachedDocumentoList = new ArrayList<Documento>();
            for (Documento documentoListDocumentoToAttach : materia.getDocumentoList()) {
                documentoListDocumentoToAttach = em.getReference(documentoListDocumentoToAttach.getClass(), documentoListDocumentoToAttach.getIdDocumento());
                attachedDocumentoList.add(documentoListDocumentoToAttach);
            }
            materia.setDocumentoList(attachedDocumentoList);
            em.persist(materia);
            for (Programa programaListPrograma : materia.getProgramaList()) {
                programaListPrograma.getMateriaList().add(materia);
                programaListPrograma = em.merge(programaListPrograma);
            }
            for (Documento documentoListDocumento : materia.getDocumentoList()) {
                Materia oldIdMateriaOfDocumentoListDocumento = documentoListDocumento.getIdMateria();
                documentoListDocumento.setIdMateria(materia);
                documentoListDocumento = em.merge(documentoListDocumento);
                if (oldIdMateriaOfDocumentoListDocumento != null) {
                    oldIdMateriaOfDocumentoListDocumento.getDocumentoList().remove(documentoListDocumento);
                    oldIdMateriaOfDocumentoListDocumento = em.merge(oldIdMateriaOfDocumentoListDocumento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getIdMateria());
            List<Programa> programaListOld = persistentMateria.getProgramaList();
            List<Programa> programaListNew = materia.getProgramaList();
            List<Documento> documentoListOld = persistentMateria.getDocumentoList();
            List<Documento> documentoListNew = materia.getDocumentoList();
            List<String> illegalOrphanMessages = null;
            for (Documento documentoListOldDocumento : documentoListOld) {
                if (!documentoListNew.contains(documentoListOldDocumento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documento " + documentoListOldDocumento + " since its idMateria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Programa> attachedProgramaListNew = new ArrayList<Programa>();
            for (Programa programaListNewProgramaToAttach : programaListNew) {
                programaListNewProgramaToAttach = em.getReference(programaListNewProgramaToAttach.getClass(), programaListNewProgramaToAttach.getIdPrograma());
                attachedProgramaListNew.add(programaListNewProgramaToAttach);
            }
            programaListNew = attachedProgramaListNew;
            materia.setProgramaList(programaListNew);
            List<Documento> attachedDocumentoListNew = new ArrayList<Documento>();
            for (Documento documentoListNewDocumentoToAttach : documentoListNew) {
                documentoListNewDocumentoToAttach = em.getReference(documentoListNewDocumentoToAttach.getClass(), documentoListNewDocumentoToAttach.getIdDocumento());
                attachedDocumentoListNew.add(documentoListNewDocumentoToAttach);
            }
            documentoListNew = attachedDocumentoListNew;
            materia.setDocumentoList(documentoListNew);
            materia = em.merge(materia);
            for (Programa programaListOldPrograma : programaListOld) {
                if (!programaListNew.contains(programaListOldPrograma)) {
                    programaListOldPrograma.getMateriaList().remove(materia);
                    programaListOldPrograma = em.merge(programaListOldPrograma);
                }
            }
            for (Programa programaListNewPrograma : programaListNew) {
                if (!programaListOld.contains(programaListNewPrograma)) {
                    programaListNewPrograma.getMateriaList().add(materia);
                    programaListNewPrograma = em.merge(programaListNewPrograma);
                }
            }
            for (Documento documentoListNewDocumento : documentoListNew) {
                if (!documentoListOld.contains(documentoListNewDocumento)) {
                    Materia oldIdMateriaOfDocumentoListNewDocumento = documentoListNewDocumento.getIdMateria();
                    documentoListNewDocumento.setIdMateria(materia);
                    documentoListNewDocumento = em.merge(documentoListNewDocumento);
                    if (oldIdMateriaOfDocumentoListNewDocumento != null && !oldIdMateriaOfDocumentoListNewDocumento.equals(materia)) {
                        oldIdMateriaOfDocumentoListNewDocumento.getDocumentoList().remove(documentoListNewDocumento);
                        oldIdMateriaOfDocumentoListNewDocumento = em.merge(oldIdMateriaOfDocumentoListNewDocumento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materia.getIdMateria();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
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
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getIdMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Documento> documentoListOrphanCheck = materia.getDocumentoList();
            for (Documento documentoListOrphanCheckDocumento : documentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the Documento " + documentoListOrphanCheckDocumento + " in its documentoList field has a non-nullable idMateria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Programa> programaList = materia.getProgramaList();
            for (Programa programaListPrograma : programaList) {
                programaListPrograma.getMateriaList().remove(materia);
                programaListPrograma = em.merge(programaListPrograma);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
