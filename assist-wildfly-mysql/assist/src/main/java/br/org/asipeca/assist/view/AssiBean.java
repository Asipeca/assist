package br.org.asipeca.assist.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.org.asipeca.assist.model.Assi;
import br.org.asipeca.assist.model.AssiHabiSaud;
import br.org.asipeca.assist.model.AssiHistFami;
import br.org.asipeca.assist.model.AssiIdenAten;
import br.org.asipeca.assist.model.AssiIdenInte;
import br.org.asipeca.assist.model.AssiInteSaud;
import br.org.asipeca.assist.model.AssiPatoAsso;
import br.org.asipeca.assist.model.AssiSituSaud;
import br.org.asipeca.assist.model.AssiTipoCanc;
import br.org.asipeca.assist.model.AssiVincRedeApoi;
import br.org.asipeca.assist.model.Esco;
import br.org.asipeca.assist.model.FaseEsta;
import br.org.asipeca.assist.model.RendMens;
import br.org.asipeca.assist.model.SituMora;
import br.org.asipeca.assist.model.SituOcup;
import br.org.asipeca.assist.model.SituSaud;
import java.util.Iterator;

/**
 * Backing bean for Assi entities.
 * <p/>
 * This class provides CRUD functionality for all Assi entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class AssiBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Assi entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Assi assi;

	public Assi getAssi() {
		return this.assi;
	}

	public void setAssi(Assi assi) {
		this.assi = assi;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "assist-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);
		return "create?faces-redirect=true";
	}

	public void retrieve() {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}

		if (this.id == null) {
			this.assi = this.example;
		} else {
			this.assi = findById(getId());
		}
	}

	public Assi findById(Long id) {

		return this.entityManager.find(Assi.class, id);
	}

	/*
	 * Support updating and deleting Assi entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.assi);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.assi);
				return "view?faces-redirect=true&id=" + this.assi.getAssiId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			Assi deletableEntity = findById(getId());
			Esco esco = deletableEntity.getEsco();
			esco.getAssis().remove(deletableEntity);
			deletableEntity.setEsco(null);
			this.entityManager.merge(esco);
			FaseEsta faseEsta = deletableEntity.getFaseEsta();
			faseEsta.getAssis().remove(deletableEntity);
			deletableEntity.setFaseEsta(null);
			this.entityManager.merge(faseEsta);
			RendMens rendMens = deletableEntity.getRendMens();
			rendMens.getAssis().remove(deletableEntity);
			deletableEntity.setRendMens(null);
			this.entityManager.merge(rendMens);
			SituMora situMora = deletableEntity.getSituMora();
			situMora.getAssis().remove(deletableEntity);
			deletableEntity.setSituMora(null);
			this.entityManager.merge(situMora);
			SituOcup situOcup = deletableEntity.getSituOcup();
			situOcup.getAssis().remove(deletableEntity);
			deletableEntity.setSituOcup(null);
			this.entityManager.merge(situOcup);
			SituSaud situSaud = deletableEntity.getSituSaud();
			situSaud.getAssis().remove(deletableEntity);
			deletableEntity.setSituSaud(null);
			this.entityManager.merge(situSaud);
			Iterator<AssiVincRedeApoi> iterAssiVincRedeApois = deletableEntity
					.getAssiVincRedeApois().iterator();
			for (; iterAssiVincRedeApois.hasNext();) {
				AssiVincRedeApoi nextInAssiVincRedeApois = iterAssiVincRedeApois
						.next();
				nextInAssiVincRedeApois.setAssi(null);
				iterAssiVincRedeApois.remove();
				this.entityManager.merge(nextInAssiVincRedeApois);
			}
			Iterator<AssiIdenInte> iterAssiIdenIntes = deletableEntity
					.getAssiIdenIntes().iterator();
			for (; iterAssiIdenIntes.hasNext();) {
				AssiIdenInte nextInAssiIdenIntes = iterAssiIdenIntes.next();
				nextInAssiIdenIntes.setAssi(null);
				iterAssiIdenIntes.remove();
				this.entityManager.merge(nextInAssiIdenIntes);
			}
			Iterator<AssiInteSaud> iterAssiInteSauds = deletableEntity
					.getAssiInteSauds().iterator();
			for (; iterAssiInteSauds.hasNext();) {
				AssiInteSaud nextInAssiInteSauds = iterAssiInteSauds.next();
				nextInAssiInteSauds.setAssi(null);
				iterAssiInteSauds.remove();
				this.entityManager.merge(nextInAssiInteSauds);
			}
			Iterator<AssiPatoAsso> iterAssiPatoAssos = deletableEntity
					.getAssiPatoAssos().iterator();
			for (; iterAssiPatoAssos.hasNext();) {
				AssiPatoAsso nextInAssiPatoAssos = iterAssiPatoAssos.next();
				nextInAssiPatoAssos.setAssi(null);
				iterAssiPatoAssos.remove();
				this.entityManager.merge(nextInAssiPatoAssos);
			}
			Iterator<AssiHabiSaud> iterAssiHabiSauds = deletableEntity
					.getAssiHabiSauds().iterator();
			for (; iterAssiHabiSauds.hasNext();) {
				AssiHabiSaud nextInAssiHabiSauds = iterAssiHabiSauds.next();
				nextInAssiHabiSauds.setAssi(null);
				iterAssiHabiSauds.remove();
				this.entityManager.merge(nextInAssiHabiSauds);
			}
			Iterator<AssiIdenAten> iterAssiIdenAtens = deletableEntity
					.getAssiIdenAtens().iterator();
			for (; iterAssiIdenAtens.hasNext();) {
				AssiIdenAten nextInAssiIdenAtens = iterAssiIdenAtens.next();
				nextInAssiIdenAtens.setAssi(null);
				iterAssiIdenAtens.remove();
				this.entityManager.merge(nextInAssiIdenAtens);
			}
			Iterator<AssiSituSaud> iterAssiSituSauds = deletableEntity
					.getAssiSituSauds().iterator();
			for (; iterAssiSituSauds.hasNext();) {
				AssiSituSaud nextInAssiSituSauds = iterAssiSituSauds.next();
				nextInAssiSituSauds.setAssi(null);
				iterAssiSituSauds.remove();
				this.entityManager.merge(nextInAssiSituSauds);
			}
			Iterator<AssiHistFami> iterAssiHistFamis = deletableEntity
					.getAssiHistFamis().iterator();
			for (; iterAssiHistFamis.hasNext();) {
				AssiHistFami nextInAssiHistFamis = iterAssiHistFamis.next();
				nextInAssiHistFamis.setAssi(null);
				iterAssiHistFamis.remove();
				this.entityManager.merge(nextInAssiHistFamis);
			}
			Iterator<AssiTipoCanc> iterAssiTipoCancs = deletableEntity
					.getAssiTipoCancs().iterator();
			for (; iterAssiTipoCancs.hasNext();) {
				AssiTipoCanc nextInAssiTipoCancs = iterAssiTipoCancs.next();
				nextInAssiTipoCancs.setAssi(null);
				iterAssiTipoCancs.remove();
				this.entityManager.merge(nextInAssiTipoCancs);
			}
			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching Assi entities with pagination
	 */

	private int page;
	private long count;
	private List<Assi> pageItems;

	private Assi example = new Assi();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Assi getExample() {
		return this.example;
	}

	public void setExample(Assi example) {
		this.example = example;
	}

	public String search() {
		this.page = 0;
		return null;
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Assi> root = countCriteria.from(Assi.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Assi> criteria = builder.createQuery(Assi.class);
		root = criteria.from(Assi.class);
		TypedQuery<Assi> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Assi> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		Esco esco = this.example.getEsco();
		if (esco != null) {
			predicatesList.add(builder.equal(root.get("esco"), esco));
		}
		FaseEsta faseEsta = this.example.getFaseEsta();
		if (faseEsta != null) {
			predicatesList.add(builder.equal(root.get("faseEsta"), faseEsta));
		}
		RendMens rendMens = this.example.getRendMens();
		if (rendMens != null) {
			predicatesList.add(builder.equal(root.get("rendMens"), rendMens));
		}
		SituMora situMora = this.example.getSituMora();
		if (situMora != null) {
			predicatesList.add(builder.equal(root.get("situMora"), situMora));
		}
		SituOcup situOcup = this.example.getSituOcup();
		if (situOcup != null) {
			predicatesList.add(builder.equal(root.get("situOcup"), situOcup));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Assi> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Assi entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Assi> getAll() {

		CriteriaQuery<Assi> criteria = this.entityManager.getCriteriaBuilder()
				.createQuery(Assi.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Assi.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final AssiBean ejbProxy = this.sessionContext
				.getBusinessObject(AssiBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((Assi) value).getAssiId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Assi add = new Assi();

	public Assi getAdd() {
		return this.add;
	}

	public Assi getAdded() {
		Assi added = this.add;
		this.add = new Assi();
		return added;
	}
}
