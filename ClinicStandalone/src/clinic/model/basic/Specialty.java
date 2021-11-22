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
@Table(name = "specialties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Specialty.findAll", query = "SELECT s FROM Specialty s")
    , @NamedQuery(name = "Specialty.findBySpecialtyId", query = "SELECT s FROM Specialty s WHERE s.specialtyId = :specialtyId")
    , @NamedQuery(name = "Specialty.findBySpecialtyName", query = "SELECT s FROM Specialty s WHERE s.specialtyName = :specialtyName")})
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SpecialtyId")
    private Short specialtyId;
    @Basic(optional = false)
    @Column(name = "SpecialtyName")
    private String specialtyName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialtyId")
    private List<Illness> illnessList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialtyId")
    private List<Doctor> doctorList;

    public Specialty() {
    }

    public Specialty(Short specialtyId) {
        this.specialtyId = specialtyId;
    }

    public Specialty(Short specialtyId, String specialtyName) {
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
    }

    public Short getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Short specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    @XmlTransient
    public List<Illness> getIllnessList() {
        return illnessList;
    }

    public void setIllnessList(List<Illness> illnessList) {
        this.illnessList = illnessList;
    }

    @XmlTransient
    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specialtyId != null ? specialtyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialty)) {
            return false;
        }
        Specialty other = (Specialty) object;
        if ((this.specialtyId == null && other.specialtyId != null) || (this.specialtyId != null && !this.specialtyId.equals(other.specialtyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clinic.model.basic.Specialty[ specialtyId=" + specialtyId + " ]";
    }
    
}
