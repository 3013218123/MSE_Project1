package com.example.administrator.canol.treeview.bean;

import com.example.administrator.canol.treeview.tree.TreeNodeId;
import com.example.administrator.canol.treeview.tree.TreeNodePid;
import com.example.administrator.canol.treeview.tree.TreeNodeLabel;

public class Bean {
	@TreeNodeId
	private int id;
	@TreeNodePid
	private int pId;
	@TreeNodeLabel
	private String label;

	public Bean() {
	}

	public Bean(int id, int pId, String label) {
		this.id = id;
		this.pId = pId;
		this.label = label;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
