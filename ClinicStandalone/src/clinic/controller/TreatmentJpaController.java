/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.controller;

import clinic.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clinic.model.basic.Doctor;
import clinic.model.basic.Illness;
import clinic.model.basic.Patient;
import clinic.model.basic.Treatment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class TreatmentJpaController implements Serializable {

    public TreatmentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Treatment treatment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doctor doctorId = treatment.getDoctorId();
            if (doctorId != null) {
                doctorId = em.getReference(doctorId.getClass(), doctorId.getDoctorId());
                treatment.setDoctorId(doctorId);
            }
            Illness illnessId = treatment.getIllnessId();
            if (illnessId != null) {
                illnessId = em.getReference(illnessId.getClass(), illnessId.getIllnessId());
                treatment.setIllnessId(illnessId);
            }
            Patient patientId = treatment.getPatientId();
            if (patientId != null) {
                patientId = em.getReference(patientId.getClass(), patientId.getPatientId());
                treatment.setPatientId(patientId);
            }
            em.persist(treatment);
            if (doctorId != null) {
                doctorId.getTreatmentList().add(treatment);
                doctorId = em.merge(doctorId);
            }
            if (illnessId != null) {
                illnessId.getTreatmentList().add(treatment);
                illnessId = em.merge(illnessId);
            }
            if (patientId != null) {
                patientId.getTreatmentList().add(treatment);
                patientId = em.merge(patientId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Treatment treatment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Treatment persistentTreatment = em.find(Treatment.class, treatment.getTreatmentId());
            Doctor doctorIdOld = persistentTreatment.getDoctorId();
            Doctor doctorIdNew = treatment.getDoctorId();
            Illness illnessIdOld = persistentTreatment.getIllnessId();
            Illness illnessIdNew = treatment.getIllnessId();
            Patient patientIdOld = persistentTreatment.getPatientId();
            Patient patientIdNew = treatment.getPatientId();
            if (doctorIdNew != null) {
                doctorIdNew = em.getReference(doctorIdNew.getClass(), doctorIdNew.getDoctorId());
                treatment.setDoctorId(doctorIdNew);
            }
            if (illnessIdNew != null) {
                illnessIdNew = em.getReference(illnessIdNew.getClass(), illnessIdNew.getIllnessId());
                treatment.setIllnessId(illnessIdNew);
            }
            if (patientIdNew != null) {
                patientIdNew = em.getReference(patientIdNew.getClass(), patientIdNew.getPatientId());
                treatment.setPatientId(patientIdNew);
            }
            treatment = em.merge(treatment);
            if (doctorIdOld != null && !doctorIdOld.equals(doctorIdNew)) {
                doctorIdOld.getTreatmentList().remove(treatment);
                doctorIdOld = em.merge(doctorIdOld);
            }
            if (doctorIdNew != null && !doctorIdNew.equals(doctorIdOld)) {
                doctorIdNew.getTreatmentList().add(treatment);
                doctorIdNew = em.merge(doctorIdNew);
            }
            if (illnessIdOld != null && !illnessIdOld.equals(illnessIdNew)) {
                illnessIdOld.getTreatmentList().remove(treatment);
                illnessIdOld = em.merge(illnessIdOld);
            }
            if (illnessIdNew != null && !illnessIdNew.equals(illnessIdOld)) {
                illnessIdNew.getTreatmentList().add(treatment);
                illnessIdNew = em.merge(illnessIdNew);
            }
            if (patientIdOld != null && !patientIdOld.equals(patientIdNew)) {
                patientIdOld.getTreatmentList().remove(treatment);
                patientIdOld = em.merge(patientIdOld);
            }
            if (patientIdNew != null && !patientIdNew.equals(patientIdOld)) {
                patientIdNew.getTreatmentList().add(treatment);
                patientIdNew = em.merge(patientIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = treatment.getTreatmentId();
                if (findTreatment(id) == null) {
                    throw new NonexistentEntityException("The treatment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Treatment treatment;
            try {
                treatment = em.getReference(Treatment.class, id);
                treatment.getTreatmentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The treatment with id " + id + " no longer exists.", enfe);
            }
            Doctor doctorId = treatment.getDoctorId();
            if (doctorId != null) {
                doctorId.getTreatmentList().remove(treatment);
                doctorId = em.merge(doctorId);
            }
            Illness illnessId = treatment.getIllnessId();
            if (illnessId != null) {
                illnessId.getTreatmentList().remove(treatment);
                illnessId = em.merge(illnessId);
            }
            Patient patientId = treatment.getPatientId();
            if (patientId != null) {
                patientId.getTreatmentList().remove(treatment);
                patientId = em.merge(patientId);
            }
            em.remove(treatment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Treatment> findTreatmentEntities() {
        return findTreatmentEntities(true, -1, -1);
    }

    public List<Treatment> findTreatmentEntities(int maxResults, int firstResult) {
        return findTreatmentEntities(false, maxResults, firstResult);
    }

    private List<Treatment> findTreatmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Treatment.class));
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

    public Treatment findTreatment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Treatment.class, id);
        } finally {
            em.close();
        }
    }

    public int getTreatmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Treatment> rt = cq.from(Treatment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
