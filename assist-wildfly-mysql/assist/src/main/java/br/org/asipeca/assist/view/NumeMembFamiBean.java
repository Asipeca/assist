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

import br.org.asipeca.assist.model.NumeMembFami;

/**
 * Backing bean for NumeMembFami entities.
 * <p/>
 * This class provides CRUD functionality for all NumeMembFami entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class NumeMembFamiBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving NumeMembFami entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private NumeMembFami numeMembFami;

	public NumeMembFami getNumeMembFami() {
		return this.numeMembFami;
	}

	public void setNumeMembFami(NumeMembFami numeMembFami) {
		this.numeMembFami = numeMembFami;
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
			this.numeMembFami = this.example;
		} else {
			this.numeMembFami = findById(getId());
		}
	}

	public NumeMembFami findById(Long id) {

		return this.entityManager.find(NumeMembFami.class, id);
	}

	/*
	 * Support updating and deleting NumeMembFami entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.numeMembFami);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.numeMembFami);
				return "view?faces-redirect=true&id="
						+ this.numeMembFami.getNumeMembFamiId();
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
			NumeMembFami deletableEntity = findById(getId());

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
	 * Support searching NumeMembFami entities with pagination
	 */

	private int page;
	private long count;
	private List<NumeMembFami> pageItems;

	private NumeMembFami example = new NumeMembFami();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public NumeMembFami getExample() {
		return this.example;
	}

	public void setExample(NumeMembFami example) {
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
		Root<NumeMembFami> root = countCriteria.from(NumeMembFami.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<NumeMembFami> criteria = builder
				.createQuery(NumeMembFami.class);
		root = criteria.from(NumeMembFami.class);
		TypedQuery<NumeMembFami> query = this.entityManager
				.createQuery(criteria.select(root).where(
						getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<NumeMembFami> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String dscr = this.example.getDscr();
		if (dscr != null && !"".equals(dscr)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("dscr")),
					'%' + dscr.toLowerCase() + '%'));
		}
		Short limiInfe = this.example.getLimiInfe();
		if (limiInfe != null && limiInfe.intValue() != 0) {
			predicatesList.add(builder.equal(root.get("limiInfe"), limiInfe));
		}
		Short limiSupe = this.example.getLimiSupe();
		if (limiSupe != null && limiSupe.intValue() != 0) {
			predicatesList.add(builder.equal(root.get("limiSupe"), limiSupe));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<NumeMembFami> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back NumeMembFami entities (e.g. from inside
	 * an HtmlSelectOneMenu)
	 */

	public List<NumeMembFami> getAll() {

		CriteriaQuery<NumeMembFami> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(NumeMembFami.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(NumeMembFami.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final NumeMembFamiBean ejbProxy = this.sessionContext
				.getBusinessObject(NumeMembFamiBean.class);

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

				return String.valueOf(((NumeMembFami) value)
						.getNumeMembFamiId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private NumeMembFami add = new NumeMembFami();

	public NumeMembFami getAdd() {
		return this.add;
	}

	public NumeMembFami getAdded() {
		NumeMembFami added = this.add;
		this.add = new NumeMembFami();
		return added;
	}
}
