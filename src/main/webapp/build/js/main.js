/**
 * Created by chenmuen on 2017/3/7.
 */
$(document).ready(function () {
    toast(message);
    bindButtonEvent();
});

function toast(message) {
    toastr.options.showEasing = 'linear';
    toastr.options.hideEasing = 'linear';
    toastr.options.closeEasing = 'linear';
    toastr.options.timeOut = 1500;
    if(message!==null&&message!=="") {
        toastr.success(message);
    }
}

function modal(message, callback) {
    $("#modal-content").html(message);
    $(".ui.modal")
        .modal('setting', 'transition', "FadeUp")
        .modal({
            inverted: true,
            onApprove: function () {
                callback();
            }
        })
        .modal('show');
}

function bindButtonEvent() {
    bindBuyButton();
    bindPayButton();
    bindCancelButton();
    bindQuitButton();
    bindApproveButton();
    bindSettlementButton();
}

function bindBuyButton() {
    $(document).on("click", ".buy-button", function () {
        function callback() {
            var id = $(this).attr("data-id");
            post("/course/reserve", {id: id});
        }
        modal("你确定购买该课程吗？", callback.bind(this));
    });
}

function bindPayButton() {
    $(document).on("click", ".pay-button", function () {
        function callback() {
            var id = $(this).parents(".item").attr("data-order-id");
            post("/course/pay", {id: id});
        }
        modal("你确定支付该课程吗？", callback.bind(this));
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