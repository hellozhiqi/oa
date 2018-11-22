$(function(){
		/**
		 * 解决AJAX访问的时候CSRF验证的问题
		 */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		// 在所有的AJAX请求发送之前，统一设置请求头
		// 这种方式设置的，只能处理通过jQuery发送的AJAX请求
		if (token && header) {
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});
		}
})
var added=false;//限制每次只能添加一个按钮
var addHoverDom=function(treeId, treeNode) {
	
	var sObj = $("#" + treeNode.tId + "_span");
	//表示满足:正在编辑名字,有节点,将终止往下执行
	if (added||treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加菜单' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		added=true;//显示为true ,将不再显示添加按钮
		//找到已有的tree
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes=zTree.addNodes(treeNode, { name:"新菜单"});
		zTree.selectNode(nodes[0],false);
		return false;
	});
};
var setting = {
	async: {
			// 激活异步请求
			enable: true,
			// 异步请求的URL，默认POST方式发送请求
			url: "/menu",
			// 使用GET方式发送请求
			type: "GET",
			// 要求返回JSON，数据类型参考jQuery的dataType
			dataType: "JSON"
	},
	treeNode:{
		isParent:false
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		//禁止多选
		selectedMulti: false
	},
	edit: {
		enable: true,
		showRenameBtn:false,
		drag: {
			isCopy: false,//禁止复制
			isMove: true//允许移动
		},
	showRemoveBtn : showRemoveBtn
	},
	callback:{
		onSelected: showToForm,
		beforeDrop:BeforeDrop,
		beforeRemove : beforeRemoveNode
	}
};
//使用var声明的函数，系统在运行的时候会把var放到最上面，但是还未赋值
//如果使用function直接声明函数，那么会在所有执行语句执行之前，先把函数分配空间
//内存分配顺序： function -> var -> let
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};
//treeId:表示当前节点的父id,node:选中节点
function showToForm(treeId, node){
	var id=node.id;
	var name=node.name;
	var url=node.url;
	var type=node.type;
	var roles=node.roles;
	$(".form-horizontal #id").val(id);
	$(".form-horizontal #inputName").val(name);
	$(".form-horizontal #inputURL").val(url);
	$(".form-horizontal input[name='type'][value='"+type+"']").prop("checked",true);
	
	//处理上级菜单
	var parentNode=node.getParentNode();
	if(parentNode){
		var parentId=parentNode.id;
		var parentName=parentNode.name;
		$(".form-horizontal #parentId").val(parentId);
		$(".form-horizontal #parentName").text(parentName);
	}else{
		$(".form-horizontal #parentId").val("");
		//span,div,可以用text,html追加内容
		$(".form-horizontal #parentName").text("");
	}
	if(node.roles){
		
		//选中关联的角色
		for(var i=0;i<node.roles.length;i++){
			var role=node.roles[i];
			$(".unselected-roles ul li input[value='"+role.id+"']").prop("checked",true);
		}
		//将勾选上角色添加到[以选角色]
		$(".add-selected").click()
	}
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
});

function moveNode(targetNode, treeNode, moveType){
	
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	if(targetNode){
		var targetNodeId=targetNode.id;
	}
	var nodes = treeObj.getNodes();
	$.each(nodes,function(index,item){
		
		var treeNodeId=item.id;
		if(treeNodeId===targetNodeId){
			treeObj.moveNode(nodes[index], nodes[index+1], "inner");
		}else{
			treeObj.moveNode(null, nodes[nodes.length], "next");
		}
	})
}

function BeforeDrop(treeId, treeNodes, targetNode, moveType){
	
	var param=new Object();
	//要移动节点的id,同一菜单不能有相同的子菜单名称
	param.id=treeNodes[0].id;
	//目标位置的字节点名称
	var targetChildrenName=new Object();
	//移动节点的名称
	var oldName=treeNodes[0].name;
	//要把节点到的目标位置
	if(targetNode){
		param.targetId=targetNode.id;
		$.each(targetNode.children,function(index,item){
			targetChildrenName=item.name;
			if(!oldName===targetChildrenName){//同一目录下有相同的子菜单
				return true;
			}
		});
	}else{
		param.targetId="";
	}
	//"inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
	param.moveType=moveType;
	$.ajax({
		url:"/menu/move",
		method:"post",
		async: false,
		data:param,
		success:function(msg){
			if(msg.status===1){
				moveNode(targetNode, treeNodes[0], moveType);
			}
		},error:function(msg){
			alert(msg.responseJSON.message);
		}
	});
	return false;
}
//显示删除按钮
var showRemoveBtn = function(treeId, treeNode) {
	// 没有下级菜单，可以删除
	return treeNode.children == 0;
};
//执行删除的操作
function removeNode(treeId, treeNode) {
	
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	var nodes = zTree.getSelectedNodes();
	zTree.reAsyncChildNodes(nodes[0], "refresh");
	// false 表示不要触发回调
	zTree.removeNode( treeNode, false );
};
function beforeRemoveNode(treeId, treeNode){
	
	$.ajax({
		url:"/menu/"+treeNode.id,
		method:"DELETE",
		dataType:"json",
		success:function(data,status,xhr){
			if(data.status==1){
				// 删除成功，把节点从页面移除
				removeNode(treeId, treeNode);
			}
		},error:function(data,status,xhr){
			alert(data.responseJSON.message);
		}
	});
	
	return false;
}