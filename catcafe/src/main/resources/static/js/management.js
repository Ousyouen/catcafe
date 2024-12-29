$(document).ready(function() {
    let updateSuccess = false;  // 更新成功标志，确保通知只显示一次

    // 初回ページ読み込み時に猫情報リストを読み込む
    loadCatList(1);

    // 検索機能
    $('#searchForm').submit(function(event) {
        event.preventDefault();
        const searchName = $('#searchName').val();
        const searchAge = $('#searchAge').val();
        searchCats(searchName, searchAge);
    });

    // 猫情報の検索関数
    function searchCats(name, age) {
        const searchParams = new URLSearchParams();
        if (name) searchParams.append('name', name);  // 名前が空でない場合、検索条件に追加
        if (age) searchParams.append('age', age);     // 年齢が空でない場合、検索条件に追加

        fetch(`/catManage/search/api/cats?${searchParams.toString()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('ネットワークエラー');
                }
                return response.json();
            })
            .then(data => {
                const cats = data.cats;
                const tableBody = $('#catListBody');
                tableBody.empty();

                if (cats.length === 0) {
                    tableBody.append('<tr><td colspan="6">一致する猫は見つかりませんでした。</td></tr>');
                } else {
                    cats.forEach(cat => {
                        const row = `
                            <tr>
                                <td>${cat.catName}</td>
                                <td>${cat.catIntro}</td>
                                <td>${cat.catAge}</td>
                                <td>${cat.likesCount}</td>
                                <td><button class="viewCat" data-id="${cat.id}">詳細</button></td>
                                <td><button class="editCat" data-id="${cat.id}">編集</button></td>
                                <td><button class="deleteCat" data-id="${cat.id}">削除</button></td>
                            </tr>
                        `;
                        tableBody.append(row);
                    });

                    // 更新分页信息
                    const totalPages = data.totalPages;
                    $('#currentPage').text(1);  // 当前页重置为1
                    $('#totalPages').text(totalPages);  // 总页数
                    $('#prevPage').prop('disabled', true);  // 上一页按钮禁用
                    $('#nextPage').prop('disabled', totalPages <= 1);  // 下一页按钮禁用

                    // 设置点击“上一页”和“下一页”的事件
                    $('#prevPage').off('click').click(function() {
                        if (data.currentPage > 1) {
                            loadCatList(data.currentPage - 1);
                        }
                    });

                    $('#nextPage').off('click').click(function() {
                        if (data.currentPage < totalPages) {
                            loadCatList(data.currentPage + 1);
                        }
                    });
                }
            })
            .catch(error => {
                alert("検索中にエラーが発生しました");
                console.error(error);
            });
    }

    // 猫情報追加のモーダルを開く
    $('#addCatButton').click(function() {
        $('#addCatModal').show();
    });

    // 猫情報追加のモーダルを閉じる
    $('#closeAddCatModal').click(function() {
        $('#addCatModal').hide();
    });

    // 新しい猫情報を送信
    $('#addCatForm').submit(function(event) {
        event.preventDefault();
        const name = $('#addCatName').val();
        const intro = $('#addCatIntro').val();
        const age = $('#addCatAge').val();
        const image = $('#addCatImage')[0].files[0];
        addCatInfo(name, intro, age, image);
    });

    // 猫情報リストをページネーション付きで読み込む
    function loadCatList(page) {
        fetch(`/catManage/api/cats?page=${page}&pageSize=4`)
            .then(response => response.json())
            .then(data => {
                const cats = data.cats;
                const totalPages = data.totalPages;
                const tableBody = $('#catListBody');
                tableBody.empty();

                if (cats.length === 0) {
                    tableBody.append('<tr><td colspan="6">猫の情報はありません。</td></tr>');
                } else {
                    cats.forEach(cat => {
                        const row = `
                            <tr>
                                <td>${cat.catName}</td>
                                <td>${cat.catIntro}</td>
                                <td>${cat.catAge}</td>
                                <td>${cat.likesCount}</td>
                                <td><button class="viewCat" data-id="${cat.id}">詳細</button></td>
                                <td><button class="editCat" data-id="${cat.id}">編集</button></td>
                                <td><button class="deleteCat" data-id="${cat.id}">削除</button></td>
                            </tr>
                        `;
                        tableBody.append(row);
                    });

                    // 更新分页信息
                    $('#currentPage').text(data.currentPage);
                    $('#totalPages').text(totalPages);
                    $('#prevPage').prop('disabled', data.currentPage === 1);
                    $('#nextPage').prop('disabled', data.currentPage === totalPages);

                    $('#prevPage').off('click').click(function () {
                        if (data.currentPage > 1) {
                            loadCatList(data.currentPage - 1);  // 跳转到上一页
                        }
                    });

                    $('#nextPage').off('click').click(function () {
                        if (data.currentPage < totalPages) {
                            loadCatList(data.currentPage + 1);  // 跳转到下一页
                        }
                    });

                    // 编辑按钮点击事件
                    $('.editCat').click(function() {
                        const catId = $(this).data('id');
                        fetch(`/catManage/api/cat/${catId}`)
                            .then(response => response.json())
                            .then(data => {
                                // 填充编辑表单
                                $('#editCatId').val(data.id);
                                $('#editCatName').val(data.catName);
                                $('#editCatIntro').val(data.catIntro);
                                $('#editCatAge').val(data.catAge);
                                $('#editCatImagePreview').attr('src', data.catImage);
                                $('#editCatModal').fadeIn(300);
                            });
                    });

                    // 更新猫信息
                    $('#submitEditCatForm').click(function() {
                        const id = $('#editCatId').val();
                        if (!id) {
                            alert('猫のIDが見つかりません');
                            return;
                        }

                        const name = $('#editCatName').val();
                        const intro = $('#editCatIntro').val();
                        const age = $('#editCatAge').val();
                        const image = $('#editCatImage')[0].files[0];

                        $(this).prop('disabled', true); // 防止重复提交

                        const formData = new FormData();
                        formData.append('id', id);
                        formData.append('name', name);
                        formData.append('personality', intro);
                        formData.append('age', age);
                        if (image) formData.append('image', image);

                        fetch('/catManage/update', {
                            method: 'PUT',
                            body: formData
                        })
                            .then(response => response.json())
                            .then(data => {
                                if (!updateSuccess) {  // 还没有显示过通知
                                    alert(data.message);  // 显示更新成功通知
                                    updateSuccess = true;  // 设置为已显示
                                }

                                if (data.message === '猫の情報が更新されました') {
                                    $('#editCatModal').fadeOut(300);  // 关闭编辑模态框
                                    loadCatList(1);  // 更新猫信息列表
                                }
                            })
                            .catch(error => {
                                console.error('更新中にエラーが発生しました:', error);
                                alert('猫情報の更新に失敗しました');
                            })
                            .finally(() => {
                                $('#submitEditCatForm').prop('disabled', false);  // 重新启用提交按钮
                            });
                    });

                    // 删除猫信息
                    $('.deleteCat').click(function() {
                        const catId = $(this).data('id');
                        if (confirm("本当にこの猫を削除しますか？")) {
                            deleteCatInfo(catId);
                        }
                    });

                    // 查看猫信息
                    $('.viewCat').click(function() {
                        const catId = $(this).data('id');
                        viewCatDetails(catId);
                    });
                }
            });
    }

    // 添加猫信息
    function addCatInfo(name, intro, age, image) {
        const formData = new FormData();
        formData.append('name', name);
        formData.append('personality', intro);
        formData.append('age', age);
        if (image) formData.append('image', image);

        fetch('/catManage/add', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                if (data.message === '猫の情報が追加されました') {
                    $('#addCatModal').fadeOut(300);
                    loadCatList(1);
                }
            })
            .catch(error => {
                alert("猫の情報追加中にエラーが発生しました");
                console.error(error);
            });
    }

    // 删除猫信息
    function deleteCatInfo(id) {
        fetch(`/catManage/delete/${id}`, {
            method: 'DELETE'
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
                loadCatList(1);
            })
            .catch(error => {
                alert("猫の情報削除中にエラーが発生しました");
                console.error(error);
            });
    }

    // 查看猫详细信息
    function viewCatDetails(id) {
        fetch(`/catManage/api/cat/${id}`)
            .then(response => response.json())
            .then(data => {
                $('#modalCatName').text(data.catName);
                $('#modalCatIntro').text(data.catIntro);
                $('#modalCatAge').text(data.catAge);
                $('#modalLikesCount').text(data.likesCount);
                $('#modalCatImage').attr('src', data.catImage);
                $('#catInfoModal').show();
            })
            .catch(error => {
                alert("猫の情報を取得できませんでした");
                console.error(error);
            });
    }

    // 关闭猫信息模态框
    $('#closeModal').click(function() {
        $('#catInfoModal').hide();
    });

    // 关闭编辑猫信息模态框
    $('#closeEditCatModal').click(function() {
        $('#editCatModal').fadeOut(300);
    });
});
