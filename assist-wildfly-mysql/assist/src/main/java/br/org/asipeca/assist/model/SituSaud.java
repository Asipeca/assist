package br.org.asipeca.assist.model;
// Generated 20 de out de 2018 18:44:07 by Hibernate Tools 5.2.11.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SituSaud generated by hbm2java
 */
@Entity
@Table(name = "situ_saud", catalog = "asipeca")
public class SituSaud implements java.io.Serializable {

	private Long situSaudId;
	private String dscr;
	private Date criaEm;
	private Long criaPo;
	private Date alteEm;
	private Long altePo;
	private Set<AssiSituSaud> assiSituSauds = new HashSet<AssiSituSaud>(0);
	private Set<Assi> assis = new HashSet<Assi>(0);

	public SituSaud() {
	}

	public SituSaud(String dscr, Date criaEm, Long criaPo, Date alteEm,
			Long altePo, Set<AssiSituSaud> assiSituSauds, Set<Assi> assis) {
		this.dscr = dscr;
		this.criaEm = criaEm;
		this.criaPo = criaPo;
		this.alteEm = alteEm;
		this.altePo = altePo;
		this.assiSituSauds = assiSituSauds;
		this.assis = assis;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "situ_saud_id", unique = true, nullable = false)
	public Long getSituSaudId() {
		return this.situSaudId;
	}

	public void setSituSaudId(Long situSaudId) {
		this.situSaudId = situSaudId;
	}

	@Column(name = "dscr", length = 30)
	public String getDscr() {
		return this.dscr;
	}

	public void setDscr(String dscr) {
		this.dscr = dscr;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cria_em", length = 26)
	public Date getCriaEm() {
		return this.criaEm;
	}

	public void setCriaEm(Date criaEm) {
		this.criaEm = criaEm;
	}

	@Column(name = "cria_po")
	public Long getCriaPo() {
		return this.criaPo;
	}

	public void setCriaPo(Long criaPo) {
		this.criaPo = criaPo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "alte_em", length = 26)
	public Date getAlteEm() {
		return this.alteEm;
	}

	public void setAlteEm(Date alteEm) {
		this.alteEm = alteEm;
	}

	@Column(name = "alte_po")
	public Long getAltePo() {
		return this.altePo;
	}

	public void setAltePo(Long altePo) {
		this.altePo = altePo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "situSaud")
	public Set<AssiSituSaud> getAssiSituSauds() {
		return this.assiSituSauds;
	}

	public void setAssiSituSauds(Set<AssiSituSaud> assiSituSauds) {
		this.assiSituSauds = assiSituSauds;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "situSaud")
	public Set<Assi> getAssis() {
		return this.assis;
	}

	public void setAssis(Set<Assi> assis) {
		this.assis = assis;
	}

}