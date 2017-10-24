package com.mse8.teamwe.cantoolapp.treeview.bean;

import com.mse8.teamwe.cantoolapp.treeview.tree.TreeNodeId;
import com.mse8.teamwe.cantoolapp.treeview.tree.TreeNodePid;
import com.mse8.teamwe.cantoolapp.treeview.tree.TreeNodeLabel;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
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
