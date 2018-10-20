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

import br.org.asipeca.assist.model.Idad;

/**
 * Backing bean for Idad entities.
 * <p/>
 * This class provides CRUD functionality for all Idad entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class IdadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Idad entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Idad idad;

	public Idad getIdad() {
		return this.idad;
	}

	public void setIdad(Idad idad) {
		this.idad = idad;
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
			this.idad = this.example;
		} else {
			this.idad = findById(getId());
		}
	}

	public Idad findById(Long id) {

		return this.entityManager.find(Idad.class, id);
	}

	/*
	 * Support updating and deleting Idad entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.idad);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.idad);
				return "view?faces-redirect=true&id=" + this.idad.getIdadId();
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
			Idad deletableEntity = findById(getId());

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
	 * Support searching Idad entities with pagination
	 */

	private int page;
	private long count;
	private List<Idad> pageItems;

	private Idad example = new Idad();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Idad getExample() {
		return this.example;
	}

	public void setExample(Idad example) {
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
		Root<Idad> root = countCriteria.from(Idad.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Idad> criteria = builder.createQuery(Idad.class);
		root = criteria.from(Idad.class);
		TypedQuery<Idad> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Idad> root) {

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

	public List<Idad> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Idad entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Idad> getAll() {

		CriteriaQuery<Idad> criteria = this.entityManager.getCriteriaBuilder()
				.createQuery(Idad.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Idad.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final IdadBean ejbProxy = this.sessionContext
				.getBusinessObject(IdadBean.class);

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

				return String.valueOf(((Idad) value).getIdadId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Idad add = new Idad();

	public Idad getAdd() {
		return this.add;
	}

	public Idad getAdded() {
		Idad added = this.add;
		this.add = new Idad();
		return added;
	}
}
