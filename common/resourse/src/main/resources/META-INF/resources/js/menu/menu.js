$(function(){
	  //添加所选
        $(".add-selected").unbind("click").click(function() {
                //在[未选中角色]中,匹配到已勾选的复选框
                $(".unselected-roles ul li input:checked").each(function(index, item) {
                        $(item).prop("checked", false);
                        //找到li
                        var ul = $(item).parent().parent();
                        $(".selected-roles ul").append(ul);
                });
        });
        //添加所有
        $(".add-all").unbind("click").click(function() {
                $(".unselected-roles ul li input").each(function(index, item) {
                        $(item).prop("checked", false);
                        //找到li
                        var ul = $(item).parent().parent();
                        $(".selected-roles ul").append(ul);
                });
       });
     //移除所选
        $(".remove-selected").unbind("click").click(function() {
                $(".selected-roles ul li input:checked").each(function(index, item) {
                       $(item).prop("checked", false);
                        //找到li
                        var ul = $(item).parent().parent();
                        $(".unselected-roles ul").append(ul);
                });
        });
        //移除所有
        $(".remove-all").unbind("click").click(function() {
                $(".selected-roles ul li input").each(function(index, item) {
                        $(item).prop("checked", false);
                        //找到li
                        var ul = $(item).parent().parent();
                        $(".unselected-roles ul").append(ul);
                });
         });
         //提交表单时,把选中角色全部勾选,未选中角色全部取消反选
         //把选中的角色的input 的name的数字,替换成1/2/3. 
         $("form").bind("submit",
         function() {
               $(".selected-roles ul li input").each(function(index, item) {
                        //选中的,全部勾上
                         $(item).prop("checked", true);
                         //name的数字,替换
                         var name = $(item).attr("name");
                         name = name.replace(/\d+/, index);
                         $(item).attr("name", name);
                         $(".unselected-roles ul li input").each(function(index, item) {
                                 $(item).prop("checked", false); //取消沟中
                         });
                 });
       });
 })