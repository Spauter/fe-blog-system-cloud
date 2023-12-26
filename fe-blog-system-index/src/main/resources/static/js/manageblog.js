$(function () {
    
    layui.use(['table','laypage'], function () {
        let table = layui.table;
        let laypage = layui.laypage;
        let allCount = 0;
        let curr = 1;
        $.ajax({
            type:'GET',
            url:'/fe-blog/AllBlogCountServlet',
            data:{},
            dataType: 'json',
            success: function(res){
                console.log(res);
                allCount = res.data;
                laypage.render({
                    elem:'manage_page',
                    count: allCount,
                    limit: 14,
                    jump: function(obj, first){
                        //obj包含了当前分页的所有参数，比如：
                        console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                        console.log(obj.limit); //得到每页显示的条数
                        curr = obj.curr;
                        console.log(obj.curr);
                        //首次不执行
                        if(!first){
                          //do something
                        }
                        table.render({
                            elem: "#blog",
                            title: "管理博客",
                            url: "/fe-blog/SelectLimitBlogController",
                            where: {
                                'page':(curr-1)*14,
                                'size': 14
                            },
                            request:{
        
                            },
                            toolbar: "true",
                            width: '1158',
                            height: 700,
                            cellMinWidth: 150,
                            toolbar: false,
                            cols: [[{ field: "checked", width: "150", title: "博客标题", unresize: true, type: "checkbox" },
                            { field: "blogId", width: "150", title: "博客id", unresize: true, type: "normal", sort: true },
                            { field: "title", width: "200", title: "博客标题", unresize: true, type: "normal" },
                            { field: "createTime", width: "150", title: "发表时间", unresize: true, type: "normal" },
                            { field: "author", width: "100", title: "作者", unresize: true, type: "normal" },
                            { field: "description", width: "200", title: "描述", unresize: true, type: "normal" },
                            { field: "cz", width: "200", title: "操作", unresize: true, toolbar: "#barDemo" }]],
                            parseData: function (res) {
                                console.log(res);
                                let dataList = [];
                                let data =res.data;
                                for(let key in data){
                                    let dataContent ={
                                        'blogId':data[key].blog['blogId'],
                                        'title':data[key].blog['title'],
                                        'createTime':data[key].blog['createTime'],
                                        'author':data[key].blog['author'],
                                        'description':data[key].blog['description']
                                    }
                                    dataList.push(dataContent);
                                }
                                
                                return {
                                    code: 0,
                                    data: dataList
                                }
                            },
                            done: function(){
                                edit();
                            }
                        })
                      }
                })
            }
        })
        
    })
    
})

function edit(){
    layui.use(['table'],function(){
        let table = layui.table;
        let session = null;
        table.on('tool(edit)',function(obj){
            var layEvent = obj.event;
            if(layEvent == 'edit'){
                $.ajax({
                    type:'GET',
                    url:'/fe-blog/DetailBlogServlet',
                    data: {'blogId':obj.data['blogId']},
                    dataType:'json',
                    success: function(res){
                        session = window.sessionStorage;
                        session.setItem('data',JSON.stringify(res.data));
                        window.location.href = 'newblog.html';
                    },
                    error: function(){
                        console.log('请求出错');
                    }
                })
            }
            if(layEvent == 'del'){
                console.log("执行了一下");
                let data = {
                    'blogId':obj.data['blogId']
                }
                $.ajax({
                    type:'POST',
                    url:'/fe-blog/DeleteBlogController',
                    data: JSON.stringify(data),
                    dataType: 'json',
                    success: function(res){
                        layui.use('layer',function(){
                            let layer = layui.layer;
                            layer.msg(res.msg);
                            obj.del();
                            
                        })
                    }

                })
            }
        })
    })
}
