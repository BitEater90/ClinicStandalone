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
import java.util.ArrayList;
import java.util.List;
import clinic.model.basic.Doctor;
import clinic.model.basic.Specialty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class SpecialtyJpaController implements Serializable {

    public SpecialtyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Specialty specialty) {
        if (specialty.getIllnessList() == null) {
            specialty.setIllnessList(new ArrayList<Illness>());
        }
        if (specialty.getDoctorList() == null) {
            specialty.setDoctorList(new ArrayList<Doctor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Illness> attachedIllnessList = new ArrayList<Illness>();
            for (Illness illnessListIllnessToAttach : specialty.getIllnessList()) {
                illnessListIllnessToAttach = em.getReference(illnessListIllnessToAttach.getClass(), illnessListIllnessToAttach.getIllnessId());
                attachedIllnessList.add(illnessListIllnessToAttach);
            }
            specialty.setIllnessList(attachedIllnessList);
            List<Doctor> attachedDoctorList = new ArrayList<Doctor>();
            for (Doctor doctorListDoctorToAttach : specialty.getDoctorList()) {
                doctorListDoctorToAttach = em.getReference(doctorListDoctorToAttach.getClass(), doctorListDoctorToAttach.getDoctorId());
                attachedDoctorList.add(doctorListDoctorToAttach);
            }
            specialty.setDoctorList(attachedDoctorList);
            em.persist(specialty);
            for (Illness illnessListIllness : specialty.getIllnessList()) {
                Specialty oldSpecialtyIdOfIllnessListIllness = illnessListIllness.getSpecialtyId();
                illnessListIllness.setSpecialtyId(specialty);
                illnessListIllness = em.merge(illnessListIllness);
                if (oldSpecialtyIdOfIllnessListIllness != null) {
                    oldSpecialtyIdOfIllnessListIllness.getIllnessList().remove(illnessListIllness);
                    oldSpecialtyIdOfIllnessListIllness = em.merge(oldSpecialtyIdOfIllnessListIllness);
                }
            }
            for (Doctor doctorListDoctor : specialty.getDoctorList()) {
                Specialty oldSpecialtyIdOfDoctorListDoctor = doctorListDoctor.getSpecialtyId();
                doctorListDoctor.setSpecialtyId(specialty);
                doctorListDoctor = em.merge(doctorListDoctor);
                if (oldSpecialtyIdOfDoctorListDoctor != null) {
                    oldSpecialtyIdOfDoctorListDoctor.getDoctorList().remove(doctorListDoctor);
                    oldSpecialtyIdOfDoctorListDoctor = em.merge(oldSpecialtyIdOfDoctorListDoctor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Specialty specialty) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Specialty persistentSpecialty = em.find(Specialty.class, specialty.getSpecialtyId());
            List<Illness> illnessListOld = persistentSpecialty.getIllnessList();
            List<Illness> illnessListNew = specialty.getIllnessList();
            List<Doctor> doctorListOld = persistentSpecialty.getDoctorList();
            List<Doctor> doctorListNew = specialty.getDoctorList();
            List<String> illegalOrphanMessages = null;
            for (Illness illnessListOldIllness : illnessListOld) {
                if (!illnessListNew.contains(illnessListOldIllness)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Illness " + illnessListOldIllness + " since its specialtyId field is not nullable.");
                }
            }
            for (Doctor doctorListOldDoctor : doctorListOld) {
                if (!doctorListNew.contains(doctorListOldDoctor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Doctor " + doctorListOldDoctor + " since its specialtyId field is not nullable.");
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
            specialty.setIllnessList(illnessListNew);
            List<Doctor> attachedDoctorListNew = new ArrayList<Doctor>();
            for (Doctor doctorListNewDoctorToAttach : doctorListNew) {
                doctorListNewDoctorToAttach = em.getReference(doctorListNewDoctorToAttach.getClass(), doctorListNewDoctorToAttach.getDoctorId());
                attachedDoctorListNew.add(doctorListNewDoctorToAttach);
            }
            doctorListNew = attachedDoctorListNew;
            specialty.setDoctorList(doctorListNew);
            specialty = em.merge(specialty);
            for (Illness illnessListNewIllness : illnessListNew) {
                if (!illnessListOld.contains(illnessListNewIllness)) {
                    Specialty oldSpecialtyIdOfIllnessListNewIllness = illnessListNewIllness.getSpecialtyId();
                    illnessListNewIllness.setSpecialtyId(specialty);
                    illnessListNewIllness = em.merge(illnessListNewIllness);
                    if (oldSpecialtyIdOfIllnessListNewIllness != null && !oldSpecialtyIdOfIllnessListNewIllness.equals(specialty)) {
                        oldSpecialtyIdOfIllnessListNewIllness.getIllnessList().remove(illnessListNewIllness);
                        oldSpecialtyIdOfIllnessListNewIllness = em.merge(oldSpecialtyIdOfIllnessListNewIllness);
                    }
                }
            }
            for (Doctor doctorListNewDoctor : doctorListNew) {
                if (!doctorListOld.contains(doctorListNewDoctor)) {
                    Specialty oldSpecialtyIdOfDoctorListNewDoctor = doctorListNewDoctor.getSpecialtyId();
                    doctorListNewDoctor.setSpecialtyId(specialty);
                    doctorListNewDoctor = em.merge(doctorListNewDoctor);
                    if (oldSpecialtyIdOfDoctorListNewDoctor != null && !oldSpecialtyIdOfDoctorListNewDoctor.equals(specialty)) {
                        oldSpecialtyIdOfDoctorListNewDoctor.getDoctorList().remove(doctorListNewDoctor);
                        oldSpecialtyIdOfDoctorListNewDoctor = em.merge(oldSpecialtyIdOfDoctorListNewDoctor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = specialty.getSpecialtyId();
                if (findSpecialty(id) == null) {
                    throw new NonexistentEntityException("The specialty with id " + id + " no longer exists.");
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
            Specialty specialty;
            try {
                specialty = em.getReference(Specialty.class, id);
                specialty.getSpecialtyId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The specialty with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Illness> illnessListOrphanCheck = specialty.getIllnessList();
            for (Illness illnessListOrphanCheckIllness : illnessListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Specialty (" + specialty + ") cannot be destroyed since the Illness " + illnessListOrphanCheckIllness + " in its illnessList field has a non-nullable specialtyId field.");
            }
            List<Doctor> doctorListOrphanCheck = specialty.getDoctorList();
            for (Doctor doctorListOrphanCheckDoctor : doctorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Specialty (" + specialty + ") cannot be destroyed since the Doctor " + doctorListOrphanCheckDoctor + " in its doctorList field has a non-nullable specialtyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(specialty);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Specialty> findSpecialtyEntities() {
        return findSpecialtyEntities(true, -1, -1);
    }

    public List<Specialty> findSpecialtyEntities(int maxResults, int firstResult) {
        return findSpecialtyEntities(false, maxResults, firstResult);
    }

    private List<Specialty> findSpecialtyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Specialty.class));
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

    public Specialty findSpecialty(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Specialty.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpecialtyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Specialty> rt = cq.from(Specialty.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
