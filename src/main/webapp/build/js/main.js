function toast(t){toastr.options.showEasing="linear",toastr.options.hideEasing="linear",toastr.options.closeEasing="linear",toastr.options.timeOut=1500,null!=t&&""!=t&&toastr.success(t)}$(document).ready(function(){toast(message)});