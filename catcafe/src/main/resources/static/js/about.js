// 菜单按钮点击事件，显示或隐藏顶部菜单
$("span.menu").click(function () {
    $(".top-menu").slideToggle("slow", function () {
        // 动画完成后回调函数
    });
});

// 平滑滚动到目标区域
jQuery(document).ready(function ($) {
    $(".scroll").click(function (event) {
        event.preventDefault();
        $("html,body").animate({ scrollTop: $(this.hash).offset().top }, 1000);
    });
});

// 页面加载后启用返回顶部功能
$(document).ready(function () {
    $().UItoTop({ easingType: 'easeOutQuart' });
});

addEventListener("load", function () {
    setTimeout(hideURLbar, 0);
}, false);

function hideURLbar() {
    window.scrollTo(0, 1);
}


