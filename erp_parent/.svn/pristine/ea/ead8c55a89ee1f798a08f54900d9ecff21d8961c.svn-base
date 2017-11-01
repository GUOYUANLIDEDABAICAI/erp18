//提交的方法名称
var method = "";
var oper = Request.oper;
$(function(){
	//加载表格数据
	var url = 'inventory_listByPage';
	
	//盘盈盘亏登记窗口属性编辑
	var editDlgCfg = {
			title: '盘盈盘亏登记',//窗口标题
			width: 250,//窗口宽度
			height: 250,//窗口高度
			closed: true,//窗口是是否为关闭状态, true：表示关闭
			modal: true,//模式窗口
			buttons:[{
				text:'保存',
				iconCls: 'icon-save',
				handler:function(){
					//验证, 当所有字段都有效的时候返回true, 如果有一个验证不通过，则为false
					if($('#editForm').form('validate') == false){
						return;
					}
					$.ajax({
						url: 'inventory_add',
						data: $('#editForm').serializeJSON(),
						dataType: 'json',
						type: 'post',
						success:function(rtn){
							$.messager.alert('提示',rtn.message, 'info',function(){
								if(rtn.success){
									//关闭弹出的窗口
									$('#editDlg').dialog('close');
									//刷新表格
									$('#grid').datagrid('reload');
								}
							});
						}
					});
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					//关闭弹出的窗口
					$('#editDlg').dialog('close');
					//刷新表格
					$('#grid').datagrid('reload');
				}
			}]
		};
	
	//审核窗口属性编辑
	var checkDlgCfg = {
			title:'盘盈盘亏审核',
			width:300,
			height:250,
			modal:true,
			closed:true,
		}
	
	//审核
	if(oper == 'doCheck'){
		url = 'inventory_listByPage?t1.state=0';
		$('#addInventoryBtn').hide();
		//详情窗口，要加一个审核的按钮
		checkDlgCfg.toolbar = [{
			text:'审核',
			iconCls:'icon-search',
			handler:doCheck
		}];
		
		document.title="盘盈盘亏审核";
	}
	
	$('#grid').datagrid({
		url:url,
		singleSelect: true,
		pagination: true,
		columns:[[
		  		{field:'uuid',title:'编号',width:100},
	  		    {field:'goodsname',title:'商品',width:100},
	  		    {field:'storename',title:'仓库',width:100},
	  		    {field:'num',title:'数量',width:100},
	  		    {field:'type',title:'类型',width:100,formatter:formateType},
	  		    {field:'createtime',title:'登记日期',width:150,formatter:formateDate},
	  		    {field:'checktime',title:'审核日期',width:150,formatter:formateDate},
	  		    {field:'createrName',title:'登记人',width:100},
	  		    {field:'checkerName',title:'审核人',width:100},
	  		    {field:'state',title:'状态',width:100,formatter:formateState},
	  		    {field:'remark',title:'备注',width:100},

				{field:'-',title:'操作',width:200,formatter: function(value,row,index){
					var oper = '<a href="javascript:void(0)" onclick="del(' + row.uuid + ')">删除</a>';
					return oper;
				}}
		]],
		
		onDblClickRow:function(rowIndex,rowData){
			if(oper == 'doCheck'){
				$("#checkDlg").dialog('open');
			}
			$("#uuid").html(rowData.uuid);
			$("#createtime").html(formateDate(rowData.createtime));
			$("#goodsuuid").html(rowData.goodsname);
			$("#storeuuid").html(rowData.storename);
			$("#num").html(rowData.num);
			$("#type").html(formateType(rowData.type));
			$("#remark").html(rowData.remark);
			
			//alert(JSON.stringify(rowData));
		}
	});

	
	
	//盘盈盘亏登记 按钮
	$("#addInventoryBtn").bind('click',function(){
		$("#editDlg").dialog('open');
	});
	
	//初始化编辑窗口
	$('#editDlg').dialog(editDlgCfg);
	
	//盘盈盘亏审核
	$("#checkDlg").dialog(checkDlgCfg);
	
	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	
})


/**
 * 删除
 */
function del(uuid){
	$.messager.confirm("确认","确认要删除吗？",function(yes){
		if(yes){
			$.ajax({
				url: 'inventory_delete?id=' + uuid,
				dataType: 'json',
				type: 'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						//刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});
}

/**
 * 审核
 */
function doCheck(){
	$.messager.confirm("确认","确认要审核吗?",function(yes){
		if(yes){
			var submitData = $("#checkForm").serializeJSON();
			submitData.id=$("#uuid").html();
			$.ajax({
				url:'inventory_doCheck',
				data:submitData,
				dataType:'json',
				type:'post',
				success:function(rtn){
					$.messager.alert('提示信息',rtn.message,'info');
					if(rtn.success){
						$("#checkDlg").dialog('close');
						$("#grid").datagrid('reload');
					}
				}
			});
		}
	})
}
/**
 * 格式化日期
 * @param value
 * @returns
 */
function formateDate(value){
	if(value){
		return new Date(value).Format('yyyy-MM-dd hh:mm:ss');
	}
	return null;
	
}
/**
 * 格式化类型
 * @param value
 * @returns
 */
function formateType(value){
	switch (value*1) {
	case 1:
		return "盘盈";
	case 2:
		return "盘亏";
	default:
		return "";
	}
	
}
/**
 * 格式化状态
 * @param value
 * @returns
 */
function formateState(value){
	switch (value*1) {
	case 0:
		return "未审核";
	case 1:
		return "已审核";
	default:
		return "";
	}
	
}