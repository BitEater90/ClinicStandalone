package clinic.model.basic;

import clinic.model.basic.Doctor;
import clinic.model.basic.Treatment;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-22T13:51:51")
@StaticMetamodel(Patient.class)
public class Patient_ { 

    public static volatile SingularAttribute<Patient, Short> visitsNumber;
    public static volatile SingularAttribute<Patient, String> firstName;
    public static volatile SingularAttribute<Patient, String> lastName;
    public static volatile SingularAttribute<Patient, Integer> patientId;
    public static volatile SingularAttribute<Patient, Doctor> doctorId;
    public static volatile SingularAttribute<Patient, Date> registrationDate;
    public static volatile ListAttribute<Patient, Treatment> treatmentList;
    public static volatile SingularAttribute<Patient, Date> lastVisitDate;
    public static volatile SingularAttribute<Patient, String> pesel;
    public static volatile SingularAttribute<Patient, Boolean> hasInsurance;

}