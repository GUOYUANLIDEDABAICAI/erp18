$(function() {
	
	$('#btnSave').bind('click', function() {
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
	});
	
});