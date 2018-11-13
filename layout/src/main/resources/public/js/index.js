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
			 
			 var html=`<div><div  onclick='showOrhide(this)'  class='menu_first'>
			 						<span class='menu_first_span' style='border-top: 20px;'>${item.name}</span>
			 					</div><ul class='nav nav-sidebar' name='nav-sidebar'>`
				var first=``;
			 html+=first;
			 //如果有二级
			 if(item.children){
				 $.each(item.children,function(i,childs){
					 var second=`<li><a class='menu_second' href='${childs.url}'>${childs.name}</a></li>`;
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
	  },error:function(data,status,xhr){
		  
	  }
  });
});

//菜单缩放
function showOrhide(div){
	/*div是，包住一级菜单的div, $(div).parent() 表示找到当前的div的父元素，然后匹配下面的ul */
	$("ul", $(div).parent()).toggle();
}
