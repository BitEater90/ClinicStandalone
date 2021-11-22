package clinic.model.basic;

import clinic.model.basic.Doctor;
import clinic.model.basic.Illness;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-22T13:51:51")
@StaticMetamodel(Specialty.class)
public class Specialty_ { 

    public static volatile SingularAttribute<Specialty, String> specialtyName;
    public static volatile ListAttribute<Specialty, Doctor> doctorList;
    public static volatile SingularAttribute<Specialty, Short> specialtyId;
    public static volatile ListAttribute<Specialty, Illness> illnessList;

}