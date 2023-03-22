const HTTP_METHOD = {
  POST: "POST",
  GET: "GET"
}

const HTTP_CONTENT_TYPE = {
  APPLICATION_JSON: 'application/json',
  APPLICATION_FORM: 'application/x-www-form-urlencoded'
}

const defaultOptions = {
  async: false, //동기 호출
  contentType: HTTP_CONTENT_TYPE.APPLICATION_JSON,
  dataType: 'json', //서버 데이터 반환 타입
  callback: ajaxCallBackSuccess
}

function requestAjax(method, url, options, data) {
  options = $.extend({}, defaultOptions, options);

  $.ajax({
    async: options.async,
    url: url,
    type: method,
    contentType: options.contentType,
    data: data || '',
    dataType: options.dataType,
    error : ajaxCallBackError,
    success : options.callback,
  });
}

function ajaxCallBackSuccess(res) {
  console.log(res);
}

function ajaxCallBackError(res){
  console.error(res);
}