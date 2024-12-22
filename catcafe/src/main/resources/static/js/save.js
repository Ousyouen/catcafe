$(document).ready(function () {
    // 获取 URL 中的用户 ID
    const userId = window.location.pathname.split('/').pop();

    // 页面加载时获取用户信息
    fetch(`/user/get/${userId}`) // 使用动态用户 ID
        .then(response => {
            if (!response.ok) {
                throw new Error('无法获取用户信息');
            }
            return response.json();
        })
        .then(data => {
            // 填充表单
            $('#userId').val(userId); // 假设页面有隐藏的用户 ID 输入框
            $('#username').val(data.username);
            $('#birthday').val(data.birthday ? data.birthday.split('T')[0] : '');
            $('#email').val(data.email);
        })
        .catch(error => console.error('获取用户信息失败:', error));

		// 保存按钮点击事件
		$('#saveButton').on('click', function (e) {
		    e.preventDefault(); // 阻止默认的表单提交

		    // 构建用户数据
		    const userData = {
		        id: $('#userId').val(),
		        username: $('#username').val(),
		        birthday: $('#birthday').val(),
		        email: $('#email').val()
		    };

		    // 使用 AJAX 发送 JSON 格式请求
		    $.ajax({
		        url: '/user/update', // 请求的 URL
		        type: 'POST', // 请求类型
		        contentType: 'application/json', // 确保是 JSON 格式
		        data: JSON.stringify(userData), // 将 JavaScript 对象转换为 JSON 字符串
		        success: function (response) {
		            // 请求成功的回调函数
		            alert('保存成功');
		        },
		        error: function (xhr, status, error) {
		            // 请求失败的回调函数
		            console.error('保存用户信息失败:', error);
		            alert('保存失败');
		        }
		    });
		});

});
