package com.example.administrator.canol.treeview.bean;

import com.example.administrator.canol.treeview.tree.TreeNodeId;
import com.example.administrator.canol.treeview.tree.TreeNodePid;
import com.example.administrator.canol.treeview.tree.TreeNodeLabel;

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
