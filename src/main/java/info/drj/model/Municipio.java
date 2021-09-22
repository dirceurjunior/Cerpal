/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Dirceu Junior
 */
@Entity
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "estado_ibge", nullable = false)
    private Long estadoIbge;

    @NotBlank
    @Column(name = "estado_nome", nullable = false)
    private String estadoNome;

    @Column(name = "mesorregiao_geografica")
    private Long mesorregiaoGeografica;

    @Column(name = "nome_mesorregiao")
    private String nomeMesorregiao;

    @Column(name = "microrregiao_geografica")
    private Long microrregiaoGeografica;

    @Column(name = "nome_microrregiao")
    private String nomeMicrorregiao;

    @NotBlank
    @Column(name = "municipio_codigo", nullable = false)
    private Long municipioCodigo;

    @NotBlank
    @Column(name = "municipio_ibge", nullable = false)
    private Long municipioIbge;

    @NotBlank
    @Column(name = "municipio_nome", nullable = false)
    private String municipioNome;

    public String getMunicipioEstado() {
        return StringUtils.upperCase(municipioNome + " - " + estadoNome);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEstadoIbge() {
        return estadoIbge;
    }

    public void setEstadoIbge(Long estadoIbge) {
        this.estadoIbge = estadoIbge;
    }

    public String getEstadoNome() {
        return estadoNome;
    }

    public void setEstadoNome(String estadoNome) {
        this.estadoNome = estadoNome;
    }

    public Long getMesorregiaoGeografica() {
        return mesorregiaoGeografica;
    }

    public void setMesorregiaoGeografica(Long mesorregiaoGeografica) {
        this.mesorregiaoGeografica = mesorregiaoGeografica;
    }

    public String getNomeMesorregiao() {
        return nomeMesorregiao;
    }

    public void setNomeMesorregiao(String nomeMesorregiao) {
        this.nomeMesorregiao = nomeMesorregiao;
    }

    public Long getMicrorregiaoGeografica() {
        return microrregiaoGeografica;
    }

    public void setMicrorregiaoGeografica(Long microrregiaoGeografica) {
        this.microrregiaoGeografica = microrregiaoGeografica;
    }

    public String getNomeMicrorregiao() {
        return nomeMicrorregiao;
    }

    public void setNomeMicrorregiao(String nomeMicrorregiao) {
        this.nomeMicrorregiao = nomeMicrorregiao;
    }

    public Long getMunicipioCodigo() {
        return municipioCodigo;
    }

    public void setMunicipioCodigo(Long municipioCodigo) {
        this.municipioCodigo = municipioCodigo;
    }

    public Long getMunicipioIbge() {
        return municipioIbge;
    }

    public void setMunicipioIbge(Long municipioIbge) {
        this.municipioIbge = municipioIbge;
    }

    public String getMunicipioNome() {
        return municipioNome;
    }

    public void setMunicipioNome(String municipioNome) {
        this.municipioNome = municipioNome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Municipio other = (Municipio) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
