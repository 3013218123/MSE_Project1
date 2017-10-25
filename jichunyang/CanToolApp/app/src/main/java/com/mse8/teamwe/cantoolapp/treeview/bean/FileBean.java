package com.mse8.teamwe.cantoolapp.treeview.bean;

import com.mse8.teamwe.cantoolapp.treeview.tree.TreeNodeId;
import com.mse8.teamwe.cantoolapp.treeview.tree.TreeNodePid;
import com.mse8.teamwe.cantoolapp.treeview.tree.TreeNodeLabel;

public class FileBean {
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private long length;
	private String desc;

	public FileBean(int _id, int parentId, String name) {
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}

}
