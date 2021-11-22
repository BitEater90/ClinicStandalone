/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.model.basic;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prezes
 */
@Entity
@Table(name = "illness_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IllnessType.findAll", query = "SELECT i FROM IllnessType i")
    , @NamedQuery(name = "IllnessType.findByTypeId", query = "SELECT i FROM IllnessType i WHERE i.typeId = :typeId")
    , @NamedQuery(name = "IllnessType.findByType", query = "SELECT i FROM IllnessType i WHERE i.type = :type")})
public class IllnessType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TypeId")
    private Short typeId;
    @Basic(optional = false)
    @Column(name = "Type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeId")
    private List<Illness> illnessList;

    public IllnessType() {
    }

    public IllnessType(Short typeId) {
        this.typeId = typeId;
    }

    public IllnessType(Short typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public Short getTypeId() {
        return typeId;
    }

    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public List<Illness> getIllnessList() {
        return illnessList;
    }

    public void setIllnessList(List<Illness> illnessList) {
        this.illnessList = illnessList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeId != null ? typeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IllnessType)) {
            return false;
        }
        IllnessType other = (IllnessType) object;
        if ((this.typeId == null && other.typeId != null) || (this.typeId != null && !this.typeId.equals(other.typeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clinic.model.basic.IllnessType[ typeId=" + typeId + " ]";
    }
    
}
