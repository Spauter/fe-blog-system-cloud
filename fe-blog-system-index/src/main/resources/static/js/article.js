let loginUser = null;
$(function () {
    content_load();
})


function load() {

    setTimeout(function () {
        $('.load_cover').animate({
            'top': '-100%'
        }, 100, function () {
            $(document.body).css({
                'overflow': 'auto'
            })
        })
    }, 200)
}

function content_load() {
    let blogId = window.location.href.split('=')[1];
    $.ajax({
        type: 'GET',
        url: '/fe-blog/DetailBlogServlet',
        data: {'blogId': blogId},
        dataType: 'json',
        success: function (res) {
            let msg = res.data;
            console.log(msg);
            let blog = msg.blog;
            $('#set_title').text(blog.title);
            $('.time').text(blog.createTime);
            $('.update').text(blog.updateTime);
            let element = `${blog.content.split('￥')[0]}`
            $('.article_content').empty().append(element);
            $('.author').text(blog.author);
            $('.field').text(res.data.field);
            $('.clicks').text(blog.clicks)
            let tags = [];
            for (let i = 0; i < msg.tags.length; i++) {
                tags.push(msg.tags[i]['name']);
            }
            $('.tags').append(tags.join(' | '));
            load();
            commentAdd(blog.blogId);
            findAllComment(blog.blogId);
        },
        error: function () {
            console.log('请求出错');
        }
    })


}


function commentAdd(blogId) {
    $('.comment_btn').on('click', function () {
        let data = {
            'blog_id': blogId,
            'content': $('#comment').val(),
        }
        if(loginUser==null){
            layer.msg("请登陆后评论", {
                icon: 6,
                time: 1000
            })
            return;
        }
        if (data.content === '' || data.content == null) {
            layer.msg("请输入评论", {
                icon: 2,
                time: 1000
            })
            return;
        }
        const str = Math.random().toString(36).slice(2);
        $.ajax({
            type: 'POST',
            url: 'https://gtf.ai.xingzheai.cn/v2.0/game_chat_ban/detect_text',
            data: {
                'token': 'LUJGYW0SB7KHIOZN',
                'data_id': str,
                'context_type': 'post',
                'context': data.content,
                'suggestion': '',
                'label': ''
            },
            dataType: 'json',
            success: function (res) {
                console.log(res);
                let sug = res.data.suggestion;
                if (sug !== "pass") {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg("含有非法词汇,发布失败", {
                            icon: 2,
                            time: 2000
                        })
                    })
                } else {
                    addComment(data);
                }
            }, error: function () {
                layer.msg("提交失败，请稍后再试", {
                    icon: 2,
                    time: 1000,
                })
            }
        })
    })
}

function addComment(data) {
    $.ajax({
        type: 'POST',
        url: '/fe-ornament/AddCommentServlet',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (res) {
            console.log(res.msg);
            layui.use('layer', function () {
                layer.msg(res.msg, {
                    icon: 6,
                    time: 1000
                })
                if (res.code === 200) {
                    $('#comment').val('');
                }
                findAllComment(data.blog_id);
            })
        }
    })
}


var SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
var recognition = new SpeechRecognition();
recognition.lang = 'zh-CN';
recognition.continuous=false;
recognition.interimResults = false;
recognition.maxAlternatives = 1;
// var app = new Vue({
//     el: '#comment',
//     data: {
//         message: ''
//     }
// })
recognition.onresult = function (event) {
    var yourComment = event.results[0][0].transcript
    console.log(event.results[0][0].transcript)
    //把结果显示在输入框上
    app.message += yourComment;
    console.log("输入的内容为：" + app.message);
    //window.location.reload("#comment");
    }

function commentByMicrophone() {
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

function findAllComment(blogId) {
    layui.use('laypage', function () {
        let laypage = layui.laypage;
        laypage.render({
            elem: 'commentPage',
            count: 100,
            limit: 20,
            jump: function (obj, first) {
                let curr = obj.curr;
                $.ajax({
                    type: 'GET',
                    url: '/fe-ornament/SelectAllCommentServlet',
                    data: {
                        'blog_id': blogId,
                        'page': (curr - 1) * 20,
                        'size': 20
                    },
                    dataType: 'json',
                    success: function (res) {
                        let data = res.data;
                        let comment_list = [];
                        for (let i = 0; i < data.length; i++) {
                            let element = `<li class="comment_item" id='comment_${data[i]['id']}'> <span>${data[i]['account']}：</span> ${data[i]['content']}
                                        <button type="button" class="layui-btn layui-btn-primary " id='reply_btn_${data[i]['id']}' lay-on="test-offset-r" onclick="reply()">回复</button>`
                            comment_list.push(element);
                        }
                        $('.comment_list').empty().append(comment_list.join(''));
                    }
                })
            }
        })
    })
}


function reply() {
    layui.use(function () {
        var layer = layui.layer;
        var util = layui.util;
        var $ = layui.$;
        // 事件
        util.on('lay-on', {
            'test-offset-r': function () {
                layer.open({
                    title: false,
                    type: 2,
                    offset: 'r',
                    anim: 'slideLeft',
                    area: ['560px', '100%'],
                    shade: 0.1,
                    shadeClose: false,
                    scrollbar: false,
                    id: 'ID-demo-layer-direction-r',
                    content: 'reply.html?rid=' + $(this).attr('id'),
                    success: function (layero) {//成功 后回调方法
                        layero.find('.layui-layer-close2').remove(); //去掉关闭按钮
                    },
                });
            }
        })
        $(document).on("click", ".layui-layer-shade", function () {
            layer.confirm('你的编辑不会保存，是否离开？', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                layer.closeAll()
            }, function () {

            });
        })
    })
}

function theResultOfAudit(audited){
    let blogId = window.location.href.split('=')[1];
    $.ajax({
        type: 'POST',
        url: '/fe-blog/resultOfAudit',
        data: {
            "blog_id" : blogId,
            "audited" : audited,
        },
        dataType: 'json',
        success: function (res) {
            if(res.code!==200){
                layer.msg("res.msg");
                return;
            }
            parent.layer.closeAll();
            layer.msg(res.msg);
            setTimeout(function () {
                parent.layer.closeAll();
                window.location.href = '../pages/auditBlog.html';
            }, 2000)
        },
        error: function () {
            console.log("错误");
            // 关闭加载图标
            layer.closeAll('loading');
        }
    });
}

function passTheAudit() {
  let audited=true;
    theResultOfAudit(audited);
}
function failTheAudit() {
    let audited=false;
    theResultOfAudit(audited);
}