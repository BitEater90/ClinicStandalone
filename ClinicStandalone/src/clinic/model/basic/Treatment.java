/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinic.model.basic;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prezes
 */
@Entity
@Table(name = "treatments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Treatment.findAll", query = "SELECT t FROM Treatment t")
    , @NamedQuery(name = "Treatment.findByTreatmentId", query = "SELECT t FROM Treatment t WHERE t.treatmentId = :treatmentId")
    , @NamedQuery(name = "Treatment.findByDiagnosisDate", query = "SELECT t FROM Treatment t WHERE t.diagnosisDate = :diagnosisDate")
    , @NamedQuery(name = "Treatment.findByTreatmentFinishDate", query = "SELECT t FROM Treatment t WHERE t.treatmentFinishDate = :treatmentFinishDate")
    , @NamedQuery(name = "Treatment.findByIsIllnessCured", query = "SELECT t FROM Treatment t WHERE t.isIllnessCured = :isIllnessCured")
    , @NamedQuery(name = "Treatment.findByVisitsNumber", query = "SELECT t FROM Treatment t WHERE t.visitsNumber = :visitsNumber")})
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TreatmentId")
    private Integer treatmentId;
    @Basic(optional = false)
    @Column(name = "DiagnosisDate")
    @Temporal(TemporalType.DATE)
    private Date diagnosisDate;
    @Basic(optional = false)
    @Column(name = "TreatmentFinishDate")
    @Temporal(TemporalType.DATE)
    private Date treatmentFinishDate;
    @Basic(optional = false)
    @Column(name = "IsIllnessCured")
    private boolean isIllnessCured;
    @Basic(optional = false)
    @Column(name = "VisitsNumber")
    private short visitsNumber;
    @JoinColumn(name = "DoctorId", referencedColumnName = "DoctorId")
    @ManyToOne
    private Doctor doctorId;
    @JoinColumn(name = "IllnessId", referencedColumnName = "IllnessId")
    @ManyToOne(optional = false)
    private Illness illnessId;
    @JoinColumn(name = "PatientId", referencedColumnName = "PatientId")
    @ManyToOne(optional = false)
    private Patient patientId;

    public Treatment() {
    }

    public Treatment(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Treatment(Integer treatmentId, Date diagnosisDate, Date treatmentFinishDate, boolean isIllnessCured, short visitsNumber) {
        this.treatmentId = treatmentId;
        this.diagnosisDate = diagnosisDate;
        this.treatmentFinishDate = treatmentFinishDate;
        this.isIllnessCured = isIllnessCured;
        this.visitsNumber = visitsNumber;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public Date getTreatmentFinishDate() {
        return treatmentFinishDate;
    }

    public void setTreatmentFinishDate(Date treatmentFinishDate) {
        this.treatmentFinishDate = treatmentFinishDate;
    }

    public boolean getIsIllnessCured() {
        return isIllnessCured;
    }

    public void setIsIllnessCured(boolean isIllnessCured) {
        this.isIllnessCured = isIllnessCured;
    }

    public short getVisitsNumber() {
        return visitsNumber;
    }

    public void setVisitsNumber(short visitsNumber) {
        this.visitsNumber = visitsNumber;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
    }

    public Illness getIllnessId() {
        return illnessId;
    }

    public void setIllnessId(Illness illnessId) {
        this.illnessId = illnessId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treatmentId != null ? treatmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treatment)) {
            return false;
        }
        Treatment other = (Treatment) object;
        if ((this.treatmentId == null && other.treatmentId != null) || (this.treatmentId != null && !this.treatmentId.equals(other.treatmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clinic.model.basic.Treatment[ treatmentId=" + treatmentId + " ]";
    }
    
}
