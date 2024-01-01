let commentId = window.location.href.split('=')[1].split('_')[2];

$(function () {
    reply_content_load()
})


function add() {
    if (loginUser == null) {
        layer.msg("请登录后再回复", {
            iron: 6,
            time: 1000
        })
        return;
    }
    $.ajax({
        success: function () {
//TODO 添加回复
        },
        error: function () {
            layer.msg("未知错误,请稍后再评论", {
                iron: 5,
                time: 1000
            })
        }
    })
}

function reply_content_load() {
    $.ajax({
        type: 'GET',
        url: 'fe-ornament/findResponseByCommentId',
        data: {
            cid: commentId,
        },
        dataType: "json",
        success: function (res) {
            if (res.code !== 200) {
                $('.reply_comment').empty().append(`<div class="comment_item"> <span style="color: #00B894;font-weight: bold;">暂无回复</span> 抢占沙发成为评论第一人`);
                layer.msg(res.msg, {
                    iron: 2,
                    time: 1000
                })
                return;
            }
            let data = res.data;
            let reply_list = [];
            for (let i = 0; i < data.length; i++) {
                let element = `<div class="comment_item" id='reply_${data[i]['rid']}'> <span style="color: #00B894;font-weight: bold;">${data[i]['account']}：</span> ${data[i]['content']}</div>`
                reply_list.push(element);
            }
            $('.reply_comment').empty().append(reply_list.join(''));
        }, error: function () {
            layer.msg("请求出错，即将返回首页");
            location.href = "../index.html"
        }
    });
    $.ajax({
        type: 'GET',
        url: '/fe-ornament/getCommentedUser',
        data: {
            cid: commentId,
        },
        success: function (res) {
            let user = res.data.user;
            let comment = res.data.comment;
            $('.comment_avatar>img').attr("src", user.avatar)
            $('.nav_title>h2').text(user.account);
            $('.nav_content>h3').text("评论:" + comment.content)
        },
        error: function (res) {

        }
    })
}

