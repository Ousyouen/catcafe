$(document).ready(function() {
	// 初始加载第一页猫咪信息
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

	// 加载猫咪信息列表，分页处理
	function loadCatList(page) {
		fetch(`/catManage/api/cats?page=${page}&pageSize=4`)
			.then(response => response.json())
			.then(data => {
				const cats = data.cats;
				const totalPages = data.totalPages;
				const tableBody = $('#catListBody');
				tableBody.empty();

				if (cats.length === 0) {
					tableBody.append('<tr><td colspan="6">猫咪信息为空。</td></tr>');
				} else {
					cats.forEach(cat => {
						const row = `
                            <tr>
                                <td>${cat.catName}</td>
                                <td>${cat.catIntro}</td>
                                <td>${cat.catAge}</td>
                                <td>${cat.likesCount}</td>
                                <td><button class="viewCat" data-id="${cat.id}">详情</button></td>
                                <td><button class="editCat" data-id="${cat.id}">编辑</button></td>
                                <td><button class="deleteCat" data-id="${cat.id}">删除</button></td>
                            </tr>
                        `;
						tableBody.append(row);
					});

					// 分页逻辑
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

					// 编辑按钮
					$('.editCat').click(function() {
						const catId = $(this).data('id');
						fetch(`/catManage/api/cat/${catId}`)
							.then(response => response.json())
							.then(data => {
								$('#editCatName').val(data.catName);
								$('#editCatIntro').val(data.catIntro);
								$('#editCatAge').val(data.catAge);
								$('#editCatImagePreview').attr('src', data.catImage);
								$('#editCatModal').fadeIn(300);
							});
					});

					// 删除按钮
					$('.deleteCat').click(function() {
						const catId = $(this).data('id');
						if (confirm("确定删除这个猫咪吗？")) {
							deleteCatInfo(catId);
						}
					});

					// 查看详细信息按钮
					$('.viewCat').click(function() {
						const catId = $(this).data('id');
						viewCatDetails(catId);
					});
				}
			});
	}

	// 搜索猫咪信息
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
					tableBody.append('<tr><td colspan="6">没有找到匹配的猫咪。</td></tr>');
				} else {
					cats.forEach(cat => {
						const row = `
                            <tr>
                                <td>${cat.catName}</td>
                                <td>${cat.catIntro}</td>
                                <td>${cat.catAge}</td>
                                <td>${cat.likesCount}</td>
                                <td><button class="viewCat" data-id="${cat.id}">详情</button></td>
                                <td><button class="editCat" data-id="${cat.id}">编辑</button></td>
                                <td><button class="deleteCat" data-id="${cat.id}">删除</button></td>
                            </tr>
                        `;
						tableBody.append(row);
					});
				}
			})
			.catch(error => {
				alert("搜索时发生错误");
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
				if (data.message === '猫咪信息添加成功') {
					$('#addCatModal').fadeOut(300);
					loadCatList(1);
				}
			})
			.catch(error => {
				alert("添加猫咪信息时发生错误");
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
				alert("删除猫咪信息时发生错误");
				console.error(error);
			});
	}

	// 查看猫咪详情
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
				alert("获取猫咪信息失败");
				console.error(error);
			});
	}

	// 关闭猫咪信息详情模态框
	$('#closeModal').click(function() {
		$('#catInfoModal').hide();
	});

	// 关闭编辑猫咪信息模态框
	$('#closeEditCatModal').click(function() {
		$('#editCatModal').fadeOut(300);
	});
});
