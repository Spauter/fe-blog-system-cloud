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
                    findAllComment(blogId);
                })
            }
        })
    })
}

//TODO 语音输入部分
function inputByMicrophone() {
    console.log("点到了话筒")
    layui.use('layer', function () {
        layer.msg("请在article.js中第88行完善", {
            icon: 6,
            time: 1000
        })
    })
    //把结果显示在输入框上
    let yourComment = "语音输入结果"
    $('#comment').val(yourComment);
}

function findAllComment(blogId) {

    layui.use('laypage', function () {
        let laypage = layui.laypage;
        let allCount = 0;
        laypage.render({
            elem: 'commentPage',
            count: 100,
            limit: 20,
            jump: function (obj, first) {
                curr = obj.curr;

                if (!first) {

                }

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
                            let element = `<li class="comment_item" id='comment_${data[i]['id']}'> <span>${data[i]['account']}：</span> ${data[i]['content']}`;
                            //TODO（回复按钮）
// <!--                                      <button type="button" class="layui-btn layui-btn-primary" onclick="reply()" lay-on="test-offset-l">从左往右</button></li>
                            comment_list.push(element);
                        }
                        $('.comment_list').empty().append(comment_list.join(''));
                    }
                })
            }
        })
    })
}

//TODO 回复部分
function reply() {
    layui.use(function () {
        var layer = layui.layer;
        var util = layui.util;
        var $ = layui.$;
        // 事件
        util.on('lay-on', {
            'test-offset-l': function () {
                layer.open({
                    type: 1,
                    offset: 'l',
                    anim: 'slideRight', // 从左往右
                    area: ['320px', '100%'],
                    shade: 0.1,
                    shadeClose: true,
                    id: 'ID-demo-layer-direction-l',
                    content: '<div style="padding: 16px;">任意 HTML 内容</div>'
                });
            }
        })
    })
}
