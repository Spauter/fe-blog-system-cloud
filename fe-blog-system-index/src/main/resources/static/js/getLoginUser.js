
$(function () {
    userInfoLoad();
})

function userInfoLoad() {
    $.ajax({
        type: 'GET',
        url: '/fe-blog/SelectUserServlet',
        data: {},
        dataType: 'json',
        success: function (res) {
            let code = res.code;
            console.log(res);
            if (code === 200) {
                loginUser = res.data; // 将值赋给全局变量
                $('#user_avatar').attr("src", loginUser.avatar);
                $('.user_avatar>img').attr("src", loginUser.avatar);
            } else {
                let layer = layui.layer;
                layer.msg(res.msg, {
                    icon: 2,
                    time: 1000
                })
                setTimeout(function () {
                    window.location.href = '../index.html';
                }, 2000);
            }
        }
    })
}
