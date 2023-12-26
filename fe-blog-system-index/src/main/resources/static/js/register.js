let clicked = false;
let register =false;

$(document).ready(function () {
    // 绑定获取验证码按钮点击事件
    $('#myButton').click(sendEmail);

    // 绑定表单提交事件
    $('#load_form').on('submit', function (e) {
        e.preventDefault();
        // 处理注册逻辑
    });
});

function toRegister() {
    //设置不可提交导致的多次表单提交
    if (register){
        layui.use(['layer'], function () {
            let layer = layui.layer;
            layer.msg("请勿重复点击", {
                icon: 2,
                time: 1000
            })
        });
        return;
    }
    register=true;
    let data = $('#load_form').serializeArray();
    $('#myButton').prop('disabled', true);
    console.log(data);
    $.ajax({
        type: 'POST',
        url: 'fe-user/register',
        data: {
            'username': data[0].value,
            'password': data[1].value,
            'confirmPassword': data[2].value,
            'email': data[3].value,
            'vericode': data[4].value
        },
        dataType: 'json',
        success: function (res) {
            // 处理注册成功或失败逻辑
            if (res.code === 500) {
                layui.use(['layer'], function () {
                    let layer = layui.layer;
                    layer.msg(res.msg, {
                        icon: 2,
                        time: 1000
                    })

                })

            } else {
                layui.use(['layer'], function () {
                    let layer = layui.layer;
                    layer.msg('注册成功！', {
                        icon: 6,
                        time: 5000
                    })

                })
                setTimeout(function () {
                    register=false;
                    window.location.href = 'index.html';
                }, 1000);
            }
            $('#load_form').prop('disabled', false);
        },
        error :function (){
            $('#load_form').prop('disabled', false);
            let layer = layui.layer;
            layer.msg('请求发送失败！', {
                icon: 6,
                time: 1000
            })
        }
    });
}

function sendEmail() {
    const layer = layui.layer;
    let data = $('#load_form').serializeArray();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let email=data[3].value;
    if( emailRegex.test(email)) {
        // 使用 event 参数获取事件对象
        $('#myButton').on('click', function (event) {

            if (clicked) {
                layer.msg('60秒后才能再次点击', {
                    icon: 2,
                    time: 1000
                });
                return;
            }

            $.ajax({
                type: 'GET',
                url: 'fe-user/sendEmail',
                data: {
                    email,
                },
                success: function (res) {
                    if (res.data !== 200) {
                    }
                    layer.msg(res.msg, {
                        icon: 6,
                        time: 1000
                    });
                }
            });

            // 模拟处理耗时操作
            layer.msg('验证码发送成功，请耐心等待', {
                icon: 6,
                time: 1000
            });
            clicked = true;

            setTimeout(function () {
                clicked = false;
                layer.msg('60秒已过，可以再次点击', {
                    icon: 6,
                    time: 1000
                });
            }, 60000);
        });
    }else {
        layer.msg('邮箱格式不正确', {
            icon: 2,
            time: 1000
        });
    }
}