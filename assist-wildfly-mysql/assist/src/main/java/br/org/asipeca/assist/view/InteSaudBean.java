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

import br.org.asipeca.assist.model.InteSaud;
import br.org.asipeca.assist.model.AssiInteSaud;
import java.util.Iterator;

/**
 * Backing bean for InteSaud entities.
 * <p/>
 * This class provides CRUD functionality for all InteSaud entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class InteSaudBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving InteSaud entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private InteSaud inteSaud;

	public InteSaud getInteSaud() {
		return this.inteSaud;
	}

	public void setInteSaud(InteSaud inteSaud) {
		this.inteSaud = inteSaud;
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
			this.inteSaud = this.example;
		} else {
			this.inteSaud = findById(getId());
		}
	}

	public InteSaud findById(Long id) {

		return this.entityManager.find(InteSaud.class, id);
	}

	/*
	 * Support updating and deleting InteSaud entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.inteSaud);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.inteSaud);
				return "view?faces-redirect=true&id="
						+ this.inteSaud.getInteSaudId();
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
			InteSaud deletableEntity = findById(getId());
			Iterator<AssiInteSaud> iterAssiInteSauds = deletableEntity
					.getAssiInteSauds().iterator();
			for (; iterAssiInteSauds.hasNext();) {
				AssiInteSaud nextInAssiInteSauds = iterAssiInteSauds.next();
				nextInAssiInteSauds.setInteSaud(null);
				iterAssiInteSauds.remove();
				this.entityManager.merge(nextInAssiInteSauds);
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
	 * Support searching InteSaud entities with pagination
	 */

	private int page;
	private long count;
	private List<InteSaud> pageItems;

	private InteSaud example = new InteSaud();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public InteSaud getExample() {
		return this.example;
	}

	public void setExample(InteSaud example) {
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
		Root<InteSaud> root = countCriteria.from(InteSaud.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<InteSaud> criteria = builder.createQuery(InteSaud.class);
		root = criteria.from(InteSaud.class);
		TypedQuery<InteSaud> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<InteSaud> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String dscr = this.example.getDscr();
		if (dscr != null && !"".equals(dscr)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("dscr")),
					'%' + dscr.toLowerCase() + '%'));
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

	public List<InteSaud> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back InteSaud entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<InteSaud> getAll() {

		CriteriaQuery<InteSaud> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(InteSaud.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(InteSaud.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final InteSaudBean ejbProxy = this.sessionContext
				.getBusinessObject(InteSaudBean.class);

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

				return String.valueOf(((InteSaud) value).getInteSaudId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private InteSaud add = new InteSaud();

	public InteSaud getAdd() {
		return this.add;
	}

	public InteSaud getAdded() {
		InteSaud added = this.add;
		this.add = new InteSaud();
		return added;
	}
}
