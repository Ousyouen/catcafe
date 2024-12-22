// 动态加载猫咪列表到选择框
document.addEventListener('DOMContentLoaded', function() {
	const catSelect = document.getElementById('catSelect');
	const deleteSelect = document.getElementById('deleteSelect');
	const catSelectSearch = document.getElementById('catSelectSearch');

	// 获取所有猫咪信息
	fetch("/catManage/api/cats")
		.then(response => response.json())
		.then(data => {
			if (Array.isArray(data)) {
				// 清空现有选项
				catSelect.innerHTML = '';
				deleteSelect.innerHTML = '';
				catSelectSearch.innerHTML = '';

				// 遍历猫咪数据，添加到下拉框
				data.forEach(cat => {
					// 为每个下拉框分别创建新的 option 元素
					const catOption = document.createElement("option");
					catOption.value = cat.id;
					catOption.textContent = cat.catName;

					const deleteOption = document.createElement("option");
					deleteOption.value = cat.id;
					deleteOption.textContent = cat.catName;

					const catSelectSearchOption = document.createElement("option");
					catSelectSearchOption.value = cat.id;
					catSelectSearchOption.textContent = cat.catName;

					// 添加到各自的下拉框
					catSelect.appendChild(catOption);
					deleteSelect.appendChild(deleteOption);
					catSelectSearch.appendChild(catSelectSearchOption);
				});
			} else {
				console.error("返回的数据不是一个数组：", data);
			}
		})
		.catch(error => {
			alert("猫咪情報の取得に失敗しました");
			console.error(error);
		});
});

// 处理选择猫咪后加载猫咪信息到输入框
document.getElementById('catSelect').addEventListener('change', function() {
	const catId = this.value;

	if (!catId) return;

	// 获取猫咪信息
	fetch(`/catManage/api/cat/${catId}`)
		.then(response => response.json())
		.then(data => {
			// 填充输入框
			document.getElementById('catName').value = data.catName;
			document.getElementById('catPersonality').value = data.catIntro;
			document.getElementById('catAge').value = data.catAge;

			// 显示原始图片（如果有）
			const catImage = data.catImage;
			const imageContainer = document.getElementById('imageContainer'); // 这个div用来显示原图片
			if (catImage) {
				imageContainer.innerHTML = `<img src="/${catImage}" alt="猫咪图片" width="100" />`;
			} else {
				imageContainer.innerHTML = ''; // 没有图片时清空
			}
		})
		.catch(error => {
			console.error("获取猫咪信息失败：", error);
		});
});


// 处理更新猫咪信息
const updateForm = document.getElementById('updateForm');
updateForm.addEventListener('submit', function(event) {
	event.preventDefault();

	const catId = document.getElementById('catSelect').value;
	const catName = document.getElementById('catName').value;
	const catPersonality = document.getElementById('catPersonality').value;
	const catAge = document.getElementById('catAge').value;
	const catImage = document.getElementById('catImage').files[0];  // 获取选择的图片文件

	// 年龄判断
	if (catAge === "" || catAge < 0) {
		alert("请输入有效的年龄！");
		return;
	}

	// 动态构建要更新的字段
	const formData = new FormData();
	formData.append('id', catId);

	// 只有当字段有变化时才提交
	if (catName) {
		formData.append('name', catName);
	}
	if (catPersonality) {
		formData.append('personality', catPersonality);
	}
	if (catAge) {
		formData.append('age', catAge);
	}

	// 只有在选择新图片时，才提交图片字段
	if (catImage) {
		formData.append('image', catImage);
	}

	// 发送更新请求
	fetch("/catManage/update", {
		method: 'PUT',
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			if (data.message === "猫咪信息更新成功") {
				alert("猫咪信息更新成功！");
				window.location.reload();  // 更新成功后自动刷新页面
			} else {
				alert("猫咪信息更新失败！");
			}
		})
		.catch(error => {
			alert("猫咪信息更新失败！");
			console.error(error);
		});
});

//删除猫咪
const deleteForm = document.getElementById('deleteForm');
deleteForm.addEventListener('submit', function(event) {
	event.preventDefault();

	const catId = document.getElementById('deleteSelect').value;

	// 发送删除请求
	fetch(`/catManage/delete/${catId}`, {
		method: 'DELETE'
	})
		.then(response => response.json())
		.then(data => {
			if (data.message === "猫咪信息删除成功") {
				alert("猫咪信息删除成功！");

				// 删除成功后刷新页面
				window.location.reload();  // 这行代码会重新加载当前页面
			} else {
				alert("猫咪信息删除失败！");
			}
		})
		.catch(error => {
			alert("猫咪信息删除失败！");
			console.error(error);
		});
});

//新增猫咪
const addForm = document.getElementById('addForm');
addForm.addEventListener('submit', function(event) {
	event.preventDefault();

	const newCatName = document.getElementById('newCatName').value;
	const newCatPersonality = document.getElementById('newCatPersonality').value;
	const newCatAge = document.getElementById('newCatAge').value;
	const newCatImage = document.getElementById('newCatImage').files[0];

	// 验证表单是否填写完整
	if (!newCatName || !newCatPersonality || !newCatAge) {
		alert("请输入其他信息");
		return;
	}

	// 将年龄转换为数字类型，并验证其有效性
	const age = parseInt(newCatAge, 10);
	if (isNaN(age) || age <= 0 || age > 70) {
		alert("请输入有效的年龄！年龄应在1到70岁之间");
		return;
	}

	const formData = new FormData();
	formData.append('name', newCatName);
	formData.append('personality', newCatPersonality);
	formData.append('age', age); // 确保传递的是数字类型
	if (newCatImage) {
		formData.append('image', newCatImage);  // 上传图片
	}

	// 发送新增请求
	fetch("/catManage/add", {
		method: 'POST',
		body: formData
	})
		.then(response => {
			// 检查响应是否成功
			if (!response.ok) {
				throw new Error('服务器错误，新增猫咪信息失败');
			}
			return response.json();
		})
		.then(data => {
			// 处理新增成功后的逻辑
			alert(data.message);

			if (data.message === "猫咪信息新增成功") {
				// 自动刷新页面
				window.location.reload();
			}
		})
		.catch(error => {
			// 捕获错误并弹出警告
			alert("猫咪信息新增失败！");
			console.error(error);
		});
});


// 处理猫咪信息查询
document.addEventListener('DOMContentLoaded', function() {
	const catSelectSearch = document.getElementById('catSelectSearch');
	const catInfoModal = document.getElementById('catInfoModal');
	const modalCatName = document.getElementById('modalCatName');
	const modalCatIntro = document.getElementById('modalCatIntro');
	const modalCatAge = document.getElementById('modalCatAge');
	const modalLikesCount = document.getElementById('modalLikesCount');
	const modalCatImage = document.getElementById('modalCatImage');
	const closeModal = document.getElementById('closeModal');

	// 获取猫咪列表并添加到下拉框
	fetch("/catManage/api/cats")
		.then(response => response.json())
		.then(data => {
			if (Array.isArray(data)) {
				// 清空下拉框内容
				catSelectSearch.innerHTML = '';
				data.forEach(cat => {
					const option = document.createElement('option');
					option.value = cat.id;
					option.textContent = cat.catName;
					catSelectSearch.appendChild(option);
				});
			}
		})
		.catch(error => {
			console.error('猫咪情報の取得に失敗しました', error);
		});

	// 选择猫咪后展示猫咪信息在模态框中
	catSelectSearch.addEventListener('change', function() {
		const catId = this.value;
		if (!catId) return;

		// 请求猫咪详细信息
		fetch(`/catManage/api/cat/${catId}`)
			.then(response => response.json())
			.then(data => {
				console.log('Cat info received:', data);

				// 获取猫咪的基本信息
				const catName = data.catName || '未知';
				const catIntro = data.catIntro || '未提供';
				const catAge = data.catAge || '未知';
				const likesCount = data.likesCount || '0';
				const catImage = data.catImage || ''; // 获取猫咪的图片URL

				// 更新模态框中的信息
				modalCatName.textContent = catName;
				modalCatIntro.textContent = catIntro;
				modalCatAge.textContent = catAge;
				modalLikesCount.textContent = likesCount;

				// 如果猫咪有图片，更新图片的URL
				if (catImage) {
					modalCatImage.src = `/${catImage}`;
					modalCatImage.style.display = 'block'; // 显示图片
				} else {
					modalCatImage.style.display = 'none'; // 如果没有图片，则隐藏
				}

				// 显示模态框
				catInfoModal.style.display = 'flex';
			})
			.catch(error => {
				console.error('获取猫咪信息失败：', error);
				alert('猫咪情報の取得に失敗しました');
			});
	});

	// 关闭模态框
	closeModal.addEventListener('click', function() {
		catInfoModal.style.display = 'none';
	});
});
