const API_URL = '/api/users';

// 加载用户到下拉框
function loadUsers() {
    $.ajax({
        url: "/api/users",
        method: "GET",
        success: function (users) {
            $("#userSelect, #deleteUserSelect").empty();
            users.forEach(user => {
                // 使用正确的字段名：user.username
                const option = `<option value="${user.id}">${user.username}</option>`;
                $("#userSelect, #deleteUserSelect").append(option);
            });
        },
        error: function () {
            alert("ユーザーのロードに失敗しました。");
        }
    });
}

// 修改用户信息
function updateUser(e) {
    e.preventDefault();

    const userId = $('#userSelect').val();
    const updatedUser = {
        username: $('#userName').val(),
        birthday: $('#userBirthday').val(),
        email: $('#userEmail').val()
    };

    $.ajax({
        url: `${API_URL}/${userId}`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedUser),
        success: function (message) {
            alert(message);
            loadUsers();
        },
        error: function (xhr, status, error) {
            console.error('ユーザー情報の更新に失敗しました:', error);
        }
    });
}

// 添加新用户
function addUser(e) {
    e.preventDefault();

    const newUser = {
        username: $('#newUserName').val(),
        birthday: $('#newUserBirthday').val(),
        email: $('#newUserEmail').val()
    };

    $.ajax({
        url: API_URL,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(newUser),
        success: function (message) {
            alert(message);
            loadUsers();
        },
        error: function (xhr, status, error) {
            console.error('新しいユーザーの追加に失敗しました:', error);
        }
    });
}

// 删除用户
function deleteUser(e) {
    e.preventDefault();

    const userId = $('#deleteUserSelect').val();

    $.ajax({
        url: `${API_URL}/${userId}`,
        method: 'DELETE',
        success: function (message) {
            alert(message);
            loadUsers();
        },
        error: function (xhr, status, error) {
            console.error('ユーザー情報の削除に失敗しました:', error);
        }
    });
}

// 搜索用户信息
function searchUser() {
    const searchName = $('#searchUserName').val();

    $.ajax({
        url: `${API_URL}/search`,
        method: 'GET',
        data: { name: searchName },
        success: function (results) {
            const resultDiv = $('#searchResult');
            if (results.length > 0) {
                resultDiv.html(results.map(user => `ID: ${user.id}, 名前: ${user.username}`).join('<br>'));
            } else {
                resultDiv.text('該当するユーザーが見つかりません。');
            }
        },
        error: function (xhr, status, error) {
            console.error('検索エラー:', error);
        }
    });
}

// 初始加载用户
$(document).ready(function () {
    loadUsers();

    // 绑定事件
    $('#updateUserForm').on('submit', updateUser);
    $('#addUserForm').on('submit', addUser);
    $('#deleteUserForm').on('submit', deleteUser);
    $('#searchButton').on('click', searchUser);
});
