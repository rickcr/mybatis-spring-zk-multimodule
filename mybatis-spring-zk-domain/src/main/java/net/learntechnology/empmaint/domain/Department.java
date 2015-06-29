package net.learntechnology.empmaint.domain;


public class Department extends BaseVO {
	private static final long serialVersionUID = -6810736897253521583L;
	private int id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Department that = (Department) o;

		return id == that.id;

	}

	@Override
	public int hashCode() {
		return id;
	}
}
