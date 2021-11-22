package clinic.controller;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Prezes
 */
public class MultiJpaController {
    
        private DoctorJpaController doctorJpaController;
        private IllnessJpaController illnessJpaController;
        private IllnessTypeJpaController illnessTypeJpaController;
        private PatientJpaController patientJpaController;
        private SpecialtyJpaController specialtyJpaController;
        private TreatmentJpaController treatmentJpaController;
        
        private static MultiJpaController mjc;
        
        private MultiJpaController(){}
        
        private MultiJpaController(EntityManagerFactory emf)
        {
            doctorJpaController = new DoctorJpaController(emf);
            illnessJpaController = new IllnessJpaController(emf);
            illnessTypeJpaController = new IllnessTypeJpaController(emf);
            patientJpaController = new PatientJpaController(emf);
            specialtyJpaController = new SpecialtyJpaController(emf);
            treatmentJpaController = new TreatmentJpaController(emf);
        }
        
        public static MultiJpaController getInstance(EntityManagerFactory emf)
        {
            if (mjc == null)
            {
                mjc = new MultiJpaController(emf);
            }
            
            return mjc;
        }
              
                
        public TreatmentJpaController getTreatmentJpaController(){
            return treatmentJpaController;
        }
        
        public SpecialtyJpaController getSpecialtyJpaController(){
            return specialtyJpaController;
        }
        
        public PatientJpaController getPatientJpaController(){
            return patientJpaController;
        }
        
        public IllnessTypeJpaController getIllnessTypeJpaController(){
            return illnessTypeJpaController;
        }
        
        public IllnessJpaController getIllnessJpaController(){
            return illnessJpaController;
        }
        
        public DoctorJpaController getDoctorJpaController(){
            return doctorJpaController;
        }
        
        
    
}
