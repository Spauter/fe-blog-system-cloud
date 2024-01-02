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


var SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
var recognition = new SpeechRecognition();
recognition.lang = 'zh-CN';
recognition.interimResults = false;
recognition.maxAlternatives = 1;
recognition.onresult = function (event) {
    var yourComment = event.results[0][0].transcript
    console.log(event.results[0][0].transcript)
    //把结果显示在输入框上
    $('#reply').append(yourComment);
    //存储回复内容
}

function responseByMicrophone() {
    console.log("开始输入")
    layui.use('layer', function () {
        layer.msg("开始语音输入", {
            icon: 6,
            time: 1000
        })
        recognition.start();
    })

}

function stopinputByMicrophone() {
    console.log("停止输入")
    layui.use('layer', function () {
        layer.msg("停止语音输入", {
            icon: 6,
            time: 1000
        })
        recognition.stop();
    })
}

(()=>{
    window.onbeforeunload = (event) => {
        return "离开不会保存您的更改，是否继续？";
    }
})  (()=>{
    window.onbeforeunload = (event) => {
        return "离开不会保存您的更改，是否继续？";
    }
})