$(function() {
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	// 在所有的AJAX请求发送之前，统一设置请求头
	// 这种方式设置的，只能处理通过jQuery发送的AJAX请求
	if (token && header) {
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	}
	
        //点击新增
        $(".btn-add").unbind("click").click(function() {
                $.ajax({
                        type: "get",
                        url: "/identity/show/add",
                        success: function(data) {
                                $('#w').window('open');
                                var idText = $(".unselected-roles ul a").prop("id");
                                $.each(data,
                                function(i, item) {
                                        if (!item.fixed) {
                                                $(".unselected-roles ul").append("<li><a href='#'  class='list-group-item slected-a'  style='padding-top: 5px;padding-bottom: 5px; ' id='" + item.id + "'><input type='checkbox'  name='roles[0].id'  " + "class='selected-input' value='" + item.id + "'/>" + item.roleName + "</a></li>");
                                        }
                                });
                        },
                        error: function(data) {
                                alert("网速不达,请检查网络!");
                        }
                });
        });
       
        //点击编辑信息页面的取消按钮
        $("#btn_cancel").click(function() {
                $('#w').window('close');
                clear();
        });
        //点击关闭窗口按钮，刷新页面，清空数据
        $(".panel-tool-close").click(function() {
                clear();
        });
        //点击最小化
        $(".panel-tool-min").click(function() {
                clear();
        });

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
});

//清除
var clear = function clear() {
        //清空id,姓名,登录名
        $("input[name='id']").val("");
        $("#inputName").val("");
        $("#inputLoginName").val("");
        //清除登录名认证的边框样式,图标,文本提示
        $('#inputLoginName').removeClass("LoginNameError");
        $("#loginNameTig").text("");
        $('.badge').addClass("badge-icon");
        //操作清空已选/未选角色
        $(".selected-roles ul li").remove();
        $(".unselected-roles ul li").remove();
}
//点击修改
var edit = function edit(id) {
		
        $.ajax({
                type: "get",
                url: "/identity/show/edit/" + id,
                success: function(data) {
                        $('#w').window('open');
                        $("input[name='id']").val(data.id);
                        $("#inputName").val(data.name);
                        $("#inputLoginName").val(data.loginName);
                        //调用checkLoginName();
                        checkLoginName(data.loginName);
                        //恢复按钮
                        //$("#btn_save").prop("disabled","");//禁用按钮，允许修改密码、角色操作
                        var arrayObj = new Array();
                        //拼接[已选择角色]
                        $.each(data.roles,
                        function(i, item) {
                                arrayObj.push(item.id); //将id 设入数组
                                if (!item.fixed) {
                                        $(".selected-roles ul").append("<li><label class='list-group-item'><input type='checkbox'  name='roles[0].id'  class='selected-input' value='" + item.id + "'/>" + item.roleName + "</label></li>");
                                }
                        });
                        //拼接[未选择角色]
                        $.each(data.unFixedRole,
                        function(i, item) {
                                if (!item.fixed && !(item.id === arrayObj[i])) {
                                        $(".unselected-roles ul").append("<li><label class='list-group-item'><input type='checkbox'  name='roles[0].id'  class='selected-input' value='" + item.id + "'/>" + item.roleName + "</label></li>");
                                }
                        });
                },
                error: function(data) {
                        alert("网速不达,请检查网络!");
                }
        });
}
//姓名校验
var loginNameIcon = function loginNameIcon(obj) {
        if (obj != "") {
                var ret = /^[A-Za-z]+$/;
                if (ret.test(obj)) {
                        $('.badge-icon1').toggleClass(function() {
                                return "badge-icon1"
                        });
                        $('#ok_error1').removeClass("glyphicon glyphicon-remove");
                        $('#ok_error1').addClass("glyphicon glyphicon-ok");
                        $(".nameIcon .badge").css("background-color", "green");
                        $("#nameTig").text("");
                } else {
                        $('.badge-icon1').toggleClass(function() {
                                return "badge-icon1"
                        });
                        $('#ok_error1').removeClass("glyphicon glyphicon-ok");
                        $('#ok_error1').addClass("glyphicon glyphicon-remove");
                        $(".nameIcon .badge").css("background-color", "red");
                        $("#nameTig").html("<font color='red'>请输入至少2个字符以上</font>");
                }
        } else {
                $('#name_icon').addClass("badge-icon1");
        }
}
//登录名校验
function checkLoginName(obj) {
        if (obj != "") {
                $.ajax({
                        type: "post",
                        url: "/identity/show/checked",
                        data: $(".form-horizontal").serialize(),
                        success: function(msg) {
                        	
                                /**边框样式*/
                                $('#inputLoginName').toggleClass("LoginNameSuccess", msg.status == 1);
                                $('#inputLoginName').toggleClass("LoginNameError", msg.status == 2);

                                /** 对错图标颜色的样式*/
                                if (msg.status == 2) {
                                        $('.badge-icon').toggleClass(function() {
                                                return "badge-icon"
                                        });
                                        $('#ok_error').toggleClass(function() {
                                                return "glyphicon glyphicon-ok"
                                        },
                                        msg.status == 1);
                                        $('#ok_error').addClass("glyphicon glyphicon-remove");
                                        $(".LoginNameIcon .badge").css("background-color", "red");
                                        $("#loginNameTig").html("<font color='red' style='font-size:14px'>用户名已被占用</font>");

                                        //禁用保存按钮
                                        //$("#btn_save").prop("disabled","disabled");//禁用按钮
                                } else {
                                        $('.badge-icon').toggleClass(function() {
                                                return "badge-icon"
                                        });
                                        $('#ok_error').toggleClass(function() {
                                                return "glyphicon glyphicon-remove"
                                        },
                                        msg.status == 2);
                                        $('#ok_error').addClass("glyphicon glyphicon-ok");
                                        $(".LoginNameIcon .badge").css("background-color", "green");
                                        $("#loginNameTig").text("");
                                        //恢复按钮
                                        //$("#btn_save").prop("disabled","");//禁用按钮
                                }
                        },
                        error: function(msg) {
                                alert("网速不达,请检查网络!");
                        }
                });
        } else {
                $('#loginName_icon').addClass("badge-icon");
        }
}