/**
 * 描述：说说js
 * @author  Ay
 * @date    2017/9/16.
 */

$(function(){

    $.get("/mood/getMoodById",
        function(data,status){
            $("#content").html(data.content);
            $("#publish_time").html(data.publish_time);
            $("#praise_num").html(data.praise_num);
     });


    $("#praise").click(function(){


    });

});

