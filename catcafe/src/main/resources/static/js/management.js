$(document).ready(function() {
	let updateSuccess = false;  // 更新が成功した場合、通知が一度だけ表示されるようにするための状態変数

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

					// ページネーションのロジック
					$('#currentPage').text(data.currentPage);
					$('#totalPages').text(totalPages);
					$('#prevPage').prop('disabled', data.currentPage === 1);
					$('#nextPage').prop('disabled', data.currentPage === totalPages);

					$('#prevPage').off('click').click(function () {
					    if (data.currentPage > 1) {
					        loadCatList(data.currentPage - 1); // 跳转到上一页
					    }
					});

					$('#nextPage').off('click').click(function () {
					    if (data.currentPage < totalPages) {
					        loadCatList(data.currentPage + 1); // 跳转到下一页
					    }
					});

					// 編集ボタンのクリックイベント
					$('.editCat').click(function() {
						const catId = $(this).data('id');
						fetch(`/catManage/api/cat/${catId}`)
							.then(response => response.json())
							.then(data => {
								// 編集フォームにデータを埋める
								$('#editCatId').val(data.id);
								$('#editCatName').val(data.catName);
								$('#editCatIntro').val(data.catIntro);
								$('#editCatAge').val(data.catAge);
								$('#editCatImagePreview').attr('src', data.catImage);
								$('#editCatModal').fadeIn(300);
							});
					});

					// 猫情報の更新
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

						// ボタンを無効にして重複送信を防ぐ
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
								if (!updateSuccess) {  // まだ通知が表示されていない場合
									alert(data.message);  // 更新成功の通知を表示
									updateSuccess = true;  // 通知を表示済みとして設定
								}

								if (data.message === '猫の情報が更新されました') {
									$('#editCatModal').fadeOut(300);  // 編集モーダルを閉じる
									loadCatList(1);  // 猫情報リストを更新
								}
							})
							.catch(error => {
								console.error('更新中にエラーが発生しました:', error);
								alert('猫情報の更新に失敗しました');
							})
							.finally(() => {
								// リクエストが完了した後、ボタンを再度有効にする
								$('#submitEditCatForm').prop('disabled', false);
							});
					});

					// 削除ボタンのクリックイベント
					$('.deleteCat').click(function() {
						const catId = $(this).data('id');
						if (confirm("本当にこの猫を削除しますか？")) {
							deleteCatInfo(catId);
						}
					});

					// 猫情報の詳細表示
					$('.viewCat').click(function() {
						const catId = $(this).data('id');
						viewCatDetails(catId);
					});
				}
			});
	}


	// 猫情報の追加
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

	// 猫情報の削除
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

	// 猫情報の詳細表示
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

	// 猫情報モーダルを閉じる
	$('#closeModal').click(function() {
		$('#catInfoModal').hide();
	});

	// 猫情報編集モーダルを閉じる
	$('#closeEditCatModal').click(function() {
		$('#editCatModal').fadeOut(300);
	});
});
