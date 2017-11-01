// 保存当前编辑的行的索引
var existEditIndex = -1;
$(function() {
	$('#ordersgrid').datagrid({
		columns : [ [ 
			{field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
					disabled:true//禁止编辑
				}}
			},
			{field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
				url:'goods_list',
				valueField:'name',//要保存的是商品的名称(已经存在有field:goodsuuid,商品的编号)
				textField:'name',
				onSelect:function(goods){
					//在用户选择列表项的时候触发。 goods => 选中的哪一项,选中的是一个商品的信息
					//alert(JSON.stringify(goods));
					//得到商品编号
					var goodsuuid = goods.uuid;
					//得到采购价格
					var price = goods.inprice;
					if(type == 2){
						//销售价
						price = goods.outprice;
					}
					//获取所有的行
		    		var rows = $('#ordersgrid').datagrid('getRows');
		    		//得到最后一行的索引
		    		var index = rows.length - 1;
					
					// 获取指定编辑器，options包含2个属性：
					//index：行索引。
					//field：字段名称。 列, 获取单元格的编辑器
					var goodsuuidEditor = getEditor('goodsuuid');
					// 给编辑器赋值
					$(goodsuuidEditor.target).val(goodsuuid);
					//价格编辑器
					var priceEditor = getEditor('price');
					// 给编辑器赋值
					$(priceEditor.target).numberbox('setValue',price);
					
					//数量编辑器
					var numEditor = getEditor('num');
					//选中数量输入框
					$(numEditor.target).select();
					//计算金额
					cal();
					//合计金额
					sum();
				}
			}}},
			{field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{
				//disabled:true,//禁止编辑
				precision:2,
				groupSeparator:',',
				prefix:'￥'
			}}},
			{field:'num',title:'数量',width:100,editor:'numberbox'},
			{field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{
				disabled:true,//禁止编辑
				precision:2
			}}},
			{field:'-',title:'操作',width:120,formatter: function(value,row,index){
				if(row.num === '合计'){
					return;
				}
				
				var oper = '<a href="javascript:void(0)" onclick="deleteRow(' + index + ')">删除</a>';
				return oper;
			}}
		] ],
		singleSelect : true,
		showFooter:true, //显示行脚
		toolbar:[
		    {
		    	text:'新增',
		    	iconCls:'icon-add',
		    	handler:function(){
		    		//关闭当前正在编辑的行
		    		if(existEditIndex > -1){
		    			//关闭当前编号的行
		    			$('#ordersgrid').datagrid('endEdit',existEditIndex);
		    		}
		    		
		    		//追加一个新行。新行将被添加到最后的位置。
		    		$('#ordersgrid').datagrid('appendRow',{
		    			num:0,
		    			money:0
		    		});
		    		//获取所有的行
		    		//var rows = $('#ordersgrid').datagrid('getRows');
		    		//得到最后一行的索引
		    		//var index = rows.length - 1;
		    		//设置当前编辑行的索引
		    		existEditIndex = $('#ordersgrid').datagrid('getRows').length - 1;
		    		//开启行编号
		    		$('#ordersgrid').datagrid('beginEdit',existEditIndex);
		    		//绑定自动计算事件
		    		bindGridEvent();
		    	}
		    },'-',{
		    	text:'提交',
		    	iconCls:'icon-save',
		    	handler:function(){
		    		var submitData = $('#orderForm').serializeJSON();
		    		if(submitData['t.supplieruuid'] == ''){
		    			$.messager.alert('提示', "请选择供应商", 'info');
		    			return;
		    		}
		    		//关闭当前正在编辑的行, 让编辑行的数据进入到datagrid里的 rows
		    		$('#ordersgrid').datagrid('endEdit',existEditIndex);
		    		var rows = $('#ordersgrid').datagrid('getRows');
		    		//把rows数组转成json字符串,jsonString与action中的属性一致
		    		submitData.jsonString = JSON.stringify(rows);
		    		//订单的类型
		    		submitData['t.type']=type;
		    		//提交
		    		$.ajax({
						url : 'orders_add',
						data : submitData,
						dataType : 'json',
						type : 'post',
						success : function(rtn) {
							$.messager.alert('提示', rtn.message, 'info',
									function() {
										if(rtn.success){
											//清除供应选择
											$('#supplier').combogrid('clear');
											//清空datagrid里的数据
											$('#ordersgrid').datagrid('loadData',{total:0,rows:[],footer:[{num: '合计', money: 0}]});
											//关闭申请的窗口
											$('#addOrdersDlg').dialog('close');
											//刷新订单列表
											$('#grid').datagrid('reload');
										}
									});
						}
					});
		    	}
		    }
		],
		onClickRow:function(rowIndex, rowData){
			//用户点击一行的时候触发，参数包括：
			//rowIndex：点击的行的索引值，该索引值从0开始。
			//rowData：对应于点击行的记录。
			
			//关闭当前编号的行
			$('#ordersgrid').datagrid('endEdit',existEditIndex);
			//开启编辑
			existEditIndex = rowIndex;
			$('#ordersgrid').datagrid('beginEdit',existEditIndex);
			//绑定自动计算事件
    		bindGridEvent();
		}
	});
	
	//加载行脚数据
	$('#ordersgrid').datagrid('reloadFooter',[{num: '合计', money: 0}]);

	//供应下拉表格
	$('#supplier').combogrid({    
	    panelWidth:750,
	    idField:'uuid',
	    textField:'name',
	    mode:'remote',
	    url:'supplier_list?t1.type=' + type,
	    columns:[[
			{field:'uuid',title:'编号',width:100},
			{field:'name',title:'名称',width:100},
			{field:'address',title:'联系地址',width:150},
			{field:'contact',title:'联系人',width:100},
			{field:'tele',title:'联系电话',width:100},
			{field:'email',title:'邮件地址',width:100}    
	    ]]    
	});  


});

/**
 * 计算商品的金额
 */
function cal(){
	//价格编辑器
	var priceEditor = getEditor('price');
	//数量编辑器
	var numEditor = getEditor('num');
	// 价格
	var price = $(priceEditor.target).numberbox('getValue');
	// 数量
	var num = $(numEditor.target).val();
	if(isNaN(num)){
		num = 0;
	};
	
	var money = num * price;
	money = money.toFixed(2);//保留小数点后2位有效数字
	//alert(money);
	//获取金额的编辑器
	var moneyEditor = getEditor('money');
	//给编辑器的输入框赋值
	$(moneyEditor.target).val(money);
	
	//给datagrid里的 row赋值
	// 获取所有的行
	var rows = $('#ordersgrid').datagrid('getRows');
	rows[existEditIndex].money = money;
}

/**
 * 获取当前编辑行的编辑器
 * @param _field
 * @returns
 */
function getEditor(_field){
	return  $('#ordersgrid').datagrid('getEditor', {index:existEditIndex,field:_field});
}

/**
 * 绑定自动计算事件
 */
function bindGridEvent(){
	//数量编辑器
	var numEditor = getEditor('num');
	//绑定keyup事件，按键弹起事件
	$(numEditor.target).bind('keyup',function(){
		cal();
		sum();
	});
}

/**
 * 删除指定的行
 * @param index
 */
function deleteRow(index){
	//删除
	$('#ordersgrid').datagrid('deleteRow',index);
	// 手工刷新
	// 获取数据
	var data = $('#ordersgrid').datagrid('getData');
	// 重新加载数据
	$('#ordersgrid').datagrid('loadData', data);
	
	//重新计算合计金额
	sum();
}

/**
 * 合计金额
 */
function sum(){
	//获取所有的行
	var rows = $('#ordersgrid').datagrid('getRows');
	var totalMoney = 0;
	//循环累计
	$.each(rows, function(i, row){
		totalMoney += parseFloat(row.money);
	});
	totalMoney = totalMoney.toFixed(2);
	//alert(totalMoney);
	//加载行脚数据
	$('#ordersgrid').datagrid('reloadFooter',[{num: '合计', money: totalMoney}]);
}