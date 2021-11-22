package clinic.model.basic;

import clinic.model.basic.Illness;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-11-22T13:51:51")
@StaticMetamodel(IllnessType.class)
public class IllnessType_ { 

    public static volatile SingularAttribute<IllnessType, Short> typeId;
    public static volatile ListAttribute<IllnessType, Illness> illnessList;
    public static volatile SingularAttribute<IllnessType, String> type;

}