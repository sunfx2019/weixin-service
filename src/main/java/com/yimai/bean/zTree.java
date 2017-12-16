package com.yimai.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class zTree {

	private int id; // 菜单ID
	private int pId; // 父ID
	private String name; // 名称
	private boolean checked = false; // 是否被选中
	private boolean open = false; // 菜单是否展开
	private int level;
	private boolean isHidden;

	public int getId() {
		return id;
	}

	@JSONField(name = "pId")
	public int getpId() {
		return pId;
	}

	public String getName() {
		return name;
	}

	public boolean isChecked() {
		return checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

}
