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
import clinic.model.basic.Doctor;
import clinic.model.basic.Patient;
import clinic.model.basic.Treatment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class PatientJpaController implements Serializable {

    public PatientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patient patient) {
        if (patient.getTreatmentList() == null) {
            patient.setTreatmentList(new ArrayList<Treatment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doctor doctorId = patient.getDoctorId();
            if (doctorId != null) {
                doctorId = em.getReference(doctorId.getClass(), doctorId.getDoctorId());
                patient.setDoctorId(doctorId);
            }
            List<Treatment> attachedTreatmentList = new ArrayList<Treatment>();
            for (Treatment treatmentListTreatmentToAttach : patient.getTreatmentList()) {
                treatmentListTreatmentToAttach = em.getReference(treatmentListTreatmentToAttach.getClass(), treatmentListTreatmentToAttach.getTreatmentId());
                attachedTreatmentList.add(treatmentListTreatmentToAttach);
            }
            patient.setTreatmentList(attachedTreatmentList);
            em.persist(patient);
            if (doctorId != null) {
                doctorId.getPatientList().add(patient);
                doctorId = em.merge(doctorId);
            }
            for (Treatment treatmentListTreatment : patient.getTreatmentList()) {
                Patient oldPatientIdOfTreatmentListTreatment = treatmentListTreatment.getPatientId();
                treatmentListTreatment.setPatientId(patient);
                treatmentListTreatment = em.merge(treatmentListTreatment);
                if (oldPatientIdOfTreatmentListTreatment != null) {
                    oldPatientIdOfTreatmentListTreatment.getTreatmentList().remove(treatmentListTreatment);
                    oldPatientIdOfTreatmentListTreatment = em.merge(oldPatientIdOfTreatmentListTreatment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Patient patient) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Patient persistentPatient = em.find(Patient.class, patient.getPatientId());
            Doctor doctorIdOld = persistentPatient.getDoctorId();
            Doctor doctorIdNew = patient.getDoctorId();
            List<Treatment> treatmentListOld = persistentPatient.getTreatmentList();
            List<Treatment> treatmentListNew = patient.getTreatmentList();
            List<String> illegalOrphanMessages = null;
            for (Treatment treatmentListOldTreatment : treatmentListOld) {
                if (!treatmentListNew.contains(treatmentListOldTreatment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Treatment " + treatmentListOldTreatment + " since its patientId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (doctorIdNew != null) {
                doctorIdNew = em.getReference(doctorIdNew.getClass(), doctorIdNew.getDoctorId());
                patient.setDoctorId(doctorIdNew);
            }
            List<Treatment> attachedTreatmentListNew = new ArrayList<Treatment>();
            for (Treatment treatmentListNewTreatmentToAttach : treatmentListNew) {
                treatmentListNewTreatmentToAttach = em.getReference(treatmentListNewTreatmentToAttach.getClass(), treatmentListNewTreatmentToAttach.getTreatmentId());
                attachedTreatmentListNew.add(treatmentListNewTreatmentToAttach);
            }
            treatmentListNew = attachedTreatmentListNew;
            patient.setTreatmentList(treatmentListNew);
            patient = em.merge(patient);
            if (doctorIdOld != null && !doctorIdOld.equals(doctorIdNew)) {
                doctorIdOld.getPatientList().remove(patient);
                doctorIdOld = em.merge(doctorIdOld);
            }
            if (doctorIdNew != null && !doctorIdNew.equals(doctorIdOld)) {
                doctorIdNew.getPatientList().add(patient);
                doctorIdNew = em.merge(doctorIdNew);
            }
            for (Treatment treatmentListNewTreatment : treatmentListNew) {
                if (!treatmentListOld.contains(treatmentListNewTreatment)) {
                    Patient oldPatientIdOfTreatmentListNewTreatment = treatmentListNewTreatment.getPatientId();
                    treatmentListNewTreatment.setPatientId(patient);
                    treatmentListNewTreatment = em.merge(treatmentListNewTreatment);
                    if (oldPatientIdOfTreatmentListNewTreatment != null && !oldPatientIdOfTreatmentListNewTreatment.equals(patient)) {
                        oldPatientIdOfTreatmentListNewTreatment.getTreatmentList().remove(treatmentListNewTreatment);
                        oldPatientIdOfTreatmentListNewTreatment = em.merge(oldPatientIdOfTreatmentListNewTreatment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = patient.getPatientId();
                if (findPatient(id) == null) {
                    throw new NonexistentEntityException("The patient with id " + id + " no longer exists.");
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
            Patient patient;
            try {
                patient = em.getReference(Patient.class, id);
                patient.getPatientId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patient with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Treatment> treatmentListOrphanCheck = patient.getTreatmentList();
            for (Treatment treatmentListOrphanCheckTreatment : treatmentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Patient (" + patient + ") cannot be destroyed since the Treatment " + treatmentListOrphanCheckTreatment + " in its treatmentList field has a non-nullable patientId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Doctor doctorId = patient.getDoctorId();
            if (doctorId != null) {
                doctorId.getPatientList().remove(patient);
                doctorId = em.merge(doctorId);
            }
            em.remove(patient);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Patient> findPatientEntities() {
        return findPatientEntities(true, -1, -1);
    }

    public List<Patient> findPatientEntities(int maxResults, int firstResult) {
        return findPatientEntities(false, maxResults, firstResult);
    }

    private List<Patient> findPatientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patient.class));
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
    
    public List<Patient> findPatientEntitiesByDoctorId(short doctorId) {
        EntityManager em = getEntityManager();
           
        try {
            Query q = em.createNamedQuery("Patient.findPatientByDoctorId").setParameter("doctorId", doctorId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Patient findPatientByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();
           
        try {
            Query q = em.createNamedQuery("Patient.findByFirstAndLastName").setParameter("firstName", firstName).setParameter("lastName", lastName);
            return (Patient)q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Patient findPatient(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patient.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patient> rt = cq.from(Patient.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
