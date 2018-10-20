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

import br.org.asipeca.assist.model.SituOcup;
import br.org.asipeca.assist.model.Assi;
import java.util.Iterator;

/**
 * Backing bean for SituOcup entities.
 * <p/>
 * This class provides CRUD functionality for all SituOcup entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class SituOcupBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving SituOcup entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private SituOcup situOcup;

	public SituOcup getSituOcup() {
		return this.situOcup;
	}

	public void setSituOcup(SituOcup situOcup) {
		this.situOcup = situOcup;
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
			this.situOcup = this.example;
		} else {
			this.situOcup = findById(getId());
		}
	}

	public SituOcup findById(Long id) {

		return this.entityManager.find(SituOcup.class, id);
	}

	/*
	 * Support updating and deleting SituOcup entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.situOcup);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.situOcup);
				return "view?faces-redirect=true&id="
						+ this.situOcup.getSituOcupId();
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
			SituOcup deletableEntity = findById(getId());
			Iterator<Assi> iterAssis = deletableEntity.getAssis().iterator();
			for (; iterAssis.hasNext();) {
				Assi nextInAssis = iterAssis.next();
				nextInAssis.setSituOcup(null);
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
	 * Support searching SituOcup entities with pagination
	 */

	private int page;
	private long count;
	private List<SituOcup> pageItems;

	private SituOcup example = new SituOcup();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public SituOcup getExample() {
		return this.example;
	}

	public void setExample(SituOcup example) {
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
		Root<SituOcup> root = countCriteria.from(SituOcup.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<SituOcup> criteria = builder.createQuery(SituOcup.class);
		root = criteria.from(SituOcup.class);
		TypedQuery<SituOcup> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<SituOcup> root) {

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

	public List<SituOcup> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back SituOcup entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<SituOcup> getAll() {

		CriteriaQuery<SituOcup> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(SituOcup.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(SituOcup.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final SituOcupBean ejbProxy = this.sessionContext
				.getBusinessObject(SituOcupBean.class);

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

				return String.valueOf(((SituOcup) value).getSituOcupId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private SituOcup add = new SituOcup();

	public SituOcup getAdd() {
		return this.add;
	}

	public SituOcup getAdded() {
		SituOcup added = this.add;
		this.add = new SituOcup();
		return added;
	}
}
