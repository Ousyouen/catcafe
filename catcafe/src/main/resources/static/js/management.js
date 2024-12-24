$(document).ready(function() {
	let updateSuccess = false;  // 新增一个状态变量，确保更新成功只提示一次

	// 初次页面加载时，加载猫咪信息列表
	loadCatList(1);

	// 搜索功能
	$('#searchForm').submit(function(event) {
		event.preventDefault();
		const searchName = $('#searchName').val();
		const searchAge = $('#searchAge').val();
		searchCats(searchName, searchAge);
	});

	// 打开添加猫咪信息模态框
	$('#addCatButton').click(function() {
		$('#addCatModal').show();
	});

	// 关闭添加猫咪信息模态框
	$('#closeAddCatModal').click(function() {
		$('#addCatModal').hide();
	});

	// 提交新猫咪信息
	$('#addCatForm').submit(function(event) {
		event.preventDefault();
		const name = $('#addCatName').val();
		const intro = $('#addCatIntro').val();
		const age = $('#addCatAge').val();
		const image = $('#addCatImage')[0].files[0];
		addCatInfo(name, intro, age, image);
	});

	// 加载猫咪列表并进行分页
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

					// 页面分页逻辑
					$('#currentPage').text(data.currentPage);
					$('#totalPages').text(totalPages);
					$('#prevPage').prop('disabled', data.currentPage === 1);
					$('#nextPage').prop('disabled', data.currentPage === totalPages);

					$('#prevPage').click(function() {
						if (data.currentPage > 1) {
							loadCatList(data.currentPage - 1);
						}
					});

					$('#nextPage').click(function() {
						if (data.currentPage < totalPages) {
							loadCatList(data.currentPage + 1);
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

					// 更新猫咪信息
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

						// 禁用按钮以防止重复提交
						$(this).prop('disabled', true);

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
								if (!updateSuccess) {  // 如果未弹出提示框
									alert(data.message);  // 显示更新成功的提示
									updateSuccess = true;  // 设置为已弹过提示框
								}

								if (data.message === '猫の情報が更新されました') {
									$('#editCatModal').fadeOut(300);  // 关闭编辑模态框
									loadCatList(1);  // 更新猫咪列表
								}
							})
							.catch(error => {
								console.error('更新时发生错误:', error);
								alert('猫情報の更新に失敗しました');
							})
							.finally(() => {
								// 请求完成后重新启用按钮
								$('#submitEditCatForm').prop('disabled', false);
							});
					});

					// 删除按钮点击事件
					$('.deleteCat').click(function() {
						const catId = $(this).data('id');
						if (confirm("本当にこの猫を削除しますか？")) {
							deleteCatInfo(catId);
						}
					});

					// 查看猫咪详细信息
					$('.viewCat').click(function() {
						const catId = $(this).data('id');
						viewCatDetails(catId);
					});
				}
			});
	}

	// 搜索猫咪
	function searchCats(name, age) {
		const searchParams = new URLSearchParams();
		if (name) searchParams.append('name', name);
		if (age) searchParams.append('age', age);

		fetch(`/catManage/api/cats?${searchParams.toString()}`)
			.then(response => response.json())
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
				}
			})
			.catch(error => {
				alert("検索中にエラーが発生しました");
				console.error(error);
			});
	}

	// 添加猫咪信息
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

	// 删除猫咪信息
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

	// 查看猫咪详细信息
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

	// 关闭猫咪信息模态框
	$('#closeModal').click(function() {
		$('#catInfoModal').hide();
	});

	// 关闭编辑猫咪信息模态框
	$('#closeEditCatModal').click(function() {
		$('#editCatModal').fadeOut(300);
	});
});
