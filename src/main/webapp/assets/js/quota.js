/**
 * Created by chenmuen on 2017/6/19.
 */
$(document).ready(function () {
    initQuotaChart();
    bindTimeTab();
});

function initQuotaChart() {
    initCourseNumRankChart("year");
    initCoursePriceRankChart("year");
    initCourseIncomeRankChart("year");
    initQuitRankChart("year");
    initSatisfactionRankChart("year");
    initQuitChart("month");
    initTeacherRankChart("year");
    initMemberAndIncomeYearToYearChart();
}

function reloadQuotaChart(timeType) {
    initCourseNumRankChart(timeType);
    initCoursePriceRankChart(timeType);
    initCourseIncomeRankChart(timeType);
    initQuitRankChart(timeType);
    initSatisfactionRankChart(timeType);
    initQuitChart("month");
    initTeacherRankChart(timeType);
}

function bindTimeTab() {
    $(document).on("click", "#view-tab .item", function () {
        if (!$(this).hasClass("active")) {
            $(".chart *").remove();
            $("#view-tab").find(".item").removeClass("active");
            $(this).addClass("active");
            reloadQuotaChart($(this).attr("data-tab"));
        }
    });
}

function initCourseNumRankChart(timeType) {
    if ($("#course-num-rank-chart").length > 0) {
        $.get("/quota/course_num_rank", {type: timeType}, function (data) {
            var xData = data.names;
            var yData = data.nums;
            barChart("course-num-rank-chart", "课程报名人数排名图", "课程", "选课人数", xData, yData);
        });
    }
}

function initCoursePriceRankChart(timeType) {
    if ($("#course-price-rank-chart").length > 0) {
        $.get("/quota/course_price_rank", {type: timeType}, function (data) {
            var xData = data.names;
            var yData = data.prices;
            barChart("course-price-rank-chart", "课程单价排名图", "课程", "价格", xData, yData);
        })
    }
}

function initCourseIncomeRankChart(timeType) {
    if ($("#course-income-rank-chart").length > 0) {
        $.get("/quota/course_income_rank", {type: timeType}, function (data) {
            var xData = data.names;
            var yData = data.incomes;
            barChart("course-income-rank-chart", "课程收入排名图", "课程", "收入", xData, yData);
        })
    }
}

function initQuitRankChart(timeType) {
    if ($("#course-quit-rank-chart").length > 0) {
        $.get("/quota/course_quit_rank", {type: timeType}, function (data) {
            var xData = data.names;
            var yData = data.rates;
            barChart("course-quit-rank-chart", "课程退课率排名图", "课程", "退课率", xData, yData);
        })
    }
}

function initSatisfactionRankChart(timeType) {
    if ($("#course-satisfaction-rank-chart").length > 0) {
        $.get("/quota/course_satisfaction_rank", {type: timeType}, function (data) {
            var xData = data.names;
            var yData = data.rates;
            barChart("course-satisfaction-rank-chart", "课程满意度排名图", "课程", "满意度", xData, yData);
        });
    }
}

function initQuitChart(timeType) {
    if ($("#course-quit-satisfaction-chart").length > 0) {
        $.get("/quota/course_quit_satisfaction", {type: timeType}, function (data) {
            var xData = getMonthList();
            var yData1 = data.quit;
            var yData2 = data.satisfaction;
            doubleLineChart("course-quit-satisfaction-chart", "退课率／满意度", "月份", "退课率", "满意度", xData, yData1, yData2)
        });
    }
}

function initTeacherRankChart(timeType) {
    if ($("#teacher-rank-chart").length > 0) {
        $.get("/quota/teacher_rank", {type: timeType}, function (data) {
            var xData = data.teachers;
            var yData = data.incomes;
            barChart("teacher-rank-chart", "教师收入排名", "教师", "收入", xData, yData);
        })
    }
}

function initMemberAndIncomeYearToYearChart() {
    if ($("#member-income-year-chart").length > 0) {
        $.get("/quota//member_income/year_to_year", function (data) {
            var xData = getMonthList();
            var yData1 = data.member;
            var yData2 = data.income;
            doubleLineChart("member-income-year-chart", "会员数/收入同比增长率", "时间", "会员数", "收入", xData, yData1, yData2);
        })
    }
}

