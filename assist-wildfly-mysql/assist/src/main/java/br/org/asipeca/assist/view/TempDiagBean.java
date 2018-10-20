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

import br.org.asipeca.assist.model.TempDiag;

/**
 * Backing bean for TempDiag entities.
 * <p/>
 * This class provides CRUD functionality for all TempDiag entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TempDiagBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving TempDiag entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private TempDiag tempDiag;

	public TempDiag getTempDiag() {
		return this.tempDiag;
	}

	public void setTempDiag(TempDiag tempDiag) {
		this.tempDiag = tempDiag;
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
			this.tempDiag = this.example;
		} else {
			this.tempDiag = findById(getId());
		}
	}

	public TempDiag findById(Long id) {

		return this.entityManager.find(TempDiag.class, id);
	}

	/*
	 * Support updating and deleting TempDiag entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.tempDiag);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.tempDiag);
				return "view?faces-redirect=true&id="
						+ this.tempDiag.getTempDiagId();
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
			TempDiag deletableEntity = findById(getId());

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
	 * Support searching TempDiag entities with pagination
	 */

	private int page;
	private long count;
	private List<TempDiag> pageItems;

	private TempDiag example = new TempDiag();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public TempDiag getExample() {
		return this.example;
	}

	public void setExample(TempDiag example) {
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
		Root<TempDiag> root = countCriteria.from(TempDiag.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<TempDiag> criteria = builder.createQuery(TempDiag.class);
		root = criteria.from(TempDiag.class);
		TypedQuery<TempDiag> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<TempDiag> root) {

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

	public List<TempDiag> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back TempDiag entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<TempDiag> getAll() {

		CriteriaQuery<TempDiag> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(TempDiag.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(TempDiag.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final TempDiagBean ejbProxy = this.sessionContext
				.getBusinessObject(TempDiagBean.class);

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

				return String.valueOf(((TempDiag) value).getTempDiagId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private TempDiag add = new TempDiag();

	public TempDiag getAdd() {
		return this.add;
	}

	public TempDiag getAdded() {
		TempDiag added = this.add;
		this.add = new TempDiag();
		return added;
	}
}
