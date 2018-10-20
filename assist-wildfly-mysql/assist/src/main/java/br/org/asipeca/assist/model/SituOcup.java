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
 * SituOcup generated by hbm2java
 */
@Entity
@Table(name = "situ_ocup", catalog = "asipeca")
public class SituOcup implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1995387953931755199L;
	private Long situOcupId;
	private String dscr;
	private Date criaEm;
	private Long criaPo;
	private Date alteEm;
	private Long altePo;
	private Set<Assi> assis = new HashSet<Assi>(0);

	public SituOcup() {
	}

	public SituOcup(String dscr, Date criaEm, Long criaPo, Date alteEm, Long altePo, Set<Assi> assis) {
		this.dscr = dscr;
		this.criaEm = criaEm;
		this.criaPo = criaPo;
		this.alteEm = alteEm;
		this.altePo = altePo;
		this.assis = assis;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "situ_ocup_id", unique = true, nullable = false)
	public Long getSituOcupId() {
		return this.situOcupId;
	}

	public void setSituOcupId(Long situOcupId) {
		this.situOcupId = situOcupId;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "situOcup")
	public Set<Assi> getAssis() {
		return this.assis;
	}

	public void setAssis(Set<Assi> assis) {
		this.assis = assis;
	}

}
