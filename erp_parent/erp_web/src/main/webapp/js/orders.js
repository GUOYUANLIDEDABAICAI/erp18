//静态传参，oper标记当前要做的业务类型
var oper = Request.oper;
//订单的类型
var type = Request.type * 1;
var typeStr = "入";
if(type == 2){
	typeStr = "出";
}

var ordersuuid = {}; // 保存点击订单列表后，该行的订单id

$(function() {
	//订单列表请求的url
	var url = '';
	
	//详情窗口属性设置
	var ordersDlgCfg = {
		title : '订单详情',//窗口标题
		width : 700,//窗口宽度
		height : 320,//窗口高度
		closed : true,//窗口是是否为关闭状态, true：表示关闭
		modal : true
	};
	//明细表格的属性设置
	var itemgridCfg = {
		columns : [ [ 
				        {field:'uuid',title:'编号',width:60},
			  		    {field:'goodsuuid',title:'商品编号',width:80},
			  		    {field:'goodsname',title:'商品名称',width:100},
			  		    {field:'price',title:'价格',width:100},
			  		    {field:'num',title:'数量',width:100},
			  		    {field:'money',title:'金额',width:100},
			  		    {field:'state',title:'状态',width:60,formatter:formatDetailState} 
			  	] ],
				singleSelect : true
	};
	
	if(oper == "orders"){
		url = 'orders_listByPage?t1.type=' + type;
	}
	
	var ordersDlgCfgToolbar = [];
	ordersDlgCfgToolbar.push({
		text:'导出',
		iconCls:'icon-excel',
		handler:function(){
			$.download('orders_exportById',{id:$('#uuid').html()});
		}
	});
	
	//审核业务
	if(oper == 'doCheck'){
		url = "orders_listByPage?t1.type=1&t1.state=0";//未审核的订单
		//详情窗口，要加一个审核的按钮
		ordersDlgCfgToolbar.push({
			text:'审核',
			iconCls:'icon-search',
			handler:doCheck
		});
		document.title="采购订单审核";
	}
	//确认业务
	if(oper == 'doStart'){
		url = "orders_listByPage?t1.type=1&t1.state=1";//已审核的订单
		//详情窗口，要加一个确认的按钮
		ordersDlgCfgToolbar.push({
			text:'确认',
			iconCls:'icon-search',
			handler:doStart
		});
		document.title="采购订单确认";
	}
	
	//申请退采购订单
	if(oper == 'doReturn'){
		url = "orders_listByPage?t1.type="+ type;
		if (type == 1) { // 采购订单
			url += "&t1.state=3";
		}
		if (type == 2) { // 销售订单
			url += "&t1.state=1";
		}
		ordersDlgCfgToolbar.push({
			text:'退货',
			iconCls:'icon-search',
			handler:doReturn
		});
		document.title="采购订单退货申请";
		if (type == 2) {
			document.title="销售订单退货申请";
		}
	}
	
	//物流详情
	ordersDlgCfgToolbar.push({
		text:'物流详情',
		iconCls:'icon-search',
		handler:function(){
			var waybillsn = $('#waybillsn').html();
			if(waybillsn == ''){
				$.messager.alert('提示',"没有运单号",'info');
				return;
			}
			//打开物流详情窗口
			$('#waybillDlg').dialog('open');
			//加载物流详情信息
			$('#waybillgrid').datagrid({
				url:'orders_waybilldetailList?waybillsn=' + waybillsn,
				columns:[[
					{field:'exedate',title:'执行日期',width:100},
					{field:'exetime',title:'执行时间',width:100},
					{field:'info',title:'执行信息',width:140}
				]],
				singleSelect:true
			});
		}
	});
	
	// 实现动态按钮
	if(ordersDlgCfgToolbar.length > 0){
		//有按钮，才加入到订单详情窗口中
		ordersDlgCfg.toolbar = ordersDlgCfgToolbar;
	}
	//出入库
	if(oper == 'doInStore' || oper == 'doOutStore'){
		url = "orders_listByPage?t1.type=" + type;
		if(type == 1){
			url += "&t1.state=2";//采购订单时，显示确认的数据
			document.title="采购订单入库";
		}
		if(type == 2){
			url += "&t1.state=0";//销售订单时，显示未出库的数据
			document.title="销售订单出库";
		}
		itemgridCfg.onDblClickRow = function(rowIndex, rowData){
			//弹出入库窗口
			$('#itemDlg').dialog('open');
			$('#goodsuuid').html(rowData.goodsuuid);
			$('#goodsname').html(rowData.goodsname);
			$('#num').html(rowData.num);
			$('#id').val(rowData.uuid);
		}
		
	}
	
	//订单列表属性设置
	var gridCfg = {
			url:url,
			columns:getColumns(),
			singleSelect:true,
			pagination:true,
			onDblClickRow:function(rowIndex, rowData){
				
				ordersuuid = rowData.uuid;
				
				/*
				 在用户双击一行的时候触发，参数包括：
				rowIndex：点击的行的索引值，该索引值从0开始。
				rowData：对应于点击行的记录。
				 */			
				$('#ordersDlg').dialog('open');
				
				$('#uuid').html(rowData.uuid);
				$('#supplierName').html(rowData.supplierName);
				$('#state').html(formatState(rowData.state));
				$('#createrName').html(rowData.createrName);
				$('#checkerName').html(rowData.checkerName);
				$('#starterName').html(rowData.starterName);
				$('#enderName').html(rowData.enderName);
				$('#createtime').html(formatDate(rowData.createtime));
				$('#checktime').html(formatDate(rowData.checktime));
				$('#starttime').html(formatDate(rowData.starttime));
				$('#endtime').html(formatDate(rowData.endtime));
				
				//运单号
				$('#waybillsn').html(rowData.waybillsn);
				
				//加载明细的数据
				$('#itemgrid').datagrid('loadData',rowData.orderdetails);
				
				//rowData有哪些内容
				//alert(JSON.stringify(rowData));
			}
		};
	//我的订单
	if(oper == "myorders"){
		gridCfg.url = "orders_myListByPage?t1.type=" + type;
		var btnName = "采购申请";
		if(type == 2){
			btnName = "销售订单录入";
		}
		gridCfg.toolbar = [
		    {
		    	text:btnName,
		    	iconCls:'icon-add',
		    	handler:function(){
		    		$('#addOrdersDlg').dialog('open');
		    	}
		    }
		];
	}
	//订单列表
	$('#grid').datagrid(gridCfg);
	
	//订单详情窗口	
	$('#ordersDlg').dialog(ordersDlgCfg);
	
	//订单明细表格
	$('#itemgrid').datagrid(itemgridCfg);
	
	//入库窗口
	//初始化编辑窗口
	
	$('#itemDlg').dialog({
		title: typeStr + '库',//窗口标题
		width: 300,//窗口宽度
		height: 200,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true,//模式窗口
		buttons:[{
			text: typeStr + '库',
			iconCls: 'icon-save',
			handler:doInOutStore
		}]
	});
	
	//采购申请窗口属性设置
	var addOrdersDlgCfg = {
			title:'采购申请',
			width:700,
			height:400,
			modal:true,
			closed:true
		};
	if(type == 2){
		addOrdersDlgCfg.title="销售订单录入";
		$('#addOrdersSupplierName').html("客户");
	}
	//采购申请窗口
	$('#addOrdersDlg').dialog(addOrdersDlgCfg);
	
	//物流详情窗口
	$('#waybillDlg').dialog({
		title:'物流详情',
		width:500,
		height:300,
		modal:true,
		closed:true
	});
});

/**
 * 日期格式化
 * @param value
 * @returns
 */
function formatDate(value){
	if(value){
		return new Date(value).Format("yyyy-MM-dd");
	}
	return null;
}

/**
 * 订单状态格式化
 * @param value
 * @returns {String}
 */
function formatState(value){
	//采购: 0:未审核 1:已审核, 2:已确认, 3:已入库；销售：0:未出库 1:已出库
	if(type == 1){
		switch (value * 1) {
			case 0: return '未审核';
			case 1: return '已审核';
			case 2: return '已确认';
			case 3: return '已入库';
			case 4: return '退货已登记';
			case 5: return '退货已审核';
			case 6: return '退货成功';
			default: return '';
		}
	}
	if(type == 2){
		switch (value * 1) {
			case 0: return '未出库';
			case 1: return '已出库';
			case 4: return '退货已登记';
			case 5: return '退货已审核';
			case 6: return '退货成功';
			default: return '';
		}
	}
}

/**
 * 明细的状态
 * @param value
 * @returns {String}
 */
function formatDetailState(value){
	if(type == 1){
		switch (value * 1) {
			case 0: return '未入库';
			case 1: return '已入库';
			default: return '';
		}
	}
	if(type == 2){
		switch (value * 1) {
			case 0: return '未出库';
			case 1: return '已出库';
			default: return '';
		}
	}
}

/**
 * 加小数2位
 * @param value
 * @returns
 */
function formatMoney(value){
	return (value * 1).toFixed(2);
}

/**
 * 审核
 */
function doCheck(){
	$.messager.confirm("确认","确认要审核吗?",function(yes){
		if(yes){
			$.ajax({
				url : 'orders_doCheck',
				data : {id:$('#uuid').html()},
				dataType : 'json',
				type : 'post',
				success : function(rtn) {
					$.messager.alert('提示', rtn.message, 'info', function() {
						if(rtn.success){
							//关闭窗口
							$('#ordersDlg').dialog('close');
							//刷新表格
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}

/**
 * 确认
 */
function doStart(){
	$.messager.confirm("确认","确定要确认吗?",function(yes){
		if(yes){
			$.ajax({
				url : 'orders_doStart',
				data : {id:$('#uuid').html()},
				dataType : 'json',
				type : 'post',
				success : function(rtn) {
					$.messager.alert('提示', rtn.message, 'info', function() {
						if(rtn.success){
							//关闭窗口
							$('#ordersDlg').dialog('close');
							//刷新表格
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}

/**
 * 出入库
 */
function doInOutStore(){
	$.messager.confirm("确认","确认要" + typeStr + "库吗?",function(yes){
		if(yes){
			var submitData = $('#itemForm').serializeJSON();
			var url = "orderdetail_doInStore";
			if(type == 2){
				url = "orderdetail_doOutStore";
			}
			$.ajax({
				url : url,
				data : submitData,
				dataType : 'json',
				type : 'post',
				success : function(rtn) {
					$.messager.alert('提示', rtn.message, 'info', function() {
						if(rtn.success){
							//关闭入库窗口
							$('#itemDlg').dialog('close');
							
							//更新明细的状态
							// 获取选中的行
							var row = $('#itemgrid').datagrid('getSelected');
							row.state = '1';
							// 刷新明细列表
							var data = $('#itemgrid').datagrid('getData');
							$('#itemgrid').datagrid('loadData',data);
							
							//遍历明细，看明细的状态是否有一个未出入库
							var flg = true;
							$.each(data.rows,function(i, r){
								if(r.state * 1 == '0'){
									flg = false;
									return false;//退出循环
								}
							});
							if(flg == true){
								//关闭详情窗口
								$('#ordersDlg').dialog('close');
								//刷新表格
								$('#grid').datagrid('reload');
							}
						}
					});
				}
			});
		}
	});	
}

//订单退货申请
function doReturn() {
	//alert(ordersuuid);
	$.messager.confirm("确认","确定要退货吗?",function(yes){
		if(yes){
			$.ajax({
				type : "POST",
				url : "returnorders_doReturn",
				data : {ordersuuid: ordersuuid},
				dataType : "json",
				success : function(rtn) {
					$.messager.alert('提示', rtn.message, 'info', function() {
						if(rtn.success){
							//关闭窗口
							$('#ordersDlg').dialog('close');
							//刷新表格
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
	
}

/**
 * 根据订单的类型来返回相应要显示的列
 */
function getColumns(){
	if(type == 1){
		//采购
		return [[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'createtime',title:'生成日期',width:100,formatter:formatDate},
		  		    {field:'checktime',title:'审核日期',width:100,formatter:formatDate},
		  		    {field:'starttime',title:'确认日期',width:100,formatter:formatDate},
		  		    {field:'endtime',title:'入库日期',width:100,formatter:formatDate},
		  		    {field:'createrName',title:'下单员',width:100},
		  		    {field:'checkerName',title:'审核员',width:100},
		  		    {field:'starterName',title:'采购员',width:100},
		  		    {field:'enderName',title:'库管员',width:100},
		  		    {field:'supplierName',title:'供应商',width:100},
		  		    {field:'totalmoney',title:'合计金额',width:100,formatter:formatMoney},
		  		    {field:'state',title:'状态',width:100,formatter:formatState},
		  		    {field:'waybillsn',title:'运单号',width:100}
				]];
	}
	if(type == 2){
		//销售
		return [[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'createtime',title:'生成日期',width:100,formatter:formatDate},
		  		    {field:'endtime',title:'出库日期',width:100,formatter:formatDate},
		  		    {field:'createrName',title:'下单员',width:100},
		  		    {field:'enderName',title:'库管员',width:100},
		  		    {field:'supplierName',title:'客户',width:100},
		  		    {field:'totalmoney',title:'合计金额',width:100,formatter:formatMoney},
		  		    {field:'state',title:'状态',width:100,formatter:formatState},
		  		    {field:'waybillsn',title:'运单号',width:100}
				]];
	}
}
