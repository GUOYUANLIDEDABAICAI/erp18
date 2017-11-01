
$(function(){
	
	var year = (new Date()).getFullYear();

	$('#year').combobox('setValue', year);
	
	var gridCfg={
			url:'report_trendCancelReport',
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
				showColumn(data.rows);
				
			}
		}
	
	//加载表格数据
	$('#grid').datagrid(gridCfg);
	


	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	$('#chart').bind('click',function(){
		
		$.ajax({
			   type: "POST",
			   url: "report_trendCancelReport",
			   data: year,
			   dataType:'json',
			   success: function(data){
				   showPie(data)
			   }
			});
	})
		$('#cloumns').bind('click',function(){
		
		$('#grid').datagrid("reload");
	})
		$('#dcloumn').bind('click',function(){
	
		$.ajax({
			   type: "POST",
			   url: "report_trendCancelReport",
			   data: year,
			   dataType:'json',
			   success: function(data){
				   showd(data)
			   }
			});
	})

});

function showColumn(_cancelData) {
		$("#sliders").html("")
	    $('#cloumn').highcharts({
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: $('#year').combobox('getValue') + '年度销售退货趋势图',
	        },
	        subtitle: {
	            text: 'Source: www.itheima.com'
	        },
	        xAxis: {
	            type: 'category',
	            labels: {
	                rotation: -45,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '销售退货金额 (￥)'
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	        	valueSuffix: '￥'
	        },
	        series: [{
	            name: '全部商品',
	            data:_cancelData,
	            dataLabels: {
	                enabled: true,
	                rotation: -0,
	                color: '#FFFFFF',
	                align: 'right',
	                format: '{point.y:.1f}', // one decimal
	                y: 10, // 10 pixels down from the top
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        }],
	        credits:{
	        	href: "http://www.itheima.com",
	        	text:'黑马程序员'
	        }
	    });

};
function showPie(_cancelData) {
	$("#sliders").html("")
	 $('#cloumn').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: $('#year').combobox('getValue') + '年度销售退货趋势图',
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    formatter: function() {
	                        if (this.percentage > 0)
	                            return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %'; // 这里进行判断
	                    }, 
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	            	 text: '销售退货金额 (￥)'
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '全部商品',
	            data: _cancelData
	        }],
	        credits:{
	        	href: "http://www.itheima.com",
	        	text:'黑马程序员'
	        }
	    });
};
function showd(_cancelData){
	var _cancelData2=[]
	$.each(_cancelData,function(i,n){
		if(n.y==0){
			_cancelData2.push(null)
		}else{
			_cancelData2.push(n)
		}
	})
	$("#sliders").html("<table>" +
			"<tr><td>垂直方向</td><td><input id=\"R0\" type=\"range\" min=\"0\" max=\"45\" value=\"15\"/> <span id=\"R0-value\" class=\"value\">" +
			"</span></td></tr><tr><td>水平方向" +
			"</td><td><input id=\"R1\" type=\"range\" min=\"0\" max=\"45\" value=\"15\"/> <span id=\"R1-value\" class=\"value\"></span></td></tr></table>")
	    var chart = new Highcharts.Chart({
	        chart: {
	            renderTo: 'cloumn',
	            type: 'column',
	            margin: 75,
	            options3d: {
	                enabled: true,
	                alpha: 15,
	                beta: 15,
	                depth: 50,
	                viewDistance: 25
	            }
	        },
	        title: {
	        	 text: $('#year').combobox('getValue') + '年度销售退货趋势图',
	        },
	        subtitle: {
	        	text: 'Source: www.itheima.com'
	        },/*这是添加月份的*/
	        xAxis: {
	            categories: Highcharts.getOptions().lang.months
	        },
	        yAxis: {
	            min: 0,
	            title: {
	            	 text: '销售退货金额 (￥)'
	            }
	        },
	        plotOptions: {
	            column: {
	                depth: 25
	            }
	        },
	        series: [{
	        	name: '全部商品',
	            data: _cancelData2
	        }],
	        credits:{
	        	href: "http://www.itheima.com",
	        	text:'黑马程序员'
	        }
	    });

	    function showValues() {
	        $('#R0-value').html(chart.options.chart.options3d.alpha);
	        $('#R1-value').html(chart.options.chart.options3d.beta);
	    }

	    // Activate the sliders
	    $('#R0').on('change', function () {
	        chart.options.chart.options3d.alpha = this.value;
	        showValues();
	        chart.redraw(false);
	    });
	    $('#R1').on('change', function () {
	        chart.options.chart.options3d.beta = this.value;
	        showValues();
	        chart.redraw(false);
	    });

	    showValues();
	
}

