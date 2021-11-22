/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.model.basic;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prezes
 */
@Entity
@Table(name = "patients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
    , @NamedQuery(name = "Patient.findPatientByDoctorId", query = "SELECT p FROM Patient p WHERE p.doctorId.doctorId = :doctorId")
    , @NamedQuery(name = "Patient.findByPatientId", query = "SELECT p FROM Patient p WHERE p.patientId = :patientId")
    , @NamedQuery(name = "Patient.findByFirstName", query = "SELECT p FROM Patient p WHERE p.firstName = :firstName")
    , @NamedQuery(name = "Patient.findByLastName", query = "SELECT p FROM Patient p WHERE p.lastName = :lastName")
    , @NamedQuery(name = "Patient.findByFirstAndLastName", query = "SELECT p FROM Patient p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    , @NamedQuery(name = "Patient.findByRegistrationDate", query = "SELECT p FROM Patient p WHERE p.registrationDate = :registrationDate")
    , @NamedQuery(name = "Patient.findByLastVisitDate", query = "SELECT p FROM Patient p WHERE p.lastVisitDate = :lastVisitDate")
    , @NamedQuery(name = "Patient.findByVisitsNumber", query = "SELECT p FROM Patient p WHERE p.visitsNumber = :visitsNumber")
    , @NamedQuery(name = "Patient.findByPesel", query = "SELECT p FROM Patient p WHERE p.pesel = :pesel")
    , @NamedQuery(name = "Patient.findByHasInsurance", query = "SELECT p FROM Patient p WHERE p.hasInsurance = :hasInsurance")})
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PatientId")
    private Integer patientId;
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "RegistrationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;
    @Basic(optional = false)
    @Column(name = "LastVisitDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitDate;
    @Basic(optional = false)
    @Column(name = "VisitsNumber")
    private short visitsNumber;
    @Basic(optional = false)
    @Column(name = "PESEL")
    private String pesel;
    @Basic(optional = false)
    @Column(name = "HasInsurance")
    private boolean hasInsurance;
    @JoinColumn(name = "DoctorId", referencedColumnName = "DoctorId")
    @ManyToOne(optional = false)
    private Doctor doctorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId")
    private List<Treatment> treatmentList;

    public Patient() {
    }

    public Patient(Integer patientId) {
        this.patientId = patientId;
    }

    public Patient(Integer patientId, String firstName, String lastName, Date registrationDate, Date lastVisitDate, short visitsNumber, String pesel, boolean hasInsurance) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.lastVisitDate = lastVisitDate;
        this.visitsNumber = visitsNumber;
        this.pesel = pesel;
        this.hasInsurance = hasInsurance;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public short getVisitsNumber() {
        return visitsNumber;
    }

    public void setVisitsNumber(short visitsNumber) {
        this.visitsNumber = visitsNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public boolean getHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
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
        hash += (patientId != null ? patientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clinic.model.basic.Patient[ patientId=" + patientId + " ]";
    }
    
}
