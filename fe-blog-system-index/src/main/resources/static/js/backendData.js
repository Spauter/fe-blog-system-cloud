$(function () {
    updateRAMData();
    updateClickData();
    updateCPUData();
    updateDiskData();
})


// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('RAM'));
// 指定图表的配置项和数据
option = {
    color: ["#A2505D", "#BB9795"],
    series: [
        {
            type: 'pie',
            radius: ['30%', '50%'],
            data: [
                {
                    value: 10,
                    name: '已使用'
                },
                {
                    value: 10,
                    name: '空闲'
                },
            ]
        }
    ],
    title: {
        show: true,
        text: "RAM",
        subtext: "已使用" + 0 + "G,共" + 0 + "G"
    },
}

// 使用刚指定的配置项和数据显示图表。


function updateRAMData() {
    $.ajax({
        type: 'GET',
        url: '../fe-ornament/RAMUsage',
        dataType: 'json',
        success: function (res) {
            let freeMemory = res.freeMemory;
            let usageMemory = res.usagMemory;
            let totoalMemory = res.totoalMemory
            // 更新图表数据
            myChart.setOption({
                series: [{
                    data: [
                        {
                            value: usageMemory,
                            name: '已使用',
                        },
                        {
                            value: freeMemory,
                            name: '空闲',
                        }
                    ]
                }],
                title: {
                    show: true,
                    text: "RAM",
                    subtext: "已使用" + usageMemory + "G,共" + totoalMemory + "G"
                },
            });
        },
        error: function () {
        }
    });
}


var CPUChart = echarts.init(document.getElementById('CPU'));
// 指定图表的配置项和数据
CPUoption = {
    color: ["#6D9E64", "#EBE7E7"],
    series: [
        {
            type: 'pie',
            radius: ['30%', '50%'],
            data: [
                {
                    value: 10,
                    name: '已使用'
                },
                {
                    value: 10,
                    name: '空闲'
                },
            ]
        }
    ],
    title: {
        show: true,
        text: "CPU",
        subtext: "已使用" + 0 + "%"
    },
}

// 使用刚指定的配置项和数据显示图表。


function updateCPUData() {
    $.ajax({
        type: 'GET',
        url: '../fe-ornament/CPUUsage',
        dataType: 'json',
        success: function (res) {
            let cpuUsage = res.cpuUsage;
            let cpuFree = res.cpuFree
            if (cpuUsage > 100) {
                cpuUsage = 100;
                cpuFree = 0;
            }
            // 更新图表数据
            CPUChart.setOption({
                series: [{
                    data: [
                        {
                            value: cpuUsage,
                            name: '已使用',
                        },
                        {
                            value: cpuFree,
                            name: '空闲',
                        }
                    ]
                }],
                title: {
                    show: true,
                    text: "CPU",
                    subtext: "已使用" + cpuUsage + "%"
                },
            });
        },
        error: function () {
        }
    });
}

var ClicksChart = echarts.init(document.getElementById('Clicks'));
Clicksoption = {
    xAxis: {
        data: ['A', 'B', 'C', 'D', 'E'],
        axisLabel: {
            interval: 0
        }
    },
    yAxis: {},
    series: [
        {
            data: [10, 22, 28, 23, 19],
            type: 'line',
            label: {
                show: true,
                position: 'bottom',
                textStyle: {
                    fontSize: 20
                }
            }
        }
    ]
};

function updateClickData() {
    $.ajax({
        type: 'GET',
        url: '../fe-blog/hotBlogs',
        dataType: 'json',
        success: function (res) {
            let data = res.data;
            let length = res.count;
            var titles = [];
            var clicks = [];
            for (i = 0; i < length; i++) {
                titles[i] = data[i].title;
                clicks[i] = data[i].clicks;
            }
            // 更新图表数据
            ClicksChart.setOption({
                xAxis: {
                    data: titles
                },
                yAxis: {},
                series: [
                    {
                        data: clicks,
                        type: 'line',
                        label: {
                            show: true,
                            position: 'bottom',
                            textStyle: {
                                fontSize: 20
                            }
                        }
                    }
                ],
                title: {
                    show: true,
                    text: "点击数最多的博客",
                },
            });
        },
        error: function () {
            console.log("获取失败")
        }
    });
}

var DiskChart = echarts.init(document.getElementById('Disk'));

// 指定图表的配置项和数据
Diskoption = {
    color: ["#26A0DA", "#E6E6E6"],
    series: [
        {
            type: 'pie',
            radius: ['30%', '50%'],
            data: [
                {
                    value: 10,
                    name: '已使用'
                },
                {
                    value: 10,
                    name: '空闲'
                },
            ]
        }
    ],
    title: {
        show: true,
        text: "磁盘",
        subtext: "已使用" + 0 + "G,共0G"
    },
}

// 使用刚指定的配置项和数据显示图表。
function updateDiskData() {
    $.ajax({
        type: 'GET',
        url: '../fe-ornament/DiskUsage',
        dataType: 'json',
        success: function (res) {
            let freeSpace = res.freeSpace;
            let usage = res.usage
            let total = res.total
            // 更新图表数据
            DiskChart.setOption({
                color: ["#26A0DA", "#E6E6E6"],
                series: [{
                    data: [
                        {
                            value: usage,
                            name: '已使用',
                        },
                        {
                            value: freeSpace,
                            name: '空闲',
                        }
                    ]
                }],
                title: {
                    show: true,
                    text: "磁盘",
                    subtext: "已使用" + usage + "G,总共" + total + "G"
                },
            });
        },
        error: function () {
        }
    });
}

// var HotTagChart=echarts.init(document.getElementById('hotTags'))
// var hotFieldChart=echarts.init(document.getElementById('hotFields'))


myChart.setOption(option);
ClicksChart.setOption(Clicksoption);
CPUChart.setOption(CPUoption);
DiskChart.setOption(Diskoption);
//自动发送请求
setInterval(updateDiskData, 120000);
setInterval(updateRAMData, 7500);
setInterval(updateCPUData, 2500);