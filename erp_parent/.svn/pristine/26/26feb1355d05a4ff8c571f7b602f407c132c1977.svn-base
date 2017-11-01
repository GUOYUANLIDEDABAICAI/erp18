$(function(){
	
	var year = (new Date()).getFullYear();

	$('#year').combobox('setValue', year);
	
	//加载表格数据
	$('#grid').datagrid({
		url:'report_tr',
		queryParams:{"year":year},
		columns:[[
		  		    {field:'name',title:'月份',width:100},
		  		    {field:'y',title:'销售额',width:100}
					]],
		singleSelect: true,
		onLoadSuccess:function(data){
			//在数据加载成功的时候触发。
			//alert(JSON.stringify(data));
			//data: {total:111,rows:[]}
			showChart(data.rows);
			
		}
	});

	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	
	
});

function showChart(_data){
	var months = [];
	for(var i = 1; i <= 12; i++){
		months.push(i+"月");
	}
	$('#chart').highcharts({
		title: {
            text: $('#year').combobox('getValue') + '年度销售趋势图',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: www.itheima.com',
            x: -20
        },
        xAxis: {
            categories: months
        },
        yAxis: {
            title: {
                text: '销售额 (￥)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '￥'
        },
        legend: {
            layout: 'vertical',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0
        },
        series: [{
            name: '全部商品',
            data: _data
        }],
        credits:{
        	href: "http://www.itheima.com",
        	text:'黑马程序员'
        }
    });
}