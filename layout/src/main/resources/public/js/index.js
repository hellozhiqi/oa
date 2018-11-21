$(document).ready(function () {
	
	  $("[data-toggle='sidebar']").click(function () {
	    $('.sidebar').toggleClass('active')
	  });
	  
  //异步加载菜单
  $.ajax({
	  url:"/menu/menu",
	  method:"GET",
	  dataType:"JSON",
	  success:function(data,status,xhr){
		 $.each(data,function(index,item){
			 
			 var html=`<div><div  onclick='showOrhide(this)'  class='menu_first glyphicon glyphicon-chevron-right'>
			 						<span class='menu_first_span' style='border-top: 20px;'>${item.name}</span>
			 					</div><ul class='nav nav-sidebar' name='nav-sidebar' style='display: none;' >`
				var first=``;
			 html+=first;
			 //如果有二级
			 if(item.children){
				 $.each(item.children,function(i,childs){
					 var second=`<li><a class='menu_second' href='${childs.url}'><span class='second-css'>${childs.name}</span></a></li>`;
					html +=second;//二级
					 $.each(childs.children,function(th,childs_three){
						 var three=`<li><a class='menu_three' href='${childs_three.url}'>${childs_three.name}</a></li>`;
						 html +=three;//三级
					 })
				 })
			 }
			 html +=`</ul></div>`;
			 //有二级拼接
			 if(item.children.length>0){
				 $(html).appendTo($(".sidebar"));
			 }else{
				 $(html).appendTo($(".sidebar"));
			 }
		 });
		 
		 //高亮显示左侧菜单
		 var url=location.pathname;
		 //console.info(url);
		 var lis=$(".nav-sidebar li");
		 //$.each(lis,function(index,item){
		//	 console.info($("a",$(this)).prop("href"));
		 //})
		 console.info("--------------------------------------");
		 lis.sort(function(lis1,lis2){
			 return $("a",lis2).attr("href").length-$("a",lis1).attr("href").length;
		 });
		 //左侧菜单效果：因为点击二级菜单是会重新在通过控制器找到视图，
		 //再次刷新页面，左侧菜单再次全部收起来。，因该展开刚点的二级菜单,最好使用ajax实现异步
		 for(var i=0;i<lis.length;i++){
			 var li=lis[i];
			 var href=$("a",$(li)).attr("href");
			 if(url.startsWith(href) ){
				 $(li).addClass("active");
				 $("ul", $(li).parent().parent()).toggle();//去掉隐藏，即展开
				 $("div",$(li).parent().parent()).removeClass("glyphicon glyphicon-chevron-right");
				 $("div",$(li).parent().parent()).addClass("glyphicon glyphicon-chevron-down");
				 break;
			 }
		 }
	  },error:function(data,status,xhr){
		  alert(data.responseJSON.message);
	  }
  });
});
//菜单缩放/以及向右向下图标切换
function showOrhide(div){
	/*div是，包住一级菜单的div, $(div).parent() 表示找到当前的div的父元素，然后匹配下面的ul */
	$("ul", $(div).parent()).toggle();
	var style=$("ul", $(div).parent()).prop("style");
	$(div).toggleClass(function() {
		  if (style.display =="") {
			  $(div).removeClass("glyphicon glyphicon-chevron-right");
		    return 'glyphicon glyphicon-chevron-down';
		  } else {
			  $(div).removeClass("glyphicon glyphicon-chevron-down");
		    return 'glyphicon glyphicon-chevron-right';
		  }
	});
}
