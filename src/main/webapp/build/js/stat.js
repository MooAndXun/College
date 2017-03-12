/**
 * Created by chenmuen on 2017/3/11.
 */
$(document).ready(function () {
    initChart();
});

function initChart() {
    initStudentScoreChart();
    initStudentCourseChart();
    initOrganIncomeChart();
    initOrganMemberLineChart();
    initOrganMemberBarChart();
    initSiteIncomeLineChart();
    initSiteMemberLineChart();
}

var primaryColor = "#20C4C8";

/*---------------------------- 学员统计图 ----------------------------*/
function initStudentScoreChart() {
    if ($("#student-score-bar").length > 0) {
        $.get("/stat/student/score/bar?studentId=" + userId, function (message) {
            var xData = ['0-60', '60-70', '70-80', '80-90', '90-100'];
            var yData = message ? message : [1, 2, 2, 3, 1];
            barChart("student-score-bar", "成绩分布图", "成绩", "课程数", xData, yData);
        });

    }

    // if($("#student-score-bar").length > 0) {
    //     var myChart = echarts.init(document.getElementById('student-score-bar'));
    //     var option = {
    //         title: {
    //             text: "成绩分布图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip : {
    //             trigger: 'axis',
    //             axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    //                 type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    //             }
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis : [
    //             {
    //                 type : 'category',
    //                 data : ['0-60', '60-70', '70-80', '80-90', '90-100'],
    //                 name : '成绩',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis : [
    //             {
    //                 type : 'value',
    //                 name : '课程数',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         series : [
    //             {
    //                 name:'成绩',
    //                 type:'bar',
    //                 barWidth: '60%',
    //                 data:[1, 2, 2, 3, 1]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //
    //     myChart.setOption(option);
    // }
}

function initStudentCourseChart() {
    // $.get("/stat/student/course", function (message) {
    var xData = ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var yData = message ? message.data : [1, 2, 2, 3, 1, 1, 2, 2, 3, 1, 1, 2];

    if ($("#student-course-line").length > 0) {
        lineChart("student-course-line", "参与课程数量曲线图", "月份", "课程数", xData, yData);
    }
    // });

    // if($('#student-course-line').length > 0) {
    //     var myChart = echarts.init(document.getElementById('student-course-line'));
    //     var option = {
    //         title: {
    //             text: "成绩分布图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip : {
    //             trigger: 'axis',
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis : [
    //             {
    //                 type : 'category',
    //                 data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //                 name : '月份',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis : [
    //             {
    //                 type : 'value',
    //                 name : '课程数',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         series : [
    //             {
    //                 name:'加入课程数',
    //                 type:'line',
    //                 data:[1, 2, 2, 3, 1,1, 2, 2, 3, 1, 1, 2]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //
    //     myChart.setOption(option);
    // }
}


/*---------------------------- 机构统计图 ----------------------------*/
function initOrganIncomeChart() {
    $.get("/stat/organ/income/line?organId=" + userId, function (message) {
        var xData = getMonthList();
        var yData = message ? message : [1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000];

        if ($("#organ-income-line").length > 0) {
            lineChart("organ-income-line", "机构收入曲线图", "月份", "收入／¥", xData, yData);
        }
    });

    // if($("#organ-income-line").length > 0){
    //     var myChart = echarts.init(document.getElementById('organ-income-line'));
    //     var option = {
    //         title: {
    //             text: "机构收入曲线图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip : {
    //             trigger: 'axis',
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis : [
    //             {
    //                 type : 'category',
    //                 data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //                 name : '月份',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis : [
    //             {
    //                 type : 'value',
    //                 name : '收入／¥',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14,
    //                     }
    //                 }
    //             }
    //         ],
    //         series : [
    //             {
    //                 name:'收入',
    //                 type:'line',
    //                 data:[1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //
    //     myChart.setOption(option);
    // }

}

function initOrganMemberLineChart() {
    $.get("/stat/organ/member/line?organId=" + userId, function (message) {
        var xData = getMonthList();
        var yData = message ? message : [10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290];

        if ($("#organ-member-line").length > 0) {
            lineChart("organ-member-line", "机构学员变化曲线图", "月份", "学员人数", xData, yData);
        }
    });

    // if ($("#organ-member-line").length > 0) {
    //     var myChart = echarts.init(document.getElementById('organ-member-line'));
    //     var option = {
    //         title: {
    //             text: "机构学员变化曲线图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip: {
    //             trigger: 'axis',
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis: [
    //             {
    //                 type: 'category',
    //                 data: ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //                 name: '月份',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis: [
    //             {
    //                 type: 'value',
    //                 name: '学员人数',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14,
    //                     }
    //                 }
    //             }
    //         ],
    //         series: [
    //             {
    //                 name: '学员人数',
    //                 type: 'line',
    //                 data: [10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //
    //     myChart.setOption(option);
    // }

}

function initOrganMemberBarChart() {
    $.get("/stat/organ/member/bar?organId=" + userId, function (message) {
        var xData = message ? message.names : ['J2EE', '心理学入门', '变态心理学', '演员的自我修养', '演员的自我修养1'];
        var yData = message ? message.members : [100, 70, 55, 32, 29];

        if ($("#organ-member-bar").length > 0) {
            barChart("organ-member-bar", "课程学员人数排名图", "月份", "学员人数", xData, yData);
        }
    });

    // if ($("#organ-member-bar").length > 0) {
    //     var myChart = echarts.init(document.getElementById('organ-member-bar'));
    //     var option = {
    //         title: {
    //             text: "课程学员人数排名图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip: {
    //             trigger: 'axis',
    //             axisPointer: {            // 坐标轴指示器，坐标轴触发有效
    //                 type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    //             }
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis: [
    //             {
    //                 type: 'category',
    //                 data: ['J2EE', '心理学入门', '变态心理学', '演员的自我修养', '演员的自我修养1'],
    //                 name: '课程名',
    //                 nameGap: 20,
    //                 axisLabel: {
    //                     interval: 0,
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis: [
    //             {
    //                 type: 'value',
    //                 name: '学员人数',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14,
    //                     }
    //                 }
    //             }
    //         ],
    //         series: [
    //             {
    //                 name: '学员人数',
    //                 type: 'bar',
    //                 data: [100, 70, 55, 32, 29]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //
    //     myChart.setOption(option);
    // }
}

/*---------------------------- 站点统计图 ----------------------------*/
function initSiteIncomeLineChart() {
    $.get("/stat/site/income/line", function (message) {
        var xData = getMonthList();
        var yData = message ? message : [1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000];

        if ($("#site-income-line").length > 0) {
            lineChart("site-income-line", "网站收入曲线图", "月份", "收入／¥", xData, yData);
        }
    });

    // if ($("#site-income-line").length > 0) {
    //     var myChart = echarts.init(document.getElementById('site-income-line'));
    //     var option = {
    //         title: {
    //             text: "网站收入曲线图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip: {
    //             trigger: 'axis',
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis: [
    //             {
    //                 type: 'category',
    //                 data: ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //                 name: '月份',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis: [
    //             {
    //                 type: 'value',
    //                 name: '收入／¥',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14,
    //                     }
    //                 }
    //             }
    //         ],
    //         series: [
    //             {
    //                 name: '收入',
    //                 type: 'line',
    //                 data: [1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //     myChart.setOption(option);
    // }
}

function initSiteMemberLineChart() {
    $.get("/stat/site/member/line", function (message) {
        var xData = getMonthList();
        var yData = message ? message : [10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290];

        if ($("#site-member-line").length > 0) {
            lineChart("site-member-line", "网站学员变化曲线图", "月份", "学员人数", xData, yData);
        }
    });

    // if ($("#site-member-line").length > 0) {
    //     var myChart = echarts.init(document.getElementById('site-member-line'));
    //     var option = {
    //         title: {
    //             text: "网站学员变化曲线图",
    //             textStyle: {
    //                 fontSize: 22
    //             },
    //             left: 'center',
    //         },
    //         color: [primaryColor],
    //         tooltip: {
    //             trigger: 'axis',
    //         },
    //         grid: {
    //             containLabel: true
    //         },
    //         xAxis: [
    //             {
    //                 type: 'category',
    //                 data: ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    //                 name: '月份',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14
    //                     }
    //                 }
    //             }
    //         ],
    //         yAxis: [
    //             {
    //                 type: 'value',
    //                 name: '学员人数',
    //                 nameGap: 25,
    //                 axisLabel: {
    //                     textStyle: {
    //                         fontSize: 14,
    //                     }
    //                 }
    //             }
    //         ],
    //         series: [
    //             {
    //                 name: '学员人数',
    //                 type: 'line',
    //                 data: [10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290]
    //             }
    //         ],
    //         textStyle: {
    //             fontSize: 16
    //         }
    //     };
    //
    //     myChart.setOption(option);
    // }
}

function lineChart(id, title, xName, yName, xData, yData) {
    var myChart = echarts.init(document.getElementById(id));
    var option = {
        title: {
            text: title,
            textStyle: {
                fontSize: 22
            },
            left: 'center',
        },
        color: [primaryColor],
        tooltip: {
            trigger: 'axis',
        },
        grid: {
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                name: xName,
                nameGap: 25,
                axisLabel: {
                    textStyle: {
                        fontSize: 14
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: yName,
                nameGap: 25,
                axisLabel: {
                    textStyle: {
                        fontSize: 14
                    }
                }
            }
        ],
        series: [
            {
                name: yName,
                type: 'line',
                data: yData
            }
        ],
        textStyle: {
            fontSize: 16
        }
    };

    myChart.setOption(option);
}

function barChart(id, title, xName, yName, xData, yData) {
    var myChart = echarts.init(document.getElementById(id));
    var option = {
        title: {
            text: title,
            textStyle: {
                fontSize: 22
            },
            left: 'center',
        },
        color: [primaryColor],
        tooltip: {
            trigger: 'axis',
        },
        grid: {
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                name: xName,
                nameGap: 25,
                axisLabel: {
                    textStyle: {
                        fontSize: 14
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: yName,
                nameGap: 25,
                axisLabel: {
                    textStyle: {
                        fontSize: 14
                    }
                }
            }
        ],
        series: [
            {
                name: yName,
                type: 'bar',
                data: yData
            }
        ],
        textStyle: {
            fontSize: 16
        }
    };

    myChart.setOption(option);
}

function getMonthList() {
    var date = new Date();
    var month = date.getMonth();
    var monthList = ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];


    var temp = monthList.slice(0, month + 1);
    var temp2 = monthList.slice(month + 1, monthList.length);
    var result = Array.prototype.concat(temp2, temp);
    return result;
}