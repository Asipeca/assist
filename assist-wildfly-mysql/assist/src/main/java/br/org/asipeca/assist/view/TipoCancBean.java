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

import br.org.asipeca.assist.model.TipoCanc;
import br.org.asipeca.assist.model.AssiTipoCanc;
import java.util.Iterator;

/**
 * Backing bean for TipoCanc entities.
 * <p/>
 * This class provides CRUD functionality for all TipoCanc entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TipoCancBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving TipoCanc entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private TipoCanc tipoCanc;

	public TipoCanc getTipoCanc() {
		return this.tipoCanc;
	}

	public void setTipoCanc(TipoCanc tipoCanc) {
		this.tipoCanc = tipoCanc;
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
			this.tipoCanc = this.example;
		} else {
			this.tipoCanc = findById(getId());
		}
	}

	public TipoCanc findById(Long id) {

		return this.entityManager.find(TipoCanc.class, id);
	}

	/*
	 * Support updating and deleting TipoCanc entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.tipoCanc);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.tipoCanc);
				return "view?faces-redirect=true&id="
						+ this.tipoCanc.getTipoCancId();
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
			TipoCanc deletableEntity = findById(getId());
			Iterator<AssiTipoCanc> iterAssiTipoCancs = deletableEntity
					.getAssiTipoCancs().iterator();
			for (; iterAssiTipoCancs.hasNext();) {
				AssiTipoCanc nextInAssiTipoCancs = iterAssiTipoCancs.next();
				nextInAssiTipoCancs.setTipoCanc(null);
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
	 * Support searching TipoCanc entities with pagination
	 */

	private int page;
	private long count;
	private List<TipoCanc> pageItems;

	private TipoCanc example = new TipoCanc();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public TipoCanc getExample() {
		return this.example;
	}

	public void setExample(TipoCanc example) {
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
		Root<TipoCanc> root = countCriteria.from(TipoCanc.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<TipoCanc> criteria = builder.createQuery(TipoCanc.class);
		root = criteria.from(TipoCanc.class);
		TypedQuery<TipoCanc> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<TipoCanc> root) {

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

	public List<TipoCanc> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back TipoCanc entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<TipoCanc> getAll() {

		CriteriaQuery<TipoCanc> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(TipoCanc.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(TipoCanc.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final TipoCancBean ejbProxy = this.sessionContext
				.getBusinessObject(TipoCancBean.class);

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

				return String.valueOf(((TipoCanc) value).getTipoCancId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private TipoCanc add = new TipoCanc();

	public TipoCanc getAdd() {
		return this.add;
	}

	public TipoCanc getAdded() {
		TipoCanc added = this.add;
		this.add = new TipoCanc();
		return added;
	}
}
