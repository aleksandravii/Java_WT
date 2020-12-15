package techshop.domain.entities;

import techshop.domain.entities.Entity;

/**
 * Group entity
 */
public class Group extends Entity {

	private static final long serialVersionUID = -5999081026089283200L;
	
	private String groupName;
	
	private int categoryId;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Group [groupName=" + groupName + ", categoryId=" + categoryId + "]";
	}

	
}
