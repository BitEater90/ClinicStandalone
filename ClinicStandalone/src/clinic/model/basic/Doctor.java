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
@Table(name = "doctors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d")
    , @NamedQuery(name = "Doctor.findBySpecialtyName", query = "SELECT d FROM Doctor d JOIN Specialty s WHERE (d.specialtyId.specialtyId = s.specialtyId) AND (s.specialtyName = :specialtyName)")
    , @NamedQuery(name = "Doctor.findByDoctorId", query = "SELECT d FROM Doctor d WHERE d.doctorId = :doctorId")
    , @NamedQuery(name = "Doctor.findByFirstName", query = "SELECT d FROM Doctor d WHERE d.firstName = :firstName")
    , @NamedQuery(name = "Doctor.findByLastName", query = "SELECT d FROM Doctor d WHERE d.lastName = :lastName")
    , @NamedQuery(name = "Doctor.findByFirstAndLastName", query = "SELECT d FROM Doctor d WHERE (d.firstName = :firstName) AND (d.lastName = :lastName)")
    , @NamedQuery(name = "Doctor.findByEmployedSinceDate", query = "SELECT d FROM Doctor d WHERE d.employedSinceDate = :employedSinceDate")})
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DoctorId")
    private Short doctorId;
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "EmployedSinceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date employedSinceDate;
    @Basic(optional = false)
    @Column(name = "ProfessionalTitle")
    private String professionalTitle;
    @JoinColumn(name = "SpecialtyId", referencedColumnName = "SpecialtyId")
    @ManyToOne(optional = false)
    private Specialty specialtyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorId")
    private List<Patient> patientList;
    @OneToMany(mappedBy = "doctorId")
    private List<Treatment> treatmentList;

    public Doctor() {
    }

    public Doctor(Short doctorId) {
        this.doctorId = doctorId;
    }

    public Doctor(Short doctorId, String firstName, String lastName, Date employedSinceDate, String professionalTitle) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employedSinceDate = employedSinceDate;
        this.professionalTitle = professionalTitle;
    }

    public Short getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Short doctorId) {
        this.doctorId = doctorId;
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

    public Date getEmployedSinceDate() {
        return employedSinceDate;
    }

    public void setEmployedSinceDate(Date employedSinceDate) {
        this.employedSinceDate = employedSinceDate;
    }
    
    public String getProfessionalTitle()
    {
        return professionalTitle;
    }
    
    public void setProfessionalTitle(String professionalTitle)
    {
        this.professionalTitle = professionalTitle;
    }

    public Specialty getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Specialty specialtyId) {
        this.specialtyId = specialtyId;
    }

    @XmlTransient
    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
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
        hash += (doctorId != null ? doctorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doctor)) {
            return false;
        }
        Doctor other = (Doctor) object;
        if ((this.doctorId == null && other.doctorId != null) || (this.doctorId != null && !this.doctorId.equals(other.doctorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clinic.model.basic.Doctor[ doctorId=" + doctorId + " ]";
    }
    
}
