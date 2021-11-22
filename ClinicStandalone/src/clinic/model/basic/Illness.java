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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "illnesses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Illness.findAll", query = "SELECT i FROM Illness i")
    , @NamedQuery(name = "Illness.findByIllnessId", query = "SELECT i FROM Illness i WHERE i.illnessId = :illnessId")
    , @NamedQuery(name = "Illness.findByIllnessName", query = "SELECT i FROM Illness i WHERE i.illnessName = :illnessName")
    , @NamedQuery(name = "Illness.findByIsCurable", query = "SELECT i FROM Illness i WHERE i.isCurable = :isCurable")})
public class Illness implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IllnessId")
    private Integer illnessId;
    @Basic(optional = false)
    @Column(name = "IllnessName")
    private String illnessName;
    @Basic(optional = false)
    @Column(name = "IsCurable")
    private boolean isCurable;
    @JoinColumn(name = "SpecialtyId", referencedColumnName = "SpecialtyId")
    @ManyToOne(optional = false)
    private Specialty specialtyId;
    @JoinColumn(name = "TypeId", referencedColumnName = "TypeId")
    @ManyToOne(optional = false)
    private IllnessType typeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "illnessId")
    private List<Treatment> treatmentList;

    public Illness() {
    }

    public Illness(Integer illnessId) {
        this.illnessId = illnessId;
    }

    public Illness(Integer illnessId, String illnessName, boolean isCurable) {
        this.illnessId = illnessId;
        this.illnessName = illnessName;
        this.isCurable = isCurable;
    }

    public Integer getIllnessId() {
        return illnessId;
    }

    public void setIllnessId(Integer illnessId) {
        this.illnessId = illnessId;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }

    public boolean getIsCurable() {
        return isCurable;
    }

    public void setIsCurable(boolean isCurable) {
        this.isCurable = isCurable;
    }

    public Specialty getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Specialty specialtyId) {
        this.specialtyId = specialtyId;
    }

    public IllnessType getTypeId() {
        return typeId;
    }

    public void setTypeId(IllnessType typeId) {
        this.typeId = typeId;
    }

    @XmlTransient
    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (illnessId != null ? illnessId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Illness)) {
            return false;
        }
        Illness other = (Illness) object;
        if ((this.illnessId == null && other.illnessId != null) || (this.illnessId != null && !this.illnessId.equals(other.illnessId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clinic.model.basic.Illness[ illnessId=" + illnessId + " ]";
    }
    
}
