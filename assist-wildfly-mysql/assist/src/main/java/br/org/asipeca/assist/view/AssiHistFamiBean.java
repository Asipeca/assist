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

import br.org.asipeca.assist.model.AssiHistFami;
import br.org.asipeca.assist.model.Assi;
import br.org.asipeca.assist.model.HistFami;

/**
 * Backing bean for AssiHistFami entities.
 * <p/>
 * This class provides CRUD functionality for all AssiHistFami entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class AssiHistFamiBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving AssiHistFami entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private AssiHistFami assiHistFami;

	public AssiHistFami getAssiHistFami() {
		return this.assiHistFami;
	}

	public void setAssiHistFami(AssiHistFami assiHistFami) {
		this.assiHistFami = assiHistFami;
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
			this.assiHistFami = this.example;
		} else {
			this.assiHistFami = findById(getId());
		}
	}

	public AssiHistFami findById(Long id) {

		return this.entityManager.find(AssiHistFami.class, id);
	}

	/*
	 * Support updating and deleting AssiHistFami entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.assiHistFami);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.assiHistFami);
				return "view?faces-redirect=true&id="
						+ this.assiHistFami.getAssiHistFamiId();
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
			AssiHistFami deletableEntity = findById(getId());
			Assi assi = deletableEntity.getAssi();
			assi.getAssiHistFamis().remove(deletableEntity);
			deletableEntity.setAssi(null);
			this.entityManager.merge(assi);
			HistFami histFami = deletableEntity.getHistFami();
			histFami.getAssiHistFamis().remove(deletableEntity);
			deletableEntity.setHistFami(null);
			this.entityManager.merge(histFami);
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
	 * Support searching AssiHistFami entities with pagination
	 */

	private int page;
	private long count;
	private List<AssiHistFami> pageItems;

	private AssiHistFami example = new AssiHistFami();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public AssiHistFami getExample() {
		return this.example;
	}

	public void setExample(AssiHistFami example) {
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
		Root<AssiHistFami> root = countCriteria.from(AssiHistFami.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<AssiHistFami> criteria = builder
				.createQuery(AssiHistFami.class);
		root = criteria.from(AssiHistFami.class);
		TypedQuery<AssiHistFami> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<AssiHistFami> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		Assi assi = this.example.getAssi();
		if (assi != null) {
			predicatesList.add(builder.equal(root.get("assi"), assi));
		}
		HistFami histFami = this.example.getHistFami();
		if (histFami != null) {
			predicatesList.add(builder.equal(root.get("histFami"), histFami));
		}
		Long criaPo = this.example.getCriaPo();
		if (criaPo != null && criaPo.intValue() != 0) {
			predicatesList.add(builder.equal(root.get("criaPo"), criaPo));
		}
		Long altePo = this.example.getAltePo();
		if (altePo != null && altePo.intValue() != 0) {
			predicatesList.add(builder.equal(root.get("altePo"), altePo));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<AssiHistFami> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back AssiHistFami entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<AssiHistFami> getAll() {

		CriteriaQuery<AssiHistFami> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(AssiHistFami.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(AssiHistFami.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final AssiHistFamiBean ejbProxy = this.sessionContext
				.getBusinessObject(AssiHistFamiBean.class);

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

				return String.valueOf(((AssiHistFami) value)
						.getAssiHistFamiId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private AssiHistFami add = new AssiHistFami();

	public AssiHistFami getAdd() {
		return this.add;
	}

	public AssiHistFami getAdded() {
		AssiHistFami added = this.add;
		this.add = new AssiHistFami();
		return added;
	}
}
