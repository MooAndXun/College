/**
 * Created by chenmuen on 2017/3/7.
 */
$(document).ready(function () {
    toast(message, error_message);
    bindButtonEvent();
    $(".ui.dropdown").dropdown();
});

function toast(message, error_message) {
    toastr.options.showEasing = 'linear';
    toastr.options.hideEasing = 'linear';
    toastr.options.closeEasing = 'linear';
    toastr.options.timeOut = 1500;
    if (message !== null && message !== "") {
        toastr.success(message);
    }
    if (error_message !== null && error_message !== "") {
        toastr.error(error_message);
    }
}



function bindButtonEvent() {
    bindBuyButton();
    bindPayButton();
    bindCancelButton();
    bindQuitButton();
    bindApproveButton();
    bindSettlementButton();
    bindLogoutButton();
    bindScoreButton();
    bindMemberCancelButton();
}

function bindBuyButton() {
    $(document).on("click", ".buy-button", function () {
        function callback() {
            var id = $(this).attr("data-id");
            post("/course/reserve", {id: id});
        }

        modal("你确定预订该课程吗？", callback.bind(this));
    });
}

function bindPayButton() {
    $(document).on("click", ".pay-button", function () {
        function callback() {
            var id = $(this).parents(".item").attr("data-order-id");
            post("/course/pay", {id: id});
        }

        modal("你确定支付该课程吗？(支付后退课仅返还50%费用)", callback.bind(this));
    });
}
function bindCancelButton() {
    $(document).on("click", ".cancel-button", function () {
        function callback() {
            var id = $(this).parents(".item").attr("data-order-id");
            post("/course/cancel", {id: id});
        }

        modal("你确定退订该课程吗？", callback.bind(this));
    });
}
function bindQuitButton() {
    $(document).on("click", ".quit-button", function () {
        function callback() {
            var id = $(this).parents(".item").attr("data-order-id");
            post("/course/quit", {id: id});
        }

        modal("你确定退出该课程吗？(将仅退还50%费用)", callback.bind(this));
    });
}

function bindApproveButton() {
    $(document).on("click", ".approve-button", function () {
        function callback() {
            var id = $(this).parents(".item").attr("data-course-id");
            post("/course/approve", {id: id});
        }

        modal("你确定审批通过该课程吗", callback.bind(this));
    });
}

function bindSettlementButton() {
    $(document).on("click", ".settlement-button", function () {
        function callback() {
            var id = $(this).parents(".item").attr("data-course-id");
            post("/settlement", {id: id});
        }

        modal("你确定结算该课程吗", callback.bind(this));
    });
}

function bindLogoutButton() {
    $(document).on("click", ".sign-out-icon", function () {
        post("/logout", null);
    });
}

function bindScoreButton() {
    $(document).on("click", ".score-button", function () {
        var orderId = $(this).parents(".item").attr("data-order-id");
        var courseId = $(this).parents(".item").attr("data-course-id");

        $(".ui.modal#score-modal")
            .modal('setting', 'transition', "FadeUp")
            .modal({
                inverted: true,
                onApprove: function () {
                    var score = $("#score-input").val();
                    post("/course/score", {courseId: courseId,orderId: orderId, score: score});
                }
            })
            .modal('show');
    })
}

function bindMemberTypeCheckbox(value) {
    if (value == 'member') {
        $('.non-member-input').addClass("invisible");
        $('.member-input').removeClass("invisible");
    } else if (value == 'non-member') {
        $('.member-input').addClass("invisible");
        $('.non-member-input').removeClass("invisible");
    }
}

function bindRegisterTypeCheckBox(option) {
    if(option==0) {
        $("#account-input").removeClass("invisible");
    } else {
        $("#account-input").addClass("invisible");
    }
}

function bindMemberCancelButton() {
    $(document).on("click", ".member-cancel-button", function () {
        function callback() {
            post("/user/cancel", {});
        }

        modal("注销后账号将永久不能使用！你确定注销你的会员资格吗？", callback.bind(this));
    });
}

function modal(message, callback) {
    $("#modal-content").html(message);
    $(".ui.modal#modal")
        .modal('setting', 'transition', "FadeUp")
        .modal({
            inverted: true,
            onApprove: function () {
                callback();
            }
        })
        .modal('show');
}

function post(URL, PARAMS) {
    var temp = document.createElement("form");
    temp.action = URL;
    temp.method = "post";
    temp.style.display = "none";
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        // alert(opt.name)
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    return temp;
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

function doubleLineChart(id, title, xName, yName1, yName2, xData, yData1, yData2) {
    var myChart = echarts.init(document.getElementById(id));
    var option = {
        title: {
            text: title,
            textStyle: {
                fontSize: 22
            },
            padding:10,
            left: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:[yName1,yName2],
            left: "right"
        },
        grid: {
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                name: xName,
                nameGap: 15,
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
                name: yName1,
                nameGap: 15,
                axisLabel: {
                    textStyle: {
                        fontSize: 14
                    }
                }
            },
            {
                type: 'value',
                name: yName2,
                nameGap: 15,
                axisLabel: {
                    textStyle: {
                        fontSize: 14
                    }
                }
            }
        ],
        series: [
            {
                name: yName1,
                type: 'line',
                data: yData1
            },
            {
                name: yName2,
                type: 'line',
                yAxisIndex: 1,
                data: yData2
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