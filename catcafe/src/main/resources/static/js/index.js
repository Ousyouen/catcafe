$(function() {

    //模板自带的JS处理 不用管
	$("span.menu").click(function() {
		$(".top-menu").slideToggle("slow", function() {
			// Animation complete.
		});
	});

	$(window).load(function() {
		$("#flexiselDemo3").flexisel({
			visibleItems: 3,
			animationSpeed: 1000,
			autoPlay: true,
			autoPlaySpeed: 3000,
			pauseOnHover: true,
			enableResponsiveBreakpoints: true,
			responsiveBreakpoints: {
				portrait: {
					changePoint: 480,
					visibleItems: 3
				},
				landscape: {
					changePoint: 640,
					visibleItems: 3
				},
				tablet: {
					changePoint: 768,
					visibleItems: 3
				}
			}
		});

	});

	$(".swipebox").swipebox();

	$(".scroll").click(function(event) {
		event.preventDefault();
		$('html,body').animate({ scrollTop: $(this.hash).offset().top }, 1000);
	});

	
	// ①  AJAXを使用してホームページ情報を取得する
	$.ajax({
			type:'POST',
			url:"http://localhost:8080/index/indexDTO",
			//data:{name:stuName},
			success:function(res){
				// 获取画面カルーセルリスト（轮播图列表）
				var carouselList = res.carouselList;  // 这里是一个数组，包含多个图片的 URL
				// 获取店铺介绍
				var storeIntro = res.storeIntro;
				// 获取猫的介绍
				var catsIntro = res.catsIntro;
				// 获取猫的领养请求
				var catsAdoption = res.catsAdoption;
				// 获取猫的家庭
				var catsFamily = res.catsFamily;
				// 获取博客内容
				var blog = res.blog;
				// 获取猫的列表（猫的信息，每只猫包含名字、介绍、点赞数、图片等）
				var catsList = res.catsList;
				//②画面カルーセルリスト
				updateCarouselImages(carouselList);
				//④	ブログ詳細	
				updateBlogContent(catsList);
			},
			error:function(res){
				alert("系统故障");
			}
		})


	   
	   
})
//②画面カルーセルリスト
//初期化時にjQueryを使用して、画面カルーセルのタグ内のIDを選択し
function updateCarouselImages(carouselList) {
    // 获取所有的 <li> 元素（每个图片项）
    var $carouselItems = $('#flexiselDemo3 li');

    // 遍历 carouselList 和每个 <li> 元素
    $carouselItems.each(function(index) {
        // 获取当前 <li> 元素下的 <img> 标签
        var $img = $(this).find('img');

        // 确保 carouselList 中有对应的图片 URL
        if (carouselList[index]) {
            // 更新图片的 src 属性
            $img.attr('src', carouselList[index]);
        }
    });
}

//④	ブログ詳細	
function updateBlogContent(catsList) {
	// 获取页面中猫咪格子容器
	       const catGridContainer = $('.blog-grids');

	       // 清空现有的猫咪格子
	       catGridContainer.empty();

    // 遍历返回的 blogList，并动态生成每一篇博客的HTML
    catsList.forEach(function(item) {
		const blogGridHtml = `
		           <div class="blog-grids">
		               <div class="col-md-5 blog-grid-info">
		                   <img src="${item.catImage}" alt="${item.catName}">
		               </div>
		               <div class="col-md-3 blog-grid-info">
		                   <div class="blog-grid-text">
		                       <a href="single.html"><h3>${item.catName}</h3></a>
		                       <p>${item.catIntro}</p>
		                       <button class="like-button" id="likeButton">
		                           <i class="fas fa-thumbs-up"></i>
		                       </button>
		                       <span id="likeCount">${item.likesCount}</span> 
		                       <a class="read" href="single.html">猫の詳しい情報</a>
		                   </div>
		               </div>
		               <div class="clearfix"></div>
		           </div>
		       `;
		       // 将生成的HTML插入到catGridContainer中
		       catGridContainer.append(blogGridHtml);
    });
}