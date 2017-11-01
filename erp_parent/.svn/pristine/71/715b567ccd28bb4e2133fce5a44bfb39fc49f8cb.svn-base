var oper = Request['oper'];

$(function() {
	
	var toolbar = [];
	if (oper == 'apply') {	
		var toolbar = [{
			text: '申请',
			iconCls: 'icon-add',
			handler: function(){
				$('#applyDlg').dialog('open');
			}
		}];
	}
	
	var url = '';
	if (oper == 'apply' || oper == 'myApply') {
		url = 'leavenote_myApplyLeavenotes';
	}
	if (oper == 'doCheck' || oper == 'myUncheck') {
		url = 'leavenote_myUncheckLeavenotes';
	}
	if (oper == 'myCheck') {
		url = 'leavenote_myCheckLeavenotes';
	}

	$('#grid').datagrid({
		url: url,
		columns:[[
  		    {field:'uuid',title:'编号',width:50},
  		    {field:'starttime',title:'开始时间',width:110, formatter: formatDate},
  		    {field:'endtime',title:'结束时间',width:110, formatter: formatDate},
  		    {field:'reason',title:'请假原因',width:400},
  		    {field:'state',title:'状态',width:70, formatter: formatState}
		]],
		singleSelect: true,
		pagination: true,
		toolbar: toolbar,
		onDblClickRow: function(index, row) {
			if (oper != 'doCheck') {
				return;
			}
			
			$.messager.confirm('提示','你确定要审批该申请？', function(yes) {
				if (yes) {
					$.ajax({
						type : "POST",
						url : "leavenote_doCheck",
						data : {id: row.uuid},
						dataType : "json",
						success : function(result) {
							$.messager.alert('提示',result.message,'info',function() {
								if (result.success) {
									$('#grid').datagrid('reload');
								}
							});
						}
					});
				}
			}); 
		}
	});
	
	$('#btnApply').bind('click', function() {
		$.messager.confirm('提示','你确定要提交该申请？', function(yes) {
			if (yes) {
				var formData = $('#leavenoteForm').serializeJSON();
				$.ajax({
					type : "POST",
					url : "leavenote_apply",
					data : formData,
					dataType : "json",
					success : function(result) {
						$.messager.alert('提示', result.message, 'info', function() {
							$('#leavenoteForm').form('clear');
						});
					}
				});
			}
		}); 
	});
	
	$('#applyDlg').dialog({
		title : '请假申请',//窗口标题
		width : 450,//窗口宽度
		height : 130,//窗口高度
		closed : true,//窗口是是否为关闭状态, true：表示关闭
		modal : true
	});
	
	$('#checkDlg').dialog({
		title : '请假审批',//窗口标题
		width : 1000,//窗口宽度
		height : 110,//窗口高度
		closed : true,//窗口是是否为关闭状态, true：表示关闭
		modal : true
	});
	
});


function formatDate(value) {
	return new Date(value).Format('yyyy-MM-dd hh:mm');
}

function formatState(value) {
	if (value * 1 == 0) {
		return '未审批';
	}
	if (value * 1 == 1) {
		return '已审批';
	}
}