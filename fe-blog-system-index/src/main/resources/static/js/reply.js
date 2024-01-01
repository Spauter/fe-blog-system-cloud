$(function (){
    reply_content_load()
})


function load(){

}

function reply_content_load(){
    let commentId=window.location.href.split('=')[1];
    $.ajax({
        type:'GET',
        url:'fe-ornament/findResponseByCommentId',
        data:{
          cid:commentId.split('_')[2],
        },
        dataType:"json",
        success:function (res){
            if(res.code!==200){
                $('.reply_comment').empty().append(`<li class="comment_item"'> <span style="color: #00B894;font-weight: bold;">暂无回复</span> 抢占沙发成为评论第一人`);
                layer.msg(res.msg,{
                    iron:2,
                    time:1000
                })
                return;
            }
            let data=res.data;
            let reply_list=[];
            for(let i=0;i<data.length;i++){
                let element=`<li class="comment_item" id='reply_${data[i]['rid']}'> <span style="color: #00B894;font-weight: bold;">${data[i]['account']}：</span> ${data[i]['content']}`
                reply_list.push(element);
            }
            $('.reply_comment').empty().append(reply_list.join(''));
        },error:function (){
            $('.reply_comment').empty().append("加载回复列表失败");
        }
    })
}

