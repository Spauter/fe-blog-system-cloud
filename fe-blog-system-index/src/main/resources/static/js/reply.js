let commentId = window.location.href.split('=')[1].split('_')[2];
let loginUser;
$(function () {
    reply_content_load()
    userInfoLoad();
})


function auditResponse() {
    if (loginUser == null) {
        layui.use('layer', function () {
            layer.msg("请登录后再回复", {
                icon: 6,
                time: 1000
            })
        });
        return;
    }
    const str = Math.random().toString(36).slice(2);
    let content = $('#reply').val()
    if (content === '' || content == null) {
       layui.use('layer',function (){
           layer.msg("请输入内容", {
               icon: 2,
               time: 1000
           })
       })
        return;
    }
    $.ajax({
        type: 'POST',
        url: 'https://gtf.ai.xingzheai.cn/v2.0/game_chat_ban/detect_text',
        data: {
            'token': 'LUJGYW0SB7KHIOZN',
            'data_id': str,
            'context_type': 'post',
            'context': content,
            'suggestion': '',
            'label': ''
        },
        dataType: 'json',
        success: function (data) {
            console.log(data);
            let sug = data.data.suggestion;
            if (sug !== "pass") {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("含有非法词汇,发布失败", {
                        icon: 2,
                        time: 2000
                    })
                })
            } else {
                add(content);
            }
        }, error: function () {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("发布失败,请稍后再试", {
                    icon: 2,
                    time: 2000
                })
            })
        }
    })
}

function add(content) {
    $.ajax({
        type: 'POST',
        url: 'fe-ornament/addResponse',
        data: {
            'content': content,
            'cid': commentId,
        },
        dataType: 'json',
        success: function (res) {
            layer.msg(res.msg, {
                iron: 6,
                time: 1000,
            })
            $('#reply').text("");
            reply_content_load();
        },
        error: function () {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("未知错误,请稍后再试", {
                    icon: 2,
                    time: 2000
                })
            })
        }
    })
}

function reply_content_load() {
    layui.use('laypage',function (){
        let laypage=layui.laypage;
        laypage.render({
            elem:'reply_page',
            count:100,
            limit:10,
            jump:function (obj,first){
                let curr = obj.curr;
                $.ajax({
                    type: 'GET',
                    url: 'fe-ornament/findResponseByCommentId',
                    data: {
                        'cid': commentId,
                        'page':(curr-1)*10,
                        'size':10
                    },
                    dataType: "json",
                    success: function (res) {
                        if (res.code !== 200) {
                            layui.use('layer', function () {
                                let layer = layui.layer;
                                layer.msg(res.msg, {
                                    icon: 6,
                                    time: 2000
                                })
                            })
                            $('.reply_comment').empty();
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
            }
        })
    })
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

(() => {
    window.onbeforeunload = (event) => {
        return "离开不会保存您的更改，是否继续？";
    }
})(() => {
    window.onbeforeunload = (event) => {
        return "离开不会保存您的更改，是否继续？";
    }
})

//到这里loginUser的周期已经结束了,再写的一个
function userInfoLoad() {
    $.ajax({
        type: 'GET',
        url: '/fe-blog/SelectUserServlet',
        data: {},
        dataType: 'json',
        success: function (res) {
            let code = res.code;
            console.log(res);
            loginUser = res.data; // 将值赋给全局变量
        }
    })
}