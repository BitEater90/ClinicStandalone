package clinic.model.basic;

import clinic.model.basic.Doctor;
import clinic.model.basic.Illness;
import clinic.model.basic.Patient;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-22T13:51:51")
@StaticMetamodel(Treatment.class)
public class Treatment_ { 

    public static volatile SingularAttribute<Treatment, Short> visitsNumber;
    public static volatile SingularAttribute<Treatment, Illness> illnessId;
    public static volatile SingularAttribute<Treatment, Doctor> doctorId;
    public static volatile SingularAttribute<Treatment, Patient> patientId;
    public static volatile SingularAttribute<Treatment, Date> diagnosisDate;
    public static volatile SingularAttribute<Treatment, Boolean> isIllnessCured;
    public static volatile SingularAttribute<Treatment, Date> treatmentFinishDate;
    public static volatile SingularAttribute<Treatment, Integer> treatmentId;

}