// 获取表单元素
const updateForm = document.getElementById('updateForm');
const addForm = document.getElementById('addForm');
const deleteForm = document.getElementById('deleteForm');

// 提交更新表单
updateForm.addEventListener('submit', function (e) {
    e.preventDefault();
    alert('猫情報が変更されました');
});

// 提交新增猫表单
addForm.addEventListener('submit', function (e) {
    e.preventDefault();
    alert('新しい猫が追加されました');
});

// 提交删除猫表单
deleteForm.addEventListener('submit', function (e) {
    e.preventDefault();
    alert('猫情報が削除されました');
});
