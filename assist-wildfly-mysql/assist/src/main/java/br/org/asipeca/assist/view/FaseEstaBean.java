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

import br.org.asipeca.assist.model.FaseEsta;
import br.org.asipeca.assist.model.Assi;
import java.util.Iterator;

/**
 * Backing bean for FaseEsta entities.
 * <p/>
 * This class provides CRUD functionality for all FaseEsta entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class FaseEstaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving FaseEsta entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private FaseEsta faseEsta;

	public FaseEsta getFaseEsta() {
		return this.faseEsta;
	}

	public void setFaseEsta(FaseEsta faseEsta) {
		this.faseEsta = faseEsta;
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
			this.faseEsta = this.example;
		} else {
			this.faseEsta = findById(getId());
		}
	}

	public FaseEsta findById(Long id) {

		return this.entityManager.find(FaseEsta.class, id);
	}

	/*
	 * Support updating and deleting FaseEsta entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.faseEsta);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.faseEsta);
				return "view?faces-redirect=true&id="
						+ this.faseEsta.getFaseEstaId();
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
			FaseEsta deletableEntity = findById(getId());
			Iterator<Assi> iterAssis = deletableEntity.getAssis().iterator();
			for (; iterAssis.hasNext();) {
				Assi nextInAssis = iterAssis.next();
				nextInAssis.setFaseEsta(null);
				iterAssis.remove();
				this.entityManager.merge(nextInAssis);
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
	 * Support searching FaseEsta entities with pagination
	 */

	private int page;
	private long count;
	private List<FaseEsta> pageItems;

	private FaseEsta example = new FaseEsta();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public FaseEsta getExample() {
		return this.example;
	}

	public void setExample(FaseEsta example) {
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
		Root<FaseEsta> root = countCriteria.from(FaseEsta.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<FaseEsta> criteria = builder.createQuery(FaseEsta.class);
		root = criteria.from(FaseEsta.class);
		TypedQuery<FaseEsta> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<FaseEsta> root) {

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

	public List<FaseEsta> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back FaseEsta entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<FaseEsta> getAll() {

		CriteriaQuery<FaseEsta> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(FaseEsta.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(FaseEsta.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final FaseEstaBean ejbProxy = this.sessionContext
				.getBusinessObject(FaseEstaBean.class);

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

				return String.valueOf(((FaseEsta) value).getFaseEstaId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private FaseEsta add = new FaseEsta();

	public FaseEsta getAdd() {
		return this.add;
	}

	public FaseEsta getAdded() {
		FaseEsta added = this.add;
		this.add = new FaseEsta();
		return added;
	}
}
