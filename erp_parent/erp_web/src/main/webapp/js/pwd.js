$(function() {
	$('#grid').datagrid({
		url : 'emp_listByPage',
		columns : [ [ 
			  {field:'uuid',title:'编号',width:100},
			  {field:'username',title:'登陆名',width:100},
			  {field:'name',title:'真实姓名',width:100},
			  {field:'gender',title:'性别',width:100,formatter:function(value){
			  	//value * 1  转成数值
			  	if(value * 1 == 1){
			  		return '男';
			  	}
			  	if(value * 1 == 0){
			  		return '女';
			  	}
			  }},
			  {field:'email',title:'邮件地址',width:100},
			  {field:'tele',title:'联系电话',width:100},
			  {field:'address',title:'联系地址',width:200},
			  {field:'birthday',title:'出生年月日',width:100,formatter:function(value){
			  	//js中的调用方法所传的参数个数是可变，但是顺序不能变
			  	if(value){//value不为空，"",null, value没有类型
			  		return new Date(value).Format("yyyy-MM-dd");
			  	}
			  }},
			  {field:'dep',title:'部门编号',width:100,formatter:function(value){
			  	if(value){//存在部门
			  		return value.name;
			  	}
			  }},
			
			{field:'-',title:'操作',width:200,formatter: function(value,row,index){
				var oper = "<a href=\"javascript:void(0)\" onclick=\"updatePwd_reset(" + row.uuid + ')">重置密码</a>';
				return oper;
			}}
		] ],
		pagination:true,
		singleSelect : true
	});
	
	//重置密码窗口初始化
	$('#editDlg').dialog({
		title : '重置密码',//窗口标题
		width : 300,//窗口宽度
		height : 110,//窗口高度
		closed : true,//窗口是是否为关闭状态, true：表示关闭
		modal : true,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				var submitData = $('#editForm').serializeJSON();
				$.ajax({
					url : 'emp_updatePwd_reset',
					data : submitData,
					dataType : 'json',
					type : 'post',
					success : function(rtn) {
						$.messager.alert('提示', rtn.message, 'info', function() {
							if(rtn.success){
								$('#editDlg').dialog('close');
							}
						});
					}
				});
			}
		} ]
	});
});

/**
 * 重置密码
 * @param uuid
 */
function updatePwd_reset(uuid){
	//设置员工编号
	$('#id').val(uuid);
	//打开重置密码窗口
	$('#editDlg').dialog('open');
}