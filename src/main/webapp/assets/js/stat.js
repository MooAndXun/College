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
    initSiteOrganBarChart();
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
}

function initStudentCourseChart() {
    if ($("#student-course-line").length > 0) {

        $.get("/stat/student/course/line?studentId=" + userId, function (message) {
            var xData = getMonthList();
            var yData = message ? message : [1, 2, 2, 3, 1, 1, 2, 2, 3, 1, 1, 2];

            lineChart("student-course-line", "参与课程数量曲线图", "月份", "课程数", xData, yData);
        });
    }
}


/*---------------------------- 机构统计图 ----------------------------*/
function initOrganIncomeChart() {
    if ($("#organ-income-line").length > 0) {

        $.get("/stat/organ/income/line?organId=" + userId, function (message) {
            var xData = getMonthList();
            var yData = message ? message : [1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000];

            lineChart("organ-income-line", "机构收入曲线图", "月份", "收入／¥", xData, yData);
        });
    }
}

function initOrganMemberLineChart() {
    if ($("#organ-member-line").length > 0) {

        $.get("/stat/organ/member/line?organId=" + userId, function (message) {
            var xData = getMonthList();
            var yData = message ? message : [10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290];

            lineChart("organ-member-line", "机构学员变化曲线图", "月份", "学员人数", xData, yData);
        });
    }

}

function initOrganMemberBarChart() {
    if ($("#organ-member-bar").length > 0) {

        $.get("/stat/organ/member/bar?organId=" + userId, function (message) {
            var xData = message ? message.names : ['J2EE', '心理学入门', '变态心理学', '演员的自我修养', '演员的自我修养1'];
            var yData = message ? message.members : [100, 70, 55, 32, 29];

            barChart("organ-member-bar", "课程学员人数排名图", "月份", "学员人数", xData, yData);
        });
    }

}

/*---------------------------- 站点统计图 ----------------------------*/
function initSiteIncomeLineChart() {
    if ($("#site-income-line").length > 0) {

        $.get("/stat/site/income/line", function (message) {
            var xData = getMonthList();
            var yData = message ? message : [1000, 2000, 2000, 3000, 1231, 988, 2322, 10000, 5990, 2400, 1000, 2000];

            lineChart("site-income-line", "网站收入曲线图", "月份", "收入／¥", xData, yData);
        });
    }
}

function initSiteMemberLineChart() {
    if ($("#site-member-line").length > 0) {
        $.get("/stat/site/member/line", function (message) {
            var xData = getMonthList();
            var yData = message ? message : [10, 14, 50, 70, 100, 120, 150, 178, 200, 219, 250, 290];

            lineChart("site-member-line", "网站学员变化曲线图", "月份", "学员人数", xData, yData);
        });
    }

}

function initSiteOrganBarChart() {
    if ($("#site-organ-bar").length > 0) {
        $.get("/stat/site/organ/bar", function (message) {
            var xData = message ? message.names:[];
            var yData = message ? message.nums:[];
            barChart("site-organ-bar", "网站机构招生情况", "机构", "学员人数", xData, yData);
        });
    }
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
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
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
                    interval: 0,
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
                barWidth: '60px',
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