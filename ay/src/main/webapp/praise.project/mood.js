/**
 * 描述：说说js
 * @author  Ay
 * @date    2017/9/16.
 */

$(function(){

    /**
     * 页面初始化加载说说
     */
    $.get("/mood/getMoodById",
        function(data,status){
            $("#content").html(data.content);
            $("#publish_time").html(data.publish_time);
            $("#praise_num").html(data.praise_num);
            $("#account").html(data.account);
     });


    /**
     * 点赞方法
     */
    $("#praise").click(function(){
        $.ajaxSetup({
            contentType : 'application/json'
        });
        var url = "/mood/praiseForRedis";
        var data = {
            user_id:'260f1edc831b4c1f9c9122fb4330f93a',
            mood_id:'67ff4377040248409ff49eb2ab90203f'
        };
        //注意点
        //1.dataType: 'application/json'
        //2.data: JSON.stringify(data)   这句话很重要，如果不写的
        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(data),
            dataType: "json",
            success: function () {
                alert(123);
            },
            dataType: 'application/json'
        });
        alert("点赞成功");

    });

});

