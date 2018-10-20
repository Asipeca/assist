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

import br.org.asipeca.assist.model.IdenInte;
import br.org.asipeca.assist.model.AssiIdenInte;
import java.util.Iterator;

/**
 * Backing bean for IdenInte entities.
 * <p/>
 * This class provides CRUD functionality for all IdenInte entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class IdenInteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving IdenInte entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private IdenInte idenInte;

	public IdenInte getIdenInte() {
		return this.idenInte;
	}

	public void setIdenInte(IdenInte idenInte) {
		this.idenInte = idenInte;
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
			this.idenInte = this.example;
		} else {
			this.idenInte = findById(getId());
		}
	}

	public IdenInte findById(Long id) {

		return this.entityManager.find(IdenInte.class, id);
	}

	/*
	 * Support updating and deleting IdenInte entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.idenInte);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.idenInte);
				return "view?faces-redirect=true&id="
						+ this.idenInte.getIdenInteId();
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
			IdenInte deletableEntity = findById(getId());
			Iterator<AssiIdenInte> iterAssiIdenIntes = deletableEntity
					.getAssiIdenIntes().iterator();
			for (; iterAssiIdenIntes.hasNext();) {
				AssiIdenInte nextInAssiIdenIntes = iterAssiIdenIntes.next();
				nextInAssiIdenIntes.setIdenInte(null);
				iterAssiIdenIntes.remove();
				this.entityManager.merge(nextInAssiIdenIntes);
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
	 * Support searching IdenInte entities with pagination
	 */

	private int page;
	private long count;
	private List<IdenInte> pageItems;

	private IdenInte example = new IdenInte();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public IdenInte getExample() {
		return this.example;
	}

	public void setExample(IdenInte example) {
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
		Root<IdenInte> root = countCriteria.from(IdenInte.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<IdenInte> criteria = builder.createQuery(IdenInte.class);
		root = criteria.from(IdenInte.class);
		TypedQuery<IdenInte> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<IdenInte> root) {

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

	public List<IdenInte> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back IdenInte entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<IdenInte> getAll() {

		CriteriaQuery<IdenInte> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(IdenInte.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(IdenInte.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final IdenInteBean ejbProxy = this.sessionContext
				.getBusinessObject(IdenInteBean.class);

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

				return String.valueOf(((IdenInte) value).getIdenInteId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private IdenInte add = new IdenInte();

	public IdenInte getAdd() {
		return this.add;
	}

	public IdenInte getAdded() {
		IdenInte added = this.add;
		this.add = new IdenInte();
		return added;
	}
}
