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
 * HistFami generated by hbm2java
 */
@Entity
@Table(name = "hist_fami", catalog = "asipeca")
public class HistFami implements java.io.Serializable {

	private static final long serialVersionUID = 5301765014372996107L;
	private Long histFamiId;
	private String dscr;
	private Date criaEm;
	private Long criaPo;
	private Date alteEm;
	private Long altePo;
	private Set<AssiHistFami> assiHistFamis = new HashSet<AssiHistFami>(0);

	public HistFami() {
	}

	public HistFami(String dscr, Date criaEm, Long criaPo, Date alteEm, Long altePo, Set<AssiHistFami> assiHistFamis) {
		this.dscr = dscr;
		this.criaEm = criaEm;
		this.criaPo = criaPo;
		this.alteEm = alteEm;
		this.altePo = altePo;
		this.assiHistFamis = assiHistFamis;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "hist_fami_id", unique = true, nullable = false)
	public Long getHistFamiId() {
		return this.histFamiId;
	}

	public void setHistFamiId(Long histFamiId) {
		this.histFamiId = histFamiId;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "histFami")
	public Set<AssiHistFami> getAssiHistFamis() {
		return this.assiHistFamis;
	}

	public void setAssiHistFamis(Set<AssiHistFami> assiHistFamis) {
		this.assiHistFamis = assiHistFamis;
	}

}
