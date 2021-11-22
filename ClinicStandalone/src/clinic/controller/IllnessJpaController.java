/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.controller;

import clinic.controller.exceptions.IllegalOrphanException;
import clinic.controller.exceptions.NonexistentEntityException;
import clinic.model.basic.Illness;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clinic.model.basic.Specialty;
import clinic.model.basic.IllnessType;
import clinic.model.basic.Treatment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class IllnessJpaController implements Serializable {

    public IllnessJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Illness illness) {
        if (illness.getTreatmentList() == null) {
            illness.setTreatmentList(new ArrayList<Treatment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialty specialtyId = illness.getSpecialtyId();
            if (specialtyId != null) {
                specialtyId = em.getReference(specialtyId.getClass(), specialtyId.getSpecialtyId());
                illness.setSpecialtyId(specialtyId);
            }
            IllnessType typeId = illness.getTypeId();
            if (typeId != null) {
                typeId = em.getReference(typeId.getClass(), typeId.getTypeId());
                illness.setTypeId(typeId);
            }
            List<Treatment> attachedTreatmentList = new ArrayList<Treatment>();
            for (Treatment treatmentListTreatmentToAttach : illness.getTreatmentList()) {
                treatmentListTreatmentToAttach = em.getReference(treatmentListTreatmentToAttach.getClass(), treatmentListTreatmentToAttach.getTreatmentId());
                attachedTreatmentList.add(treatmentListTreatmentToAttach);
            }
            illness.setTreatmentList(attachedTreatmentList);
            em.persist(illness);
            if (specialtyId != null) {
                specialtyId.getIllnessList().add(illness);
                specialtyId = em.merge(specialtyId);
            }
            if (typeId != null) {
                typeId.getIllnessList().add(illness);
                typeId = em.merge(typeId);
            }
            for (Treatment treatmentListTreatment : illness.getTreatmentList()) {
                Illness oldIllnessIdOfTreatmentListTreatment = treatmentListTreatment.getIllnessId();
                treatmentListTreatment.setIllnessId(illness);
                treatmentListTreatment = em.merge(treatmentListTreatment);
                if (oldIllnessIdOfTreatmentListTreatment != null) {
                    oldIllnessIdOfTreatmentListTreatment.getTreatmentList().remove(treatmentListTreatment);
                    oldIllnessIdOfTreatmentListTreatment = em.merge(oldIllnessIdOfTreatmentListTreatment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Illness illness) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Illness persistentIllness = em.find(Illness.class, illness.getIllnessId());
            Specialty specialtyIdOld = persistentIllness.getSpecialtyId();
            Specialty specialtyIdNew = illness.getSpecialtyId();
            IllnessType typeIdOld = persistentIllness.getTypeId();
            IllnessType typeIdNew = illness.getTypeId();
            List<Treatment> treatmentListOld = persistentIllness.getTreatmentList();
            List<Treatment> treatmentListNew = illness.getTreatmentList();
            List<String> illegalOrphanMessages = null;
            for (Treatment treatmentListOldTreatment : treatmentListOld) {
                if (!treatmentListNew.contains(treatmentListOldTreatment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Treatment " + treatmentListOldTreatment + " since its illnessId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (specialtyIdNew != null) {
                specialtyIdNew = em.getReference(specialtyIdNew.getClass(), specialtyIdNew.getSpecialtyId());
                illness.setSpecialtyId(specialtyIdNew);
            }
            if (typeIdNew != null) {
                typeIdNew = em.getReference(typeIdNew.getClass(), typeIdNew.getTypeId());
                illness.setTypeId(typeIdNew);
            }
            List<Treatment> attachedTreatmentListNew = new ArrayList<Treatment>();
            for (Treatment treatmentListNewTreatmentToAttach : treatmentListNew) {
                treatmentListNewTreatmentToAttach = em.getReference(treatmentListNewTreatmentToAttach.getClass(), treatmentListNewTreatmentToAttach.getTreatmentId());
                attachedTreatmentListNew.add(treatmentListNewTreatmentToAttach);
            }
            treatmentListNew = attachedTreatmentListNew;
            illness.setTreatmentList(treatmentListNew);
            illness = em.merge(illness);
            if (specialtyIdOld != null && !specialtyIdOld.equals(specialtyIdNew)) {
                specialtyIdOld.getIllnessList().remove(illness);
                specialtyIdOld = em.merge(specialtyIdOld);
            }
            if (specialtyIdNew != null && !specialtyIdNew.equals(specialtyIdOld)) {
                specialtyIdNew.getIllnessList().add(illness);
                specialtyIdNew = em.merge(specialtyIdNew);
            }
            if (typeIdOld != null && !typeIdOld.equals(typeIdNew)) {
                typeIdOld.getIllnessList().remove(illness);
                typeIdOld = em.merge(typeIdOld);
            }
            if (typeIdNew != null && !typeIdNew.equals(typeIdOld)) {
                typeIdNew.getIllnessList().add(illness);
                typeIdNew = em.merge(typeIdNew);
            }
            for (Treatment treatmentListNewTreatment : treatmentListNew) {
                if (!treatmentListOld.contains(treatmentListNewTreatment)) {
                    Illness oldIllnessIdOfTreatmentListNewTreatment = treatmentListNewTreatment.getIllnessId();
                    treatmentListNewTreatment.setIllnessId(illness);
                    treatmentListNewTreatment = em.merge(treatmentListNewTreatment);
                    if (oldIllnessIdOfTreatmentListNewTreatment != null && !oldIllnessIdOfTreatmentListNewTreatment.equals(illness)) {
                        oldIllnessIdOfTreatmentListNewTreatment.getTreatmentList().remove(treatmentListNewTreatment);
                        oldIllnessIdOfTreatmentListNewTreatment = em.merge(oldIllnessIdOfTreatmentListNewTreatment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = illness.getIllnessId();
                if (findIllness(id) == null) {
                    throw new NonexistentEntityException("The illness with id " + id + " no longer exists.");
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
            Illness illness;
            try {
                illness = em.getReference(Illness.class, id);
                illness.getIllnessId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The illness with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Treatment> treatmentListOrphanCheck = illness.getTreatmentList();
            for (Treatment treatmentListOrphanCheckTreatment : treatmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Illness (" + illness + ") cannot be destroyed since the Treatment " + treatmentListOrphanCheckTreatment + " in its treatmentList field has a non-nullable illnessId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Specialty specialtyId = illness.getSpecialtyId();
            if (specialtyId != null) {
                specialtyId.getIllnessList().remove(illness);
                specialtyId = em.merge(specialtyId);
            }
            IllnessType typeId = illness.getTypeId();
            if (typeId != null) {
                typeId.getIllnessList().remove(illness);
                typeId = em.merge(typeId);
            }
            em.remove(illness);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Illness> findIllnessEntities() {
        return findIllnessEntities(true, -1, -1);
    }

    public List<Illness> findIllnessEntities(int maxResults, int firstResult) {
        return findIllnessEntities(false, maxResults, firstResult);
    }

    private List<Illness> findIllnessEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Illness.class));
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

    public Illness findIllness(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Illness.class, id);
        } finally {
            em.close();
        }
    }

    public int getIllnessCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Illness> rt = cq.from(Illness.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
