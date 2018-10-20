package br.org.asipeca.assist.model;
// Generated 20 de out de 2018 18:44:07 by Hibernate Tools 5.2.11.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * AssiInteSaud generated by hbm2java
 */
@Entity
@Table(name = "assi_inte_saud", catalog = "asipeca", uniqueConstraints = @UniqueConstraint(columnNames = {
		"assi_id", "inte_saud_id"}))
public class AssiInteSaud implements java.io.Serializable {

	private Long assiInteSaudId;
	private Assi assi;
	private InteSaud inteSaud;
	private Date criaEm;
	private Long criaPo;
	private Date alteEm;
	private Long altePo;

	public AssiInteSaud() {
	}

	public AssiInteSaud(Assi assi, InteSaud inteSaud) {
		this.assi = assi;
		this.inteSaud = inteSaud;
	}
	public AssiInteSaud(Assi assi, InteSaud inteSaud, Date criaEm, Long criaPo,
			Date alteEm, Long altePo) {
		this.assi = assi;
		this.inteSaud = inteSaud;
		this.criaEm = criaEm;
		this.criaPo = criaPo;
		this.alteEm = alteEm;
		this.altePo = altePo;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "assi_inte_saud_id", unique = true, nullable = false)
	public Long getAssiInteSaudId() {
		return this.assiInteSaudId;
	}

	public void setAssiInteSaudId(Long assiInteSaudId) {
		this.assiInteSaudId = assiInteSaudId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assi_id", nullable = false)
	public Assi getAssi() {
		return this.assi;
	}

	public void setAssi(Assi assi) {
		this.assi = assi;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inte_saud_id", nullable = false)
	public InteSaud getInteSaud() {
		return this.inteSaud;
	}

	public void setInteSaud(InteSaud inteSaud) {
		this.inteSaud = inteSaud;
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

}