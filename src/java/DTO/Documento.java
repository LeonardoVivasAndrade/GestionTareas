/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
 * @author Leonardo
 */
@Entity
@Table(name = "documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d")
    , @NamedQuery(name = "Documento.findByIdDocumento", query = "SELECT d FROM Documento d WHERE d.idDocumento = :idDocumento")
    , @NamedQuery(name = "Documento.findByNombre", query = "SELECT d FROM Documento d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Documento.findByVistas", query = "SELECT d FROM Documento d WHERE d.vistas = :vistas")
    , @NamedQuery(name = "Documento.findByFechaReg", query = "SELECT d FROM Documento d WHERE d.fechaReg = :fechaReg")
    , @NamedQuery(name = "Documento.findByScore", query = "SELECT d FROM Documento d WHERE d.score = :score")
    , @NamedQuery(name = "Documento.findByUrl", query = "SELECT d FROM Documento d WHERE d.url = :url")
    , @NamedQuery(name = "Documento.findByIconUrl", query = "SELECT d FROM Documento d WHERE d.iconUrl = :iconUrl")
    , @NamedQuery(name = "Documento.findByEmbedUrl", query = "SELECT d FROM Documento d WHERE d.embedUrl = :embedUrl")
    , @NamedQuery(name = "Documento.findBySizeBytes", query = "SELECT d FROM Documento d WHERE d.sizeBytes = :sizeBytes")
    , @NamedQuery(name = "Documento.findByIdDrive", query = "SELECT d FROM Documento d WHERE d.idDrive = :idDrive")})
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documento")
    private Integer idDocumento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "vistas")
    private int vistas;
    @Basic(optional = false)
    @Column(name = "fecha_reg")
    @Temporal(TemporalType.DATE)
    private Date fechaReg;
    @Basic(optional = false)
    @Column(name = "score")
    private int score;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "iconUrl")
    private String iconUrl;
    @Basic(optional = false)
    @Column(name = "embedUrl")
    private String embedUrl;
    @Basic(optional = false)
    @Column(name = "sizeBytes")
    private String sizeBytes;
    @Basic(optional = false)
    @Column(name = "idDrive")
    private String idDrive;
    @JoinTable(name = "favorito", joinColumns = {
        @JoinColumn(name = "id_documento", referencedColumnName = "id_documento")}, inverseJoinColumns = {
        @JoinColumn(name = "id_usuario", referencedColumnName = "id")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne(optional = false)
    private Materia idMateria;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento")
    private List<Comentario> comentarioList;

    public Documento() {
    }

    public Documento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Documento(Integer idDocumento, String nombre, int vistas, Date fechaReg, int score, String url, String iconUrl, String embedUrl, String sizeBytes, String idDrive) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
        this.vistas = vistas;
        this.fechaReg = fechaReg;
        this.score = score;
        this.url = url;
        this.iconUrl = iconUrl;
        this.embedUrl = embedUrl;
        this.sizeBytes = sizeBytes;
        this.idDrive = idDrive;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVistas() {
        return vistas;
    }

    public void setVistas(int vistas) {
        this.vistas = vistas;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(String sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getIdDrive() {
        return idDrive;
    }

    public void setIdDrive(String idDrive) {
        this.idDrive = idDrive;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Materia getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Materia idMateria) {
        this.idMateria = idMateria;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setComentarioList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumento != null ? idDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.idDocumento == null && other.idDocumento != null) || (this.idDocumento != null && !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Documento[ idDocumento=" + idDocumento + " ]";
    }
    
}
