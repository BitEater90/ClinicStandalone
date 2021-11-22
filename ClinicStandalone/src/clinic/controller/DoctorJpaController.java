/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.controller;

import clinic.controller.exceptions.IllegalOrphanException;
import clinic.controller.exceptions.NonexistentEntityException;
import clinic.model.basic.Doctor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import clinic.model.basic.Specialty;
import clinic.model.basic.Patient;
import java.util.ArrayList;
import java.util.List;
import clinic.model.basic.Treatment;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class DoctorJpaController implements Serializable {

    public DoctorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Doctor doctor) {
        if (doctor.getPatientList() == null) {
            doctor.setPatientList(new ArrayList<Patient>());
        }
        if (doctor.getTreatmentList() == null) {
            doctor.setTreatmentList(new ArrayList<Treatment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialty specialtyId = doctor.getSpecialtyId();
            if (specialtyId != null) {
                specialtyId = em.getReference(specialtyId.getClass(), specialtyId.getSpecialtyId());
                doctor.setSpecialtyId(specialtyId);
            }
            List<Patient> attachedPatientList = new ArrayList<Patient>();
            for (Patient patientListPatientToAttach : doctor.getPatientList()) {
                patientListPatientToAttach = em.getReference(patientListPatientToAttach.getClass(), patientListPatientToAttach.getPatientId());
                attachedPatientList.add(patientListPatientToAttach);
            }
            doctor.setPatientList(attachedPatientList);
            List<Treatment> attachedTreatmentList = new ArrayList<Treatment>();
            for (Treatment treatmentListTreatmentToAttach : doctor.getTreatmentList()) {
                treatmentListTreatmentToAttach = em.getReference(treatmentListTreatmentToAttach.getClass(), treatmentListTreatmentToAttach.getTreatmentId());
                attachedTreatmentList.add(treatmentListTreatmentToAttach);
            }
            doctor.setTreatmentList(attachedTreatmentList);
            em.persist(doctor);
            if (specialtyId != null) {
                specialtyId.getDoctorList().add(doctor);
                specialtyId = em.merge(specialtyId);
            }
            for (Patient patientListPatient : doctor.getPatientList()) {
                Doctor oldDoctorIdOfPatientListPatient = patientListPatient.getDoctorId();
                patientListPatient.setDoctorId(doctor);
                patientListPatient = em.merge(patientListPatient);
                if (oldDoctorIdOfPatientListPatient != null) {
                    oldDoctorIdOfPatientListPatient.getPatientList().remove(patientListPatient);
                    oldDoctorIdOfPatientListPatient = em.merge(oldDoctorIdOfPatientListPatient);
                }
            }
            for (Treatment treatmentListTreatment : doctor.getTreatmentList()) {
                Doctor oldDoctorIdOfTreatmentListTreatment = treatmentListTreatment.getDoctorId();
                treatmentListTreatment.setDoctorId(doctor);
                treatmentListTreatment = em.merge(treatmentListTreatment);
                if (oldDoctorIdOfTreatmentListTreatment != null) {
                    oldDoctorIdOfTreatmentListTreatment.getTreatmentList().remove(treatmentListTreatment);
                    oldDoctorIdOfTreatmentListTreatment = em.merge(oldDoctorIdOfTreatmentListTreatment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Doctor doctor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doctor persistentDoctor = em.find(Doctor.class, doctor.getDoctorId());
            Specialty specialtyIdOld = persistentDoctor.getSpecialtyId();
            Specialty specialtyIdNew = doctor.getSpecialtyId();
            List<Patient> patientListOld = persistentDoctor.getPatientList();
            List<Patient> patientListNew = doctor.getPatientList();
            List<Treatment> treatmentListOld = persistentDoctor.getTreatmentList();
            List<Treatment> treatmentListNew = doctor.getTreatmentList();
            List<String> illegalOrphanMessages = null;
            for (Patient patientListOldPatient : patientListOld) {
                if (!patientListNew.contains(patientListOldPatient)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Patient " + patientListOldPatient + " since its doctorId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (specialtyIdNew != null) {
                specialtyIdNew = em.getReference(specialtyIdNew.getClass(), specialtyIdNew.getSpecialtyId());
                doctor.setSpecialtyId(specialtyIdNew);
            }
            List<Patient> attachedPatientListNew = new ArrayList<Patient>();
            for (Patient patientListNewPatientToAttach : patientListNew) {
                patientListNewPatientToAttach = em.getReference(patientListNewPatientToAttach.getClass(), patientListNewPatientToAttach.getPatientId());
                attachedPatientListNew.add(patientListNewPatientToAttach);
            }
            patientListNew = attachedPatientListNew;
            doctor.setPatientList(patientListNew);
            List<Treatment> attachedTreatmentListNew = new ArrayList<Treatment>();
            for (Treatment treatmentListNewTreatmentToAttach : treatmentListNew) {
                treatmentListNewTreatmentToAttach = em.getReference(treatmentListNewTreatmentToAttach.getClass(), treatmentListNewTreatmentToAttach.getTreatmentId());
                attachedTreatmentListNew.add(treatmentListNewTreatmentToAttach);
            }
            treatmentListNew = attachedTreatmentListNew;
            doctor.setTreatmentList(treatmentListNew);
            doctor = em.merge(doctor);
            if (specialtyIdOld != null && !specialtyIdOld.equals(specialtyIdNew)) {
                specialtyIdOld.getDoctorList().remove(doctor);
                specialtyIdOld = em.merge(specialtyIdOld);
            }
            if (specialtyIdNew != null && !specialtyIdNew.equals(specialtyIdOld)) {
                specialtyIdNew.getDoctorList().add(doctor);
                specialtyIdNew = em.merge(specialtyIdNew);
            }
            for (Patient patientListNewPatient : patientListNew) {
                if (!patientListOld.contains(patientListNewPatient)) {
                    Doctor oldDoctorIdOfPatientListNewPatient = patientListNewPatient.getDoctorId();
                    patientListNewPatient.setDoctorId(doctor);
                    patientListNewPatient = em.merge(patientListNewPatient);
                    if (oldDoctorIdOfPatientListNewPatient != null && !oldDoctorIdOfPatientListNewPatient.equals(doctor)) {
                        oldDoctorIdOfPatientListNewPatient.getPatientList().remove(patientListNewPatient);
                        oldDoctorIdOfPatientListNewPatient = em.merge(oldDoctorIdOfPatientListNewPatient);
                    }
                }
            }
            for (Treatment treatmentListOldTreatment : treatmentListOld) {
                if (!treatmentListNew.contains(treatmentListOldTreatment)) {
                    treatmentListOldTreatment.setDoctorId(null);
                    treatmentListOldTreatment = em.merge(treatmentListOldTreatment);
                }
            }
            for (Treatment treatmentListNewTreatment : treatmentListNew) {
                if (!treatmentListOld.contains(treatmentListNewTreatment)) {
                    Doctor oldDoctorIdOfTreatmentListNewTreatment = treatmentListNewTreatment.getDoctorId();
                    treatmentListNewTreatment.setDoctorId(doctor);
                    treatmentListNewTreatment = em.merge(treatmentListNewTreatment);
                    if (oldDoctorIdOfTreatmentListNewTreatment != null && !oldDoctorIdOfTreatmentListNewTreatment.equals(doctor)) {
                        oldDoctorIdOfTreatmentListNewTreatment.getTreatmentList().remove(treatmentListNewTreatment);
                        oldDoctorIdOfTreatmentListNewTreatment = em.merge(oldDoctorIdOfTreatmentListNewTreatment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = doctor.getDoctorId();
                if (findDoctor(id) == null) {
                    throw new NonexistentEntityException("The doctor with id " + id + " no longer exists.");
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
            Doctor doctor;
            try {
                doctor = em.getReference(Doctor.class, id);
                doctor.getDoctorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The doctor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Patient> patientListOrphanCheck = doctor.getPatientList();
            for (Patient patientListOrphanCheckPatient : patientListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Doctor (" + doctor + ") cannot be destroyed since the Patient " + patientListOrphanCheckPatient + " in its patientList field has a non-nullable doctorId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Specialty specialtyId = doctor.getSpecialtyId();
            if (specialtyId != null) {
                specialtyId.getDoctorList().remove(doctor);
                specialtyId = em.merge(specialtyId);
            }
            List<Treatment> treatmentList = doctor.getTreatmentList();
            for (Treatment treatmentListTreatment : treatmentList) {
                treatmentListTreatment.setDoctorId(null);
                treatmentListTreatment = em.merge(treatmentListTreatment);
            }
            em.remove(doctor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Doctor> findDoctorEntities() {
        return findDoctorEntities(true, -1, -1);
    }

    public List<Doctor> findDoctorEntities(int maxResults, int firstResult) {
        return findDoctorEntities(false, maxResults, firstResult);
    }

    private List<Doctor> findDoctorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Doctor.class));
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
    
    public List<Doctor> findDoctorEntitiesBySpecialty(String specialtyName) {
        EntityManager em = getEntityManager();
           
        try {
            Query q = em.createNamedQuery("Doctor.findBySpecialtyName").setParameter("specialtyName", specialtyName);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Doctor findDoctoryByFirstAndLastName(String firstName, String lastName){
        EntityManager em = getEntityManager();
        
        try {
            Query q = em.createNamedQuery("Doctor.findByFirstAndLastName").setParameter("firstName", firstName).setParameter("lastName", lastName);
            return (Doctor)q.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Doctor findDoctor(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Doctor.class, id);
        } finally {
            em.close();
        }
    }

    public int getDoctorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Doctor> rt = cq.from(Doctor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
