  $(document).ready(function() {
        // 点赞按钮点击事件
        $("#likeButton").click(function() {
            var likeCount = parseInt($("#likeCount").text());
            var newLikeCount = likeCount + 1;  

           
            $.ajax({
                url: "/like", 
                type: "POST", 
                data: { likeCount: newLikeCount },  
                success: function(response) {
                   
                    $("#likeCount").text(response.newLikeCount);  
                },
                error: function() {
                    alert("点赞失败，请稍后再试！");
                }
            });
        });
    })