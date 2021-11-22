package clinic.model.basic;

import clinic.model.basic.IllnessType;
import clinic.model.basic.Specialty;
import clinic.model.basic.Treatment;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-22T13:51:51")
@StaticMetamodel(Illness.class)
public class Illness_ { 

    public static volatile SingularAttribute<Illness, Integer> illnessId;
    public static volatile SingularAttribute<Illness, Boolean> isCurable;
    public static volatile SingularAttribute<Illness, Specialty> specialtyId;
    public static volatile SingularAttribute<Illness, IllnessType> typeId;
    public static volatile ListAttribute<Illness, Treatment> treatmentList;
    public static volatile SingularAttribute<Illness, String> illnessName;

}