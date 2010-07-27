package ai.liga.microurl.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ai.liga.microurl.util.BaseConverter;

@Entity(name = "microurl")
public class Microurl {

	@Id
	@GeneratedValue
	private int id;

	@Column(length = 10240)
	private String url;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMicro() {
		return "http://liga.ai/" + BaseConverter.toBase62(getId());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

}
