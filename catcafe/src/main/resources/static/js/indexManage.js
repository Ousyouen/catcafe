$.ajax({
			type:'POST',
			url:"http://localhost:8080/index/indexDTO",
			//data:{name:stuName},
			success:function(res){
				// 获取画面カルーセルリスト（轮播图列表）
				var carouselList = res.carouselList;  // 这里是一个数组，包含多个图片的 URL
				// 获取店铺介绍
				var storeIntro = res.storeIntro;
				$("#storeIntr").text(storeIntro);
				// 获取猫的介绍
				var catsIntro = res.catsIntro;
				$("#catsIntr").text(catsIntro)
				// 获取猫的领养请求
				var catsAdoption = res.catsAdoption;
				$("#adoptionInfo").text(catsAdoption)
				// 获取猫的家庭
				var catsFamily = res.catsFamily;
				$("#catsFamil").text(catsFamily);
				// 获取博客内容
				var blog = res.blog;
				$("#blogContent").text(blog);
				// 获取猫的列表（猫的信息，每只猫包含名字、介绍、点赞数、图片等）
				var catsList = res.catsList;
				//②画面カルーセルリスト
				updateCarouselImages(carouselList);
				
				}})
				
				
				
				
				
