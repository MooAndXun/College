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
}

var primaryColor = "#20C4C8";

/*---------------------------- 学员统计图 ----------------------------*/
function initStudentScoreChart() {
    if($("#student-score-bar").length > 0) {
        var myChart = echarts.init(document.getElementById('student-score-bar'));
        var option = {
            title: {
                text: "成绩分布图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['0-60', '60-70', '70-80', '80-90', '90-100'],
                    name : '成绩',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '课程数',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            series : [
                {
                    name:'成绩',
                    type:'bar',
                    barWidth: '60%',
                    data:[1, 2, 2, 3, 1]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };

        myChart.setOption(option);
    }
}

function initStudentCourseChart() {
    if($('#student-course-line').length > 0) {
        var myChart = echarts.init(document.getElementById('student-course-line'));
        var option = {
            title: {
                text: "成绩分布图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    name : '月份',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '课程数',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            series : [
                {
                    name:'加入课程数',
                    type:'line',
                    data:[1, 2, 2, 3, 1,1, 2, 2, 3, 1, 1, 2]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };

        myChart.setOption(option);
    }
}


/*---------------------------- 机构统计图 ----------------------------*/
function initOrganIncomeChart() {
    if($("#organ-income-line").length > 0){
        var myChart = echarts.init(document.getElementById('organ-income-line'));
        var option = {
            title: {
                text: "机构收入曲线图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    name : '月份',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '收入／¥',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14,
                        }
                    }
                }
            ],
            series : [
                {
                    name:'收入',
                    type:'line',
                    data:[1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };

        myChart.setOption(option);
    }

}

function initOrganMemberLineChart() {
    if($("#organ-member-line").length > 0){
        var myChart = echarts.init(document.getElementById('organ-member-line'));
        var option = {
            title: {
                text: "机构学员变化曲线图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    name : '月份',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '学员人数',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14,
                        }
                    }
                }
            ],
            series : [
                {
                    name:'学员人数',
                    type:'line',
                    data:[10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };

        myChart.setOption(option);
    }

}

function initOrganMemberBarChart() {
    if($("#organ-member-bar").length > 0){
        var myChart = echarts.init(document.getElementById('organ-member-bar'));
        var option = {
            title: {
                text: "课程学员人数排名图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['J2EE', '心理学入门', '变态心理学', '演员的自我修养', '演员的自我修养1'],
                    name : '课程名',
                    nameGap: 20,
                    axisLabel: {
                        interval: 0,
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '学员人数',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14,
                        }
                    }
                }
            ],
            series : [
                {
                    name:'学员人数',
                    type:'bar',
                    data:[100, 70, 55, 32, 29]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };

        myChart.setOption(option);
    }
}

/*---------------------------- 站点统计图 ----------------------------*/
function initSiteIncomeLineChart() {
    if($("#site-income-line").length > 0){
        var myChart = echarts.init(document.getElementById('site-income-line'));
        var option = {
            title: {
                text: "网站收入曲线图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    name : '月份',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '收入／¥',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14,
                        }
                    }
                }
            ],
            series : [
                {
                    name:'收入',
                    type:'line',
                    data:[1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };
        myChart.setOption(option);
    }
}

function initOrganMemberLineChart() {
    if($("#site-member-line").length > 0){
        var myChart = echarts.init(document.getElementById('site-member-line'));
        var option = {
            title: {
                text: "网站学员变化曲线图",
                textStyle: {
                    fontSize: 22
                },
                left: 'center',
            },
            color: [primaryColor],
            tooltip : {
                trigger: 'axis',
            },
            grid: {
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Jen', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                    name : '月份',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name : '学员人数',
                    nameGap: 25,
                    axisLabel: {
                        textStyle: {
                            fontSize: 14,
                        }
                    }
                }
            ],
            series : [
                {
                    name:'学员人数',
                    type:'line',
                    data:[10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290]
                }
            ],
            textStyle: {
                fontSize: 16
            }
        };

        myChart.setOption(option);
    }

}