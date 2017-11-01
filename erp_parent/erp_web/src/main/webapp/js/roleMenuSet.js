$(function() {
	$('#grid').datagrid({
		url:'role_list',
		columns:[[
		    {field:'uuid',title:'编号',width:100},
	  		{field:'name',title:'名称',width:100}
		]],
		singleSelect:true,
		onClickRow:function(rowIndex, rowData){
			$('#tree').tree({
				url:'role_readRoleMenus?id=' + rowData.uuid,
				animate:true,
				checkbox:true
			});
		}
	});
	

	//绑定保存事件
	$('#btnSave').bind('click',function(){
		var nodes = $('#tree').tree('getChecked');
		//alert(JSON.stringify(nodes));
		var idArr = [];//选中的菜单的编号
		$.each(nodes,function(i,node){
			idArr.push(node.id);
		});
		var submitData = {};
		// ids应该与roleAction中的ids一致, 菜单编号以逗分割连起来
		submitData.ids = idArr.toString();
		//获取选中的角色
		var role = $('#grid').datagrid('getSelected');
		// id为属性驱动所需要角色的编号
		submitData.id = role.uuid;
		$.ajax({
			url : 'role_updateRoleMenus',
			data : submitData,
			dataType : 'json',
			type : 'post',
			success : function(rtn) {
				$.messager.alert('提示', rtn.message, 'info');
			}
		});
		
	});
});