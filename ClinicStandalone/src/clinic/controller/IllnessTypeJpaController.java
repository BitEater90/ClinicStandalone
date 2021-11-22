/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.controller;

import clinic.controller.exceptions.IllegalOrphanException;
import clinic.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clinic.model.basic.Illness;
import clinic.model.basic.IllnessType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class IllnessTypeJpaController implements Serializable {

    public IllnessTypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IllnessType illnessType) {
        if (illnessType.getIllnessList() == null) {
            illnessType.setIllnessList(new ArrayList<Illness>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Illness> attachedIllnessList = new ArrayList<Illness>();
            for (Illness illnessListIllnessToAttach : illnessType.getIllnessList()) {
                illnessListIllnessToAttach = em.getReference(illnessListIllnessToAttach.getClass(), illnessListIllnessToAttach.getIllnessId());
                attachedIllnessList.add(illnessListIllnessToAttach);
            }
            illnessType.setIllnessList(attachedIllnessList);
            em.persist(illnessType);
            for (Illness illnessListIllness : illnessType.getIllnessList()) {
                IllnessType oldTypeIdOfIllnessListIllness = illnessListIllness.getTypeId();
                illnessListIllness.setTypeId(illnessType);
                illnessListIllness = em.merge(illnessListIllness);
                if (oldTypeIdOfIllnessListIllness != null) {
                    oldTypeIdOfIllnessListIllness.getIllnessList().remove(illnessListIllness);
                    oldTypeIdOfIllnessListIllness = em.merge(oldTypeIdOfIllnessListIllness);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IllnessType illnessType) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IllnessType persistentIllnessType = em.find(IllnessType.class, illnessType.getTypeId());
            List<Illness> illnessListOld = persistentIllnessType.getIllnessList();
            List<Illness> illnessListNew = illnessType.getIllnessList();
            List<String> illegalOrphanMessages = null;
            for (Illness illnessListOldIllness : illnessListOld) {
                if (!illnessListNew.contains(illnessListOldIllness)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Illness " + illnessListOldIllness + " since its typeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Illness> attachedIllnessListNew = new ArrayList<Illness>();
            for (Illness illnessListNewIllnessToAttach : illnessListNew) {
                illnessListNewIllnessToAttach = em.getReference(illnessListNewIllnessToAttach.getClass(), illnessListNewIllnessToAttach.getIllnessId());
                attachedIllnessListNew.add(illnessListNewIllnessToAttach);
            }
            illnessListNew = attachedIllnessListNew;
            illnessType.setIllnessList(illnessListNew);
            illnessType = em.merge(illnessType);
            for (Illness illnessListNewIllness : illnessListNew) {
                if (!illnessListOld.contains(illnessListNewIllness)) {
                    IllnessType oldTypeIdOfIllnessListNewIllness = illnessListNewIllness.getTypeId();
                    illnessListNewIllness.setTypeId(illnessType);
                    illnessListNewIllness = em.merge(illnessListNewIllness);
                    if (oldTypeIdOfIllnessListNewIllness != null && !oldTypeIdOfIllnessListNewIllness.equals(illnessType)) {
                        oldTypeIdOfIllnessListNewIllness.getIllnessList().remove(illnessListNewIllness);
                        oldTypeIdOfIllnessListNewIllness = em.merge(oldTypeIdOfIllnessListNewIllness);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = illnessType.getTypeId();
                if (findIllnessType(id) == null) {
                    throw new NonexistentEntityException("The illnessType with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IllnessType illnessType;
            try {
                illnessType = em.getReference(IllnessType.class, id);
                illnessType.getTypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The illnessType with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Illness> illnessListOrphanCheck = illnessType.getIllnessList();
            for (Illness illnessListOrphanCheckIllness : illnessListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IllnessType (" + illnessType + ") cannot be destroyed since the Illness " + illnessListOrphanCheckIllness + " in its illnessList field has a non-nullable typeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(illnessType);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IllnessType> findIllnessTypeEntities() {
        return findIllnessTypeEntities(true, -1, -1);
    }

    public List<IllnessType> findIllnessTypeEntities(int maxResults, int firstResult) {
        return findIllnessTypeEntities(false, maxResults, firstResult);
    }

    private List<IllnessType> findIllnessTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IllnessType.class));
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

    public IllnessType findIllnessType(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IllnessType.class, id);
        } finally {
            em.close();
        }
    }

    public int getIllnessTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IllnessType> rt = cq.from(IllnessType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
