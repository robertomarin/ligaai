package ai.liga.ligaai.model;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.apache.commons.validator.GenericValidator;

import ai.liga.user.model.User;

@Entity
public class LigaAi {

	@Id
	@GeneratedValue
	private Long id;

	@Null(message = "Usuário não deveria vir preenchido.")
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(joinColumns = { @JoinColumn(name = "ligaai_id") }, inverseJoinColumns = { @JoinColumn(name = "contact_id") })
	private List<Contact> contacts;

	@NotNull(message = "Não foi enviada nenhuma mensagem.")
	@Column(length = 200)
	private String message;

	@Null(message = "Created não deveria vir preenchido.")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Calendar created;

	@Column(name = "remoteaddress", length = 100, updatable = false)
	private String remoteAddress;

	@Null(message = "Tags não deveriam vir preenchidas.")
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(joinColumns = { @JoinColumn(name = "ligaai_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	private Set<Tag> tags;

	private int likes;

	private int dislikes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = !GenericValidator.isBlankOrNull(remoteAddress) && remoteAddress.length() > 100 ? remoteAddress
				.substring(0, 100) : remoteAddress;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

}
