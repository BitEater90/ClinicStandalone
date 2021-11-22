package clinic.model.basic;

import clinic.model.basic.Patient;
import clinic.model.basic.Specialty;
import clinic.model.basic.Treatment;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-22T13:51:51")
@StaticMetamodel(Doctor.class)
public class Doctor_ { 

    public static volatile SingularAttribute<Doctor, String> firstName;
    public static volatile SingularAttribute<Doctor, String> lastName;
    public static volatile SingularAttribute<Doctor, Date> employedSinceDate;
    public static volatile ListAttribute<Doctor, Patient> patientList;
    public static volatile SingularAttribute<Doctor, Short> doctorId;
    public static volatile SingularAttribute<Doctor, Specialty> specialtyId;
    public static volatile SingularAttribute<Doctor, String> professionalTitle;
    public static volatile ListAttribute<Doctor, Treatment> treatmentList;

}